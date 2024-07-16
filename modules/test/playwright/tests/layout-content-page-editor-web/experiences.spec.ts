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
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

test('Allows renaming an experience', async ({
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

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Create experience and rename it

	await pageEditorPage.createExperience('E1');

	await expect(page.getByLabel('Experience: E1')).toBeVisible();

	await pageEditorPage.editExperienceName('E1', 'E1 edited');

	await expect(page.getByLabel('Experience: E1 edited')).toBeVisible();
});

test('Allows changing the segment of an existing experience', async ({
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

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Create experience and rename it

	await pageEditorPage.createExperience('E1');

	await expect(page.getByLabel('Experience: E1')).toBeVisible();

	await pageEditorPage.editExperienceSegment('E1', 'S1');

	await page.locator('.page-editor__experience-selector').click();

	const row = page.locator('.dropdown-menu__experience', {hasText: 'E1'});

	await expect(row).toContainText('AudienceS1');
});

test('Creates new experiences as expected', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {

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

	// Create new experience and check it's the last one and inactive

	await pageEditorPage.createExperience('E1');

	await expect(page.getByLabel('Experience: E1')).toBeVisible();

	await pageEditorPage.openExperienceSelector();

	const row = page.locator('.dropdown-menu__experience').last();

	await expect(row).toContainText('E1');
	await expect(row).toContainText('Inactive');

	await pageEditorPage.closeExperienceSelector();

	// Edit heading text in E1 experience

	await pageEditorPage.editTextEditable(headingId, 'element-text', 'E1 Text');

	await pageEditorPage.publishPage();

	// Go to view mode of page and check it displays the Default experience text

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

	await expect(page.getByText('E1 Text')).not.toBeAttached();

	await expect(page.getByText('Heading Example')).toBeVisible();
});

test('Keeps modal open when canceling segment creation', async ({
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

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Open experience creation modal and go to create new segment

	await pageEditorPage.experienceSelector.click();

	await page.getByText('Select Experience').waitFor();
	await page.getByLabel('New Experience').click();

	await page.getByText('New Segment').waitFor();
	await page.getByText('New Segment').click();

	// Cancel segment creation and check we are back to page editor

	await page.getByText('No Conditions yet').waitFor();

	await page.getByText('Cancel', {exact: true}).click();

	await expect(
		page.locator('.modal-title', {hasText: 'New Experience'})
	).toBeVisible();
});

test('Styles changes affect to current experience only', async ({
	apiHelpers,
	pageEditorPage,
	site,
}) => {

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

	// Change heading margin top

	await pageEditorPage.changeFragmentSpacing(headingId, 'Margin Top', '2');

	expect(
		await pageEditorPage.getFragmentStyle({
			fragmentId: headingId,
			isTopperStyle: true,
			style: 'marginTop',
		})
	).toBe('8px');

	// Create new experience and change margin top again

	await pageEditorPage.createExperience('E1');

	await pageEditorPage.changeFragmentSpacing(
		headingId,
		'Margin Top',
		'5',
		'px'
	);

	expect(
		await pageEditorPage.getFragmentStyle({
			fragmentId: headingId,
			isTopperStyle: true,
			style: 'marginTop',
		})
	).toBe('5px');

	// Change to Default experience again and check previous margin

	await pageEditorPage.switchExperience('Default');

	expect(
		await pageEditorPage.getFragmentStyle({
			fragmentId: headingId,
			isTopperStyle: true,
			style: 'marginTop',
		})
	).toBe('8px');
});

test('Allows duplicating an experience', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {

	// Create a page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Create new experience and duplicate it

	await pageEditorPage.createExperience('E1');

	await pageEditorPage.duplicateExperience('E1');

	await expect(page.getByLabel('Experience: Copy of E1')).toBeVisible();

	await pageEditorPage.openExperienceSelector();

	const row = page.locator('.dropdown-menu__experience').last();

	await expect(row).toContainText('Copy of E1');
	await expect(row).toContainText('Inactive');
});

test('Allows creating experiences with different fragments', async ({
	apiHelpers,
	pageEditorPage,
	site,
}) => {

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

	// Create new experience and remove the fragment

	await pageEditorPage.createExperience('E1');

	await pageEditorPage.removeFragment(headingId);

	// Change to Default experience again and check the fragment is present

	await pageEditorPage.switchExperience('Default');

	await expect(pageEditorPage.getFragment(headingId)).toBeVisible();
});

test('Allows editing and deleting an experience', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {

	// Create a page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Create new experience

	await pageEditorPage.createExperience('E1');

	// Edit it

	await pageEditorPage.editExperienceName('E1', 'E2');

	// Delete it

	await pageEditorPage.deleteExperience('E2');

	await pageEditorPage.openExperienceSelector();

	await expect(
		page.locator('.dropdown-menu__experience', {
			hasText: 'E2',
		})
	).not.toBeVisible();
});
