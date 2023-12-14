/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect} from '@playwright/test';

import {ObjectDefinitionsPage} from './objectDefinitions.page';

export class ModelBuilderPage {
	constructor(page) {
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
	}

	clickObjectDefinitionCardDot(
		objectDefinitionExternalReferenceCode,
		position
	) {
		let dataHandled = 'fixedRightHandle';

		if (position === 'left') {
			dataHandled = 'fixedLeftHandle';
		}

		return this.page.locator(
			`div[data-handleid="${objectDefinitionExternalReferenceCode}_${position}"]:not([data-handleid="${dataHandled}"])`
		);
	}

	async clickObjectDefinitionShowAllFieldsButton(objectDefinitionName) {
		await this.objectDefinitionNodes
			.filter({hasText: objectDefinitionName})
			.getByRole('button', {name: 'Show All Fields'})
			.click();
	}

	async goto() {
		await this.objectDefinitionsPage.goto();
		await this.objectDefinitionsPage.viewInModelBuilder();
	}

	async createObjectRelationship(
		objectDefinitionId1,
		objectDefinitionId2,
		objectRelationshipLabel,
		type
	) {
		await this.clickObjectDefinitionCardDot(
			objectDefinitionId1,
			'right'
		).dragTo(
			this.clickObjectDefinitionCardDot(objectDefinitionId2, 'left')
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
