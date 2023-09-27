/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	fetch,
	openConfirmModal,
	openSimpleInputModal,
	openToast,
} from 'frontend-js-web';

function deleteEntry(deleteURL) {
	openConfirmModal({
		message: Liferay.Language.get('are-you-sure-you-want-to-delete-this'),
		onConfirm: (isConfirmed) => {
			if (isConfirmed) {
				fetch(deleteURL, {
					method: 'DELETE',
				}).then((response) => {
					if (response.ok) {
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
				});
			}
		},
	});
}

function renameEntry(renameURL, value) {
	openSimpleInputModal({
		dialogTitle: Liferay.Language.get('rename'),
		formSubmitURL: renameURL,
		mainFieldLabel: Liferay.Language.get('name'),
		mainFieldName: 'name',
		mainFieldValue: value,
		namespace: '',
		onFormSuccess: (response) => {
			if (response.success) {
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
		},
	});
}

export {deleteEntry, renameEntry};
