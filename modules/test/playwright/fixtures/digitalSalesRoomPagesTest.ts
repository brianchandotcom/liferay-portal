/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {DigitalSalesRoomUsersPage} from '../pages/site-dsr-site-initializer/DigitalSalesRoomUsersPage';
import {DigitalSalesRoomsPage} from '../pages/site-dsr-site-initializer/DigitalSalesRoomsPage';
import {EditDigitalSalesRoomPage} from '../pages/site-dsr-site-initializer/EditDigitalSalesRoomPage';

const digitalSalesRoomPagesTest = test.extend<{
	digitalSalesRoomUsersPage: DigitalSalesRoomUsersPage;
	digitalSalesRoomsPage: DigitalSalesRoomsPage;
	editDigitalSalesRoomPage: EditDigitalSalesRoomPage;
}>({
	digitalSalesRoomUsersPage: async ({page}, use) => {
		await use(new DigitalSalesRoomUsersPage(page));
	},
	digitalSalesRoomsPage: async ({page}, use) => {
		await use(new DigitalSalesRoomsPage(page));
	},
	editDigitalSalesRoomPage: async ({page}, use) => {
		await use(new EditDigitalSalesRoomPage(page));
	},
});

export {digitalSalesRoomPagesTest};
