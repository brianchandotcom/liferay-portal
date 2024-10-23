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
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPD-32075': true,
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

test(
	'Shows topper bar with name and without options on hover',
	{tag: ['@LPD-33348']},
	async ({apiHelpers, page, pageEditorPage, site}) => {

		// Create a page with a Heading fragment and go to edit mode

		const headingId = getRandomString();

		const headingDefinition = getFragmentDefinition({
			id: headingId,
			key: 'BASIC_COMPONENT-heading',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([headingDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Check the topper is not shown if not hovering

		const bar = page.locator('.page-editor__topper__bar');

		await expect(bar).not.toBeVisible();

		// Check topper is shown only with name on hover

		const heading = pageEditorPage.getFragment(headingId);
		await heading.hover();

		const name = bar.getByText('Heading');
		const dragHandler = bar.locator('.page-editor__topper__drag-icon');
		const actionsButton = bar.getByLabel('Options');

		await expect(bar).toBeVisible();
		await expect(name).toBeVisible();
		await expect(dragHandler).not.toBeVisible();
		await expect(actionsButton).not.toBeVisible();

		// Check topper is shown with name and options when active

		await pageEditorPage.selectFragment(headingId);

		await expect(bar).toBeVisible();
		await expect(name).toBeVisible();
		await expect(dragHandler).toBeVisible();
		await expect(actionsButton).toBeVisible();
	}
);
