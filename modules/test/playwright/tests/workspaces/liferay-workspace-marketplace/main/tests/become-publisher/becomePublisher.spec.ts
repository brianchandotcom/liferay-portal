/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../../../utils/clickAndExpectToBeVisible';
import {marketplacePagesTest} from '../../fixtures/marketplacePages';
import {marketplaceSiteFixture} from '../../fixtures/marketplaceSite';

export const test = mergeTests(marketplaceSiteFixture, marketplacePagesTest);

test.describe('Customers can become a publisher', () => {
	test('LPD-35456 Becoming a Liferay Marketplace publisher', async ({
		becomePublisherPage,
		marketplace,
		page,
	}) => {
		const becomePublisher = page.getByRole('link', {
			name: 'Become a Publisher',
		});

		await expect(becomePublisher).toBeVisible();

		await becomePublisher.click();
		await becomePublisherPage.goto(marketplace.friendlyUrlPath);

		await expect(becomePublisherPage.becomePublisherTitle).toBeVisible();
		await expect(becomePublisherPage.requestAccountButton).toBeEnabled();

		await clickAndExpectToBeVisible({
			target: becomePublisherPage.requestAccountTitle,
			trigger: becomePublisherPage.requestAccountButton,
		});

		await becomePublisherPage.firstName.fill('Joana');
		await becomePublisherPage.lastName.fill('Grego');
		await becomePublisherPage.phone.fill('11111111111');
		await becomePublisherPage.description.fill('This is my last dance.');

		await page.keyboard.press('Tab');

		await expect(becomePublisherPage.continueButton).toBeEnabled();

		await clickAndExpectToBeVisible({
			target: becomePublisherPage.completePublisherTitle,
			trigger: becomePublisherPage.continueButton,
		});

		await expect(becomePublisherPage.requestAccountButton).toBeEnabled();

		await clickAndExpectToBeVisible({
			target: becomePublisherPage.thankYou,
			trigger: becomePublisherPage.requestAccountButton,
		});
	});
});
