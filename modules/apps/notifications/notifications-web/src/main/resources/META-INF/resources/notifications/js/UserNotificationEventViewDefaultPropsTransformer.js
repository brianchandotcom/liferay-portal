/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const ACTIONS = {
	delete({deleteURL}) {
		submitForm(document.hrefFm, deleteURL);
	},

	markNotificationAsRead({markNotificationAsReadURL}) {
		submitForm(document.hrefFm, markNotificationAsReadURL);
	},

	markNotificationAsUnread({markNotificationAsUnreadURL}) {
		submitForm(document.hrefFm, markNotificationAsUnreadURL);
	},

	unsubscribe({unsubscribeURL}) {
		submitForm(document.hrefFm, unsubscribeURL);
	},
};

export default function propsTransformer({items, portletNamespace, ...props}) {
	return {
		...props,
		items: items.map((item) => {
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
