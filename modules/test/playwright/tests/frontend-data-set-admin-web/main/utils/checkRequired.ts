/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

export default async function checkRequired({
	formElements,
	page,
}: {
	formElements: Array<Locator>;
	page: Page;
}) {
	formElements.forEach((formElement) => {
		const parent = page
			.locator('.form-group.has-error')
			.filter({has: formElement});

		expect(parent).toBeVisible();

		expect(parent.locator('.lexicon-icon-asterisk')).toBeVisible();

		expect(parent.getByText('This field is required.')).toBeVisible();
	});
}
