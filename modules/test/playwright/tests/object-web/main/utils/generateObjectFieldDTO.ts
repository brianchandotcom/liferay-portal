/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectField} from '@liferay/object-admin-rest-client-js';

import {ObjectFieldBusinessTypes} from './mockObjectFields';

function getObjectFieldBaseProperties() {
	return {
		indexedAsKeyword: false,
		indexedLanguageId: '',
		localized: false,
		readOnly: 'false' as ObjectField['readOnly'],
		readOnlyConditionExpression: '',
		required: false,
		state: false,
		system: false,
		unique: false,
	};
}

function getObjectFieldSpecificProperties(
	businessType: ObjectFieldBusinessTypes
): {
	['DBType']: ObjectField['DBType'];
	['businessType']: ObjectField['businessType'];
	['listTypeDefinitionExternalReferenceCode']?: ObjectField['listTypeDefinitionExternalReferenceCode'];
	['objectFieldSettings']?: any;
	['type']: ObjectField['type'];
} {
	switch (businessType) {
		case 'Attachment':
			return {
				DBType: 'Long',
				businessType: 'Attachment',
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
				type: 'Long',
			};
		case 'AutoIncrement':
			return {
				DBType: 'Long',
				businessType: 'Attachment',
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
				type: 'Long',
			};
		case 'Boolean':
			return {
				DBType: 'Boolean',
				businessType: 'Boolean',
				type: 'Boolean',
			};
		case 'Date':
			return {
				DBType: 'Date',
				businessType: 'Date',
				type: 'Date',
			};
		case 'DateTime':
			return {
				DBType: 'DateTime',
				businessType: 'DateTime',
				objectFieldSettings: [
					{
						name: 'timeStorage',
						value: 'convertToUTC',
					},
				],
				type: 'DateTime',
			};
		case 'Decimal':
			return {
				DBType: 'Double',
				businessType: 'Decimal',
				type: 'Double',
			};
		case 'Encrypted':
			return {
				DBType: 'Clob',
				businessType: 'Encrypted',
				type: 'Clob',
			};
		case 'Integer':
			return {
				DBType: 'Integer',
				businessType: 'Integer',
				type: 'Integer',
			};
		case 'LongInteger':
			return {
				DBType: 'Long',
				businessType: 'LongInteger',
				type: 'Long',
			};
		case 'LongText':
			return {
				DBType: 'Clob',
				businessType: 'LongText',
				objectFieldSettings: [
					{
						name: 'showCounter',
						value: false,
					} as any,
				],
				type: 'Clob',
			};
		case 'MultiselectPicklist':
			return {
				DBType: 'String',
				businessType: 'MultiselectPicklist',
				listTypeDefinitionExternalReferenceCode: '',
				type: 'String',
			};
		case 'Picklist':
			return {
				DBType: 'String',
				businessType: 'Picklist',
				listTypeDefinitionExternalReferenceCode: '',
				type: 'String',
			};
		case 'PrecisionDecimal':
			return {
				DBType: 'BigDecimal',
				businessType: 'PrecisionDecimal',
				type: 'BigDecimal',
			};
		case 'RichText':
			return {
				DBType: 'Clob',
				businessType: 'RichText',
				type: 'Clob',
			};
		case 'Text':
			return {
				DBType: 'String',
				businessType: 'Text',
				type: 'String',
			};
	}
}

export function generateObjectFieldStructure(
	businessType: ObjectFieldBusinessTypes,
	label: string,
	name: string
): Partial<ObjectField> {
	const objectFieldBaseProperties = getObjectFieldBaseProperties();
	const objectFieldSpecificProperties =
		getObjectFieldSpecificProperties(businessType);

	return {
		...objectFieldBaseProperties,
		...objectFieldSpecificProperties,
		label: {en_US: label},
		name,
	};
}
