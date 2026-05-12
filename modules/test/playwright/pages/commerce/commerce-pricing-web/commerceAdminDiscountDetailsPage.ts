/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {CommerceDNDTablePage} from '../commerceDNDTablePage';

export class CommerceAdminDiscountDetailsPage extends CommerceDNDTablePage {
	readonly addDiscountRuleButton: Locator;
	readonly addRelationField: (title: string) => Locator;
	readonly addRelationFieldOption: (
		title: string,
		entryName: string
	) => Locator;
	readonly amountFieldReuiredErrorMessage: Locator;
	readonly amountInput: Locator;
	readonly cartTotalMiniumAmountInput: Locator;
	readonly draftStatus: Locator;
	readonly editDiscountRuleFrame: FrameLocator;
	readonly eligibilityEntryCell: (name: string) => Locator;
	readonly eligibilityRowActions: (name: string) => Locator;
	readonly eligibilityRowRemoveMenuItem: Locator;
	readonly eligibilityTab: Locator;
	readonly errorAlert: (text: string) => Locator;
	readonly maximumDiscountAmountInput: Locator;
	readonly mustBeDecimalErrorMessage: Locator;
	readonly mustBeValidNumberErrorMessage: Locator;
	readonly nameInput: Locator;
	readonly page: Page;
	readonly pendingStatus: Locator;
	readonly publishButton: Locator;
	readonly saveAsDraftButton: Locator;
	readonly submitForWorkflowButton: Locator;
	readonly relationFindInput: (placeholder: string) => Locator;
	readonly relationResultCell: (entryName: string) => Locator;
	readonly relationRowSelectButton: (entryName: string) => Locator;
	readonly saveButton: Locator;
	readonly specificAccountGroupsRadio: Locator;
	readonly specificAccountsRadio: Locator;
	readonly specificChannelsRadio: Locator;
	readonly specificOrderTypesRadio: Locator;
	readonly tableRowAtIndex: (rowIndex: number) => Locator;
	readonly targetSelect: Locator;
	readonly typeSelect: Locator;

	constructor(page: Page) {
		super(
			page,
			'#_com_liferay_commerce_pricing_web_internal_portlet_CommerceDiscountPortlet_fm .fds table'
		);
		this.editDiscountRuleFrame = page.frameLocator('iframe').nth(1);

		this.addDiscountRuleButton = page.getByRole('button', {
			exact: true,
			name: 'Add',
		});
		this.addRelationField = (title: string) =>
			page
				.locator('div.input-group')
				.filter({hasText: title})
				.getByPlaceholder(title);
		this.addRelationFieldOption = (title: string, entryName: string) =>
			page
				.locator('.dropdown-menu.show')
				.filter({hasText: title})
				.getByRole('option', {name: entryName})
				.or(
					page
						.locator('.dropdown-menu.show')
						.getByRole('button', {name: entryName})
				)
				.or(page.getByRole('cell', {name: entryName}).first());
		this.amountInput = page.getByLabel('Amount', {exact: true});
		this.errorAlert = (text: string) =>
			page.locator('.alert-danger', {hasText: text});
		this.maximumDiscountAmountInput = page.getByLabel(
			'Maximum Discount Amount'
		);
		this.draftStatus = page.getByText('Draft', {exact: true});
		this.nameInput = page.getByLabel('Name', {exact: true});
		this.pendingStatus = page.getByText('Pending', {exact: true});
		this.publishButton = page
			.getByRole('button', {exact: true, name: 'Publish'})
			.or(page.getByRole('link', {exact: true, name: 'Publish'}));
		this.saveAsDraftButton = page
			.getByRole('button', {exact: true, name: 'Save as Draft'})
			.or(page.getByRole('link', {exact: true, name: 'Save as Draft'}));
		this.submitForWorkflowButton = page
			.getByRole('button', {exact: true, name: 'Submit for Workflow'})
			.or(
				page.getByRole('link', {
					exact: true,
					name: 'Submit for Workflow',
				})
			);
		this.relationFindInput = (placeholder: string) =>
			page.getByPlaceholder(placeholder);
		this.relationResultCell = (entryName: string) =>
			page.getByRole('cell', {name: entryName}).first();
		this.relationRowSelectButton = (entryName: string) =>
			page
				.getByRole('row')
				.filter({hasText: entryName})
				.getByRole('button', {exact: true, name: 'Select'});
		this.specificAccountGroupsRadio = page.getByRole('radio', {
			name: 'Specific Account Groups',
		});
		this.tableRowAtIndex = (rowIndex: number) =>
			this.table.locator('tbody tr').nth(rowIndex);
		this.targetSelect = page.getByLabel('Apply To');
		this.typeSelect = page.getByLabel('Type', {exact: true});
		this.amountFieldReuiredErrorMessage =
			this.editDiscountRuleFrame.getByText(
				'The Cart Total Minimum Amount field is required.'
			);
		this.cartTotalMiniumAmountInput = this.editDiscountRuleFrame.getByLabel(
			'Cart Total Minimum Amount'
		);
		this.mustBeDecimalErrorMessage = this.editDiscountRuleFrame.getByText(
			'Error:Cart total minimum amount cannot be empty and must be a decimal number.'
		);
		this.mustBeValidNumberErrorMessage =
			this.editDiscountRuleFrame.getByText(
				'Please enter a valid number.'
			);
		this.eligibilityEntryCell = (name: string) =>
			page.getByRole('cell', {name}).first();
		this.eligibilityRowActions = (name: string) =>
			page
				.getByRole('row')
				.filter({hasText: name})
				.getByRole('button', {name: 'Actions'});
		this.eligibilityRowRemoveMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Remove',
		});
		this.eligibilityTab = page.getByRole('link', {
			exact: true,
			name: 'Eligibility',
		});
		this.page = page;
		this.saveButton = this.editDiscountRuleFrame.getByRole('button', {
			name: 'Save',
		});
		this.specificAccountsRadio = page.getByRole('radio', {
			name: 'Specific Accounts',
		});
		this.specificChannelsRadio = page.getByRole('radio', {
			name: 'Specific Channels',
		});
		this.specificOrderTypesRadio = page.getByRole('radio', {
			name: 'Specific Order Types',
		});
	}

	async addEligibilityEntry(placeholder: string, entryName: string) {
		const findInput = this.relationFindInput(placeholder);

		await findInput.click();
		await findInput.fill(entryName);

		const rowSelectButton = this.relationRowSelectButton(entryName);

		await expect(rowSelectButton).toBeVisible();

		await rowSelectButton.click();
	}
}
