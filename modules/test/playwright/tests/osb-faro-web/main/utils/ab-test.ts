/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

import {liferayConfig} from '../../../../liferay.config';
import {clickOnLink} from './actions';

export async function checkEmptyStateOnACSide(page: Page) {
	await expect(page.getByText('There are no tests found.')).toBeVisible();
}

export async function clickOnActionButton({
	name,
	page,
}: {
	name: string;
	page: Page;
}) {
	await clickOnLink({baseUrl: liferayConfig.environment.baseUrl, name, page});
}
