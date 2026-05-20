/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {clickAndExpectToBeHidden} from '../../../utils/clickAndExpectToBeHidden';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import {PORTLET_URLS} from '../../../utils/portletUrls';
import {goToSegmentsAdmin} from '../../change-tracking-web/main/utils/segments';

const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-78863': {enabled: true, system: true},
		'LPS-178052': {enabled: true},
	}),
	instanceSettingsPagesTest,
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

test.beforeEach(async ({instanceSettingsPage}) => {
	await instanceSettingsPage.goToInstanceSetting(
		'Segments',
		'Segments Service'
	);

	await instanceSettingsPage.checkOption('Enable Segmentation', false);

	await instanceSettingsPage.saveAndWaitForAlert();
});

test.afterEach(async ({instanceSettingsPage}) => {
	await instanceSettingsPage.goToInstanceSetting(
		'Segments',
		'Segments Service'
	);

	await instanceSettingsPage.resetInstanceSetting();
});

test(
	'Asserts the segmentation-disabled alert can be dismissed and recovered at the Dynamic Collection editor, and that the re-enable link reaches Segments Service',
	{tag: ['@LPS-154019', '@LPS-152539']},
	async ({apiHelpers, page, site}) => {

		// Create a dynamic asset list

		await apiHelpers.jsonWebServicesAssetListEntry.addDynamicAssetListEntry(
			{
				groupId: site.id,
				title: 'Dynamic Collection Test',
			}
		);

		// Open the Asset Lists Admin and edit the collection

		await page.goto(
			`/group${site.friendlyUrlPath}${PORTLET_URLS.collections}`
		);

		await page.getByRole('link', {name: 'Dynamic Collection Test'}).click();

		const warning = page.locator('.alert-warning');

		const alert = warning.getByText(
			'Personalized variations cannot be displayed because segmentation is disabled.'
		);

		// Close the alert and assert it is hidden

		await expect(alert).toBeVisible();

		await clickAndExpectToBeHidden({
			target: alert,
			trigger: warning.getByRole('button', {name: 'Close'}),
		});

		// Refresh and assert the alert is back

		await page.reload();

		await expect(alert).toBeVisible();

		// Click the re-enable link and assert Segments Service is reached

		await clickAndExpectToBeVisible({
			target: page.getByRole('heading', {name: 'Segments Service'}),
			trigger: page.getByRole('link', {
				name: 'To enable, go to Instance Settings.',
			}),
		});

		await expect(page.getByText('Virtual Instance Scope')).toBeVisible();
	}
);

test(
	'Asserts the segmentation-disabled alert can be dismissed and recovered at the Segments admin list and editor, and that the re-enable link reaches Segments Service',
	{tag: ['@LPS-154019', '@LPS-152539']},
	async ({page, site}) => {

		// Open the Segments admin list

		await goToSegmentsAdmin(page, site.friendlyUrlPath);

		const warning = page.locator('.alert-warning');

		const alert = warning.getByText('Segmentation is disabled.');

		// Close the alert and assert it is hidden on the list

		await expect(alert).toBeVisible();

		await clickAndExpectToBeHidden({
			target: alert,
			trigger: warning.getByRole('button', {name: 'Close'}),
		});

		// Refresh and assert the alert is back on the list

		await page.reload();

		await expect(alert).toBeVisible();

		// Open the segment editor and close the alert again

		await page.getByRole('link', {name: 'Add New User Segment'}).click();

		await expect(alert).toBeVisible();

		await clickAndExpectToBeHidden({
			target: alert,
			trigger: warning.getByRole('button', {name: 'Close'}),
		});

		// Refresh and assert the alert is back on the editor

		await page.reload();

		await expect(alert).toBeVisible();

		// Click the re-enable link and assert Segments Service is reached

		await clickAndExpectToBeVisible({
			target: page.getByRole('heading', {name: 'Segments Service'}),
			trigger: page.getByRole('link', {
				name: 'To enable, go to Instance Settings.',
			}),
		});

		await expect(page.getByText('Virtual Instance Scope')).toBeVisible();
	}
);

test(
	'Asserts the segmentation-disabled alert can be dismissed and recovered at the Experiences menu of the page editor, and that the re-enable link reaches Segments Service',
	{tag: ['@LPS-154019', '@LPS-151362', '@LPS-152539']},
	async ({apiHelpers, page, pageEditorPage, site}) => {

		// Create a content page and open it in the editor

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Open the Experiences menu

		await pageEditorPage.openExperienceSelector();

		const warning = page.locator('.alert-warning');

		const alert = warning.getByText(
			'Experiences cannot be displayed because segmentation is disabled.'
		);

		// Close the alert and assert it is hidden

		await expect(alert).toBeVisible();

		await clickAndExpectToBeHidden({
			target: alert,
			trigger: warning.getByRole('button', {name: 'Close'}),
		});

		// Refresh, reopen the Experiences menu, and assert the alert is back

		await page.reload();

		await pageEditorPage.openExperienceSelector();

		await expect(alert).toBeVisible();

		// Click the re-enable link and assert Segments Service is reached

		await clickAndExpectToBeVisible({
			target: page.getByRole('heading', {name: 'Segments Service'}),
			trigger: page.getByRole('link', {
				name: 'To enable, go to Instance Settings.',
			}),
		});

		await expect(page.getByText('Virtual Instance Scope')).toBeVisible();
	}
);

test(
	'Asserts the segmentation-disabled alert can be dismissed and recovered at the Manual Collection editor, and that the re-enable link reaches Segments Service',
	{tag: ['@LPS-154019', '@LPS-152539']},
	async ({apiHelpers, page, site}) => {

		// Create a manual asset list

		await apiHelpers.jsonWebServicesAssetListEntry.addManualAssetListEntry({
			groupId: site.id,
			title: 'Manual Collection Test',
		});

		// Open the Asset Lists Admin and edit the collection

		await page.goto(
			`/group${site.friendlyUrlPath}${PORTLET_URLS.collections}`
		);

		await page.getByRole('link', {name: 'Manual Collection Test'}).click();

		const warning = page.locator('.alert-warning');

		const alert = warning.getByText(
			'Personalized variations cannot be displayed because segmentation is disabled.'
		);

		// Close the alert and assert it is hidden

		await expect(alert).toBeVisible();

		await clickAndExpectToBeHidden({
			target: alert,
			trigger: warning.getByRole('button', {name: 'Close'}),
		});

		// Refresh and assert the alert is back

		await page.reload();

		await expect(alert).toBeVisible();

		// Click the re-enable link and assert Segments Service is reached

		await clickAndExpectToBeVisible({
			target: page.getByRole('heading', {name: 'Segments Service'}),
			trigger: page.getByRole('link', {
				name: 'To enable, go to Instance Settings.',
			}),
		});

		await expect(page.getByText('Virtual Instance Scope')).toBeVisible();
	}
);
