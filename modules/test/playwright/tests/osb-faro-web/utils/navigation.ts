/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {faroConfig} from '../faro.config';
import {waitForLoading} from './loading';

export enum ACPage {
	assetPage = 'assets',
	eventAnalysisPage = 'events',
	individualPage = 'individuals',
	segmentPage = 'segments',
	sitePage = 'sites',
	testPage = 'tests',
}

export async function navigateTo({
	page,
	pageName,
}: {
	page: Page;
	pageName: string;
}) {
	await page.getByRole('link', {name: pageName}).first().click();

	await waitForLoading(page);
}

export async function navigateToACWorkspace({
	page,
	workspaceName = 'FARO-DEV-liferay Liferay Demo Enterprise Plan',
}: {
	page: Page;
	workspaceName?: string;
}) {
	await page.goto(faroConfig.environment.baseUrl);

	await page
		.getByRole('link', {
			name: workspaceName,
		})
		.click();
}

export async function navigateToACPageViaURL({
	acPage,
	channelID,
	page,
	projectID,
}: {
	acPage: ACPage;
	channelID: string;
	page: Page;
	projectID: string;
}) {
	await page.goto(
		`${faroConfig.environment.baseUrl}/workspace/${projectID}/${channelID}/${acPage}`
	);
}
