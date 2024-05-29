/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import getFragmentDefinition from '../layout-content-page-editor-web/utils/getFragmentDefinition';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest()
);

test('This is a test for LPD-21554. Some page names result in 404 friendly URLs.', async ({
	apiHelpers,
	page,
}) => {
	const company = await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
		'liferay.com'
	);

	const group = await apiHelpers.jsonWebServicesGroup.getGroupByKey(
		company.companyId,
		'Guest'
	);

	// Create a page in Guest site with name matching a supported locale which
	// is not an available locale for the site

	const pageName = 'th';

	const sitePage = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getFragmentDefinition({
				id: getRandomString(),
				key: 'BASIC_COMPONENT-heading',
			}),
		]),
		siteId: group.groupId,
		title: pageName,
	});

	await page.goto(liferayConfig.environment.baseUrl);

	if (await page.getByText(pageName, {exact: true}).isVisible()) {
		await page.getByText(pageName, {exact: true}).click();
	}

	await expect.soft(page.getByText('Heading Example')).toBeVisible();

	await apiHelpers.jsonWebServicesLayout.deleteLayout(String(sitePage.id));
});
