/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';
export class CommerceAdminChannelDetailsPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly channelNameLink: (channelName: string) => Locator;
	readonly closeSidePanelFrame: (
		isNestedFrame: boolean,
		tableName: string
	) => Promise<Locator>;
	readonly countryTab: Locator;
	readonly detailsButton: (tableName: string) => Promise<Locator>;
	readonly eligibilityTab: (
		isNestedFrame: boolean,
		tableName: string
	) => Promise<Locator>;
	readonly eligibilityOptionButton: (
		eligibilityOption: string,
		isNestedFrame: boolean,
		tableName: string
	) => Promise<Locator>;
	readonly frameSaveButton: (
		isNestedFrame: boolean,
		tableName: string
	) => Promise<Locator>;
	readonly generalCommerceAdminChannelTableLink: (
		name: string
	) => Promise<Locator>;
	readonly isActive: (tableName: string) => Promise<Locator>;
	readonly page: Page;
	readonly placeHolderTerm: (
		isNestedFrame: boolean,
		tableName: string,
		text: string
	) => Promise<Locator>;
	readonly saveButton: Locator;
	readonly selectButton: (
		isNestedFrame: boolean,
		tableName: string
	) => Promise<Locator>;
	readonly sidePanelFrame: (tableName: string) => Promise<FrameLocator>;
	readonly sidePanelFrameButton: (
		buttonName: string,
		tableName: string
	) => Promise<Locator>;
	readonly sidePanelNestedFrame: (tableName: string) => Promise<FrameLocator>;
	readonly shippingOptionsTab: (tableName: string) => Promise<Locator>;
	readonly shippingOptionsTableLink: (
		shippingOptionName: string,
		tableName: string
	) => Promise<Locator>;
	readonly showSeparateOrderItemsToggle: Locator;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.channelNameLink = (channelName: string) =>
			page.getByRole('link', {
				exact: true,
				name: channelName,
			});
		this.countryTab = page.getByRole('link', {name: 'Countries'});
		this.generalCommerceAdminChannelTableLink = async (name: string) => {
			return page.getByRole('link', {exact: true, name});
		};
		this.sidePanelFrame = async (tableName: string) => {
			switch (tableName) {
				case 'Payment Methods':
					return this.page.frameLocator('iframe >> nth=1');
				case 'Shipping Methods':
					return this.page.frameLocator('iframe >> nth=2');
				case 'Tax Calculations':
					return this.page.frameLocator('iframe >> nth=3');
				default:
					break;
			}
		};
		this.sidePanelNestedFrame = async (tableName: string) => {
			return (await this.sidePanelFrame(tableName)).frameLocator(
				'iframe'
			);
		};
		this.closeSidePanelFrame = async (
			isNestedFrame: boolean,
			tableName: string
		) => {
			if (isNestedFrame) {
				return (await this.sidePanelNestedFrame(tableName))
					.locator('.btn')
					.first();
			}

			return (await this.sidePanelFrame(tableName))
				.getByRole('button')
				.first();
		};
		this.detailsButton = async (tableName: string) => {
			return (await this.sidePanelNestedFrame(tableName)).getByRole(
				'button',
				{name: 'Details'}
			);
		};
		this.eligibilityTab = async (
			isNestedFrame: boolean,
			tableName: string
		) => {
			if (isNestedFrame) {
				return (await this.sidePanelNestedFrame(tableName)).getByRole(
					'link',
					{name: 'Eligibility'}
				);
			}

			return (await this.sidePanelFrame(tableName)).getByRole('link', {
				name: 'Eligibility',
			});
		};
		this.eligibilityOptionButton = async (
			eligibilityOption: string,
			isNestedFrame: boolean,
			tableName: string
		) => {
			if (isNestedFrame) {
				return (await this.sidePanelNestedFrame(tableName)).getByLabel(
					eligibilityOption
				);
			}

			return (await this.sidePanelFrame(tableName)).getByLabel(
				eligibilityOption
			);
		};
		this.frameSaveButton = async (
			isNestedFrame: boolean,
			tableName: string
		) => {
			if (isNestedFrame) {
				return (await this.sidePanelNestedFrame(tableName)).getByRole(
					'button',
					{name: 'Save'}
				);
			}

			return (await this.sidePanelFrame(tableName)).getByRole('button', {
				name: 'Save',
			});
		};
		this.isActive = async (tableName: string) => {
			return (await this.sidePanelFrame(tableName)).getByLabel('Active');
		};
		this.sidePanelFrameButton = async (
			buttonName: string,
			tableName: string
		) => {
			return (await this.sidePanelNestedFrame(tableName)).getByRole(
				'button',
				{exact: true, name: buttonName}
			);
		};
		this.saveButton = page.getByRole('link', {name: 'Save'});
		this.selectButton = async (
			isNestedFrame: boolean,
			tableName: string
		) => {
			if (isNestedFrame) {
				return (await this.sidePanelNestedFrame(tableName)).getByRole(
					'button',
					{name: 'Select'}
				);
			}

			return (await this.sidePanelFrame(tableName)).getByRole('button', {
				name: 'Select',
			});
		};
		this.shippingOptionsTab = async (tableName: string) => {
			return (await this.sidePanelFrame(tableName)).getByRole('link', {
				name: 'Shipping Options',
			});
		};
		this.shippingOptionsTableLink = async (
			shippingOptionName: string,
			tableName: string
		) => {
			return (await this.sidePanelFrame(tableName)).getByRole('link', {
				name: shippingOptionName,
			});
		};
		this.showSeparateOrderItemsToggle = page.getByLabel(
			'Show Separate Order Items'
		);
		this.page = page;
		this.placeHolderTerm = async (
			isNestedFrame: boolean,
			tableName: string,
			text: string
		) => {
			if (isNestedFrame) {
				return (
					await this.sidePanelNestedFrame(tableName)
				).getByPlaceholder(text);
			}

			return (await this.sidePanelFrame(tableName)).getByPlaceholder(
				text
			);
		};
	}

	async activateChannelConfiguration(name: string, tableName: string) {
		await (await this.generalCommerceAdminChannelTableLink(name)).click();

		await (await this.isActive(tableName)).check();
		await (await this.frameSaveButton(false, tableName)).click();
		await (await this.closeSidePanelFrame(false, tableName)).click();
	}

	async setEntryEligibility(
		eligibilityOption: string,
		entryName: string,
		tableName: string,
		shippingOption?: string
	) {
		let isNestedFrame: boolean;
		if (tableName === 'Payment Methods') {
			isNestedFrame = false;
			await (await this.eligibilityTab(isNestedFrame, tableName)).click();
			await (
				await this.eligibilityOptionButton(
					eligibilityOption,
					isNestedFrame,
					tableName
				)
			).click();
			await (
				await this.placeHolderTerm(
					isNestedFrame,
					tableName,
					'Find a Payment Term'
				)
			).fill(entryName);
			await (await this.selectButton(isNestedFrame, tableName)).click();
			await (
				await this.frameSaveButton(isNestedFrame, tableName)
			).click();
			await waitForSuccessAlert(await this.sidePanelFrame(tableName));
			await (
				await this.closeSidePanelFrame(isNestedFrame, tableName)
			).click();
		}
		else if (tableName === 'Shipping Methods') {
			isNestedFrame = true;
			await (await this.shippingOptionsTab(tableName)).click();
			await (
				await this.shippingOptionsTableLink(shippingOption, tableName)
			).click();
			await (await this.detailsButton(tableName)).click();
			await (await this.eligibilityTab(isNestedFrame, tableName)).click();
			await (
				await this.eligibilityOptionButton(
					eligibilityOption,
					isNestedFrame,
					tableName
				)
			).click();
			await this.page.waitForLoadState();
			await (
				await this.placeHolderTerm(
					isNestedFrame,
					tableName,
					'Find a Delivery Term'
				)
			).fill(entryName);
			await (await this.selectButton(isNestedFrame, tableName)).click();
			await (
				await this.frameSaveButton(isNestedFrame, tableName)
			).click();
			await waitForSuccessAlert(
				await this.sidePanelNestedFrame(tableName)
			);
			await (
				await this.closeSidePanelFrame(isNestedFrame, tableName)
			).click();
		}
	}

	async goto(checkTabVisibility = true) {
		await this.applicationsMenuPage.goToCommerceChannels(
			checkTabVisibility
		);
	}

	async goToCountries() {
		await this.countryTab.click();
	}
}
