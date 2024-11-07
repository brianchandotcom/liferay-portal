/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {CalendarWidgetPage} from '../pages/calendar-web/CalendarWidgetPage';
import {ModalRecurrencePage} from '../pages/calendar-web/ModalRecurrencePage';

const calendarPagesTest = test.extend<{
	calendarWidgetPage: CalendarWidgetPage;
	modalRecurrencePage: ModalRecurrencePage;
}>({
	calendarWidgetPage: async ({page}, use) => {
		await use(new CalendarWidgetPage(page));
	},
	modalRecurrencePage: async ({page}, use) => {
		await use(new ModalRecurrencePage(page));
	},
});

export {calendarPagesTest};
