/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getChannelProductByFriendlyUrlPath} from 'liferay-headless-rest-client/headless-commerce-delivery-catalog-v1.0';

import {WithLiferay} from '../../../liferay/server';

export async function getProductDetails({
	friendlyUrlPath,
	liferay,
	nestedFields,
}: WithLiferay<{
	friendlyUrlPath: string;
	nestedFields: string;
}>) {
	const response = getChannelProductByFriendlyUrlPath({
		client: liferay.client,
		path: {
			channelId: liferay.getChannel().id,
			friendlyUrlPath,
		},
		query: {nestedFields} as unknown as Record<string, string>,
	});

	return response;
}
