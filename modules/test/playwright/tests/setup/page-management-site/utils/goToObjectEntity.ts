import {Page} from '@playwright/test';

import {expandSection} from '../../../../utils/expandSection';
import {openProductMenu} from '../../../../utils/productMenu';
import {
	OBJECT_ENTITIES,
	PageManagementObjectEntity,
} from '../constants/objects';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
export async function goToObjectEntity({
	entityName,
	page,
}: {
	entityName: PageManagementObjectEntity;
	page: Page;
}) {
	const pluralName = OBJECT_ENTITIES[entityName].plural;

	// Go to entity

	await openProductMenu(page);

	const sectionButton = page.getByRole('menuitem', {
		name: 'Content & Data',
	});

	await expandSection(sectionButton);

	await page.getByRole('menuitem', {name: pluralName}).click();

	await page.locator('.dnd-tbody').waitFor();
}
