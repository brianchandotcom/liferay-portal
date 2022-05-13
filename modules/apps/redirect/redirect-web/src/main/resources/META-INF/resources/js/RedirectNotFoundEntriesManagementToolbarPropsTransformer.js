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
	additionalProps: {editRedirectNotFoundEntriesURL},
	portletNamespace,
	...otherProps
}) {
	const ignoreSelectedRedirectNotFoundEntries = () => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (form) {
			postForm(form, {
				data: {
					deleteEntryIds: getCheckedCheckboxes(
						form,
						`${portletNamespace}allRowIds`
					),
					ignored: true,
				},
				url: editRedirectNotFoundEntriesURL,
			});
		}
	};

	const unignoreSelectedRedirectNotFoundEntries = () => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (form) {
			postForm(form, {
				data: {
					deleteEntryIds: getCheckedCheckboxes(
						form,
						`${portletNamespace}allRowIds`
					),
					ignored: false,
				},
				url: editRedirectNotFoundEntriesURL,
			});
		}
	};

	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const action = item?.data?.action;

			if (action === 'ignoreSelectedRedirectNotFoundEntries') {
				ignoreSelectedRedirectNotFoundEntries();
			}
			else if (action === 'unignoreSelectedRedirectNotFoundEntries') {
				unignoreSelectedRedirectNotFoundEntries();
			}
		},
	};
}
