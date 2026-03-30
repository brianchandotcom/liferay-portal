/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';

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

	async deletePicklist(externalReferenceCode: string) {
		const picklist = await this.getPicklist(externalReferenceCode);

		const url = `${this.baseUrl}headless-admin-list-type/v1.0/list-type-definitions/${picklist.id}`;

		return this.delete(url);
	}

	async editPicklist({
		externalReferenceCode,
		key,
		value,
	}: {
		externalReferenceCode: string;
		key: string;
		value: string;
	}) {
		const picklist = await this.getPicklist(externalReferenceCode);

		const url = `${this.baseUrl}headless-admin-list-type/v1.0/list-type-definitions/${picklist.id}/list-type-entries`;

		const data = {
			key,
			name_i18n: {
				en_US: value,
			},
		};

		return this.post(url, {data});
	}

	async getPicklist(externalReferenceCode: string) {
		const url = `${this.baseUrl}headless-admin-list-type/v1.0/list-type-definitions/by-external-reference-code/${externalReferenceCode}`;

		return this.get(url);
	}
}
