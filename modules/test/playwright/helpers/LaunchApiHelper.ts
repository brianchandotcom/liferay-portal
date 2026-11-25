/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';

export type Launch = {
	description: string;
	id: number;
	name: string;
};

export type LaunchEntry = {
	baseVersionKey: string;
	className: string;
	classPK: number;
	draftId: number;
	id: number;
	r_launchToLaunchEntries_c_launchId: number;
	versionKey?: string;
};

export class LaunchApiHelper {
	readonly apiHelpers: ApiHelpers;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
	}

	async createLaunch(name: string): Promise<Launch> {
		return this.apiHelpers.post(`${this.apiHelpers.baseUrl}launches`, {
			data: {description: '', name, status: {code: 2}},
		});
	}

	async deleteLaunch(launchId: number) {
		await this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}launches/${launchId}`
		);
	}

	async getLaunchByName(name: string): Promise<Launch | undefined> {
		const filter = encodeURIComponent(`name eq '${name}'`);

		const {items} = await this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}launches?filter=${filter}`
		);

		return items?.[0];
	}

	async getLaunchEntries(launchId: number): Promise<LaunchEntry[]> {
		const filter = encodeURIComponent(
			`r_launchToLaunchEntries_c_launchId eq '${launchId}'`
		);

		const {items} = await this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}launch-entries?filter=${filter}&pageSize=100`
		);

		return items || [];
	}

	async getLaunchEntry(
		launchId: number,
		className: string
	): Promise<LaunchEntry> {
		const launchEntries = await this.getLaunchEntries(launchId);

		const launchEntry = launchEntries.find(
			(candidate) => candidate.className === className
		);

		if (!launchEntry) {
			throw new Error(
				`Launch ${launchId} holds no entry of type ${className}`
			);
		}

		return launchEntry;
	}
}
