/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import path from 'path';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {digitalSalesRoomPagesTest} from '../../../fixtures/digitalSalesRoomPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {getRandomInt} from '../../../utils/getRandomInt';

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
});

test(
	'Create a digital sales room',
	{tag: '@LPD-69509'},
	async ({digitalSalesRoomsPage, editDigitalSalesRoomPage}) => {
		const roomName = `A${getRandomInt()}`;

		await digitalSalesRoomsPage.goto();

		await expect(
			digitalSalesRoomsPage.digitalSalesRoomsTable.searchInput
		).toBeVisible();

		await digitalSalesRoomsPage.digitalSalesRoomsTable.newButton.click();

		await editDigitalSalesRoomPage.addDigitalSalesRoom({
			banner: path.join(__dirname, '/dependencies/liferay.png'),
			roomName,
		});

		await digitalSalesRoomsPage.goto();

		await expect(
			digitalSalesRoomsPage.digitalSalesRoomsTable.cell(roomName)
		).toBeVisible();
	}
);

test(
	'Create a digital sales room with account and channel',
	{tag: '@LPD-73190'},
	async ({apiHelpers, digitalSalesRoomsPage, editDigitalSalesRoomPage}) => {
		const roomName = `A${getRandomInt()}`;

		const account = await apiHelpers.headlessAdminUser.postAccount({
			type: 'business',
		});
		const channel =
			await apiHelpers.headlessCommerceAdminChannel.postChannel({});

		await digitalSalesRoomsPage.goto();

		await expect(
			digitalSalesRoomsPage.digitalSalesRoomsTable.searchInput
		).toBeVisible();

		await digitalSalesRoomsPage.digitalSalesRoomsTable.newButton.click();

		await editDigitalSalesRoomPage.addDigitalSalesRoom({
			accountName: account.name,
			channelName: channel.name,
			roomName,
		});

		await digitalSalesRoomsPage.goto();

		await expect(
			digitalSalesRoomsPage.digitalSalesRoomsTable.cell(channel.name)
		).toBeVisible();
		await expect(
			digitalSalesRoomsPage.digitalSalesRoomsTable.cell(roomName)
		).toBeVisible();
	}
);
