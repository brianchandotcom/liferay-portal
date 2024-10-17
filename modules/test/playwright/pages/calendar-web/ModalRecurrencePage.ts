/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';
import moment from 'moment';

export class ModalRecurrencePage {
	readonly afterRadio: Locator;
	readonly countTextbox: Locator;
	readonly doneButton: Locator;
	readonly inputDate: Locator;
	readonly onRadio: Locator;
	readonly page: Page;
	readonly repeatSelect: Locator;

	constructor(page: Page) {
		this.afterRadio = page
			.frameLocator('iframe')
			.locator('input[type="radio"][value="after"]');
		this.countTextbox = page
			.frameLocator('iframe')
			.getByRole('textbox', {name: 'Count'});
		this.doneButton = page
			.frameLocator('iframe')
			.getByRole('button', {name: 'Done'});
		this.inputDate = page
			.frameLocator('iframe')
			.getByRole('textbox', {name: 'mm/dd/yyyy'});
		this.onRadio = page
			.frameLocator('iframe')
			.locator('input[type="radio"][value="on"]');
		this.page = page;
		this.repeatSelect = page
			.frameLocator('iframe')
			.locator('select[title="frequency"]');
	}

	async addRecurrence(recurrence: Recurrence) {
		const {frequency, ocurrences, repeatDays} = recurrence;

		await this.repeatSelect.selectOption(frequency);

		await this.selectRepeatDays(repeatDays);

		await this.afterRadio.check();

		await this.countTextbox.fill(ocurrences);

		await this.doneButton.click();
	}

	async addRecurrenceUntilDate(daysFromNow: number) {
		const nowDate = new Date();

		const untilDate = new Date(
			nowDate.getFullYear(),
			nowDate.getMonth(),
			nowDate.getDate() + daysFromNow
		);

		const formatUntilDate = moment(untilDate).format('MM/DD/YYYY');

		await this.onRadio.check();

		await this.inputDate.click();

		await this.inputDate.fill(formatUntilDate);
	}

	async handleDayCheckboxesSelection(
		repeatDays: DaysOfTheWeek[],
		checked: boolean
	) {
		for (const day of repeatDays) {
			await this.page
				.frameLocator('iframe')
				.getByRole('checkbox', {
					exact: true,
					name: day,
				})
				.setChecked(checked);
		}
	}

	async selectRepeatDays(repeatDays: DaysOfTheWeek[]) {
		await this.handleDayCheckboxesSelection(repeatDays, true);
	}

	async unselectRepeatDays(repeatDays: DaysOfTheWeek[]) {
		await this.handleDayCheckboxesSelection(repeatDays, false);
	}
}
