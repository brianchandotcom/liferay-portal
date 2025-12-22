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
	'Go to the template page',
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

test(
	'Create a digital sales room template',
	{tag: '@LPD-75031'},
	async ({
		digitalSalesRoomTemplatesPage,
		digitalSalesRoomsPage,
		editDigitalSalesRoomTemplatePage,
	}) => {
		const name = `A${getRandomInt()}`;

		await digitalSalesRoomsPage.goto();
		await digitalSalesRoomsPage.templateLink.click();

		await digitalSalesRoomTemplatesPage.newDigitalSalesRoomTemplateButton.click();

		await editDigitalSalesRoomTemplatePage.addDigitalSalesRoomTemplate({
			banner: path.join(__dirname, '/dependencies/liferay.png'),
			name,
		});

		await digitalSalesRoomsPage.goto();
		await digitalSalesRoomsPage.templateLink.click();

		await expect(
			digitalSalesRoomTemplatesPage.digitalSalesRoomTemplatesTable.cell(
				name,
				false
			)
		).toBeVisible();
	}
);

test(
	'Delete a digital sales room template',
	{tag: '@LPD-75031'},
	async ({
		digitalSalesRoomTemplatesPage,
		digitalSalesRoomsPage,
		editDigitalSalesRoomTemplatePage,
		page,
	}) => {
		const name = `A${getRandomInt()}`;

		await digitalSalesRoomsPage.goto();
		await digitalSalesRoomsPage.templateLink.click();

		await digitalSalesRoomTemplatesPage.newDigitalSalesRoomTemplateButton.click();

		await editDigitalSalesRoomTemplatePage.addDigitalSalesRoomTemplate({
			banner: path.join(__dirname, '/dependencies/liferay.png'),
			name,
		});

		await digitalSalesRoomsPage.goto();
		await digitalSalesRoomsPage.templateLink.click();

		await expect(
			digitalSalesRoomTemplatesPage.digitalSalesRoomTemplatesTable.cell(
				name,
				false
			)
		).toBeVisible();

		await (
			await digitalSalesRoomTemplatesPage.digitalSalesRoomTemplatesTable.rowActions(
				name,
				0,
				false
			)
		).click();

		const modal = page.getByRole('alert');

		await expect(modal).toBeVisible();

		await modal.getByRole('button', {name: 'Delete'}).click();

		await waitForAlert(page);

		await expect(
			digitalSalesRoomTemplatesPage.noResultsFoundMessage
		).toBeVisible();
	}
);
