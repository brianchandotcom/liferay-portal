/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {digitalSalesRoomPagesTest} from '../../../fixtures/digitalSalesRoomPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';

export const test = mergeTests(
	dataApiHelpersTest,
	digitalSalesRoomPagesTest,
	featureFlagsTest({
		'LPD-66359': {enabled: true},
	}),
	loginTest()
);

test.afterEach(async ({apiHelpers}) => {
	const digitalSalesRooms =
		await apiHelpers.headlessDigitalSalesRoom.getDigitalSalesRooms();

	for (const digitalSalesRoom of digitalSalesRooms.items) {
		await apiHelpers.headlessSite.deleteSite(digitalSalesRoom.id);
	}

	const digitalSalesRoomTemplates =
		await apiHelpers.headlessDigitalSalesRoom.getDigitalSalesRoomTemplates();

	for (const digitalSalesRoomTemplate of digitalSalesRoomTemplates.items) {
		await apiHelpers.headlessSite.deleteSite(digitalSalesRoomTemplate.id);
	}
});

test(
	'Got to the template page',
	{tag: '@LPD-73189'},
	async ({digitalSalesRoomTemplatesPage, digitalSalesRoomsPage}) => {
		await digitalSalesRoomsPage.goto();

		await expect(
			digitalSalesRoomsPage.digitalSalesRoomsTable.searchInput
		).toBeVisible();

		await expect(digitalSalesRoomsPage.roomLink).toBeVisible();
		await expect(digitalSalesRoomsPage.templateLink).toBeVisible();

		await digitalSalesRoomsPage.templateLink.click();

		await expect(
			digitalSalesRoomTemplatesPage.newDigitalSalesRoomTemplateButton
		).toBeVisible();
		await expect(digitalSalesRoomTemplatesPage.roomLink).toBeVisible();
		await expect(digitalSalesRoomTemplatesPage.templateLink).toBeVisible();
	}
);
