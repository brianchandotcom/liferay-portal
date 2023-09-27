/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {COOKIE_TYPES, getTop, sessionStorage} from 'frontend-js-web';

export default function propsTransformer({...otherProps}) {
	return {
		...otherProps,
		onCreateButtonClick(event, {item}) {
			const data = item?.data;

			if (data?.action === 'addSegmentEntry') {
				sessionStorage.setItem(
					data?.sessionKey,
					'open',
					COOKIE_TYPES.NECESSARY
				);

				getTop().location.href = data?.addSegmentEntryURL;
			}
		},
	};
}
