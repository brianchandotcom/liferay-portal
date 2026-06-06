/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {messageBoardsPagesTest} from '../../../fixtures/messageBoardsTest';
import {MessageBoardsEditThreadPage} from '../../../pages/message-boards/MessageBoardsEditThreadPage';
import getRandomString from '../../../utils/getRandomString';

const test = mergeTests(isolatedSiteTest, loginTest(), messageBoardsPagesTest);

async function addAttachment(
	messageBoardsEditThreadPage: MessageBoardsEditThreadPage,
	name: string,
	page: Page
) {
	const expanded =
		await messageBoardsEditThreadPage.attachmentCollapse.evaluate(
			(element) => element.getAttribute('aria-expanded') === 'true'
		);

	if (!expanded) {
		await messageBoardsEditThreadPage.attachmentCollapse.click();
	}

	const fileChooserPromise = page.waitForEvent('filechooser');

	await messageBoardsEditThreadPage.fileSelector.click();

	const fileChooser = await fileChooserPromise;

	await fileChooser.setFiles({
		buffer: Buffer.from(getRandomString()),
		mimeType: 'text/plain',
		name,
	});

	await messageBoardsEditThreadPage.allFilesReadyToBeSavedMessage.waitFor();
}

test('Can add a thread with multiple attachments', async ({
	messageBoardsEditThreadPage,
	page,
	site,
}) => {
	const firstFileName = `${getRandomString()}.txt`;
	const secondFileName = `${getRandomString()}.txt`;

	await messageBoardsEditThreadPage.goto(site.friendlyUrlPath);

	await messageBoardsEditThreadPage.subjectSelector.fill(getRandomString());
	await messageBoardsEditThreadPage.bodyTextBox.fill(getRandomString());

	await addAttachment(messageBoardsEditThreadPage, firstFileName, page);
	await addAttachment(messageBoardsEditThreadPage, secondFileName, page);

	await messageBoardsEditThreadPage.publishButton.click();

	// Both attachments are listed on the published thread

	await expect(
		page.getByRole('link', {name: new RegExp(firstFileName)})
	).toBeVisible();
	await expect(
		page.getByRole('link', {name: new RegExp(secondFileName)})
	).toBeVisible();
});

test(
	'Can download a thread attachment',
	{tag: '@LPS-136914'},
	async ({messageBoardsEditThreadPage, page, site}) => {
		const fileName = `${getRandomString()}.txt`;

		await messageBoardsEditThreadPage.goto(site.friendlyUrlPath);

		await messageBoardsEditThreadPage.subjectSelector.fill(
			getRandomString()
		);
		await messageBoardsEditThreadPage.bodyTextBox.fill(getRandomString());

		await addAttachment(messageBoardsEditThreadPage, fileName, page);

		await messageBoardsEditThreadPage.publishButton.click();

		// The attachment can be downloaded from the published thread

		const downloadPromise = page.waitForEvent('download');

		await page.getByRole('link', {name: new RegExp(fileName)}).click();

		const download = await downloadPromise;

		expect(download.suggestedFilename()).toBe(fileName);
	}
);
