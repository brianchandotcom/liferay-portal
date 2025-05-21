/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {EditClientExtensionsPage} from './EditClientExtensionsPage';

export class EditCustomElementPage extends EditClientExtensionsPage {
	readonly addCSSURLButton: Locator;
	readonly addJavaScriptURLButton: Locator;
	readonly cssURLInput: Locator;
	readonly deleteCSSURLButton: Locator;
	readonly deleteJavaScriptURLButton: Locator;
	readonly htmlElementNameInput: Locator;
	readonly javaScriptURLInput: Locator;

	constructor(page: Page) {
		super(page, 'customElement');

		this.addCSSURLButton = page.locator(
			`#${this.portletId}__cssURLs_field button.add-row`
		);
		this.addJavaScriptURLButton = page.locator(
			`#${this.portletId}__urls_field button.add-row`
		);
		this.cssURLInput = page.locator(`[name=${this.portletId}_cssURLs]`);
		this.deleteCSSURLButton = page.locator(
			`#${this.portletId}__cssURLs_field button.delete-row`
		);
		this.deleteJavaScriptURLButton = page.locator(
			`#${this.portletId}__urls_field button.delete-row`
		);
		this.htmlElementNameInput = page.locator(
			`[name=${this.portletId}_htmlElementName]`
		);
		this.javaScriptURLInput = page.locator(`[name=${this.portletId}_urls]`);
	}
}
