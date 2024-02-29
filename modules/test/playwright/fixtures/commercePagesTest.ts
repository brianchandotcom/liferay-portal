/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {test} from '@playwright/test';

import {PendingOrdersPage} from '../pages/commerce/commerce-order-content-web/pendingOrdersPage';
import {SpecificationFacetsPage} from '../pages/commerce/commerce-product-content-search-web/specificationFacetsPage';
import {AttachmentsPage} from '../pages/commerce/commerce-product-definitions-web/attachmentsPage';
import {CommerceAdminChannelDetailsCountriesPage} from '../pages/commerce/commerceAdminChannelDetailsCountriesPage';
import {CommerceAdminChannelDetailsPage} from '../pages/commerce/commerceAdminChannelDetailsPage';
import {CommerceAdminChannelsPage} from '../pages/commerce/commerceAdminChannelsPage';
import {CommerceAdminOrderDetailsPage} from '../pages/commerce/commerceAdminOrderDetailsPage';
import {CommerceAdminOrdersPage} from '../pages/commerce/commerceAdminOrdersPage';
import {CommerceLayoutsPage} from '../pages/commerce/commerceLayoutsPage';
import {CommerceMiniCartPage} from '../pages/commerce/commerceMiniCartPage';
import {CommerceProductAdminDetailsPage} from '../pages/commerce/commerceProductAdminDetailsPage';
import {CommerceProductAdminDetailsProductRelationsPage} from '../pages/commerce/commerceProductAdminDetailsProductRelationsPage';
import {CommerceProductAdminPage} from '../pages/commerce/commerceProductAdminPage';

const commercePagesTest = test.extend<{
	attachmentsPage: AttachmentsPage;
	commerceAdminChannelDetailsCountriesPage: CommerceAdminChannelDetailsCountriesPage;
	commerceAdminChannelDetailsPage: CommerceAdminChannelDetailsPage;
	commerceAdminChannelsPage: CommerceAdminChannelsPage;
	commerceAdminOrderDetailsPage: CommerceAdminOrderDetailsPage;
	commerceAdminOrdersPage: CommerceAdminOrdersPage;
	commerceLayoutsPage: CommerceLayoutsPage;
	commerceMiniCartPage: CommerceMiniCartPage;
	commerceProductAdminDetailsPage: CommerceProductAdminDetailsPage;
	commerceProductAdminDetailsProductRelationsPage: CommerceProductAdminDetailsProductRelationsPage;
	commerceProductAdminPage: CommerceProductAdminPage;
	pendingOrdersPage: PendingOrdersPage;
	specificationFacetsPage: SpecificationFacetsPage;
}>({
	attachmentsPage: async ({page}, use) => {
		await use(new AttachmentsPage(page));
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
	commerceLayoutsPage: async ({page}, use) => {
		await use(new CommerceLayoutsPage(page));
	},
	commerceMiniCartPage: async ({page}, use) => {
		await use(new CommerceMiniCartPage(page));
	},
	commerceProductAdminDetailsPage: async ({page}, use) => {
		await use(new CommerceProductAdminDetailsPage(page));
	},
	commerceProductAdminDetailsProductRelationsPage: async ({page}, use) => {
		await use(new CommerceProductAdminDetailsProductRelationsPage(page));
	},
	commerceProductAdminPage: async ({page}, use) => {
		await use(new CommerceProductAdminPage(page));
	},
	pendingOrdersPage: async ({page}, use) => {
		await use(new PendingOrdersPage(page));
	},
	specificationFacetsPage: async ({page}, use) => {
		await use(new SpecificationFacetsPage(page));
	},
});

export {commercePagesTest};
