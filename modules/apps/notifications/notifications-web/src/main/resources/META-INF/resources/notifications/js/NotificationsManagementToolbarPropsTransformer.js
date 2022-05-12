/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {getCheckedCheckboxes, postForm} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {
		deleteNotificationsURL,
		markNotificationsAsReadURL,
		markNotificationsAsUnreadURL,
	},
	portletNamespace,
	...otherProps
}) {
	const deleteNotifications = () => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (form) {
			postForm(form, {
				data: {
					deleteEntryIds: getCheckedCheckboxes(
						form,
						`${portletNamespace}allRowIds`
					),
				},
				url: deleteNotificationsURL,
			});
		}
	};

	const markNotificationsAsRead = () => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (form) {
			form.setAttribute('method', 'post');

			submitForm(form, markNotificationsAsReadURL);
		}
	};

	const markNotificationsAsUnread = () => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (form) {
			form.setAttribute('method', 'post');

			submitForm(form, markNotificationsAsUnreadURL);
		}
	};

	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const action = item?.data?.action;

			if (action === 'deleteNotifications') {
				deleteNotifications();
			}
			else if (action === 'markNotificationsAsRead') {
				markNotificationsAsRead();
			}
			else if (action === 'markNotificationsAsUnread') {
				markNotificationsAsUnread();
			}
		},
	};
}
