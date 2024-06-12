/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../../../helpers/ApiHelpers';

export class PicklistApiHelpers extends ApiHelpers {
	async createPicklist({name}: {name: string}) {
		const url = `${this.baseUrl}headless-admin-list-type/v1.0/list-type-definitions`;

		const data = {
			name_i18n: {
				en_US: name,
			},
		};

		return this.post(url, {data});
	}

	async deletePicklist(id: number) {
		const url = `${this.baseUrl}headless-admin-list-type/v1.0/list-type-definitions/${id}`;

		return this.delete(url);
	}

	async addPicklistEntry({
		entryKey,
		entryName,
		picklistId,
	}: {
		entryKey: string;
		entryName: string;
		picklistId: number;
	}) {
		const url = `${this.baseUrl}headless-admin-list-type/v1.0/list-type-definitions/${picklistId}/list-type-entries`;

		const data = {
			key: entryKey,
			name_i18n: {
				en_US: entryName,
			},
		};

		return this.post(url, {data});
	}

	async getPicklist(name: string) {
		const url = `${this.baseUrl}headless-admin-list-type/v1.0/list-type-definitions`;

		const picklists = await this.get(url);

		return picklists.items.find((item) => item.name === name);
	}
}
