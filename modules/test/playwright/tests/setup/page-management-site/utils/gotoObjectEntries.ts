import {Page} from '@playwright/test';

import {openProductMenu} from '../../../../utils/productMenu';
import {JournalPage} from '../../../journal-web/pages/JournalPage';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
export async function gotoObjectEntries({
	entityName,
	page,
	siteUrl,
}: {
	entityName: 'All Fields' | 'Lemons' | 'Lemon Baskets' | 'Potatoes';
	page: Page;
	siteUrl: Site['friendlyUrlPath'];
}) {

	// Go to Web Content admin

	const journalPage = new JournalPage(page);
	await journalPage.goto(siteUrl);

	// Go to entity

	await openProductMenu(page);

	await page.getByRole('menuitem', {name: entityName}).click();

	await page.locator('.dnd-tbody').waitFor();
}
