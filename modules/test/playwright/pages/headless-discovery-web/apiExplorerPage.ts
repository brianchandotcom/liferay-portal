/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {liferayConfig} from '../../liferay.config';

export class ApiExplorerPage {
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
	}

	async endpointHasParameters(endpointPath: string, parameters: string[]) {
		await (await this.getEndpointLocator(endpointPath)).click();
		for (const parameter of parameters) {
			await expect(
				this.page.getByRole('cell', {exact: true, name: parameter})
			).toBeVisible();
		}
		await (await this.getEndpointLocator(endpointPath)).click();
	}

	async endpointHasNotParameters(endpointPath: string, parameters: string[]) {
		await (await this.getEndpointLocator(endpointPath)).click();
		for (const parameter of parameters) {
			await expect(
				this.page.getByRole('cell', {exact: true, name: parameter})
			).toBeHidden();
		}
		await (await this.getEndpointLocator(endpointPath)).click();
	}

	async goto() {
		await this.page.goto('/o/api');
	}

	async goToApplication(applicationURL: string) {
		await this.page.goto(
			`/o/api?endpoint=${liferayConfig.environment.baseUrl}/o/${applicationURL}/openapi.json`
		);
	}

	async getEndpointLocator(endpointPath: string): Promise<Locator> {
		return await this.page.locator(
			`//span[@data-path="${endpointPath}"]/a/span`
		);
	}
}
