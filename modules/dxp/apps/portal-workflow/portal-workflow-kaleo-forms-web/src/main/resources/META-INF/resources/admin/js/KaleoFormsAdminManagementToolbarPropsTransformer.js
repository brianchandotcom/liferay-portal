/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
