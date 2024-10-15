/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import path from 'path';

import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageTemplatesPagesTest} from '../../fixtures/pageTemplatesPagesTest';
import getRandomString from '../../utils/getRandomString';
import {waitForAlert} from '../../utils/waitForAlert';

export const test = mergeTests(
	isolatedSiteTest,
	loginTest(),
	pageTemplatesPagesTest
);

test('Add, rename and delete a content page template', async ({
	page,
	pageTemplatesPage,
	site,
}) => {

	// Go to page template administration

	await pageTemplatesPage.goto(site.friendlyUrlPath);

	// Create page template collection

	const pageTemplateCollectionName = getRandomString();

	await pageTemplatesPage.addPageTemplateCollection(
		pageTemplateCollectionName
	);

	// Create content page template

	const pageTemplateName = getRandomString();

	await pageTemplatesPage.addContentPageTemplate(pageTemplateName);

	// Assert content page template

	await pageTemplatesPage.goto(site.friendlyUrlPath);

	await expect(
		page.getByRole('link', {exact: true, name: pageTemplateName})
	).toBeVisible();

	// Change thumbnail

	const fileChooserPromise = page.waitForEvent('filechooser');

	await pageTemplatesPage.clickAction('Change Thumbnail', pageTemplateName);

	const iframe = page.frameLocator('iframe[title="Page Template Thumbnail"]');

	await expect(
		iframe.getByText('Drag & Drop Your Files or Browse to Upload')
	).toBeVisible();

	await iframe
		.getByText('Drag & Drop Your Files or Browse to Upload')
		.click();

	const fileChooser = await fileChooserPromise;

	await fileChooser.setFiles(path.join(__dirname, '/dependencies/image.jpg'));

	await iframe.getByRole('button', {exact: true, name: 'Add'}).click();

	await expect(
		page
			.locator('.card-type-asset')
			.filter({hasText: pageTemplateName})
			.locator('img')
	).toBeAttached();

	// Rename content page template

	await pageTemplatesPage.clickAction('Rename', pageTemplateName);

	const newPageTemplateName = getRandomString();

	await page
		.getByPlaceholder('Name', {exact: true})
		.fill(newPageTemplateName);

	await page.getByRole('button', {name: 'Save'}).click();

	await waitForAlert(page);

	await expect(
		page.getByRole('link', {exact: true, name: newPageTemplateName})
	).toBeVisible();

	// Delete content page template

	await pageTemplatesPage.deletePageTemplate(newPageTemplateName);

	await expect(
		page.getByRole('link', {exact: true, name: newPageTemplateName})
	).not.toBeVisible();
});

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

	await pageTemplatesPage.addWidgetPageTemplate(pageTemplateName);

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

test('Add, rename and delete a page template collection', async ({
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

	// Rename page template collection

	await pageTemplatesPage.clickPageTemplateCollectionAction(
		'Edit',
		pageTemplateCollectionName
	);

	const newPageTemplateCollectionName = getRandomString();

	await page.getByLabel('Name').fill(newPageTemplateCollectionName);

	await page.getByRole('button', {name: 'Save'}).click();

	await waitForAlert(page);

	await expect(
		page.getByRole('menuitem', {
			exact: true,
			name: newPageTemplateCollectionName,
		})
	).toBeVisible();

	// Delete page template collection

	await pageTemplatesPage.deletePageTemplateCollection(
		newPageTemplateCollectionName
	);

	await expect(
		page.getByRole('menuitem', {
			exact: true,
			name: newPageTemplateCollectionName,
		})
	).not.toBeVisible();
});
