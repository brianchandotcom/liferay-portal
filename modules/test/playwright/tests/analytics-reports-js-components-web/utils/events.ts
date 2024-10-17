import {Page} from '@playwright/test';

import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {IndividualIdentity, RangeSelectors} from '../types';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export async function createBlogsEventsForEveryDayByRangeSelector({
	apiHelpers,
	assetId,
	assetTitle,
	channel,
	individualIdentities,
	page,
	pages = ['DXP Page 1'],
	rangeSelector,
}: {
	apiHelpers: ApiHelpers;
	assetId: string;
	assetTitle: string;
	channel: {
		id: string;
	};
	individualIdentities: IndividualIdentity[];
	page: Page;
	pages?: string[];
	rangeSelector: RangeSelectors;
}) {
	const canonicalUrl = 'https://www.liferay.com';

	let blogEvents = [];
	let pageEvents = [];

	for (const pageTitle of pages) {
		const date = new Date();

		const currentPageEvents = individualIdentities.map((identity) => ({
			canonicalUrl,
			channelId: channel.id,
			eventDate: date.toISOString(),
			title: pageTitle,
			userId: identity.id,
			views: Number(rangeSelector),
		}));

		pageEvents = [...pageEvents, ...currentPageEvents];

		for (let i = 0; i <= Number(rangeSelector); i++) {
			const currentBlogEvents = individualIdentities.map((identity) => ({
				assetId,
				assetTitle,
				canonicalUrl,
				channelId: channel.id,
				clicks: 1,
				comments: 2,
				eventDate: date.toISOString(),
				pageTitle,
				ratings: 1,
				ratingsScore: 1,
				readTime: 1,
				sessions: 1,
				userId: identity.id,
				views: 1,
			}));

			blogEvents = [...blogEvents, ...currentBlogEvents];

			date.setDate(date.getDate() - 1);
		}
	}

	await apiHelpers.jsonWebServicesOSBAsah.createPagesDaily(pageEvents);

	await apiHelpers.jsonWebServicesOSBAsah.createBlogsDaily(blogEvents);

	await page.waitForTimeout(1000);
}
