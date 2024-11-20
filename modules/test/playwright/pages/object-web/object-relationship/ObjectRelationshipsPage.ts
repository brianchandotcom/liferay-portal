/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ViewObjectDefinitionsPage} from '../ViewObjectDefinitionsPage';

export class ObjectRelationshipsPage {
	readonly actionsButton: Locator;
	readonly cancelButton: Locator;
	readonly deleteObjectRelationshipOption: Locator;
	readonly inheritanceModalHeader: Locator;
	readonly inheritanceModalDisableButton: Locator;
	readonly inheritanceCheckbox: Locator;
	readonly inheritanceWarningMessage: Locator;
	readonly relationshipTabItem: Locator;
	readonly saveObjectRelationshipButton: Locator;
	readonly viewObjectDefinitionsPage: ViewObjectDefinitionsPage;

	constructor(page: Page) {
		this.actionsButton = page.getByRole('button', {name: 'Actions'});
		this.cancelButton = page.frameLocator('iframe').getByText('Cancel');
		this.deleteObjectRelationshipOption = page.getByRole('menuitem', {
			name: 'Delete',
		});
		this.inheritanceModalHeader = page.getByRole('heading', {
			name: 'Disable Inheritance Confirmation',
		});
		this.inheritanceModalDisableButton = page.getByRole('button', {
			name: 'Disable',
		});
		this.inheritanceCheckbox = page
			.frameLocator('iframe')
			.getByRole('checkbox');
		this.inheritanceWarningMessage = page
			.frameLocator('iframe')
			.getByText(
				'Warning:Unable to bind the object definitions when the child object definition is bound to another object definition'
			);
		this.relationshipTabItem = page.getByRole('link', {
			name: 'Relationships',
		});
		this.saveObjectRelationshipButton = page
			.frameLocator('iframe')
			.getByRole('button', {name: 'Save'});
		this.viewObjectDefinitionsPage = new ViewObjectDefinitionsPage(page);
	}

	async goto(objectDefinitionLabel: string) {
		await this.viewObjectDefinitionsPage.goto();

		await this.viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			objectDefinitionLabel
		);

		await this.relationshipTabItem.click();
	}
}
