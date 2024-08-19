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

	async getGroups() {
		return this.apiHelpers.getResponse(
			`${this.apiHelpers.baseUrl}${this.basePath}v2/Groups`,
			false,
			{
				'Content-Type': 'application/scim+json',
				...(await this.apiHelpers.getCSRFTokenHeader()),
			}
		);
	}

	async getGroupsWithOAuth(oAuth2Token: string) {
		return this.apiHelpers.getResponse(
			`${this.apiHelpers.baseUrl}${this.basePath}v2/Groups`,
			false,
			{
				'Authorization': `Bearer ${oAuth2Token}`,
				'Content-Type': 'application/scim+json',
			}
		);
	}

	async getUsers() {
		return this.apiHelpers.getResponse(
			`${this.apiHelpers.baseUrl}${this.basePath}v2/Users`,
			false,
			{
				'Content-Type': 'application/scim+json',
				...(await this.apiHelpers.getCSRFTokenHeader()),
			}
		);
	}

	getUsersWithOAuth(oAuth2Token: string) {
		return this.apiHelpers.getResponse(
			`${this.apiHelpers.baseUrl}${this.basePath}v2/Users`,
			false,
			{
				'Authorization': `Bearer ${oAuth2Token}`,
				'Content-Type': 'application/scim+json',
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

	async postGroupWithOAuth(data: any, oAuth2Token: string) {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}v2/Groups`,
			{
				data,
				headers: {
					'Authorization': `Bearer ${oAuth2Token}`,
					'Content-Type': 'application/scim+json',
				},
			}
		);
	}

	async postUser(data: any) {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}v2/Users`,
			{
				data,
				headers: {
					'Content-Type': 'application/scim+json',
					...(await this.apiHelpers.getCSRFTokenHeader()),
				},
			}
		);
	}

	async postUserWithOAuth(data: any, oAuth2Token: string) {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}v2/Users`,
			{
				data,
				headers: {
					'Authorization': `Bearer ${oAuth2Token}`,
					'Content-Type': 'application/scim+json',
				},
			}
		);
	}
}
