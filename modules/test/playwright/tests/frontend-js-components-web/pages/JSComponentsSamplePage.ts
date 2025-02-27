/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {liferayConfig} from '../../../liferay.config';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import getPageDefinition from '../../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../../layout-content-page-editor-web/utils/getWidgetDefinition';

export class JSComponentsSamplePage {
	readonly adminLocalizedInputContainer: Locator;
	readonly adminEnglishLocalizedInputContainer: Locator;
	readonly localizedInput: Locator;
	readonly page: Page;
	readonly tablist: Locator;
	readonly translationManagerEnglishTriggerButton: Locator;
	readonly translationManagerTriggerButton: Locator;
	readonly translationManageButton: Locator;
	readonly translationManagerDialog: Locator;
	readonly translationManagerCatalanChoice: Locator;
	readonly translationManagerCancelButton: Locator;
	readonly translationManagerDoneButton: Locator;
	readonly translationManagerAddButton: Locator;
	readonly translationManagerSearchInput: Locator;
	readonly translationManagerGermanChoice: Locator;
	readonly translationManagerCatalanRow: Locator;
	readonly translationManagerFrenchRow: Locator;

	constructor(page: Page) {
		this.page = page;
		this.tablist = page.getByRole('tablist');
		this.adminLocalizedInputContainer = page
			.locator('.input-localized.input-localized-input')
			.first();
		this.adminEnglishLocalizedInputContainer = page
			.getByText('Admin English (United States)')
			.first()
			.locator('.input-localized.input-localized-input')
			.first();
		this.localizedInput =
			this.adminLocalizedInputContainer.getByRole('textbox');
		this.translationManagerTriggerButton =
			this.adminLocalizedInputContainer.getByRole('button');
		this.translationManagerEnglishTriggerButton =
			this.adminEnglishLocalizedInputContainer.getByRole('button');
		this.translationManageButton = page.getByRole('button', {
			name: 'Manage Translations',
		});
		this.translationManagerDialog = page.getByRole('dialog', {
			name: 'Manage Translations',
		});
		this.translationManagerCatalanChoice = page.getByRole('menuitem', {
			name: 'Not translated into Catalan.',
		});
		this.translationManagerCancelButton =
			this.translationManagerDialog.getByRole('button', {name: 'Cancel'});
		this.translationManagerDoneButton =
			this.translationManagerDialog.getByRole('button', {name: 'Done'});
		this.translationManagerAddButton = page.getByLabel('Add', {
			exact: true,
		});
		this.translationManagerSearchInput = page.getByPlaceholder('Search');
		this.translationManagerGermanChoice = page.getByRole('menuitem', {
			name: 'Not translated into German.',
		});
		this.translationManagerCatalanRow =
			this.translationManagerDialog.getByRole('row', {
				name: 'Catalan (Spain)',
			});
		this.translationManagerFrenchRow =
			this.translationManagerDialog.getByRole('row', {
				name: 'French (France)',
			});
	}

	async selectTab(tabName: string, target) {
		const tabHeading = this.tablist.getByRole('tab', {name: tabName});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target,
			trigger: tabHeading,
		});
	}

	async setupJSComponentsSampleWidget({apiHelpers, site}) {
		const widgetDefinition = getWidgetDefinition({
			id: getRandomString(),
			widgetName:
				'com_liferay_frontend_js_components_sample_web_portlet_FrontendJSComponentsSampleWebPortlet',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([widgetDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});

		await this.page.goto(
			`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
		);
	}
}
