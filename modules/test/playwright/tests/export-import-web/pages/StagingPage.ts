/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';
import {getComparator} from 'playwright-core/lib/utils';

import {getTempDir} from '../../../utils/temp';

export class StagingPage {
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
	}

	async compareCurrentPageVersions(siteKey: string) {
		const comparator = getComparator('image/png');

		const buffer = comparator(
			await this.getCurrentPageScreenshot(siteKey, 'Live'),
			await this.getCurrentPageScreenshot(siteKey, 'Staging')
		);

		if (buffer !== null && buffer.diff !== undefined) {
			const fs = require('fs');

			fs.writeFile(
				getTempDir() + '/' + siteKey + '-diff.png',
				buffer.diff,
				(error) => {
					if (error) {
						throw error;
					}
				}
			);
		}

		expect(buffer).toBeNull();
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

	async goto(siteKey: string) {
		await this.page.goto(
			`/group/${siteKey}/~/control_panel/manage?p_p_id=com_liferay_staging_processes_web_portlet_StagingProcessesPortlet`
		);
	}

	private async getCurrentPageScreenshot(siteKey: string, version: string) {
		await this.page.goto(
			`/web/${siteKey}${version === 'Staging' ? '-staging' : ''}`
		);

		const url = this.page.url();

		await this.page.goto(`${url}?p_l_mode=preview`, {waitUntil: 'load'});

		await this.page.waitForFunction(() => document.fonts.ready);

		const screenshot = await this.page.screenshot({
			fullPage: true,
			mask: [this.page.getByTestId('notificationsCount')],
			path: getTempDir() + '/' + siteKey + '-' + version + '.png',
		});

		await this.page.goto(url);

		return screenshot;
	}
}
