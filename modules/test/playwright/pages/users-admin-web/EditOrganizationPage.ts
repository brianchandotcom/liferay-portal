/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {getRandomInt} from '../../utils/getRandomInt';
import {waitForAlert} from '../../utils/waitForAlert';
import {UsersAndOrganizationsPage} from './UsersAndOrganizationsPage';

export class EditOrganizationPage {
	readonly addAddressButton: Locator;
	readonly addAddressCityInput: Locator;
	readonly addAddressCountrySelect: Locator;
	readonly addAddressPostalCodeInput: Locator;
	readonly addAddressRegionSelect: Locator;
	readonly addAddressStreet1Input: Locator;
	readonly addAddressTypeSelect: Locator;
	readonly addEmailAddressButton: Locator;
	readonly addEmailAddressInput: Locator;
	readonly addOpeningHoursButton: Locator;
	readonly addPhoneNumberButton: Locator;
	readonly addPhoneNumberInput: Locator;
	readonly addWebsiteButton: Locator;
	readonly addWebsiteUrlInput: Locator;
	readonly addressesLink: Locator;
	readonly backButton: Locator;
	readonly categoryGridCell: (categoryName: string) => Locator;
	readonly categoryOption: (categoryName: string) => Locator;
	readonly categoryInput: (vocabularyName: string) => Locator;
	readonly contactInformationLink: Locator;
	readonly createSiteToggle: Locator;
	readonly commentsInput: Locator;
	readonly contactLink: Locator;
	readonly countrySelect: Locator;
	readonly editOrgLaborIconMenu: Locator;
	readonly headerTitle: Locator;
	readonly manageSiteLink: Locator;
	readonly mondayCloseSelect: Locator;
	readonly mondayOpenSelect: Locator;
	readonly nameInput: Locator;
	readonly openingHoursLink: Locator;
	readonly organizationEditMenuItem: Locator;
	readonly organizationSiteLink: Locator;
	readonly organizationSiteSaveButton: Locator;
	readonly orgLaborListTypeSelectedValue: Locator;
	readonly page: Page;
	readonly regionSelect: Locator;
	readonly saveButton: Locator;
	readonly securityQuestionsInput: Locator;
	readonly securityQuestionsLink: Locator;
	readonly securityQuestionsLocaleButton: Locator;
	readonly securityQuestionsLocaleItem: (locale: string) => Locator;
	readonly siteIdInput: Locator;
	readonly tagsInput: Locator;
	readonly typeLabel: Locator;
	readonly usersAndOrganizationsPage: UsersAndOrganizationsPage;

	constructor(page: Page) {
		this.addAddressButton = page.getByLabel('Add Addresses');
		this.addAddressCityInput = page.getByLabel('City Required');
		this.addAddressCountrySelect = page.getByLabel('Country Required');
		this.addAddressPostalCodeInput = page.getByLabel(
			'Postal Code Required'
		);
		this.addAddressRegionSelect = page.getByLabel('Region Required');
		this.addAddressStreet1Input = page.getByLabel('Street 1 Required');
		this.addAddressTypeSelect = page.getByLabel('Type', {exact: true});
		this.addEmailAddressButton = page.getByLabel(
			'Add Additional Email Addresses'
		);
		this.addEmailAddressInput = page.getByLabel('Address Required');
		this.addOpeningHoursButton = page.locator('.add-opening-hours-link');
		this.addPhoneNumberButton = page.getByLabel('Add Phone Numbers');
		this.addPhoneNumberInput = page.getByLabel('Number Required');
		this.addWebsiteButton = page.getByLabel('Add Website');
		this.addWebsiteUrlInput = page.getByLabel('Url Required');
		this.addressesLink = page.getByRole('link', {
			exact: true,
			name: 'Addresses',
		});
		this.backButton = page.getByRole('link', {
			name: 'Go to Users and Organizations',
		});
		this.categoryGridCell = (categoryName: string) =>
			page.getByRole('gridcell', {exact: true, name: categoryName});
		this.categoryOption = (categoryName: string) =>
			page.getByRole('option', {name: categoryName});
		this.categoryInput = (vocabularyName: string) =>
			page.getByRole('combobox', {exact: true, name: vocabularyName});
		this.contactInformationLink = page.getByRole('link', {
			exact: true,
			name: 'Contact Information',
		});
		this.createSiteToggle = page.getByLabel('Create Site');
		this.commentsInput = page.locator(
			'#_com_liferay_users_admin_web_portlet_UsersAdminPortlet_comments'
		);
		this.contactLink = page.getByRole('link', {name: 'Contact'});
		this.countrySelect = page
			.locator(
				'#_com_liferay_users_admin_web_portlet_UsersAdminPortlet_countryId'
			)
			.or(
				page.locator(
					'#_com_liferay_users_admin_web_portlet_MyOrganizationsPortlet_countryId'
				)
			);
		this.editOrgLaborIconMenu = page.getByTestId('editOrgLaborIconMenu');
		this.headerTitle = page.getByTestId('headerTitle');
		this.manageSiteLink = page.getByRole('link', {name: 'Manage Site'});
		this.mondayCloseSelect = page.locator('select[id*="monClose"]');
		this.mondayOpenSelect = page.locator('select[id*="monOpen"]');
		this.nameInput = page
			.locator(
				'#_com_liferay_users_admin_web_portlet_UsersAdminPortlet_name'
			)
			.or(
				page.locator(
					'#_com_liferay_users_admin_web_portlet_MyOrganizationsPortlet_name'
				)
			);
		this.openingHoursLink = page.getByRole('link', {name: 'Opening Hours'});
		this.organizationEditMenuItem = page.getByRole('menuitem', {
			name: 'Edit',
		});
		this.organizationSiteLink = page.getByRole('link', {
			name: 'Organization Site',
		});
		this.organizationSiteSaveButton = page.getByRole('button', {
			name: 'Save',
		});
		this.orgLaborListTypeSelectedValue = page
			.locator(
				'#_com_liferay_users_admin_web_portlet_UsersAdminPortlet_orgLaborListTypeId'
			)
			.locator('option[selected=""]');
		this.page = page;
		this.regionSelect = page
			.locator(
				'#_com_liferay_users_admin_web_portlet_UsersAdminPortlet_regionId'
			)
			.or(
				page.locator(
					'#_com_liferay_users_admin_web_portlet_MyOrganizationsPortlet_regionId'
				)
			);
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.securityQuestionsInput = page.locator(
			'textarea[id*="reminderQueries"]'
		);
		this.securityQuestionsLink = page.getByRole('link', {
			exact: true,
			name: 'Security Questions',
		});
		this.securityQuestionsLocaleButton = page.locator(
			'.form-group:has(textarea[id*="reminderQueries"]) button'
		);
		this.securityQuestionsLocaleItem = (locale: string) =>
			page.locator('.palette-item').getByText(locale);
		this.siteIdInput = page.getByLabel('Site ID');
		this.tagsInput = page.locator(
			"div[id*='assetTagsSelector'] input.form-control-inset"
		);
		this.typeLabel = page.getByLabel('Type Label');
		this.usersAndOrganizationsPage = new UsersAndOrganizationsPage(page);
	}

	async addOrganization(name = `Organization${getRandomInt()}`) {
		await this.nameInput.fill(name);
		await this.saveButton.click();

		await waitForAlert(this.page);

		return name;
	}

	async addTag(tagName: string) {
		await this.tagsInput.fill(tagName);
		await this.tagsInput.press('Enter');
		await this.tagsInput.press('Tab');
		await this.saveButton.click();

		await waitForAlert(this.page);
	}

	async gotoOrganizationEditOpeningHoursTab(organizationName: string) {
		await expect(async () => {
			await (
				await this.usersAndOrganizationsPage.organizationsTable.rowActions(
					organizationName
				)
			).click();

			await expect(this.organizationEditMenuItem).toBeVisible();
		}).toPass({timeout: 5000});

		await this.organizationEditMenuItem.click();
		await this.contactLink.click();
		await this.openingHoursLink.click();
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.organizationEditMenuItem,
			trigger: this.editOrgLaborIconMenu,
		});
	}
}
