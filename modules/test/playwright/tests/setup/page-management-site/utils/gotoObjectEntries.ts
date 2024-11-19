import {Page} from '@playwright/test';

import {expandSection} from '../../../../utils/expandSection';
import {openProductMenu} from '../../../../utils/productMenu';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
export async function gotoObjectEntries({
	entityName,
	page,
}: {
	entityName: 'All Fields' | 'Lemons' | 'Lemon Baskets' | 'Potatoes';
	page: Page;
}) {

	// Go to entity

	await openProductMenu(page);

	const sectionButton = page.getByRole('menuitem', {
		name: 'Content & Data',
	});

	await expandSection(sectionButton);

	await page.getByRole('menuitem', {name: entityName}).click();

	await page.locator('.dnd-tbody').waitFor();
}
