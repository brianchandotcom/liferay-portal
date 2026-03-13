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

		await expect(digitalSalesRoomsPage.roomLink(roomName)).toHaveAttribute(
			'href',
			/view_room/
		);
	}
);

test(
	'View a digital sales room',
	{tag: '@LPD-69528'},
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
			await expect(digitalSalesRoomsPage.viewMenuItem).toBeVisible({
				timeout: 200,
			});
		}).toPass({timeout: 1000});

		await digitalSalesRoomsPage.viewMenuItem.click();

		await expect(page.locator('.page-editor__sidebar')).not.toBeVisible();
	}
);

test(
	'Edit a digital sales room',
	{tag: '@LPD-69528'},
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
			await expect(digitalSalesRoomsPage.viewMenuItem).toBeVisible({
				timeout: 200,
			});
		}).toPass({timeout: 1000});

		await digitalSalesRoomsPage.editMenuItem.click();

		await expect(page.locator('.page-editor__sidebar')).toBeVisible();
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

test(
	'Invite external user and verify pending status then remove user',
	{tag: '@LPD-66359'},
	async ({
		apiHelpers,
		digitalSalesRoomUsersPage,
		digitalSalesRoomsPage,
		editDigitalSalesRoomPage,
		page,
	}) => {
		const accountName = `B${getRandomInt()}`;
		const email = `invited-${getRandomInt()}@liferay.com`;
		const roomName = `A${getRandomInt()}`;

		await apiHelpers.headlessAdminUser.postAccount({
			name: accountName,
			type: 'business',
		});

		await digitalSalesRoomsPage.goto();

		await expect(
			digitalSalesRoomsPage.digitalSalesRoomsTable.searchInput
		).toBeVisible();

		await digitalSalesRoomsPage.digitalSalesRoomsTable.newButton.click();

		await editDigitalSalesRoomPage.addDigitalSalesRoom({
			accountName,
			roomName,
		});

		await digitalSalesRoomsPage.goto();

		await expect(
			digitalSalesRoomsPage.digitalSalesRoomsTable.cell(roomName, false)
		).toBeVisible();

		await expect(async () => {
			await (
				await digitalSalesRoomsPage.digitalSalesRoomsTable.rowActions(
					roomName,
					0,
					false
				)
			).click();

			await expect(digitalSalesRoomsPage.shareMenuItem).toBeVisible({
				timeout: 200,
			});
		}).toPass({timeout: 1000});

		await digitalSalesRoomsPage.shareMenuItem.click();

		await expect(
			digitalSalesRoomUsersPage.userEmailAddressesInput
		).toBeVisible();

		await digitalSalesRoomUsersPage.userEmailAddressesInput.fill(email);
		await digitalSalesRoomUsersPage.userEmailAddressesInput.press('Enter');

		await digitalSalesRoomUsersPage.inviteButton.click();

		await waitForAlert(page, 'Success:User was invited successfully.');

		await expect(digitalSalesRoomUsersPage.userRow(email)).toBeVisible();
		await expect(
			digitalSalesRoomUsersPage.roleText(email, 'Viewer')
		).toBeVisible();

		await digitalSalesRoomUsersPage.removeUserButton(email).click();

		await expect(digitalSalesRoomUsersPage.removeUserModal).toBeVisible();

		await digitalSalesRoomUsersPage.removeUserModalRemoveButton.click();

		await waitForAlert(page, 'Success:User was removed successfully.');

		await expect(
			digitalSalesRoomUsersPage.userRow(email)
		).not.toBeVisible();
	}
);

test(
	'Share room via header share button',
	{tag: '@LPD-66359'},
	async ({
		apiHelpers,
		digitalSalesRoomUsersPage,
		digitalSalesRoomsPage,
		editDigitalSalesRoomPage,
		page,
	}) => {
		const accountName = `B${getRandomInt()}`;
		const roomName = `A${getRandomInt()}`;

		await apiHelpers.headlessAdminUser.postAccount({
			name: accountName,
			type: 'business',
		});

		await digitalSalesRoomsPage.goto();

		await expect(
			digitalSalesRoomsPage.digitalSalesRoomsTable.searchInput
		).toBeVisible();

		await digitalSalesRoomsPage.digitalSalesRoomsTable.newButton.click();

		await editDigitalSalesRoomPage.addDigitalSalesRoom({
			accountName,
			roomName,
		});

		await page.getByRole('button', {name: 'Publish'}).click();

		await expect(digitalSalesRoomUsersPage.shareButton).toBeVisible();

		await digitalSalesRoomUsersPage.shareButton.click();

		await expect(digitalSalesRoomUsersPage.shareModalHeading).toBeVisible();

		await expect(
			digitalSalesRoomUsersPage.shareModalEmailInput
		).toBeVisible();
	}
);
