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
import {waitForAlert} from '../../../utils/waitForAlert';

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

test(
	'Create a digital sales room with users',
	{tag: '@LPD-69528'},
	async ({apiHelpers, digitalSalesRoomsPage, editDigitalSalesRoomPage}) => {
		const roomName = `A${getRandomInt()}`;

		const user1 = await apiHelpers.headlessAdminUser.postUserAccount();
		const user2 = await apiHelpers.headlessAdminUser.postUserAccount();

		await digitalSalesRoomsPage.goto();

		await expect(
			digitalSalesRoomsPage.digitalSalesRoomsTable.searchInput
		).toBeVisible();

		await digitalSalesRoomsPage.digitalSalesRoomsTable.newButton.click();

		await editDigitalSalesRoomPage.addDigitalSalesRoom({
			roomName,
			usersEmailAddresses: [user1.emailAddress, user2.emailAddress],
		});

		await digitalSalesRoomsPage.goto();

		await expect(
			digitalSalesRoomsPage.digitalSalesRoomsTable.cell(roomName)
		).toBeVisible();

		const digitalSalesRoom = (
			await apiHelpers.headlessDigitalSalesRoom.getDigitalSalesRooms()
		).items.pop();

		expect(digitalSalesRoom.userAccountBriefs.length).toEqual(3);
	}
);

test(
	'Delete a digital sales room',
	{tag: '@LPD-73577'},
	async ({digitalSalesRoomsPage, editDigitalSalesRoomPage, page}) => {
		const roomName = `A${getRandomInt()}`;

		await digitalSalesRoomsPage.goto();
		await digitalSalesRoomsPage.digitalSalesRoomsTable.newButton.click();

		await editDigitalSalesRoomPage.addDigitalSalesRoom({
			banner: path.join(__dirname, '/dependencies/liferay.png'),
			roomName,
		});

		await digitalSalesRoomsPage.goto();

		await expect(
			digitalSalesRoomsPage.digitalSalesRoomsTable.cell(roomName)
		).toBeVisible();

		await expect(async () => {
			await (
				await digitalSalesRoomsPage.digitalSalesRoomsTable.rowActions(
					roomName,
					0
				)
			).click();
			await expect(digitalSalesRoomsPage.deleteMenuItem).toBeVisible({
				timeout: 200,
			});
		}).toPass({timeout: 1000});

		await digitalSalesRoomsPage.deleteMenuItem.click();

		const modal = page.getByRole('alert');

		await expect(modal).toBeVisible();

		await modal.getByRole('button', {name: 'Delete'}).click();

		await waitForAlert(page);

		await expect(digitalSalesRoomsPage.noResultsFoundMessage).toBeVisible();
	}
);

test(
	'Save a digital sales room as template',
	{tag: '@LPD-73189'},
	async ({
		digitalSalesRoomSaveAsTemplatePage,
		digitalSalesRoomTemplatesPage,
		digitalSalesRoomsPage,
		editDigitalSalesRoomPage,
	}) => {
		const roomName = `A${getRandomInt()}`;

		await digitalSalesRoomsPage.goto();
		await digitalSalesRoomsPage.digitalSalesRoomsTable.newButton.click();

		await editDigitalSalesRoomPage.addDigitalSalesRoom({
			banner: path.join(__dirname, '/dependencies/liferay.png'),
			roomName,
		});

		await digitalSalesRoomsPage.goto();

		await expect(
			digitalSalesRoomsPage.digitalSalesRoomsTable.cell(roomName)
		).toBeVisible();

		await expect(async () => {
			await (
				await digitalSalesRoomsPage.digitalSalesRoomsTable.rowActions(
					roomName,
					0
				)
			).click();
			await expect(
				digitalSalesRoomsPage.saveAsTemplateMenuItem
			).toBeVisible({timeout: 200});
		}).toPass({timeout: 1000});

		await digitalSalesRoomsPage.saveAsTemplateMenuItem.click();

		const template = {
			description: `Description ${getRandomInt()}`,
			roomName: `T${getRandomInt()}`,
		};

		await digitalSalesRoomSaveAsTemplatePage.saveAsTemplate(template);

		await digitalSalesRoomTemplatesPage.goto();

		await expect(
			digitalSalesRoomTemplatesPage.digitalSalesRoomTemplatesTable.cell(
				template.roomName,
				false
			)
		).toBeVisible();
		await expect(
			digitalSalesRoomTemplatesPage.digitalSalesRoomTemplatesTable.cell(
				template.description,
				false
			)
		).toBeVisible();
	}
);
