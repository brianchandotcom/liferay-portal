/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

export class SLAPage {
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
	}

	// LPD-94183: the dropdowns in the SLA configuration form do not behave
	// correctly and can stay open over the form, intercepting later actions
	// such as clicking Save. Dismiss any open dropdown before continuing.

	async dismissOpenDropdown() {
		await this.page.keyboard.press('Escape');
	}

	async fillNodeDropdown(
		labelFor: 'slaTimePause' | 'slaTimeStart' | 'slaTimeStop',
		optionText: string
	) {
		const dropdownId = {
			slaTimePause: 'pause',
			slaTimeStart: 'start',
			slaTimeStop: 'stop',
		}[labelFor];

		await this.getNodeField(labelFor).getByRole('textbox').click();

		await this.page
			.locator(`#dropDownList${dropdownId}`)
			.getByText(optionText, {exact: true})
			.click();
	}

	getNodeField(labelFor: 'slaTimePause' | 'slaTimeStart' | 'slaTimeStop') {
		return this.page
			.locator('.form-group')
			.filter({has: this.page.locator(`label[for="${labelFor}"]`)});
	}

	getSelectedNode(labelFor: 'slaTimePause' | 'slaTimeStart' | 'slaTimeStop') {
		return this.getNodeField(labelFor).locator('.label-item-expand');
	}
}
