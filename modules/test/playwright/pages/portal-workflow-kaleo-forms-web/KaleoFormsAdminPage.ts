/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PORTLET_URLS} from '../../utils/portletUrls';

export class KaleoFormsAdminPage {
	readonly actionMenuChooseOption: Locator;
	readonly addNewProcessButton: Locator;
	readonly addNewProcessNextButton: Locator;
	readonly assignFormButton: Locator;
	readonly deleteButton: Locator;
	readonly firstFieldSetActionMenu: Locator;
	readonly firstFormsActionMenu: Locator;
	readonly firstWorkflowActionMenu: Locator;
	readonly newProcessNameField: Locator;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly selectAllItemsCheckbox: Locator;
	readonly submitNewProcessEntryButton: Locator;

	constructor(page: Page) {
		this.actionMenuChooseOption = page.getByRole('link', {name: 'Choose'});
		this.addNewProcessButton = page.getByRole('link', {name: 'Add'});
		this.addNewProcessNextButton = page.getByRole('button', {name: 'Next'});
		this.assignFormButton = page.getByRole('link', {name: 'Assign Form'});
		this.deleteButton = page.getByRole('button', {name: 'Delete'});
		this.firstFieldSetActionMenu = page.locator(
			'[id="_com_liferay_portal_workflow_kaleo_forms_web_portlet_KaleoFormsAdminPortlet_ddmStructuresSearchContainer_1_menu"]'
		);
		this.firstFormsActionMenu = page.locator(
			'[id="_com_liferay_portal_workflow_kaleo_forms_web_portlet_KaleoFormsAdminPortlet_kaleoTaskFormPairsSearchContainer_1_menu"]'
		);
		this.firstWorkflowActionMenu = page.locator(
			'[id="_com_liferay_portal_workflow_kaleo_forms_web_portlet_KaleoFormsAdminPortlet_workflowDefinitionsSearchContainer_1_menu"]'
		);
		this.newProcessNameField = page.getByLabel('Name Required');
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.selectAllItemsCheckbox = page.getByLabel(
			'Select All Items on the Page'
		);
		this.submitNewProcessEntryButton = page.getByRole('link', {
			name: 'Submit New',
		});
	}

	async clickKaleoFormProcessLink(processName: string) {
		await this.page.getByRole('link', {name: processName}).click();
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.kaleoFormsAdmin}`
		);
	}
}
