/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {collectionsPagesTest} from '../../fixtures/collectionsPagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pageManagementSiteTest} from '../../fixtures/pageManagementSiteTest';
import getRandomString from '../../utils/getRandomString';
import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';
import {ANIMALS_COLLECTION_NAME} from '../setup/page-management-site/constants';
import getCollectionDefinition from './utils/getCollectionDefinition';
import getCollectionItemDefinition from './utils/getCollectionItemDefinition';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

const FRAGMENT_FIELDS = [
	{
		id: 'element-text',
		value: {
			text: {
				mapping: {
					fieldKey: 'JournalArticle_title',
					itemReference: {
						contextSource: 'CollectionItem',
					},
				},
			},
		},
	},
];

const test = mergeTests(
	apiHelpersTest,
	collectionsPagesTest,
	featureFlagsTest({
		'LPD-15596': true,
		'LPS-178052': true,
	}),
	journalPagesTest,
	loginTest(),
	pageEditorPagesTest,
	pageManagementSiteTest
);

test(
	'Page creator can perform actions on collection displayed in collection display via page content panel',
	{
		tag: '@LPS-125985',
	},
	async ({
		apiHelpers,
		collectionsPage,
		journalEditArticlePage,
		page,
		pageEditorPage,
		pageManagementSite,
	}) => {

		// Create definition for a collection mapped to Animals collection

		const animalsClassPK = await collectionsPage.getCollectionClassPK(
			ANIMALS_COLLECTION_NAME,
			pageManagementSite.friendlyUrlPath
		);

		const animalsCollection = getCollectionItemDefinition(
			getRandomString(),
			[
				getFragmentDefinition({
					fragmentFields: FRAGMENT_FIELDS,
					id: getRandomString(),
					key: 'BASIC_COMPONENT-heading',
				}),
			]
		);

		const collectionDefinition = getCollectionDefinition({
			classPK: animalsClassPK,
			id: getRandomString(),
			pageElements: [animalsCollection],
		});

		// Create a content page and go to edit mode

		const layoutTitle = getRandomString();

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([collectionDefinition]),
			siteId: pageManagementSite.id,
			title: layoutTitle,
		});

		await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

		// Go to page contents panel and click in edit

		await pageEditorPage.clickPageContentContentAction(
			'Edit',
			ANIMALS_COLLECTION_NAME
		);

		await expect(
			page.getByRole('heading', {name: ANIMALS_COLLECTION_NAME})
		).toBeVisible();

		await page.getByTitle(`Go to ${layoutTitle}`).click();

		// Go to page contents panel and click in view items

		await pageEditorPage.clickPageContentContentAction(
			'View Items',
			ANIMALS_COLLECTION_NAME
		);

		await expect(
			page.getByRole('heading', {name: 'View Items'})
		).toBeVisible();

		await expect(
			page.getByText('Animal 01 - Dogs and Cats categories')
		).toBeVisible();
		await expect(page.getByText('Animal 02 - Dogs category')).toBeVisible();

		await page.getByRole('dialog').getByLabel('close').click();

		// Go to page contents panel, click in add items and add a new item

		await pageEditorPage.clickPageContentContentAction(
			'Add Items',
			ANIMALS_COLLECTION_NAME,
			'Animal'
		);

		const articleTitle = 'Animal 03 - Elephant';

		await journalEditArticlePage.fillTitle(articleTitle);
		await journalEditArticlePage.publishArticle();

		await expect(page.getByText(articleTitle)).toBeVisible();

		// Go to page contents panel and click in permissions

		await pageEditorPage.clickPageContentContentAction(
			'Permissions',
			ANIMALS_COLLECTION_NAME
		);

		const permissionsFrame = page.frameLocator(
			'iframe[title="Permissions"]'
		);

		await permissionsFrame
			.getByRole('cell', {exact: true, name: 'Role'})
			.waitFor();

		const guestActionViewCheckBox = permissionsFrame.locator(
			'[id="guest_ACTION_VIEW"]'
		);

		await expect(guestActionViewCheckBox).toBeChecked();

		await guestActionViewCheckBox.uncheck();

		await permissionsFrame
			.getByRole('button', {exact: true, name: 'Save'})
			.click();

		await waitForSuccessAlert(permissionsFrame);

		await expect(guestActionViewCheckBox).not.toBeChecked();

		// Clean up

		await apiHelpers.jsonWebServicesLayout.deleteLayout(layout.id);
	}
);
