/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../utils/portletUrls';
import {UIElementsPage} from '../uielements/UIElementsPage';

export class WebContentDisplayPage {
	readonly page: Page;

	readonly app: Locator;
	readonly configurationFrame: FrameLocator;
	readonly configurationFrameSelectButton: Locator;
	readonly configurationOption: Locator;
	readonly scopeTab: Locator;
	readonly scopeOptions: Locator;
	readonly saveButton: Locator;
	readonly selectButton: Locator;
	readonly selectWebContentButton: Locator;
	readonly selectWebContentInConfigurationFrame: FrameLocator;
	readonly selectWebContentFrame: FrameLocator;
	readonly uiElementsPage;
	readonly webContentDisplay: Locator;
	readonly webContentDisplayAddButton: Locator;
	readonly webContentDisplayConfig: FrameLocator;
	readonly webContentDisplayContent: Locator;
	readonly webContentDisplayOptionsContent: Locator;
	readonly webContentDisplayOptionsWidget: Locator;
	readonly webContentDisplayWidget: Locator;
	readonly webContentToSelect: Locator;

	constructor(page: Page) {
		this.page = page;
		this.configurationFrame = page.frameLocator(
			'iframe[title*="Configuration"]'
		);

		this.app = page.getByTestId('app-loaded');

		this.configurationFrameSelectButton = this.configurationFrame.getByRole(
			'button',
			{
				name: 'Select',
			}
		);
		this.configurationOption = page.getByRole('menuitem', {
			exact: true,
			name: 'Configuration',
		});
		this.scopeOptions = this.configurationFrame.locator(
			'[id="_com_liferay_portlet_configuration_web_portlet_PortletConfigurationPortlet_scope"]'
		);
		this.saveButton = page
			.frameLocator('iframe[title*="Web Content Display"]')
			.getByRole('button', {
				name: 'Save',
			});
		this.scopeTab = this.configurationFrame.getByRole('link', {
			name: 'Scope Deprecated',
		});
		this.selectButton = this.app.getByRole('button', {
			name: 'Select',
		});
		this.selectWebContentButton = page
			.frameLocator('iframe[title*="Web Content Display"]')
			.getByRole('button', {name: 'Select'});
		this.selectWebContentInConfigurationFrame =
			this.configurationFrame.frameLocator(
				'iframe[title="Select Web Content"]'
			);
		this.selectWebContentFrame = page
			.frameLocator('iframe[title*="Web Content Display"]')
			.frameLocator('iframe[title="Select Web Content"]');
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
		this.webContentDisplayConfig = page.frameLocator(
			'iframe[title*="Web Content Display"]'
		);
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
		this.webContentToSelect =
			this.selectWebContentFrame.locator('[data-qa-id="row"]');
	}

	async gotoWebContentAdmin(pageScopeId: string) {
		await this.page.goto(`/group/${pageScopeId}${PORTLET_URLS.journal}`, {
			waitUntil: 'domcontentloaded',
		});
	}

	async goToConfiguration() {
		await this.webContentDisplay.waitFor({state: 'visible'});
		await this.webContentDisplayContent.hover();
		await this.webContentDisplayContent.click();

		await this.page
			.locator('[id*="JournalContentPortlet"]')
			.getByRole('button', {name: 'Options'})
			.click();
		await this.configurationOption.click();
	}

	async addWebContentWithDisplay(
		options: {pageType?: 'content' | 'widget'; webContentName?: string} = {
			pageType: 'content',
			webContentName: '',
		}
	) {
		await this.webContentDisplay.waitFor({state: 'visible'});
		await this.webContentDisplayContent.hover();
		await this.webContentDisplayContent.click();

		const {pageType, webContentName} = options;

		if (pageType === 'widget') {
			await this.page
				.locator('[id*="JournalContentPortlet"]')
				.getByRole('button', {name: 'Options'})
				.click();
		}
		else {
			await this.page
				.locator('#wrapper')
				.getByText('Web Content Display')
				.last()
				.locator('..')
				.getByRole('button', {name: 'Options'})
				.click();
		}

		await this.configurationOption.click();

		await this.page
			.getByText('Success:The application was added to the page.')
			.waitFor({state: 'hidden'});

		await this.configurationFrameSelectButton.waitFor({state: 'visible'});
		await this.configurationFrameSelectButton.click();

		if (webContentName) {
			await this.selectWebContentInConfigurationFrame
				.getByText(webContentName)
				.waitFor({state: 'visible'});
			await this.selectWebContentInConfigurationFrame
				.getByText(webContentName)
				.hover();
			await this.selectWebContentInConfigurationFrame
				.getByText(webContentName)
				.click();
		}
		else {
			await this.webContentDisplayOptionsContent.click();

			await this.saveConfigurationFrameOptions();

			await this.configurationOption.click();
			await this.page
				.getByText('Success:The application was added to the page.')
				.waitFor({state: 'hidden'});

			await this.selectWebContentButton.waitFor({state: 'visible'});
			await this.selectWebContentButton.click();
			await this.webContentToSelect.waitFor({state: 'visible'});
			await this.webContentToSelect.hover();
			await this.webContentToSelect.click();

			if (!this.saveButton.isVisible) {
				await this.webContentToSelect.click();
			}

			if (!this.saveButton.isVisible) {
				await this.webContentToSelect.click();
			}

			await this.saveButton.click();
			await this.uiElementsPage.closeClickable.click();

			await this.page
				.locator('header')
				.filter({hasText: 'Web Content Display'})
				.waitFor({state: 'visible'});
		}

		await this.saveConfigurationFrameOptions();
	}

	async setScope(scope: string) {
		await this.scopeTab.click();
		await this.scopeOptions.selectOption(`layout,${scope}`);
	}

	async addWebContentWithWidget(webContentName: string) {
		await this.webContentDisplayAddButton.click();
		await this.page
			.getByText('Success:The application was added to the page.')
			.waitFor({state: 'visible'});
		await this.page
			.getByText('Success:The application was added to the page.')
			.waitFor({state: 'hidden'});
		await this.page
			.getByRole('heading', {name: 'Web Content Display'})
			.hover();
		await this.selectButton.waitFor({state: 'visible'});
		await clickAndExpectToBeVisible({
			target: this.selectWebContentButton,
			trigger: this.selectButton,
		});
		await clickAndExpectToBeVisible({
			target: this.webContentToSelect.getByText(webContentName),
			trigger: this.selectWebContentButton,
		});
		await this.webContentToSelect.getByText(webContentName).hover();
		await this.webContentToSelect.getByText(webContentName).click();
		if (!this.saveButton.isVisible) {
			await this.webContentToSelect.getByLabel(webContentName).click();
		}
		await this.saveButton.click();
	}

	async saveConfigurationFrameOptions() {
		await this.configurationFrame
			.getByRole('button', {
				name: 'Save',
			})
			.click();

		await this.uiElementsPage.closeClickable.click();

		await this.page
			.locator(
				'[id^="portlet_com_liferay_journal_content_web_portlet_JournalContentPortlet_INSTANCE_"] header'
			)
			.filter({hasText: 'Web Content Display'})
			.waitFor({state: 'visible'});
	}
}
