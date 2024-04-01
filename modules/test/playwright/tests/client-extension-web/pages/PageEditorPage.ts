/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

import {Layout} from '../../../helpers/json-web-services/JSONWebServicesLayoutApiHelper';
import {liferayConfig} from '../../../liferay.config';

export class PageEditorPage {
	readonly page: Page;
	readonly layout: Layout;

	private editMode: boolean;

	constructor(page: Page, layout: Layout) {
		this.page = page;
		this.layout = layout;
		this.editMode = false;
	}

	async addWidget(category: string, name: string) {
		this.assertEditMode(true);

		await this.page
			.getByRole('tab', {exact: true, name: 'Widgets'})
			.click();
		await this.page.getByRole('menuitem', {name: category}).click();
		await this.page.getByText(name).click();
		await this.page
			.getByRole('menuitem', {
				name: `${name} Add ${name} Mark ${name} as Favorite`,
			})
			.press('Tab');
		await this.page.getByLabel(`Add ${name}`).press('Enter');
		await this.page.getByLabel(`Add ${name}`).press('Enter');
	}

	async edit() {
		this.assertEditMode(false);

		await this.page.getByRole('link', {name: 'Edit'}).click();
		expect(this.page.getByLabel('Publish', {exact: true})).toBeVisible();

		this.editMode = true;
	}

	async goto() {
		await this.page.goto(
			`${liferayConfig.environment.baseUrl}${this.layout.friendlyURL}`
		);
	}

	async publish() {
		this.assertEditMode(true);

		await this.page.getByLabel('Publish', {exact: true}).click();
		await this.page.waitForEvent('load');

		this.editMode = false;
	}

	private assertEditMode(edit: boolean) {
		if (this.editMode !== edit) {
			throw new Error(
				`Layout ${this.layout.nameCurrentValue} is ${
					edit ? 'not' : 'already'
				} in edit mode`
			);
		}
	}
}
