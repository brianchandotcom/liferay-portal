/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {MessageBoardsPage} from './MessageBoardsPage';

export class MessageBoardsEditThreadPage {
	readonly allFilesReadyToBeSavedMessage: Locator;
	readonly attachmentCollapse: Locator;
	readonly bodyFrameLocator: FrameLocator;
	readonly bodyTextBox: Locator;
	readonly fileSelector: Locator;
	readonly messageBoardsPage: MessageBoardsPage;
	readonly page: Page;
	readonly publishButton: Locator;
	readonly subjectSelector: Locator;
	readonly submitForWorkflowButton: Locator;

	constructor(page: Page) {
		this.allFilesReadyToBeSavedMessage = page.getByText(
			'All files ready to be saved.'
		);
		this.attachmentCollapse = page.getByRole('button', {
			name: 'Attachments',
		});
		this.bodyFrameLocator = page.frameLocator('iframe');
		this.bodyTextBox = this.bodyFrameLocator.getByRole('textbox');
		this.fileSelector = page.getByRole('button', {name: 'Select File'});
		this.messageBoardsPage = new MessageBoardsPage(page);
		this.page = page;
		this.publishButton = page.getByRole('button', {
			exact: true,
			name: 'Publish',
		});
		this.subjectSelector = page.getByLabel('Subject');
		this.submitForWorkflowButton = page.getByRole('button', {
			exact: true,
			name: 'Submit for Workflow',
		});
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.messageBoardsPage.goto(siteUrl);

		await this.messageBoardsPage.goToCreateNewThread();
	}

	async publishNewBasicThread(
		subject: string,
		body: string,
		siteUrl?: Site['friendlyUrlPath'],
		filePath?: string
	) {
		await this.goto(siteUrl);

		await this.subjectSelector.fill(subject);
		await this.bodyTextBox.fill(body);

		if (filePath) {
			await this.selectFile(filePath);
		}

		await this.publishButton.click();
	}

	async publishNewThreadForWorkflow(subject: string, body: string) {
		await this.messageBoardsPage.newThreadButton.click();
		await this.subjectSelector.fill(subject);
		await this.bodyTextBox.fill(body);
		await this.submitForWorkflowButton.click();
	}

	async selectFile(filePath: string) {
		const isExpanded = await this.attachmentCollapse.evaluate(
			(element) => element.getAttribute('aria-expanded') === 'true'
		);
		if (!isExpanded) {
			await this.attachmentCollapse.click();
		}
		const fileChooserPromise = this.page.waitForEvent('filechooser');
		await this.fileSelector.click();
		const fileChooser = await fileChooserPromise;
		await fileChooser.setFiles(filePath);
		await this.allFilesReadyToBeSavedMessage.waitFor();
	}
}
