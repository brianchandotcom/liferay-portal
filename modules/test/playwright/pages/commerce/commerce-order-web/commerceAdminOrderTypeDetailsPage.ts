/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CommerceAdminOrderTypeDetailsPage {
	readonly activeToggle: Locator;
	readonly displayOrderInput: Locator;
	readonly eligibilityFindChannelInput: Locator;
	readonly eligibilityRowSelectButton: (channelName: string) => Locator;
	readonly eligibilitySelectButton: Locator;
	readonly eligibilityTab: Locator;
	readonly errorAlert: (text: string) => Locator;
	readonly headerDetailsTitle: Locator;
	readonly orderTypeId: Locator;
	readonly publishLink: Locator;
	readonly saveButton: Locator;
	readonly specificChannelsRadio: Locator;

	constructor(page: Page) {
		this.activeToggle = page.getByLabel('Active', {exact: true});
		this.displayOrderInput = page.getByLabel('Display Order');
		this.eligibilityFindChannelInput =
			page.getByPlaceholder('Find a Channel');
		this.eligibilityRowSelectButton = (channelName: string) =>
			page
				.getByRole('row', {name: channelName})
				.getByRole('button', {exact: true, name: 'Select'});
		this.eligibilitySelectButton = page.getByRole('button', {
			exact: true,
			name: 'Select',
		});
		this.eligibilityTab = page.getByRole('link', {
			exact: true,
			name: 'Eligibility',
		});
		this.errorAlert = (text: string) =>
			page.locator('.alert-danger', {hasText: text});
		this.headerDetailsTitle = page.getByTestId('headerDetailsTitle');
		this.orderTypeId = page
			.getByText('ID')
			.locator('..')
			.locator('.header-info-value');
		this.publishLink = page
			.getByRole('link', {name: 'Publish'})
			.or(page.getByRole('button', {exact: true, name: 'Publish'}));
		this.saveButton = page
			.getByRole('button', {exact: true, name: 'Save'})
			.or(this.publishLink);
		this.specificChannelsRadio = page.getByRole('radio', {
			name: 'Specific Channels',
		});
	}
}
