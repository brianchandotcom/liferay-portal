/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {redirectPagesTest} from '../../fixtures/redirectPagesTest';
import {liferayConfig} from '../../liferay.config';

export const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	redirectPagesTest
);

test('Ensure that a redirect alias will override a redirect pattern', async ({
	apiHelpers,
	page,
	redirectPage,
	site,
}) => {
	const aliasPage = await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: 'Alias Page',
	});

	const patternPage = await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: 'Pattern Page',
	});

	await redirectPage.goto(site.friendlyUrlPath);

	await redirectPage.addRedirect(
		'test/source/url',
		`${liferayConfig.environment.baseUrl}/web/${site.name}${aliasPage.friendlyURL}`,
		false
	);

	await redirectPage.addRedirectPattern(
		'(.*)/source/url$',
		`${liferayConfig.environment.baseUrl}/web/${site.name}${patternPage.friendlyURL}`
	);

	await page.goto(`/web/${site.name}/test/source/url`);

	await expect(page.url()).toContain(aliasPage.friendlyURL);

	await redirectPage.goto(site.friendlyUrlPath);

	await redirectPage.deleteRedirect('test/source/url');

	await page.goto(`/web/${site.name}/test/source/url`);

	await expect(page.url()).toContain(patternPage.friendlyURL);
});