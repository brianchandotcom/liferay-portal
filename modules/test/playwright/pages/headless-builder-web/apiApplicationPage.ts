/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class ApiApplicationPage {
	readonly page: Page;
	readonly addAPIEndpointButton: Locator;
	readonly endpointPathTextBox: Locator;
	readonly endpointCreateButton: Locator;
	readonly publishButton: Locator;

	constructor(page: Page) {
		this.page = page;
		this.addAPIEndpointButton = page.getByLabel('Add API Endpoint');
		this.endpointPathTextBox = page.getByPlaceholder('Enter Path');
		this.endpointCreateButton = page.getByRole('button', {name: 'Create'});
		this.publishButton = page.getByRole('button', {name: 'Publish'});
	}

	async goToDetailsTab() {
		await this.page.getByRole('button', {name: 'Details'}).click();
		await this.page.waitForLoadState();
	}

	async goToEndpointsTab() {
		await this.page.getByRole('button', {name: 'Endpoints'}).click();
		await this.page.waitForLoadState();
	}

	async goToSchemasTab() {
		await this.page.getByRole('button', {name: 'Schemas'}).click();
		await this.page.waitForLoadState();
	}

	async goToEndpointConfigurationTab() {
		await this.page.getByRole('tab', {name: 'Configuration'}).click();
		await this.page.waitForLoadState();
	}

	async goToEndpointInfoTab() {
		await this.page.getByRole('tab', {name: 'Info'}).click();
		await this.page.waitForLoadState();
	}

	async goToEditEndpoint(endpointPath: string) {
		await this.page.getByLabel(endpointPath).click();
		await this.page.waitForLoadState();
	}

	async selectEndpointRequestSchema(schemaName: string) {
		await this.page.getByLabel('Request Body Schema').click();
		await this.page.getByRole('menuitem', {name: schemaName}).click();
	}

	async selectEndpointResponseSchema(schemaName: string) {
		await this.page.getByRole('button', {name: 'Select a Schema'}).click();
		await this.page.getByRole('menuitem', {name: schemaName}).click();
	}

	async setEndpointMethod(methodValue: 'GET' | 'POST') {
		await this.page.getByLabel('Method').click();
		await this.page.getByRole('menuitem', {name: methodValue}).click();
	}

	async setEndpointScope(scopeValue: 'Company' | 'Site') {
		await this.page.getByLabel('Select Scope').click();
		await this.page.getByRole('menuitem', {name: scopeValue}).click();
	}

	async createApiEndpoint(
		httpMethod: 'GET' | 'POST',
		scope: 'Company' | 'Site',
		path: string
	) {
		await this.goToEndpointsTab();
		await this.addAPIEndpointButton.click();
		await this.setEndpointMethod(httpMethod);
		await this.setEndpointScope(scope);
		await this.endpointPathTextBox.fill(path);
		await this.endpointCreateButton.click();
	}
}
