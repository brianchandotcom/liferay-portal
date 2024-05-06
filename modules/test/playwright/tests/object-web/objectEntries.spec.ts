/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {collectionsPagesTest} from '../../fixtures/CollectionsPageTest';
import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {pageEditorPagesTest} from '../layout-content-page-editor-web/fixtures/pageEditorPagesTest';
import getCollectionDefinition from '../layout-content-page-editor-web/utils/getCollectionDefinition';
import getFragmentDefinition from '../layout-content-page-editor-web/utils/getFragmentDefinition';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';

export const test = mergeTests(
	apiHelpersTest,
	collectionsPagesTest,
	isolatedSiteTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest(),
	pageEditorPagesTest
);
test.describe('Manage object entries through page templates', () => {
	test('filters object definition entries of boolean type on an collection display page', async ({
		apiHelpers,
		page,
		pageEditorPage,
		site,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postObjectDefinition({
				active: true,
				externalReferenceCode: 'customObjectERC',
				label: {
					en_US: 'customobject',
				},
				name: 'CustomObject',
				objectFields: [
					{
						DBType: 'Boolean',
						businessType: 'Boolean',
						externalReferenceCode: 'customBoolean',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'customBoolean'},
						listTypeDefinitionId: 0,
						name: 'customBoolean',
						required: false,
						system: false,
						type: 'Boolean',
					},
				],
				pluralLabel: {
					en_US: 'customobjects',
				},
				portlet: true,
				scope: 'company',
				status: {
					code: 0,
				},
			});

		const trueObjectEntry = {
			customBoolean: true,
		};

		const falseObjectEntry = {
			customBoolean: false,
		};

		await apiHelpers.objectEntry.postObjectEntry(
			trueObjectEntry,
			'c/customobjects'
		);

		await apiHelpers.objectEntry.postObjectEntry(
			falseObjectEntry,
			'c/customobjects'
		);

		const collectionDefinition = getCollectionDefinition({
			id: getRandomString(),
		});

		const headingDefinition = getFragmentDefinition(
			getRandomString(),
			'BASIC_COMPONENT-heading'
		);

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				collectionDefinition,
				headingDefinition,
			]),
			siteId: site.id,
			title: 'Collection Display filtered by boolean type',
		});

		await pageEditorPage.goToEditMode(layout, site.friendlyUrlPath);

		await pageEditorPage.selectFragment(collectionDefinition.id);

		await pageEditorPage.goToConfigurationTab('General');

		await pageEditorPage.chooseCollectionDisplayOption(
			'Collection Providers',
			'customobjects customobject'
		);

		await pageEditorPage.chooseCollectionFilterOption(
			'customBoolean',
			'true'
		);

		await pageEditorPage.goToSidebarTab('Browser');

		await pageEditorPage.selectFragment(collectionDefinition.id);

		await pageEditorPage.selectFragment(headingDefinition.id);

		await page
			.getByLabel('Select Heading')
			.dragTo(page.getByLabel('Collection Item', {exact: true}));

		await page.getByLabel('Select element-text').click();

		await page
			.getByLabel('Field')
			.selectOption('ObjectField_customBoolean');

		await pageEditorPage.publishPage();

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

		await expect(page.getByText('true')).toBeVisible();

		await expect(page.getByText('false')).toBeHidden();

		// Clean up

		await apiHelpers.jsonWebServicesLayout.deleteLayout(layout.id);

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition.id
		);
	});
});
