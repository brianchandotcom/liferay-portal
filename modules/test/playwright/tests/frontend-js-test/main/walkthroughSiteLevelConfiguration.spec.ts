/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';
import * as fs from 'fs';
import * as path from 'path';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {siteSettingsPagesTest} from '../../../fixtures/siteSettingsPagesTest';
import {DataApiHelpers} from '../../../helpers/ApiHelpers';
import {liferayConfig} from '../../../liferay.config';
import {SiteSettingsPage} from '../../../pages/site-admin-web/SiteSettingsPage';
import getRandomString from '../../../utils/getRandomString';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import getWidgetDefinition from '../../layout-content-page-editor-web/main/utils/getWidgetDefinition';

const test = mergeTests(
	dataApiHelpersTest,
	isolatedSiteTest,
	instanceSettingsPagesTest,
	siteSettingsPagesTest,
	loginTest()
);

function readJsonDependency(fileName: string): string {
	return fs.readFileSync(
		path.resolve(__dirname, 'dependencies', fileName),
		'utf-8'
	);
}

async function addWalkthrough(
	siteSettingsPage: SiteSettingsPage,
	page: Page,
	siteUrl: string,
	jsonContent: string
) {
	await siteSettingsPage.goToSiteSetting('Walkthrough', undefined, siteUrl);

	await page.getByLabel('enable-walkthrough').check();

	await page
		.getByLabel('steps-walkthrough')
		.fill(jsonContent);

	await siteSettingsPage.saveConfiguration();
}

async function createWalkthroughPage(
	apiHelpers: DataApiHelpers,
	siteId: string,
	title: string
) {
	const widgetDefinition = getWidgetDefinition({
		id: getRandomString(),
		widgetName:
			'com_liferay_frontend_js_walkthrough_sample_web_internal_portlet_FrontendJSWalkthroughSampleWebPortlet',
	});

	return await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([widgetDefinition]),
		siteId,
		title,
	});
}

test.beforeEach(async ({apiHelpers, instanceSettingsPage, page, site}) => {
	await instanceSettingsPage.goToInstanceSetting(
		'Feature Flags',
		'Deprecation'
	);

	await page.getByPlaceholder('Search for').fill('Walkthrough');

	const walkthroughRow = page.locator('.list-group-item', {
		hasText: 'Walkthrough',
	});

	await walkthroughRow.waitFor();

	const walkthroughToggle = walkthroughRow.locator(
		'input[type="checkbox"]'
	);

	if (!(await walkthroughToggle.isChecked())) {
		await walkthroughRow.getByText('Disabled').click();
	}

	await page.goto('/');

	const layout = await createWalkthroughPage(
		apiHelpers,
		site.id,
		'Walkthrough Page 1'
	);

	test.info().annotations.push({
		description: layout.friendlyUrlPath,
		type: 'walkthroughPageUrl',
	});

	test.info().annotations.push({
		description: site.friendlyUrlPath,
		type: 'siteUrl',
	});
});

test(
	'CanBeDisabled',
	{tag: '@LPS-150905'},
	async ({page, site, siteSettingsPage}) => {
		const singlePageJson = readJsonDependency(
			'walkthrough_configuration_single_page.json'
		);

		await addWalkthrough(
			siteSettingsPage,
			page,
			site.friendlyUrlPath,
			singlePageJson
		);

		await siteSettingsPage.goToSiteSetting(
			'Walkthrough',
			undefined,
			site.friendlyUrlPath
		);

		await page.getByLabel('enable-walkthrough').uncheck();

		await siteSettingsPage.saveConfiguration();

		const walkthroughPageUrl = test.info().annotations.find(
			(a) => a.type === 'walkthroughPageUrl'
		)?.description;

		await page.goto(
			`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${walkthroughPageUrl}`
		);

		await expect(
			page.locator('.lfr-walkthrough-hotspot')
		).not.toBeVisible();
	}
);

test(
	'CanBeEnabled',
	{tag: '@LPS-150905'},
	async ({page, site, siteSettingsPage}) => {
		await siteSettingsPage.goToSiteSetting(
			'Walkthrough',
			undefined,
			site.friendlyUrlPath
		);

		await page.getByLabel('enable-walkthrough').check();

		await siteSettingsPage.saveConfiguration();
	}
);

test(
	'JSONCanBeConfigured',
	{tag: '@LPS-150905'},
	async ({page, site, siteSettingsPage}) => {
		const singlePageJson = readJsonDependency(
			'walkthrough_configuration_single_page.json'
		);

		await siteSettingsPage.goToSiteSetting(
			'Walkthrough',
			undefined,
			site.friendlyUrlPath
		);

		await page
			.getByLabel('steps-walkthrough')
			.fill(singlePageJson);

		await siteSettingsPage.saveConfiguration();
	}
);

test(
	'JSONCanBeUpdated',
	{tag: '@LPS-150905'},
	async ({page, site, siteSettingsPage}) => {
		const singlePageJson = readJsonDependency(
			'walkthrough_configuration_single_page.json'
		);

		await addWalkthrough(
			siteSettingsPage,
			page,
			site.friendlyUrlPath,
			singlePageJson
		);

		const editJson = readJsonDependency(
			'edit_walkthrough_configuration.json'
		);

		await page
			.getByLabel('steps-walkthrough')
			.fill(editJson);

		await siteSettingsPage.saveConfiguration();

		const walkthroughPageUrl = test.info().annotations.find(
			(a) => a.type === 'walkthroughPageUrl'
		)?.description;

		await page.goto(
			`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${walkthroughPageUrl}`
		);

		const hotspot = page.locator('.lfr-walkthrough-hotspot');

		await hotspot.click();

		await expect(page.locator('.popover-header span')).toContainText(
			'EDIT: Click the Button'
		);
	}
);

test(
	'WillDisplayWhenDefinedForSite',
	{tag: '@LPS-150905'},
	async ({page, site, siteSettingsPage}) => {
		const multiplePageJson = readJsonDependency(
			'walkthrough_configuration_multiple_page.json'
		);

		await addWalkthrough(
			siteSettingsPage,
			page,
			site.friendlyUrlPath,
			multiplePageJson
		);

		const walkthroughPageUrl = test.info().annotations.find(
			(a) => a.type === 'walkthroughPageUrl'
		)?.description;

		await page.goto(
			`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${walkthroughPageUrl}`
		);

		const hotspot = page.locator('.lfr-walkthrough-hotspot');

		await hotspot.click();

		await expect(page.locator('.popover-header span')).toContainText(
			'Click the Button'
		);
	}
);

test(
	'WillNotDisplayWhenNotDefinedForSite',
	{tag: '@LPS-150905'},
	async ({page, site}) => {
		const walkthroughPageUrl = test.info().annotations.find(
			(a) => a.type === 'walkthroughPageUrl'
		)?.description;

		await page.goto(
			`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${walkthroughPageUrl}`
		);

		await expect(
			page.locator('.popover.show')
		).not.toBeVisible();
	}
);
