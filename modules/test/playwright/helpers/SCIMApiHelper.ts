/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';

export class SCIMApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'scim/v1.0/';
	}

	async getUsers(oAuth2Token?: string, failOnStatusCode?: boolean) {
		if (oAuth2Token) {
			return this.apiHelpers.getResponse(
				`${this.apiHelpers.baseUrl}${this.basePath}v2/Users`,
				failOnStatusCode,
				{
					'Authorization': `Bearer ${oAuth2Token}`,
					'Content-Type': 'application/scim+json',
				}
			);
		}
		else {
			return this.apiHelpers.getResponse(
				`${this.apiHelpers.baseUrl}${this.basePath}v2/Users`,
				failOnStatusCode,
				{
					'Content-Type': 'application/scim+json',
					...(await this.apiHelpers.getCSRFTokenHeader()),
				}
			);
		}
	}

	async postUser(data: any, failOnStatusCode?: boolean) {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}v2/Users`,
			{
				data,
				failOnStatusCode,
				headers: {
					'Content-Type': 'application/scim+json',
					...(await this.apiHelpers.getCSRFTokenHeader()),
				},
			}
		);
	}

	async postGroup(data: any) {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}v2/Groups`,
			{
				data,
				headers: {
					'Content-Type': 'application/scim+json',
					...(await this.apiHelpers.getCSRFTokenHeader()),
				},
			}
		);
	}

	async getGroups(failOnStatusCode?: boolean) {
		return this.apiHelpers.getResponse(
			`${this.apiHelpers.baseUrl}${this.basePath}v2/Groups`,
			failOnStatusCode,
			{
				'Content-Type': 'application/scim+json',
				...(await this.apiHelpers.getCSRFTokenHeader()),
			}
		);
	}
}
