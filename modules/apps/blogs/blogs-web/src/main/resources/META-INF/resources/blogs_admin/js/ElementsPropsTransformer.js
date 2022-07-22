/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {openConfirmModal} from 'frontend-js-web';

const ACTIONS = {
	delete(itemData, trashEnabled) {
		const message = Liferay.Language.get(
			'are-you-sure-you-want-to-delete-this'
		);

		openConfirmModal({
			message,
			onConfirm: (isConfirmed) => {
				if (isConfirmed || trashEnabled) {
					this.send(itemData.deleteURL);
				}
			},
		});
	},

	permissions(itemData) {
		Liferay.Util.openWindow({
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
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-publish-to-live')
			)
		) {
			this.send(itemData.publishEntryURL);
		}
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
