/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {API, getLocalizableLabel} from '@liferay/object-js-components-web';

export type ObjectsOptionsList = {
	label: string;
	options: LabelValueObject[];
	type: string;
}[];

function fillSelect(
	label: string,
	options: LabelValueObject[],
	objectsOptionsList: ObjectsOptionsList
) {
	if (options.length) {
		objectsOptionsList.push({label, options, type: 'group'});
	}
}

interface FetchObjectDefinitionsProps {
	objectDefinitionsRelationshipsURL: string;
	setAddObjectEntryDefinitions: (values: AddObjectEntryDefinitions[]) => void;
	setObjectOptions: (values: ObjectsOptionsList) => void;
	setSelectedObjectDefinition?: (value: string) => void;
	values: Partial<ObjectAction>;
}

export async function fetchObjectDefinitions({
	objectDefinitionsRelationshipsURL,
	setAddObjectEntryDefinitions,
	setObjectOptions,
	setSelectedObjectDefinition,
	values,
}: FetchObjectDefinitionsProps) {
	const addObjectEntryDefinitions = await API.fetchJSON<
		AddObjectEntryDefinitions[]
	>(objectDefinitionsRelationshipsURL);

	const relatedObjects: LabelValueObject[] = [];
	const unrelatedObjects: LabelValueObject[] = [];

	addObjectEntryDefinitions?.forEach((object) => {
		const {externalReferenceCode, id, label, system} = object;

		const target = object.related ? relatedObjects : unrelatedObjects;

		target.push({label, value: `${externalReferenceCode},${id},${system}`});
	});

	const objectsOptionsList: ObjectsOptionsList = [];

	fillSelect(
		Liferay.Language.get('related-objects'),
		relatedObjects,
		objectsOptionsList
	);

	fillSelect(
		Liferay.Language.get('unrelated-objects'),
		unrelatedObjects,
		objectsOptionsList
	);

	const {
		objectDefinitionExternalReferenceCode,
	} = values.parameters as ObjectActionParameters;

	if (setSelectedObjectDefinition && objectDefinitionExternalReferenceCode) {
		const {
			defaultLanguageId,
			label,
			name,
		} = await API.getObjectDefinitionByExternalReferenceCode(
			objectDefinitionExternalReferenceCode
		);

		setSelectedObjectDefinition(
			getLocalizableLabel(defaultLanguageId, label, name)
		);
	}

	setObjectOptions(objectsOptionsList);
	setAddObjectEntryDefinitions(addObjectEntryDefinitions);
}

export async function fetchObjectDefinitionFields(
	objectDefinitionId: number,
	objectDefinitionExternalReferenceCode: string,
	systemObject: boolean,
	values: Partial<ObjectAction>,
	isValidField: (
		{businessType, name, objectFieldSettings, system}: ObjectField,
		isObjectActionSystem?: boolean
	) => boolean,
	setCurrentObjectDefinitionFields: (values: ObjectField[]) => void,
	setValues: (values: Partial<ObjectAction>) => void
) {
	let definitionId = objectDefinitionId;
	let externalReferenceCode = objectDefinitionExternalReferenceCode;
	let isSystemObject = systemObject;
	const validFields: ObjectField[] = [];

	if (values.objectActionExecutorKey === 'add-object-entry') {
		definitionId = values?.parameters?.objectDefinitionId as number;
		externalReferenceCode = values.parameters
			?.objectDefinitionExternalReferenceCode as string;
		isSystemObject = !!values?.parameters?.system;
	}

	if (externalReferenceCode) {
		const items = await API.getObjectFieldsByExternalReferenceCode(
			externalReferenceCode
		);

		items.forEach((field) => {
			if (isValidField(field, isSystemObject)) {
				validFields.push(field);
			}
		});
	}

	setCurrentObjectDefinitionFields(validFields);

	const {predefinedValues = []} = values.parameters as ObjectActionParameters;

	const predefinedValuesMap = new Map<string, PredefinedValue>();

	predefinedValues.forEach((field) => {
		predefinedValuesMap.set(field.name, field);
	});

	const newPredefinedValues: PredefinedValue[] = [];

	validFields.forEach(({label, name, required}) => {
		if (predefinedValuesMap.has(name)) {
			const field = predefinedValuesMap.get(name);

			newPredefinedValues.push(field as PredefinedValue);
		}
		else if (
			required &&
			values.objectActionExecutorKey === 'add-object-entry'
		) {
			newPredefinedValues.push({
				inputAsValue: false,
				label,
				name,
				value: '',
			});
		}
	});
	setValues({
		parameters: {
			...values.parameters,
			objectDefinitionExternalReferenceCode: externalReferenceCode,
			objectDefinitionId: definitionId,
			predefinedValues: newPredefinedValues,
		},
	});
}
