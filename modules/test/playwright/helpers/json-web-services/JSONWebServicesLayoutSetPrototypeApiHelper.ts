/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayConfig} from '../../liferay.config';
import {ApiHelpers} from '../ApiHelpers';

export type LayoutSetPrototype = {
	layoutSetPrototypeId: number;
	nameCurrentValue: string;
	uuid: string;
};

export class JSONWebServicesLayoutSetPrototypeApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;
	private layoutSetPrototypes: LayoutSetPrototype[];

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/layoutsetprototype';
		this.layoutSetPrototypes = [];
	}

	async deleteLayoutSetPrototypes(layoutSetPrototypeId: string) {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('layoutSetPrototypeId', layoutSetPrototypeId);

		return this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/delete-layout-set-prototype`,
			urlSearchParams.toString(),
			true,
			await this.apiHelpers.getJSONWebServicesHeaders()
		);
	}

	async getLayoutSetPrototypes(): Promise<LayoutSetPrototype[]> {
		const urlSearchParams = new URLSearchParams();

		const company =
			await this.apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
				'liferay.com'
			);

		urlSearchParams.append('companyId', company.companyId);

		return this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/get-layout-set-prototypes`,
			urlSearchParams.toString(),
			true,
			await this.apiHelpers.getJSONWebServicesHeaders()
		);
	}
}
