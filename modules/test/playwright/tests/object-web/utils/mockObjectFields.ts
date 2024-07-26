/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {
	getObjectEntryAPIDateFormat,
	getObjectEntryUIDateFormat,
} from './dateFormat';

interface MockObjectFieldsReturn {
	listTypeDefinition?: ListTypeDefinition;
	objectEntry?: ObjectEntry;
	objectFields: Partial<ObjectField>[];
	titleObjectFieldName?: string;
}

type ObjectFieldBusinessTypesLabelName = {
	[K in ObjectFieldBusinessTypes]: LabelNameObject;
};

type ObjectEntry = {
	[K in Partial<ObjectFieldBusinessTypes>]: string;
};

type ObjectFieldBusinessTypes =
	| 'attachment'
	| 'autoIncrement'
	| 'boolean'
	| 'date'
	| 'decimal'
	| 'encrypted'
	| 'integer'
	| 'longInteger'
	| 'longText'
	| 'multiselectPicklist'
	| 'picklist'
	| 'precisionDecimal'
	| 'richText'
	| 'text';

const objectFieldbusinessTypeInfo: {
	[K in ObjectFieldBusinessTypes]: {
		['DBType']: string;
		['businessType']: ObjectFieldBusinessTypeName;
	};
} = {
	attachment: {
		DBType: 'Long',
		businessType: 'Attachment',
	},
	autoIncrement: {
		DBType: 'String',
		businessType: 'AutoIncrement',
	},
	boolean: {
		DBType: 'Boolean',
		businessType: 'Boolean',
	},
	date: {
		DBType: 'Date',
		businessType: 'Date',
	},
	decimal: {
		DBType: 'Double',
		businessType: 'Decimal',
	},
	encrypted: {
		DBType: 'Clob',
		businessType: 'Encrypted',
	},
	integer: {
		DBType: 'Integer',
		businessType: 'Integer',
	},
	longInteger: {
		DBType: 'Long',
		businessType: 'LongInteger',
	},
	longText: {
		DBType: 'Clob',
		businessType: 'LongText',
	},
	multiselectPicklist: {
		DBType: 'String',
		businessType: 'MultiselectPicklist',
	},
	picklist: {
		DBType: 'String',
		businessType: 'Picklist',
	},
	precisionDecimal: {
		DBType: 'BigDecimal',
		businessType: 'PrecisionDecimal',
	},
	richText: {
		DBType: 'Clob',
		businessType: 'RichText',
	},
	text: {
		DBType: 'String',
		businessType: 'Text',
	},
};

export function createObjectField(
	businessType: keyof ObjectFieldBusinessTypesLabelName,
	objectFieldBusinessTypeLabelName: LabelNameObject,
	additionalSettings: Partial<ObjectField> = {}
): Partial<ObjectField> {
	const baseObjectField = {
		indexedAsKeyword: false,
		indexedLanguageId: '',
		localized: false,
		readOnly: 'false' as ReadOnlyFieldValue,
		readOnlyConditionExpression: '',
		required: false,
		state: false,
		system: false,
		unique: false,
	};

	return {
		DBType: objectFieldbusinessTypeInfo[businessType].DBType,
		businessType: objectFieldbusinessTypeInfo[businessType].businessType,
		label: {
			en_US: objectFieldBusinessTypeLabelName.label,
		},
		name: objectFieldBusinessTypeLabelName.name,
		type: objectFieldbusinessTypeInfo[businessType].DBType,
		...additionalSettings,
		...baseObjectField,
	};
}

function getFormatDate(format: 'API' | 'UI'): string {
	const date = new Date();

	if (format === 'API') {
		return getObjectEntryAPIDateFormat(date);
	}
	else {
		return getObjectEntryUIDateFormat(date);
	}
}

function getRandomObjectFieldEntryValue(
	format: 'API' | 'UI',
	listTypeDefinitionItems: string[],
	objectFieldBusinessType: ObjectFieldBusinessTypes
) {
	switch (objectFieldBusinessType) {
		case 'boolean':
			return Math.random() < 0.5;
		case 'date':
			return getFormatDate(format);
		case 'decimal':
			return parseFloat(Math.random().toFixed(10)).toString();
		case 'encrypted':
			return getRandomString();
		case 'integer':
			return Math.floor(Math.random() * 100).toString();
		case 'longInteger':
			return getRandomInt().toString();
		case 'longText':
			return getRandomString();
		case 'multiselectPicklist':
			return [listTypeDefinitionItems[0], listTypeDefinitionItems[1]];
		case 'picklist':
			return {key: listTypeDefinitionItems[0]};
		case 'precisionDecimal':
			return parseFloat(Math.random().toFixed(15)).toString();
		case 'richText':
			return getRandomString();
		case 'text':
			return getRandomString();
		default:
			return '';
	}
}

export async function mockObjectFields({
	apiHelpers,
	objectEntryReturn,
	objectFieldBusinessTypes,
	titleObjectFieldName,
}: {
	apiHelpers: ApiHelpers;
	objectEntryReturn?: {format: 'API' | 'UI'};
	objectFieldBusinessTypes: ObjectFieldBusinessTypes[];
	titleObjectFieldName?: ObjectFieldBusinessTypes;
}): Promise<MockObjectFieldsReturn> {
	let listTypeDefinition: ListTypeDefinition;
	let listTypeDefinitionItems: string[];

	if (
		objectFieldBusinessTypes.includes('picklist') ||
		objectFieldBusinessTypes.includes('multiselectPicklist')
	) {
		listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		listTypeDefinitionItems = new Array(3)
			.fill('')
			.map(() => getRandomInt().toString());

		for (const lisTypeEntry of listTypeDefinitionItems) {
			await apiHelpers.listTypeAdmin.postListTypeEntry(
				listTypeDefinition.externalReferenceCode,
				lisTypeEntry
			);
		}
	}

	let objectFieldBusinessTypesLabelName =
		{} as ObjectFieldBusinessTypesLabelName;

	function setLabelName(businessType: string, {label, name}) {
		objectFieldBusinessTypesLabelName = {
			...objectFieldBusinessTypesLabelName,
			[businessType]: {label, name},
		};
	}

	for (const objectFieldBusinessType of objectFieldBusinessTypes) {
		setLabelName(objectFieldBusinessType, {
			label: `${objectFieldBusinessType}${getRandomInt()}`,
			name: `${objectFieldBusinessType}${getRandomInt()}`,
		});
	}

	let objectEntry = {} as ObjectEntry;

	const objectFields: Partial<ObjectField>[] = [];
	function setObjectFieldsAdditionalSettings(
		objectFieldBusinessType: ObjectFieldBusinessTypes
	): Partial<ObjectField> | undefined {
		switch (objectFieldBusinessType) {
			case 'attachment':
				return {
					objectFieldSettings: [
						{
							name: 'acceptedFileExtensions',
							value: 'jpeg, jpg, pdf, png',
						},
						{
							name: 'fileSource',
							value: 'documentsAndMedia',
						},
						{
							name: 'maximumFileSize',
							value: '100',
						},
					],
				};
			case 'autoIncrement':
				return {
					objectFieldSettings: [
						{
							name: 'initialValue',
							value: '1',
						},
					],
				};
			case 'longText':
				return {
					objectFieldSettings: [
						{
							name: 'showCounter',
							value: false,
						},
					],
				};
			case 'multiselectPicklist':
				return {
					listTypeDefinitionExternalReferenceCode:
						listTypeDefinition.externalReferenceCode,
					listTypeDefinitionId: listTypeDefinition.id,
				};
			case 'picklist':
				return {
					listTypeDefinitionExternalReferenceCode:
						listTypeDefinition.externalReferenceCode,
				};
			default:
				return undefined;
		}
	}

	for (const objectFieldBusinessType in objectFieldBusinessTypesLabelName) {
		objectFields.push(
			createObjectField(
				objectFieldBusinessType as ObjectFieldBusinessTypes,
				objectFieldBusinessTypesLabelName[objectFieldBusinessType],
				setObjectFieldsAdditionalSettings(
					objectFieldBusinessType as ObjectFieldBusinessTypes
				)
			)
		);

		if (
			objectFieldBusinessType !== 'attachment' &&
			objectFieldBusinessType !== 'autoIncrement' &&
			objectEntryReturn
		) {
			objectEntry = {
				...objectEntry,
				[objectFieldBusinessTypesLabelName[objectFieldBusinessType]
					.name]: getRandomObjectFieldEntryValue(
					objectEntryReturn.format,
					listTypeDefinitionItems,
					objectFieldBusinessType as ObjectFieldBusinessTypes
				),
			};
		}
	}

	return {
		listTypeDefinition,
		objectEntry: objectEntryReturn ? objectEntry : undefined,
		objectFields,
		titleObjectFieldName: titleObjectFieldName
			? objectFieldBusinessTypesLabelName[titleObjectFieldName].name
			: undefined,
	};
}
