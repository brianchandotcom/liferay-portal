/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch, openConfirmModal, openModal} from 'frontend-js-web';

const ACTIONS = {
	editDefaultPermissions(itemData) {
		openModal({
			size: 'full-screen',
			title: Liferay.Language.get('edit-default-permissions'),
			url: itemData.editDefaultPermissionsURL,
		});
	},
	resetDefaultPermissions(itemData) {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-reset-your-customizations-to-default'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					fetch(itemData.resetDefaultPermissionsURL).then(
						(response) => {
							response.json().then((res) => {
								if (res.success) {
									Liferay.Util.openToast({
										message: Liferay.Language.get(
											'your-request-completed-successfully'
										),
										type: 'success',
									});
								}
								else {
									Liferay.Util.openToast({
										message: Liferay.Language.get(
											'an-unexpected-error-occurred'
										),
										type: 'danger',
									});
								}
							});
						}
					);
				}
			},
		});
	},
};

export default function propsTransformer({items, ...otherProps}) {
	return {
		...otherProps,
		items: items.map((item) => {
			return {
				...item,
				onClick(event) {
					const action = item.data?.action;

					if (action) {
						event.preventDefault();

						ACTIONS[action](item.data);
					}
				},
			};
		}),
	};
}
