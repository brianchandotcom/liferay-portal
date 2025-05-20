/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectField} from '@liferay/object-admin-rest-client-js';

import {DataApiHelpers} from '../../../../helpers/ApiHelpers';
import {getRandomInt} from '../../../../utils/getRandomInt';
import getRandomString from '../../../../utils/getRandomString';
import {
	getObjectEntryAPIDateFormat,
	getObjectEntryUIDateFormat,
} from './dateFormat';

interface MockObjectFieldsReturn {
	listTypeDefinition: ListTypeDefinition;
	listTypeDefinitionItems: string[];
	objectEntry: ObjectEntry;
	objectFields: Partial<ObjectField>[];
	titleObjectFieldName?: string;
	translatedListTypeDefinitionItems?: string[];
}

type ObjectFieldBusinessTypesLabelName = {
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

const objectFieldbusinessTypeInfo: {
	[K in ObjectFieldBusinessTypes]: {
		['DBType']: ObjectField['DBType'];
		['businessType']: ObjectField['businessType'];
		['type']: ObjectField['type'];
	};
} = {
	Attachment: {
		DBType: 'Long',
		businessType: 'Attachment',
		type: 'Long',
	},
	AutoIncrement: {
		DBType: 'String',
		businessType: 'AutoIncrement',
		type: 'String',
	},
	Boolean: {
		DBType: 'Boolean',
		businessType: 'Boolean',
		type: 'Boolean',
	},
	Date: {
		DBType: 'Date',
		businessType: 'Date',
		type: 'Date',
	},
	DateTime: {
		DBType: 'DateTime',
		businessType: 'DateTime',
		type: 'DateTime',
	},
	Decimal: {
		DBType: 'Double',
		businessType: 'Decimal',
		type: 'Double',
	},
	Encrypted: {
		DBType: 'Clob',
		businessType: 'Encrypted',
		type: 'Clob',
	},
	Integer: {
		DBType: 'Integer',
		businessType: 'Integer',
		type: 'Integer',
	},
	LongInteger: {
		DBType: 'Long',
		businessType: 'LongInteger',
		type: 'Long',
	},
	LongText: {
		DBType: 'Clob',
		businessType: 'LongText',
		type: 'Clob',
	},
	MultiselectPicklist: {
		DBType: 'String',
		businessType: 'MultiselectPicklist',
		type: 'String',
	},
	Picklist: {
		DBType: 'String',
		businessType: 'Picklist',
		type: 'String',
	},
	PrecisionDecimal: {
		DBType: 'BigDecimal',
		businessType: 'PrecisionDecimal',
		type: 'BigDecimal',
	},
	RichText: {
		DBType: 'Clob',
		businessType: 'RichText',
		type: 'Clob',
	},
	Text: {
		DBType: 'String',
		businessType: 'Text',
		type: 'String',
	},
};

function isLocalizable(businessType: ObjectFieldBusinessTypes) {
	const localizableBusinessTypes: ObjectFieldBusinessTypes[] = [
		'Attachment',
		'Boolean',
		'Date',
		'DateTime',
		'Decimal',
		'Integer',
		'LongInteger',
		'MultiselectPicklist',
		'Picklist',
		'PrecisionDecimal',
		'Text',
	];

	return localizableBusinessTypes.includes(businessType);
}

export function createObjectFields(
	businessType: keyof ObjectFieldBusinessTypesLabelName,
	objectFieldsBusinessTypeLabelName: LabelNameObject[],
	additionalSettings: Partial<ObjectField> = {},
	localizeAllLocalizable: boolean = false
): Partial<ObjectField>[] {
	const baseObjectField: ObjectField = {
		indexedAsKeyword: false,
		indexedLanguageId: '',
		localized: !!(isLocalizable(businessType) && localizeAllLocalizable),
		readOnly: 'false',
		readOnlyConditionExpression: '',
		required: false,
		state: false,
		system: false,
		unique: false,
	};

	return objectFieldsBusinessTypeLabelName.map(({label, name}) => ({
		DBType: objectFieldbusinessTypeInfo[businessType].DBType,
		businessType: objectFieldbusinessTypeInfo[businessType].businessType,
		label: {
			en_US: label,
		},
		name,
		type: objectFieldbusinessTypeInfo[businessType].type,
		...baseObjectField,
		...additionalSettings,
	}));
}

function getRandomFormatDate(format: 'API' | 'UI'): string {
	const currentDate = new Date();

	const randomDate = new Date(currentDate.getTime() * Math.random());

	if (format === 'API') {
		return getObjectEntryAPIDateFormat(randomDate);
	}
	else {
		return getObjectEntryUIDateFormat(randomDate);
	}
}

export function getRandomObjectFieldObjectEntryValue(
	format: 'API' | 'UI',
	listTypeDefinitionItems: string[],
	objectFieldBusinessType: ObjectFieldBusinessTypes
) {
	const listTypeDefinitionItemsRandomLength1 = Math.floor(
		Math.random() * listTypeDefinitionItems.length
	);
	const listTypeDefinitionItemsRandomLength2 = Math.floor(
		Math.random() * listTypeDefinitionItems.length
	);

	switch (objectFieldBusinessType) {
		case 'Boolean':
			return Math.random() < 0.5;
		case 'Date':
			return getRandomFormatDate(format);
		case 'Decimal':
			return parseFloat(Math.random().toFixed(10)).toString();
		case 'Encrypted':
			return getRandomString();
		case 'Integer':
			return Math.floor(Math.random() * 100).toString();
		case 'LongInteger':
			return getRandomInt().toString();
		case 'LongText':
			return getRandomString();
		case 'MultiselectPicklist':
			return [
				listTypeDefinitionItems[listTypeDefinitionItemsRandomLength1],
				listTypeDefinitionItems[listTypeDefinitionItemsRandomLength2],
			];
		case 'Picklist':
			return {
				key: listTypeDefinitionItems[
					listTypeDefinitionItemsRandomLength1
				],
			};
		case 'PrecisionDecimal':
			return parseFloat(Math.random().toFixed(15)).toString();
		case 'RichText':
			return getRandomString().substring(0, 35);
		case 'Text':
			return getRandomString();
		default:
			return '';
	}
}

export async function mockObjectFields({
	apiHelpers,
	localeToTranslateListTypeItems,
	localizeAllLocalizable,
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

	function setObjectFieldsAdditionalSettings(
		objectFieldBusinessType: ObjectFieldBusinessTypes
	): Partial<ObjectField> | undefined {
		switch (objectFieldBusinessType) {
			case 'Attachment':
				return {
					objectFieldSettings: [
						{
							name: 'acceptedFileExtensions',
							value: 'jpeg, jpg, pdf, png',
						} as any,
						{
							name: 'fileSource',
							value: 'documentsAndMedia',
						} as any,
						{
							name: 'maximumFileSize',
							value: '100',
						} as any,
					],
				};
			case 'AutoIncrement':
				return {
					objectFieldSettings: [
						{
							name: 'initialValue',
							value: '1',
						} as any,
					],
				};
			case 'DateTime':
				return {
					objectFieldSettings: [
						{
							name: 'timeStorage',
							value: 'convertToUTC',
						} as any,
					],
				};
			case 'LongText':
				return {
					objectFieldSettings: [
						{
							name: 'showCounter',
							value: false,
						} as any,
					],
				};
			case 'MultiselectPicklist':
				return {
					listTypeDefinitionExternalReferenceCode:
						listTypeDefinition.externalReferenceCode,
					listTypeDefinitionId: listTypeDefinition.id,
				};
			case 'Picklist':
				return {
					listTypeDefinitionExternalReferenceCode:
						listTypeDefinition.externalReferenceCode,
				};
			default:
				return undefined;
		}
	}

	const objectEntry = {} as ObjectEntry;

	let objectFields: Partial<ObjectField>[] = [];

	for (const objectFieldBusinessType in objectFieldBusinessTypesLabelName) {
		objectFields = objectFields.concat(
			createObjectFields(
				objectFieldBusinessType as ObjectFieldBusinessTypes,
				objectFieldBusinessTypesLabelName[objectFieldBusinessType],
				setObjectFieldsAdditionalSettings(
					objectFieldBusinessType as ObjectFieldBusinessTypes
				),
				localizeAllLocalizable
			)
		);

		if (
			objectFieldBusinessType !== 'attachment' &&
			objectFieldBusinessType !== 'autoIncrement' &&
			objectEntryReturn
		) {
			for (const field of objectFieldBusinessTypesLabelName[
				objectFieldBusinessType
			]) {
				objectEntry[field.name] = getRandomObjectFieldObjectEntryValue(
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
