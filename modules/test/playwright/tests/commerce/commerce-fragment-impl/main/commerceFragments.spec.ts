/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {displayPageTemplatesPagesTest} from '../../../../fixtures/displayPageTemplatesPagesTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {globalMenuPagesTest} from '../../../../fixtures/globalMenuPagesTest';
import {isolatedSiteTest} from '../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../../fixtures/pageEditorPagesTest';
import {clickAndExpectToBeHidden} from '../../../../utils/clickAndExpectToBeHidden';
import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../../utils/getRandomString';
import getFragmentDefinition from '../../../layout-content-page-editor-web/main/utils/getFragmentDefinition';
import getPageDefinition from '../../../layout-content-page-editor-web/main/utils/getPageDefinition';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	displayPageTemplatesPagesTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	globalMenuPagesTest,
	pageEditorPagesTest,
	isolatedSiteTest,
	loginTest()
);

test(
	'Account selector fragments are visible',
	{
		tag: [
			'@LPD-63169',
			'@LPD-63170',
			'@LPD-63171',
			'@LPD-63172',
			'@LPD-63173',
			'@LPD-63174',
			'@LPD-63175',
		],
	},
	async ({apiHelpers, globalMenuPage, page, site}) => {
		await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getFragmentDefinition({
					id: getRandomString(),
					key: 'COMMERCE_ACCOUNT_SELECTOR_FRAGMENTS-account-selector-fragment',
				}),
				getFragmentDefinition({
					id: getRandomString(),
					key: 'COMMERCE_ACCOUNT_SELECTOR_FRAGMENTS-account-selector-panel',
				}),
				getFragmentDefinition({
					id: getRandomString(),
					key: 'com.liferay.commerce.fragment.internal.renderer.CreateAccountButtonFragmentRenderer',
				}),
				getFragmentDefinition({
					id: getRandomString(),
					key: 'com.liferay.commerce.fragment.internal.renderer.CreateOrderButtonFragmentRenderer',
				}),
				getFragmentDefinition({
					id: getRandomString(),
					key: 'com.liferay.commerce.fragment.internal.renderer.AccountsDataSetFragmentRenderer',
				}),
				getFragmentDefinition({
					id: getRandomString(),
					key: 'com.liferay.commerce.fragment.internal.renderer.PendingAccountOrdersDataSetFragmentRenderer',
				}),
				getFragmentDefinition({
					id: getRandomString(),
					key: 'com.liferay.commerce.fragment.internal.renderer.AccountSelectorButtonFragmentRenderer',
				}),
			]),
			siteId: site.id,
			title: getRandomString(),
		});

		await globalMenuPage.goToSite(site.name);

		await expect(page.locator('.account-selector-container')).toHaveCount(
			1
		);
		await expect(
			page.locator('.account-selector-panel-drop-zone-container')
		).toHaveCount(1);
		await expect(
			page.locator(
				'.lfr-layout-structure-item-com-liferay-commerce-fragment-internal-renderer-createaccountbuttonfragmentrenderer'
			)
		).toHaveCount(1);
		await expect(
			page.locator(
				'.lfr-layout-structure-item-com-liferay-commerce-fragment-internal-renderer-createorderbuttonfragmentrenderer'
			)
		).toHaveCount(1);
		await expect(
			page.locator(
				'.lfr-layout-structure-item-com-liferay-commerce-fragment-internal-renderer-accountsdatasetfragmentrenderer'
			)
		).toHaveCount(1);
		await expect(
			page.locator(
				'.lfr-layout-structure-item-com-liferay-commerce-fragment-internal-renderer-pendingaccountordersdatasetfragmentrenderer'
			)
		).toHaveCount(1);
		await expect(
			page.locator(
				'.lfr-layout-structure-item-com-liferay-commerce-fragment-internal-renderer-accountselectorbuttonfragmentrenderer'
			)
		).toHaveCount(1);
	}
);

test(
	'Account selector dropdown opens and closes on click in page edit mode',
	{tag: '@LPD-92403'},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getFragmentDefinition({
					id: getRandomString(),
					key: 'COMMERCE_ACCOUNT_SELECTOR_FRAGMENTS-account-selector-fragment',
					pageElements: [
						{
							id: getRandomString(),
							pageElements: [
								getFragmentDefinition({
									id: getRandomString(),
									key: 'com.liferay.commerce.fragment.internal.renderer.AccountSelectorButtonFragmentRenderer',
								}),
							],
							type: 'FragmentDropZone',
						},
					],
				}),
			]),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		const accountSelectorContainer = page.locator(
			'.account-selector-container'
		);
		const accountSelectorCtaContainer = page.locator(
			'.account-selector-cta-container'
		);
		const accountSelectorDropdownMenu = accountSelectorContainer.locator(
			'[id$="-account-selector-dropdown-menu"]'
		);

		await expect(accountSelectorContainer).toBeVisible();

		await expect(accountSelectorDropdownMenu).toBeHidden();

		await clickAndExpectToBeVisible({
			target: accountSelectorDropdownMenu,
			trigger: accountSelectorCtaContainer,
		});

		await clickAndExpectToBeHidden({
			target: accountSelectorDropdownMenu,
			trigger: accountSelectorCtaContainer,
		});
	}
);
