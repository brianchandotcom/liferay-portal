/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

export class PortletPublishToLivePage {
	readonly page: Page;
	readonly publishToLiveIframe: FrameLocator;
	readonly publishToLiveSuccessStatus: Locator;
	readonly publishToLiveButton: Locator;
	readonly publishToLiveIframeButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.publishToLiveIframe = this.page.frameLocator(
			'iframe[title="Publish to Live"]'
		);

		this.publishToLiveSuccessStatus = this.publishToLiveIframe
			.locator('[data-qa-id="row"]')
			.first()
			.getByText('Successful');

		this.publishToLiveButton = this.page.getByRole('button', {
			name: 'Publish to Live',
		});

		this.publishToLiveIframeButton = this.publishToLiveIframe.getByRole(
			'button',
			{
				name: 'Publish to Live',
			}
		);
	}

	async goToPortletAdvancedStagings() {
		await this.publishToLiveButton.click();

		await this.publishToLiveIframe
			.getByRole('link', {name: 'Switch to Advanced Publish'})
			.click();
	}
}
