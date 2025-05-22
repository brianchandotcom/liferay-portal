/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import POM from '../../../../utils/POM';

export class EditorSamplesPage extends POM {
	readonly alloyEditorContainer: Locator;
	readonly alloyEditorToolbarContainer: Locator;
	readonly balloonEditorContainer: Locator;

	constructor(page: Page, url: string) {
		super(page, url);

		this.alloyEditorContainer = page.locator('div[id$=sampleAlloyEditor]');
		this.alloyEditorToolbarContainer = page.locator('.ae-toolbars');

		this.balloonEditorContainer = page.locator(
			'div[id$=sampleBalloonEditor]'
		);
	}

	async selectTab({tabLabel}: {tabLabel: string}) {
		const tab = this.page.locator('.nav-link').filter({hasText: tabLabel});

		await tab.click();
	}

	override async waitFor() {}
}
