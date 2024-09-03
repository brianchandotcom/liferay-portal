/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {PORTLET_URLS} from '../../../utils/portletUrls';
import {ViewObjectDefinitionsPage} from '../ViewObjectDefinitionsPage';
import {ModelBuilderLeftSidebarPage} from './ModelBuilderLeftSidebarPage';

export class ModelBuilderPage {
	readonly deletionNotAllowed: Locator;
	readonly diagramArea: Locator;
	readonly editInPageViewOption: Locator;
	readonly editObjectFolderDetailsButton: Locator;
	readonly fitViewButton: Locator;
	readonly modelBuilderLeftSidebarPage: ModelBuilderLeftSidebarPage;
	readonly newObjectFieldName: Locator;
	readonly newObjectRelationshipLabel: Locator;
	readonly newObjectRelationshipTitle: Locator;
	readonly newObjectRelationshipType: Locator;
	readonly newObjectRelationshipSaveButton: Locator;
	readonly objectDefinitionNodes: Locator;
	readonly objectRelationshipEdges: Locator;
	readonly openPageViewButton: Locator;
	readonly page: Page;
	readonly postalAddressObjectRelationshipWarning: Locator;
	readonly toggleSidebarsButton: Locator;
	readonly viewObjectDefinitionsPage: ViewObjectDefinitionsPage;

	constructor(page: Page) {
		this.deletionNotAllowed = page.getByRole('heading', {
			name: 'Deletion Not Allowed',
		});
		this.diagramArea = page.locator('.react-flow');
		this.editInPageViewOption = page.getByRole('menuitem', {
			name: 'Edit in page view',
		});
		this.editObjectFolderDetailsButton = page.locator(
			'button[name=editObjectFolderButton]'
		);
		this.fitViewButton = page.locator(
			'button.react-flow__controls-button.react-flow__controls-fitview'
		);
		this.modelBuilderLeftSidebarPage = new ModelBuilderLeftSidebarPage(
			page
		);
		this.newObjectRelationshipLabel = page
			.locator('div.form-group')
			.filter({hasText: /^LabelMandatory$/})
			.getByRole('textbox');
		this.newObjectRelationshipTitle = page.getByRole('heading', {
			name: 'New Relationship',
		});
		this.newObjectRelationshipType = page.getByText('Many to Many');
		this.newObjectRelationshipSaveButton = page
			.getByLabel('New Relationship')
			.getByRole('button', {
				name: 'Save',
			});
		this.viewObjectDefinitionsPage = new ViewObjectDefinitionsPage(page);
		this.objectDefinitionNodes = page.locator('.react-flow__node');
		this.objectRelationshipEdges = page.locator('.react-flow__edge');
		this.openPageViewButton = page.getByRole('button', {
			name: 'Open Page View',
		});
		this.page = page;
		this.postalAddressObjectRelationshipWarning = page.locator(
			'.alert-warning',
			{
				hasText:
					'Postal Address can only have a relationship with the Account object.',
			}
		);
		this.toggleSidebarsButton = page.getByLabel('Toggle Sidebars');
	}

	async clickObjectRelationshipEdge(objectRelationshipLabel: string) {
		await this.objectRelationshipEdges
			.filter({hasText: objectRelationshipLabel})
			.click();
	}

	async connectObjectDefinitionsNodeHandles(
		objectDefinitionId1: number,
		objectDefinitionId2: number
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
	}

	async createObjectRelationship(
		objectRelationshipLabel: string,
		type: string
	) {
		await expect(this.newObjectRelationshipTitle).toBeVisible();

		await this.newObjectRelationshipLabel.fill(objectRelationshipLabel);
		await this.newObjectRelationshipType.click();
		await this.page.getByRole('option', {name: type}).click();
		const responsePromise = this.page.waitForResponse(
			'**/object-relationships'
		);
		await this.newObjectRelationshipSaveButton.click();
		const response = await responsePromise;

		return response.json();
	}

	async dragNodeThroughDiagram(
		objectDefinitionLabel: string,
		targetX: number,
		targetY: number
	) {
		await this.objectDefinitionNodes
			.getByText(objectDefinitionLabel, {exact: true})
			.dragTo(this.diagramArea, {
				targetPosition: {x: targetX, y: targetY},
			});
	}

	getObjectDefinitionNodeRelationshipHandle(
		objectDefinitionId: number,
		position: string
	) {
		let dataHandled = 'fixedRightHandle';

		if (position === 'left') {
			dataHandled = 'fixedLeftHandle';
		}

		return this.page.locator(
			`div[data-handleid="${objectDefinitionId}_${position}"]:not([data-handleid="${dataHandled}"])`
		);
	}

	getObjectFolderERCHeaderLocator(objectFolderERC: string) {
		return this.page.getByTitle(`ERC: ${objectFolderERC}`);
	}

	getObjectFolderLabelHeaderLocator = (objectFolderLabel: string) => {
		return this.page.getByTitle(
			`Object Folder Label: ${objectFolderLabel}`
		);
	};

	async goto({
		objectFolderName,
		siteUrl,
	}: {
		objectFolderName: string;
		siteUrl?: Site['friendlyUrlPath'];
	}) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${
				PORTLET_URLS.modelBuilder
			}&objectFolderName=${objectFolderName}`,
			{waitUntil: 'load'}
		);
	}
}
