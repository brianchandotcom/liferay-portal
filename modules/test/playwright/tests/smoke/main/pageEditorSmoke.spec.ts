/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectDefinitionAPI} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';
import {resolve} from 'path';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {backendPageTest} from '../../../fixtures/backendPageTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {PAGE_MANAGEMENT_SITE_ERC} from '../../../fixtures/pageManagementSiteTest';
import {ApiHelpers} from '../../../helpers/ApiHelpers';
import getRandomString from '../../../utils/getRandomString';
import getBasicWebContentStructureId from '../../../utils/structured-content/getBasicWebContentStructureId';
import getFragmentDefinition from '../../layout-content-page-editor-web/main/utils/getFragmentDefinition';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import {OBJECT_ENTITIES} from '../../setup/page-management-site/main/constants/objects';
import {PAGE_MANAGEMENT_SITE_NAME} from '../../setup/page-management-site/main/constants/site';

const test = mergeTests(
	apiHelpersTest,
	backendPageTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

test('Allows editing a page and mapping an editable', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {

	// Create a BWC

	const journalTitle = getRandomString();

	await apiHelpers.jsonWebServicesJournal.addWebContent({
		ddmStructureId: await getBasicWebContentStructureId(apiHelpers),
		groupId: site.id,
		titleMap: {en_US: journalTitle},
	});

	// Create page with a Heading fragment and go to edit mode

	const headingId = getRandomString();

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getFragmentDefinition({
				id: headingId,
				key: 'BASIC_COMPONENT-heading',
			}),
		]),
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Map the editable to the BWC

	await pageEditorPage.selectEditable(headingId, 'element-text');

	await pageEditorPage.setMappingConfiguration({
		mapping: {
			entity: 'Web Content',
			entry: journalTitle,
			field: 'Title',
		},
	});

	// Publish the page

	await pageEditorPage.publishPage();

	// Go to view mode of page and check mapping works

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

	await expect(page.getByText(journalTitle)).toBeVisible();
});

test('Can create and delete Page Management site', async ({backendPage}) => {

	// Create site

	const apiHelpers = new ApiHelpers(backendPage);

	const site = await apiHelpers.headlessSite.createSiteFromZip(
		{
			externalReferenceCode: PAGE_MANAGEMENT_SITE_ERC,
			name: PAGE_MANAGEMENT_SITE_NAME,
		},
		resolve(
			__dirname,
			'../../setup/page-management-site/main/site-initializer'
		)
	);

	expect(site).toHaveProperty(
		'externalReferenceCode',
		PAGE_MANAGEMENT_SITE_ERC
	);

	// Delete object definitions

	const ERCs = Object.values(OBJECT_ENTITIES).map((entity) => entity.ERC);

	for (const ERC of ERCs) {
		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionAPI);

		const {id: objectDefinitionId} = (
			await objectDefinitionAPIClient.getObjectDefinitionByExternalReferenceCode(
				ERC
			)
		).body;

		if (objectDefinitionId) {
			await objectDefinitionAPIClient.deleteObjectDefinition(
				objectDefinitionId
			);
		}
	}

	// Delete site

	await apiHelpers.headlessSite.deleteSiteByERC(PAGE_MANAGEMENT_SITE_ERC);
});
