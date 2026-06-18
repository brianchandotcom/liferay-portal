/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {SiteSettingsPage} from '../../../pages/site-admin-web/SiteSettingsPage';
import getRandomString from '../../../utils/getRandomString';
import {journalPagesTest} from '../../journal-web/main/fixtures/journalPagesTest';
import getDataStructureDefinition from '../../journal-web/main/utils/getDataStructureDefinition';

export const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	journalPagesTest,
	loginTest()
);

test(
	'Geolocation field renders the OpenStreetMap marker icon served from the local module',
	{
		tag: '@LPD-95231',
	},
	async ({apiHelpers, journalEditArticlePage, page, site}) => {
		const structureName = getRandomString();

		await apiHelpers.dataEngine.createStructure(
			site.id,
			getDataStructureDefinition({
				defaultLanguageId: 'en_US',
				fields: [{fieldType: 'geolocation', name: 'Location'}],
				name: structureName,
			})
		);

		const siteSettingsPage = new SiteSettingsPage(page);

		await siteSettingsPage.goToSiteSetting(
			'Maps',
			'Maps',
			site.friendlyUrlPath
		);

		await page.getByLabel('OpenStreetMap', {exact: true}).check();

		await siteSettingsPage.saveConfiguration();

		const markerIconResponses: number[] = [];

		page.on('response', (response) => {
			if (response.url().includes('marker_icon')) {
				markerIconResponses.push(response.status());
			}
		});

		await journalEditArticlePage.goto({
			siteUrl: site.friendlyUrlPath,
			structureName,
		});

		const markerIcon = page.locator('.lfr-map img.leaflet-marker-icon');

		await expect(markerIcon).toBeVisible();

		await expect(markerIcon).toHaveAttribute(
			'src',
			/\/o\/map-openstreetmap\/images\/marker_icon\.png/
		);

		expect(markerIconResponses.length).toBeGreaterThan(0);

		for (const status of markerIconResponses) {
			expect(status).toBe(200);
		}
	}
);
