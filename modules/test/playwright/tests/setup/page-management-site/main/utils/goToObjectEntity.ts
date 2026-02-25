import {Page, expect} from '@playwright/test';

import {expandSection} from '../../../../../utils/expandSection';
import {openProductMenu} from '../../../../../utils/productMenu';
import {OBJECT_ENTITIES} from '../constants/objects';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type SiteScope = 'Applications' | 'Control Panel';

export async function goToObjectEntity({
	entityName,
	entityPluralName,
	page,
	siteScope,
}: {
	entityName: string;
	entityPluralName?: string;
	page: Page;
	siteScope?: SiteScope;
}) {
	const pluralName = entityPluralName || OBJECT_ENTITIES[entityName].plural;

	// Go to entity

	await expect(async () => {
		if (siteScope) {
			await page
				.getByRole('button', {name: 'Open Applications Menu'})
				.click({timeout: 2000});

			await page
				.getByRole('tab', {name: siteScope})
				.click({timeout: 2000});

			await page
				.getByRole('menuitem', {name: pluralName})
				.click({timeout: 2000});
		}
		else {
			await openProductMenu(page);

			const sectionButton = page.getByRole('menuitem', {
				name: 'Content & Data',
			});

			await expandSection(sectionButton);

			await page
				.getByRole('menuitem', {name: pluralName})
				.click({timeout: 2000});
		}

		await page.locator('.fds tbody').waitFor({timeout: 2000});
	}).toPass();
}
