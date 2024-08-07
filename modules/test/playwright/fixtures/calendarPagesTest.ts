/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {CalendarWidgetPage} from '../pages/calendar-web/CalendarWidgetPage';

const calendarPagesTest = test.extend<{
	calendarWidgetPage: CalendarWidgetPage;
}>({
	calendarWidgetPage: async ({page}, use) => {
		await use(new CalendarWidgetPage(page));
	},
});

export {calendarPagesTest};
