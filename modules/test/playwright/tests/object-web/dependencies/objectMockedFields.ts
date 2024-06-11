/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
export const mockedObjectFields: any = {
	attachmentFieldDocumentsAndMedia: {
		DBType: 'Long',
		businessType: 'Attachment',
		externalReferenceCode: 'testAttachment',
		indexed: true,
		indexedAsKeyword: false,
		indexedLanguageId: '',
		label: {
			en_US: 'testAttachment',
		},
		listTypeDefinitionId: 0,
		name: 'testAttachment',
		objectFieldSettings: [
			{
				name: 'acceptedFileExtensions',
				value: 'jpeg, jpg, pdf, png, txt',
			},
			{
				name: 'maximumFileSize',
				value: 1,
			},
			{
				name: 'fileSource',
				value: 'documentsAndMedia',
			},
		],
		required: false,
		state: false,
		system: false,
		type: 'Long',
		unique: false,
	},
	attachmentFieldUserComputer: {
		DBType: 'Long',
		businessType: 'Attachment',
		externalReferenceCode: 'testAttachment',
		indexed: true,
		indexedAsKeyword: false,
		indexedLanguageId: '',
		label: {
			en_US: 'testAttachment',
		},
		listTypeDefinitionId: 0,
		name: 'testAttachment',
		objectFieldSettings: [
			{
				name: 'acceptedFileExtensions',
				value: 'jpeg, jpg, pdf, png, txt',
			},
			{
				name: 'maximumFileSize',
				value: 100,
			},
			{
				name: 'fileSource',
				value: 'userComputer',
			},
			{
				name: 'showFilesInDocumentsAndMedia',
				value: false,
			},
		],
		required: false,
		state: false,
		system: false,
		type: 'Long',
		unique: false,
	},
};
