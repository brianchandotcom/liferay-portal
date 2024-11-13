/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {waitForAlert} from '../../../utils/waitForAlert';

type DistributionScope = 'Organizations' | 'Roles' | 'Sites' | 'User Groups';

export class AnnouncementsWidgetConfigurationPage {
	readonly page: Page;

	readonly configurationIframe: FrameLocator;

	readonly distributionScopeOrganizationsTab: Locator;
	readonly distributionScopeRolesTab: Locator;
	readonly distributionScopeSitesTab: Locator;
	readonly distributionScopeUserGroupsTab: Locator;
	readonly cancelButton: Locator;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.configurationIframe = this.page.frameLocator(
			'iframe[title*="Announcements"]'
		);

		this.distributionScopeOrganizationsTab =
			this.configurationIframe.getByRole('tab', {name: 'Organizations'});
		this.distributionScopeRolesTab = this.configurationIframe.getByRole(
			'tab',
			{name: 'Roles'}
		);
		this.distributionScopeSitesTab = this.configurationIframe.getByRole(
			'tab',
			{name: 'Sites'}
		);
		this.distributionScopeUserGroupsTab =
			this.configurationIframe.getByRole('tab', {name: 'User Groups'});

		this.cancelButton = this.configurationIframe.getByRole('button', {
			name: 'Cancel',
		});
		this.saveButton = this.configurationIframe.getByRole('button', {
			name: 'Save',
		});
	}

	async distributionScopeMoveToCurrent(
		distributionScope: DistributionScope,
		option: string
	) {
		await this.configurationIframe
			.getByRole('tab', {name: distributionScope})
			.click();

		const distributionScopeCode =
			this.getDistributionScopeCode(distributionScope);

		const availableScopeList = this.configurationIframe.locator(
			`[id="_com_liferay_portlet_configuration_web_portlet_PortletConfigurationPortlet_availableScope${distributionScopeCode}ExternalReferenceCodes"]`
		);

		await availableScopeList.waitFor();

		await availableScopeList.selectOption({label: option});

		await this.configurationIframe
			.getByRole('button', {
				name: 'Move selected items from Available to Current.',
			})
			.click();
	}

	async saveAndClose() {
		await this.saveButton.click();

		await waitForAlert(
			this.configurationIframe,
			'Success:You have successfully updated the setup.'
		);

		await this.cancelButton.click();
	}

	private getDistributionScopeCode(distributionScope: DistributionScope) {
		switch (distributionScope) {
			case 'Organizations':
				return 'Organization';
			case 'Roles':
				return 'Role';
			case 'Sites':
				return 'Group';
			default:
				return 'UserGroup';
		}
	}
}
