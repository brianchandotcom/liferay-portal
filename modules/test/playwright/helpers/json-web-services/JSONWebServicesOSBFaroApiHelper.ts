/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {faroConfig} from '../../tests/osb-faro-web/faro.config';
import {ApiHelpers} from '../ApiHelpers';

type Channel = {
	id: string;
};

type Project = {
	groupId: string;
	name: string;
};

export class JSONWebServicesOSBFaroApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/o/faro';
	}

	async getProjects(): Promise<Project[]> {
		return this.apiHelpers.get(
			`${faroConfig.environment.baseUrl}${this.basePath}/main/project`,
			true,
			await this.apiHelpers.getJSONWebServicesHeaders()
		);
	}

	async createChannel(name: string, groupId: string): Promise<Channel> {
		const formdata = new FormData();

		formdata.append('name', name);

		const header = new Headers();

		header.append(
			'Authorization',
			this.apiHelpers.getAuthorizationHeader()
		);

		return fetch(
			`${faroConfig.environment.baseUrl}${this.basePath}/main/${groupId}/channel`,
			{
				body: formdata,
				headers: header,
				method: 'POST',
			}
		).then((response) => response.json());
	}

	async deleteChannel(ids: string, groupId: string): Promise<Response> {
		const formdata = new FormData();

		formdata.append('ids', ids);

		const header = new Headers();

		header.append(
			'Authorization',
			this.apiHelpers.getAuthorizationHeader()
		);

		return fetch(
			`${faroConfig.environment.baseUrl}${this.basePath}/main/${groupId}/channel`,
			{
				body: formdata,
				headers: header,
				method: 'DELETE',
			}
		).then((response) => response);
	}
}
