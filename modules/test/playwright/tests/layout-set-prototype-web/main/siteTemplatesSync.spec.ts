/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {globalMenuPagesTest} from '../../../fixtures/globalMenuPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {productMenuPageTest} from '../../../fixtures/productMenuPageTest';
import {sitesPageTest} from '../../../fixtures/sitesPageTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import {sitesAdminPagesTest} from '../../site-admin-web/main/fixtures/sitesAdminPagesTest';
import createSiteTemplate from './utils/createSiteTemplate';

export const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-35443': {enabled: true},
		'LPD-82107': {enabled: true},
	}),
	globalMenuPagesTest,
	loginTest(),
	productMenuPageTest,
	sitesAdminPagesTest,
	sitesPageTest
);

test(
	'Execute Site Template Sync action is hidden for inactive Site Templates',
	{tag: '@LPD-87027'},
	async ({apiHelpers, globalMenuPage, page}) => {

		// Create an inactive Site Template

		const siteTemplateName = 'SiteTemplate-' + getRandomString();

		const layoutSetPrototype =
			await apiHelpers.jsonWebServicesLayoutSetPrototype.addLayoutSetPrototypes(
				{
					active: false,
					name: siteTemplateName,
				}
			);

		apiHelpers.data.push({
			id: layoutSetPrototype.layoutSetPrototypeId,
			type: 'layoutSetPrototype',
		});

		// Open the row Actions dropdown in the Site Templates list

		await globalMenuPage.goToControlPanel('Site Templates');

		// The sync action is hidden, and Activate is shown instead of Deactivate

		await clickAndExpectToBeVisible({
			target: page.getByRole('menuitem', {name: 'Activate'}),
			trigger: page
				.locator('tr', {hasText: siteTemplateName})
				.getByLabel('Actions'),
		});

		await expect(
			page.getByRole('menuitem', {name: 'Execute Site Template Sync'})
		).toBeHidden();

		// Activate the Site Template and verify the sync action is now visible

		await page.getByRole('menuitem', {name: 'Activate'}).click();

		await clickAndExpectToBeVisible({
			target: page.getByRole('menuitem', {
				name: 'Execute Site Template Sync',
			}),
			trigger: page
				.locator('tr', {hasText: siteTemplateName})
				.getByLabel('Actions'),
		});
	}
);

test(
	'Execute Site Template Sync propagates changes to a linked Site',
	{tag: '@LPD-87027'},
	async ({
		apiHelpers,
		globalMenuPage,
		page,
		productMenuPage,
		sitesAdminPage,
		sitesPage,
	}) => {
		test.slow();

		// Create the Site Template and a linked Site

		const siteTemplateName = 'SiteTemplate-' + getRandomString();

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

		await sitesAdminPage.goto();

		const siteName = 'Site-' + getRandomString();

		const {externalReferenceCode} = await sitesPage.createSite({
			isCustom: true,
			siteName,
			templateName: siteTemplateName,
		});

		apiHelpers.data.push({id: externalReferenceCode, type: 'site'});

		// Add a new page to the Site Template

		const layoutSetPrototypeGroup =
			await apiHelpers.jsonWebServicesGroup.getGroupByKey(
				layoutSetPrototype.companyId,
				layoutSetPrototype.layoutSetPrototypeId
			);

		const newPageName = 'NewPage-' + getRandomString();

		await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: layoutSetPrototypeGroup.groupId,
			privateLayout: 'true',
			title: newPageName,
		});

		// Trigger the manual sync from the Site Templates list

		await globalMenuPage.goToControlPanel('Site Templates');

		await clickAndExpectToBeVisible({
			target: page.getByRole('menuitem', {
				name: 'Execute Site Template Sync',
			}),
			trigger: page
				.locator('tr', {hasText: siteTemplateName})
				.getByLabel('Actions'),
		});

		page.once('dialog', (dialog) => dialog.accept());

		await page
			.getByRole('menuitem', {name: 'Execute Site Template Sync'})
			.click();

		await expect(
			page.getByText(
				`The sync of the site template ${siteTemplateName} started. You will receive a notification when the process is complete.`
			)
		).toBeVisible();

		// Reload on each attempt so the bell reflects the latest server-side
		// notification state instead of the dropdown rendered before the
		// asynchronous sync notification arrived

		await expect(async () => {
			await page.reload();

			await page.getByLabel('New Notification').click({timeout: 100});

			await expect(
				page.getByText(
					`The sync of the site template ${siteTemplateName} finished successfully.`,
					{exact: true}
				)
			).toBeVisible({timeout: 100});
		}).toPass();

		// The new page propagates to the linked Site

		await expect(async () => {
			const sitePages = await apiHelpers.headlessAdminSite.getPages(
				externalReferenceCode,
				'pageSize=100&privateLayout=false'
			);

			expect(
				sitePages.items.some(
					(item) => item.name_i18n['en-US'] === newPageName
				)
			).toBeTruthy();
		}).toPass();
	}
);
