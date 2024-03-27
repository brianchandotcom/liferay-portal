/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {UIElementsPage} from '../uielements/UIElementsPage';

export class WebContentDisplayPage {
	readonly page: Page;

	readonly configurationOption: Locator;
	readonly modalIFrame: FrameLocator;
	readonly selectButton: Locator;
	readonly selectWebContentButton: Locator;
	readonly saveButton: Locator;
	readonly webContentDisplay: Locator;
	readonly webContentDisplayAddButton: Locator;
	readonly webContentDisplayContent: Locator;
	readonly webContentDisplayOptionsWidget: Locator;
	readonly webContentDisplayWidget: Locator;
	readonly webContentDisplayOptionsContent: Locator;
	readonly uiElementsPage;

	constructor(page: Page) {
		this.configurationOption = page.getByRole('menuitem', {
			exact: true,
			name: 'Configuration',
		});
		this.modalIFrame = page.frameLocator('iframe[id="modalIframe"]');
		this.page = page;
		this.saveButton = this.modalIFrame.getByRole('button', {name: 'Save'});
		this.selectButton = this.modalIFrame.getByRole('button', {
			name: 'Select',
		});
		this.selectWebContentButton = this.modalIFrame
			.frameLocator('iframe[title="Select Web Content"]')
			.locator('*[data-qa-id="row"]');
		this.uiElementsPage = new UIElementsPage(page);
		this.webContentDisplay = page
			.getByText('Select web content to make it visible')
			.first();
		this.webContentDisplayAddButton = page
			.getByLabel(
				'Asset PublisherDocuments and MediaMenu DisplayWeb Content Display'
			)
			.locator('li')
			.filter({hasText: 'Web Content Display'})
			.getByLabel('Add Content');
		this.webContentDisplayContent = page.locator(
			'[id^="portlet_com_liferay_journal_content_web_portlet_JournalContentPortlet_INSTANCE"]'
		);
		this.webContentDisplayOptionsContent =
			this.webContentDisplayContent.getByLabel('Options');
		this.webContentDisplayOptionsWidget = page
			.locator(
				'[id^="portlet-topper-toolbar_com_liferay_journal_content_web_portlet_JournalContentPortlet_INSTANCE_"]'
			)
			.getByLabel('Options');
		this.webContentDisplayWidget = page.getByText(
			'Web Content Display Info: This application is not visible to users yet. Select w'
		);
	}

	async addWebContentWithDisplay() {
		await this.webContentDisplay.waitFor({state: 'visible'});
		await this.webContentDisplayContent.hover();
		await this.webContentDisplayOptionsContent.click();
		await this.configurationOption.click();
		await this.selectButton.waitFor({state: 'visible'});
		await this.selectButton.click();
		await this.selectWebContentButton.click();
		if (!this.saveButton.isVisible) {
			await this.selectWebContentButton.click();
		}
		await this.saveButton.click();
		await this.uiElementsPage.closeClickable.click();
		await this.page
			.locator('header')
			.filter({hasText: 'Web Content Display'})
			.waitFor({state: 'visible'});
	}

	async addWebContentWithWidget() {
		await this.webContentDisplayAddButton.click();
		await this.uiElementsPage.pageCreatedAlert.waitFor({state: 'hidden'});
		await this.uiElementsPage.pageUpdatedAlert.waitFor({state: 'hidden'});
		await this.webContentDisplayWidget.hover();
		await this.webContentDisplayOptionsWidget.waitFor({state: 'visible'});
		await this.webContentDisplayOptionsWidget.click();
		await this.configurationOption.click();
		await this.page
			.getByText('Success:The application was added to the page.')
			.waitFor({state: 'hidden'});
		await this.selectButton.waitFor({state: 'visible'});
		await this.selectButton.click();
		await this.selectWebContentButton.click();
		if (!this.saveButton.isVisible) {
			await this.selectWebContentButton.click();
		}
		await this.saveButton.click();
	}
}
