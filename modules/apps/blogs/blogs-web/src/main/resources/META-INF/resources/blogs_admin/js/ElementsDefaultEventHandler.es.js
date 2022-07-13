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

import {DefaultEventHandler, openConfirmModal} from 'frontend-js-web';

class ElementsDefaultEventHandler extends DefaultEventHandler {
	created(props) {
		this.trashEnabled = props.trashEnabled;
	}

	delete(itemData) {
		if (this.trashEnabled) {
			this._send(itemData.deleteURL);
		}
		else {
			openConfirmModal({
				message: Liferay.Language.get(
					'are-you-sure-you-want-to-delete-this'
				),
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						this._send(itemData.deleteURL);
					}
				},
			});
		}
	}

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
	}

	publishToLive(itemData) {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-publish-to-live'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					this._send(itemData.publishEntryURL);
				}
			},
		});
	}

	_send(url) {
		submitForm(document.hrefFm, url);
	}
}

export default ElementsDefaultEventHandler;
