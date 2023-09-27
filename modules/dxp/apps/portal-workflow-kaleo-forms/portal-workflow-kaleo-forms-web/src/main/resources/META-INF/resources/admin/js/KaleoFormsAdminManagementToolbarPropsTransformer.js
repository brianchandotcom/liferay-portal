/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getCheckedCheckboxes, openConfirmModal} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {deleteKaleoProcessURL},
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			if (item?.data?.action === 'deleteKaleoProcess') {
				openConfirmModal({
					message: Liferay.Language.get(
						'are-you-sure-you-want-to-delete-this'
					),
					onConfirm: (isConfirmed) => {
						if (isConfirmed) {
							const form = document.getElementById(
								`${portletNamespace}fm`
							);

							const searchContainer = document.getElementById(
								otherProps.searchContainerId
							);

							const kaleoProcessIdsElement = document.getElementById(
								`${portletNamespace}kaleoProcessIds`
							);

							if (
								!form ||
								!searchContainer ||
								!kaleoProcessIdsElement
							) {
								return;
							}

							form.setAttribute('method', 'post');

							kaleoProcessIdsElement.value = getCheckedCheckboxes(
								searchContainer,
								`${portletNamespace}allRowIds`
							);

							submitForm(form, deleteKaleoProcessURL);
						}
					},
				});
			}
		},
	};
}
