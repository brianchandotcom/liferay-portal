/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {Locator, Page} from '@playwright/test';

export class ModelBuilderRightSidebarPage {
	readonly deleteButton: Locator;
	readonly deleteObjectRelationshipButton: Locator;
	readonly deleteTrashButton: Locator;
	readonly managePicklistsButton: Locator;
	readonly modalDeleteObjectRelationshipTextField: Locator;
	readonly objectDefinitionActivateObject: Locator;
	readonly objectDefinitionEntryTitleField: Locator;
	readonly objectDefinitionLabelLocalizationButton: Locator;
	readonly objectDefinitionPanelLink: Locator;
	readonly objectDefinitionPluralLabel: Locator;
	readonly objectDefinitionPluralLabelLocalizationButton: Locator;
	readonly objectDefinitionScope: Locator;
	readonly objectRelationshipDeletionType: Locator;
	readonly objectRelationshipManyRecordsOf: Locator;
	readonly objectRelationshipOneRecordOf: Locator;
	readonly objectRelationshipType: Locator;
	readonly page: Page;
	readonly sidebarLabelInput: Locator;
	readonly sidebarName: Locator;

	constructor(page: Page) {
		this.deleteButton = page.getByRole('button', {
			exact: true,
			name: 'Delete',
		});
		this.deleteObjectRelationshipButton = page.getByLabel(
			'Delete Relationship'
		);
		this.deleteTrashButton = page
			.getByRole('tabpanel')
			.getByTitle('Delete');
		this.managePicklistsButton = page.getByRole('button', {
			name: 'Manage Picklists',
		});
		this.modalDeleteObjectRelationshipTextField = page.getByPlaceholder(
			'Confirm Relationship Name'
		);
		this.objectDefinitionActivateObject =
			page.getByLabel('Activate Object');
		this.objectDefinitionEntryTitleField =
			page.getByLabel('Entry Title Field');
		this.sidebarLabelInput = page.getByLabel('Label' + 'Mandatory', {
			exact: true,
		});
		this.sidebarName = page.getByLabel('Name' + 'Mandatory', {
			exact: true,
		});
		this.objectDefinitionLabelLocalizationButton = page
			.getByTitle('Open Localizations')
			.first();
		this.objectDefinitionPanelLink = page.getByLabel('Panel Link');
		this.objectDefinitionPluralLabel = page.getByLabel('Plural Label');
		this.objectDefinitionPluralLabelLocalizationButton = page
			.getByTitle('Open Localizations')
			.last();
		this.objectDefinitionScope = page.getByLabel('Scope');
		this.objectRelationshipDeletionType = page.getByLabel('Deletion Type');
		this.objectRelationshipManyRecordsOf =
			page.getByLabel('Many Records Of');
		this.objectRelationshipOneRecordOf = page.getByLabel('One Record Of');
		this.objectRelationshipType = page.getByLabel('Type');
		this.page = page;
	}

	async deleteObjectRelationship(objectRelationshipName: string) {
		await this.deleteObjectRelationshipButton.click();
		await this.modalDeleteObjectRelationshipTextField.click();
		await this.modalDeleteObjectRelationshipTextField.fill(
			objectRelationshipName
		);
		await this.deleteButton.click();
	}

	getRightSidebarLocator(createNewObjectDefinitionButton: Locator) {
		return this.page.getByRole('tabpanel').filter({
			hasNot: createNewObjectDefinitionButton,
		});
	}
}
