/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {ICreationAction, IItemAction} from '../../../../../utils/types';
import {DataSetPage} from '../DataSetPage';

export class ActionsPage {
	readonly actionForm: {
		addIconButton: Locator;
		cancelButton: Locator;
		changeIconButton: Locator;
		confirmationMessageInput: Locator;
		confirmationMessageTypeSelect: Locator;
		headlessActionKeyInput: Locator;
		iconInput: Locator;
		labelInput: Locator;
		methodSelect: Locator;
		saveButton: Locator;
		selectIconModal: {
			iconsList: Locator;
			searchInput: Locator;
		};
		typeSelect: Locator;
		urlInput: Locator;
		variantSelect: Locator;
	};
	readonly creationActionsTab: Locator;
	readonly creationActionsTable: Locator;
	readonly dataSetPage: DataSetPage;
	readonly deletionConfirmationModal: Locator;
	readonly itemActionsTab: Locator;
	readonly itemActionsTable: Locator;
	readonly newItemActionPlusButton: Locator;
	readonly newCreationActionPlusButton: Locator;
	readonly newCreationActionButton: Locator;
	readonly newItemActionButton: Locator;
	readonly noActionsWereCreatedMessage: Locator;
	readonly page: Page;
	private readonly tabsContainer: Locator;

	constructor(page: Page) {
		this.actionForm = {
			addIconButton: page.getByLabel('add-icon'),
			cancelButton: page.getByRole('button', {name: 'Cancel'}),
			changeIconButton: page.getByLabel('change-icon'),
			confirmationMessageInput: page.getByPlaceholder(
				'Add a message here.'
			),
			confirmationMessageTypeSelect: page.getByLabel('Message Type', {
				exact: true,
			}),
			headlessActionKeyInput: page.getByPlaceholder('Add a value here.'),
			iconInput: page.getByPlaceholder('No Icon Selected'),
			labelInput: page.getByPlaceholder('Action Name'),
			methodSelect: page.getByLabel('MethodRequired', {exact: true}),
			saveButton: page.getByRole('button', {name: 'Save'}),
			selectIconModal: {
				iconsList: page.getByRole('listitem'),
				searchInput: page.getByPlaceholder('Search'),
			},
			typeSelect: page.getByLabel('TypeRequired', {exact: true}),
			urlInput: page.getByPlaceholder('Add a URL here.'),
			variantSelect: page.getByLabel('VariantRequired', {exact: true}),
		};
		this.creationActionsTab = page.getByRole('tab', {
			name: 'Creation Actions',
		});
		this.creationActionsTable = page.locator(
			'.creation-actions-tab-pane table'
		);
		this.dataSetPage = new DataSetPage(page);
		this.deletionConfirmationModal = page
			.getByRole('dialog')
			.and(page.getByLabel('Delete Action'));
		this.itemActionsTab = page.getByRole('tab', {name: 'Item Actions'});
		this.itemActionsTable = page.locator('.item-actions-tab-pane table');
		this.newItemActionPlusButton = page.getByTitle('New Item Action');
		this.newCreationActionPlusButton = page.getByText(
			'New Creation Action'
		);
		this.newCreationActionButton = page.getByText('New Creation Action');
		this.newItemActionButton = page.getByText('New Item Action');
		this.noActionsWereCreatedMessage = page
			.getByRole('tabpanel')
			.nth(0)
			.locator('.c-empty-state-title');
		this.page = page;
		this.tabsContainer = page.locator('.actions-tabs');
	}

	async goto({dataSetLabel}: {dataSetLabel: string}) {
		await this.dataSetPage.goto({
			dataSetLabel,
		});

		await this.dataSetPage.selectTab('Actions');
	}

	async gotoCreationActionsTab({dataSetLabel}: {dataSetLabel: string}) {
		await this.goto({
			dataSetLabel,
		});

		await this.selectTab('Creation Actions');
	}

	async gotoItemActionsTab({dataSetLabel}: {dataSetLabel: string}) {
		await this.goto({
			dataSetLabel,
		});

		await this.selectTab('Item Actions');
	}

	async createCreationAction(creationActionProps: ICreationAction) {
		await this.newCreationActionPlusButton.click();

		await this.fillActionFormValues({...creationActionProps});

		await this.actionForm.saveButton.click();
	}

	async createItemAction(itemActionProps: IItemAction) {
		await this.newItemActionPlusButton.click();

		await this.fillItemActionFormValues({...itemActionProps});

		await this.actionForm.saveButton.click();
	}

	async fillItemActionFormValues(itemActionProps: IItemAction) {
		const {confirmationMessage, confirmationMessageType} = itemActionProps;

		if (confirmationMessage) {
			this.actionForm.confirmationMessageInput.fill(confirmationMessage);
		}

		if (confirmationMessageType) {
			this.actionForm.confirmationMessageTypeSelect.selectOption(
				confirmationMessageType
			);
		}

		await this.fillActionFormValues({...itemActionProps});
	}

	private async fillActionFormValues(
		actionProps: ICreationAction | IItemAction
	) {
		await this.actionForm.labelInput.fill(actionProps.label);

		const iconSelected = Boolean(
			await this.actionForm.iconInput.inputValue()
		);

		if (iconSelected) {
			await this.actionForm.changeIconButton.click();
		}
		else {
			await this.actionForm.addIconButton.click();
		}

		await this.actionForm.selectIconModal.searchInput.fill(
			actionProps.icon
		);
		await this.actionForm.selectIconModal.iconsList
			.getByText(actionProps.icon, {exact: true})
			.click();

		const typeDisabled = await this.actionForm.typeSelect.isDisabled();

		if (!typeDisabled) {
			await this.actionForm.typeSelect.selectOption(actionProps.type);
		}

		if (actionProps.type === 'modal') {
			await this.actionForm.variantSelect.waitFor({state: 'visible'});
			await this.actionForm.variantSelect.selectOption(
				actionProps.variant
			);
		}

		if (actionProps.type === 'modal' || actionProps.type === 'sidePanel') {
			const actionTitle = !actionProps.title
				? `${actionProps.label} title`
				: `${actionProps.title}`;

			await this.page.getByPlaceholder('Add the title').click();
			await this.page
				.getByPlaceholder('Add the title')
				.fill(`${actionTitle}`);
		}

		if (actionProps.type === 'async') {
			await this.actionForm.methodSelect.selectOption(actionProps.method);
		}

		if (actionProps.type !== 'headless') {
			await this.actionForm.urlInput.fill(actionProps.url);
		}

		if (actionProps.headlessActionKey) {
			await this.actionForm.headlessActionKeyInput.fill(
				actionProps.headlessActionKey
			);
		}
	}

	private async selectTab(tabLabel: string) {
		const tabButton = this.tabsContainer.getByRole('tab', {
			exact: true,
			name: tabLabel,
		});

		await tabButton.click();

		await tabButton.and(this.page.locator('.active')).waitFor();

		await expect(
			this.page.locator('.loading-animation')
		).not.toBeInViewport();
	}
}
