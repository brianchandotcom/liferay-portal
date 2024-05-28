/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {TAGS_OBJECT_ERC} from '../../setup/wem-site/constants';
import getGlobalSiteId from '../../utils/getGlobalSiteId';
import getRandomString from '../../utils/getRandomString';
import {PORTLET_URLS} from '../../utils/portletUrls';
import getFormContainerDefinition from './utils/getFormContainerDefinition';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

const OBJECT_DEFINITION_PATH = 'object-admin/v1.0/object-definitions';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest()
);

test('uses Tags fragment for Forms in a Content Page', async ({
	apiHelpers,
	page,
	site,
}) => {

	// Get the id of the tags object from the site initializer

	const {id: objectId} =
		await apiHelpers.objectEntry.getObjectEntryByExternalReferenceCode(
			OBJECT_DEFINITION_PATH,
			TAGS_OBJECT_ERC
		);

	// Create a Form Container with a Tags fragment and Submit fragment

	const firstTagsFragmentDefinition = getFragmentDefinition(
		getRandomString(),
		'com.liferay.fragment.renderer.categorization.inputs.internal.TagsInputFragmentRenderer'
	);

	const secondTagsFragmentDefinition = getFragmentDefinition(
		getRandomString(),
		'com.liferay.fragment.renderer.categorization.inputs.internal.TagsInputFragmentRenderer'
	);

	const submitFragmentDefinition = getFragmentDefinition(
		getRandomString(),
		'INPUTS-submit-button',
		{
			buttonSize: 'nm',
			buttonType: 'primary',
			submittedEntryStatus: 'approved',
		},
		[
			{
				id: 'submit-button-text',
				value: {
					fragmentLink: {},
				},
			},
		]
	);

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
		siteId: site.id,
		title: getRandomString(),
	});

	// Create two tags in Wem Site

	for (const tagName of ['Dogs', 'Cats']) {
		await apiHelpers.headlessAdminTaxonomy.createTag({
			name: tagName,
			siteId: site.id,
		});
	}

	// Create one tag on Global

	const globalSiteId = await getGlobalSiteId(apiHelpers);

	const globalTag = await apiHelpers.headlessAdminTaxonomy.createTag({
		name: 'Rabbits',
		siteId: globalSiteId,
	});

	// Go to view mode of the created page, select a tag for each fragment and submit the form

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

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
		`/group${site.friendlyUrlPath}${PORTLET_URLS.objects}_${objectId}`
	);

	await page.locator('.table-list-title').getByRole('link').first().click();

	const grid = await page.getByRole('grid');

	await grid.waitFor();

	await expect(grid).toHaveText('RabbitsCatsDogs');

	// Remove the tag created on Global

	await apiHelpers.headlessAdminTaxonomy.deleteTag({
		id: globalTag.id,
	});
});
