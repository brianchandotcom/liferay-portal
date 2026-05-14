/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

import POM from '../../../utils/POM';
import {EEditorType, waitForEditor} from '../../../utils/waitFor';
import {SubTabName} from '../../frontend-editor-ckeditor-sample-web/pages/CKEditorSamplePage';
import {BalloonPage} from '../pages/BalloonPage';
import {ClassicPage} from '../pages/ClassicPage';
import {InputLocalizedPage} from '../pages/InputLocalizedPage';

export class CKEditor5SamplePage extends POM {
	constructor(page: Page, url: string) {
		super(page, url);
	}

	async gotoTab<T>(subTabName: SubTabName): Promise<T | null> {
		const navLink = this.page
			.locator(
				'.portlet-ckeditor5-sample .lfr-tooltip-scope:nth-child(1) .navbar'
			)
			.getByRole('link', {exact: true, name: subTabName});

		await navLink.click();

		await expect(navLink).toHaveClass(/active/);

		await waitForEditor({
			editorType: EEditorType.CKEDITOR5,
			page: this.page,
		});

		let visitedPage = null;

		switch (subTabName) {
			case SubTabName.ADVANCED_CLASSIC:
			case SubTabName.BASIC_CLASSIC:
			case SubTabName.REACT:
			case SubTabName.REACT_PLUS_CET:
				visitedPage = new ClassicPage(this.page);
				break;

			case SubTabName.BALLOON:
				visitedPage = new BalloonPage(this.page);
				break;

			case SubTabName.INPUT_LOCALIZED:
				visitedPage = new InputLocalizedPage(this.page);
				break;

			default:
				break;
		}

		return visitedPage as unknown as T;
	}

	override async waitFor() {
		await waitForEditor({
			editorType: EEditorType.CKEDITOR5,
			page: this.page,
		});
	}
}
