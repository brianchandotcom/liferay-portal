/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	getCheckedCheckboxes,
	openConfirmModal,
	postForm,
} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {deleteRolesURL},
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		onActionButtonClick() {
			const form = document.getElementById(`${portletNamespace}fm`);

			if (!form) {
				return;
			}

			const deleteRoleIds = getCheckedCheckboxes(
				form,
				`${portletNamespace}allRowIds`
			);

			openConfirmModal({
				message: Liferay.Language.get(
					'are-you-sure-you-want-to-delete-this-role?-task-assignments-may-be-deleted'
				),
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						postForm(form, {
							data: {
								deleteRoleIds,
							},
							url: deleteRolesURL,
						});
					}
				},
			});
		},
	};
}
