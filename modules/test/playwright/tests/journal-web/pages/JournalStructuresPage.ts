/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../../utils/portletUrls';

export class JournalStructuresPage {
	readonly page: Page;

	readonly newButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.newButton = page.getByText('New', {exact: true});
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.journalStructures}`
		);
	}

	async goToCreateNewStructure() {
		await this.goto();
		await this.newButton.click();
	}

	async goToJournalStructureAction(action: string, title: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				exact: true,
				name: action,
			}),
			trigger: this.page
				.getByRole('row', {name: `${title}`})
				.getByLabel('Show Actions', {exact: true}),
		});
	}
}
