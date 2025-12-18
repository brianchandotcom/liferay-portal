/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers, DataApiHelpers} from './ApiHelpers';

export class HeadlessDigitalSalesRoomApiHelper {
	readonly apiHelpers: ApiHelpers | DataApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers | DataApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-digital-sales-room/v1.0';
	}

	async getDigitalSalesRooms() {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/digital-sales-rooms`
		);
	}

	async getDigitalSalesRoomTemplates() {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/digital-sales-room-templates`
		);
	}
}
