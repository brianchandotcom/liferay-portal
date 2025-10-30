/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

import POM from '../../../../utils/POM';
import {EEditorType, waitForEditor} from "../../../../utils/waitFor";

export enum TabName {
	CK_EDITOR_4 = 'CKEditor 4',
	CK_EDITOR_5 = 'CKEditor 5',
}

export enum SubTabName {
	ADVANCED_CLASSIC = 'Advanced Classic',
	ALLOY = 'Alloy',
	BALLOON = 'Balloon',
	BASIC_CLASSIC = 'Basic Classic',
	CLASSIC = 'Classic',
	INPUT_LOCALIZED = 'Input Localized',
	LEGACY = 'Legacy',
	REACT = 'React',
	REACT_PLUS_CET = 'React + CET',
}

export class CKEditorSamplePage extends POM {
	constructor(page: Page, url: string) {
		super(page, url);
	}

	async selectTab(tabName: TabName, subTabName: SubTabName) {
		const navLink = this.page
			.locator('.portlet-ckeditor-sample .lfr-tooltip-scope:nth-child(1) .navbar')
			.getByRole('link', {exact: true, name: tabName})

		await navLink.click();

		await expect(navLink).toHaveClass(/active/);

		let editorType = EEditorType.CKEDITOR5;

		if (tabName === TabName.CK_EDITOR_4) {
			editorType = EEditorType.CKEDITOR4;
		}

		await waitForEditor({editorType, page: this.page});

		const subNavLink = this.page
			.locator('.portlet-ckeditor-sample .lfr-tooltip-scope:nth-child(2) .navbar')
			.getByRole('link', {exact: true, name: subTabName})

		await subNavLink.click();

		await expect(subNavLink).toHaveClass(/active/);

		if (subTabName === SubTabName.ALLOY) {
			editorType = EEditorType.ALLOYEDITOR;
		}

		await waitForEditor({editorType, page: this.page});
	}

	override async waitFor() {
		await waitForEditor({page: this.page});
	}
}
