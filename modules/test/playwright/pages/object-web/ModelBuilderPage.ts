/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {PORTLET_URLS} from '../../utils/portletUrls';
import {ViewObjectDefinitionsPage} from './ViewObjectDefinitionsPage';

export class ModelBuilderPage {
	readonly addObjectFieldButton: Locator;
	readonly createNewObjectDefinitionButton: Locator;
	readonly deleteButton: Locator;
	readonly deleteObjectDefinitionOption: Locator;
	readonly deleteObjectRelationshipButton: Locator;
	readonly deleteTrashButton: Locator;
	readonly diagramArea: Locator;
	readonly editInPageViewOption: Locator;
	readonly editObjectFolderDetailsButton: Locator;
	readonly fitViewButton: Locator;
	readonly goToFolderButton: Locator;
	readonly leftSidebarItems: Locator;
	readonly newObjectFieldSelectBusinessType: Locator;
	readonly newObjectFieldLabel: Locator;
	readonly newObjectFieldName: Locator;
	readonly newObjectFieldSaveButton: Locator;
	readonly newObjectFieldSelectPicklist: Locator;
	readonly modalDeleteObjectDefinitionConfirmationButton: Locator;
	readonly modalDeleteObjectDefinitionTextField: Locator;
	readonly modalDeleteObjectRelationshipConfirmationButton: Locator;
	readonly modalDeleteObjectRelationshipTextField: Locator;
	readonly newObjectRelationshipLabel: Locator;
	readonly newObjectRelationshipTitle: Locator;
	readonly newObjectRelationshipType: Locator;
	readonly newObjectRelationshipSaveButton: Locator;
	readonly objectDefinitionNodes: Locator;
	readonly objectRelationshipEdges: Locator;
	readonly openPageViewButton: Locator;
	readonly otherObjectFolders: Locator;
	readonly page: Page;
	readonly rightSidebar: Locator;
	readonly selectedObjectFolder: Locator;
	readonly toggleSidebarsButton: Locator;
	readonly viewObjectDefinitionsPage: ViewObjectDefinitionsPage;

	constructor(page: Page) {
		this.addObjectFieldButton = page.getByRole('menuitem', {
			exact: true,
			name: 'Add Field',
		});
		this.createNewObjectDefinitionButton =
			page.getByText('Create New Object');
		this.deleteButton = page.getByTitle('Delete');
		this.deleteObjectDefinitionOption = page.getByRole('menuitem', {
			name: 'Delete Object',
		});
		this.deleteObjectRelationshipButton = page.getByLabel(
			'Delete Relationship'
		);
		this.deleteTrashButton = page
			.getByRole('tabpanel')
			.getByTitle('Delete');
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
		this.goToFolderButton = page.getByRole('button', {
			exact: true,
			name: 'Go to Folder',
		});
		this.leftSidebarItems = page.locator(
			'li.treeview-item div.autofit-row'
		);
		this.modalDeleteObjectDefinitionConfirmationButton = page
			.getByRole('dialog')
			.getByRole('button', {exact: true, name: 'Delete'});
		this.modalDeleteObjectDefinitionTextField = page.getByPlaceholder(
			'Confirm Object Definition Name'
		);
		this.modalDeleteObjectRelationshipConfirmationButton = page.getByRole(
			'button',
			{exact: true, name: 'Delete'}
		);
		this.modalDeleteObjectRelationshipTextField = page.getByPlaceholder(
			'Confirm Relationship Name'
		);
		this.newObjectFieldSelectBusinessType = page
			.locator('div.form-group')
			.filter({hasText: /^TypeMandatorySelect an Option$/})
			.getByRole('combobox');
		this.newObjectFieldLabel = page
			.locator('div.form-group')
			.filter({hasText: /^LabelMandatory$/})
			.getByRole('textbox');
		this.newObjectFieldSaveButton = page
			.getByLabel('New Field')
			.getByRole('button', {
				name: 'Save',
			});
		this.newObjectFieldSelectPicklist = page
			.locator('div.form-group')
			.filter({hasText: /^PicklistSelect an Option$/})
			.getByRole('combobox');
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
		this.otherObjectFolders = page
			.getByRole('region')
			.filter({has: page.getByTitle('Go to Folder')});
		this.page = page;
		this.rightSidebar = page
			.getByRole('tabpanel')
			.filter({hasNot: this.createNewObjectDefinitionButton});
		this.selectedObjectFolder = page
			.getByRole('tabpanel')
			.getByRole('treeitem')
			.filter({hasNot: page.getByTitle('Go to Folder')})
			.first();
		this.toggleSidebarsButton = page.getByLabel('Toggle Sidebars');
	}

	async clickHideFieldsButton(objectDefinitionName: string) {
		await this.objectDefinitionNodes
			.filter({hasText: objectDefinitionName})
			.getByRole('button', {name: 'Hide Fields'})
			.click();
	}

	async clickLeftSideBarItem(objectDefinitionLabel: string) {
		await this.leftSidebarItems
			.filter({hasText: objectDefinitionLabel})
			.click();
	}

	async clickObjectDefinitionActionsButton(objectDefinitionLabel: string) {
		await this.objectDefinitionNodes
			.filter({hasText: objectDefinitionLabel})
			.getByLabel('Show Actions')
			.click();
	}

	async clickObjectDefinitionActionsButtonInLeftSidebar(
		objectDefinitionLabel: string
	) {
		await this.leftSidebarItems
			.filter({hasText: objectDefinitionLabel})
			.getByLabel('Actions')
			.click();
	}

	async clickObjectRelationshipEdge(objectRelationshipLabel: string) {
		await this.objectRelationshipEdges
			.filter({hasText: objectRelationshipLabel})
			.click();
	}

	async clickShowAllFieldsButton(objectDefinitionName: string) {
		await this.objectDefinitionNodes
			.filter({hasText: objectDefinitionName})
			.getByRole('button', {name: 'Show All Fields'})
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

	async createObjectField({
		listTypeDefinitionName,
		mandatory,
		objectDefinitionName,
		objectFieldBusinessType,
		objectFieldLabel,
	}: CreateObjectField) {
		await this.openNewFieldModal(objectDefinitionName);

		await this.fillNewObjectFieldLabel(objectFieldLabel);

		await this.selectNewObjectFieldBusinessTypeOption(
			objectFieldBusinessType
		);

		if (objectFieldBusinessType === 'Picklist') {
			await this.newObjectFieldSelectPicklist.click();
			await this.page
				.getByRole('option', {
					exact: true,
					name: listTypeDefinitionName,
				})
				.click();
		}

		if (mandatory) {
			await this.page.getByLabel('Mandatory', {exact: true}).check();
		}

		await this.newObjectFieldSaveButton.click();
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

	async deleteObjectDefinition(objectDefinitionName: string) {
		await this.deleteObjectDefinitionOption.click();
		await this.modalDeleteObjectDefinitionTextField.click();
		await this.modalDeleteObjectDefinitionTextField.fill(
			objectDefinitionName
		);
		await this.modalDeleteObjectDefinitionConfirmationButton.click();
	}

	async deleteObjectRelationship(objectRelationshipName: string) {
		await this.deleteObjectRelationshipButton.click();
		await this.modalDeleteObjectRelationshipTextField.click();
		await this.modalDeleteObjectRelationshipTextField.fill(
			objectRelationshipName
		);
		await this.modalDeleteObjectRelationshipConfirmationButton.click();
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

	async fillNewObjectFieldLabel(objectFieldLabel: string) {
		await this.newObjectFieldLabel.fill(objectFieldLabel);
	}

	async openNewFieldModal(objectDefinitionName: string) {
		await this.leftSidebarItems
			.filter({hasText: objectDefinitionName})
			.click();

		await this.objectDefinitionNodes
			.filter({hasText: objectDefinitionName})
			.getByRole('button', {name: 'Add Field or Relationship'})
			.click();

		await this.addObjectFieldButton.click();
	}

	async selectNewObjectFieldBusinessTypeOption(
		objectFieldBusinessType: string
	) {
		await this.newObjectFieldSelectBusinessType.click();
		await this.page
			.getByRole('option', {exact: true, name: objectFieldBusinessType})
			.click();
	}

	getLinkedObjectDefinitionIconLocator = (objectDefinitionLabel: string) => {
		return this.objectDefinitionNodes
			.filter({
				hasText: objectDefinitionLabel,
			})
			.locator('svg.lexicon-icon-link');
	};

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

	getOtherObjectFolderLocator = (objectFolderLabel: string) => {
		return this.otherObjectFolders
			.getByRole('treeitem')
			.filter({hasText: objectFolderLabel});
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
