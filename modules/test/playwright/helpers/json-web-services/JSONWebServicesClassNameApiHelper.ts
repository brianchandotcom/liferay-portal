/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayConfig} from '../../liferay.config';
import {ApiHelpers} from '../ApiHelpers';

type ClassName = {
	classNameId: string;
};

export class JSONWebServicesClassNameApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/classname';
	}

	async fetchClassName(value: string): Promise<ClassName> {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('value', value);

		return this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/fetch-class-name`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}
}
