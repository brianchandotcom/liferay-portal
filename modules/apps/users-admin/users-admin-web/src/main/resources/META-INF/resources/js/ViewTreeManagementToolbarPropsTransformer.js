/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ACTIONS} from './actions.es';

export default function propsTransformer({
	additionalProps: {basePortletURL},
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const data = item?.data;

			const action = data?.action;

			if (action) {
				event.preventDefault();

				ACTIONS[action](data, portletNamespace);
			}
		},
		onCreationMenuItemClick(event, {item}) {
			const data = item?.data;

			const action = data?.action;

			if (action === 'selectUsers') {
				ACTIONS.selectUsers({
					basePortletURL,
					organizationId: data?.organizationId,
					portletNamespace,
					selectUsersURL: data?.selectUsersURL,
				});
			}
		},
	};
}
