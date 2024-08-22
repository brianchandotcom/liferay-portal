/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class ActionPage {
	readonly nameInput: Locator;
	readonly scriptInput: Locator;
	readonly selectActionType: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.nameInput = page.locator('#name');
		this.scriptInput = page.locator('#script');
		this.selectActionType = page.locator('#type');
		this.page = page;
	}

	async fillWorkflowAction(name: string, script: string, typeOption: string) {
		await this.nameInput.fill(name);
		await this.selectActionType.selectOption(typeOption);
		await this.scriptInput.fill(script);
		await this.scriptInput.blur();
	}

	async getTypeSelectOption(optionValue: string) {
		return await this.page.$(`#type option[value="${optionValue}"]`);
	}
}
