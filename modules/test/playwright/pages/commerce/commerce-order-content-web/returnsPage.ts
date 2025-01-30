/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {waitForAlert} from '../../../utils/waitForAlert';
import {CommerceDNDTablePage} from '../commerceDNDTablePage';
import {CommerceLayoutsPage} from './commerceLayoutsPage';

export class ReturnsPage extends CommerceDNDTablePage {
	readonly layoutsPage: CommerceLayoutsPage;
	readonly page: Page;

	constructor(page: Page) {
		super(
			page,
			'#_com_liferay_commerce_order_content_web_internal_portlet_CommerceReturnContentPortlet_return-content-container .fds table'
		);

		this.layoutsPage = new CommerceLayoutsPage(page);
		this.page = page;
	}

	async addReturnsWidget() {
		await this.layoutsPage.addWidgetToPage('Returns');
		await waitForAlert(
			this.page,
			'Success:The application was added to the page.'
		);
	}

	async goto() {
		await this.layoutsPage.goto();
	}
}
