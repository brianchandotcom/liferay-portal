/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	fetch,
	openConfirmModal,
	openToast,
	sessionStorage,
} from 'frontend-js-web';

export default function propsTransformer({...otherProps}) {
	const handleDelete = (isConfirmed, itemData) => {
		if (isConfirmed) {
			const deleteURL = itemData.actions.delete.href;

			fetch(deleteURL.replace('{id}', itemData.id), {method: 'DELETE'})
				.then((response) => {
					if (response.ok) {
						sessionStorage.setItem(
							'com.liferay.change.tracking.web.successMessage',
							Liferay.Language.get(
								'your-request-completed-successfully'
							),
							sessionStorage.TYPES.NECESSARY
						);

						window.location.reload();
					}
					else {
						openToast({
							message: Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
							type: 'danger',
						});
					}
				})
				.catch(() => {
					openToast({
						message: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
						type: 'danger',
					});
				});
		}
	};

	return {
		...otherProps,
		onActionDropdownItemClick({action, itemData}) {
			if (action.data.id === 'delete') {
				openConfirmModal({
					message: Liferay.Language.get(
						'are-you-sure-you-want-to-delete-this-publication'
					),
					onConfirm: (isConfirmed) =>
						handleDelete(isConfirmed, itemData),
				});
			}
		},
	};
}
