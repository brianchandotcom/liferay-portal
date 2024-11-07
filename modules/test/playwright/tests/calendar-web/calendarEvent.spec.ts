/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {calendarPagesTest} from '../../fixtures/calendarPagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../layout-content-page-editor-web/utils/getWidgetDefinition';
import {toLocalDateTimeFormatted} from './utils/toLocalDateTimeFormatted';

export const test = mergeTests(
	apiHelpersTest,
	calendarPagesTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

const recurrence = {
	frequency: 'WEEKLY',
	ocurrences: '2',
	repeatDays: ['Wednesday'],
} as Recurrence;

test.beforeEach(
	async ({apiHelpers, calendarWidgetPage, page, pageEditorPage, site}) => {
		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getWidgetDefinition({
					id: getRandomString(),
					widgetName:
						'com_liferay_calendar_web_portlet_CalendarPortlet',
				}),
			]),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await calendarWidgetPage.setCalendarWidgetConfiguration(
			'Europe/Paris',
			false
		);

		await pageEditorPage.publishPage();

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);
	}
);

test('can create all-day calendar event with different time zone', async ({
	calendarWidgetPage,
}) => {
	await calendarWidgetPage.addEvent({allDay: true, publishEvent: true});

	const endDate = await calendarWidgetPage.endDate.inputValue();
	const startDate = await calendarWidgetPage.startDate.inputValue();

	await expect(endDate).toEqual(startDate);
});

test('can create an all-day calendar event in a different time zone, ensuring that the recurrence link remains consistent', async ({
	calendarWidgetPage,
}) => {
	await calendarWidgetPage.fillEventWithRecurrenceAndAllDay(true, recurrence);

	const {frequency, ocurrences, repeatDays} = recurrence;

	const expectedLink =
		frequency +
		', on ' +
		repeatDays.join(',') +
		', ' +
		ocurrences +
		' Times';

	await expect(
		calendarWidgetPage.page.frameLocator('iframe').getByRole('link', {
			name: expectedLink,
		})
	).toBeVisible();

	await calendarWidgetPage.publishEvent({waitForSuccessAlert: true});

	await expect(
		calendarWidgetPage.page.frameLocator('iframe').getByRole('link', {
			name: expectedLink,
		})
	).toBeVisible();
});

test('can create calendar event different start/end dates ensuring that the end date is displayed', async ({
	calendarWidgetPage,
	page,
}) => {
	const startDate = new Date();

	const endDate = new Date(startDate);
	endDate.setDate(endDate.getDate() + 1);

	const endDateFormatted = toLocalDateTimeFormatted(endDate.toUTCString(), {
		day: '2-digit',
		hour: '2-digit',
		month: '2-digit',
		timeZone: 'UTC',
		year: 'numeric',
	} as const);

	const title = getRandomInt().toString();

	await calendarWidgetPage.addEvent({
		allDay: false,
		dateEnd: endDateFormatted,
		publishEvent: true,
		title,
	});

	await calendarWidgetPage.closeModalEvent();

	await calendarWidgetPage.clickEvent(title);

	await expect(page.locator('.scheduler-event-recorder-date')).toHaveText(
		new RegExp(
			toLocalDateTimeFormatted(endDate.toUTCString(), {
				day: '2-digit',
				month: 'long',
				timeZone: 'UTC',
				weekday: 'short',
			} as const)
		)
	);
});

test('can create calendar event with invitation', async ({
	apiHelpers,
	calendarWidgetPage,
	page,
}) => {
	const user1 = await apiHelpers.headlessAdminUser.postUserAccount();
	const user2 = await apiHelpers.headlessAdminUser.postUserAccount();

	try {

		// Access the calendar widget page as new user to create user calendar resources

		const currentURL = page.url();

		await page.goto(`${currentURL}?doAsUserId=${user1.id}`);
		await page.goto(`${currentURL}?doAsUserId=${user2.id}`);

		// As user 2, add an event and send an invitation to user 1

		await calendarWidgetPage.addEvent({allDay: true, publishEvent: false});

		await calendarWidgetPage.addInvitation(user1.name);

		await calendarWidgetPage.publishEvent();

		await calendarWidgetPage.openInvitations();

		await expect(
			calendarWidgetPage.page
				.frameLocator('iframe')
				.getByText('Pending (1)')
		).toBeVisible();
		await expect(
			calendarWidgetPage.page.frameLocator('iframe').getByText(user1.name)
		).toBeVisible();
	}
	finally {
		await apiHelpers.headlessAdminUser.deleteUserAccount(Number(user1.id));
		await apiHelpers.headlessAdminUser.deleteUserAccount(Number(user2.id));
	}
});

test('can see calendar event inputs alerts', async ({
	calendarWidgetPage,
	page,
}) => {
	await calendarWidgetPage.clickAddEventButton();

	await calendarWidgetPage.startDate.fill('');

	await calendarWidgetPage.startDate.blur();

	await expect(
		page
			.frameLocator('iframe')
			.getByText(
				'This field will be automatically filled if it is empty or incomplete.'
			)
	).toBeVisible();

	await calendarWidgetPage.startDate.fill('abc');

	await calendarWidgetPage.startDate.blur();

	await expect(
		page.frameLocator('iframe').getByText('Please enter a valid date.')
	).toBeVisible();

	await calendarWidgetPage.startDate.fill('10/10/2010');

	await calendarWidgetPage.startDate.blur();

	await expect(
		page.frameLocator('iframe').getByText('Please enter a valid date.')
	).toBeHidden();

	await expect(
		page
			.frameLocator('iframe')
			.getByText(
				'This field will be automatically filled if it is empty or incomplete.'
			)
	).toBeHidden();

	await calendarWidgetPage.startTime.focus();

	await page.keyboard.press('Backspace');

	await calendarWidgetPage.startTime.blur();

	await expect(
		page.frameLocator('iframe').getByText('Please enter a valid time')
	).toBeVisible();

	await calendarWidgetPage.startTime.evaluate((startTime) => {
		(startTime as HTMLInputElement).value = '';
	});

	await calendarWidgetPage.startTime.focus();

	await calendarWidgetPage.startTime.blur();

	await expect(
		page
			.frameLocator('iframe')
			.getByText(
				'This field will be automatically filled if it is empty or incomplete.'
			)
	).toBeVisible();

	await calendarWidgetPage.startTime.evaluate((startTime) => {
		(startTime as HTMLInputElement).value = '14:30';
	});

	await calendarWidgetPage.startTime.focus();

	await calendarWidgetPage.startTime.blur();

	await expect(
		page.frameLocator('iframe').getByText('Please enter a valid time.')
	).toBeHidden();

	await expect(
		page
			.frameLocator('iframe')
			.getByText(
				'This field will be automatically filled if it is empty or incomplete.'
			)
	).toBeHidden();
});

test('can update an event with recurrence', async ({
	calendarWidgetPage,
	modalRecurrencePage,
}) => {
	await calendarWidgetPage.fillEventWithRecurrenceUntilDate({daysFromNow: 5});

	await expect(modalRecurrencePage.inputDate).toBeEnabled();

	await modalRecurrencePage.doneButton.click();

	await calendarWidgetPage.publishEvent();

	await expect(calendarWidgetPage.successAlert).toBeVisible();

	await calendarWidgetPage.publishEvent({
		recurrenceOption: 'Following Events',
	});

	await expect(calendarWidgetPage.successAlert).toBeVisible();

	await calendarWidgetPage.publishEvent({recurrenceOption: 'Entire Series'});

	await expect(calendarWidgetPage.successAlert).toBeVisible();

	await calendarWidgetPage.repeatCheckbox.setChecked(false);

	await calendarWidgetPage.repeatCheckbox.setChecked(true);

	await modalRecurrencePage.doneButton.nth(1).click();

	await calendarWidgetPage.publishEvent({recurrenceOption: 'Single Event'});

	await expect(calendarWidgetPage.successAlert).toBeVisible();
});
