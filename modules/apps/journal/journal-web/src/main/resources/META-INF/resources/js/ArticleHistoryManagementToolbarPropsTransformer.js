/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openConfirmModal} from 'frontend-js-web';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const deleteArticles = (itemData) => {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-the-selected-version'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					const form = document.getElementById(
						`${portletNamespace}fm`
					);

					if (form) {
						submitForm(form, itemData?.deleteArticlesURL);
					}
				}
			},
		});
	};

	const expireArticles = (itemData) => {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-expire-the-selected-version'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					const form = document.getElementById(
						`${portletNamespace}fm`
					);

					if (form) {
						submitForm(form, itemData?.expireArticlesURL);
					}
				}
			},
		});
	};

	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const data = item.data;

			const action = data?.action;

			if (action === 'deleteArticles') {
				deleteArticles(data);
			}
			else if (action === 'expireArticles') {
				expireArticles(data);
			}
		},
	};
}
