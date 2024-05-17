/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import {ApiHelpers} from '../ApiHelpers';

type TArticle = {
	content?: string;
	displayDate?: Date;
	entryId?: number;
	expirationDate?: Date;
	priority?: string;
	title?: string;
	type?: string;
	url?: string;
};

export class JSONWebServicesAnnouncementsEntryApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/announcementsentry';
	}

	private formatDate(date: Date) {
		return date.toISOString().split('T')[0];
	}

	async addEntry(article?: TArticle): Promise<TArticle> {
		const urlSearchParams = new URLSearchParams();

		article = {
			content: getRandomString(),
			displayDate: new Date(),
			expirationDate: new Date(
				new Date().setFullYear(new Date().getFullYear() + 1)
			),
			priority: '0',
			title: getRandomString(),
			type: 'general',
			url: '',
			...(article || {}),
		};

		urlSearchParams.append('classNameId', '0');
		urlSearchParams.append('classPK', '0');
		urlSearchParams.append('title', article.title);
		urlSearchParams.append('content', article.content);
		urlSearchParams.append('url', article.url);
		urlSearchParams.append('type', article.type);
		urlSearchParams.append(
			'displayDate',
			this.formatDate(article.displayDate)
		);
		urlSearchParams.append(
			'expirationDate',
			this.formatDate(article.expirationDate)
		);
		urlSearchParams.append('priority', article.priority);
		urlSearchParams.append('alert', 'false');

		return this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/add-entry`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}

	async deleteEntry(entryId: number): Promise<void> {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('entryId', String(entryId));

		return this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/delete-entry`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}
}
