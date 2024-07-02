/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {CommerceCartPage} from '../pages/commerce/commerce-cart-content-web/commerceCartPage';
import {CommerceCartSummaryPage} from '../pages/commerce/commerce-cart-content-web/commerceCartSummaryPage';
import {CheckoutPage} from '../pages/commerce/commerce-checkout-web/checkoutPage';
import {PendingOrdersPage} from '../pages/commerce/commerce-order-content-web/pendingOrdersPage';
import {PlacedOrdersPage} from '../pages/commerce/commerce-order-content-web/placedOrdersPage';
import {SpecificationFacetsPage} from '../pages/commerce/commerce-product-content-search-web/specificationFacetsPage';
import {ProductDetailsPage} from '../pages/commerce/commerce-product-content-web/productDetailsPage';
import {AttachmentsPage} from '../pages/commerce/commerce-product-definitions-web/attachmentsPage';
import {CommerceProductSpecificationsPage} from '../pages/commerce/commerce-product-options-web/commerceProductSpecificationsPage';
import {CommerceThemeMiniumCatalogPage} from '../pages/commerce/commerce-theme-minium/commerceThemeMiniumCatalogPage';
import {CommerceThemeMiniumPage} from '../pages/commerce/commerce-theme-minium/commerceThemeMiniumPage';
import {CommerceAccountManagementPage} from '../pages/commerce/commerceAccountManagementPage';
import {CommerceAdminChannelDetailsCountriesPage} from '../pages/commerce/commerceAdminChannelDetailsCountriesPage';
import {CommerceAdminChannelDetailsPage} from '../pages/commerce/commerceAdminChannelDetailsPage';
import {CommerceAdminChannelsPage} from '../pages/commerce/commerceAdminChannelsPage';
import {CommerceAdminDiscountDetailsPage} from '../pages/commerce/commerceAdminDiscountDetailsPage';
import {CommerceAdminDiscountsPage} from '../pages/commerce/commerceAdminDiscountsPage';
import {CommerceAdminOrderDetailsPage} from '../pages/commerce/commerceAdminOrderDetailsPage';
import {CommerceAdminOrdersPage} from '../pages/commerce/commerceAdminOrdersPage';
import {CommerceAdminProductDetailsDiagramPage} from '../pages/commerce/commerceAdminProductDetailsDiagramPage';
import {CommerceAdminProductDetailsPage} from '../pages/commerce/commerceAdminProductDetailsPage';
import {CommerceAdminProductDetailsProductOptionsPage} from '../pages/commerce/commerceAdminProductDetailsProductOptionsPage';
import {CommerceAdminProductDetailsProductRelationsPage} from '../pages/commerce/commerceAdminProductDetailsProductRelationsPage';
import {CommerceAdminProductPage} from '../pages/commerce/commerceAdminProductPage';
import {CommerceCatalogSystemSettingsPage} from '../pages/commerce/commerceCatalogSystemSettingsPage';
import {CommerceChannelDefaultsPage} from '../pages/commerce/commerceChannelDefaultsPage';
import {CommerceLayoutsPage} from '../pages/commerce/commerceLayoutsPage';
import {CommerceMiniCartPage} from '../pages/commerce/commerceMiniCartPage';
import {CommercePaymentsPage} from '../pages/commerce/commercePaymentsPage';

const commercePagesTest = test.extend<{
	attachmentsPage: AttachmentsPage;
	checkoutPage: CheckoutPage;
	commerceAccountManagementPage: CommerceAccountManagementPage;
	commerceAdminChannelDetailsCountriesPage: CommerceAdminChannelDetailsCountriesPage;
	commerceAdminChannelDetailsPage: CommerceAdminChannelDetailsPage;
	commerceAdminChannelsPage: CommerceAdminChannelsPage;
	commerceAdminDiscountDetailsPage: CommerceAdminDiscountDetailsPage;
	commerceAdminDiscountsPage: CommerceAdminDiscountsPage;
	commerceAdminOrderDetailsPage: CommerceAdminOrderDetailsPage;
	commerceAdminOrdersPage: CommerceAdminOrdersPage;
	commerceAdminProductDetailsDiagramPage: CommerceAdminProductDetailsDiagramPage;
	commerceAdminProductDetailsPage: CommerceAdminProductDetailsPage;
	commerceAdminProductDetailsProductOptionsPage: CommerceAdminProductDetailsProductOptionsPage;
	commerceAdminProductDetailsProductRelationsPage: CommerceAdminProductDetailsProductRelationsPage;
	commerceAdminProductPage: CommerceAdminProductPage;
	commerceCartPage: CommerceCartPage;
	commerceCartSummaryPage: CommerceCartSummaryPage;
	commerceCatalogSystemSettingsPage: CommerceCatalogSystemSettingsPage;
	commerceChannelDefaultsPage: CommerceChannelDefaultsPage;
	commerceLayoutsPage: CommerceLayoutsPage;
	commerceMiniCartPage: CommerceMiniCartPage;
	commercePaymentsPage: CommercePaymentsPage;
	commerceThemeMiniumCatalogPage: CommerceThemeMiniumCatalogPage;
	commerceThemeMiniumPage: CommerceThemeMiniumPage;
	pendingOrdersPage: PendingOrdersPage;
	placedOrdersPage: PlacedOrdersPage;
	productDetailsPage: ProductDetailsPage;
	specificationFacetsPage: SpecificationFacetsPage;
}>({
	attachmentsPage: async ({page}, use) => {
		await use(new AttachmentsPage(page));
	},
	checkoutPage: async ({page}, use) => {
		await use(new CheckoutPage(page));
	},
	commerceAccountManagementPage: async ({page}, use) => {
		await use(new CommerceAccountManagementPage(page));
	},
	commerceAdminChannelDetailsCountriesPage: async ({page}, use) => {
		await use(new CommerceAdminChannelDetailsCountriesPage(page));
	},
	commerceAdminChannelDetailsPage: async ({page}, use) => {
		await use(new CommerceAdminChannelDetailsPage(page));
	},
	commerceAdminChannelsPage: async ({page}, use) => {
		await use(new CommerceAdminChannelsPage(page));
	},
	commerceAdminDiscountDetailsPage: async ({page}, use) => {
		await use(new CommerceAdminDiscountDetailsPage(page));
	},
	commerceAdminDiscountsPage: async ({page}, use) => {
		await use(new CommerceAdminDiscountsPage(page));
	},
	commerceAdminOrderDetailsPage: async ({page}, use) => {
		await use(new CommerceAdminOrderDetailsPage(page));
	},
	commerceAdminOrdersPage: async ({page}, use) => {
		await use(new CommerceAdminOrdersPage(page));
	},
	commerceAdminProductDetailsDiagramPage: async ({page}, use) => {
		await use(new CommerceAdminProductDetailsDiagramPage(page));
	},
	commerceAdminProductDetailsPage: async ({page}, use) => {
		await use(new CommerceAdminProductDetailsPage(page));
	},
	commerceAdminProductDetailsProductOptionsPage: async ({page}, use) => {
		await use(new CommerceAdminProductDetailsProductOptionsPage(page));
	},
	commerceAdminProductDetailsProductRelationsPage: async ({page}, use) => {
		await use(new CommerceAdminProductDetailsProductRelationsPage(page));
	},
	commerceAdminProductPage: async ({page}, use) => {
		await use(new CommerceAdminProductPage(page));
	},
	commerceCartPage: async ({page}, use) => {
		await use(new CommerceCartPage(page));
	},
	commerceCartSummaryPage: async ({page}, use) => {
		await use(new CommerceCartSummaryPage(page));
	},
	commerceCatalogSystemSettingsPage: async ({page}, use) => {
		await use(new CommerceCatalogSystemSettingsPage(page));
	},
	commerceChannelDefaultsPage: async ({page}, use) => {
		await use(new CommerceChannelDefaultsPage(page));
	},
	commerceLayoutsPage: async ({page}, use) => {
		await use(new CommerceLayoutsPage(page));
	},
	commerceMiniCartPage: async ({page}, use) => {
		await use(new CommerceMiniCartPage(page));
	},
	commercePaymentsPage: async ({page}, use) => {
		await use(new CommercePaymentsPage(page));
	},
	commerceProductSpecificationsPage: async ({page}, use) => {
		await use(new CommerceProductSpecificationsPage(page));
	},
	commerceThemeMiniumCatalogPage: async ({page}, use) => {
		await use(new CommerceThemeMiniumCatalogPage(page));
	},
	commerceThemeMiniumPage: async ({page}, use) => {
		await use(new CommerceThemeMiniumPage(page));
	},
	pendingOrdersPage: async ({page}, use) => {
		await use(new PendingOrdersPage(page));
	},
	placedOrdersPage: async ({page}, use) => {
		await use(new PlacedOrdersPage(page));
	},
	productDetailsPage: async ({page}, use) => {
		await use(new ProductDetailsPage(page));
	},
	specificationFacetsPage: async ({page}, use) => {
		await use(new SpecificationFacetsPage(page));
	},
});

export {commercePagesTest};
