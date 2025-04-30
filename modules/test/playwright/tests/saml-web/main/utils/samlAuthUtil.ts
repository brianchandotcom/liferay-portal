/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

export async function clickSignInButton(page: Page, idpSelection?: string) {
	const signInButton = await page.getByRole('button', {
		name: 'Sign In',
	});

	await signInButton.click();

	// An undefined IdP selection means we assume only one IdP available, so we
	// are redirected automatically.  Else, IdP is defined, so select it.

	if (idpSelection === undefined) {
		await page
			.getByText('Redirecting to your identity provider...')
			.waitFor({timeout: 30 * 1000});
	}
	else {
		await page
			.getByRole('paragraph')
			.getByText('Please select your identity provider.')
			.waitFor({timeout: 30 * 1000});

		await page.getByLabel('Identity Provider').selectOption(idpSelection);

		await page
			.locator('#content')
			.getByRole('button', {
				exact: true,
				name: 'Sign In',
			})
			.click();
	}
}

export async function performSpInitiatedSSO(
	browser,
	emailAddress: string,
	spDomain: string,
	assertSuccessful: boolean = true,
	idpSelection?: string
): Promise<Page> {
	const newPage = await browser.newPage({
		baseURL: spDomain,
	});

	await newPage.goto(spDomain);

	await clickSignInButton(newPage, idpSelection);

	await newPage.getByLabel('Email Address').waitFor({timeout: 30 * 1000});

	// Authenticate on IdP instance

	await newPage.waitForTimeout(1000);
	await newPage.getByLabel('Email Address').fill(emailAddress);
	await newPage.getByLabel('Password').fill('test');
	await newPage.getByLabel('Remember Me').check();
	await newPage.getByRole('button', {name: 'Sign In'}).click();

	if (assertSuccessful) {

		// Wait for authentication and redirection to complete

		await newPage
			.getByTitle('User Profile Menu')
			.waitFor({timeout: 30 * 1000});
	}

	return newPage;
}

export async function performIdpInitiatedSSO(
	browser,
	emailAddress: string,
	idpDomain: string,
	relayState: string,
	spName: string
): Promise<Page> {
	const newPage = await browser.newPage({
		baseURL: idpDomain,
	});

	await newPage.goto(
		`${idpDomain}/c/portal/saml/sso?entityId=${spName}&RelayState=${relayState}`
	);

	await newPage.getByLabel('Email Address').waitFor({timeout: 30 * 1000});

	// Authenticate on IdP instance

	await newPage.waitForTimeout(1000);
	await newPage.getByLabel('Email Address').fill(emailAddress);
	await newPage.getByLabel('Password').fill('test');
	await newPage.getByRole('button', {name: 'Sign In'}).click();

	// Wait for authentication and redirection to complete

	await newPage.getByTitle('User Profile Menu').waitFor({timeout: 30 * 1000});

	return newPage;
}
