/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getRandomInt} from '../utils/util';
import {ApiHelpers} from './ApiHelpers';

export class HeadlessCommerceAdminChannelApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-commerce-admin-channel/v1.0/';
	}

	async deleteChannel(channelId: string) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/channels/${channelId}`
		);
	}

	async getChannel(channelId: string) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/channels/${channelId}`
		);
	}

	async postChannel(
		channelName: string = 'Channel' + getRandomInt(),
		siteGroupId: string
	) {
		const postChannel = await this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/channels`,
			{
				currencyCode: 'USD',
				name: channelName,
				siteGroupId,
				type: 'site',
			}
		);

		return postChannel;
	}
}
