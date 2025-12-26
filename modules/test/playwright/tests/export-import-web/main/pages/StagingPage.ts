/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import getRandomString from '../../../../utils/getRandomString';

export class StagingPage {
	readonly localStagingButton: Locator;
	readonly page: Page;
	readonly stagedPortletCheckbox: (stagedPortletName: string) => Locator;

	constructor(page: Page) {
		this.localStagingButton = page.getByTestId('stagingType_local');
		this.page = page;
		this.stagedPortletCheckbox = (stagedPortletName: string) =>
			this.page
				.locator('.custom-checkbox')
				.filter({hasText: stagedPortletName})
				.locator('input');
	}

	async enableLocalStaging(stagedPortlets?: string[]) {
		await this.localStagingButton.check();

		this.page.once('dialog', async (dialog) => {
			expect(dialog.message()).toContain(
				'Are you sure you want to activate local staging for'
			);
			await dialog.accept().catch();
		});

		if (stagedPortlets) {
			await this.stagedPortletCheckbox('Select All').check();
			await this.stagedPortletCheckbox('Select All').uncheck();

			for (const stagedPortlet of stagedPortlets) {
				await this.stagedPortletCheckbox(stagedPortlet).check();
			}
		}

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

	async addTemplate(templateName: string) {
		await this.page.waitForLoadState('domcontentloaded');
		await this.page.getByRole('link', {exact: true, name: 'New'}).click();
		await this.page.getByLabel('Title Required').fill(templateName);
		await this.page.getByRole('button', {name: 'Save'}).click();
		await this.page.getByText(templateName).waitFor({state: 'visible'});
	}

	async publishTemplate(templateName: string) {
		await this.page
			.locator(`tr`)
			.filter({hasText: templateName})
			.getByRole('button')
			.click();

		await this.page.getByRole('menuitem', {name: 'Publish'}).click();
		await this.page.getByRole('button', {name: 'Publish to Live'}).click();
		await expect(
			this.page
				.locator('.list-group-item div')
				.filter({hasText: templateName})
				.getByTestId('processResult')
				.getByText('Successful')
		).toBeVisible({
			timeout: 60 * 1000,
		});
	}

	async publish({
		includeIfModified = [],
		rangeAll,
		selectedEntities = [],
		title,
	}: {
		includeIfModified?: string[];
		rangeAll?: boolean;
		selectedEntities?: string[];
		title?: string;
	} = {}) {
		if (!title) {
			title = getRandomString();
		}

		await this.page
			.getByRole('link', {name: 'Custom Publish Process'})
			.click();

		await this.page
			.getByPlaceholder('Enter the name of the process')
			.fill(title);

		if (rangeAll) {
			await this.page.locator('[data-qa-id="range_rangeAll"]').check();
			await this.page.getByRole('link', {name: 'Refresh Counts'}).click();
		}

		for (const selectedEntity of selectedEntities) {
			await this.page
				.getByRole('checkbox', {name: selectedEntity})
				.check();
		}

		for (const i in includeIfModified) {
			await this.page
				.getByText(includeIfModified[i])
				.getByRole('button', {name: 'Change'})
				.click();

			await this.page
				.locator('input[type="radio"][value="include-if-modified"]')
				.check();
		}

		await this.page
			.getByRole('button', {exact: true, name: 'Publish to Live'})
			.click();

		await expect(
			this.page
				.locator(
					'[id="_com_liferay_staging_processes_web_portlet_StagingProcessesPortlet_publishLayoutProcesses_1"]'
				)
				.locator('span')
				.filter({hasText: 'Successful'})
				.first()
		).toBeVisible();
	}

	async goto(siteKey: string) {
		await this.page.goto(
			`/group/${siteKey}/~/control_panel/manage?p_p_id=com_liferay_staging_processes_web_portlet_StagingProcessesPortlet`,
			{waitUntil: 'domcontentloaded'}
		);
	}

	async gotoTemplatePage() {
		await this.page
			.getByText('Staging Open Applications')
			.getByLabel('Options')
			.click();
		await this.page
			.getByRole('menuitem', {name: 'Publish Templates'})
			.click();
	}
}
