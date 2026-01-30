/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ContentData} from '../../../../utils/types';

import type {WithLiferay} from '../../../../liferay/index';

export async function getEventData({
	articleId,
	lang,
	liferay,
}: WithLiferay<{articleId: number; lang: string}>): Promise<{
	data: null | ContentData;
	error?: unknown;
}> {
	try {
		const response = await liferay.fetch(
			liferay.contentEndpoints.getEventURL(articleId),
			{lang}
		);

		const article = await response.json();

		return {data: article || null};
	}
	catch (error) {
		return {
			data: null,
			error,
		};
	}
}
