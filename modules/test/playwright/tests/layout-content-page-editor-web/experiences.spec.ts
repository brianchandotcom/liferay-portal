/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import getRandomString from '../../utils/getRandomString';
import {pageEditorPagesTest} from './fixtures/pageEditorPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	pageEditorPagesTest
);

test('allows renaming an experience', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {

	// Create page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goToEditMode(layout, site.friendlyUrlPath);

	// Create experience and rename it

	await pageEditorPage.createExperience('E1');

	await expect(page.getByLabel('Experience: E1')).toBeVisible();

	await pageEditorPage.editExperienceName('E1', 'E1 edited');

	await expect(page.getByLabel('Experience: E1 edited')).toBeVisible();
});

test('allows changing the segment of an existing experience', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {

	// Create page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goToEditMode(layout, site.friendlyUrlPath);

	// Create experience and rename it

	await pageEditorPage.createExperience('E1');

	await expect(page.getByLabel('Experience: E1')).toBeVisible();

	await pageEditorPage.editExperienceSegment('E1', 'S1');

	await page.locator('.page-editor__experience-selector').click();

	const row = page.locator('.dropdown-menu__experience', {hasText: 'E1'});

	await expect(row).toContainText('AudienceS1');
});
