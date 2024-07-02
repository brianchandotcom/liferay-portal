/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {asahConfig} from '../../tests/osb-faro-web/asah.config';
import { Nanites } from '../../tests/osb-faro-web/utils/nanites';
import {ApiHelpers} from '../ApiHelpers';

type Event = {
	applicationId: string;
	canonicalUrl: string;
	channelId: string;
	eventDate: string;
	eventId: string;
	title: string;
	userId: string;
};

type Identity = {
	createDate: string;
	id: string;
	individualId?: string;
};

type Field = {
	dataSourceId: number;
	name: string;
	value: string;
};

type Individual = {
	emailAddress: string;
	fields: Field[];
	firstName: string;
	id: string;
	lastName: string;
};

type PageDaily = {
	canonicalUrl: string;
	channelId: string;
	eventDate: string;
	title: string;
	userId: string;
	views: number;
};

type Session = {
	channelId: string;
	id: string;
	sessionEnd: string;
	sessionStart: string;
	userId: string;
};

export class JSONWebServicesOSBAsahApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/functional';
	}

	getHeaders() {
		return {
			'Content-Type': 'application/json',
			'OSB-Asah-Project-ID': asahConfig.environment.projectId,
		};
	}

	async runNanites(nanites: Nanites[]): Promise<any> {
		return this.apiHelpers.post(
			`${asahConfig.environment.batchCuratordUrl}/nanites/run`,
			{
				data: nanites,
				failOnStatusCode: true,
				headers: this.getHeaders(),
			}
		);
	}

	async createEvents(events: Event[]): Promise<any> {
		return this.apiHelpers.post(
			`${asahConfig.environment.backendUrl}${this.basePath}/events`,
			{
				data: events,
				failOnStatusCode: true,
				headers: this.getHeaders(),
			}
		);
	}

	async createIdentities(identities: Identity[]): Promise<any> {
		return this.apiHelpers.post(
			`${asahConfig.environment.backendUrl}${this.basePath}/identities`,
			{
				data: identities,
				failOnStatusCode: true,
				headers: this.getHeaders(),
			}
		);
	}

	async createIndividuals(individuals: Individual[]): Promise<any> {
		return this.apiHelpers.post(
			`${asahConfig.environment.backendUrl}${this.basePath}/individuals`,
			{
				data: individuals,
				failOnStatusCode: true,
				headers: this.getHeaders(),
			}
		);
	}

	async createPagesDaily(pagesDaily: PageDaily[]): Promise<any> {
		return this.apiHelpers.post(
			`${asahConfig.environment.backendUrl}${this.basePath}/pagesdaily`,
			{
				data: pagesDaily,
				failOnStatusCode: true,
				headers: this.getHeaders(),
			}
		);
	}

	async createSessions(session: Session[]): Promise<any> {
		return this.apiHelpers.post(
			`${asahConfig.environment.baseUrl}${this.basePath}/sessions`,
			{
				data: session,
				failOnStatusCode: true,
				headers: this.getHeaders(),
			}
		);
	}

	async closeSessions(): Promise<any> {
		return this.apiHelpers.delete(
			`${asahConfig.environment.baseUrl}${this.basePath}/sessions/close`,
			this.getHeaders()
		);
	}
}
