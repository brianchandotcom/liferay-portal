/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from './clickAndExpectToBeVisible';

function getPaginator(page: Page | FrameLocator) {
	return page.locator('[data-qa-id="paginator"]');
}

export async function previousPage(page) {
	await getPaginator(page).getByTitle('Previous page').click();
}

export async function nextPage(page) {
	await getPaginator(page).getByTitle('next page').click();
}

export async function gotoPage(page, pageNumber: number | string) {
	await getPaginator(page)
		.getByRole('link', {name: `Page ${pageNumber}`})
		.click();
}

export async function setItemsPerPage(page, limit: number | string) {
	await clickAndExpectToBeVisible({
		autoClick: true,
		target: getPaginator(page).getByRole('option', {
			name: `${limit} Entries per Page`,
		}),
		trigger: getPaginator(page).getByLabel('Items per Page'),
	});
}
