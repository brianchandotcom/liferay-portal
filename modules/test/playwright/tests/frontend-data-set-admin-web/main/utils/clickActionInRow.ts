/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect} from '@playwright/test';

import getRowByText from './getRowByText';

export default async function clickActionInRow({actionName, page, rowName}) {
	const row = await getRowByText({page, text: rowName});

	await expect(row).toBeInViewport();

	row.locator('.actions-cell button').click();

	const actionButton = page.getByRole('menuitem', {
		name: actionName,
	});

	await expect(actionButton).toBeInViewport();

	await actionButton.click();
}
