/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import getRandomString from '../../utils/getRandomString';
import getContainerDefinition from './utils/getContainerDefinition';
import getPageDefinition from './utils/getPageDefinition';
import getWidgetDefinition from './utils/getWidgetDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

test('Fails to duplicate a container', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {

	// Create a page with a container using the API

	const containerId = getRandomString();

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getContainerDefinition({
				id: containerId,
				pageElements: [
					getWidgetDefinition({
						id: getRandomString(),
						widgetName:
							'com_liferay_blogs_web_portlet_BlogsPortlet',
					}),
				],
			}),
		]),
		siteId: site.id,
		title: getRandomString(),
	});

	// Navigate to the page editor

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Click on the duplicate option

	await pageEditorPage.clickFragmentOption(containerId, 'Duplicate');

	// Assert that the error message is displayed

	await expect(page.locator('.alert-danger')).toContainText(
		'The layout could not be duplicated because it contains a widget (Blogs) that can only appear once in the page.'
	);
});
