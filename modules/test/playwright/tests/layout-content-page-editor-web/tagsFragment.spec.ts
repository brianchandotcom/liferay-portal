/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {wemSiteTest} from '../../fixtures/wemSiteTest';
import {TAGS_OBJECT_ERC} from '../../setup/wem-site/constants';
import getRandomString from '../../utils/getRandomString';
import {PORTLET_URLS} from '../../utils/portletUrls';
import getFormContainerDefinition from './utils/getFormContainerDefinition';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

const OBJECT_DEFINITION_PATH = 'object-admin/v1.0/object-definitions';

export const test = mergeTests(
	apiHelpersTest,
	wemSiteTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest()
);

test('uses Tags fragment for Forms in a Content Page', async ({
	apiHelpers,
	page,
	wemSite,
}) => {

	// Get the id of the tags object from the site initializer

	const {id: objectId} =
		await apiHelpers.object.getObjectEntryByExternalReferenceCode(
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
		siteId: wemSite.id,
		title: getRandomString(),
	});

	// Create two tags

	for (const tagName of ['Dogs', 'Cats']) {
		await apiHelpers.headlessAdminTaxonomy.createTag({
			name: tagName,
			siteId: wemSite.id,
		});
	}

	// Go to view mode of the created page, select a tag for each fragment and submit the form

	await page.goto(`/web${wemSite.friendlyUrlPath}${layout.friendlyUrlPath}`);

	await page.getByRole('combobox').first().click();
	await page.getByRole('option', {name: 'Dogs'}).click();

	await page.getByRole('combobox').nth(1).click();
	await page.getByRole('option', {name: 'Cats'}).click();

	await page.getByRole('button', {name: 'Submit'}).click();

	await page
		.getByText('Thank you. Your information was successfully received.')
		.waitFor();

	// Go to the object definition page and check the Tags fragment

	await page.goto(
		`/group${wemSite.friendlyUrlPath}${PORTLET_URLS.objects}_${objectId}`
	);

	await page.locator('.table-list-title').getByRole('link').first().click();

	await page.waitForTimeout(1000);

	expect(await page.getByRole('grid')).toHaveText('CatsDogs');
});
