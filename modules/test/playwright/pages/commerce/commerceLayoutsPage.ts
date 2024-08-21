/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {DataApiHelpers} from '../../helpers/ApiHelpers';
import {liferayConfig} from '../../liferay.config';

export class CommerceLayoutsPage {
	readonly addOrderButton: Locator;
	readonly addPageButton: Locator;
	readonly addPageModalSubmitButton: Locator;
	readonly addPageNameInput: Locator;
	readonly addWidgetButton: Locator;
	readonly addWidgetLabel: (widgetName: string) => Locator;
	readonly availableThemesFrame: FrameLocator;
	readonly backLink: Locator;
	readonly catalogLink: Locator;
	readonly changeCurrentThemeButton: Locator;
	readonly closeProductMenuButton: Locator;
	readonly configureMenuItem: Locator;
	readonly createPageMenuItem: Locator;
	readonly defaultDisplayPageTemplateIcon: Locator;
	readonly defineCustomThemeCheckbox: Locator;
	readonly deleteLayoutModal: Locator;
	readonly deletePageButton: Locator;
	readonly designMenuItem: Locator;
	readonly designLink: Locator;
	readonly displayPageTemplateLink: (name: string) => Locator;
	readonly displayPageTemplatesLink: Locator;
	readonly markAsDefaultMenuItem: Locator;
	readonly moreActionsButton: Locator;
	readonly openProductMenuButton: Locator;
	readonly page: Page;
	readonly pagesMenuItem: Locator;
	readonly pageTemplatesMenuItem: Locator;
	readonly pendingOrdersLink: Locator;
	readonly previewItemSelectorButton: Locator;
	readonly publishButton: Locator;
	readonly saveButton: Locator;
	readonly searchFormInput: Locator;
	readonly selectOtherItemDropdownItem: Locator;
	readonly showLabelInput: Locator;
	readonly siteBuilderMenuItem: Locator;
	readonly siteHomePageLink: Locator;
	readonly widgetPageTemplateButton: Locator;

	constructor(page: Page) {
		this.addOrderButton = page.getByRole('button', {
			exact: true,
			name: 'Add Order',
		});
		this.addPageButton = page
			.getByTestId('creationMenuNewButton')
			.locator('visible=true');
		this.addPageModalSubmitButton = page
			.frameLocator('#addLayoutDialog_iframe_')
			.getByTestId('addLayoutFooter')
			.getByRole('button', {exact: true, name: 'Add'});
		this.addPageNameInput = page
			.frameLocator('#addLayoutDialog_iframe_')
			.getByTestId('addPageNameInput');
		this.addWidgetButton = page.getByTestId('add');
		this.addWidgetLabel = (widgetName) => {
			return page
				.getByTestId('addPanelTabItem')
				.filter({has: page.locator(`text="${widgetName}"`)})
				.getByRole('button', {exact: true, name: 'Add Content'});
		};
		this.availableThemesFrame = page.frameLocator(
			'iframe[title="Available Themes"]'
		);

		this.backLink = page.getByRole('link', {exact: true, name: 'Back'});
		this.catalogLink = page.getByRole('link', {
			exact: true,
			name: 'Catalog',
		});
		this.changeCurrentThemeButton = page.getByRole('button', {
			exact: true,
			name: 'Change Current Theme',
		});
		this.closeProductMenuButton = page.getByRole('tab', {
			exact: true,
			name: 'Close Product Menu',
		});
		this.configureMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Configure',
		});
		this.createPageMenuItem = page
			.getByTestId('dropdownMenu')
			.getByRole('menuitem', {
				exact: true,
				name: 'Page',
			});
		this.defaultDisplayPageTemplateIcon = page
			.getByTestId('row')
			.locator('use')
			.nth(1);
		this.defineCustomThemeCheckbox = page.getByLabel(
			'Define a custom theme for this page.'
		);
		this.deleteLayoutModal = page.locator('#deleteLayoutModalDeleteButton');
		this.deletePageButton = page
			.getByTestId('actionDropdownItem')
			.getByRole('button', {
				exact: true,
				name: 'Delete',
			});
		this.designMenuItem = page
			.getByTestId('appGroup')
			.filter({hasText: 'Design'});
		this.designLink = page.getByRole('link', {exact: true, name: 'Design'});
		this.displayPageTemplateLink = (name: string) =>
			page.getByRole('link', {exact: true, name});
		this.displayPageTemplatesLink = page.getByRole('link', {
			exact: true,
			name: 'Display Page Templates',
		});
		this.markAsDefaultMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Mark as Default',
		});
		this.moreActionsButton = page.getByLabel('More actions');
		this.openProductMenuButton = page.getByRole('tab', {
			exact: true,
			name: 'Open Product Menu',
		});
		this.page = page;
		this.pagesMenuItem = page
			.getByTestId('app')
			.filter({hasNotText: 'Locked', hasText: 'Pages'});
		this.pageTemplatesMenuItem = page
			.getByTestId('app')
			.filter({hasText: 'Page Templates'});
		this.pendingOrdersLink = page.getByRole('link', {
			exact: true,
			name: 'Pending Orders',
		});
		this.previewItemSelectorButton = page.getByTestId(
			'previewItemSelectorButton'
		);
		this.publishButton = page.getByRole('button', {
			exact: true,
			name: 'Publish',
		});
		this.saveButton = page.getByRole('button', {exact: true, name: 'Save'});
		this.searchFormInput = page.getByRole('textbox', {
			name: 'Search Form',
		});
		this.selectOtherItemDropdownItem = page.getByTestId(
			'selectOtherItemDropdownItem'
		);
		this.showLabelInput = page.getByLabel('Show Label', {exact: true});
		this.siteBuilderMenuItem = page
			.getByTestId('appGroup')
			.filter({hasText: 'Site Builder'});
		this.siteHomePageLink = page.getByRole('link', {
			exact: true,
			name: 'Home',
		});
		this.widgetPageTemplateButton = page
			.getByTestId('cardPageItemDirectory')
			.getByRole('button', {
				exact: true,
				name: 'Widget Page',
			});
	}

	async addFragment(itemName: string) {
		const source = await this.page.getByRole('menuitem', {
			name: itemName,
		});

		await source.focus();
		await source.press('Enter');
		await source.press('Enter');
	}

	async addProductFragment(itemName: string) {
		await this.page
			.getByRole('menuitem', {exact: true, name: 'Product'})
			.click();

		const source = await this.page.getByRole('menuitem', {
			name: itemName,
		});

		await source.focus();
		await source.press('Enter');
		await source.press('Enter');

		await this.page
			.getByRole('menuitem', {exact: true, name: 'Product'})
			.click();
	}

	async addWidgetToPage(widgetName: string) {
		await this.addWidgetButton.click();
		await this.searchFormInput.fill(widgetName);
		await this.addWidgetLabel(widgetName).click();
	}

	async cleanupSiteInitializerData(
		apiHelpers: DataApiHelpers,
		siteName: string
	) {
		const channels =
			await apiHelpers.headlessCommerceAdminChannel.getChannelsPage(
				siteName
			);

		apiHelpers.data.push({id: channels.items[0].id, type: 'channel'});

		const catalogs =
			await apiHelpers.headlessCommerceAdminCatalog.getCatalogsPage(
				siteName
			);

		const catalogId = catalogs.items[0].id;

		apiHelpers.data.push({id: catalogId, type: 'catalog'});

		const products =
			await apiHelpers.headlessCommerceAdminCatalog.getProductsPage(
				50,
				''
			);

		products.items.forEach((product) => {
			if (product.catalogId === catalogId) {
				apiHelpers.data.push({
					id: product.productId,
					type: 'product',
				});
			}
		});

		const options =
			await apiHelpers.headlessCommerceAdminCatalog.getOptions();

		options.items.forEach((option) => {
			apiHelpers.data.push({
				id: option.id,
				type: 'option',
			});
		});

		const optionCategories =
			await apiHelpers.headlessCommerceAdminCatalog.getOptionCategories();

		optionCategories.items.forEach((optionCategory) => {
			apiHelpers.data.push({
				id: optionCategory.id,
				type: 'optionCategory',
			});
		});

		const specifications =
			await apiHelpers.headlessCommerceAdminCatalog.getSpecifications();

		specifications.items.forEach((specification) => {
			apiHelpers.data.push({
				id: specification.id,
				type: 'specification',
			});
		});

		const warehouses =
			await apiHelpers.headlessCommerceAdminInventoryApiHelper.getWarehousesPage();

		warehouses.items.forEach((warehouse) => {
			apiHelpers.data.push({
				id: warehouse.id,
				type: 'warehouse',
			});
		});
	}

	async createDisplayPageTemplate(
		displayPageTemplateName: string,
		contentTypeLabel: string = 'Product',
		siteName: string = 'guest'
	) {
		await this.page
			.getByRole('link', {exact: true, name: 'Display Page Template'})
			.click();
		await this.page
			.getByRole('button', {exact: true, name: 'Blank'})
			.click();
		await this.page.getByLabel('Name').fill(displayPageTemplateName);
		await this.page
			.getByLabel('Content Type')
			.selectOption({label: contentTypeLabel});
		await Promise.all([
			this.page.getByRole('button', {exact: true, name: 'Save'}).click(),
			this.page.waitForResponse(
				(resp) => resp.status() === 200 && resp.url().includes(siteName)
			),
		]);
	}

	async configureDisplayPageTemplateTheme(themeName: string) {
		await this.moreActionsButton.click();
		await this.configureMenuItem.click();
		await this.designLink.click();
		await this.defineCustomThemeCheckbox.check();
		await this.changeCurrentThemeButton.click();
		await this.availableThemesFrame
			.getByRole('button', {exact: true, name: themeName})
			.click();
		await this.saveButton.click();
	}

	async createWidgetPage(pageName: string) {
		await this.addPageButton.first().click();
		await this.createPageMenuItem.click();
		await this.widgetPageTemplateButton.click();
		await this.addPageNameInput.waitFor({
			state: 'attached',
		});
		await this.addPageNameInput.click();
		await this.addPageNameInput.fill(pageName);
		await Promise.all([
			this.addPageModalSubmitButton.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp
						.url()
						.includes(
							'p_p_id=com_liferay_layout_admin_web_portlet_GroupPagesPortlet'
						)
			),
		]);
	}

	async goto() {
		await this.page.goto(liferayConfig.environment.baseUrl);
	}

	async goToDisplayPageTemplates(navigation: boolean = false) {
		if (navigation) {
			await this.goto();
		}

		if (
			(await this.closeProductMenuButton.isVisible()) &&
			(await this.pageTemplatesMenuItem.isHidden())
		) {
			await this.designMenuItem.click();
		}
		else if (await this.openProductMenuButton.isVisible()) {
			await this.openProductMenuButton.click();

			const promise = new Promise(() => {
				if (this.pageTemplatesMenuItem.isHidden()) {
					this.designMenuItem.click();
				}
			});

			Promise.race([
				promise,
				expect(this.pageTemplatesMenuItem).toBeVisible(),
			]).catch((error) => console.error(error));
		}

		await Promise.all([
			this.pageTemplatesMenuItem.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp
						.url()
						.includes(
							'p_p_id=com_liferay_layout_page_template_admin_web_portlet_LayoutPageTemplatesPortlet'
						)
			),
		]);

		await Promise.all([
			this.displayPageTemplatesLink.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp
						.url()
						.includes(
							'p_p_id=com_liferay_layout_page_template_admin_web_portlet_LayoutPageTemplatesPortlet'
						)
			),
		]);
	}

	async goToPages(navigation: boolean = true, siteName?: string) {
		if (navigation) {
			await this.goto();
		}

		if (siteName) {
			await this.page.goto(
				`/group/${siteName}/~/control_panel/manage?p_p_id=com_liferay_layout_admin_web_portlet_GroupPagesPortlet`
			);
		}
		else {
			if (
				(await this.closeProductMenuButton.isVisible()) &&
				(await this.pagesMenuItem.isHidden())
			) {
				await this.siteBuilderMenuItem.click();
			}
			else if (await this.openProductMenuButton.isVisible()) {
				await this.openProductMenuButton.click();

				if (await this.pagesMenuItem.isHidden()) {
					await this.siteBuilderMenuItem.click();
				}
			}

			await Promise.all([
				this.pagesMenuItem.click(),
				this.page.waitForResponse(
					(resp) =>
						resp.status() === 200 &&
						resp
							.url()
							.includes(
								'p_p_id=com_liferay_layout_admin_web_portlet_GroupPagesPortlet'
							)
				),
			]);
		}
	}

	async selectDisplayPageTemplatePreviewItem(itemName: string) {
		await this.previewItemSelectorButton.click();
		await this.selectOtherItemDropdownItem.click();

		const itemButton = await this.page
			.frameLocator('iframe[title="Select"]')
			.getByRole('button', {name: itemName});

		await expect(itemButton).toBeVisible();

		await itemButton.click();
	}
}
