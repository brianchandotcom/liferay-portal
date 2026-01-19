/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {waitForAlert} from '../../utils/waitForAlert';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class AppManagerPage {
	readonly activateLink: Locator;
	readonly activeFilterMenuItem: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly appLink: (appName: string) => Locator;
	readonly appRow: (appName: string) => Locator;
	readonly appRowOptionsMenu: (appName: string) => Locator;
	readonly appRowBySymbolicName: (appName: string) => Locator;
	readonly appRowOptionsMenuBySymbolicName: (appName: string) => Locator;
	readonly deactivateLink: Locator;
	readonly filterButton: Locator;
	readonly installedFilterMenuItem: Locator;
	readonly noAppsMessage: Locator;
	readonly noResultsMessage: Locator;
	readonly optionsMenu: Locator;
	readonly page: Page;
	readonly resolvedFilterMenuItem: Locator;
	readonly searchInput: Locator;
	readonly uninstallLink: Locator;
	readonly uploadFrame: FrameLocator;
	readonly uploadFrameCloseButton: Locator;
	readonly uploadFrameErrorMessage: Locator;
	readonly uploadFrameFileInput: Locator;
	readonly uploadFrameInstallButton: Locator;
	readonly uploadMenuItem: Locator;

	constructor(page: Page) {
		this.activateLink = page.getByRole('link', {name: 'Activate'});
		this.activeFilterMenuItem = page.getByRole('menuitem', {
			name: 'Active',
		});
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.appLink = (appName) =>
			page
				.getByRole('link', {name: appName})
				.or(page.getByRole('heading', {name: appName}))
				.first();
		this.appRow = (appName) => this.appLink(appName).locator('../../..');
		this.appRowOptionsMenu = (appName) =>
			this.appRow(appName).locator('.lfr-icon-menu > a');
		this.appRowBySymbolicName = (symbolicName: string) =>
			page
				.getByTestId('rowItemContent')
				.filter({
					has: page.locator('.list-group-text').filter({

						// Exact text

						hasText: new RegExp(`^\\s*${symbolicName}\\s*$`),
					}),
				})
				.locator('..');
		this.appRowOptionsMenuBySymbolicName = (symbolicName: string) =>
			this.appRowBySymbolicName(symbolicName).locator(
				'.lfr-icon-menu > a'
			);
		this.deactivateLink = page.getByRole('link', {name: 'Deactivate'});
		this.filterButton = page.getByLabel('Filter', {exact: true});
		this.installedFilterMenuItem = page.getByRole('menuitem', {
			name: 'Installed',
		});
		this.noAppsMessage = page.getByText('No apps were found.');
		this.noResultsMessage = page.getByText('No results were found.');
		this.optionsMenu = page.getByLabel('Options').first();
		this.page = page;
		this.resolvedFilterMenuItem = page.getByRole('menuitem', {
			name: 'Resolved',
		});
		this.searchInput = page.getByPlaceholder('Search for');
		this.uninstallLink = page.getByRole('link', {name: 'Uninstall'});
		this.uploadFrame = page.frameLocator('iframe[title="Upload"]');
		this.uploadFrameErrorMessage = this.uploadFrame.getByText(
			'Please upload a file with a valid extension (JAR, LPKG, or WAR File).'
		);
		this.uploadFrameCloseButton = page.getByLabel('Close', {exact: true});
		this.uploadFrameInstallButton = this.uploadFrame.getByRole('button', {
			name: 'Install',
		});
		this.uploadFrameFileInput = this.uploadFrame.getByRole('textbox', {
			name: 'File',
		});
		this.uploadMenuItem = page.getByRole('menuitem', {name: 'Upload'});
	}

	async goto() {
		await this.applicationsMenuPage.goToAppManager();
	}
	async activateApp(appName: string) {
		await this.goto();

		await this.searchAppAndExpectToBeVisible(appName);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.activateLink,
			trigger: this.appRowOptionsMenu(appName),
		});

		await waitForAlert(this.page);

		await this.searchAppAndExpectToBeVisible(appName, 'Active');
	}

	async activateAppBySymbolicName(symbolicName: string) {
		await this.goto();

		await this.searchAppBySymbolicNameAndExpectToBeVisible(symbolicName);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.activateLink,
			trigger: this.appRowOptionsMenuBySymbolicName(symbolicName),
		});

		await waitForAlert(this.page);

		await this.searchAppBySymbolicNameAndExpectToBeVisible(
			symbolicName,
			'Active'
		);
	}

	async deactivateApp(appName: string) {
		await this.goto();

		await this.searchAppAndExpectToBeVisible(appName);

		this.page.once('dialog', (dialog) => dialog.accept());

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.deactivateLink,
			trigger: this.appRowOptionsMenu(appName),
		});

		await waitForAlert(this.page);

		await this.searchAppAndExpectToBeVisible(appName, 'Resolved');
	}

	async deactivateAppBySymbolicName(symbolicName: string) {
		await this.goto();

		await this.searchAppBySymbolicNameAndExpectToBeVisible(symbolicName);

		this.page.once('dialog', (dialog) => dialog.accept());

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.deactivateLink,
			trigger: this.appRowOptionsMenuBySymbolicName(symbolicName),
		});

		await waitForAlert(this.page);

		await this.searchAppBySymbolicNameAndExpectToBeVisible(
			symbolicName,
			'Resolved'
		);
	}

	async uninstallApp(appName: string) {
		await this.goto();

		await this.searchAppAndExpectToBeVisible(appName);

		this.page.once('dialog', (dialog) => dialog.accept());

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.uninstallLink,
			trigger: this.appRowOptionsMenu(appName),
		});

		await waitForAlert(this.page);
	}

	async searchAppAndExpectToBeVisible(
		appName: string,
		expectedStatus?: string
	) {
		await expect(async () => {
			await this.searchInput.fill(appName);

			await this.searchInput.press('Enter');

			await expect(this.appLink(appName)).toBeVisible({
				timeout: 2000,
			});

			if (expectedStatus) {
				await expect(this.appRow(appName)).toContainText(
					expectedStatus
				);
			}
		}).toPass();
	}

	async searchAppBySymbolicNameAndExpectToBeVisible(
		symbolicName: string,
		expectedStatus?: string
	) {
		await expect(async () => {
			await this.searchInput.fill(symbolicName);
			await this.searchInput.press('Enter');

			const row = this.appRowBySymbolicName(symbolicName);

			await expect(row).toBeVisible({timeout: 2000});

			if (expectedStatus) {
				await expect(row).toContainText(expectedStatus);
			}
		}).toPass();
	}
}
