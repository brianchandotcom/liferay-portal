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

import ClayLabel from '@clayui/label';
import {
	API,
	AutoComplete,
	FormError,
	Input,
	REQUIRED_MSG,
	SingleSelect,
	filterArrayByQuery,
	getLocalizableLabel,
	invalidateRequired,
	useForm,
} from '@liferay/object-js-components-web';
import React, {useEffect, useMemo, useState} from 'react';

import {defaultLanguageId} from '../../utils/constants';

interface ObjectRelationshipFormBaseProps {
	errors: FormError<ObjectRelationship>;
	ffOneToOneRelationshipConfigurationEnabled?: boolean;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	readonly?: boolean;
	setValues: (values: Partial<ObjectRelationship>) => void;
	values: Partial<ObjectRelationship>;
}

interface UseObjectRelationshipFormProps {
	initialValues: Partial<ObjectRelationship>;
	onSubmit: (relationship: ObjectRelationship) => void;
	parameterRequired: boolean;
}

export enum ObjectRelationshipType {
	MANY_TO_MANY = 'manyToMany',
	ONE_TO_MANY = 'oneToMany',
	ONE_TO_ONE = 'oneToOne',
}

const MANY_TO_MANY = {
	description: Liferay.Language.get(
		"multiple-object's-entries-can-interact-with-many-others-object's-entries"
	),
	label: Liferay.Language.get('many-to-many'),
	value: ObjectRelationshipType.MANY_TO_MANY,
};
const ONE_TO_MANY = {
	description: Liferay.Language.get(
		"one-object's-entry-interacts-with-many-others-object's-entries"
	),
	label: Liferay.Language.get('one-to-many'),
	value: ObjectRelationshipType.ONE_TO_MANY,
};
const ONE_TO_ONE = {
	description: Liferay.Language.get(
		"one-object's-entry-interacts-only-with-one-other-object's-entry"
	),
	label: Liferay.Language.get('one-to-one'),
	value: ObjectRelationshipType.ONE_TO_ONE,
};

export function useObjectRelationshipForm({
	initialValues,
	onSubmit,
	parameterRequired,
}: UseObjectRelationshipFormProps) {
	const validate = (relationship: Partial<ObjectRelationship>) => {
		const errors: FormError<ObjectRelationship> = {};

		const label = relationship.label?.[defaultLanguageId];

		if (invalidateRequired(label)) {
			errors.label = REQUIRED_MSG;
		}

		if (invalidateRequired(relationship.name ?? label)) {
			errors.name = REQUIRED_MSG;
		}

		if (invalidateRequired(relationship.type)) {
			errors.type = REQUIRED_MSG;
		}

		if (!relationship.objectDefinitionId2) {
			errors.objectDefinitionId2 = REQUIRED_MSG;
		}

		if (
			parameterRequired &&
			relationship.type === ObjectRelationshipType.ONE_TO_MANY &&
			!relationship.parameterObjectFieldName
		) {
			errors.parameterObjectFieldName = REQUIRED_MSG;
		}

		return errors;
	};

	const {errors, handleChange, handleSubmit, setValues, values} = useForm({
		initialValues,
		onSubmit,
		validate,
	});

	return {errors, handleChange, handleSubmit, setValues, values};
}

export function ObjectRelationshipFormBase({
	errors,
	ffOneToOneRelationshipConfigurationEnabled,
	handleChange,
	readonly,
	setValues,
	values,
}: ObjectRelationshipFormBaseProps) {
	const [creationLanguageId, setCreationLanguageId] = useState<
		Liferay.Language.Locale
	>();

	const [objectDefinitions, setObjectDefinitions] = useState<
		Partial<ObjectDefinition>[]
	>([]);
	const [query, setQuery] = useState<string>('');

	const [types, selectedType] = useMemo(() => {
		const types = [ONE_TO_MANY, MANY_TO_MANY];
		if (ffOneToOneRelationshipConfigurationEnabled) {
			types.push(ONE_TO_ONE);
		}

		return [types, types.find(({value}) => value === values.type)?.label];
	}, [ffOneToOneRelationshipConfigurationEnabled, values.type]);

	const filteredRelationships = useMemo(() => {
		return filterArrayByQuery({
			array: objectDefinitions,
			creationLanguageId,
			query,
			str: 'label',
		});
	}, [creationLanguageId, objectDefinitions, query]);

	useEffect(() => {
		const fetchObjectDefinitions = async () => {
			const items = await API.getAllObjectDefinitions();

			const currentObjectDefinition = items.find(
				({externalReferenceCode}) =>
					values.objectDefinitionExternalReferenceCode1 ===
					externalReferenceCode
			)!;

			const objectDefinitions = items.filter(
				({modifiable, parameterRequired, storageType, system}) => {
					if (Liferay.FeatureFlags['LPS-167253']) {
						return (
							(currentObjectDefinition.modifiable ||
								modifiable) &&
							(!Liferay.FeatureFlags['LPS-135430'] ||
								storageType === 'default') &&
							!parameterRequired
						);
					}

					return (
						(!currentObjectDefinition.system || !system) &&
						(!Liferay.FeatureFlags['LPS-135430'] ||
							storageType === 'default') &&
						!parameterRequired
					);
				}
			);

			setCreationLanguageId(currentObjectDefinition.defaultLanguageId);

			setObjectDefinitions(objectDefinitions);
		};

		if (readonly) {
			setObjectDefinitions([
				{
					externalReferenceCode: values.objectDefinitionExternalReferenceCode2 as string,
					id: values.objectDefinitionId2 as number,
					label: values.label as LocalizedValue<string>,
					name: values.objectDefinitionName2 as string,
					system: false,
				},
			]);
		}
		else {
			fetchObjectDefinitions();
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [readonly, values.objectDefinitionExternalReferenceCode1]);

	return (
		<>
			<Input
				disabled={readonly}
				error={errors.name}
				label={Liferay.Language.get('name')}
				name="name"
				onChange={handleChange}
				required
				value={values.name}
			/>

			<SingleSelect
				disabled={readonly}
				error={errors.type}
				label={Liferay.Language.get('type')}
				onChange={({value}) => setValues({type: value})}
				options={types}
				required
				value={selectedType}
			/>

			<AutoComplete<Partial<ObjectDefinition>>
				disabled={readonly}
				emptyStateMessage={Liferay.Language.get(
					'no-objects-were-found'
				)}
				error={errors.objectDefinitionId2}
				items={filteredRelationships}
				label={Liferay.Language.get('object')}
				onActive={(item) => item.name === values.objectDefinitionName2}
				onChangeQuery={setQuery}
				onSelectItem={(item) => {
					setValues({
						objectDefinitionExternalReferenceCode2:
							item.externalReferenceCode,
						objectDefinitionId2: item.id,
						objectDefinitionName2: item.name,
					});
				}}
				query={query}
				required
				value={values.objectDefinitionName2}
			>
				{({label, name, system}) => (
					<div className="d-flex justify-content-between">
						<div>
							{getLocalizableLabel(
								creationLanguageId as Liferay.Language.Locale,
								label,
								name
							)}
						</div>

						<ClayLabel displayType={system ? 'info' : 'warning'}>
							{system
								? Liferay.Language.get('system')
								: Liferay.Language.get('custom')}
						</ClayLabel>
					</div>
				)}
			</AutoComplete>
		</>
	);
}
