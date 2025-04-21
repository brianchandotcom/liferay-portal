/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {PageEditorPage} from '../../../pages/layout-content-page-editor-web/PageEditorPage';

export class RemoteStagingPage {
	readonly page: Page;
	readonly pageEditorPage: PageEditorPage;
	readonly publishToRemoteLiveFrame: FrameLocator;
	readonly publishToRemoteLiveButton: Locator;
	readonly remoteLiveButton: Locator;
	readonly remoteHostTextBox: Locator;
	readonly remotePortTextBox: Locator;
	readonly remoteSiteIdTextBox: Locator;
	readonly publishSuccessfulLabel: Locator;

	constructor(page: Page) {
		this.page = page;
		this.pageEditorPage = new PageEditorPage(page);
		this.publishToRemoteLiveFrame = this.page.frameLocator(
			'iframe[name="_com_liferay_staging_bar_web_portlet_StagingBarPortlet_publishLatestChangesDialog_iframe_"]'
		);
		this.publishToRemoteLiveButton =
			this.publishToRemoteLiveFrame.getByRole('button', {
				name: 'Publish to Remote Live',
			});
		this.remoteLiveButton = this.page.getByLabel('Remote Live:');
		this.remoteHostTextBox = this.page.getByLabel('Remote Host/IP');
		this.remotePortTextBox = this.page.getByLabel('Remote Port');
		this.remoteSiteIdTextBox = this.page.getByLabel('Remote Site ID');
		this.publishSuccessfulLabel =
			this.publishToRemoteLiveFrame.getByText('Successful');
	}

	async publishToLive({
		layoutFriendlyURL,
		siteFriendlyUrl,
	}: {
		layoutFriendlyURL: string;
		siteFriendlyUrl: string;
	}) {
		await this.page.goto(`/web${siteFriendlyUrl}${layoutFriendlyURL}`);
		await this.pageEditorPage.publishToLiveButton.click();
		await this.page.waitForTimeout(5000)
		await this.publishToRemoteLiveButton.click();
		await expect(this.publishSuccessfulLabel).toBeVisible();
	}
}
