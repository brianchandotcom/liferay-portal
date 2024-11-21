/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';
import {ModalRecurrencePage} from './ModalRecurrencePage';

type RecurrenceOption = 'Entire Series' | 'Following Events' | 'Single Event';

export class CalendarWidgetPage {
	readonly addEventButton: Locator;
	readonly allDayCheckbox: Locator;
	readonly calendarWidget: Locator;
	readonly closeConfigurationButton: Locator;
	readonly configurationMenuItem: Locator;
	readonly endDate: Locator;
	readonly endTime: Locator;
	readonly invitations: Locator;
	readonly inviteResource: Locator;
	readonly modalRecurrencePage: ModalRecurrencePage;
	readonly miniCalendarBase: Locator;
	readonly miniCalendarGrid: Locator;
	readonly miniCalendarHeaderLabel: Locator;
	readonly miniCalendarNextMonthButton: Locator;
	readonly page: Page;
	readonly publishEventButton: Locator;
	readonly repeatCheckbox: Locator;
	readonly saveConfigurationButton: Locator;
	readonly startDate: Locator;
	readonly startTime: Locator;
	readonly successAlert: Locator;
	readonly timeZoneDropdown: Locator;
	readonly title: Locator;
	readonly useGlobalTimeZoneCheckBox: Locator;

	constructor(page: Page) {
		this.addEventButton = page.getByRole('button', {name: 'Add Event'});
		this.allDayCheckbox = page
			.frameLocator('iframe')
			.getByRole('checkbox', {
				exact: true,
				name: 'All Day',
			});
		this.calendarWidget = page.locator(
			'.lfr-layout-structure-item-com-liferay-calendar-web-portlet-calendarportlet'
		);
		this.closeConfigurationButton = page.getByRole('button', {
			exact: true,
			name: 'close',
		});
		this.configurationMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Configuration',
		});
		this.endDate = page
			.frameLocator('iframe')
			.getByLabel('Ends Required', {exact: true});
		this.endTime = page
			.frameLocator('iframe')
			.locator('input[type="time"]')
			.last();
		this.invitations = page
			.frameLocator('iframe')
			.getByText('Invitations', {exact: true});
		this.inviteResource = page
			.frameLocator('iframe')
			.getByTitle('Invite Resource', {exact: true});
		this.modalRecurrencePage = new ModalRecurrencePage(page);
		this.miniCalendarBase = page.locator('.yui3-calendarbase');
		this.miniCalendarGrid = page.locator('.yui3-calendar-grid');
		this.miniCalendarHeaderLabel = page.locator(
			'.yui3-calendar-header-label'
		);
		this.miniCalendarNextMonthButton = page.getByRole('button', {
			name: 'Go to next month',
		});
		this.page = page;
		this.publishEventButton = page
			.frameLocator('iframe')
			.getByRole('button', {exact: true, name: 'Publish'});
		this.repeatCheckbox = page
			.frameLocator('iframe')
			.getByRole('checkbox', {
				exact: true,
				name: 'Repeat',
			});
		this.saveConfigurationButton = page
			.frameLocator('iframe')
			.getByRole('button', {exact: true, name: 'Save'});
		this.startDate = page
			.frameLocator('iframe')
			.getByLabel('Starts Required', {exact: true});
		this.startTime = page
			.frameLocator('iframe')
			.locator('input[type="time"]')
			.first();
		this.successAlert = page
			.frameLocator('iframe')
			.locator('.alert-success', {
				hasText: 'Success:Your request completed successfully.',
			});
		this.timeZoneDropdown = page
			.frameLocator('iframe')
			.getByLabel('Time Zone', {exact: true});
		this.title = page
			.frameLocator('iframe')
			.getByLabel('Title', {exact: true});
		this.useGlobalTimeZoneCheckBox = page
			.frameLocator('iframe')
			.getByRole('checkbox', {
				exact: true,
				name: 'Use Global Time Zone',
			});
	}

	async addEvent({
		allDay,
		dateEnd,
		publishEvent,
		title,
	}: {
		allDay: boolean;
		dateEnd?: string;
		publishEvent?: boolean;
		title?: string;
	}) {
		await this.clickAddEventButton();

		await this.allDayCheckbox.hover();
		await this.allDayCheckbox.setChecked(allDay);

		if (dateEnd) {
			await this.endDate.fill(dateEnd);
		}

		if (title) {
			await this.title.fill(title);
		}

		if (publishEvent) {
			await this.publishEvent({waitForSuccessAlert: true});
		}
	}

	async addInvitation(userName: string) {
		await this.openInvitations();

		await this.inviteResource.fill(userName);

		await this.page
			.frameLocator('iframe')
			.getByRole('option', {name: userName})
			.click();
	}

	async openInvitations() {
		await this.invitations.click();
	}

	async publishEvent({
		recurrenceOption,
		waitForSuccessAlert,
	}: {
		recurrenceOption?: RecurrenceOption;
		waitForSuccessAlert?: boolean;
	} = {}) {
		await this.publishEventButton.click();

		if (recurrenceOption) {
			await this.page
				.frameLocator('iframe')
				.getByRole('button', {name: recurrenceOption})
				.click();
		}

		if (waitForSuccessAlert) {
			await waitForAlert(
				this.page.frameLocator('iframe'),
				`Success:Your request completed successfully.`
			);
		}
	}

	async closeModalEvent() {
		await this.page.getByRole('button', {name: 'Close'}).click();
	}

	async clickAddEventButton() {
		await this.addEventButton.click();

		await this.page.waitForLoadState('networkidle');
	}

	async clickEvent(title: string) {
		await this.page.getByText(title).click();
	}

	async fillEventWithRecurrenceAndAllDay(
		allDay: boolean,
		recurrence: Recurrence
	) {
		await this.clickAddEventButton();

		await this.allDayCheckbox.hover();
		await this.allDayCheckbox.setChecked(allDay);

		await this.repeatCheckbox.setChecked(true);

		await this.modalRecurrencePage.addRecurrence(recurrence);
	}

	async fillEventWithRecurrenceUntilDate({
		daysFromNow,
	}: {
		daysFromNow: number;
	}) {
		await this.clickAddEventButton();

		await this.repeatCheckbox.setChecked(true);

		await this.modalRecurrencePage.addRecurrenceUntilDate(daysFromNow);
	}

	async setCalendarWidgetConfiguration(
		timeZone: string,
		useGlobalTimeZone: boolean
	) {
		await this.calendarWidget.click();
		await this.calendarWidget.getByLabel('Options').click();

		await this.configurationMenuItem.click();

		await this.useGlobalTimeZoneCheckBox.setChecked(useGlobalTimeZone);

		if (!useGlobalTimeZone) {
			await this.timeZoneDropdown.selectOption(timeZone);
		}

		await this.saveConfigurationButton.click();
		await this.closeConfigurationButton.click();
	}
}
