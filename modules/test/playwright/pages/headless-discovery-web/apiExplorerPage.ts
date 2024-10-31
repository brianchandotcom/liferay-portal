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

	async expectEndpointWithParameters(
		endpointPath: string,
		parameters: string[]
	) {
		await this.getEndpointLocator(endpointPath).click();
		for (const parameter of parameters) {
			await expect(
				this.page
					.getByRole('row')
					.getByRole('cell')
					.getByText(parameter)
			).toBeVisible();
		}
	}

	async expectEndpointWithoutParameters(
		endpointPath: string,
		parameters: string[]
	) {
		await this.getEndpointLocator(endpointPath).click();
		for (const parameter of parameters) {
			await expect(
				this.page
					.getByRole('row')
					.getByRole('cell')
					.getByText(parameter)
			).toBeHidden();
		}
		await this.getEndpointLocator(endpointPath).click();
	}

	async goto() {
		await this.page.goto('/o/api');
	}

	async goToApplication(applicationURL: string) {
		await this.page.goto(
			`/o/api?endpoint=${liferayConfig.environment.baseUrl}/o/${applicationURL}/openapi.json`
		);
	}

	getEndpointLocator(endpointPath: string, options?: object): Locator {
		return this.page.locator(
			`//span[@data-path="${endpointPath}"]/a/span`,
			options
		);
	}
}
