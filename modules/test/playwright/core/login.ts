/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	APIRequestContext,
	Cookie,
	Page,
	expect,
	request,
} from '@playwright/test';

import {clearAuthToken, getHeader, readAuthToken} from './authToken';
import {liferayConfig} from './liferayConfig';

const LOGIN_PORTLET_NAMESPACE = '_com_liferay_login_web_portlet_LoginPortlet_';

export type LoginScreenName =
	| 'demo.company.admin'
	| 'demo.organization.owner'
	| 'demo.unprivileged'
	| 'test'
	| 'user';

export const userData = {
	'demo.company.admin': {
		name: 'Demo',
		password: 'demo',
		surname: 'Company Admin',
	},
	'demo.organization.owner': {
		name: 'Demo',
		password: 'demo',
		surname: 'Organization Owner',
	},
	'demo.unprivileged': {
		name: 'Demo',
		password: 'demo',
		surname: 'Unprivileged',
	},
	'test': {
		name: 'Test',
		password: liferayConfig.environment.password,
		surname: 'Test',
	},
	'user': {
		password: liferayConfig.environment.password,
	},
};

interface LoginOptions {
	domain?: string;
	loginUrl?: string;
	page: Page;
	password?: string;
	rememberMe?: boolean;
	screenName: LoginScreenName | string;
}

export async function createPortalSession(): Promise<APIRequestContext> {
	const apiRequestContext = await request.newContext({
		baseURL: liferayConfig.environment.baseUrl,
	});

	const signInPageResponse = await apiRequestContext.get('/c/portal/login');

	const loginForm = (await signInPageResponse.text()).match(
		new RegExp(`<form[^>]*id="${LOGIN_PORTLET_NAMESPACE}loginForm"[^>]*>`)
	);

	const action = loginForm?.[0].match(/action="([^"]+)"/)?.[1];

	if (!action) {
		throw new Error('Unable to find the login form in the sign in page');
	}

	const loginResponse = await apiRequestContext.post(
		action.replace(/&amp;/g, '&'),
		{
			form: {
				[`${LOGIN_PORTLET_NAMESPACE}login`]: 'test@liferay.com',
				[`${LOGIN_PORTLET_NAMESPACE}password`]: userData.test.password,
			},
			maxRedirects: 0,
		}
	);

	const location = loginResponse.headers()['location'];

	if (!location?.endsWith('/c')) {
		throw new Error(
			`Unable to sign in through the sign in page: HTTP ${loginResponse.status()} redirected to "${location}"`
		);
	}

	return apiRequestContext;
}

export async function performLogin(
	page: Page,
	screenName: LoginScreenName | string,
	baseUrl = '/',
	domain = '@liferay.com',
	rememberMe = true
): Promise<Cookie[]> {
	const {name, password, surname} = userData[screenName];

	await page.goto(baseUrl);

	await page.getByRole('button', {name: 'Sign In'}).click();

	const emailAddressInput = page.getByLabel('Email Address');

	await expect(emailAddressInput).toBeVisible();

	await emailAddressInput.fill(`${screenName}${domain}`);

	await expect(emailAddressInput).toHaveValue(`${screenName}${domain}`);

	await page.getByLabel('Password').fill(password);
	await page.getByLabel('Remember Me').setChecked(rememberMe);

	await page
		.locator('form.sign-in-form')
		.getByRole('button', {name: 'Sign In'})
		.click();

	await expect(page.getByLabel(`${name} ${surname}`)).toBeVisible({
		timeout: 30 * 1000,
	});

	await readAuthToken(page);

	return await page.context().cookies();
}

export async function performLoginViaApi({
	domain = '@liferay.com',
	loginUrl = liferayConfig.environment.baseUrl,
	page,
	password = undefined,
	rememberMe = true,
	screenName,
}: LoginOptions) {
	const resolvedPassword =
		password ?? userData[screenName || 'test'].password;

	const params = new URLSearchParams({
		login: `${screenName}${domain}`,
		password: resolvedPassword,
		rememberMe: String(rememberMe),
	});

	try {
		await page.goto(loginUrl);

		clearAuthToken(page);

		const url = `${loginUrl}/c/portal/login`;

		await expect
			.poll(async () => {
				const response = await page.request.post(url, {
					data: params.toString(),
					headers: await getHeader(
						page,
						'application/x-www-form-urlencoded'
					),
				});

				return response.status();
			})
			.toBe(200);

		await page.goto(loginUrl);

		await readAuthToken(page);
	}
	catch (error) {
		error.message = `Login via API failed\n\n${error.message}`;

		throw error;
	}

	return await page.context().cookies();
}

export async function performLogout(page: Page) {
	await page.goto('/c/portal/logout');

	await page.waitForURL((url) => !url.pathname.endsWith('/c/portal/logout'));

	clearAuthToken(page);
}

export async function performUserSwitch(
	page: Page,
	screenName: LoginScreenName | string
) {
	await performLogout(page);

	await performLogin(page, screenName);
}

export async function performUserSwitchViaApi(
	page: Page,
	screenName: LoginScreenName | string
) {
	await page.waitForURL((url) => !url.pathname.endsWith('/c/portal/logout'));

	await performLoginViaApi({page, screenName});
}
