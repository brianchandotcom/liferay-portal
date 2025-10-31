/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {CKEditorSamplePageTab} from '../CKEditorSamplePage';

export class BalloonPage implements CKEditorSamplePageTab {
	readonly editable: Locator;
	readonly toolbar: Locator;

	constructor(page: Page) {
		this.editable = page.locator('.ck-editor__editable');
		this.toolbar = page.getByLabel('Editor contextual toolbar');
	}
}
