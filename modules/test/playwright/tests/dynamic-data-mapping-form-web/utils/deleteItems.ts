/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {FormsPage} from '../../../pages/dynamic-data-mapping-form-web/FormsPage';

export async function deleteItems(formsPage: FormsPage, page: Page) {
	await page.waitForTimeout(1000);

	if (await formsPage.managementToolbarSelectAllItems.isEnabled()) {
		await formsPage.managementToolbarSelectAllItems.click();

		page.once('dialog', (dialog) => {
			dialog.accept();
		});

		await formsPage.managementToolbarDeleteButton.click();
	}
}
