/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {DigitalSalesRoomSaveAsTemplatePage} from '../pages/digital-sales-room-web/DigitalSalesRoomSaveAsTemplatePage';
import {DigitalSalesRoomSettingsPage} from '../pages/digital-sales-room-web/DigitalSalesRoomSettingsPage';
import {DigitalSalesRoomTemplateSettingsPage} from '../pages/digital-sales-room-web/DigitalSalesRoomTemplateSettingsPage';
import {DigitalSalesRoomTemplatesPage} from '../pages/digital-sales-room-web/DigitalSalesRoomTemplatesPage';
import {DigitalSalesRoomsPage} from '../pages/digital-sales-room-web/DigitalSalesRoomsPage';
import {EditDigitalSalesRoomPage} from '../pages/digital-sales-room-web/EditDigitalSalesRoomPage';
import {EditDigitalSalesRoomTemplatePage} from '../pages/digital-sales-room-web/EditDigitalSalesRoomTemplatePage';

const digitalSalesRoomPagesTest = test.extend<{
	digitalSalesRoomSaveAsTemplatePage: DigitalSalesRoomSaveAsTemplatePage;
	digitalSalesRoomSettingsPage: DigitalSalesRoomSettingsPage;
	digitalSalesRoomTemplateSettingsPage: DigitalSalesRoomTemplateSettingsPage;
	digitalSalesRoomTemplatesPage: DigitalSalesRoomTemplatesPage;
	digitalSalesRoomsPage: DigitalSalesRoomsPage;
	editDigitalSalesRoomPage: EditDigitalSalesRoomPage;
	editDigitalSalesRoomTemplatePage: EditDigitalSalesRoomTemplatePage;
}>({
	digitalSalesRoomSaveAsTemplatePage: async ({page}, use) => {
		await use(new DigitalSalesRoomSaveAsTemplatePage(page));
	},
	digitalSalesRoomSettingsPage: async ({page}, use) => {
		await use(new DigitalSalesRoomSettingsPage(page));
	},
	digitalSalesRoomTemplateSettingsPage: async ({page}, use) => {
		await use(new DigitalSalesRoomTemplateSettingsPage(page));
	},
	digitalSalesRoomTemplatesPage: async ({page}, use) => {
		await use(new DigitalSalesRoomTemplatesPage(page));
	},
	digitalSalesRoomsPage: async ({page}, use) => {
		await use(new DigitalSalesRoomsPage(page));
	},
	editDigitalSalesRoomPage: async ({page}, use) => {
		await use(new EditDigitalSalesRoomPage(page));
	},
	editDigitalSalesRoomTemplatePage: async ({page}, use) => {
		await use(new EditDigitalSalesRoomTemplatePage(page));
	},
});

export {digitalSalesRoomPagesTest};
