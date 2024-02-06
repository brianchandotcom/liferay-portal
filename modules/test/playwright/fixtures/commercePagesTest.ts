/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {test} from '@playwright/test';

import {SpecificationFacetsPage} from '../pages/commerce/commerce-product-content-search-web/specificationFacetsPage';
import {AttachmentsPage} from '../pages/commerce/commerce-product-definitions-web/attachmentsPage';
import {CommerceAdminOrderDetailsPage} from '../pages/commerce/commerceAdminOrderDetailsPage';
import {CommerceAdminOrdersPage} from '../pages/commerce/commerceAdminOrdersPage';
import {CommerceLayoutsPage} from '../pages/commerce/commerceLayoutsPage';
import {CommerceMiniCartPage} from '../pages/commerce/commerceMiniCartPage';
import {CommerceProductAdminPage} from '../pages/commerce/commerceProductAdminPage';

const commercePagesTest = test.extend<{
	attachmentsPage: AttachmentsPage;
	commerceAdminOrderDetailsPage: CommerceAdminOrderDetailsPage;
	commerceAdminOrdersPage: CommerceAdminOrdersPage;
	commerceLayoutsPage: CommerceLayoutsPage;
	commerceMiniCartPage: CommerceMiniCartPage;
	commerceProductAdminPage: CommerceProductAdminPage;
	specificationFacetsPage: SpecificationFacetsPage;
}>({
	attachmentsPage: async ({page}, use) => {
		await use(new AttachmentsPage(page));
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
	commerceProductAdminPage: async ({page}, use) => {
		await use(new CommerceProductAdminPage(page));
	},
	specificationFacetsPage: async ({page}, use) => {
		await use(new SpecificationFacetsPage(page));
	},
});

export {commercePagesTest};
