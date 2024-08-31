/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {pageTemplatesPagesTest} from './fixtures/pageTemplatesPagesTest';

export const test = mergeTests(
	isolatedSiteTest,
	loginTest(),
	pageTemplatesPagesTest
);

test('Add and delete a widget page template', async ({
	page,
	pageTemplatesPage,
	site,
}) => {

	// Go to page template administration in global site

	await pageTemplatesPage.goto(site.friendlyUrlPath);

	// Create page template collection

	const pageTemplateCollectionName = getRandomString();

	await pageTemplatesPage.addPageTemplateCollection(
		pageTemplateCollectionName
	);

	await expect(
		page.getByRole('menuitem', {
			exact: true,
			name: pageTemplateCollectionName,
		})
	).toBeVisible();

	// Create widget page template

	const pageTemplateName = getRandomString();

	await pageTemplatesPage.addPageTemplate(
		pageTemplateName,
		'Widget Page Template'
	);

	// Assert page template

	await pageTemplatesPage.goto(site.friendlyUrlPath);

	await expect(
		page.getByRole('link', {exact: true, name: pageTemplateName})
	).toBeVisible();

	// Delete page template

	await pageTemplatesPage.deletePageTemplate(pageTemplateName);

	await expect(
		page.getByRole('link', {exact: true, name: pageTemplateName})
	).not.toBeVisible();
});
