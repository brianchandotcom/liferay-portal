/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {GlobalMenuPage} from '../product-navigation-applications-menu/GlobalMenuPage';

export class ProtectedResourceLocalMetadatasPage {
	readonly addProtectedResourceButton: Locator;
	readonly authorizationServers: Locator;
	readonly globalMenuPage: GlobalMenuPage;
	readonly oAuthClientPRLocalMetadataTab: Locator;
	readonly oAuthClientPRLocalMetadataTable: Locator;
	readonly page: Page;
	readonly resource: Locator;
	readonly saveButton: Locator;
	readonly successMessage: Locator;

	constructor(page: Page) {
		this.addProtectedResourceButton = page.getByRole('link', {
			name: 'Add OAuth Protected Resource',
		});
		this.authorizationServers = page.getByLabel(
			'Authorization Servers Required Input'
		);
		this.globalMenuPage = new GlobalMenuPage(page);
		this.oAuthClientPRLocalMetadataTab = page.getByRole('link', {
			exact: true,
			name: 'Protected Resource Local Metadata',
		});
		this.oAuthClientPRLocalMetadataTable = page.locator(
			'#_com_liferay_oauth_client_admin_web_internal_portlet_OAuthClientAdminPortlet_oAuthClientPRLocalMetadataSearchContainer'
		);
		this.page = page;
		this.resource = page.getByLabel('Resource Required Resource must');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.successMessage = page.getByText(
			'Your request completed successfully'
		);
	}

	async addProtectedResourceLocalMetadata(
		resource: string,
		authorizationServers: string,
		expectedMessage?: string
	) {
		await this.addProtectedResourceButton.click();

		await this.resource.fill(resource);

		await this.authorizationServers.fill(authorizationServers);

		// eslint-disable-next-line no-console
		console.log(
			'[PR diag] save buttons:',
			await this.saveButton.count(),
			'resource value:',
			await this.resource.inputValue()
		);

		// Track POSTs to the update endpoint so we can see if a double-submit
		// is happening.

		const postUrls: string[] = [];
		const handler = (request: import('@playwright/test').Request) => {
			if (
				request.method() === 'POST' &&
				request.url().includes('update_oauth_client_pr')
			) {
				postUrls.push(request.url());
			}
		};

		this.page.on('request', handler);

		await this.saveButton.click();

		await this.page.waitForLoadState('networkidle');

		this.page.off('request', handler);

		// eslint-disable-next-line no-console
		console.log(
			'[PR diag] POSTs after save:',
			postUrls.length,
			'expected:',
			expectedMessage ?? 'success'
		);

		if (expectedMessage !== undefined) {
			await expect(
				await this.page.getByText(expectedMessage)
			).toBeVisible();

			if (
				await this.page
					.locator('#ToastAlertContainer')
					.getByLabel('Close')
					.isVisible()
			) {
				await this.page
					.locator('#ToastAlertContainer')
					.getByLabel('Close')
					.click();
			}

			await this.page.getByRole('button', {name: 'Cancel'}).click();
		}
		else {
			await expect(await this.successMessage).toBeVisible();
			await this.page.locator('.alert').getByLabel('Close').click();
		}
	}

	async deleteProtectedResourceLocalMetadata() {
		await this.oAuthClientPRLocalMetadataTable.waitFor();

		const row = await this.oAuthClientPRLocalMetadataTable
			.getByRole('row')
			.last();

		while (await row.isVisible()) {
			await clickAndExpectToBeVisible({
				autoClick: true,
				target: this.page.getByRole('link', {name: 'Delete'}),
				trigger: row.locator('.dropdown-toggle'),
			});

			await this.page
				.getByRole('alertdialog')
				.getByRole('button', {name: 'OK'})
				.click();

			await expect(await this.successMessage).toBeVisible();

			await this.page.locator('.alert').getByLabel('Close').click();
		}
	}

	async goTo() {
		if (await this.oAuthClientPRLocalMetadataTab.isHidden()) {
			await this.globalMenuPage.goToControlPanel(
				'OAuth Client Administration'
			);
		}

		await this.oAuthClientPRLocalMetadataTab.click();

		await expect(await this.addProtectedResourceButton).toBeVisible();
	}
}
