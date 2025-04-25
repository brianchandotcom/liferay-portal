/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

export class StagingPage {
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
	}

	async enableLocalStaging() {
		await this.page.getByTestId('stagingType_local').check();

		this.page.once('dialog', async (dialog) => {
			expect(dialog.message()).toContain(
				'Are you sure you want to activate local staging for'
			);
			await dialog.accept().catch();
		});

		await this.page.getByRole('button', {name: 'Save'}).click();

		await expect(
			this.page.getByText('Initial Publish Process').first()
		).toBeVisible();

		for (const processResult of await this.page
			.getByTestId('processResult')
			.all()) {
			await expect(processResult.getByText('Successful')).toBeVisible({
				timeout: 60 * 1000,
			});
		}
	}

	async publish(includeIfModified?: string[]) {
		await this.page
			.getByRole('link', {name: 'Custom Publish Process'})
			.click();

		for (const i in includeIfModified) {
			await this.page
				.getByText(includeIfModified[i])
				.getByRole('button', {name: 'Change'})
				.click();

			await this.page
				.getByRole('radio', {exact: true, name: 'Include If Modified'})
				.click();
		}

		await this.page
			.getByRole('button', {exact: true, name: 'Publish to Live'})
			.click();

		await this.page.locator('span').filter({ hasText: 'In Progress' }).waitFor({ state: 'hidden' });
	}

	async goto(siteKey: string) {
		await this.page.goto(
			`/group/${siteKey}/~/control_panel/manage?p_p_id=com_liferay_staging_processes_web_portlet_StagingProcessesPortlet`
		);
	}
}
