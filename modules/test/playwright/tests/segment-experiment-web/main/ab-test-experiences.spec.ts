/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {clickAndExpectToBeHidden} from '../../../utils/clickAndExpectToBeHidden';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import {syncAnalyticsCloud} from '../../analytics-settings-web/main/utils/analytics-settings';
import getFragmentDefinition from '../../layout-content-page-editor-web/main/utils/getFragmentDefinition';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import {createABTest, createVariant, openABTesSidebar} from './utils/ab-test';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPD-78863': {enabled: true, system: true},
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginAnalyticsCloudTest(),
	loginTest(),
	pageEditorPagesTest
);

test(
	'AB Test panel context changes when the user switches between site pages',
	{tag: '@LPS-97882'},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		let channel;
		let project;

		try {
			const layoutOne = await apiHelpers.headlessDelivery.createSitePage({
				siteId: site.id,
				title: 'Page One',
			});

			const layoutTwo = await apiHelpers.headlessDelivery.createSitePage({
				siteId: site.id,
				title: 'Page Two',
			});

			const result = await syncAnalyticsCloud({
				apiHelpers,
				channelName: 'My Property - ' + getRandomString(),
				page,
				siteName: site.name,
			});

			channel = result.channel;
			project = result.project;

			// Create an experience on Page Two and add an AB Test on that experience

			await page.goto(
				`/web${site.friendlyUrlPath}${layoutTwo.friendlyUrlPath}?p_l_mode=edit`
			);

			await pageEditorPage.createExperience('E1');

			await pageEditorPage.publishPage();

			await page.goto(
				`/web${site.friendlyUrlPath}${layoutTwo.friendlyUrlPath}`
			);

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: page.getByRole('option', {
					name: 'E1 Segment: Anyone Inactive',
				}),
				trigger: page.getByLabel('Experience Selector'),
			});

			await openABTesSidebar(page);

			await createABTest({name: 'AB Test ' + getRandomString(), page});

			// Switch to Page One and assert the panel shows the empty state

			await page.goto(
				`/web${site.friendlyUrlPath}${layoutOne.friendlyUrlPath}`
			);

			await openABTesSidebar(page);

			await expect(page.getByText('Create Test')).toBeVisible();
		}
		finally {
			if (channel && project) {
				await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
					`[${channel.id}]`,
					project.groupId
				);
			}
		}
	}
);

test(
	'Deleting an experience with a draft AB Test can be cancelled or confirmed',
	{tag: '@LPS-101341'},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		let channel;
		let project;

		try {
			const experienceName = 'E1';

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				siteId: site.id,
				title: 'My Page',
			});

			const result = await syncAnalyticsCloud({
				apiHelpers,
				channelName: 'My Property - ' + getRandomString(),
				page,
				siteName: site.name,
			});

			channel = result.channel;
			project = result.project;

			// Create an experience and a draft AB Test on it

			await page.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}?p_l_mode=edit`
			);

			await pageEditorPage.createExperience(experienceName);

			await pageEditorPage.publishPage();

			await page.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: page.getByRole('option', {
					name: `${experienceName} Segment: Anyone Inactive`,
				}),
				trigger: page.getByLabel('Experience Selector'),
			});

			await openABTesSidebar(page);

			await createABTest({name: 'AB Test ' + getRandomString(), page});

			await createVariant({name: 'V1', page});

			// Try to delete the experience and cancel the confirm dialog

			await page.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}?p_l_mode=edit`
			);

			await pageEditorPage.openExperienceSelector();

			page.once('dialog', (dialog) => dialog.dismiss());

			await page
				.locator('.dropdown-menu__experience', {
					hasText: experienceName,
				})
				.getByLabel('Delete Experience')
				.click();

			await pageEditorPage.closeExperienceSelector();

			await pageEditorPage.openExperienceSelector();

			await expect(
				page.locator('.dropdown-menu__experience', {
					hasText: experienceName,
				})
			).toBeVisible();

			await pageEditorPage.closeExperienceSelector();

			// Delete the experience with confirm

			await pageEditorPage.deleteExperience(experienceName);

			await pageEditorPage.openExperienceSelector();

			await expect(
				page.locator('.dropdown-menu__experience', {
					hasText: experienceName,
				})
			).toHaveCount(0);
		}
		finally {
			if (channel && project) {
				await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
					`[${channel.id}]`,
					project.groupId
				);
			}
		}
	}
);

test(
	'Locked experience elements cannot be edited from the Browser tree',
	{tag: '@LPS-109345'},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		let channel;
		let project;

		try {
			const experienceName = 'E1';

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([
					getFragmentDefinition({
						id: getRandomString(),
						key: 'BASIC_COMPONENT-heading',
					}),
				]),
				siteId: site.id,
				title: 'My Page',
			});

			const result = await syncAnalyticsCloud({
				apiHelpers,
				channelName: 'My Property - ' + getRandomString(),
				page,
				siteName: site.name,
			});

			channel = result.channel;
			project = result.project;

			await page.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}?p_l_mode=edit`
			);

			await pageEditorPage.createExperience(experienceName);

			await pageEditorPage.publishPage();

			await page.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: page.getByRole('option', {
					name: `${experienceName} Segment: Anyone Inactive`,
				}),
				trigger: page.getByLabel('Experience Selector'),
			});

			await openABTesSidebar(page);

			await createABTest({name: 'AB Test ' + getRandomString(), page});

			await createVariant({name: 'V1', page});

			// Run the test so the experience becomes locked

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: page.getByRole('button', {name: 'Run'}),
				trigger: page.getByText('Review and Run Test'),
			});

			await expect(page.getByText('Test is now running.')).toBeVisible();

			await clickAndExpectToBeHidden({
				target: page.getByText('Test is now running.'),
				trigger: page.getByRole('button', {name: 'OK'}),
			});

			// Open the Browser tab and assert the Heading has no actions menu

			await page.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}?p_l_mode=edit`
			);

			await pageEditorPage.goToSidebarTab('Browser');

			const headingRow = page
				.locator('.treeview-link', {hasText: 'Heading'})
				.first();

			await expect(headingRow).toBeVisible();

			await expect(
				headingRow.locator('button.dropdown-toggle')
			).toHaveCount(0);
		}
		finally {
			if (channel && project) {
				await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
					`[${channel.id}]`,
					project.groupId
				);
			}
		}
	}
);
