/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {ObjectDefinitionsPage} from './objectDefinitions.page';

export class ModelBuilderPage {
	readonly fitViewButton: Locator;
	readonly newObjectRelationshipLabel: Locator;
	readonly newObjectRelationshipTitle: Locator;
	readonly newObjectRelationshipType: Locator;
	readonly objectDefinitionsPage: ObjectDefinitionsPage;
	readonly objectDefinitionNodes: Locator;
	readonly objectRelationshipEdges: Locator;
	readonly page: Page;
	readonly saveNewObjectRelationshipButton: Locator;
	readonly toggleSidebarsButton: Locator;

	constructor(page: Page) {
		this.fitViewButton = page.locator(
			'button.react-flow__controls-button.react-flow__controls-fitview'
		);
		this.newObjectRelationshipLabel = page
			.locator('div.form-group')
			.filter({hasText: /^LabelMandatory$/})
			.getByRole('textbox');
		this.newObjectRelationshipTitle = page.getByRole('heading', {
			name: 'New Relationship',
		});
		this.newObjectRelationshipType = page.getByText('Many to Many');
		this.objectDefinitionsPage = new ObjectDefinitionsPage(page);
		this.objectDefinitionNodes = page.locator('.react-flow__node');
		this.objectRelationshipEdges = page.locator('.react-flow__edge');
		this.page = page;
		this.saveNewObjectRelationshipButton = page.getByRole('button', {
			name: 'Save',
		});
		this.toggleSidebarsButton = page.getByLabel('Toggle Sidebars');
	}

	getObjectDefinitionNodeRelationshipHandle(
		objectDefinitionExternalReferenceCode: string,
		position: string
	) {
		let dataHandled = 'fixedRightHandle';

		if (position === 'left') {
			dataHandled = 'fixedLeftHandle';
		}

		return this.page.locator(
			`div[data-handleid="${objectDefinitionExternalReferenceCode}_${position}"]:not([data-handleid="${dataHandled}"])`
		);
	}

	async clickFitViewButton() {
		this.fitViewButton.click({force: true});
	}

	async clickObjectDefinitionShowAllFieldsButton(
		objectDefinitionName: string
	) {
		await this.objectDefinitionNodes
			.filter({hasText: objectDefinitionName})
			.getByRole('button', {name: 'Show All Fields'})
			.click();
	}

	async clickToggleSidebarsButton() {
		this.toggleSidebarsButton.click();
	}

	async goto() {
		await this.objectDefinitionsPage.goto();
		await this.objectDefinitionsPage.viewInModelBuilder();
	}

	async createObjectRelationship(
		objectDefinitionId1: string,
		objectDefinitionId2: string,
		objectRelationshipLabel: string,
		type: string
	) {
		await this.getObjectDefinitionNodeRelationshipHandle(
			objectDefinitionId1,
			'right'
		).dragTo(
			this.getObjectDefinitionNodeRelationshipHandle(
				objectDefinitionId2,
				'left'
			)
		);

		await expect(this.newObjectRelationshipTitle).toBeVisible();

		await this.newObjectRelationshipLabel.fill(objectRelationshipLabel);
		await this.newObjectRelationshipType.click();
		await this.page.getByRole('option', {name: type}).click();
		const responsePromise = this.page.waitForResponse(
			'**/object-relationships'
		);
		await this.saveNewObjectRelationshipButton.click();
		const response = await responsePromise;

		return response.json();
	}
}
