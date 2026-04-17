/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {liferayConfig} from '../../liferay.config';

export interface MockMockEmailMatcher {
	body?: string;
	from?: string;
	subject: string;
	to?: string;
}

export class MockMockPage {
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
	}

	async assertEmail(expected: MockMockEmailMatcher) {
		await this.waitForSubject(expected.subject);

		await this.page
			.getByRole('link', {name: expected.subject})
			.first()
			.click();

		await expect(
			this.page.getByRole('heading', {name: expected.subject})
		).toBeVisible();

		const addresses = this.section('Addresses');

		if (expected.from !== undefined) {
			await expect(addresses).toContainText(expected.from);
		}

		if (expected.to !== undefined) {
			await expect(addresses).toContainText(expected.to);
		}

		if (expected.body !== undefined) {
			await expect(
				this.section(/^(Plain text body|HTML body unformatted)$/)
			).toContainText(expected.body);
		}
	}

	async goto() {
		const url = new URL(liferayConfig.environment.baseUrl);

		url.port = '8282';

		await this.page.goto(url.toString());
	}

	private section(heading: string | RegExp): Locator {
		return this.page
			.getByRole('heading', {name: heading})
			.first()
			.locator('xpath=..');
	}

	private async waitForSubject(subject: string) {
		await expect
			.poll(
				async () => {
					await this.goto();

					return await this.page
						.getByRole('link', {name: subject})
						.first()
						.isVisible();
				},
				{
					message: `Timed out waiting for email with subject "${subject}" in MockMock inbox.`,
					timeout: 10000,
				}
			)
			.toBeTruthy();
	}
}
