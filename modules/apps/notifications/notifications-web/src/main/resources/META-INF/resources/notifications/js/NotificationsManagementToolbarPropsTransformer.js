/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getCheckedCheckboxes, postForm} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {
		deleteNotificationsURL,
		markNotificationsAsReadURL,
		markNotificationsAsUnreadURL,
		searchContainerId,
	},
	portletNamespace,
	...otherProps
}) {
	let searchContainer;

	Liferay.componentReady(`${portletNamespace}${searchContainerId}`).then(
		(searchContainerComponent) => {
			searchContainer = searchContainerComponent;
		}
	);

	const processAction = (url) => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (form) {
			postForm(form, {
				data: {
					selectAll: searchContainer.select?.get('bulkSelection'),
					selectedEntryIds: getCheckedCheckboxes(
						form,
						`${portletNamespace}allRowIds`
					),
				},
				url,
			});
		}
	};

	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const action = item?.data?.action;

			if (action === 'deleteNotifications') {
				processAction(deleteNotificationsURL);
			}
			else if (action === 'markNotificationsAsRead') {
				processAction(markNotificationsAsReadURL);
			}
			else if (action === 'markNotificationsAsUnread') {
				processAction(markNotificationsAsUnreadURL);
			}
		},
	};
}
