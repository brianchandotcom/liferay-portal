/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {expandSection} from './expandSection';

export async function openFieldset(
	page: Page | FrameLocator,
	name: string
): Promise<Locator> {
	const fieldset = page.getByRole('group', {
		name,
	});

	await expandSection(fieldset.getByRole('button', {name}));

	return fieldset;
}
