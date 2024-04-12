/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';

type TNotificationTemplate = {
	editorType: string;
	id?: number;
	name: string;
	recipientType: string;
	recipients?: TRecipient[];
	subject: {
		[key: string]: string;
	};
	type: string;
};

type TRecipient = {
	from: string;
	fromName: {
		[key: string]: string;
	};
	to: {
		[key: string]: string;
	};
};

export class NotificationApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'notification/v1.0';
	}

	async deleteNotificationTemplate(notificationTemplateId: number) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/notification-templates/${notificationTemplateId}`
		);
	}

	async deleteNotificationQueueEntry(notificationQueueEntryId: number) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/notification-queue-entries/${notificationQueueEntryId}`
		);
	}

	async getNotificationQueueEntriesPage(search: string) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/notification-queue-entries?search=${search}`
		);
	}

	async postNotificationTemplate(
		notificationTemplate?: TNotificationTemplate
	): Promise<TNotificationTemplate> {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/notification-templates`,
			{data: notificationTemplate}
		);
	}
}
