/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../fixtures/systemSettingsPageTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-203351': true,
	}),
	loginTest(),
	systemSettingsPageTest
);

test('LPD-25926 Display page template edit mode works with Speedwell theme', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	page,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	const displayPageTemplateName = getRandomString();

	await applicationsMenuPage.goToSite(site.name);

	await commerceLayoutsPage.goToDisplayPageTemplates();
	await commerceLayoutsPage.createDisplayPageTemplate(
		displayPageTemplateName,
		'Blogs Entry',
		site.name
	);
	await commerceLayoutsPage.addFragment('Heading');

	await expect(page.getByText('Heading Example')).toBeVisible();

	await commerceLayoutsPage.publishButton.click();
	await commerceLayoutsPage.configureDisplayPageTemplateTheme(
		'Select Speedwell By Liferay, Inc.'
	);

	await expect(
		page.getByText('Success:The page was updated successfully.')
	).toBeVisible();

	await commerceLayoutsPage.backLink.click();

	await commerceLayoutsPage
		.displayPageTemplateLink(displayPageTemplateName)
		.click();

	await expect(page.getByText('Heading Example')).toBeVisible();
});

test('LPD-33439 Default order display page template is accessible via friendly URL', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	page,
	systemSettingsPage,
}) => {
	try {
		await systemSettingsPage.goToSystemSetting(
			'Feature Flags',
			'Developer'
		);

		await page.getByLabel('COMMERCE-9410').click();

		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: getRandomString(),
			type: 'person',
		});

		apiHelpers.data.push({id: account.id, type: 'account'});

		const site = await apiHelpers.headlessSite.createSite({
			name: getRandomString(),
		});

		apiHelpers.data.push({id: site.id, type: 'site'});

		await applicationsMenuPage.goToSite(site.name);

		await commerceLayoutsPage.goToDisplayPageTemplates();
		await commerceLayoutsPage.createDisplayPageTemplate(
			getRandomString(),
			'Order',
			site.name
		);
		await commerceLayoutsPage.addFragment('Heading');

		await expect(page.getByText('Heading Example')).toBeVisible();

		await commerceLayoutsPage.publishButton.click();
		await commerceLayoutsPage.moreActionsButton.click();
		await commerceLayoutsPage.markAsDefaultMenuItem.click();

		await waitForSuccessAlert(page);

		await expect(
			commerceLayoutsPage.defaultDisplayPageTemplateIcon
		).toBeVisible();

		const channel =
			await apiHelpers.headlessCommerceAdminChannel.postChannel({
				siteGroupId: site.id,
			});

		const catalog =
			await apiHelpers.headlessCommerceAdminCatalog.postCatalog();

		const product =
			await apiHelpers.headlessCommerceAdminCatalog.postProduct({
				catalogId: catalog.id,
			});

		const sku = product.skus[0];

		const cart = await apiHelpers.headlessCommerceDeliveryCart.postCart(
			{
				accountId: account.id,
				cartItems: [
					{
						quantity: 1,
						skuId: sku.id,
					},
				],
			},
			channel.id
		);

		await page.goto(
			liferayConfig.environment.baseUrl +
				`/web/${site.name}/order/${cart.id}`
		);

		await expect(page.getByText('Heading Example')).toBeVisible();
	}
	finally {
		await systemSettingsPage.goToSystemSetting(
			'Feature Flags',
			'Developer'
		);

		if (await page.getByLabel('COMMERCE-9410').isChecked()) {
			await page.getByLabel('COMMERCE-9410').click();
		}
	}
});

test('LPD-32227 Order info box fragment configuration', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	page,
	systemSettingsPage,
}) => {
	try {
		await systemSettingsPage.goToSystemSetting(
			'Feature Flags',
			'Developer'
		);

		await page.getByLabel('COMMERCE-9410').click();

		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: getRandomString(),
			type: 'person',
		});

		apiHelpers.data.push({id: account.id, type: 'account'});

		const site = await apiHelpers.headlessSite.createSite({
			name: getRandomString(),
		});

		apiHelpers.data.push({id: site.id, type: 'site'});

		await applicationsMenuPage.goToSite(site.name);

		await commerceLayoutsPage.goToDisplayPageTemplates();
		await commerceLayoutsPage.createDisplayPageTemplate(
			getRandomString(),
			'Order',
			site.name
		);
		await commerceLayoutsPage.addFragment('Info Box', 'Order');
		await commerceLayoutsPage.infoBoxReadOnlyToggle.uncheck();

		await expect(
			page.getByText(
				'The info box component is not correctly configured.'
			)
		).toBeVisible();

		await commerceLayoutsPage.infoBoxFieldSelect.selectOption(
			'purchaseOrderNumber'
		);

		await expect(
			page.getByText(
				'The info box component is not correctly configured.'
			)
		).toBeHidden();

		await commerceLayoutsPage.infoBoxLabelInput.fill('PON');
		await commerceLayoutsPage.publishButton.click();

		await waitForSuccessAlert(
			page,
			'The display page template was published successfully.'
		);

		await commerceLayoutsPage.moreActionsButton.click();
		await commerceLayoutsPage.markAsDefaultMenuItem.click();

		await waitForSuccessAlert(page);

		await expect(
			commerceLayoutsPage.defaultDisplayPageTemplateIcon
		).toBeVisible();

		const channel =
			await apiHelpers.headlessCommerceAdminChannel.postChannel({
				siteGroupId: site.id,
			});

		const catalog =
			await apiHelpers.headlessCommerceAdminCatalog.postCatalog();

		const product =
			await apiHelpers.headlessCommerceAdminCatalog.postProduct({
				catalogId: catalog.id,
			});

		const sku = product.skus[0];

		const cart = await apiHelpers.headlessCommerceDeliveryCart.postCart(
			{
				accountId: account.id,
				cartItems: [
					{
						quantity: 1,
						skuId: sku.id,
					},
				],
			},
			channel.id
		);

		await page.goto(
			liferayConfig.environment.baseUrl +
				`/web/${site.name}/order/${cart.id}`
		);

		await expect(page.getByText('PON')).toBeVisible();

		await commerceLayoutsPage.infoBoxButton('PON').click();
		await commerceLayoutsPage.inputTextbox('PON').fill('testPON');
		await commerceLayoutsPage.saveButton.click();

		await expect(page.getByText('testPON')).toBeVisible();

		await commerceLayoutsPage.infoBoxButton('PON').click();
		await commerceLayoutsPage.inputTextbox('PON').fill('testPONEdited');
		await commerceLayoutsPage.saveButton.click();

		await expect(page.getByText('testPONEdited')).toBeVisible();

		await commerceLayoutsPage.goToDisplayPageTemplates();
		await commerceLayoutsPage.moreActionsButton.click();
		await commerceLayoutsPage.editMenuItem.click();
		await commerceLayoutsPage.firstFragment.click();
		await commerceLayoutsPage.infoBoxFieldSelect.selectOption(
			'accountInfo'
		);

		await expect(
			page.getByText(
				'The info box component is not correctly configured.'
			)
		).toBeVisible();

		await commerceLayoutsPage.infoBoxReadOnlyToggle.check();

		await expect(
			page.getByText(
				'The info box component is not correctly configured.'
			)
		).toBeHidden();

		await commerceLayoutsPage.infoBoxLabelInput.fill('Account Info');
		await commerceLayoutsPage.publishButton.click();

		await waitForSuccessAlert(
			page,
			'The display page template was published successfully.'
		);

		await page.goto(
			liferayConfig.environment.baseUrl +
				`/web/${site.name}/order/${cart.id}`
		);

		await expect(page.getByText('Account Info')).toBeVisible();
		await expect(page.getByText(account.name)).toBeVisible();
		await expect(page.getByText(String(account.id))).toBeVisible();
		await expect(commerceLayoutsPage.infoBoxButton('PON')).toBeHidden();
	}
	finally {
		await systemSettingsPage.goToSystemSetting(
			'Feature Flags',
			'Developer'
		);

		if (await page.getByLabel('COMMERCE-9410').isChecked()) {
			await page.getByLabel('COMMERCE-9410').click();
		}
	}
});

test('LPD-32232 Edit Requested Delivery Date in Open Order Details', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	page,
	systemSettingsPage,
}) => {
	try {
		await systemSettingsPage.goToSystemSetting(
			'Feature Flags',
			'Developer'
		);

		await page.getByLabel('COMMERCE-9410').click();

		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: getRandomString(),
			type: 'person',
		});

		apiHelpers.data.push({id: account.id, type: 'account'});

		const site = await apiHelpers.headlessSite.createSite({
			name: getRandomString(),
		});

		apiHelpers.data.push({id: site.id, type: 'site'});

		await applicationsMenuPage.goToSite(site.name);

		await commerceLayoutsPage.goToDisplayPageTemplates();
		await commerceLayoutsPage.createDisplayPageTemplate(
			getRandomString(),
			'Order',
			site.name
		);
		await commerceLayoutsPage.addFragment('Info Box', 'Order');
		await commerceLayoutsPage.infoBoxReadOnlyToggle.uncheck();

		await expect(
			page.getByText(
				'The info box component is not correctly configured.'
			)
		).toBeVisible();

		await commerceLayoutsPage.infoBoxFieldSelect.selectOption(
			'requestedDeliveryDate'
		);

		await expect(
			page.getByText(
				'The info box component is not correctly configured.'
			)
		).toBeHidden();

		await commerceLayoutsPage.infoBoxLabelInput.fill(
			'Requested Delivery Date'
		);
		await commerceLayoutsPage.publishButton.click();

		await waitForSuccessAlert(
			page,
			'The display page template was published successfully.'
		);

		await commerceLayoutsPage.moreActionsButton.click();
		await commerceLayoutsPage.markAsDefaultMenuItem.click();

		await waitForSuccessAlert(page);

		await expect(
			commerceLayoutsPage.defaultDisplayPageTemplateIcon
		).toBeVisible();

		const channel =
			await apiHelpers.headlessCommerceAdminChannel.postChannel({
				siteGroupId: site.id,
			});

		const catalog =
			await apiHelpers.headlessCommerceAdminCatalog.postCatalog();

		const product =
			await apiHelpers.headlessCommerceAdminCatalog.postProduct({
				catalogId: catalog.id,
			});

		const sku = product.skus[0];

		const cart = await apiHelpers.headlessCommerceDeliveryCart.postCart(
			{
				accountId: account.id,
				cartItems: [
					{
						quantity: 1,
						skuId: sku.id,
					},
				],
			},
			channel.id
		);

		await page.goto(
			liferayConfig.environment.baseUrl +
				`/web/${site.name}/order/${cart.id}`
		);

		await expect(page.getByText('Requested Delivery Date')).toBeVisible();

		await commerceLayoutsPage
			.infoBoxButton('Requested Delivery Date')
			.click();
		await commerceLayoutsPage
			.inputTextbox('Requested Delivery Date')
			.fill('2024-09-11');
		await commerceLayoutsPage.saveButton.click();

		await expect(page.getByText('9/11/24', {exact: true})).toBeVisible();

		await commerceLayoutsPage
			.infoBoxButton('Requested Delivery Date')
			.click();
		await commerceLayoutsPage
			.inputTextbox('Requested Delivery Date')
			.fill('2024-09-13');
		await commerceLayoutsPage.saveButton.click();

		await expect(page.getByText('9/13/24', {exact: true})).toBeVisible();

		await apiHelpers.headlessCommerceDeliveryCart.checkoutCart(cart.id);

		await page.reload();

		await expect(
			commerceLayoutsPage.infoBoxButton('Requested Delivery Date')
		).toBeHidden();
	}
	finally {
		await systemSettingsPage.goToSystemSetting(
			'Feature Flags',
			'Developer'
		);

		if (await page.getByLabel('COMMERCE-9410').isChecked()) {
			await page.getByLabel('COMMERCE-9410').click();
		}
	}
});
