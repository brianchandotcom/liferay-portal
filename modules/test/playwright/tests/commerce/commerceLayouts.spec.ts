/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

test('LPD-25926 Display page template edit mode works with Speedwell theme', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	page,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	const displayPageTemplateName = getRandomString();

	await applicationsMenuPage.goToSite(site.name);

	await commerceLayoutsPage.goToDisplayPageTemplates();
	await commerceLayoutsPage.createDisplayPageTemplate(
		displayPageTemplateName,
		'Blogs Entry',
		site.name
	);
	await commerceLayoutsPage.addFragment('Heading');

	await expect(page.getByText('Heading Example')).toBeVisible();

	await commerceLayoutsPage.publishButton.click();
	await commerceLayoutsPage.configureDisplayPageTemplateTheme(
		'Select Speedwell By Liferay, Inc.'
	);

	await expect(
		page.getByText('Success:The page was updated successfully.')
	).toBeVisible();

	await commerceLayoutsPage.backLink.click();

	await commerceLayoutsPage
		.displayPageTemplateLink(displayPageTemplateName)
		.click();

	await expect(page.getByText('Heading Example')).toBeVisible();
});
