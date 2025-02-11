/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {breadcrumbWidgetPagesTest} from '../../fixtures/breadcrumbWidgetPagesTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';

export const test = mergeTests(
	breadcrumbWidgetPagesTest,
	isolatedSiteTest,
	loginTest()
);

test(
	'Currently selected page in Breadcrumb widget has aria-current attribute',
	{
		tag: '@LPD-40431',
	},
	async ({breadcrumbWidgetPage, page, site}) => {
		await breadcrumbWidgetPage.addBreadcrumbPortlet(site);

		await expect(
			page.locator('.active.breadcrumb-text-truncate')
		).toHaveAttribute('aria-current', 'page');
	}
);
