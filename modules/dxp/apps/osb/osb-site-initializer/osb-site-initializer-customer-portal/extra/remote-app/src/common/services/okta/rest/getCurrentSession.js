/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {CONTENT_TYPES} from '../../../../routes/customer-portal/utils/constants';
import {Liferay} from '../../liferay';

export async function getCurrentSession(oktaSessionAPI) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(`${oktaSessionAPI}/me`, {
		credentials: 'include',
	});

	if (response.status >= 400) {
		Liferay.Util.navigate('/c/portal/login');
	}

	const responseContentType = response.headers.get('content-type');

	return responseContentType === CONTENT_TYPES.json ? response.json() : null;
}
