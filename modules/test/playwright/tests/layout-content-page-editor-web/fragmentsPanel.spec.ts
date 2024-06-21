/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {fragmentsPagesTest} from '../../fixtures/fragmentPagesTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import getRandomString from '../../utils/getRandomString';
import getPageDefinition from './utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	fragmentsPagesTest,
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

test('Only published fragments are shown in the Fragments Sidebar', async ({
	apiHelpers,
	fragmentEditorPage,
	fragmentsPage,
	page,
	pageEditorPage,
	site,
}) => {
	await fragmentsPage.goto(site.friendlyUrlPath);

	const setName = getRandomString();
	await fragmentsPage.createFragmentSet(setName);

	await fragmentsPage.goto(site.friendlyUrlPath);

	const unpublishedFragmentName = getRandomString();
	await fragmentsPage.createFragment(setName, unpublishedFragmentName);

	await fragmentsPage.goto(site.friendlyUrlPath);

	const publishedFragmentName = getRandomString();
	await fragmentsPage.createFragment(setName, publishedFragmentName);
	await fragmentEditorPage.publish();

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition(),
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);
	await pageEditorPage.goToSidebarTab('Fragments and Widgets');

	const header = page.getByRole('menuitem', {
		exact: true,
		name: setName,
	});

	const isOpen = await header.evaluate(
		(element) => element.getAttribute('aria-expanded') === 'true'
	);

	if (!isOpen) {
		await header.click();
	}

	await expect(page.getByText(publishedFragmentName)).toBeVisible();

	await expect(page.getByText(unpublishedFragmentName)).not.toBeVisible();
});
