/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayConfig} from '../../liferay.config';
import {ApiHelpers} from '../ApiHelpers';

type CalendarResource = {
	calendarResourceId: string;
};

export class JSONWebServicesCalendarResourceApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/calendar.calendarresource';
	}

	async fetchCalendarResource({
		classNameId,
		classPK,
	}: {
		classNameId: string;
		classPK: string;
	}): Promise<CalendarResource> {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('classNameId', classNameId);
		urlSearchParams.append('classPK', classPK);

		return await this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/fetch-calendar-resource`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}
}
