/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {headlessBuilderPagesTest} from '../../fixtures/headlessBuilderPagesTest';
import {headlessDiscoveryPagesTest} from '../../fixtures/headlessDiscoveryWebPagesTest';
import {loginTest} from '../../fixtures/loginTest';

export const test = mergeTests(
	apiHelpersTest,
	headlessBuilderPagesTest,
	headlessDiscoveryPagesTest,
	loginTest
);

test('can get updated title in response after publish', async ({
	apiHelpers,
	applicationPage,
	headlessBuilderPage,
	page,
}) => {
	const basicAPIApplication = await apiHelpers.object.postObjectEntry(
		{
			apiApplicationToAPISchemas: [
				{
					description: 'API Application Schema',
					externalReferenceCode: 'api-application-schema',
					mainObjectDefinitionERC: 'L_API_APPLICATION',
					name: 'API Application Schema',
				},
			],
			applicationStatus: 'unpublished',
			baseURL: 'basic-application',
			description: 'Test API Application',
			externalReferenceCode: 'basic-application',
			title: 'Basic application',
		},
		'headless-builder/applications'
	);

	await headlessBuilderPage.goto();
	await headlessBuilderPage.openApplicationActions(basicAPIApplication.title);
	await page.getByRole('menuitem', {name: 'Edit'}).click();

	await applicationPage.applicationTitleTextBox.fill(
		`${basicAPIApplication.title} 1`
	);
	await applicationPage.publishButton.click();

	await page.goto('/');
	const modifiedBasicAPIApplication =
		await apiHelpers.object.getObjectEntryByExternalReferenceCode(
			'headless-builder/applications',
			basicAPIApplication.externalReferenceCode
		);
	expect(modifiedBasicAPIApplication.title).toEqual(
		`${basicAPIApplication.title} 1`
	);

	await apiHelpers.object.deleteObjectEntryByExternalReferenceCode(
		'headless-builder/applications',
		basicAPIApplication.externalReferenceCode
	);
});
