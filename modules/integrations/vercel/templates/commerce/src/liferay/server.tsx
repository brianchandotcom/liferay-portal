/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createClient} from 'liferay-headless-rest-client';

const LIFERAY_HOST = process.env.LIFERAY_HOST || '';
const LIFERAY_CHANNEL_ID = process.env.LIFERAY_CHANNEL_ID || '0';
const LIFERAY_SITE_NAME =
	process.env.NEXT_PUBLIC_SITE_NAME || 'Liferay Commerce';

export const liferay = {
	client: createClient({
		baseUrl: LIFERAY_HOST,
	}),

	getChannel: () => {
		return {
			id: LIFERAY_CHANNEL_ID,
			siteName: LIFERAY_SITE_NAME,
		};
	},
};

export type Liferay = typeof liferay;

export type WithLiferay<TParams = unknown> = TParams & {liferay: Liferay};
