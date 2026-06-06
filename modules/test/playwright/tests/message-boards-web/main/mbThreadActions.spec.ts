/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {messageBoardsPagesTest} from '../../../fixtures/messageBoardsTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	messageBoardsPagesTest
);

function threadActionItem(page, name) {
	return page.locator('.dropdown-menu').getByText(name, {exact: true});
}

async function openThreadAction({headline, name, page}) {
	await page.getByRole('link', {name: headline}).click();

	await clickAndExpectToBeVisible({
		autoClick: true,
		target: threadActionItem(page, name),
		trigger: page.locator('.panel-heading .dropdown-toggle'),
	});
}

test('Can edit a thread subject and body', async ({
	apiHelpers,
	messageBoardsEditThreadPage,
	messageBoardsPage,
	page,
	site,
}) => {
	const headline = getRandomString();

	await apiHelpers.headlessDelivery.postMessageBoardThread({
		articleBody: getRandomString(),
		headline,
		siteId: site.id,
	});

	await messageBoardsPage.goto(site.friendlyUrlPath);

	await openThreadAction({headline, name: 'Edit', page});

	const editedSubject = getRandomString();
	const editedBody = getRandomString();

	await messageBoardsEditThreadPage.subjectSelector.fill(editedSubject);
	await messageBoardsEditThreadPage.bodyTextBox.fill(editedBody);
	await messageBoardsEditThreadPage.publishButton.click();

	await expect(page.getByTestId('headerTitle')).toHaveText(editedSubject);
	await expect(page.getByText(editedBody)).toBeVisible();
});

test('Can cancel editing a thread', async ({
	apiHelpers,
	messageBoardsEditThreadPage,
	messageBoardsPage,
	page,
	site,
}) => {
	const headline = getRandomString();

	await apiHelpers.headlessDelivery.postMessageBoardThread({
		articleBody: getRandomString(),
		headline,
		siteId: site.id,
	});

	await messageBoardsPage.goto(site.friendlyUrlPath);

	await openThreadAction({headline, name: 'Edit', page});

	await messageBoardsEditThreadPage.subjectSelector.fill(getRandomString());

	await page.getByRole('button', {name: 'Cancel'}).click();

	// The original subject is preserved

	await expect(page.getByTestId('headerTitle')).toHaveText(headline);
});
