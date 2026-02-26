/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeHidden} from '../../utils/clickAndExpectToBeHidden';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../utils/portletUrls';
import {openProductMenu} from '../../utils/productMenu';

export class ProductMenuPage {
	readonly backButton: Locator;
	readonly blogsButton: Locator;
	readonly closeProductMenuButton: Locator;
	readonly configurationButton: Locator;
	readonly contentAndDataButton: Locator;
	readonly exportButton: Locator;
	readonly formsButton: Locator;
	readonly importButton: Locator;
	readonly membershipsButton: Locator;
	readonly messageBoardsButton: Locator;
	readonly segmentsButton: Locator;
	readonly openProductMenuButton: Locator;
	readonly page: Page;
	readonly pagesButton: Locator;
	readonly peopleButton: Locator;
	readonly productMenuHeader: Locator;
	readonly productMenuWrapper: Locator;
	readonly publishingButton: Locator;
	readonly siteBuilderButton: Locator;
	readonly siteSettingsButton: Locator;
	readonly stagingMenuItem: Locator;
	readonly webContentButton: Locator;

	constructor(page: Page) {
		this.backButton = page.getByRole('link', {name: 'Back'});
		this.blogsButton = page.getByRole('menuitem', {
			name: 'Blogs',
		});
		this.configurationButton = page.getByRole('menuitem', {
			exact: true,
			name: 'Configuration',
		});
		this.contentAndDataButton = page.getByRole('menuitem', {
			name: 'Content & Data',
		});
		this.exportButton = page.getByRole('menuitem', {
			name: 'Export',
		});
		this.formsButton = page.getByRole('menuitem', {
			exact: true,
			name: 'Forms',
		});
		this.importButton = page.getByRole('menuitem', {
			name: 'Import',
		});
		this.membershipsButton = page.getByRole('menuitem', {
			name: 'Memberships',
		});
		this.messageBoardsButton = page.getByRole('menuitem', {
			name: 'Message Boards',
		});
		this.segmentsButton = page.getByRole('menuitem', {
			name: 'Segments',
		});
		this.page = page;
		this.pagesButton = page.getByRole('menuitem', {name: 'Pages'});
		this.peopleButton = page.getByRole('menuitem', {name: 'People'});
		this.openProductMenuButton = page.getByLabel('Open Product Menu');
		this.closeProductMenuButton = page.getByLabel('Close Product Menu');
		this.productMenuHeader = page.locator(
			'[id="_com_liferay_product_navigation_product_menu_web_portlet_ProductMenuPortlet_site_administrationHeading"] div'
		);
		this.publishingButton = page.getByRole('menuitem', {
			name: 'Publishing',
		});
		this.productMenuWrapper = page.getByRole('navigation', {
			name: 'Product Menu',
		});
		this.siteBuilderButton = page.getByRole('menuitem', {
			name: 'Site Builder',
		});
		this.siteSettingsButton = page.getByRole('menuitem', {
			exact: true,
			name: 'Site Settings',
		});
		this.stagingMenuItem = page.getByRole('menuitem', {
			name: 'Staging',
		});
		this.webContentButton = page.getByRole('menuitem', {
			name: 'Web Content',
		});
	}

	async checkIfAdecuateProductMenu(templateName: string) {
		await this.productMenuHeader
			.filter({hasText: templateName})
			.nth(2)
			.isVisible();
	}

	async clickSpecificPage(pageName: string) {
		await this.pagesButton.click();
		await this.page.getByLabel(pageName, {exact: true}).click();
	}

	async getSiteTemplateUrl(templateName: string) {
		return await this.page.getByText(templateName).getAttribute('href');
	}

	async goToBlogs() {
		await this.openProductMenuIfClosed();

		await this.contentAndDataButton.click();
		await this.blogsButton.click();
	}

	async goToForms() {
		await this.contentAndDataButton.click();
		await this.formsButton.click();
	}

	async goToMemberships() {
		await this.peopleButton.click();
		await this.membershipsButton.click();
	}

	async goToMessageBoards() {
		await this.openProductMenuIfClosed();

		await this.contentAndDataButton.click();
		await this.messageBoardsButton.click();
	}

	async goToPages() {
		await openProductMenu(this.page);

		await clickAndExpectToBeVisible({
			target: this.page
				.locator('.product-menu')
				.getByText('Pages', {exact: true}),
			trigger: this.page
				.locator('.product-menu')
				.getByText('Site Builder', {exact: true}),
		});

		await clickAndExpectToBeVisible({
			target: this.page.getByLabel('Control Menu').getByText('Pages'),
			trigger: this.page
				.locator('.product-menu')
				.getByText('Pages', {exact: true}),
		});
	}

	async goToPublishingExport() {
		await this.publishingButton.click();
		await this.exportButton.click();
	}

	async goToPublishingImport() {
		await this.publishingButton.click();
		await this.importButton.click();
	}

	async goToPublishingStaging() {
		await this.publishingButton.click();
		await this.stagingMenuItem.click();
	}

	async goToSegments() {
		await this.openProductMenuIfClosed();

		await this.peopleButton.click();
		await this.segmentsButton.click();
	}

	async goToSite(name: string) {
		await openProductMenu(this.page);

		const frame = this.page.frameLocator('iframe[title="Select Site"]');

		const goToOtherSiteButton = this.page.getByRole('button', {
			name: 'Go to Other Site',
		});

		await goToOtherSiteButton.waitFor();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: frame.getByRole('link', {
				name: 'All Sites',
			}),
			trigger: this.page.getByRole('button', {
				name: 'Go to Other Site',
			}),
		});

		await clickAndExpectToBeHidden({
			target: this.page.locator('.modal-title', {
				hasText: 'Select Site',
			}),
			trigger: frame
				.getByRole('link', {name})
				.and(this.page.locator('.card-title')),
		});
	}

	async goToSiteSettings() {
		await this.configurationButton.click();
		await this.siteSettingsButton.click();
	}

	async goToTeams(siteURL?: string) {
		await this.page.goto(
			`/group${siteURL || '/guest'}${PORTLET_URLS.teams}`
		);
	}

	async goToWebContent() {
		await this.contentAndDataButton.click();
		await this.webContentButton.click();
	}

	async goToPortlet({
		category,
		panel,
		portlet,
	}: {
		category: string;
		panel: string;
		portlet: string;
	}) {
		await this.page.reload();

		await this.openProductMenuIfClosed();

		const categoryTrigger = this.productMenuWrapper
			.getByLabel(panel)
			.getByRole('menuitem', {name: category});

		await clickAndExpectToBeVisible({
			target: categoryTrigger,
			trigger: this.productMenuWrapper.getByRole('button', {
				name: panel,
			}),
		});

		const portletTrigger = this.productMenuWrapper
			.getByLabel(panel)
			.getByLabel(category)
			.getByRole('menuitem', {name: portlet});

		await clickAndExpectToBeVisible({
			target: portletTrigger,
			trigger: categoryTrigger,
		});

		await clickAndExpectToBeVisible({
			target: this.page.getByRole('heading', {
				exact: true,
				name: portlet,
			}),
			trigger: portletTrigger,
		});
	}

	async openProductMenuIfClosed() {
		await clickAndExpectToBeVisible({
			target: this.productMenuWrapper,
			trigger: this.openProductMenuButton,
		});
	}
}
