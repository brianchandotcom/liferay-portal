/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import {widgetPageTemplatesPagesTest} from '../../fixtures/widgetPageTemplatesPagesTest';
import getRandomString from '../../utils/getRandomString';

export const test = mergeTests(
	isolatedSiteTest,
	loginTest(),
	pagesAdminPagesTest,
	widgetPageTemplatesPagesTest
);

test('Add, rename and delete a page template in global site', async ({
	page,
	widgetPageTemplatesPage,
}) => {

	// Go to page template administration in global site

	await widgetPageTemplatesPage.goto();

	// Create global page template

	const widgetPageTemplateName = getRandomString();

	await widgetPageTemplatesPage.addGlobalWidgetPageTemplate(
		widgetPageTemplateName
	);

	await expect(
		page.getByText(widgetPageTemplateName, {exact: true})
	).toBeVisible();

	// Rename global page template

	const newWidgetPageTemplateName = getRandomString();

	await widgetPageTemplatesPage.renameGlobalWidgetPageTemplate(
		newWidgetPageTemplateName,
		widgetPageTemplateName
	);

	await expect(
		page.getByText(newWidgetPageTemplateName, {exact: true})
	).toBeVisible();

	// Delete global page template

	await widgetPageTemplatesPage.delete(newWidgetPageTemplateName);

	await expect(
		page.getByText(newWidgetPageTemplateName, {exact: true})
	).not.toBeVisible();
});

test('Add an active page template in global site and deactivate it', async ({
	page,
	pagesAdminPage,
	site,
	widgetPageTemplatesPage,
}) => {

	// Go to page template administration in global site

	await widgetPageTemplatesPage.goto();

	// Create global page template

	const widgetPageTemplateName = getRandomString();

	await widgetPageTemplatesPage.addGlobalWidgetPageTemplate(
		widgetPageTemplateName
	);

	// Check global page template is present in page creation

	await pagesAdminPage.goto(site.friendlyUrlPath);

	await pagesAdminPage.gotoSelectGlobalTemplates();

	await expect(
		page.getByText(widgetPageTemplateName, {exact: true})
	).toBeVisible();

	// Disable global page template

	await widgetPageTemplatesPage.goto();

	await widgetPageTemplatesPage.deactivateGlobalWidgetPageTemplate(
		widgetPageTemplateName
	);

	// Check global page template is not present in page creation

	await pagesAdminPage.goto(site.friendlyUrlPath);

	await pagesAdminPage.gotoSelectGlobalTemplates();

	await expect(
		page.getByText(widgetPageTemplateName, {exact: true})
	).not.toBeVisible();
});
