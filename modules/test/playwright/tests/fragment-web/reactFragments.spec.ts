/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import path from 'path';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {fragmentsPagesTest} from '../../fixtures/fragmentPagesTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import getRandomString from '../../utils/getRandomString';
import {zipFolder} from '../../utils/zip';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest(),
	fragmentsPagesTest,
	pageEditorPagesTest
);

test(
	'View react fragment is rendered correctly in page',
	{
		tag: '@LPS-129182',
	},
	async ({apiHelpers, fragmentsPage, page, pageEditorPage, site}) => {

		// Go to fragment administration and create fragment set

		await fragmentsPage.goto(site.friendlyUrlPath);

		// Go to import fragment

		await fragmentsPage.clickFragmentSetsAction('Import');

		// Import react fragment

		const fileChooserPromise = page.waitForEvent('filechooser');

		await page
			.getByRole('button', {exact: true, name: 'Select File'})
			.click();

		const fileChooser = await fileChooserPromise;

		await fileChooser.setFiles(
			await zipFolder(
				path.join(__dirname, '/dependencies/react-fragment-example.zip')
			)
		);

		await page.getByText('react-fragment-example.zip').waitFor();

		await page.getByRole('button', {name: 'Import'}).click();

		await expect(page.getByText('React Fragment Example')).toBeVisible();

		await page.getByRole('button', {name: 'Done'}).click();

		// Assert react fragment was imported successfully

		const fragmentCard = page
			.locator('.card-page-item')
			.filter({hasText: 'React Fragment Example'});

		await expect(fragmentCard).toBeVisible();

		await expect(
			fragmentCard.locator('.sticker-overlay .lexicon-icon-react')
		).toBeVisible();

		// Add react fragment to content page

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await pageEditorPage.addFragment(
			'Collection Name',
			'React Fragment Example'
		);

		// Assert react fragment in edit mode

		await expect(page.getByText('Hello World')).toBeAttached();

		// Publish content page

		await pageEditorPage.publishPage();

		// Assert react fragment  in view mode

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

		await expect(page.getByText('Hello World')).toBeAttached();
	}
);
