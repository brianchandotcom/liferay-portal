/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

import POM from '../../../utils/POM';
import {EEditorType, waitForEditor} from '../../../utils/waitFor';
import {AlloyPage} from './ckeditor4/AlloyPage';
import {ClassicPage as CKEditor4ClassicPage} from './ckeditor4/ClassicPage';

export enum TabName {
	ALLOY = 'Alloy',
	CLASSIC = 'Classic',
	LEGACY = 'Legacy',
	REACT = 'React',
}

export interface CKEditorSamplePageTab {}

export class CKEditorSamplePage extends POM {
	constructor(page: Page, url: string) {
		super(page, url);
	}

	async gotoTab<T extends CKEditorSamplePageTab>(
		tabName: TabName
	): Promise<T | null> {
		const navLink = this.page
			.locator(
				'.portlet-ckeditor-sample .lfr-tooltip-scope:nth-child(1) .navbar'
			)
			.getByRole('link', {exact: true, name: tabName});

		await navLink.click();

		await expect(navLink).toHaveClass(/active/);

		const editorType =
			tabName === TabName.ALLOY
				? EEditorType.ALLOYEDITOR
				: EEditorType.CKEDITOR4;

		await waitForEditor({editorType, page: this.page});

		let visitedPage = null;

		switch (tabName) {
			case TabName.ALLOY:
				visitedPage = new AlloyPage(this.page);
				break;

			case TabName.CLASSIC:
				visitedPage = new CKEditor4ClassicPage(this.page);
				break;

			default:
				break;
		}

		return visitedPage as unknown as T;
	}

	override async waitFor() {
		await waitForEditor({
			editorType: EEditorType.CKEDITOR4,
			page: this.page,
		});
	}
}
