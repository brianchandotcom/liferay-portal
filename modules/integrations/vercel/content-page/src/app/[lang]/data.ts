/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {LocalizedField, WithLiferay} from '../../liferay/index';

export interface ContentData
	extends LocalizedField<'title'>,
		LocalizedField<'content'>,
		LocalizedField<'summary'> {
	dateCreated: string;
	id: number;
	image: {
		link: {href: string; label: string};
	};
	locationMapUrl: string;
	locationName: string;
	registrationLink: string;
}

export async function getContentData({
	lang,
	liferay,
}: WithLiferay<{lang: string}>): Promise<{
	data: null | ContentData;
	error?: unknown;
}> {
	try {
		const response = await liferay.fetch(
			liferay.contentEndpoints.getContentURL(),
			{lang}
		);

		const data = (await response.json()) as ContentData;

		return {data};
	}
	catch (error) {
		return {
			data: null,
			error,
		};
	}
}
