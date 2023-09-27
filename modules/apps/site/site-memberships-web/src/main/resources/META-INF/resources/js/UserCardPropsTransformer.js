/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ACTIONS} from './actions';

export default function propsTransformer({
	actions,
	portletNamespace,
	...props
}) {
	return {
		...props,
		actions: actions?.map((item) => {
			return {
				...item,
				onClick(event) {
					const action = item.data?.action;

					if (action) {
						event.preventDefault();

						ACTIONS[action](item.data, portletNamespace);
					}
				},
			};
		}),
	};
}
