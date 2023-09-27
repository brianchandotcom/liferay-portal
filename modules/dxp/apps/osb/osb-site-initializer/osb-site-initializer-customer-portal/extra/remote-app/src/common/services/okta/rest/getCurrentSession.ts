/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CONTENT_TYPES} from '../../../../routes/customer-portal/utils/constants';
import {Liferay} from '../../liferay';

const event = Liferay.publish('okta-status-changed', {
	async: true,
	fireOn: true,
});

export async function getCurrentSession(oktaSessionAPI: string) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(`${oktaSessionAPI}/me`, {
		credentials: 'include',
	});

	const responseContentType = response.headers.get('content-type');

	event.fire({
		statusCode: response.status,
		success: response.ok,
	});

	return responseContentType === CONTENT_TYPES.json ? response.json() : null;
}
