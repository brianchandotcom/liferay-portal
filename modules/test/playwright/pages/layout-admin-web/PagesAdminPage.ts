/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {liferayConfig} from '../../liferay.config';
import {clickAndExpectToBeHidden} from '../../utils/clickAndExpectToBeHidden';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../utils/portletUrls';
import {reloadUntilVisible} from '../../utils/reloadUntilVisible';
import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';
import {PageEditorPage} from '../layout-content-page-editor-web/PageEditorPage';
import {UIElementsPage} from '../uielements/UIElementsPage';

export class PagesAdminPage {
	readonly page: Page;

	readonly addButton: Locator;
	readonly addPageButton: Locator;
	readonly addPageIFrame: FrameLocator;
	readonly addTemplatePageButton: Locator;
	readonly blankTypeButton: Locator;
	readonly configurationSaveButton: Locator;
	readonly homePageLink: Locator;
	readonly javaScriptClientExtensionsTab: Locator;
	readonly newButton: Locator;
	readonly oneColumnButton: Locator;
	readonly pageEditorPage: PageEditorPage;
	readonly pageTitleBox: Locator;
	readonly searchButton: Locator;
	readonly searchInput: Locator;
	readonly uiElementsPage: UIElementsPage;
	readonly widgetPageButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.addPageButton = page.getByRole('menuitem', {
			exact: true,
			name: 'Page',
		});
		this.addPageIFrame = page.frameLocator('iframe[title="Add Page"]');
		this.addButton = this.addPageIFrame.getByRole('button', {name: 'Add'});
		this.addTemplatePageButton = page.getByRole('menuitem', {
			name: 'Add Site Template Page',
		});
		this.blankTypeButton = page.getByRole('button', {name: 'Blank'});
		this.configurationSaveButton = page.getByRole('button', {
			exact: true,
			name: 'Save',
		});
		this.homePageLink = page.getByLabel('Home', {exact: true});
		this.javaScriptClientExtensionsTab = page.getByRole('tab', {
			name: 'JavaScript',
		});
		this.newButton = page
			.locator('.management-bar')
			.getByRole('button', {name: 'New'});
		this.oneColumnButton = page.getByText('1 Column', {exact: true});
		this.pageEditorPage = new PageEditorPage(this.page);
		this.pageTitleBox = this.addPageIFrame.locator(
			'input[id="_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_name"]'
		);
		this.searchButton = this.page.getByLabel('Search for', {exact: true});
		this.searchInput = this.page.getByPlaceholder('Search for');
		this.uiElementsPage = new UIElementsPage(page);
		this.widgetPageButton = page.getByRole('button', {name: 'Widget Page'});
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.pages}`
		);
	}

	async addContentPage(pageName: string) {
		await this.blankTypeButton.waitFor({state: 'visible'});
		await this.blankTypeButton.click();
		await this.pageTitleBox.fill(pageName);
		await this.addButton.click();
		await this.page
			.getByTitle(`Go to ${pageName}`)
			.waitFor({state: 'visible'});
	}

	async addWidgetPage(pageName: string) {
		await this.widgetPageButton.waitFor({state: 'visible'});
		await this.widgetPageButton.click();
		await this.pageTitleBox.waitFor({state: 'attached'});
		await this.pageTitleBox.hover();
		await this.pageTitleBox.click();
		await this.pageTitleBox.fill(pageName);
		await this.addButton.waitFor({state: 'attached'});
		await this.addButton.hover();
		await this.addButton.click();
		await this.page
			.getByText('Success:The page was created successfully.')
			.waitFor({state: 'visible'});
		await this.oneColumnButton.click();
		await this.uiElementsPage.saveButton.click();
		await this.page
			.getByText('Success:The page was updated successfully.')
			.waitFor({state: 'visible'});
	}

	async checkIfWebContentAddedToHome(
		siteName: string,
		webContentBody: string
	) {
		await this.page.goto(
			liferayConfig.environment.baseUrl + `/group/${siteName}`
		);
		const myLocator = this.page.getByRole('link', {
			name: `Go to ${siteName}`,
		});
		await reloadUntilVisible({
			myLocator,
			page: this.page,
		});
		await this.page
			.getByText(webContentBody)
			.waitFor({state: 'visible', timeout: 3000});
		await this.page.getByText(webContentBody).isVisible();
	}

	async checkIfWebContentAdded(
		siteName: string,
		webContentName: string,
		webContentBody: string
	) {
		await this.page.goto(
			liferayConfig.environment.baseUrl + `/group/${siteName}`
		);
		const myLocator = this.page.getByText(webContentName);
		await reloadUntilVisible({
			myLocator,
			page: this.page,
		});
		await this.page
			.getByRole('menuitem', {name: webContentName})
			.waitFor({state: 'visible'});
		await this.page.getByRole('menuitem', {name: webContentName}).click();
		await this.page.getByText(webContentBody).waitFor({state: 'visible'});
		await this.page.getByText(webContentBody).isVisible();
	}

	async clickOnAction(action: string, title: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				exact: true,
				name: action,
			}),
			trigger: this.page
				.locator('li', {has: this.page.getByText(title)})
				.getByRole('button', {name: 'Open Page Options Menu'}),
		});
	}

	async createNewPage({
		draft = false,
		name,
		parent,
		template,
	}: {
		draft?: boolean;
		name: string;
		parent?: string;
		template?: string;
	}) {

		// If no parent specified, just create from toolbar

		if (!parent) {
			await this.newButton.click();

			await this.page
				.getByRole('menuitem')
				.getByText('Page', {exact: true})
				.click();
		}

		// If parent is specified, create child page

		else {
			await clickAndExpectToBeVisible({
				autoClick: true,
				target: this.page.getByRole('menuitem', {name: 'Add Page'}),
				trigger: this.page
					.locator('li', {has: this.page.getByText(parent)})
					.getByTitle('Add Child Page'),
			});
		}

		// Select template and fill name

		await this.page
			.locator('.card-page-item')
			.filter({hasText: template || 'Blank'})
			.click();

		const loadingAnimation = this.page.locator(
			'.modal-body-iframe .loading-animation'
		);
		await loadingAnimation.waitFor();
		await loadingAnimation.waitFor({state: 'hidden'});

		const modalFrame = this.page.frameLocator('iframe[title="Add Page"]');
		const inputName = modalFrame.getByPlaceholder('Add Page Name');

		await inputName.fill(name);

		await modalFrame.getByRole('button', {name: 'Add'}).click();

		await waitForSuccessAlert(
			this.page,
			'Success:The page was created successfully.'
		);

		// Publish is draft param is false

		if (!draft) {
			await this.pageEditorPage.publishPage();
		}
	}

	async deletePage(name: string) {
		await this.clickOnAction('Delete', name);

		await this.page
			.locator('.modal-title')
			.getByText('Delete Page')
			.waitFor();

		await this.page.getByRole('button', {name: 'Delete'}).click();

		await waitForSuccessAlert(
			this.page,
			'Success:Your request completed successfully.'
		);
	}

	async editPage(name: string) {
		await this.clickOnAction('Edit', name);

		await this.page
			.getByText('Select a Page Element', {exact: true})
			.waitFor();
	}

	async gotoPagesConfiguration() {
		await this.goto();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: 'Configuration'}),
			trigger: this.page
				.locator('.control-menu-nav-item')
				.getByTitle('Options', {exact: true}),
		});
	}

	async gotoSelectGlobalTemplates() {
		await this.newButton.click();

		await this.page
			.getByRole('menuitem')
			.getByText('Page', {exact: true})
			.click();

		await this.page
			.getByRole('menuitem')
			.getByText('Global Templates', {exact: true})
			.click();
	}

	async searchPage(keywords: string) {
		await this.searchInput.click();
		await this.searchInput.clear();
		await this.searchInput.fill(keywords);

		await this.searchButton.click();

		await this.page.getByText('Search Results').waitFor();
	}

	async selectJavaScriptClientExtension(clientExtensionName: string) {
		await this.gotoPagesConfiguration();

		await this.javaScriptClientExtensionsTab.click();

		await this.page
			.getByRole('button', {name: 'Add JavaScript Client Extensions'})
			.click();

		await this.page.getByRole('menuitem', {name: 'In Page Head'}).click();

		const iframe = this.page.frameLocator('#selectGlobalJSCETs_iframe_');

		// Wait for "Select Items" checkbox label to be visible which occurs when JavaScript hydration is complete.

		await iframe.getByText('Select Items').waitFor({state: 'visible'});

		await iframe.getByLabel(clientExtensionName).check();

		const addButton = this.page.getByRole('button', {
			exact: true,
			name: 'Add',
		});

		const clientExtensionEntry = this.page.getByRole('cell', {
			name: clientExtensionName,
		});

		await clickAndExpectToBeVisible({
			target: clientExtensionEntry,
			trigger: addButton,
		});

		await this.configurationSaveButton.click();

		await waitForSuccessAlert(this.page);
	}

	async selectThemeCSSClientExtension(clientExtensionName: string) {
		await this.gotoPagesConfiguration();

		await this.page
			.locator(
				'#_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_themeCSSReplacementExtension'
			)
			.click();

		const iframe = this.page.locator(
			'#selectThemeCSSClientExtension_iframe_'
		);

		await iframe.waitFor({
			state: 'visible',
		});

		const clientExtension = this.page
			.frameLocator('#selectThemeCSSClientExtension_iframe_')
			.getByTestId('rowItemContent')
			.filter({hasText: clientExtensionName});

		await clickAndExpectToBeHidden({
			target: iframe,
			trigger: clientExtension,
		});

		await this.configurationSaveButton.click();
	}

	async selectPageAndChangePermissions(
		pageNames: string[],
		permissionIds: string[]
	) {

		// Select the pages

		for (const pageName of pageNames) {
			const pageInput = await this.page.getByLabel(`Select ${pageName}`, {
				exact: true,
			});

			await pageInput.setChecked(true, {trial: true});
			await pageInput.setChecked(true, {timeout: 1000});
		}

		// Open the permissions modal

		await this.page.getByRole('button', {name: 'Permissions'}).click();

		const permissionsFrame = this.page.frameLocator(
			'iframe[title="Permissions"]'
		);

		await permissionsFrame
			.getByRole('cell', {exact: true, name: 'Role'})
			.waitFor();

		// Check the permissions

		for (const permissionId of permissionIds) {
			const permission = permissionsFrame.locator(`#${permissionId}`);

			await permission.uncheck({trial: true});
			await permission.uncheck({timeout: 1000});
		}

		// Save and close the modal

		await permissionsFrame.getByRole('button', {name: 'Save'}).click();

		const successMessage =
			pageNames.length > 1
				? `Success:${pageNames.length} permissions were updated successfully.`
				: undefined;

		await waitForSuccessAlert(permissionsFrame, successMessage);

		await this.page.getByLabel('close', {exact: true}).click();
	}
}
