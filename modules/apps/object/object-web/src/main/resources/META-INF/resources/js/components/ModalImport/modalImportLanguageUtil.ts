/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type ModalImportLanguage = {
	[key: string]: string;
};

export const modalImportContentFeedbackMessage: ModalImportLanguage = {
	listTypeDefinition: Liferay.Language.get(
		'unique-key-for-referencing-the-picklist'
	),
	objectDefinition: Liferay.Language.get(
		'unique-key-for-referencing-the-object-definition'
	),
	objectFolder: Liferay.Language.get(
		'unique-key-for-referencing-the-object-folder'
	),
};

export const modalImportContentTitle: ModalImportLanguage = {
	listTypeDefinition: Liferay.Language.get('import-picklist'),
	objectDefinition: Liferay.Language.get('import-object-definition'),
	objectFolder: Liferay.Language.get('import-object-folder'),
};

export const modalImportWarningBodyTexts: ModalImportLanguage[] = [
	{
		listTypeDefinition: Liferay.Language.get(
			'another-picklist-has-the-same-external-reference-code'
		),
		objectDefinition: Liferay.Language.get(
			'another-object-definition-has-the-same-external-reference-code'
		),
		objectDefinitions: Liferay.Language.get(
			'there-are-object-definitions-with-the-same-external-reference-code-as-the-imported-ones'
		),
		objectFolder: Liferay.Language.get(
			'another-object-folder-has-the-same-external-reference-code'
		),
	},
	{
		listTypeDefinition: Liferay.Language.get(
			'before-importing-the-new-picklist-you-may-want-to-back-up-its-entries-to-prevent-data-loss'
		),
		objectDefinition: Liferay.Language.get(
			'before-importing-the-new-object-definition-you-may-want-to-back-up-its-entries-to-prevent-data-loss'
		),
		objectFolder: Liferay.Language.get(
			'before-importing-the-new-object-folder-you-may-want-to-back-up-its-entries-to-prevent-data-loss'
		),
	},
];

export const modalImportWarningTitle: ModalImportLanguage = {
	listTypeDefinition: Liferay.Language.get('update-existing-picklist'),
	objectDefinition: Liferay.Language.get('update-existing-object-definition'),
	objectDefinitions: Liferay.Language.get(
		'update-existing-object-definitions'
	),
	objectFolder: Liferay.Language.get('update-existing-object-folder'),
};
