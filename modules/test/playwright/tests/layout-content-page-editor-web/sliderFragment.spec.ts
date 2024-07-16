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
import {checkAccessibility} from '../../utils/checkAccessibility';
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

test('Checks that the Slider fragment works correctly', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	const expectSlideIsActive = async (name: string) => {
		await expect(page.getByLabel(name, {exact: true})).toHaveClass(
			/active/
		);
		await expect(page.getByLabel(`Go to ${name}`)).toHaveAttribute(
			'aria-current',
			'true'
		);
	};

	const expectSlideIsNotActive = async (name: string) => {
		await expect(page.getByLabel(name, {exact: true})).not.toHaveClass(
			/active/
		);
		await expect(page.getByLabel(`Go to ${name}`)).toHaveAttribute(
			'aria-current',
			'false'
		);
	};

	// Create page with Slider fragment and go to edit mode

	const sliderId = getRandomString();

	const sliderDefinition = getFragmentDefinition({
		fragmentConfig: {
			numberOfSlides: 3,
		},
		fragmentFields: [
			{
				id: '02-02-title',
				value: {
					text: {
						value_i18n: {
							en_US: 'Slide 2',
						},
					},
				},
			},
			{
				id: '01-02-title',
				value: {
					text: {
						value_i18n: {
							en_US: 'Slide 1',
						},
					},
				},
			},
		],
		id: sliderId,
		key: 'BASIC_COMPONENT-slider',
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([sliderDefinition]),
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Change the number of slides

	const slide = page.locator('[aria-roledescription="slide"]');

	expect(await slide.all()).toHaveLength(3);

	await pageEditorPage.changeFragmentConfiguration({
		fieldLabel: 'Number of Slides',
		fragmentId: sliderId,
		tab: 'General',
		value: '2',
	});

	expect(await slide.all()).toHaveLength(2);

	// Check Auto Hide Play button

	const playButton = page.locator('.stopped');

	await expect(playButton).not.toHaveClass(
		/carousel-toggle-button--always-visible/
	);

	await pageEditorPage.changeFragmentConfiguration({
		fieldLabel: 'Auto Hide Play Button',
		fragmentId: sliderId,
		tab: 'General',
		value: false,
	});

	await expect(playButton).toHaveClass(
		/carousel-toggle-button--always-visible/
	);

	await pageEditorPage.publishPage();

	// Go to view mode

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

	// Pause the carousel

	await expect(page.getByText('Stop slide rotation')).toBeVisible();

	await page.locator('.carousel-toggle-button.playing').click();

	await expect(page.getByText('Start slide rotation')).toBeVisible();

	// Check the slide 1 is active

	await expectSlideIsActive('Slide 1');
	await expectSlideIsNotActive('Slide 2');

	// Click next slide and check the slide 2 is active

	await page.getByLabel('Next Slide').click();

	await expectSlideIsActive('Slide 2');
	await expectSlideIsNotActive('Slide 1');

	// Click previous slide and check the slide 1 is active

	await page.getByLabel('Previous Slide').click();

	await expectSlideIsActive('Slide 1');
	await expectSlideIsNotActive('Slide 2');

	// Check accessibility

	await checkAccessibility({page, selectors: ['.component-slider']});
});
