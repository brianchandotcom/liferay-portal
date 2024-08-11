/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {templatesPageTest} from '../../fixtures/templatesPageTest';
import getRandomString from '../../utils/getRandomString';

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
