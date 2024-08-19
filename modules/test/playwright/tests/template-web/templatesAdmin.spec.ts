/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';
import {templatesPageTest} from './fixtures/templatesPageTest';

const test = mergeTests(isolatedSiteTest, loginTest(), templatesPageTest);

test(
	'Can add, copy and delete an information template',
	{
		tag: '@LPS-124478',
	},
	async ({page, site, templatesPage}) => {

		// Go to templates administration

		await templatesPage.goto(site.friendlyUrlPath);

		// Create information template

		const informationTemplateName = getRandomString();

		await templatesPage.createInformationTemplate({
			itemSubtype: 'Basic Web Content',
			itemType: 'Web Content Article',
			name: informationTemplateName,
		});

		// Copy information template

		await templatesPage.goto(site.friendlyUrlPath);
		await templatesPage.copyInformationTemplate(informationTemplateName);

		await expect(
			page.getByRole('link', {
				exact: true,
				name: `${informationTemplateName} (Copy)`,
			})
		).toBeVisible();

		// Delete information template

		await templatesPage.deleteInformationTemplate(
			`${informationTemplateName} (Copy)`
		);

		await expect(
			page.getByRole('link', {
				exact: true,
				name: `${informationTemplateName} (Copy)`,
			})
		).not.toBeVisible();
	}
);

test(
	'Edit an information template',
	{
		tag: '@LPS-124478',
	},
	async ({page, site, templatesPage}) => {

		// Go to templates administration

		await templatesPage.goto(site.friendlyUrlPath);

		// Create information template

		const informationTemplateName = getRandomString();

		await templatesPage.createInformationTemplate({
			itemSubtype: 'Basic Web Content',
			itemType: 'Web Content Article',
			name: informationTemplateName,
		});

		// Add title and description to script

		await page.getByRole('button', {name: 'Title'}).click();
		await page.getByRole('button', {name: 'Description'}).click();

		// Check properties tab

		await page.getByLabel('Properties').click();

		await expect(
			page.getByText('Web Content Article', {exact: true})
		).toBeVisible();
		await expect(
			page.locator('p').filter({hasText: 'Basic Web Content'})
		).toBeVisible();
		await expect(page.getByLabel('Template Key')).toBeVisible();
		await expect(page.getByLabel('URL', {exact: true})).toBeVisible();
		await expect(
			page.getByLabel('WebDAV URL', {exact: true})
		).toBeVisible();

		// Save information template

		await page.getByRole('button', {exact: true, name: 'Save'}).click();
		await waitForSuccessAlert(page);

		// Edit

		await page
			.getByRole('link', {exact: true, name: informationTemplateName})
			.click();

		await expect(page.locator('.ddm_template_editor__App')).toContainText(
			'${JournalArticle_title.getData()}'
		);
		await expect(page.locator('.ddm_template_editor__App')).toContainText(
			'${JournalArticle_description.getData()}'
		);
	}
);
