/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {getRandomInt} from '../../utils/getRandomInt';
import {TimerPage} from './TimerPage';

export class NodePropertiesSidebarPage {
	readonly addTimerButton: Locator;
	readonly timerPage: TimerPage;

	constructor(page: Page) {
		this.addTimerButton = page
			.getByRole('tablist')
			.filter({hasText: 'Timers'})
			.getByRole('button', {name: 'New'})
			.first();
		this.timerPage = new TimerPage(page);
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
}
