/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {test} from '@playwright/test';

import {CheckoutPage} from '../pages/commerce/commerce-checkout-web/checkoutPage';
import {PendingOrdersPage} from '../pages/commerce/commerce-order-content-web/pendingOrdersPage';
import {SpecificationFacetsPage} from '../pages/commerce/commerce-product-content-search-web/specificationFacetsPage';
import {AttachmentsPage} from '../pages/commerce/commerce-product-definitions-web/attachmentsPage';
import {CommerceAdminChannelDetailsCountriesPage} from '../pages/commerce/commerceAdminChannelDetailsCountriesPage';
import {CommerceAdminChannelDetailsPage} from '../pages/commerce/commerceAdminChannelDetailsPage';
import {CommerceAdminChannelsPage} from '../pages/commerce/commerceAdminChannelsPage';
import {CommerceAdminOrderDetailsPage} from '../pages/commerce/commerceAdminOrderDetailsPage';
import {CommerceAdminOrdersPage} from '../pages/commerce/commerceAdminOrdersPage';
import {CommerceAdminProductDetailsDiagramPage} from '../pages/commerce/commerceAdminProductDetailsDiagramPage';
import {CommerceAdminProductDetailsPage} from '../pages/commerce/commerceAdminProductDetailsPage';
import {CommerceAdminProductDetailsProductRelationsPage} from '../pages/commerce/commerceAdminProductDetailsProductRelationsPage';
import {CommerceAdminProductPage} from '../pages/commerce/commerceAdminProductPage';
import {CommerceCatalogSystemSettingsPage} from '../pages/commerce/commerceCatalogSystemSettingsPage';
import {CommerceLayoutsPage} from '../pages/commerce/commerceLayoutsPage';
import {CommerceMiniCartPage} from '../pages/commerce/commerceMiniCartPage';
import {CommercePaymentsPage} from '../pages/commerce/commercePaymentsPage';

const commercePagesTest = test.extend<{
	attachmentsPage: AttachmentsPage;
	checkoutPage: CheckoutPage;
	commerceAdminChannelDetailsCountriesPage: CommerceAdminChannelDetailsCountriesPage;
	commerceAdminChannelDetailsPage: CommerceAdminChannelDetailsPage;
	commerceAdminChannelsPage: CommerceAdminChannelsPage;
	commerceAdminOrderDetailsPage: CommerceAdminOrderDetailsPage;
	commerceAdminOrdersPage: CommerceAdminOrdersPage;
	commerceAdminProductDetailsDiagramPage: CommerceAdminProductDetailsDiagramPage;
	commerceAdminProductDetailsPage: CommerceAdminProductDetailsPage;
	commerceAdminProductDetailsProductRelationsPage: CommerceAdminProductDetailsProductRelationsPage;
	commerceAdminProductPage: CommerceAdminProductPage;
	commerceCatalogSystemSettingsPage: CommerceCatalogSystemSettingsPage;
	commerceLayoutsPage: CommerceLayoutsPage;
	commerceMiniCartPage: CommerceMiniCartPage;
	commercePaymentsPage: CommercePaymentsPage;
	pendingOrdersPage: PendingOrdersPage;
	specificationFacetsPage: SpecificationFacetsPage;
}>({
	attachmentsPage: async ({page}, use) => {
		await use(new AttachmentsPage(page));
	},
	checkoutPage: async ({page}, use) => {
		await use(new CheckoutPage(page));
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
	commerceAdminProductDetailsProductRelationsPage: async ({page}, use) => {
		await use(new CommerceAdminProductDetailsProductRelationsPage(page));
	},
	commerceAdminProductPage: async ({page}, use) => {
		await use(new CommerceAdminProductPage(page));
	},
	commerceCatalogSystemSettingsPage: async ({page}, use) => {
		await use(new CommerceCatalogSystemSettingsPage(page));
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
	pendingOrdersPage: async ({page}, use) => {
		await use(new PendingOrdersPage(page));
	},
	specificationFacetsPage: async ({page}, use) => {
		await use(new SpecificationFacetsPage(page));
	},
});

export {commercePagesTest};
