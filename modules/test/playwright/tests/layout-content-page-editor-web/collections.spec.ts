/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {collectionsPagesTest} from '../../fixtures/CollectionsPageTest';
import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {wemSiteTest} from '../../fixtures/wemSiteTest';
import getRandomString from '../../utils/getRandomString';
import getCollectionDefinition from './utils/getCollectionDefinition';
import getCollectionItemDefinition from './utils/getCollectionItemDefinition';
import getPageDefinition from './utils/getPageDefinition';

export const test = mergeTests(
	apiHelpersTest,
	wemSiteTest,
	collectionsPagesTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest(),
	pageEditorPagesTest
);

export const testWithIsolatedSite = mergeTests(test, isolatedSiteTest);

test('allows adding a Collection Display with a manual collection into another Collection Display with Recent Content', async ({
	apiHelpers,
	collectionsPage,
	pageEditorPage,
	wemSite,
}) => {

	// Create definition for a collection mapped to
	// Recent Content provider with Bordered List style

	const firstCollectionId = getRandomString();

	const firstCollectionDefinition = getCollectionDefinition({
		id: firstCollectionId,
		listStyle: 'Bordered List (Collection Provider)',
		provider: 'Recent Content',
	});

	// Create definition for a collection mapped to Samples collection

	const samplesClassPK = await collectionsPage.getCollectionClassPK(
		'Samples',
		wemSite.friendlyUrlPath
	);

	const samplesCollection = getCollectionItemDefinition(getRandomString(), [
		getCollectionDefinition({
			classPK: samplesClassPK,
			id: getRandomString(),
			listStyle: 'Bulleted List (Journal)',
		}),
	]);

	// Create definition for another collection mapped to Recent Content provider

	const secondCollectionId = getRandomString();

	const secondCollectionDefinition = getCollectionDefinition({
		id: secondCollectionId,
		pageElements: [samplesCollection],
		provider: 'Recent Content',
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			firstCollectionDefinition,
			secondCollectionDefinition,
		]),
		siteId: wemSite.id,
		title: getRandomString(),
	});

	// Go to edit mode of page

	await pageEditorPage.goto(layout, wemSite.friendlyUrlPath);

	// Calculate the number of recent contents

	const firstCollection = await pageEditorPage.getFragment(firstCollectionId);

	const count = await firstCollection.locator('.list-group-item').count();

	// Expect second collection to display only Sample 01 content that times

	const secondCollection = await pageEditorPage.getFragment(
		secondCollectionId
	);

	await expect(secondCollection.getByText('Sample 01')).toHaveCount(count);

	for (const item of await secondCollection.getByRole('listitem').all()) {
		await expect(item).toHaveText('Sample 01');
	}

	await apiHelpers.jsonWebServicesLayout.deleteLayout(layout.id);
});

testWithIsolatedSite(
	'checks the error message when trying to drag a fragment to an unmapped collection',
	async ({apiHelpers, page, pageEditorPage, site}) => {
		const collectionDefinition = getCollectionDefinition({
			id: getRandomString(),
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([collectionDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await page
			.getByRole('menuitem', {
				name: 'Add Button',
			})
			.dragTo(page.getByText('No Collection Selected Yet'));

		await expect(page.locator('.alert-danger')).toHaveText(
			'Error:Fragments cannot be placed inside an unmapped collection display fragment.'
		);
	}
);
