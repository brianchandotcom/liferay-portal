/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class UserAssociatedDataJournalPage {
	readonly articleCreator: (name: string, title: string) => Locator;
	readonly articleLink: (title: string) => Locator;
	readonly articleTitleInput: Locator;
	readonly optionsButton: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.articleCreator = (name: string, title: string) =>
			this.articleLink(title).locator('../..').getByText(name);
		this.articleLink = (title: string) => page.getByText(`${title}`);
		this.articleTitleInput = page.locator(
			'.article-content-title .input-group-item input'
		);
		this.optionsButton = page.getByRole('button', {name: 'Options'});
		this.page = page;
	}
}
