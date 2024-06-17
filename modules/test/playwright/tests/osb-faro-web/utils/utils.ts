/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { Page, expect } from "@playwright/test";
export async function changeTimeFilterTo(page: Page, timeFilter: string) {
	await page.getByRole('button', {name: 'Last 30 days'}).click();
	await page.getByRole('menuitem', {name: timeFilter}).click();
}

export async function searchTerm(page: Page, searchTerm: string) {
	await page.getByPlaceholder('Search').click();
	await page.getByPlaceholder('Search').fill(searchTerm);
	await page.getByPlaceholder('Search').press('Enter');
}

export async function createIndividuals(apiHelpers, names: string[]) {
    const individuals = names.map(name => ({
        emailAddress: `${name}@liferay.com`,
        fields: [
            { dataSourceId: 0, name: 'givenName', value: name },
            { dataSourceId: 0, name: 'familyName', value: name },
            { dataSourceId: 0, name: 'email', value: `${name}@liferay.com` },
        ],
        firstName: name,
        id: `${name}@liferay.com`,
        lastName: name,
    }));

    await apiHelpers.jsonWebServicesOSBAsah.createIndividuals(individuals);
}
