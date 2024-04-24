/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {getRandomInt} from '../../utils/getRandomInt';
import {ActionPage} from './ActionPage';
import {NotificationSectionPage} from './NotificationSectionPage';
import {TimerPage} from './TimerPage';

export class NodePropertiesSidebarPage {
	readonly actionPage: ActionPage;
	readonly addActionButton: Locator;
	readonly addNotificationButton: Locator;
	readonly addTimerButton: Locator;
	readonly deleteNotificationsButton: Locator;
	readonly notificationPage: NotificationSectionPage;
	readonly page: Page;
	readonly timerPage: TimerPage;

	constructor(page: Page) {
		this.addActionButton = page
			.getByRole('tablist')
			.filter({hasText: 'Actions'})
			.getByRole('button', {name: 'New'})
			.first();
		this.addNotificationButton = page
			.getByRole('tablist')
			.filter({hasText: 'Notifications'})
			.getByRole('button', {name: 'New'})
			.first();
		this.addTimerButton = page
			.getByRole('tablist')
			.filter({hasText: 'Timers'})
			.getByRole('button', {name: 'New'})
			.first();
		this.deleteNotificationsButton = page.locator(
			'button[title="Delete Notifications"]'
		);
		this.notificationPage = new NotificationSectionPage(page, 0);
		this.page = page;
		this.timerPage = new TimerPage(page);
		this.actionPage = new ActionPage(page);
	}

	async createTimerAction(name: string, script: string, typeOption: string) {
		await this.addTimerButton.click();

		await this.timerPage.fillTimerFields(
			'timerDescription' + getRandomInt(),
			'3',
			'timerName' + getRandomInt(),
			'week'
		);

		await this.actionPage.fillWorkflowAction(name, script, typeOption);
	}

	async createNotification(notification: Notification) {
		await this.addNotificationButton.click();
		await this.notificationPage.fillNotificationSectionFields(
			false,
			notification
		);
	}

	async createTimerNotification(notifications: Notification[]) {
		await this.addTimerButton.click();
		await this.timerPage.fillTimerFields(
			'timerDescription' + getRandomInt(),
			'3',
			'timerName' + getRandomInt(),
			'week'
		);

		for (let i = 0; i < notifications.length; i++) {
			await this.timerPage.fillTimerActionNotificationFields(
				i,
				notifications[i]
			);

			if (i === notifications.length - 1) {
				return;
			}
			await this.timerPage.addNewAction(i);
		}
	}

	async createTimerReassignmentRoleType(roleTypes: RoleType[]) {
		await this.addTimerButton.click();
		await this.timerPage.fillTimerFields(
			'timerDescription' + getRandomInt(),
			'3',
			'timerName' + getRandomInt(),
			'week'
		);

		await this.timerPage.fillTimerActionReassignmentRoleType(roleTypes);
	}

	async deleteNotifications() {
		await this.deleteNotificationsButton.click();
	}
}
