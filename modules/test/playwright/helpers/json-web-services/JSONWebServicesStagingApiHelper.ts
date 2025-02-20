/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayConfig} from '../../liferay.config';
import {ApiHelpers} from '../ApiHelpers';

export class JSONWebServicesStagingApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/staging';
	}

	/**
	 * Enables local staging for a given site (group).
	 *
	 * @param branchingPrivate
	 * Whether private pages should support branching.
	 *
	 * @param branchingPublic
	 * Whether public pages should support branching.
	 *
	 * @param groupId
	 * The ID of the group (site) for which staging should be enabled.
	 *
	 * @param unCheckedContent
	 * Content to exclude from staging (optional).
	 */
	async enableLocalStaging({
		branchingPrivate = false,
		branchingPublic = false,
		groupId,
		unCheckedContent = null,
	}: {
		branchingPrivate?: boolean;
		branchingPublic?: boolean;
		groupId: number | string;
		unCheckedContent?: any;
	}): Promise<void> {
		const portalURL = liferayConfig.environment.baseUrl;
		const serviceContext = JSON.stringify({});

		const urlSearchParams = new URLSearchParams();
		urlSearchParams.append('groupId', groupId.toString());
		urlSearchParams.append('branchingPublic', String(branchingPublic));
		urlSearchParams.append('branchingPrivate', String(branchingPrivate));
		urlSearchParams.append('serviceContext', serviceContext);

		if (unCheckedContent !== null) {
			urlSearchParams.append(
				'unCheckedContent',
				JSON.stringify(unCheckedContent)
			);
		}

		await this.apiHelpers.post(
			`${portalURL}${this.basePath}/enable-local-staging`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}
}
