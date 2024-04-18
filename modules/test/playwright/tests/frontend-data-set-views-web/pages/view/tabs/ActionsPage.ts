/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ICreationAction, IItemAction} from '../../../utils/types';
import {ViewPage} from '../ViewPage';

export class ActionsPage {
	readonly creationActionsTab: Locator;
	readonly itemActionsTab: Locator;
	readonly newActionButton: Locator;
	readonly newActionForm: {
		addIconButton: Locator;
		confirmationMessageInput: Locator;
		headlessPermissionKeyInput: Locator;
		methodSelect: Locator;
		nameInput: Locator;
		permissionKeyInput: Locator;
		saveButton: Locator;
		selectIconModal: {
			iconsList: Locator;
			searchInput: Locator;
		};
		titleInput: Locator;
		typeSelect: Locator;
		urlText: Locator;
		variantSelect: Locator;
	};
	readonly newCreationActionButton: Locator;
	readonly newItemActionButton: Locator;
	readonly noActionsWereCreatedMessage: Locator;
	readonly page: Page;
	readonly viewPage: ViewPage;

	constructor(page: Page) {
		this.creationActionsTab = page.getByRole('tab', {
			name: 'Creation Actions',
		});
		this.itemActionsTab = page.getByRole('tab', {name: 'Item Actions'});
		this.newActionButton = page.getByRole('button', {name: 'Add Action'});
		this.newActionForm = {
			addIconButton: page.getByLabel('add-icon'),
			confirmationMessageInput: page.getByLabel('Confirmation Message', {
				exact: true,
			}),
			headlessPermissionKeyInput: page.getByLabel(
				'Headless Action KeyRequired',
				{exact: true}
			),
			methodSelect: page.getByLabel('MethodRequired', {exact: true}),
			nameInput: page.getByPlaceholder('Action Name'),
			permissionKeyInput: page.getByLabel('Headless Action Key', {
				exact: true,
			}),
			saveButton: page.getByRole('button', {name: 'Save'}),
			selectIconModal: {
				iconsList: page.getByRole('listitem'),
				searchInput: page.getByPlaceholder('Search'),
			},
			titleInput: page.getByLabel('TitleRequired', {exact: true}),
			typeSelect: page.getByLabel('TypeRequired', {exact: true}),
			urlText: page.getByPlaceholder('Add a URL here.'),
			variantSelect: page.getByLabel('VariantRequired', {exact: true}),
		};
		this.newCreationActionButton = page.getByRole('button', {
			name: 'New Creation Action',
		});
		this.newItemActionButton = page.getByRole('button', {
			name: 'New Item Action',
		});
		this.noActionsWereCreatedMessage = page
			.getByRole('tabpanel')
			.nth(0)
			.locator('.c-empty-state-title');
		this.page = page;
		this.viewPage = new ViewPage(page);
	}

	async goto({
		dataSetLabel,
		viewLabel,
	}: {
		dataSetLabel: string;
		viewLabel: string;
	}) {
		await this.viewPage.goto({
			dataSetLabel,
			viewLabel,
		});

		await this.viewPage.selectTab('Actions');
	}

	async createCreationAction(creationActionProps: ICreationAction) {
		await this.creationActionsTab.click();

		await this.newActionButton.waitFor({state: 'visible'});

		await this.newActionButton.click();

		await this.createAction({...creationActionProps});
	}

	async createItemAction(itemActionProps: IItemAction) {
		await this.itemActionsTab.click();

		await this.newActionButton.click();

		await this.createAction({...itemActionProps});
	}

	private async createAction(actionProps: ICreationAction | IItemAction) {
		await this.newActionForm.nameInput.fill(actionProps.name);
		await this.newActionForm.addIconButton.click();

		await this.newActionForm.selectIconModal.searchInput.fill(
			actionProps.icon
		);
		await this.newActionForm.selectIconModal.iconsList
			.getByText(actionProps.icon, {exact: true})
			.click();

		await this.newActionForm.typeSelect.selectOption(actionProps.type);

		if (actionProps.type === 'modal') {
			await this.newActionForm.variantSelect.waitFor({state: 'visible'});
			await this.newActionForm.variantSelect.selectOption(
				actionProps.variant
			);
		}

		if (actionProps.type === 'modal' || actionProps.type === 'sidePanel') {
			const actionTitle = !actionProps.title
				? `${actionProps.name} title`
				: `${actionProps.title}`;

			await this.page.getByPlaceholder('add-here-the-title').click();
			await this.page
				.getByPlaceholder('add-here-the-title')
				.fill(`${actionTitle}`);
		}

		if (actionProps.type === 'async') {
			await this.newActionForm.methodSelect.selectOption(
				actionProps.method
			);
		}

		if (actionProps.type !== 'headless') {
			await this.newActionForm.urlText.fill(actionProps.url);
		}

		if ('permissionKey' in actionProps) {
			if (actionProps.type === 'headless') {
				await this.newActionForm.headlessPermissionKeyInput.fill(
					actionProps.permissionKey
				);
			}
			else {
				await this.newActionForm.permissionKeyInput.fill(
					actionProps.permissionKey
				);
			}
		}

		if ('confirmationMessage' in actionProps) {
			this.newActionForm.confirmationMessageInput.fill(
				actionProps.confirmationMessage
			);
		}

		await this.newActionForm.saveButton.click();
	}
}
