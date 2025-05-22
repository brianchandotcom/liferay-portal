/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectField} from '@liferay/object-admin-rest-client-js';

import {DataApiHelpers} from '../../../../helpers/ApiHelpers';
import {getRandomInt} from '../../../../utils/getRandomInt';
import {generateRandomObjectFieldObjectEntryValue} from './generateObjectEntryValue';
import {generateObjectFieldStructure} from './generateObjectFieldDTO';

interface MockObjectFieldsReturn {
	listTypeDefinition: ListTypeDefinition;
	listTypeDefinitionItems: string[];
	objectEntry: ObjectEntry;
	objectFields: Partial<ObjectField>[];
	titleObjectFieldName?: string;
	translatedListTypeDefinitionItems?: string[];
}

export type ObjectFieldBusinessTypesLabelName = {
	[K in ObjectFieldBusinessTypes]: LabelNameObject;
};

type ObjectEntry = {
	[K in Partial<ObjectFieldBusinessTypes>]: string;
};

export type ObjectFieldBusinessTypes =
	| 'Attachment'
	| 'AutoIncrement'
	| 'Boolean'
	| 'Date'
	| 'DateTime'
	| 'Decimal'
	| 'Encrypted'
	| 'Integer'
	| 'LongInteger'
	| 'LongText'
	| 'MultiselectPicklist'
	| 'Picklist'
	| 'PrecisionDecimal'
	| 'RichText'
	| 'Text';

export async function mockObjectFields({
	apiHelpers,
	localeToTranslateListTypeItems,
	objectEntryReturn,
	objectFieldBusinessTypes,
	titleObjectFieldName,
}: {
	apiHelpers: DataApiHelpers;
	localeToTranslateListTypeItems?: Locale;
	localizeAllLocalizable?: boolean;
	objectEntryReturn?: {format: 'API' | 'UI'};
	objectFieldBusinessTypes: ObjectFieldBusinessTypes[];
	titleObjectFieldName?: ObjectFieldBusinessTypes;
}): Promise<MockObjectFieldsReturn> {
	let translatedListTypeDefinitionItems: string[];
	let listTypeDefinition: ListTypeDefinition;
	let listTypeDefinitionItems: string[];

	if (
		objectFieldBusinessTypes.includes('Picklist') ||
		objectFieldBusinessTypes.includes('MultiselectPicklist')
	) {
		listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		const numberOfListTypeDefinitionItems = 4;

		listTypeDefinitionItems = new Array(numberOfListTypeDefinitionItems)
			.fill('')
			.map(() => getRandomInt().toString());

		if (localeToTranslateListTypeItems) {
			translatedListTypeDefinitionItems = listTypeDefinitionItems.map(
				() => getRandomInt().toString()
			);
		}

		for (let i = 0; i < numberOfListTypeDefinitionItems; i++) {
			await apiHelpers.listTypeAdmin.postListTypeEntry(
				listTypeDefinition.externalReferenceCode,
				listTypeDefinitionItems[i],
				translatedListTypeDefinitionItems
					? {
							[localeToTranslateListTypeItems]:
								translatedListTypeDefinitionItems[i],
						}
					: {}
			);
		}
	}

	let objectFieldBusinessTypesLabelName =
		{} as ObjectFieldBusinessTypesLabelName;

	function setLabelName(businessType: string, {label, name}) {
		objectFieldBusinessTypesLabelName = {
			...objectFieldBusinessTypesLabelName,
			[businessType]: [
				...(objectFieldBusinessTypesLabelName[businessType] || []),
				{label, name},
			],
		};
	}

	for (const objectFieldBusinessType of objectFieldBusinessTypes) {
		setLabelName(objectFieldBusinessType, {
			label: `label${objectFieldBusinessType}${getRandomInt()}`,
			name: `name${objectFieldBusinessType}${getRandomInt()}`,
		});
	}

	const objectEntry = {} as ObjectEntry;

	let objectFields: Partial<ObjectField>[] = [];

	for (const objectFieldBusinessType in objectFieldBusinessTypesLabelName) {
		const objectField = generateObjectFieldStructure(
			objectFieldBusinessType as ObjectFieldBusinessTypes,
			objectFieldBusinessTypesLabelName[objectFieldBusinessType].label,
			objectFieldBusinessTypesLabelName[objectFieldBusinessType].name
		);

		objectFields = objectFields.concat([objectField]);

		if (
			objectFieldBusinessType !== 'attachment' &&
			objectFieldBusinessType !== 'autoIncrement' &&
			objectEntryReturn
		) {
			for (const field of objectFieldBusinessTypesLabelName[
				objectFieldBusinessType
			]) {
				objectEntry[field.name] =
					generateRandomObjectFieldObjectEntryValue(
						objectEntryReturn.format,
						listTypeDefinitionItems,
						objectFieldBusinessType as ObjectFieldBusinessTypes
					);
			}
		}
	}

	return {
		listTypeDefinition,
		listTypeDefinitionItems,
		objectEntry: objectEntryReturn ? objectEntry : undefined,
		objectFields,
		titleObjectFieldName: titleObjectFieldName
			? objectFieldBusinessTypesLabelName[titleObjectFieldName][0].name
			: undefined,
		translatedListTypeDefinitionItems,
	};
}
