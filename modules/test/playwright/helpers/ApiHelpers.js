/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayConfig} from '../liferay.config';
import {FeatureFlagApiHelper} from './FeatureFlagApiHelper';
import {ObjectAdminApiHelper} from './ObjectAdminApiHelper';

export class ApiHelpers {
	constructor(page) {
		this.baseUrl = liferayConfig.environment.baseUrl + '/o/';
		this.featureFlag = new FeatureFlagApiHelper(page);
		this.objectAdmin = new ObjectAdminApiHelper(this);
		this.page = page;
	}

	async delete(url) {
		return this.page.request.delete(url, {
			headers: await this.getHeader(),
		});
	}

	async post(url, data) {
		const response = await this.page.request.post(url, {
			data,
			headers: await this.getHeader(),
		});

		return await response.json();
	}

	async getHeader() {
		const authToken = await this.page.evaluate(() => Liferay.authToken);

		return {
			'Content-Type': 'application/json',
			'x-csrf-token': authToken,
		};
	}
}
