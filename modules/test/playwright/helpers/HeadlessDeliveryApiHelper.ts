/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Layout} from '../types/Layout';
import {PageDefinition} from '../types/PageDefinition';
import {ApiHelpers} from './ApiHelpers';

export class HeadlessDeliveryApiHelper {
	apiHelpers: ApiHelpers;
	basePath: string;

	constructor(apiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-delivery/v1.0';
	}

	async createSitePage(
		siteId: string,
		title: string,
		pageDefinition?: PageDefinition
	): Promise<Layout> {
		await this.apiHelpers.featureFlag.updateFeatureFlag('LPS-178052', true);

		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/${siteId}/site-pages`,
			{pageDefinition, title}
		);
	}
}
