/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export class ApplicationsMenuPage {
	constructor(page) {
		this.applicationMenuButton = page.getByLabel(
			'Open Applications MenuCtrl+'
		);
		this.controlPanelButton = page.getByRole('tab', {
			name: 'Control Panel',
		});
		this.instanceSettingsLink = page.getByRole('link', {
			name: 'Instance Settings',
		});
		this.objectsLink = page.getByRole('link', {name: 'Objects'});
		this.page = page;
		this.signInButton = page.getByRole('button', {name: 'Sign In'});
	}

	async goto() {
		await this.page.goto('/');
	}

	async goToObjects() {
		await this.goToControlPanel();
		await this.objectsLink.click();
	}

	async goToInstanceSettings() {
		await this.goToControlPanel();
		await this.instanceSettingsLink.click();
	}

	async goToControlPanel() {
		await this.goto();
		await this.applicationMenuButton.click();
		await this.controlPanelButton.click();
	}
}
