/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

export class UserAssociatedDataSiteStagingPage {
	readonly stagingFrame: FrameLocator;
	readonly stagingFramePublishToLiveButton: Locator;
	readonly stagingFrameSuccessfulStatusCell: Locator;
	readonly stagingLink: Locator;
	readonly stagingMenuItem: Locator;
	readonly webContentCheckbox: Locator;

	constructor(page: Page) {
		this.stagingFrame = page.frameLocator('iframe[title="Staging"]');
		this.stagingFramePublishToLiveButton = this.stagingFrame.getByRole(
			'button',
			{
				name: 'Publish to Live',
			}
		);
		this.stagingFrameSuccessfulStatusCell = this.stagingFrame.getByRole(
			'cell',
			{name: 'Successful'}
		);
		this.stagingLink = page.getByTestId('staging');
		this.stagingMenuItem = page.getByRole('menuitem', {
			name: 'Staging',
		});
		this.webContentCheckbox = page.getByTestId(
			'staged--staged-portlet_com_liferay_journal_web_portlet_JournalPortlet--'
		);
	}
}
