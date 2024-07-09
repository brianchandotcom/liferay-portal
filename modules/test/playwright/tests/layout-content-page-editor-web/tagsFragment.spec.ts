/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {wemSiteTest} from '../../fixtures/wemSiteTest';
import {
	LEMON_BASKET_OBJECT_ERC,
	LEMON_OBJECT_ERC,
} from '../../setup/wem-site/constants';
import getGlobalSiteId from '../../utils/getGlobalSiteId';
import getRandomString from '../../utils/getRandomString';
import {PORTLET_URLS} from '../../utils/portletUrls';
import getFormContainerDefinition from './utils/getFormContainerDefinition';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest(),
	wemSiteTest,
	objectPagesTest,
	pageEditorPagesTest
);

test('uses Tags fragment for Forms in a Content Page', async ({
	apiHelpers,
	page,
	wemSite,
}) => {

	// Get the id of Lemon object from the site initializer

	const {id: objectId} =
		await apiHelpers.objectAdmin.getObjectDefinitionByExternalReferenceCode(
			LEMON_OBJECT_ERC
		);

	// Create a Form Container with a Tags fragment and Submit fragment

	const firstTagsFragmentDefinition = getFragmentDefinition({
		id: getRandomString(),
		key: 'com.liferay.fragment.renderer.categorization.inputs.internal.TagsInputFragmentRenderer',
	});

	const secondTagsFragmentDefinition = getFragmentDefinition({
		id: getRandomString(),
		key: 'com.liferay.fragment.renderer.categorization.inputs.internal.TagsInputFragmentRenderer',
	});

	const submitFragmentDefinition = getFragmentDefinition({
		fragmentConfig: {
			buttonSize: 'nm',
			buttonType: 'primary',
			submittedEntryStatus: 'approved',
		},
		fragmentFields: [
			{
				id: 'submit-button-text',
				value: {
					fragmentLink: {},
				},
			},
		],
		id: getRandomString(),
		key: 'INPUTS-submit-button',
	});

	const formDefinition = getFormContainerDefinition({
		id: getRandomString(),
		objectId,
		pageElements: [
			firstTagsFragmentDefinition,
			secondTagsFragmentDefinition,
			submitFragmentDefinition,
		],
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([formDefinition]),
		siteId: wemSite.id,
		title: getRandomString(),
	});

	// Create two tags in Wem Site

	for (const tagName of ['Dogs', 'Cats']) {
		await apiHelpers.headlessAdminTaxonomy.postSiteKeyword({
			name: tagName,
			siteId: wemSite.id,
		});
	}

	// Create one tag on Global

	const globalSiteId = await getGlobalSiteId(apiHelpers);

	const globalTag = await apiHelpers.headlessAdminTaxonomy.postSiteKeyword({
		name: 'Rabbits',
		siteId: globalSiteId,
	});

	// Go to view mode of the created page, select a tag for each fragment and submit the form

	await page.goto(`/web${wemSite.friendlyUrlPath}${layout.friendlyUrlPath}`);

	await page.getByRole('combobox').first().click();
	await page.getByRole('option', {exact: true, name: 'Dogs'}).click();

	await page.getByRole('combobox').first().click();
	await page.getByRole('option', {exact: true, name: 'Rabbits'}).click();

	await page.getByRole('combobox').nth(1).click();
	await page.getByRole('option', {exact: true, name: 'Cats'}).click();

	await page.getByRole('button', {name: 'Submit'}).click();

	await page
		.getByText('Thank you. Your information was successfully received.')
		.waitFor();

	// Go to the object definition page and check the Tags fragment

	await page.goto(
		`/group${wemSite.friendlyUrlPath}${PORTLET_URLS.objects}_${objectId}`
	);

	await page.locator('.table-list-title').getByRole('link').first().click();

	const grid = await page.getByRole('grid');

	await grid.waitFor();

	await expect(grid).toHaveText('RabbitsCatsDogs');

	// Remove the tag created on Global

	await apiHelpers.headlessAdminTaxonomy.deleteKeyword({
		id: globalTag.id,
	});
});

test('checks that an info message appears when categorization is disabled', async ({
	apiHelpers,
	objectDetailsPage,
	page,
	pageEditorPage,
	wemSite,
}) => {

	// Get Lemon Basket object id from the site initializer

	const {id: objectId} =
		await apiHelpers.objectAdmin.getObjectDefinitionByExternalReferenceCode(
			LEMON_BASKET_OBJECT_ERC
		);

	// Set the "Enable Categorization of Object entries" configuration to false

	await objectDetailsPage.goto('Lemon Basket');

	await objectDetailsPage.updateConfiguration({
		fieldLabel: 'Enable Categorization of Object entries',
		value: false,
	});

	// Create a Form Container with a Tags fragment

	const formDefinition = getFormContainerDefinition({
		id: getRandomString(),
		objectId,
		pageElements: [
			getFragmentDefinition({
				id: getRandomString(),
				key: 'com.liferay.fragment.renderer.categorization.inputs.internal.TagsInputFragmentRenderer',
			}),
		],
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([formDefinition]),
		siteId: wemSite.id,
		title: getRandomString(),
	});

	// Go to edit mode and check the info message

	await pageEditorPage.goto(layout, wemSite.friendlyUrlPath);

	await expect(
		page.getByText(
			'Categorization is disabled for the selected content. To show categories in this fragment, categorization must be enabled.'
		)
	).toBeVisible();

	// Reset initial configuration

	await objectDetailsPage.goto('Lemon Basket');

	await objectDetailsPage.updateConfiguration({
		fieldLabel: 'Enable Categorization of Object entries',
		value: true,
	});
});
