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

const actionHandlers = {
	copyLayout: event => {
		event.preventDefault();

		Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true,
				height: 480,
				resizable: false,
				width: 640
			},
			dialogIframe: {
				bodyCssClass: 'dialog-with-footer'
			},
			id: 'addLayoutDialog',
			title: Liferay.Language.get('copy-page'),
			uri: event.target.href
		});
	},

	delete: event => {
		const deleteMessage = Liferay.Language.get(
			'are-you-sure-you-want-to-delete-this'
		);

		if (!confirm(deleteMessage)) {
			event.preventDefault();
		}
	},

	permissions: event => {
		Liferay.Util.openInDialog(event, {
			dialog: {
				destroyOnHide: true
			},
			dialogIframe: {
				bodyCssClass: 'dialog-with-footer'
			},
			uri: event.target.href
		});
	}
};

export default actionHandlers;
