/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class AlloyPage {
	readonly alloyEditorContainer: Locator;
	readonly alloyEditorToolbarContainer: Locator;
	readonly balloonEditorContainer: Locator;

	constructor(page: Page) {
		this.alloyEditorContainer = page.locator('div[id$=sampleAlloyEditor]');
		this.alloyEditorToolbarContainer = page.locator('.ae-toolbars');

		this.balloonEditorContainer = page.locator(
			'div[id$=sampleBalloonEditor]'
		);
	}
}
