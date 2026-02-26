/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';

export interface MessageId {
	key: string;
	languageId: string;
}

export interface Message extends MessageId {
	value: string;
}

export class LanguageApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'language/v1.0';
	}

	async deleteMessage(messageId: MessageId): Promise<void> {
		await this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/messages?key=${messageId.key}&languageId=${messageId.languageId}`,
			{
				failOnStatusCode: true,
			}
		);
	}

	async putMessage(message: Message): Promise<Message> {
		return this.apiHelpers.put(
			`${this.apiHelpers.baseUrl}${this.basePath}/messages`,
			{
				data: message,
				failOnStatusCode: true,
			}
		);
	}
}
