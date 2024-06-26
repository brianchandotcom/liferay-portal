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

test('checks that the Slider fragment works correctly', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
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

	// Create page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([sliderDefinition]),
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Change the number of slides

	const slide = await page.locator('[aria-roledescription="slide"]');

	await expect(await slide.all()).toHaveLength(3);

	await pageEditorPage.changeFragmentConfiguration({
		fieldLabel: 'Number of Slides',
		fragmentId: sliderId,
		tab: 'General',
		value: '2',
	});

	await expect(await slide.all()).toHaveLength(2);
});
