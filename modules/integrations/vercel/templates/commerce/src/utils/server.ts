/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {headers} from 'next/headers';

export async function getServerURL() {
	const _headers = await headers();

	const host =
		_headers.get('x-forwarded-host') ||
		_headers.get('host') ||
		process.env.NEXT_PUBLIC_SITE_URL ||
		'localhost:3000';

	const protocol = host.includes('localhost') ? 'http' : 'https';

	return `${protocol}://${host}`;
}
