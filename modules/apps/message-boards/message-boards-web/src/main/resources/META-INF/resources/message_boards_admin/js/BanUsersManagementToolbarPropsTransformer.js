/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {postForm} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {banUsersURL},
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			if (item?.data?.action === 'unbanUser') {
				const form = document.getElementById(`${portletNamespace}fm`);

				if (form) {
					postForm(form, {
						data: {
							cmd: 'unban',
						},
						url: banUsersURL,
					});
				}
			}
		},
	};
}
