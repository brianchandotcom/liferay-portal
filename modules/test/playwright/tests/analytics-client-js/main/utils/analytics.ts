/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {liferayConfig} from '../../../../liferay.config';

const ENDPOINT_PREFIX = `${liferayConfig.environment.baseUrl}/o/mock/osb-asah-publisher`;

export type Identity = {
	channelId: string;
	dataSourceId: string;
	emailAddressHashed: string;
	userId: string;
};

export type Event = {
	applicationId: string;
	contextHash: string;
	eventDate: string;
	eventId: string;
	eventLocalDate: string;
	properties: {
		[key in string]: string | number | Date | boolean;
	};
};

export type EventBucket = {
	channelId: string;
	context: object;
	dataSourceId: string;
	emailAddressHashed: string;
	events: Event[];
	id: string;
	userId: string;
};

export class Analytics {
	readonly endpoints = {
		events: ENDPOINT_PREFIX,
		identity: `${ENDPOINT_PREFIX}/identity`,
	};
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
	}

	async cleanup() {
		await this.page.evaluate(() => {
			window.localStorage.clear();
		});

		await this.page.waitForTimeout(1000);
	}

	async getIdentity(): Promise<Identity | null> {
		let result: Identity | null = null;

		this.page.on('response', async (response) => {
			if (response.url() === this.endpoints.identity) {
				try {
					const request = response.request();

					const data = request.postData();

					const responseStatus = response.status();

					if (responseStatus !== 204) {
						throw new Error(`response status is ${responseStatus}`);
					}

					result = data ? JSON.parse(data) : null;

					console.error(`Identity: ${data}`);
				}
				catch (error) {
					console.error(`Identity Error ${error}`);
				}
			}
		});

		await this.page.waitForTimeout(5000);

		return result;
	}

	async getEvents(eventId?: string): Promise<EventBucket | Event | null> {
		return new Promise<EventBucket | Event | null>((resolve) => {
			let result: EventBucket | Event | null = null;
			let isEventFound = false;

			const startTime = Date.now();

			const handleResponse = async (response) => {
				if (response.url() === this.endpoints.events) {
					try {
						const request = response.request();
						const data = request.postData();
						const responseStatus = response.status();

						if (responseStatus !== 200) {
							throw new Error(
								`response status is ${responseStatus}`
							);
						}

						const eventBucket: EventBucket | null = data
							? JSON.parse(data)
							: null;

						if (eventId) {
							result =
								eventBucket?.events.find(
									({eventId: curEventId}) =>
										eventId === curEventId
								) ?? null;

							if (result) {
								isEventFound = true;

								console.error(
									`Event by eventId ${eventId}: ${JSON.stringify(result)}`
								);

								resolve(result);
							}
						}
						else {
							isEventFound = true;
							result = eventBucket;

							console.error(`Events ${data}`);

							resolve(result);
						}
					}
					catch (error) {
						console.error(`Events Error ${error}`);

						resolve(null);
					}
				}
			};

			this.page.on('response', handleResponse);

			(async () => {
				while (!isEventFound && Date.now() - startTime < 10000) {
					await this.page.waitForTimeout(100);
				}

				if (!isEventFound) {
					resolve(null);
				}
			})();
		});
	}
}
