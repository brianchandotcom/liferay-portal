/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

export default async function saveFromModal({
	page,
	saveText = 'Save',
}: {
	page: Page;
	saveText?: string;
}) {
	const saveButton = page.locator('.liferay-modal').getByRole('button', {
		exact: true,
		name: saveText,
	});

	await expect(saveButton).toBeInViewport();

	await saveButton.click();

	await expect(saveButton).not.toBeInViewport();

	const toastContainer = page.locator('.alert-container');

	await expect(toastContainer.getByText('Success')).toBeInViewport();

	await toastContainer
		.getByRole('button', {
			name: 'Close',
		})
		.click();

	await expect(toastContainer).toBeHidden();
}
