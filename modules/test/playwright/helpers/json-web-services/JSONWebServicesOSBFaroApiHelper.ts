/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {faroConfig} from '../../tests/osb-faro-web/main/faro.config';
import {ApiHelpers} from '../ApiHelpers';

type Channel = {
	id: string;
	name: string;
};

type FaroUser = {
	emailAddress: string;
	groupId: string;
	id: string;
	name: string;
	roleName: string;
	status: number;
	userId: number;
};

type IndividualSegment = {
	id: string;
	name: string;
};

type Project = {
	groupId: string;
	name: string;
};

type ApiToken = {
	createDate: string;
	expirationDate: string;
	lastAccessDate: string;
	token: string;
};

const _authorization = `Basic ${btoa(
	`${faroConfig.user.login}:${faroConfig.user.password}`
)}`;

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

		header.append('Authorization', _authorization);

		return fetch(
			`${faroConfig.environment.baseUrl}${this.basePath}/main/${groupId}/channel`,
			{
				body: formdata,
				headers: header,
				method: 'POST',
			}
		).then((response) => response.json());
	}

	async createIndividualSegment({
		channelId,
		filter = '',
		groupId,
		name,
	}: {
		channelId: string;
		filter?: string;
		groupId: string;
		name: string;
	}): Promise<IndividualSegment> {
		const formdata = new FormData();

		formdata.append('channelId', channelId);
		formdata.append('filter', filter);
		formdata.append('name', name);
		formdata.append('segmentType', 'BATCH');

		const header = new Headers();

		header.append('Authorization', _authorization);

		const response = await fetch(
			`${faroConfig.environment.baseUrl}${this.basePath}/contacts/${groupId}/individual_segment`,
			{
				body: formdata,
				headers: header,
				method: 'POST',
			}
		);

		if (!response.ok) {
			throw new Error(
				`createIndividualSegment failed: ${response.status} ${await response.text()}`
			);
		}

		return response.json();
	}

	async createUser(
		emailAddress: string,
		groupId: string,
		roleName: string,
		sendEmail: boolean = false
	): Promise<FaroUser[]> {
		const formdata = new FormData();

		formdata.append('emailAddresses', JSON.stringify([emailAddress]));
		formdata.append('roleName', roleName);
		formdata.append('sendEmail', String(sendEmail));

		const header = new Headers();

		header.append('Authorization', _authorization);

		return fetch(
			`${faroConfig.environment.baseUrl}${this.basePath}/main/${groupId}/user`,
			{
				body: formdata,
				headers: header,
				method: 'POST',
			}
		).then((response) => response.json());
	}

	async createProject(name: string): Promise<Project> {
		const formdata = new FormData();

		formdata.append('emailAddressDomains', '[]');
		formdata.append('incidentReportEmailAddresses', '["test@liferay.com"]');
		formdata.append('name', name);
		formdata.append('serverLocation', 'us-west1-ac4-c1');

		const header = new Headers();

		header.append('Authorization', _authorization);

		return fetch(
			`${faroConfig.environment.baseUrl}${this.basePath}/main/project/trial`,
			{
				body: formdata,
				headers: header,
				method: 'POST',
			}
		).then((response) => response.json());
	}

	async deleteIndividualSegments(
		ids: string,
		groupId: string
	): Promise<Response> {
		const formdata = new FormData();

		formdata.append('ids', ids);

		const header = new Headers();

		header.append('Authorization', _authorization);

		return fetch(
			`${faroConfig.environment.baseUrl}${this.basePath}/contacts/${groupId}/individual_segment`,
			{
				body: formdata,
				headers: header,
				method: 'DELETE',
			}
		).then((response) => response);
	}

	async addChannelUsers(
		channelId: string,
		groupId: string,
		userIds: number[]
	): Promise<Response> {
		const formdata = new FormData();

		formdata.append('userIds', JSON.stringify(userIds));

		const header = new Headers();

		header.append('Authorization', _authorization);

		return fetch(
			`${faroConfig.environment.baseUrl}${this.basePath}/main/${groupId}/channel/${channelId}/users`,
			{
				body: formdata,
				headers: header,
				method: 'POST',
			}
		).then((response) => response);
	}

	async updateChannelPermission(
		channelId: string,
		groupId: string,
		permissionType: number
	): Promise<Response> {
		const formdata = new FormData();

		formdata.append('permissionType', String(permissionType));

		const header = new Headers();

		header.append('Authorization', _authorization);

		return fetch(
			`${faroConfig.environment.baseUrl}${this.basePath}/main/${groupId}/channel/${channelId}`,
			{
				body: formdata,
				headers: header,
				method: 'PATCH',
			}
		).then((response) => response);
	}

	async getUser(groupId: string, emailAddress: string): Promise<FaroUser> {
		const urlSearchParams = new URLSearchParams({
			cur: '1',
			delta: '1',
			query: emailAddress,
		});

		const {items} = await this.apiHelpers.get(
			`${faroConfig.environment.baseUrl}${this.basePath}/main/${groupId}/user?${urlSearchParams.toString()}`,
			true,
			await this.apiHelpers.getJSONWebServicesHeaders()
		);

		return items[0];
	}

	async deleteChannel(ids: string, groupId: string): Promise<Response> {
		const formdata = new FormData();

		formdata.append('ids', ids);

		const header = new Headers();

		header.append('Authorization', _authorization);

		return fetch(
			`${faroConfig.environment.baseUrl}${this.basePath}/main/${groupId}/channel`,
			{
				body: formdata,
				headers: header,
				method: 'DELETE',
			}
		).then((response) => response);
	}

	async deleteDistributionTab(
		distributionTabId: string,
		groupId: string
	): Promise<Response> {
		const formdata = new FormData();

		formdata.append('distributionTabId', distributionTabId);
		formdata.append('scope', 'group');

		const header = new Headers();

		header.append('Authorization', _authorization);

		return fetch(
			`${faroConfig.environment.baseUrl}${this.basePath}/main/${groupId}/preferences/distribution_tabs`,
			{
				body: formdata,
				headers: header,
				method: 'DELETE',
			}
		).then((response) => response);
	}

	async deleteUser(id: string, groupId: string): Promise<Response> {
		const header = new Headers();

		header.append('Authorization', _authorization);

		return fetch(
			`${faroConfig.environment.baseUrl}${this.basePath}/main/${groupId}/user/${id}`,
			{
				headers: header,
				method: 'DELETE',
			}
		).then((response) => response);
	}

	async deleteProject(groupId: number): Promise<Project> {
		const header = new Headers();

		header.append('Authorization', _authorization);

		return fetch(
			`${faroConfig.environment.baseUrl}${this.basePath}/main/project/${groupId}`,
			{
				headers: header,
				method: 'DELETE',
			}
		).then((response) => {
			return response.json();
		});
	}

	async fetchApiToken(groupId: string, expiresIn: number): Promise<ApiToken> {
		return this.apiHelpers.post(
			`${faroConfig.environment.baseUrl}${this.basePath}/main/${groupId}/oauth2/tokens/new?expiresIn=${expiresIn}`,
			{
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}

	async fetchDataSourceConnectionToken(groupId: string): Promise<string> {
		const header = new Headers();

		header.append('Authorization', _authorization);

		const response = await fetch(
			`${faroConfig.environment.baseUrl}${this.basePath}/contacts/${groupId}/data_source/token`,
			{
				headers: header,
				method: 'GET',
			}
		);

		if (!response.ok) {
			throw new Error(
				`fetchDataSourceConnectionToken failed: ${response.status} ${await response.text()}`
			);
		}

		return response.text();
	}
}
