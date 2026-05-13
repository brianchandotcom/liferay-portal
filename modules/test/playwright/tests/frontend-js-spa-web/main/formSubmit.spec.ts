/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pageViewModePagesTest} from '../../../fixtures/pageViewModePagesTest';
import {productMenuPageTest} from '../../../fixtures/productMenuPageTest';
import getRandomString from '../../../utils/getRandomString';
import isSPAEnabled from '../../../utils/isSPAEnabled';
import {journalPagesTest} from '../../journal-web/main/fixtures/journalPagesTest';
import {waitForAlert} from '../../../utils/waitForAlert';

export const test = mergeTests(
	loginTest(),
	apiHelpersTest,
	isolatedSiteTest,
	journalPagesTest,
	pageEditorPagesTest,
	productMenuPageTest,
	pageViewModePagesTest
);

test(
	'Assert form submit works correctly on Safari browser',
	{
		tag: '@LPD-26285',
	},
	async ({
		apiHelpers,
		journalEditArticlePage,
		page,
		site,
		widgetPagePage,
	}) => {
		const {devices, webkit} = require('playwright');
		const safari = devices['Desktop Safari'];
		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			options: {
				type: 'portlet',
			},
			title: getRandomString(),
		});

		await test.step('Naviagate to root page', async () => {
			await page.goto('/');
		});

		await test.step('Asset SPA is enabled', async () => {
			expect(isSPAEnabled({page})).toBeTruthy();
		});

		await test.step('Add WCD and content with a form including two submit buttons', async () => {
			await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

			await widgetPagePage.addPortlet('Web Content Display');

			const addButton = page
				.locator(
					'[id^="_com_liferay_journal_content_web_portlet_JournalContentPortlet_INSTANCE_"]'
				)
				.and(page.getByRole('button'));

			await addButton.hover();
			await addButton.click();

			await page
				.getByRole('menuitem',  {exact: true, name: 'Basic Web Content'})
				.click();

			const title = getRandomString();

			await journalEditArticlePage.fillTitle(title);

			await page.getByLabel('Source', {exact: true}).click();

			const content =
				'<form action="" method="post"><input type="submit" value="Button A" /> <input type="submit" value="Button B" /></form>';

			await page
				.locator('.ck-source-editing-area')
				.getByRole('textbox')
				.fill(content);

			await page.getByLabel('Source', {exact: true}).click();

			await journalEditArticlePage.publishArticle(true);

			await waitForAlert(page, `Success:Your request completed successfully.`);

		});

		await test.step('Set Safari as the browser and check submit buttons in form', async () => {
			const browser = await webkit.launch();

			const baseURL = new URL(page.url()).origin;

			const context = await browser.newContext({
				...safari,
				baseURL,
			});

			const incognitoPage = await context.newPage();

			await incognitoPage.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyURL}`
			);

			await incognitoPage.evaluate(() => {
				(window as any).__capturedButtonValue = null;

				(window as any).Liferay.once('beforeNavigate', function () {
					(window as any).__capturedButtonValue =
						(window as any).Liferay.SPA
							.__capturedFormButtonElement__?.value ?? null;
				});
			});

			await incognitoPage.getByRole('button', {name: 'Button B'}).click();

			await incognitoPage.waitForFunction(
				() => (window as any).__capturedButtonValue !== null
			);

			const capturedValue = await incognitoPage.evaluate(
				() => (window as any).__capturedButtonValue
			);

			expect(capturedValue).toBe('Button B');

			// Dispose context once it's no longer needed.

			await context.close();
		});
	}
);
