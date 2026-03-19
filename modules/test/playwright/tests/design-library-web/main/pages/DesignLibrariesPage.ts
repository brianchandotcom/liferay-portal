/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {liferayConfig} from '../../../../liferay.config';
import POM from '../../../../utils/POM';

const PORTLET_NAME =
	'com_liferay_design_library_web_internal_portlet_DesignLibraryAdminPortlet';

const PORTLET_URL =
	`${liferayConfig.environment.baseUrl}/group/guest/~/control_panel/manage` +
	`?p_p_id=${PORTLET_NAME}`;

export class DesignLibrariesPage extends POM {
	readonly portletName = PORTLET_NAME;
	readonly emptyStateContainer: Locator;

	constructor(page: Page) {
		super(page, PORTLET_URL);

		this.emptyStateContainer = page.locator('.fds .c-empty-state');
	}

	override async waitFor() {
		await this.page
			.locator('.data-set-content-wrapper')
			.waitFor({state: 'visible'});
	}
}
