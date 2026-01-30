/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ContentData} from '../../utils/types';

import type {WithLiferay} from '../../liferay/index';

type Page<T> = {
	items: T[];
	page: number;
	pageSize: number;
	totalCount: number;
};

export async function getEventsData({
	lang,
	liferay,
}: WithLiferay<{lang: string}>): Promise<{
	data: null | Page<ContentData>;
	error?: unknown;
}> {
	try {
		const response = await liferay.fetch(
			liferay.contentEndpoints.getEventsURL(),
			{lang}
		);

		const data = await response.json();

		return {data};
	}
	catch (error) {
		return {
			data: null,
			error,
		};
	}
}
