/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openConfirmModal} from 'frontend-js-web';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const deleteAssetDisplayPageEntry = (itemData) => {
		openConfirmModal({
			message: itemData.deleteAssetDisplayPageEntryMessage,
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					const form = document.getElementById(
						`${portletNamespace}fm`
					);

					if (form) {
						submitForm(
							form,
							itemData?.deleteAssetDisplayPageEntryURL
						);
					}
				}
			},
		});
	};
	const updateAssetDisplayPageEntry = (itemData) => {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-do-not-want-to-set-a-display-page-template-for-this'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					const form = document.getElementById(
						`${portletNamespace}fm`
					);

					if (form) {
						submitForm(
							form,
							itemData?.updateAssetDisplayPageEntryURL
						);
					}
				}
			},
		});
	};

	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const data = item?.data;

			const action = data?.action;

			if (action === 'deleteAssetDisplayPageEntry') {
				deleteAssetDisplayPageEntry(data);
			}
			else if (action === 'updateAssetDisplayPageEntry') {
				updateAssetDisplayPageEntry(data);
			}
		},
	};
}
