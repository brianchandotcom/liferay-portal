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

	const objectEntryValues = {
		boolean: Math.random() < 0.5,
		date: getFormatDate(objectEntryReturn.format),
		decimal: parseFloat(Math.random().toFixed(10)).toString(),
		encrypted: getRandomString(),
		integer: Math.floor(Math.random() * 100).toString(),
		longInteger: getRandomInt().toString(),
		longText: getRandomString(),
		multiselectPicklist: [
			listTypeDefinitionItems[0],
			listTypeDefinitionItems[1],
		],
		picklist: {key: listTypeDefinitionItems[0]},
		precisionDecimal: parseFloat(Math.random().toFixed(15)).toString(),
		richText: getRandomString(),
		text: getRandomString(),
	};

	const objectFields: Partial<ObjectField>[] = [];
	const objectFieldsAdditionalSettings = {
		attachment: {
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
		},
		autoIncrement: {
			objectFieldSettings: [
				{
					name: 'initialValue',
					value: '1',
				},
			],
		},
		longText: {
			objectFieldSettings: [
				{
					name: 'showCounter',
					value: false,
				},
			],
		},
		multiselectPicklist: {
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			listTypeDefinitionId: listTypeDefinition.id,
		},
		picklist: {
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
		},
	};

	for (const objectFieldBusinessType in objectFieldBusinessTypesLabelName) {
		objectFields.push(
			createObjectField(
				objectFieldBusinessType as ObjectFieldBusinessTypes,
				objectFieldBusinessTypesLabelName[objectFieldBusinessType],
				objectFieldsAdditionalSettings[objectFieldBusinessType] ??
					undefined
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
					.name]: objectEntryValues[objectFieldBusinessType],
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
