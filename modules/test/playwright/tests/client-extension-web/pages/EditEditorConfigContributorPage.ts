/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {EditClientExtensionsPage} from './EditClientExtensionsPage';

export class EditEditorConfigContributorPage extends EditClientExtensionsPage {
	readonly aiCreatorEditorToolbarButton: Locator;
	readonly editorConfigKeysInput: Locator;
	readonly editorNamesInput: Locator;
	readonly portletNamesInput: Locator;
	readonly urlInput: Locator;

	constructor(page: Page) {
		super(page, 'editorConfigContributor');

		this.aiCreatorEditorToolbarButton =
			page.getByTitle('Create AI Content');
		this.urlInput = page.locator(`#${this.portletId}_url`);
		this.portletNamesInput = page.locator(
			`[name=${this.portletId}_portletNames]`
		);
		this.editorNamesInput = page.locator(
			`[name=${this.portletId}_editorNames]`
		);
		this.editorConfigKeysInput = page.locator(
			`[name=${this.portletId}_editorConfigKeys]`
		);
	}
}
