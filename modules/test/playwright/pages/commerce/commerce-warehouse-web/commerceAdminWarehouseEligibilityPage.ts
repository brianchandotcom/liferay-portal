/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CommerceAdminWarehouseEligibilityPage {
	readonly addChannels: Locator;
	readonly channelRow: (channelName: string) => Locator;
	readonly channelRowSelectButton: (channelName: string) => Locator;
	readonly channelsHeading: Locator;
	readonly detailsActiveToggle: Locator;
	readonly linkTab: Locator;
	readonly noChannelRadio: Locator;
	readonly page: Page;
	readonly specificChannelsRadio: Locator;
	readonly selectButton: Locator;

	constructor(page: Page) {
		this.addChannels = page.getByPlaceholder('Find a Channel');
		this.channelRow = (channelName: string) =>
			page.getByRole('link', {exact: true, name: channelName});
		this.channelRowSelectButton = (channelName: string) =>
			page
				.getByRole('row')
				.filter({hasText: channelName})
				.getByRole('button', {exact: true, name: 'Select'});
		this.channelsHeading = page.getByRole('heading', {
			exact: true,
			name: 'Channels',
		});
		this.detailsActiveToggle = page.getByLabel('Active');
		this.linkTab = page.getByRole('link', {
			exact: true,
			name: 'Eligibility',
		});
		this.noChannelRadio = page.getByLabel('No Channel');
		this.page = page;
		this.specificChannelsRadio = page.getByLabel('Specific Channels');
		this.selectButton = page.getByRole('button', {
			exact: true,
			name: 'Select',
		});
	}
}
