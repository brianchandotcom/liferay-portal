import {FrameLocator, Page} from '@playwright/test';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
export async function waitForSuccessAlert(
	page: Page | FrameLocator,
	text = 'Success:Your request completed successfully.',
	{autoClose} = {autoClose: true}
) {
	const alert = page.locator('.alert-success', {
		hasText: text,
	});

	await alert.waitFor();

	if (autoClose) {
		await alert.getByLabel('Close').click();

		await alert.waitFor({state: 'hidden'});
	}
}
