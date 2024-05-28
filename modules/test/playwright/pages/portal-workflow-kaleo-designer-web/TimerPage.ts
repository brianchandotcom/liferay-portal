/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ActionReassignmentPage} from './ActionReassignmentPage';
import {NotificationSectionPage} from './NotificationSectionPage';

export class TimerPage {
	actionReassignmentPage: ActionReassignmentPage;
	addActionButton: Locator;
	notificationSectionPage: NotificationSectionPage;
	readonly inputTimerDescription: Locator;
	readonly inputTimerDuration: Locator;
	readonly inputTimerName: Locator;
	readonly inputTimerRecurrence: Locator;
	readonly inputTimerScale: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.inputTimerDescription = page.locator('#timerDescription');
		this.inputTimerDuration = page
			.locator('div')
			.filter({hasText: /^Duration\*$/})
			.getByRole('spinbutton');
		this.inputTimerName = page.locator('#timerName');
		this.inputTimerRecurrence = page.getByLabel('Recurrence');
		this.inputTimerScale = page.locator('#scale');
		this.page = page;
	}

	async addNewAction(index: number) {
		this.addActionButton = this.page
			.getByRole('button', {name: 'New Action'})
			.nth(index);

		await this.addActionButton.click();
	}

	async assertTimerActionNotificationFields(notifications: Notification[]) {
		for (let index = 0; index < notifications.length; index++) {
			this.notificationSectionPage = new NotificationSectionPage(
				this.page,
				index
			);

			await this.notificationSectionPage.assertNotificationSectionFields(
				index,
				notifications[index]
			);
		}
	}

	async fillTimerActionNotificationFields(
		index: number,
		notification: Notification
	) {
		this.notificationSectionPage = new NotificationSectionPage(
			this.page,
			index
		);

		await this.notificationSectionPage.fillNotificationSectionFields(
			true,
			notification
		);
	}

	async fillTimerFields(
		description: string,
		duration: string,
		name: string,
		scale: string
	) {
		await this.inputTimerDescription.fill(description);
		await this.inputTimerDuration.fill(duration);
		await this.inputTimerName.fill(name);
		await this.inputTimerRecurrence.uncheck();
		await this.inputTimerScale.selectOption(scale);
	}

	async fillTimerActionReassignmentRoleType(roleTypes: RoleType[]) {
		this.actionReassignmentPage = new ActionReassignmentPage(this.page);

		await this.actionReassignmentPage.fillRoleTypeReassignmentType(
			roleTypes
		);
	}
}
