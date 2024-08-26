/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import path from 'path';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import getRandomString from '../../utils/getRandomString';
import {pagesPagesTest} from './fixtures/pagesPagesTest';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	pagesPagesTest,
	pageEditorPagesTest
);

test(
	'Test utility pages ui',
	{
		tag: ['@LPS-162765', '@LPS-162767'],
	},
	async ({
		apiHelpers,
		context,
		page,
		pageEditorPage,
		site,
		utilityPagesPage,
	}) => {

		// Add new page since it is needed to show site utility page

		await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		// Go to utility pages and add a new 404 utility page

		await utilityPagesPage.goto(site.friendlyUrlPath);

		const utilityPageName = getRandomString();

		await utilityPagesPage.createPage({
			name: utilityPageName,
			type: '404 Error',
		});

		// Assert utility page was created successfully

		const card = page
			.locator('.card-type-asset')
			.filter({hasText: utilityPageName});

		await expect(card.getByText('404 Error')).toBeVisible();

		await expect(card.getByText('Approved')).toBeVisible();

		// Add heading fragment to the utility page

		await utilityPagesPage.clickOnAction('Edit', utilityPageName);

		await pageEditorPage.addFragment('Basic Components', 'Heading');

		const headingId = await pageEditorPage.getFragmentId('Heading');

		await pageEditorPage.editTextEditable(
			headingId,
			'element-text',
			'Resource Not Found'
		);

		await utilityPagesPage.publishPage();

		// Preview utility page

		await utilityPagesPage.previewPage(utilityPageName);

		const pagePromise = context.waitForEvent('page');

		const newPage = await pagePromise;

		await expect(newPage.getByText('Resource Not Found')).toBeVisible();

		// Change thumbnail

		await utilityPagesPage.changeThumbnail(
			path.join(__dirname, '/dependencies/thumbnail.jpg'),
			utilityPageName
		);

		await expect(card.locator('img')).toBeAttached();

		// Copy utility page

		await utilityPagesPage.makeACopy(utilityPageName);

		await expect(
			page.getByRole('link', {
				exact: true,
				name: `${utilityPageName} (Copy)`,
			})
		).toBeVisible();

		// Rename utility page

		const newUtilityPageName = getRandomString();

		await utilityPagesPage.renamePage(
			newUtilityPageName,
			`${utilityPageName} (Copy)`
		);

		await expect(
			page.getByRole('link', {exact: true, name: newUtilityPageName})
		).toBeVisible();

		// Mark as default utility page

		await utilityPagesPage.markAsDefault(utilityPageName);

		await expect(card.locator('.lexicon-icon-check-circle')).toBeVisible();

		await page.goto('/web' + site.friendlyUrlPath + '/non-existing-url');

		await expect(page.getByText('Resource Not Found')).toBeVisible();

		// Unmark as default utility page

		await utilityPagesPage.goto(site.friendlyUrlPath);

		await utilityPagesPage.unmarkAsDefault(utilityPageName);

		await expect(
			card.locator('.lexicon-icon-check-circle')
		).not.toBeVisible();

		await page.goto('/web' + site.friendlyUrlPath + '/non-existing-url');

		await expect(
			page.getByText('The requested resource could not be found.')
		).toBeVisible();

		// Delete default utility page

		await utilityPagesPage.goto(site.friendlyUrlPath);

		await utilityPagesPage.markAsDefault(utilityPageName);
		await utilityPagesPage.deletePage(utilityPageName);

		await expect(card).not.toBeVisible();
	}
);
