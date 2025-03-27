/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {masterPagesPagesTest} from '../../fixtures/masterPagesPagesTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import {styleBookPageTest} from '../../fixtures/styleBookPageTest';
import {ApiHelpers} from '../../helpers/ApiHelpers';
import {PagesAdminPage} from '../../pages/layout-admin-web/PagesAdminPage';
import {PageEditorPage} from '../../pages/layout-content-page-editor-web/PageEditorPage';
import {MasterPagesPage} from '../../pages/layout-page-template-admin-web/MasterPagesPage';
import {StyleBooksPage} from '../../pages/style-book-web/StyleBooksPage';
import getRandomString from '../../utils/getRandomString';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	featureFlagsTest({
		'LPD-30204': {enabled: true},
	}),
	masterPagesPagesTest,
	pageEditorPagesTest,
	pagesAdminPagesTest,
	styleBookPageTest
);

test('Assert that the style books in page editor are based on the applied theme', async ({
	page,
	pageEditorPage,
	pagesAdminPage,
	site,
	styleBooksPage,
}) => {
	const styleBookName = getRandomString();

	await test.step('Create a style book', async () => {
		await styleBooksPage.goto(site.friendlyUrlPath);

		await styleBooksPage.create(styleBookName);

		await styleBooksPage.publish();
	});

	const pageName = getRandomString();

	await test.step('Create a content page and assert the Classic style book is applied', async () => {
		await pagesAdminPage.goto(site.friendlyUrlPath);

		await pagesAdminPage.createNewPage({
			draft: true,
			name: pageName,
		});

		await pageEditorPage.goToSidebarTab('Page Design Options');

		await pageEditorPage.goToConfigurationTab('Style Book');

		await expect(page.getByText('Styles from Classic Theme')).toBeVisible();

		await expect(page.getByText(styleBookName)).toBeVisible();
	});

	await test.step('Apply the Dialect theme and assert the Dialect style book is applied', async () => {
		await pagesAdminPage.goto(site.friendlyUrlPath);

		await pagesAdminPage.goToDesignTabConfiguration(pageName);

		await pagesAdminPage.changeTheme('Dialect');

		await pagesAdminPage.goto(site.friendlyUrlPath);

		await pagesAdminPage.editPage(pageName);

		await pageEditorPage.goToSidebarTab('Page Design Options');

		await pageEditorPage.goToConfigurationTab('Style Book');

		await expect(page.getByText('Styles from Dialect Theme')).toBeVisible();
		await expect(page.getByText(styleBookName)).toBeHidden();
	});
});

test('Assert that the style books in page editor are based on the applied theme CSS client extension', async ({
	page,
	pageEditorPage,
	pagesAdminPage,
	site,
}) => {
	const pageName = getRandomString();

	await test.step('Create a content page and apply the Liferay Sample Theme CSS 3', async () => {
		await pagesAdminPage.goto(site.friendlyUrlPath);

		await pagesAdminPage.createNewPage({
			draft: true,
			name: pageName,
		});

		await pagesAdminPage.goto(site.friendlyUrlPath);

		await pagesAdminPage.goToDesignTabConfiguration(pageName);

		await pagesAdminPage.selectThemeCSSClientExtension(
			'Liferay Sample Theme CSS 3'
		);
	});

	await test.step('Assert the message that warns that the theme does not support style books', async () => {
		await pagesAdminPage.goto(site.friendlyUrlPath);

		await pagesAdminPage.editPage(pageName);

		await pageEditorPage.goToSidebarTab('Page Design Options');

		await pageEditorPage.goToConfigurationTab('Style Book');

		await expect(
			page.getByText(
				'The current theme does not support style books. To use this feature, you must change the selected theme.'
			)
		).toBeVisible();
	});

	await test.step('Create a content page and apply the Liferay Sample Theme CSS 4', async () => {
		await pagesAdminPage.goto(site.friendlyUrlPath);

		await pagesAdminPage.goToDesignTabConfiguration(pageName);

		await pagesAdminPage.selectThemeCSSClientExtension(
			'Liferay Sample Theme CSS 4'
		);
	});

	await test.step('Assert that the applied style book is the one provided by Liferay Sample Theme CSS 4', async () => {
		await pagesAdminPage.goto(site.friendlyUrlPath);

		await pagesAdminPage.editPage(pageName);

		await pageEditorPage.goToSidebarTab('Page Design Options');

		await pageEditorPage.goToConfigurationTab('Style Book');

		await expect(
			page.getByText(
				'Styles from Liferay Sample Theme CSS 4 Theme CSS Client Extension'
			)
		).toBeVisible();
	});
});

test.describe('Style books applied to master pages', async () => {
	async function setUp(
		apiHelpers: ApiHelpers,
		masterPagesPage: MasterPagesPage,
		page: Page,
		pageEditorPage: PageEditorPage,
		pagesAdminPage: PagesAdminPage,
		site: Site,
		styleBooksPage: StyleBooksPage,
		markStyleBookAsDefault?: boolean,
		selectStyleBookForMasterPage?: boolean
	) {
		const styleBookName = getRandomString();

		await styleBooksPage.goto(site.friendlyUrlPath);

		await styleBooksPage.create(styleBookName);

		await styleBooksPage.publish();

		if (markStyleBookAsDefault) {
			page.once('dialog', (dialog) => {
				dialog.accept();
			});

			await styleBooksPage.markAsDefault(styleBookName);
		}

		const masterPageName = getRandomString();

		await apiHelpers.jsonWebServicesLayoutPageTemplateEntry.addLayoutPageTemplateEntry(
			{
				groupId: site.id,
				name: masterPageName,
				type: 'master-layout',
			}
		);

		if (selectStyleBookForMasterPage) {
			await masterPagesPage.goto(site.friendlyUrlPath);

			await masterPagesPage.editMaster(masterPageName);

			await pageEditorPage.selectStyleBook(styleBookName);

			await pageEditorPage.publishPage();
		}

		const pageName = getRandomString();

		await pagesAdminPage.goto(site.friendlyUrlPath);

		await pagesAdminPage.createNewPage({
			draft: true,
			name: pageName,
			template: masterPageName,
		});

		return {
			masterPageName,
			pageName,
			styleBookName,
		};
	}

	test('Without selected style book and without default style book for the theme', async ({
		apiHelpers,
		masterPagesPage,
		page,
		pageEditorPage,
		pagesAdminPage,
		site,
		styleBooksPage,
	}) => {
		await setUp(
			apiHelpers,
			masterPagesPage,
			page,
			pageEditorPage,
			pagesAdminPage,
			site,
			styleBooksPage
		);

		await test.step('Assert that the style book "styles from classic theme" is applied to the child page', async () => {
			await pageEditorPage.goToSidebarTab('Page Design Options');

			await pageEditorPage.goToConfigurationTab('Style Book');

			await expect(
				page.getByText('Styles from Classic Theme')
			).toBeVisible();
		});
	});

	test('With selected style book and without default style book for the theme', async ({
		apiHelpers,
		masterPagesPage,
		page,
		pageEditorPage,
		pagesAdminPage,
		site,
		styleBooksPage,
	}) => {
		await setUp(
			apiHelpers,
			masterPagesPage,
			page,
			pageEditorPage,
			pagesAdminPage,
			site,
			styleBooksPage,
			false,
			true
		);

		await test.step('Assert that the selected style book from master is applied to the child page', async () => {
			await pageEditorPage.goToSidebarTab('Page Design Options');

			await pageEditorPage.goToConfigurationTab('Style Book');

			await expect(page.getByText('Styles from master')).toBeVisible();
		});
	});

	test('Without selected style book and with default style book for the theme', async ({
		apiHelpers,
		masterPagesPage,
		page,
		pageEditorPage,
		pagesAdminPage,
		site,
		styleBooksPage,
	}) => {
		await setUp(
			apiHelpers,
			masterPagesPage,
			page,
			pageEditorPage,
			pagesAdminPage,
			site,
			styleBooksPage,
			true,
			false
		);

		await test.step('Assert that the selected style book from master is applied to the child page', async () => {
			await pageEditorPage.goToSidebarTab('Page Design Options');

			await pageEditorPage.goToConfigurationTab('Style Book');

			await expect(page.getByText('Styles by default')).toBeVisible();
		});
	});
});

test.describe('Style book is incompatible with the applied theme', () => {
	test('Without selected style book and with default style book for the theme', async ({
		page,
		pageEditorPage,
		pagesAdminPage,
		site,
		styleBooksPage,
	}) => {
		const styleBookName = getRandomString();

		await test.step('Create style book', async () => {
			await styleBooksPage.goto(site.friendlyUrlPath);

			await styleBooksPage.create(styleBookName);

			await styleBooksPage.publish();
		});

		const pageName = getRandomString();

		await test.step('Create content page and apply the new style book', async () => {
			await pagesAdminPage.goto(site.friendlyUrlPath);

			await pagesAdminPage.createNewPage({
				draft: true,
				name: pageName,
			});

			await pageEditorPage.selectStyleBook(styleBookName);

			await pageEditorPage.publishPage();
		});

		await test.step('Change theme and assert that the applied style book is the one from the new theme', async () => {
			await pagesAdminPage.goto(site.friendlyUrlPath);

			await pagesAdminPage.goToDesignTabConfiguration(pageName);

			await pagesAdminPage.changeTheme('Dialect');

			await pagesAdminPage.goto(site.friendlyUrlPath);

			await pagesAdminPage.editPage(pageName);

			await pageEditorPage.goToSidebarTab('Page Design Options');

			await pageEditorPage.goToConfigurationTab('Style Book');

			await expect(
				page.getByText('Styles from Dialect Theme')
			).toBeVisible();
		});
	});
});
