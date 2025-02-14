/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {PORTLET_URLS} from '../../../utils/portletUrls';
import {DataTablePage} from '../../account-admin-web/DataTablePage';

export class DocumentLibraryPage {
	readonly documentsTable: DataTablePage;
	readonly page: Page;
	readonly shareDocumentLink: Locator;
	readonly sharingDialogueIFrame: FrameLocator;
	readonly sharingDialogueInput: Locator;
	readonly sharingDialogueShareButton: Locator;

	constructor(page: Page) {
		this.documentsTable = new DataTablePage(
			page,
			page.locator(
				'#_com_liferay_document_library_web_portlet_DLAdminPortlet_entriesSearchContainer'
			)
		);
		this.page = page;
		this.shareDocumentLink = page.getByRole('menuitem', {name: 'Share'});
		this.sharingDialogueIFrame = page.frameLocator(
			'iframe[title="Share Attachment"]'
		);
		this.sharingDialogueInput = this.sharingDialogueIFrame.getByPlaceholder(
			'Enter name or email address.'
		);
		this.sharingDialogueShareButton = this.sharingDialogueIFrame.getByText(
			'Share',
			{exact: true}
		);
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.documentLibrary}`
		);
	}
}
