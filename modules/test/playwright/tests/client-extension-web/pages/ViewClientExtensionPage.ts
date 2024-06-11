/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {liferayConfig} from '../../../liferay.config';

export class ViewClientExtensionPage {
	readonly externalReferenceCode: string;
	readonly nameLocator: Locator;
	readonly page: Page;

	constructor(page: Page, externalReferenceCode: string) {
		this.page = page;
		this.externalReferenceCode = externalReferenceCode;

		this.nameLocator = page.getByLabel('Name', {exact: true});
	}

	fieldLocator(fieldLabel: string): Locator {
		return this.page.getByLabel(fieldLabel, {exact: true});
	}

	async assertReadOnlyLocator(locator: Locator, fieldValue: string) {
		await expect(locator).toBeVisible();
		await expect(locator).toBeDisabled();
		await expect(locator).toHaveValue(fieldValue);
	}

	async assertReadOnlyField(fieldName: string, fieldValue: string) {
		await this.assertReadOnlyLocator(
			this.fieldLocator(fieldName),
			fieldValue
		);
	}

	async goto() {
		const portletId =
			'com_liferay_client_extension_web_internal_portlet_ClientExtensionAdminPortlet';

		const params = new URLSearchParams();

		params.append('p_p_id', portletId);
		params.append(
			`_${portletId}_mvcRenderCommandName`,
			'/client_extension_admin/view_client_extension_entry'
		);
		params.append(
			`_${portletId}_externalReferenceCode`,
			this.externalReferenceCode
		);

		await this.page.goto(
			`${liferayConfig.environment.baseUrl}/group/control_panel/manage?${params}`
		);
	}
}
