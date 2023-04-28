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

const updateUserEntries = (portletNamespace, url) => {
	const form = document.getElementById(`${portletNamespace}fm`);

	if (form) {
		postForm(form, {
			data: {
				UserEntryIds: getCheckedCheckboxes(
					form,
					`${portletNamespace}allRowIds`
				),
			},
			url,
		});
	}
};

export default function propsTransformer({
	additionalProps: {basePortletURL},
	portletNamespace,
	...otherProps
}) {
	const deactivateUsersEntries = (itemData) => {
		updateUserEntries(portletNamespace, itemData?.deactivateUserEntriesURL);
	};

	return {
		...otherProps,
		onActionButtonClick: (_, {item}) => {
			const data = item?.data;

			const action = data?.action;

			if (action === 'selectUsers') {
				action.selectUsers({
					basePortletURL,
					organizationId: data?.organizationId,
					portletNamespace,
				});
			}
			else if (action === 'deactivateUserEntries') {
				deactivateUsersEntries(data);
			}
		},
	};
}
