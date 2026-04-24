/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {commercePagesTest} from '../../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {siteSettingsPagesTest} from '../../../../fixtures/siteSettingsPagesTest';
import {DataApiHelpers} from '../../../../helpers/ApiHelpers';
import getRandomString from '../../../../utils/getRandomString';
import {
	performLoginViaApi,
	performUserSwitch,
} from '../../../../utils/performLogin';
import {waitForAlert} from '../../../../utils/waitForAlert';
import {createAccountWithBuyerUser, miniumSetUp} from '../../utils/commerce';

export const test = mergeTests(
	commercePagesTest,
	dataApiHelpersTest,
	loginTest(),
	siteSettingsPagesTest
);

let channel: {id: number; name: string};
let setupData: Array<{id: number | string; type: string}>;
let site: Site;

const createdRefundKeys: string[] = [];

test.beforeAll(async ({browser}) => {
	const page = await browser.newPage();

	await performLoginViaApi({page, screenName: 'test'});

	const apiHelpers = new DataApiHelpers(page);

	const miniumResult = await miniumSetUp(apiHelpers);

	channel = miniumResult.channel;
	site = miniumResult.site;
	setupData = [...apiHelpers.data];

	await page.close();
});

test.afterAll(async ({browser}) => {
	const page = await browser.newPage();

	await performLoginViaApi({page, screenName: 'test'});

	const apiHelpers = new DataApiHelpers(page);

	apiHelpers.setData(setupData);

	await apiHelpers.clearData();

	await page.close();
});

test.afterEach(async ({browser}) => {
	const page = await browser.newPage();

	await performLoginViaApi({page, screenName: 'test'});

	const apiHelpers = new DataApiHelpers(page);

	const ordersPage =
		await apiHelpers.headlessCommerceAdminOrder.getOrdersPage();

	await Promise.all(
		(ordersPage.items ?? []).map((order) =>
			apiHelpers.headlessCommerceAdminOrder.deleteOrder(order.id)
		)
	);

	await page.close();
});

test(
	'Can add / edit / delete a refund reason',
	{tag: ['@COMMERCE-12756', '@LPD-85008']},
	async ({page, refundReasonsSystemSettingPage}) => {
		const key = `refund-${getRandomString()}`;

		createdRefundKeys.push(key);

		await refundReasonsSystemSettingPage.goto();

		await refundReasonsSystemSettingPage.addReason({
			key,
			name: 'reason',
			priority: 3,
		});

		await expect(
			refundReasonsSystemSettingPage.configurationLink(key)
		).toBeVisible();

		await refundReasonsSystemSettingPage.addLink.click();
		await refundReasonsSystemSettingPage.keyInput.fill('');
		await refundReasonsSystemSettingPage.saveButton.click();

		await expect(
			refundReasonsSystemSettingPage.fieldRequiredWarning
		).toBeVisible();

		await refundReasonsSystemSettingPage.goto();

		await expect(async () => {
			await refundReasonsSystemSettingPage.actionsButton(key).click();

			await expect(
				refundReasonsSystemSettingPage.editMenuItem
			).toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});

		await refundReasonsSystemSettingPage.editMenuItem.click();

		const newKey = `${key}-edited`;

		await refundReasonsSystemSettingPage.keyInput.fill(newKey);
		await refundReasonsSystemSettingPage.updateButton.click();

		await waitForAlert(page);

		await expect(
			refundReasonsSystemSettingPage.configurationLink(newKey)
		).toBeVisible();

		await refundReasonsSystemSettingPage.deleteReason(newKey);

		await expect(
			refundReasonsSystemSettingPage.configurationLink(newKey)
		).not.toBeVisible();
	}
);

test(
	'Money Order payment method can be used to place an order',
	{tag: ['@COMMERCE-6218', '@LPD-85008']},
	async ({
		apiHelpers,
		checkoutPage,
		commerceAdminChannelDetailsPage,
		commerceAdminChannelsPage,
		commerceMiniCartPage,
		page,
	}) => {
		test.setTimeout(180000);

		await commerceAdminChannelsPage.goto();

		await (
			await commerceAdminChannelsPage.channelsTableRowLink(channel.name)
		).click();
		await commerceAdminChannelDetailsPage.activateChannelConfiguration(
			'Money Order',
			'Payment Methods'
		);

		const {buyerUser} = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		await performUserSwitch(page, buyerUser.alternateName);

		await page.goto(`/web/${site.name}/catalog`);

		await commerceMiniCartPage.quickAddToCart('MIN55861');
		await commerceMiniCartPage.submitButton.click();

		await checkoutPage.addAddress({
			city: 'Test City',
			countryLabel: 'United States',
			name: 'Test Name',
			regionLabel: 'Florida',
			street: 'Test Street',
			zip: '12345',
		});
		await checkoutPage.continueButton.click();

		await page.waitForURL((url) => url.href.includes('shipping-method'));

		await checkoutPage.continueButton.click();

		await page.waitForURL((url) => url.href.includes('order-summary'));

		await expect(page.getByText('Money Order').first()).toBeVisible();
	}
);

test(
	'Money Order payment can be cancelled from the admin',
	{tag: ['@COMMERCE-6219', '@LPD-85008']},
	async ({
		apiHelpers,
		checkoutPage,
		commerceAdminChannelDetailsPage,
		commerceAdminChannelsPage,
		commerceAdminOrderDetailsPage,
		commerceAdminOrdersPage,
		commerceMiniCartPage,
		page,
	}) => {
		test.setTimeout(180000);

		await commerceAdminChannelsPage.goto();

		await (
			await commerceAdminChannelsPage.channelsTableRowLink(channel.name)
		).click();
		await commerceAdminChannelDetailsPage.activateChannelConfiguration(
			'Money Order',
			'Payment Methods'
		);

		const {buyerUser} = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		await performUserSwitch(page, buyerUser.alternateName);

		await page.goto(`/web/${site.name}/catalog`);

		await commerceMiniCartPage.quickAddToCart('MIN55861');
		await commerceMiniCartPage.submitButton.click();

		await checkoutPage.addAddress({
			city: 'Test City',
			countryLabel: 'United States',
			name: 'Test Name',
			regionLabel: 'Florida',
			street: 'Test Street',
			zip: '12345',
		});
		await checkoutPage.continueButton.click();

		await page.waitForURL((url) => url.href.includes('shipping-method'));

		await checkoutPage.continueButton.click();

		await page.waitForURL((url) => url.href.includes('order-summary'));

		await checkoutPage.continueButton.click();

		await expect(checkoutPage.goToOrderDetailsButton).toBeVisible();

		await performUserSwitch(page, 'test');

		const ordersResponse =
			await apiHelpers.headlessCommerceAdminOrder.getOrdersPage();
		const order = ordersResponse.items[0];

		await commerceAdminOrdersPage.goto();

		await (
			await commerceAdminOrdersPage.tableRowLink({
				colIndex: 1,
				rowValue: order.id,
			})
		).click();

		await (
			await commerceAdminOrderDetailsPage.orderDetailsTab('Payments')
		).click();

		await expect(
			commerceAdminOrderDetailsPage.paymentStatusText('Pending')
		).toBeVisible();

		await (
			await commerceAdminOrderDetailsPage.editEntryActionLink(
				'Payment Status',
				'Edit'
			)
		).click();

		await commerceAdminOrderDetailsPage.editPaymentStatusSelect.selectOption(
			{label: 'Canceled'}
		);
		await commerceAdminOrderDetailsPage.editPaymentStatusSubmitButton.click();

		await expect(
			commerceAdminOrderDetailsPage.paymentStatusText('Canceled')
		).toBeVisible();
	}
);

test(
	'Mercanet secret key is masked in the page and HTML source',
	{tag: ['@COMMERCE-11432', '@LPD-85008']},
	async ({
		commerceAdminChannelDetailsPage,
		commerceAdminChannelsPage,
		page,
		siteSettingsPage,
	}) => {
		await test.step('Site Settings scope', async () => {
			const secretKeyInput = page
				.getByText('Secret Key')
				.locator('../input')
				.first();

			await siteSettingsPage.goToSiteSetting(
				'Payment',
				'Mercanet Payment Engine Method Group Service',
				site.friendlyUrlPath
			);

			await secretKeyInput.fill('MercanetPassword');
			await siteSettingsPage.saveConfiguration();

			await expect(secretKeyInput).toHaveValue('TEMP_OBFUSCATION_VALUE');

			const html = await page.content();

			expect(html).toContain('TEMP_OBFUSCATION_VALUE');
			expect(html).not.toContain('MercanetPassword');
		});

		await test.step('Channel admin side panel scope', async () => {
			await commerceAdminChannelsPage.goto();

			await (
				await commerceAdminChannelsPage.channelsTableRowLink(
					channel.name
				)
			).click();

			await (
				await commerceAdminChannelDetailsPage.generalCommerceAdminChannelTableLink(
					'Mercanet'
				)
			).click();

			if (!(await commerceAdminChannelDetailsPage.isActive.isChecked())) {
				await commerceAdminChannelDetailsPage.isActive.check();
			}

			await (
				await commerceAdminChannelDetailsPage.frameSaveButton(
					false,
					'Payment Methods'
				)
			).click();
			await (
				await commerceAdminChannelDetailsPage.sidePanelFrameNavLink(
					'Configuration',
					'Payment Methods'
				)
			).click();

			const secretKeyInput =
				await commerceAdminChannelDetailsPage.sidePanelFrameInput(
					'Secret Key',
					'Payment Methods'
				);

			await secretKeyInput.fill('MercanetPassword');
			await (
				await commerceAdminChannelDetailsPage.frameSaveButton(
					false,
					'Payment Methods'
				)
			).click();

			await expect(secretKeyInput).toHaveValue('TEMP_OBFUSCATION_VALUE');

			const iframeHtml =
				await commerceAdminChannelDetailsPage.sidePanelFrameBodyHTML(
					'Payment Methods'
				);

			expect(iframeHtml).toContain('TEMP_OBFUSCATION_VALUE');
			expect(iframeHtml).not.toContain('MercanetPassword');
		});
	}
);
