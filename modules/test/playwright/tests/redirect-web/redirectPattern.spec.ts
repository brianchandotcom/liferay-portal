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
import {waitForAlert} from '../../utils/waitForAlert';

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

test('Ensure that a redirect pattern can be updated', async ({
	apiHelpers,
	page,
	redirectPage,
	site,
}) => {
	const destinationPage = await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: 'Destination Page',
	});

	await redirectPage.goto(site.friendlyUrlPath);

	await redirectPage.addRedirectPattern(
		'(.*)/source/url$',
		`${liferayConfig.environment.baseUrl}/web/${site.name}/invalid-page`
	);

	await redirectPage.addRedirectPattern(
		'(.*)/source/url$',
		`${liferayConfig.environment.baseUrl}/web/${site.name}${destinationPage.friendlyURL}`
	);

	await page.goto(`/web/${site.name}/test/source/url`);

	await expect(page.url()).toContain(destinationPage.friendlyURL);
});

test('Ensure that a long destination URL is persisted and truncated by ellipsis', async ({
	page,
	redirectPage,
	site,
}) => {
	await redirectPage.goto(site.friendlyUrlPath);

	await redirectPage.addRedirectPattern(
		'(.*)/source/url$',
		'/web/test-site/$1/000000000000000000000000000000000'
	);

	await expect(page.getByLabel('Destination URL')).toHaveValue(
		'/web/test-site/$1/000000000000000000000000000000000'
	);

	await expect(page.getByLabel('Destination URL')).toHaveCSS(
		'text-overflow',
		'ellipsis'
	);
});

test('Ensure that an error displays when entering an invalid destination URL', async ({
	page,
	redirectPage,
	site,
}) => {
	await redirectPage.goto(site.friendlyUrlPath);

	await redirectPage.addRedirectPattern('(.*)/source/url$', '(', false);

	await expect(page.getByText('Please enter a valid URL.')).toBeVisible();
});

test('Ensure that an error displays when entering an invalid pattern', async ({
	page,
	redirectPage,
	site,
}) => {
	await redirectPage.goto(site.friendlyUrlPath);

	await redirectPage.addRedirectPattern('(', '/web/guest/home', false);

	await waitForAlert(
		page,
		'Error:Patterns must be valid regular expressions.',
		{
			type: 'danger',
		}
	);
});
