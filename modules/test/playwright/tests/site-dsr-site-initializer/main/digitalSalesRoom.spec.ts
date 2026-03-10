/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {digitalSalesRoomPagesTest} from '../../../fixtures/digitalSalesRoomPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import {waitForAlert} from '../../../utils/waitForAlert';

export const test = mergeTests(
	dataApiHelpersTest,
	digitalSalesRoomPagesTest,
	featureFlagsTest({
		'LPD-35443': {enabled: true},
		'LPD-66359': {enabled: true},
	}),
	loginTest()
);

test.afterEach(async ({apiHelpers}) => {
	const rooms = await apiHelpers.headlessDigitalSalesRoom.getRooms();

	for (const room of rooms.items) {
		await apiHelpers.headlessDigitalSalesRoom.deleteRoom(room.id);
	}
});

test(
	'Create a digital sales room',
	{tag: '@LPD-69509'},
	async ({apiHelpers, digitalSalesRoomsPage, editDigitalSalesRoomPage}) => {
		const account = await apiHelpers.headlessAdminUser.postAccount({
			type: 'business',
		});

		const roomName = `A${getRandomInt()}`;

		await digitalSalesRoomsPage.goto();

		await expect(
			digitalSalesRoomsPage.digitalSalesRoomsTable.searchInput
		).toBeVisible();

		await digitalSalesRoomsPage.digitalSalesRoomsTable.newButton.click();

		await editDigitalSalesRoomPage.addDigitalSalesRoom({
			accountName: account.name,
			roomName,
		});

		await digitalSalesRoomsPage.goto();

		await expect(
			digitalSalesRoomsPage.digitalSalesRoomsTable.cell(roomName, false)
		).toBeVisible();
	}
);

test(
	'Delete a digital sales room',
	{tag: '@LPD-73577'},
	async ({
		apiHelpers,
		digitalSalesRoomsPage,
		editDigitalSalesRoomPage,
		page,
	}) => {
		const account = await apiHelpers.headlessAdminUser.postAccount({
			type: 'business',
		});

		const roomName = `A${getRandomInt()}`;

		await digitalSalesRoomsPage.goto();

		await expect(
			digitalSalesRoomsPage.digitalSalesRoomsTable.searchInput
		).toBeVisible();

		await digitalSalesRoomsPage.digitalSalesRoomsTable.newButton.click();

		await editDigitalSalesRoomPage.addDigitalSalesRoom({
			accountName: account.name,
			roomName,
		});

		await digitalSalesRoomsPage.goto();

		await expect(async () => {
			await (
				await digitalSalesRoomsPage.digitalSalesRoomsTable.rowActions(
					roomName,
					0,
					false
				)
			).click();
			await expect(digitalSalesRoomsPage.deleteMenuItem).toBeVisible({
				timeout: 200,
			});
		}).toPass({timeout: 1000});

		await digitalSalesRoomsPage.deleteMenuItem.click();

		await expect(
			digitalSalesRoomsPage.deleteConfirmationModal
		).toBeVisible();

		await digitalSalesRoomsPage.deleteButton.click();

		await waitForAlert(page);

		await expect(digitalSalesRoomsPage.noResultsFoundMessage).toBeVisible();
	}
);
