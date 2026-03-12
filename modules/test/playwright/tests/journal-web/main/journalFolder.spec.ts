/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {productMenuPageTest} from '../../../fixtures/productMenuPageTest';
import getRandomString from '../../../utils/getRandomString';
import {openFieldset} from '../../../utils/openFieldset';
import createSiteTemplate from '../../layout-set-prototype-web/main/utils/createSiteTemplate';
import {journalPagesTest} from './fixtures/journalPagesTest';

const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	loginTest(),
	isolatedSiteTest,
	journalPagesTest,
	productMenuPageTest
);

test(
	'Workflow configuration should not be visible for journal folders in Site Templates',
	{tag: '@LPD-82511'},
	async ({
		apiHelpers,
		journalEditFolderPage,
		journalPage,
		page,
		productMenuPage,
	}) => {
		const siteTemplateName: string = 'Template-' + getRandomString();

		const layoutSetPrototype = await createSiteTemplate({
			apiHelpers,
			page,
			productMenuPage,
			templateName: siteTemplateName,
		});

		apiHelpers.data.push({
			id: layoutSetPrototype.layoutSetPrototypeId,
			type: 'layoutSetPrototype',
		});

		await journalPage.goto(
			'/template-' + layoutSetPrototype.layoutSetPrototypeId
		);
		await journalPage.goToCreateFolder();

		const title = getRandomString();
		await journalEditFolderPage.title.fill(title);

		await page.getByRole('button', {name: 'Save'}).click();

		await journalEditFolderPage.editFolder(title);

		await page.waitForURL(/edit_folder/);

		await openFieldset(page, 'Structure Restrictions');

		await expect(page.getByText('Inherit allowed structures')).toBeHidden();
		await expect(page.getByText('Set the allowed structures')).toBeHidden();
		await expect(
			page.getByText('Set the default workflow for')
		).toBeHidden();

		await expect(
			page.getByText('Use structure restrictions of')
		).toBeVisible();
		await expect(page.getByText('Define specific structure')).toBeVisible();
	}
);

test(
	'Test Advance Update permission for Journal folder',
	{tag: '@LPD-46006'},
	async ({apiHelpers, journalEditFolderPage, journalPage, page, site}) => {
		const testUser = await apiHelpers.headlessAdminUser.postUserAccount();

		const role =
			await apiHelpers.headlessAdminUser.getRoleByName('Site Member');

		await apiHelpers.headlessAdminUser.assignUserToSite(
			String(role.id),
			site.id,
			testUser.id
		);

		await journalPage.goto(site.friendlyUrlPath);
		await journalPage.goToCreateFolder();

		const title = getRandomString();
		await journalEditFolderPage.title.fill(title);

		await openFieldset(page, 'Permissions');

		await page
			.getByLabel(
				'Give Update permission to users with role Site Member.'
			)
			.uncheck();

		await page
			.getByLabel(
				'Give Advanced Update permission to users with role Site Member.'
			)
			.check();

		await page.getByRole('button', {name: 'Save'}).click();

		await journalEditFolderPage.editFolder(title);

		await page.waitForURL(/edit_folder/);

		const doAsUserIdURL = `${page.url()}&doAsUserId=${testUser.id}`;

		await page.goto(doAsUserIdURL);

		await expect(journalEditFolderPage.title).toBeDisabled();
		await expect(journalEditFolderPage.structureRestricions).toBeVisible();

		await journalPage.goto(site.friendlyUrlPath);
		await journalEditFolderPage.gotToPermission(title);

		const permissionIframe = page.frameLocator(
			'iframe[title="Permissions"]'
		);

		await permissionIframe.locator('#site-member_ACTION_UPDATE').check();

		await permissionIframe
			.locator('#site-member_ACTION_ADVANCED_UPDATE')
			.uncheck();

		await permissionIframe.getByRole('button', {name: 'Save'}).click();

		await page.getByLabel('Close', {exact: true}).click();

		await page.goto(doAsUserIdURL);

		await expect(journalEditFolderPage.title).toBeEnabled();
		await expect(journalEditFolderPage.structureRestricions).toBeHidden();
	}
);
