/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {reloadImportPage} from './reloadImportPage';

export async function openImportFieldset({
	maxAttempts = 5,
	name,
	page,
}: {
	maxAttempts?: number;
	name: string;
	page: Page;
}) {
	let attempts = 0;

	while (attempts < maxAttempts) {
		const fieldset = page
			.getByRole('group', {
				name,
			})
			.getByRole('button', {name});

		if (!(await _isExpanded(fieldset))) {
			await fieldset.click();
		}
		else {
			break;
		}

		if (!(await _isExpanded(fieldset))) {
			reloadImportPage(page);
		}

		attempts++;
	}
}

async function _isExpanded(fieldset: Locator) {
	return await fieldset.evaluate(
		(element) => element.getAttribute('aria-expanded') === 'true'
	);
}
