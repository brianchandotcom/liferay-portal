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
import {pageViewModePagesTest} from '../../fixtures/pageViewModePagesTest';
import {wemSiteTest} from '../../fixtures/wemSiteTest';
import {ANIMALS_COLLECTION_NAME} from '../../setup/wem-site/constants';
import getRandomString from '../../utils/getRandomString';
import getCollectionDefinition from './utils/getCollectionDefinition';
import getCollectionItemDefinition from './utils/getCollectionItemDefinition';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	collectionsPagesTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest(),
	pageEditorPagesTest,
	pageViewModePagesTest,
	wemSiteTest
);

const testWithIsolatedSite = mergeTests(test, isolatedSiteTest);

test('Allows adding a Collection Display with a manual collection into another Collection Display with Recent Content', async ({
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

	// Create definition for a collection mapped to Animals collection

	const animalsClassPK = await collectionsPage.getCollectionClassPK(
		ANIMALS_COLLECTION_NAME,
		wemSite.friendlyUrlPath
	);

	const animalsCollection = getCollectionItemDefinition(getRandomString(), [
		getCollectionDefinition({
			classPK: animalsClassPK,
			id: getRandomString(),
			listStyle: 'Bulleted List (Journal)',
		}),
	]);

	// Create definition for another collection mapped to Recent Content provider

	const secondCollectionId = getRandomString();

	const secondCollectionDefinition = getCollectionDefinition({
		id: secondCollectionId,
		pageElements: [animalsCollection],
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

	// Expect second collection to display only Animal 01 and Animal 02 contents that times

	const secondCollection = pageEditorPage.getFragment(secondCollectionId);

	await expect(secondCollection.locator('li')).toHaveCount(count * 2);
	await expect(secondCollection.getByText('Animal 01')).toHaveCount(count);
	await expect(secondCollection.getByText('Animal 02')).toHaveCount(count);

	await apiHelpers.jsonWebServicesLayout.deleteLayout(layout.id);
});

testWithIsolatedSite(
	'Checks the error message when trying to drag a fragment to an unmapped collection',
	async ({apiHelpers, page, pageEditorPage, site}) => {

		// Create a content page with an empty collection display

		const collectionDefinition = getCollectionDefinition({
			id: getRandomString(),
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([collectionDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Try to drag fragment to collection and check error alert

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

test('Checks Content Flags, Content Ratings and Content Display are compatible with Collection Display', async ({
	apiHelpers,
	collectionsPage,
	page,
	pageEditorPage,
	wemSite,
}) => {

	// Create definition for a collection mapped to Animals collection with Content Flags, Content Ratings and Display Content fragments.

	const animalsClassPK = await collectionsPage.getCollectionClassPK(
		ANIMALS_COLLECTION_NAME,
		wemSite.friendlyUrlPath
	);

	const animalsCollection = getCollectionItemDefinition(getRandomString(), [
		getFragmentDefinition({
			id: getRandomString(),
			key: 'com.liferay.fragment.internal.renderer.ContentFlagsFragmentRenderer',
		}),
		getFragmentDefinition({
			id: getRandomString(),
			key: 'com.liferay.fragment.internal.renderer.ContentRatingsFragmentRenderer',
		}),
		getFragmentDefinition({
			fragmentConfig: {
				itemSelector: {
					template: {
						infoItemRendererKey:
							'com.liferay.journal.web.internal.info.item.renderer.JournalArticleFullContentInfoItemRenderer',
					},
				},
			},
			id: getRandomString(),
			key: 'com.liferay.fragment.internal.renderer.ContentObjectFragmentRenderer',
		}),
	]);

	const collectionDefinition = getCollectionDefinition({
		classPK: animalsClassPK,
		id: getRandomString(),
		pageElements: [animalsCollection],
	});

	// Create a content page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([collectionDefinition]),
		siteId: wemSite.id,
		title: getRandomString(),
	});

	// Go to edit mode of the created

	await pageEditorPage.goto(layout, wemSite.friendlyUrlPath);

	// Check that the Content Display shows the content in each item

	await expect(
		page.locator('.page-editor').getByText('Content', {exact: true})
	).toHaveCount(2);
	await expect(page.getByText('Animal 01 content')).toBeVisible();
	await expect(page.getByText('Animal 02 content')).toBeVisible();

	// Check that the Content Display shows Default Template by default

	await page.getByText('Animal 02 content').click();

	await expect(page.getByLabel('Template', {exact: true})).toHaveValue(
		'Default Template'
	);

	// Check that the Content Ratings is shown in each item and the Field input has the corresponding name

	const voteItem = await page.getByLabel('Vote', {exact: true});

	await expect(voteItem).toHaveCount(2);

	await voteItem.first().click();

	await expect(page.getByPlaceholder('No Item Selected')).toHaveValue(
		'Animal 01 - Dogs and Cats categories'
	);

	await voteItem.nth(1).click();

	await expect(page.getByPlaceholder('No Item Selected')).toHaveValue(
		'Animal 02 - Dogs category'
	);

	// Check that the Content Flags is shown in each item and the Field input has the corresponding name

	const reportItem = await page.locator('.taglib-flags');

	await expect(reportItem).toHaveCount(2);

	await reportItem.first().click();

	await expect(page.getByPlaceholder('No Item Selected')).toHaveValue(
		'Animal 01 - Dogs and Cats categories'
	);

	await reportItem.nth(1).click();

	await expect(page.getByPlaceholder('No Item Selected')).toHaveValue(
		'Animal 02 - Dogs category'
	);
});

test('Modifies inline text on all collection items', async ({
	apiHelpers,
	collectionsPage,
	page,
	pageEditorPage,
	wemSite,
}) => {

	// Create definition for a collection mapped to Animals collection

	const animalsClassPK = await collectionsPage.getCollectionClassPK(
		ANIMALS_COLLECTION_NAME,
		wemSite.friendlyUrlPath
	);

	const headingId = getRandomString();

	const animalsCollection = getCollectionItemDefinition(getRandomString(), [
		getFragmentDefinition({id: headingId, key: 'BASIC_COMPONENT-heading'}),
	]);

	const collectionDefinition = getCollectionDefinition({
		classPK: animalsClassPK,
		id: getRandomString(),
		pageElements: [animalsCollection],
	});

	// Create a content page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([collectionDefinition]),
		siteId: wemSite.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, wemSite.friendlyUrlPath);

	// Fill new content

	await pageEditorPage.editTextEditable(
		headingId,
		'element-text',
		'New Content'
	);

	// Check that the inline text changes in all items of the collection

	await expect(
		page.locator('.page-editor').getByText('New Content')
	).toHaveCount(2);
});

test('Checks the different styles for the Display Collection', async ({
	apiHelpers,
	collectionsPage,
	page,
	pageEditorPage,
	wemSite,
}) => {
	const checkStyleDisplay = async () => {
		const listItem = await page.locator(
			'.lfr-layout-structure-item-collection ul'
		);

		// Check the Bordered List style

		await expect(listItem.first()).toHaveClass('list-group');
		await expect(listItem.first().locator('li').first()).toHaveClass(
			'list-group-item'
		);

		// Check the Bulleted List style

		await expect(listItem.nth(1)).toHaveAttribute('class', '');
		await expect(listItem.nth(1).locator('li').first()).toHaveAttribute(
			'class',
			''
		);

		// Check the Inline List style

		await expect(listItem.nth(2)).toHaveClass('d-flex list-inline');
		await expect(listItem.nth(2).locator('li').first()).toHaveClass(
			'flex-grow-1'
		);

		// Check the Numbered List style

		const orderedListItem = await page.locator(
			'.lfr-layout-structure-item-collection ol'
		);

		await expect(orderedListItem).not.toHaveAttribute('class');
		await expect(orderedListItem.locator('li').first()).not.toHaveAttribute(
			'class'
		);

		// Check the Unstyled List style

		await expect(listItem.nth(3)).toHaveClass('list-unstyled');
		await expect(listItem.nth(3).locator('li').first()).toHaveAttribute(
			'class',
			''
		);
	};

	// Create several definitions with different Style Display

	const animalsClassPK = await collectionsPage.getCollectionClassPK(
		ANIMALS_COLLECTION_NAME,
		wemSite.friendlyUrlPath
	);

	const animalsCollection = getCollectionItemDefinition(getRandomString(), [
		getFragmentDefinition({
			id: getRandomString(),
			key: 'BASIC_COMPONENT-heading',
		}),
	]);

	const borderedListCollection = getCollectionDefinition({
		classPK: animalsClassPK,
		id: getRandomString(),
		listStyle: 'Bordered List (Collection Provider)',
		pageElements: [animalsCollection],
	});

	const bulletedListCollection = getCollectionDefinition({
		classPK: animalsClassPK,
		id: getRandomString(),
		listStyle: 'Bulleted List (Collection Provider)',
		pageElements: [animalsCollection],
	});

	const inlineListCollection = getCollectionDefinition({
		classPK: animalsClassPK,
		id: getRandomString(),
		listStyle: 'Inline List',
		pageElements: [animalsCollection],
	});

	const numberedListCollection = getCollectionDefinition({
		classPK: animalsClassPK,
		id: getRandomString(),
		listStyle: 'Numbered List',
		pageElements: [animalsCollection],
	});

	const unstyledListCollection = getCollectionDefinition({
		classPK: animalsClassPK,
		id: getRandomString(),
		listStyle: 'Unstyled List',
		pageElements: [animalsCollection],
	});

	// Create a content page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			borderedListCollection,
			bulletedListCollection,
			inlineListCollection,
			numberedListCollection,
			unstyledListCollection,
		]),
		siteId: wemSite.id,
		title: getRandomString(),
	});

	// Check the Style Display in edit mode

	await pageEditorPage.goto(layout, wemSite.friendlyUrlPath);

	await checkStyleDisplay();

	// Check the Style Display in view mode

	await page.goto(`/web${wemSite.friendlyUrlPath}${layout.friendlyUrlPath}`);

	await checkStyleDisplay();
});

test('Checks that fragment ids used within a display collection are not repeated even if they are nested elements', async ({
	apiHelpers,
	collectionsPage,
	page,
	pageEditorPage,
	wemSite,
}) => {
	const checkNonRepeatedFragmentIds = async () => {

		// Get all fragments with Heading Example text

		const fragments = await page
			.getByText('Heading Example', {exact: true})
			.locator('..')
			.all();

		// Check that the fragment ids are not repeated

		const fragmentIds = [];

		for (const fragment of fragments) {
			fragmentIds.push(fragment.getAttribute('id'));
		}

		expect(Array.from(new Set(fragmentIds))).toHaveLength(4);
	};

	const animalsClassPK = await collectionsPage.getCollectionClassPK(
		ANIMALS_COLLECTION_NAME,
		wemSite.friendlyUrlPath
	);

	// Create a first collection definition with headings

	const headingDefinition = getFragmentDefinition({
		id: getRandomString(),
		key: 'BASIC_COMPONENT-heading',
	});

	const headingCollectionDefintion = getCollectionItemDefinition(
		getRandomString(),
		[headingDefinition]
	);

	const collectionWithHeadings = getCollectionDefinition({
		classPK: animalsClassPK,
		id: getRandomString(),
		pageElements: [headingCollectionDefintion],
	});

	// Create a second collection definition with tabs and headings inside

	const tabsCollectionDefinition = getCollectionItemDefinition(
		getRandomString(),
		[
			getFragmentDefinition({
				fragmentConfig: {
					numberOfTabs: 1,
				},
				fragmentFields: [
					{
						id: 'title1',
						value: {
							fragmentLink: {},
						},
					},
				],
				id: getRandomString(),
				key: 'BASIC_COMPONENT-tabs',
				pageElements: [
					{
						id: getRandomString(),
						pageElements: [headingDefinition],
						type: 'FragmentDropZone',
					},
				],
			}),
		]
	);

	const collectionWithTabs = getCollectionDefinition({
		classPK: animalsClassPK,
		id: getRandomString(),
		pageElements: [tabsCollectionDefinition],
	});

	// Create a content page with the two collections and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			collectionWithHeadings,
			collectionWithTabs,
		]),
		siteId: wemSite.id,
		title: getRandomString(),
	});

	// Check that the fragment ids are not repeated in edit mode

	await pageEditorPage.goto(layout, wemSite.friendlyUrlPath);

	await checkNonRepeatedFragmentIds();

	// Check that the fragment ids are not repeated in view mode

	await page.goto(`/web${wemSite.friendlyUrlPath}${layout.friendlyUrlPath}`);

	await checkNonRepeatedFragmentIds();
});

test('Displays correct layout in other viewports', async ({
	apiHelpers,
	collectionsPage,
	page,
	pageEditorPage,
	wemSite,
}) => {

	// Create definition for a collection mapped to Animals collection

	const animalsClassPK = await collectionsPage.getCollectionClassPK(
		ANIMALS_COLLECTION_NAME,
		wemSite.friendlyUrlPath
	);

	const headingId = getRandomString();

	const animalsCollection = getCollectionItemDefinition(getRandomString(), [
		getFragmentDefinition({id: headingId, key: 'BASIC_COMPONENT-heading'}),
	]);

	const collectionId = getRandomString();

	const collectionDefinition = getCollectionDefinition({
		classPK: animalsClassPK,
		id: collectionId,
		pageElements: [animalsCollection],
	});

	// Create a content page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([collectionDefinition]),
		siteId: wemSite.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, wemSite.friendlyUrlPath);

	// Change layout to 4 columns in Desktop

	await pageEditorPage.changeFragmentConfiguration({
		fieldLabel: 'Layout',
		fragmentId: collectionId,
		tab: 'General',
		value: '4 Columns',
	});

	await pageEditorPage.publishPage();

	// Go to view mode and check correct layout is displayed on each viewport

	await page.goto(`/web${wemSite.friendlyUrlPath}${layout.friendlyUrlPath}`);

	const row = page.locator('.lfr-layout-structure-item-collection row');

	for (const col of await row.locator('.col').all()) {
		await expect(col).toHaveClass(/col-lg-3/);
		await expect(col).toHaveClass(/col-md-12/);
		await expect(col).toHaveClass(/col-sm-12/);
	}

	// Edit the page again and change layout to 2 columns in Tablet and Mobile

	await pageEditorPage.goto(layout, wemSite.friendlyUrlPath);

	await pageEditorPage.switchViewport('Tablet');

	await pageEditorPage.changeFragmentConfiguration({
		fieldLabel: 'Layout',
		fragmentId: collectionId,
		isDesktop: false,
		tab: 'General',
		value: '2 Columns',
	});

	await pageEditorPage.switchViewport('Portrait Phone');

	await pageEditorPage.changeFragmentConfiguration({
		fieldLabel: 'Layout',
		fragmentId: collectionId,
		isDesktop: false,
		tab: 'General',
		value: '2 Columns',
	});

	// Go to view mode again and check correct layout is displayed on each viewport

	await page.goto(`/web${wemSite.friendlyUrlPath}${layout.friendlyUrlPath}`);

	for (const col of await row.locator('.col').all()) {
		await expect(col).toHaveClass(/col-lg-3/);
		await expect(col).toHaveClass(/col-md-6/);
		await expect(col).toHaveClass(/col-sm-6/);
	}
});
