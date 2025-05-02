/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayConfig} from '../../liferay.config';
import {ApiHelpers} from '../ApiHelpers';

type Calendar = {
	calendarId: string;
};

export class JSONWebServicesCalendarApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/calendar.calendar';
	}

	async addCalendar({
		calendarResourceId,
		color = 0,
		defaultCalendar = false,
		descriptionMap,
		enableComments = false,
		enableRatings = false,
		groupId,
		nameMap,
		timeZoneId = 'UTC',
	}: {
		calendarResourceId: string;
		color?: number;
		defaultCalendar?: boolean;
		descriptionMap: string;
		enableComments?: boolean;
		enableRatings?: boolean;
		groupId: string;
		nameMap: string;
		timeZoneId?: string;
	}): Promise<Calendar> {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('calendarResourceId', calendarResourceId);
		urlSearchParams.append('color', String(color));
		urlSearchParams.append('defaultCalendar', String(defaultCalendar));
		urlSearchParams.append('descriptionMap', descriptionMap);
		urlSearchParams.append('enableComments', String(enableComments));
		urlSearchParams.append('enableRatings', String(enableRatings));
		urlSearchParams.append('groupId', groupId);
		urlSearchParams.append('nameMap', nameMap);
		urlSearchParams.append('timeZoneId', timeZoneId);

		return await this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/add-calendar`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}
}
