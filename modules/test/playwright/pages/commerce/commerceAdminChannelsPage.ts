/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';
import {searchTableRowByValue} from './commerceDNDTablePage';

export class CommerceAdminChannelsPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly buyerOrderApprovalWorkflow: Locator;
	readonly channelsTable: Locator;
	readonly channelsTableRow: (
		colPosition: number,
		value: number | string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly channelsTableRowLink: (channelName: string) => Promise<Locator>;
	readonly commerceSiteType: Locator;
	readonly headerActions: Locator;
	readonly headerActionsSaveButton: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.buyerOrderApprovalWorkflow = page.getByLabel(
			'Buyer Order Approval Workflow'
		);
		this.channelsTable = page.locator(
			'#portlet_com_liferay_commerce_channel_web_internal_portlet_CommerceChannelsPortlet .dnd-table'
		);
		this.channelsTableRow = async (
			colPosition: number,
			value: number | string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.channelsTable,
				colPosition,
				String(value),
				strictEqual
			);
		};
		this.channelsTableRowLink = async (channelName: string) => {
			const channelsTableRow = await this.channelsTableRow(
				0,
				channelName,
				true
			);

			if (channelsTableRow && channelsTableRow.column) {
				return channelsTableRow.column.getByRole('link', {
					name: String(channelName),
				});
			}

			throw new Error(
				`Cannot locate channel row with name ${channelName}`
			);
		};
		this.commerceSiteType = page.getByLabel('Commerce Site Type');
		this.headerActions = page.locator('.header-actions');
		this.headerActionsSaveButton = this.headerActions.getByText('Save');
		this.page = page;
	}

	async goto() {
		await this.applicationsMenuPage.goToCommerceChannels();
	}

	async changeCommerceChannelBuyerOrderApprovalWorkflow(
		buyerOrderApprovalWorkflow: string,
		channelName: string
	) {
		await this.goto();

		await (await this.channelsTableRowLink(channelName)).click();

		await this.buyerOrderApprovalWorkflow.selectOption({
			label: buyerOrderApprovalWorkflow,
		});
		await this.headerActionsSaveButton.click();
		await waitForSuccessAlert(this.page);
	}

	async changeCommerceChannelSiteType(channelName: string, siteType: string) {
		await this.goto();

		await (await this.channelsTableRowLink(channelName)).click();

		await this.commerceSiteType.selectOption({label: siteType});
		await this.headerActionsSaveButton.click();
		await this.page.waitForTimeout(200);
	}
}
