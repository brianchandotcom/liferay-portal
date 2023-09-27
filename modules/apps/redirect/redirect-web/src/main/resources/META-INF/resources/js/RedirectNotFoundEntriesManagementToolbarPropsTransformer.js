/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
