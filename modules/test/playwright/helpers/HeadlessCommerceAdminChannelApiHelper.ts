/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getRandomInt} from '../utils/util';
import {ApiHelpers} from './ApiHelpers';

type TChannel = {
	currencyCode?: string;
	name?: string;
	siteGroupId: number | string;
	type?: string;
};

export class HeadlessCommerceAdminChannelApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-commerce-admin-channel/v1.0/';
	}

	async deleteChannel(channelId: number) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/channels/${channelId}`
		);
	}

	async getChannel(channelId: number): Promise<TChannel> {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/channels/${channelId}`
		);
	}

	async postChannel(channel: TChannel) {
		return await this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/channels`,
			{
				currencyCode: 'USD',
				name: 'Channel' + getRandomInt(),
				siteGroupId: 0,
				type: 'site',
				...channel,
			}
		);
	}
}
