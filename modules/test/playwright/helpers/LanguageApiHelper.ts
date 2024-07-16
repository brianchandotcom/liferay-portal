/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';

export class LanguageApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'language/v1.0';
	}

	async deleteMessage(key: string, languageId: string): Promise<void> {
		await this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/messages?key=${key}&languageId=${languageId}`
		);
	}

	async getMessage(key: string, languageId: string): Promise<Message> {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/messages?key=${key}&languageId=${languageId}`
		);
	}

	async putMessage(message: Message): Promise<Message> {
		return this.apiHelpers.put(
			`${this.apiHelpers.baseUrl}${this.basePath}/messages`,
			{data: message}
		);
	}
}
