/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';
import {faroConfig} from '../faro.config';
import {ACPage, navigateToACSettingsViaURL} from './navigation';

export async function checkDataSourceStatus({
	dataSourceName,
	dataSourceStatus,
	page,
}: {
	dataSourceName: string;
	dataSourceStatus: string;
	page: Page;
}) {
	await expect(
		page
			.getByRole('heading', {exact: true, name: dataSourceName})
			.locator('span')
	).toBeVisible();

	await expect(
		page.getByRole('button', {
			name: `Current Status: ${dataSourceStatus}`,
		})
	).toBeVisible();
}

export async function createDataSource(page) {
	await page.goto(faroConfig.environment.baseUrl);

	await page
		.getByRole('link', {
			name: 'FARO-DEV-liferay',
		})
		.click();

	await page.getByRole('link', {name: 'Settings'}).click();

	await clickAndExpectToBeVisible({
		autoClick: true,
		target: page.getByRole('menuitem', {name: 'Liferay DXP'}),
		trigger: page.getByRole('button', {name: 'Add Data Source'}),
	});

	const input = page.locator('#token');

	await expect(input).not.toHaveValue('');

	const token = await input.inputValue();

	return {token};
}

export async function findDataSource({
	dataSourceName,
	page,
}: {
	dataSourceName: string;
	page: Page;
}) {
	await page.getByRole('textbox', {name: 'Search'}).click();

	await page.getByRole('textbox', {name: 'Search'}).fill(dataSourceName);

	await page.getByRole('textbox', {name: 'Search'}).press('Enter');

	return page.getByRole('link', {exact: true, name: dataSourceName});
}

export async function gotoLatestLiferayDXPDataSource(page, project) {
	await navigateToACSettingsViaURL({
		acPage: ACPage.dataSourcePage,
		page,
		projectID: project.groupId,
	});

	await page
		.locator('td')
		.filter({exact: false, hasText: 'Liferay DXP'})
		.first()
		.click();
}

export async function renameDataSource({
	newDataSourceName,
	oldDataSourceName,
	page,
}: {
	newDataSourceName: string;
	oldDataSourceName: string;
	page: Page;
}) {
	const dataSource = await findDataSource({
		dataSourceName: oldDataSourceName,
		page,
	});

	if (dataSource) {
		await dataSource.click();
	}
	else {
		return;
	}

	await page.getByRole('button', {name: 'Edit'}).click();

	await page.locator('#dataSourceName').click();

	await page.locator('#dataSourceName').fill(newDataSourceName);

	await page
		.getByRole('button', {name: 'Submit'})
		.waitFor({state: 'visible'});

	await page.getByRole('button', {name: 'Submit'}).click();

	await expect(
		page
			.getByRole('heading', {exact: true, name: newDataSourceName})
			.locator('span')
	).toBeVisible();
}
