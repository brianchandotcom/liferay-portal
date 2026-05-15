/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinition,
	ObjectDefinitionAPI,
} from '@liferay/object-admin-rest-client-js';
import {Page, expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {DataMigrationCenterPage} from './pages/DataMigrationCenterPage';
import {dataMigrationCenterPagesTest} from './fixtures/dataMigrationCenterPagesTest';

export const test = mergeTests(
	dataApiHelpersTest,
	dataMigrationCenterPagesTest,
	featureFlagsTest({
		'COMMERCE-8087': {enabled: true},
	}),
	loginTest()
);

const BACK_URL_PARAM =
	'_com_liferay_batch_planner_web_internal_portlet_BatchPlannerPortlet_backURL';

const stockObjectDefinition: ObjectDefinition = {
	active: true,
	externalReferenceCode: 'stockERC',
	label: {en_US: 'stock'},
	name: 'Stock',
	objectFields: [
		{
			DBType: 'String',
			businessType: 'Text',
			externalReferenceCode: 'nameERC',
			indexed: true,
			indexedAsKeyword: true,
			label: {en_US: 'name'},
			name: 'name',
			required: true,
		},
	],
	pluralLabel: {en_US: 'stocks'},
	portlet: true,
	scope: 'company',
	status: {code: 0},
};

const stockObjectEntry = {
	externalReferenceCode: 'nameERC',
	name: 'Stock Entry',
};

async function createPlanAndOpenViewPage(
	apiHelpers: any,
	dataMigrationCenterPage: DataMigrationCenterPage
): Promise<Page> {
	const objectDefinitionAPIClient = await apiHelpers.buildRestClient(
		ObjectDefinitionAPI
	);

	const {body: objectDefinition} =
		await objectDefinitionAPIClient.postObjectDefinition(
			stockObjectDefinition
		);

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await apiHelpers.objectEntry.postObjectEntry(stockObjectEntry, 'c/stocks');

	await dataMigrationCenterPage.exportFile(
		'JSON',
		'Stock (v1.0 - Liferay Object REST)'
	);

	await dataMigrationCenterPage.gotoPage();

	const {page} = dataMigrationCenterPage;

	await page
		.getByRole('table')
		.getByRole('link')
		.first()
		.click();

	await expect(
		page.getByText('Batch Engine Task Details')
	).toBeVisible();

	return page;
}

test(
	'Does not allow javascript URL in backURL when viewing a plan',
	{tag: '@LPD-90347'},
	async ({apiHelpers, dataMigrationCenterPage}) => {
		const page = await createPlanAndOpenViewPage(
			apiHelpers,
			dataMigrationCenterPage
		);

		const url = new URL(page.url());

		url.searchParams.set(BACK_URL_PARAM, 'javascript:alert(1)');

		await page.goto(url.toString());

		await expect(
			page.locator('a[href^="javascript:" i], a[href^=" javascript:" i]')
		).toHaveCount(0);
	}
);

test(
	'Preserves a safe backURL when viewing a plan',
	{tag: '@LPD-90347'},
	async ({apiHelpers, dataMigrationCenterPage}) => {
		const page = await createPlanAndOpenViewPage(
			apiHelpers,
			dataMigrationCenterPage
		);

		const safeBackURL = new URL(page.url()).pathname;

		const url = new URL(page.url());

		url.searchParams.set(BACK_URL_PARAM, safeBackURL);

		await page.goto(url.toString());

		const href = await page
			.getByRole('link', {name: 'Back'})
			.getAttribute('href');

		expect(href).toContain(safeBackURL);
	}
);
