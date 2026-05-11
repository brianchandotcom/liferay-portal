/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accountsPagesTest} from '../../../../fixtures/accountsPagesTest';
import {commercePagesTest} from '../../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {DataApiHelpers} from '../../../../helpers/ApiHelpers';
import {CommerceAdminChannelDetailsPage} from '../../../../pages/commerce/commerce-channel-web/commerceAdminChannelDetailsPage';
import {CommerceAdminChannelsPage} from '../../../../pages/commerce/commerce-channel-web/commerceAdminChannelsPage';
import getRandomString from '../../../../utils/getRandomString';
import {
	performLoginViaApi,
	performUserSwitch,
} from '../../../../utils/performLogin';
import {createAccountWithBuyerUser, miniumSetUp} from '../../utils/commerce';

export const test = mergeTests(
	accountsPagesTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

let channel: {id: number; name: string};
let setupData: Array<{id: number | string; type: string}>;
let site: Site;

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

test.afterEach(async ({browser}, testInfo) => {
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

	if (
		new Set([
			'Eligibility — admin UI hides ineligible shipping options',
			'Eligibility — checkout cannot select an ineligible default shipping option',
		]).has(testInfo.title)
	) {
		const commerceAdminChannelsPage = new CommerceAdminChannelsPage(page);
		const commerceAdminChannelDetailsPage =
			new CommerceAdminChannelDetailsPage(page);

		await commerceAdminChannelsPage.goto();
		await (
			await commerceAdminChannelsPage.channelsTableRowLink(channel.name)
		).click();
		await commerceAdminChannelDetailsPage.deactivateChannelConfiguration(
			'Variable Rate',
			'Shipping Methods'
		);
		await commerceAdminChannelDetailsPage.activateChannelConfiguration(
			'Flat Rate',
			'Shipping Methods'
		);
	}

	await page.close();
});

test(
	'Eligibility — admin UI hides ineligible shipping options',
	{tag: ['@COMMERCE-9130', '@LPD-85008']},
	async ({
		accountsPage,
		apiHelpers,
		commerceAdminChannelDetailsPage,
		commerceAdminChannelsPage,
		editAccountChannelDefaultsPage,
		editAccountPage,
	}) => {
		const {account} = await createAccountWithBuyerUser(apiHelpers, site.id);

		await test.step('Disable Flat Rate shipping method', async () => {
			await commerceAdminChannelsPage.goto();

			await (
				await commerceAdminChannelsPage.channelsTableRowLink(
					channel.name
				)
			).click();

			await commerceAdminChannelDetailsPage.deactivateChannelConfiguration(
				'Flat Rate',
				'Shipping Methods'
			);
		});

		await test.step('Open the account Channel Defaults tab', async () => {
			await accountsPage.gotoAccountAdmin();

			await (
				await accountsPage.accountsTable.cellLink(account.name)
			).click();
			await editAccountPage.channelDefaultsLink.click();
		});

		await test.step('Only "Use Priority Settings" is selectable', async () => {
			await expect(async () => {
				await (
					await editAccountChannelDefaultsPage.defaultShippingOptionsTableRowAction(
						'Edit',
						channel.name
					)
				).click();

				await expect(
					editAccountChannelDefaultsPage.modalIframe
				).toBeVisible({timeout: 1000});
			}).toPass({timeout: 10000});

			await expect(
				editAccountChannelDefaultsPage.modalOptionCheckbox(
					'Use Priority Settings'
				)
			).toBeVisible();
			await expect(
				editAccountChannelDefaultsPage.modalOptionCheckbox(
					'Flat Rate / Standard Delivery'
				)
			).toHaveCount(0);
			await expect(
				editAccountChannelDefaultsPage.modalOptionCheckbox(
					'Flat Rate / Expedited Delivery'
				)
			).toHaveCount(0);

			await editAccountChannelDefaultsPage
				.modalOptionCheckbox('Use Priority Settings')
				.check();
			await editAccountChannelDefaultsPage.modalSaveButton.click();

			await expect(
				editAccountChannelDefaultsPage.modalIframe
			).toHaveCount(0);
			await expect(
				editAccountChannelDefaultsPage.usePrioritySettingsCell
			).toBeVisible();
		});
	}
);

test(
	'Eligibility — checkout cannot select an ineligible default shipping option',
	{tag: ['@LPD-85008']},
	async ({
		accountsPage,
		apiHelpers,
		checkoutPage,
		commerceAdminChannelDetailsPage,
		commerceAdminChannelsPage,
		commerceMiniCartPage,
		editAccountChannelDefaultsPage,
		editAccountPage,
		page,
	}) => {
		const {account, buyerUser} = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		const variableRateOptionName = `Test Shipping Option ${getRandomString()}`;

		await test.step('Open the account Channel Defaults tab', async () => {
			await accountsPage.gotoAccountAdmin();

			await (
				await accountsPage.accountsTable.cellLink(account.name)
			).click();
			await editAccountPage.channelDefaultsLink.click();
		});

		await test.step('Set Flat Rate / Standard Delivery as default for the account', async () => {
			await (
				await editAccountChannelDefaultsPage.defaultShippingOptionsTableRowAction(
					'Edit',
					channel.name
				)
			).click();

			await editAccountChannelDefaultsPage
				.modalOptionCheckbox('Flat Rate / Standard Delivery')
				.check();

			await editAccountChannelDefaultsPage.modalSaveButton.click();

			await expect(
				editAccountChannelDefaultsPage.modalIframe
			).toHaveCount(0);
		});

		await test.step('Disable Flat Rate, activate Variable Rate, add a Variable Rate option', async () => {
			await commerceAdminChannelsPage.goto();

			await (
				await commerceAdminChannelsPage.channelsTableRowLink(
					channel.name
				)
			).click();

			await commerceAdminChannelDetailsPage.deactivateChannelConfiguration(
				'Flat Rate',
				'Shipping Methods'
			);
			await commerceAdminChannelDetailsPage.activateChannelConfiguration(
				'Variable Rate',
				'Shipping Methods'
			);
			await commerceAdminChannelDetailsPage.addVariableRateShippingOption(
				variableRateOptionName
			);
			await commerceAdminChannelDetailsPage.addVariableRateShippingOptionSetting(
				variableRateOptionName,
				undefined,
				'100',
				'100'
			);
		});

		await test.step('Buyer checks out and falls through to Variable Rate option', async () => {
			await performUserSwitch(page, buyerUser.alternateName);

			await page.goto(`/web${site.friendlyUrlPath}/catalog`);

			await commerceMiniCartPage.quickAddToCart('MIN55861');
			await commerceMiniCartPage.submitButton.click();

			await checkoutPage.addAddress({
				city: 'Test City',
				countryLabel: 'United States',
				name: 'Address Name',
				regionLabel: 'Florida',
				street: 'Test Address',
				zip: '12345',
			});

			await checkoutPage.continueButton.click();

			await page.waitForURL(
				(url) =>
					url.href.includes('order-summary') ||
					url.href.includes('shipping-method')
			);

			await expect(
				page.getByText(variableRateOptionName).first()
			).toBeVisible();
		});
	}
);

test(
	'Eligibility — priority fallback when default is not eligible for the order type',
	{tag: ['@LPD-85008']},
	async ({
		accountsPage,
		apiHelpers,
		checkoutPage,
		commerceAdminChannelDetailsPage,
		commerceAdminChannelsPage,
		commerceMiniCartPage,
		editAccountChannelDefaultsPage,
		editAccountPage,
		page,
	}) => {
		const {account, buyerUser} = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		const orderType1 =
			await apiHelpers.headlessCommerceAdminOrder.postOrderType({
				active: true,
				name: {en_US: `OrderType1 ${getRandomString()}`},
			});
		const orderType2 =
			await apiHelpers.headlessCommerceAdminOrder.postOrderType({
				active: true,
				name: {en_US: `OrderType2 ${getRandomString()}`},
			});

		const testOptionName = `Test Shipping Option ${getRandomString()}`;

		await test.step('Add Test Shipping Option (Flat Rate, priority 3)', async () => {
			await commerceAdminChannelsPage.goto();

			await (
				await commerceAdminChannelsPage.channelsTableRowLink(
					channel.name
				)
			).click();

			await commerceAdminChannelDetailsPage.addFlatRateShippingOption(
				testOptionName,
				'10',
				'',
				'3'
			);
		});

		await test.step('Link order types to shipping options', async () => {
			await (
				await commerceAdminChannelDetailsPage.generalCommerceAdminChannelTableLink(
					'Flat Rate'
				)
			).click();

			await commerceAdminChannelDetailsPage.setEntryEligibility(
				'Specific Order Types',
				orderType1.name.en_US,
				'Shipping Methods',
				'Standard Delivery'
			);

			await (
				await commerceAdminChannelDetailsPage.generalCommerceAdminChannelTableLink(
					'Flat Rate'
				)
			).click();

			await commerceAdminChannelDetailsPage.setEntryEligibility(
				'Specific Order Types',
				orderType1.name.en_US,
				'Shipping Methods',
				testOptionName
			);

			await (
				await commerceAdminChannelDetailsPage.generalCommerceAdminChannelTableLink(
					'Flat Rate'
				)
			).click();

			await commerceAdminChannelDetailsPage.setEntryEligibility(
				'Specific Order Types',
				orderType2.name.en_US,
				'Shipping Methods',
				'Expedited Delivery'
			);
		});

		await test.step('Open the account Channel Defaults tab', async () => {
			await accountsPage.gotoAccountAdmin();

			await (
				await accountsPage.accountsTable.cellLink(account.name)
			).click();
			await editAccountPage.channelDefaultsLink.click();
		});

		await test.step('Set Flat Rate / Expedited Delivery as default for the account', async () => {
			await (
				await editAccountChannelDefaultsPage.defaultShippingOptionsTableRowAction(
					'Edit',
					channel.name
				)
			).click();

			await editAccountChannelDefaultsPage
				.modalOptionCheckbox('Flat Rate / Expedited Delivery')
				.check();

			await editAccountChannelDefaultsPage.modalSaveButton.click();

			await expect(
				editAccountChannelDefaultsPage.modalIframe
			).toHaveCount(0);
		});

		await test.step('Buyer checks out with Order Type 1 — falls back to Test Shipping Option', async () => {
			await performUserSwitch(page, buyerUser.alternateName);

			await page.goto(`/web${site.friendlyUrlPath}/catalog`);

			await commerceMiniCartPage.quickAddToCart('MIN55861');
			await commerceMiniCartPage.submitButton.click();

			await checkoutPage.addAddress({
				city: 'Test City',
				countryLabel: 'United States',
				name: 'Address Name',
				regionLabel: 'Florida',
				street: 'Test Address',
				zip: '12345',
			});

			await checkoutPage.continueButton.click();

			await expect(page.getByText(testOptionName).first()).toBeVisible();
			await expect(
				page.getByText('Expedited Delivery').first()
			).not.toBeVisible();
		});
	}
);

test(
	'Lifecycle — set, edit, and revert the default account shipping option',
	{tag: ['@LPD-85008']},
	async ({
		accountsPage,
		apiHelpers,
		editAccountChannelDefaultsPage,
		editAccountPage,
	}) => {
		const {account} = await createAccountWithBuyerUser(apiHelpers, site.id);

		await test.step('Open the account Channel Defaults tab', async () => {
			await accountsPage.gotoAccountAdmin();

			await (
				await accountsPage.accountsTable.cellLink(account.name)
			).click();
			await editAccountPage.channelDefaultsLink.click();
		});

		await test.step('Set Flat Rate / Expedited Delivery as default', async () => {
			await (
				await editAccountChannelDefaultsPage.defaultShippingOptionsTableRowAction(
					'Edit',
					channel.name
				)
			).click();

			await editAccountChannelDefaultsPage
				.modalOptionCheckbox('Flat Rate / Expedited Delivery')
				.check();

			await editAccountChannelDefaultsPage.modalSaveButton.click();

			await expect(
				editAccountChannelDefaultsPage.modalIframe
			).toHaveCount(0);
			await expect(
				editAccountChannelDefaultsPage.defaultShippingOptionsTable.getByText(
					'Expedited Delivery'
				)
			).toBeVisible();
		});

		await test.step('Switch to Flat Rate / Standard Delivery', async () => {
			await (
				await editAccountChannelDefaultsPage.defaultShippingOptionsTableRowAction(
					'Edit',
					channel.name
				)
			).click();

			await editAccountChannelDefaultsPage
				.modalOptionCheckbox('Flat Rate / Standard Delivery')
				.check();

			await editAccountChannelDefaultsPage.modalSaveButton.click();

			await expect(
				editAccountChannelDefaultsPage.modalIframe
			).toHaveCount(0);
			await expect(
				editAccountChannelDefaultsPage.defaultShippingOptionsTable.getByText(
					'Standard Delivery'
				)
			).toBeVisible();
		});

		await test.step('Revert to Use Priority Settings', async () => {
			await (
				await editAccountChannelDefaultsPage.defaultShippingOptionsTableRowAction(
					'Edit',
					channel.name
				)
			).click();

			await editAccountChannelDefaultsPage
				.modalOptionCheckbox('Use Priority Settings')
				.check();

			await editAccountChannelDefaultsPage.modalSaveButton.click();

			await expect(
				editAccountChannelDefaultsPage.modalIframe
			).toHaveCount(0);
			await expect(
				editAccountChannelDefaultsPage.usePrioritySettingsCell
			).toBeVisible();
		});
	}
);

test(
	'UI presentation — list is sorted, "Use Priority Settings" is present, and default pre-fills at checkout',
	{tag: ['@LPD-85008']},
	async ({
		accountsPage,
		apiHelpers,
		checkoutPage,
		commerceMiniCartPage,
		editAccountChannelDefaultsPage,
		editAccountPage,
		page,
	}) => {
		const {account, buyerUser} = await createAccountWithBuyerUser(
			apiHelpers,
			site.id
		);

		await test.step('Open the account Channel Defaults tab', async () => {
			await accountsPage.gotoAccountAdmin();

			await (
				await accountsPage.accountsTable.cellLink(account.name)
			).click();
			await editAccountPage.channelDefaultsLink.click();
		});

		await test.step('Modal lists shipping options sorted alphanumerically', async () => {
			await (
				await editAccountChannelDefaultsPage.defaultShippingOptionsTableRowAction(
					'Edit',
					channel.name
				)
			).click();

			await expect(
				editAccountChannelDefaultsPage.modalOptionCheckbox(
					'Use Priority Settings'
				)
			).toBeVisible();

			const labels = (
				await editAccountChannelDefaultsPage.modalRadioLabels.allInnerTexts()
			).map((text) => text.trim());

			expect(labels[0]).toBe('Use Priority Settings');

			const rest = labels.slice(1);

			expect(rest).toEqual([...rest].sort());

			await editAccountChannelDefaultsPage
				.modalOptionCheckbox('Flat Rate / Standard Delivery')
				.check();
			await editAccountChannelDefaultsPage.modalSaveButton.click();

			await expect(
				editAccountChannelDefaultsPage.modalIframe
			).toHaveCount(0);
		});

		await test.step('Buyer sees the default pre-selected at checkout', async () => {
			await performUserSwitch(page, buyerUser.alternateName);

			await page.goto(`/web${site.friendlyUrlPath}/catalog`);

			await commerceMiniCartPage.quickAddToCart('MIN55861');
			await commerceMiniCartPage.submitButton.click();

			await checkoutPage.addAddress({
				city: 'Test City',
				countryLabel: 'United States',
				name: 'Address Name',
				regionLabel: 'Florida',
				street: 'Test Address',
				zip: '12345',
			});

			await checkoutPage.continueButton.click();

			await expect(
				page.getByText('Standard Delivery').first()
			).toBeVisible();
		});
	}
);

test(
	'Deleting a shipping option that is linked as default reverts the account default to Use Priority Settings',
	{tag: ['@LPD-85008']},
	async ({
		accountsPage,
		apiHelpers,
		commerceAdminChannelDetailsPage,
		commerceAdminChannelsPage,
		editAccountChannelDefaultsPage,
		editAccountPage,
		page,
	}) => {
		const optionName = 'OptionToDelete' + getRandomString();

		const {account} = await createAccountWithBuyerUser(apiHelpers, site.id);

		const openAccountChannelDefaults = async () => {
			await accountsPage.gotoAccountAdmin();

			await (
				await accountsPage.accountsTable.cellLink(account.name)
			).click();
			await editAccountPage.channelDefaultsLink.click();
		};

		await test.step('Add a custom Flat Rate shipping option to the channel', async () => {
			await commerceAdminChannelsPage.goto();

			await (
				await commerceAdminChannelsPage.channelsTableRowLink(
					channel.name
				)
			).click();

			await commerceAdminChannelDetailsPage.addFlatRateShippingOption(
				optionName,
				'5'
			);
		});

		await test.step('Set Flat Rate / <custom option> as the account default', async () => {
			await openAccountChannelDefaults();

			await (
				await editAccountChannelDefaultsPage.defaultShippingOptionsTableRowAction(
					'Edit',
					channel.name
				)
			).click();

			await editAccountChannelDefaultsPage
				.modalOptionCheckbox(`Flat Rate / ${optionName}`)
				.check();

			await editAccountChannelDefaultsPage.modalSaveButton.click();

			await expect(
				editAccountChannelDefaultsPage.modalIframe
			).toHaveCount(0);
			await expect(
				editAccountChannelDefaultsPage.defaultShippingOptionsTable.getByText(
					optionName
				)
			).toBeVisible();
		});

		await test.step('Delete the underlying shipping option from the channel', async () => {
			await commerceAdminChannelsPage.goto();

			await (
				await commerceAdminChannelsPage.channelsTableRowLink(
					channel.name
				)
			).click();

			await (
				await commerceAdminChannelDetailsPage.generalCommerceAdminChannelTableLink(
					'Flat Rate'
				)
			).click();
			await (
				await commerceAdminChannelDetailsPage.shippingOptionsTab(
					'Shipping Methods'
				)
			).click();
			await (
				await commerceAdminChannelDetailsPage.sidePanelFrameActionsButton(
					'Shipping Methods',
					optionName
				)
			).click();

			page.once('dialog', (dialog) => dialog.accept());

			await (
				await commerceAdminChannelDetailsPage.sidePanelFrameDeleteMenuItem(
					'Shipping Methods'
				)
			).click();

			await expect(
				(
					await commerceAdminChannelDetailsPage.sidePanelFrame(
						'Shipping Methods'
					)
				).getByText(optionName)
			).toHaveCount(0);
		});

		await test.step('Account default reverts to Use Priority Settings', async () => {
			await openAccountChannelDefaults();

			await expect(
				editAccountChannelDefaultsPage.usePrioritySettingsCell
			).toBeVisible();
			await expect(
				editAccountChannelDefaultsPage.defaultShippingOptionsTable.getByText(
					optionName
				)
			).toHaveCount(0);
		});
	}
);

test(
	'Channel-level shipping method toggle propagates to the account default Active column',
	{tag: ['@LPD-85008']},
	async ({
		accountsPage,
		apiHelpers,
		commerceAdminChannelDetailsPage,
		commerceAdminChannelsPage,
		editAccountChannelDefaultsPage,
		editAccountPage,
	}) => {
		const {account} = await createAccountWithBuyerUser(apiHelpers, site.id);

		const openAccountChannelDefaults = async () => {
			await accountsPage.gotoAccountAdmin();

			await (
				await accountsPage.accountsTable.cellLink(account.name)
			).click();
			await editAccountPage.channelDefaultsLink.click();
		};

		await test.step('Set Flat Rate / Standard Delivery as default', async () => {
			await openAccountChannelDefaults();

			await (
				await editAccountChannelDefaultsPage.defaultShippingOptionsTableRowAction(
					'Edit',
					channel.name
				)
			).click();

			await editAccountChannelDefaultsPage
				.modalOptionCheckbox('Flat Rate / Standard Delivery')
				.check();

			await editAccountChannelDefaultsPage.modalSaveButton.click();

			await expect(
				editAccountChannelDefaultsPage.modalIframe
			).toHaveCount(0);

			await expect(
				await editAccountChannelDefaultsPage.defaultShippingOptionsTableRowActiveCell(
					channel.name
				)
			).toHaveText('Yes');
		});

		try {
			await test.step('Disable Flat Rate at the channel — Active column flips to No', async () => {
				await commerceAdminChannelsPage.goto();

				await (
					await commerceAdminChannelsPage.channelsTableRowLink(
						channel.name
					)
				).click();

				await commerceAdminChannelDetailsPage.deactivateChannelConfiguration(
					'Flat Rate',
					'Shipping Methods'
				);

				await openAccountChannelDefaults();

				await expect(
					await editAccountChannelDefaultsPage.defaultShippingOptionsTableRowActiveCell(
						channel.name
					)
				).toHaveText('No');
			});
		}
		finally {
			await test.step('Re-enable Flat Rate at the channel — Active column flips back to Yes', async () => {
				await commerceAdminChannelsPage.goto();

				await (
					await commerceAdminChannelsPage.channelsTableRowLink(
						channel.name
					)
				).click();

				await commerceAdminChannelDetailsPage.activateChannelConfiguration(
					'Flat Rate',
					'Shipping Methods'
				);

				await openAccountChannelDefaults();

				await expect(
					await editAccountChannelDefaultsPage.defaultShippingOptionsTableRowActiveCell(
						channel.name
					)
				).toHaveText('Yes');
			});
		}
	}
);
