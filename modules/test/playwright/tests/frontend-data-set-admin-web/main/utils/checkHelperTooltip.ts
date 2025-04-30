/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

export default async function checkLocalized({
	formElement,
	page,
	text,
}: {
	formElement: Locator;
	page: Page;
	text: string;
}) {
	const parent = page.locator('.form-group').filter({has: formElement});

	const tooltipTrigger = parent.locator('.lexicon-icon-question-circle-full');

	await expect(tooltipTrigger).toBeVisible();

	await tooltipTrigger.hover();

	await expect(page.getByText(text)).toBeVisible();
}
