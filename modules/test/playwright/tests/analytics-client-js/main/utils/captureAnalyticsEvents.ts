/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

export type CapturedEvent = {
	applicationId: string;
	channelId: string;
	context: Record<string, unknown>;
	dataSourceId: string;
	eventId: string;
	properties: Record<string, unknown>;
};

export function captureAnalyticsEvents(page: Page): CapturedEvent[] {
	const capturedEvents: CapturedEvent[] = [];

	page.on('request', (request) => {
		if (request.method() !== 'POST') {
			return;
		}

		const postData = request.postData();

		if (!postData || !postData.includes('"eventId":')) {
			return;
		}

		try {
			const eventBucket = JSON.parse(postData);

			if (!eventBucket.events) {
				return;
			}

			for (const event of eventBucket.events) {
				capturedEvents.push({
					applicationId: event.applicationId,
					channelId: eventBucket.channelId,
					context: eventBucket.context ?? {},
					dataSourceId: eventBucket.dataSourceId,
					eventId: event.eventId,
					properties: event.properties ?? {},
				});
			}
		}
		catch {

			// Ignore non-JSON bodies; only analytics POSTs are valid here.

		}
	});

	return capturedEvents;
}
