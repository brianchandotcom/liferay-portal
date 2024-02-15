/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Page} from '@playwright/test';

export class GogoShellPage {
    readonly commandInput: Locator;
	readonly page: Page;
    readonly executeButton: Locator;

	constructor(page: Page) {
		this.page = page;

        this.commandInput = this.page.getByLabel('Command');
        this.executeButton = this.page.getByRole('button', {name: 'Execute'});
	}

    async goto() {
		await this.page.getByLabel('Open Applications MenuCtrl+').click();
		await this.page.getByRole('tab', {name: 'Control Panel'}).click();
		await this.page.getByRole('menuitem', { name: 'Gogo Shell' }).click();
    }

    async addCommand(command: string) {
        await this.goto();

        await this.commandInput.fill(command);
		await this.executeButton.click();
		await this.page.waitForLoadState();
    }
}
