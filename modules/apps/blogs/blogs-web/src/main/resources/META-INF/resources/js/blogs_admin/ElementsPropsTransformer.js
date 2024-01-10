/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openConfirmModal, openWindow} from 'frontend-js-web';

const ACTIONS = {
	delete(itemData, trashEnabled) {
		if (trashEnabled) {
			this.send(itemData.deleteURL);
		}
		else {
			openConfirmModal({
				message: Liferay.Language.get(
					'are-you-sure-you-want-to-delete-this'
				),
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						this.send(itemData.deleteURL);
					}
				},
			});
		}
	},

	permissions(itemData) {
		openWindow({
			dialog: {
				destroyOnHide: true,
				modal: true,
			},
			dialogIframe: {
				bodyCssClass: 'dialog-with-footer',
			},
			title: Liferay.Language.get('permissions'),
			uri: itemData.permissionsURL,
		});
	},

	publishToLive(itemData) {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-publish-to-live'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					this.send(itemData.publishEntryURL);
				}
			},
		});
	},

	send(url) {
		submitForm(document.hrefFm, url);
	},
};

export default function propsTransformer({
	additionalProps: {trashEnabled},
	items,
	...props
}) {
	return {
		...props,
		items: items.map((item) => {
			return {
				...item,
				onClick(event) {
					const action = item.data?.action;

					if (action) {
						event.preventDefault();

						ACTIONS[action](item.data, trashEnabled);
					}
				},
			};
		}),
	};
}
