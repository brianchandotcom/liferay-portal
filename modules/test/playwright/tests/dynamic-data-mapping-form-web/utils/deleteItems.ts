/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormsPage} from '../../../pages/dynamic-data-mapping-form-web/FormsPage';

export async function deleteItems(formsPage: FormsPage) {
	await formsPage.page.waitForTimeout(1000);

	if (await formsPage.managementToolbarSelectAllItems.isEnabled()) {
		await formsPage.managementToolbarSelectAllItems.click();

		formsPage.page.once('dialog', async (dialog) => {
			await dialog.accept();
		});

		await formsPage.managementToolbarDeleteButton.click();
	}
}
