/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectField} from '../../../../../apps/object/object-admin-rest-client-js';
import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {LabelNameObject, ListTypeDefinition} from '../../../types/object';
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
		['businessType']: ObjectField['businessType'];
		['dBType']: ObjectField['dBType'];
		['type']: ObjectField['type'];
	};
} = {
	attachment: {
		businessType: ObjectField.BusinessTypeEnum.Attachment,
		dBType: ObjectField.DBTypeEnum.Long,
		type: ObjectField.TypeEnum.Long,
	},
	autoIncrement: {
		businessType: ObjectField.BusinessTypeEnum.AutoIncrement,
		dBType: ObjectField.DBTypeEnum.String,
		type: ObjectField.TypeEnum.String,
	},
	boolean: {
		businessType: ObjectField.BusinessTypeEnum.Boolean,
		dBType: ObjectField.DBTypeEnum.Boolean,
		type: ObjectField.TypeEnum.Boolean,
	},
	date: {
		businessType: ObjectField.BusinessTypeEnum.Date,
		dBType: ObjectField.DBTypeEnum.Date,
		type: ObjectField.TypeEnum.Date,
	},
	decimal: {
		businessType: ObjectField.BusinessTypeEnum.Decimal,
		dBType: ObjectField.DBTypeEnum.Double,
		type: ObjectField.TypeEnum.Double,
	},
	encrypted: {
		businessType: ObjectField.BusinessTypeEnum.Encrypted,
		dBType: ObjectField.DBTypeEnum.Clob,
		type: ObjectField.TypeEnum.Clob,
	},
	integer: {
		businessType: ObjectField.BusinessTypeEnum.Integer,
		dBType: ObjectField.DBTypeEnum.Integer,
		type: ObjectField.TypeEnum.Integer,
	},
	longInteger: {
		businessType: ObjectField.BusinessTypeEnum.LongInteger,
		dBType: ObjectField.DBTypeEnum.Long,
		type: ObjectField.TypeEnum.Long,
	},
	longText: {
		businessType: ObjectField.BusinessTypeEnum.LongText,
		dBType: ObjectField.DBTypeEnum.Clob,
		type: ObjectField.TypeEnum.Clob,
	},
	multiselectPicklist: {
		businessType: ObjectField.BusinessTypeEnum.MultiselectPicklist,
		dBType: ObjectField.DBTypeEnum.String,
		type: ObjectField.TypeEnum.String,
	},
	picklist: {
		businessType: ObjectField.BusinessTypeEnum.Picklist,
		dBType: ObjectField.DBTypeEnum.String,
		type: ObjectField.TypeEnum.String,
	},
	precisionDecimal: {
		businessType: ObjectField.BusinessTypeEnum.PrecisionDecimal,
		dBType: ObjectField.DBTypeEnum.BigDecimal,
		type: ObjectField.TypeEnum.BigDecimal,
	},
	richText: {
		businessType: ObjectField.BusinessTypeEnum.RichText,
		dBType: ObjectField.DBTypeEnum.Clob,
		type: ObjectField.TypeEnum.Clob,
	},
	text: {
		businessType: ObjectField.BusinessTypeEnum.Text,
		dBType: ObjectField.DBTypeEnum.String,
		type: ObjectField.TypeEnum.String,
	},
};

export function createObjectField(
	businessType: keyof ObjectFieldBusinessTypesLabelName,
	objectFieldBusinessTypeLabelName: LabelNameObject,
	additionalSettings: Partial<ObjectField> = {}
): Partial<ObjectField> {
	const baseObjectField: ObjectField = {
		indexedAsKeyword: false,
		indexedLanguageId: '',
		localized: false,
		readOnly: ObjectField.ReadOnlyEnum.False,
		readOnlyConditionExpression: '',
		required: false,
		state: false,
		system: false,
		unique: false,
	};

	return {
		businessType: objectFieldbusinessTypeInfo[businessType].businessType,
		dBType: objectFieldbusinessTypeInfo[businessType].dBType,
		label: {
			en_US: objectFieldBusinessTypeLabelName.label,
		},
		name: objectFieldBusinessTypeLabelName.name,
		type: objectFieldbusinessTypeInfo[businessType].type,
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
			case 'autoIncrement':
				return {
					objectFieldSettings: [
						{
							name: 'initialValue',
							value: '1',
						} as any,
					],
				};
			case 'longText':
				return {
					objectFieldSettings: [
						{
							name: 'showCounter',
							value: false,
						} as any,
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
