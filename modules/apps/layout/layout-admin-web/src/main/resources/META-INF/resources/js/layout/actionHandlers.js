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
	copyLayout: ({actionURL, namespace}) => {
		Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true,
				height: 480,
				resizable: false,
				width: 640,
			},
			dialogIframe: {
				bodyCssClass: 'dialog-with-footer',
			},
			id: `${namespace}addLayoutDialog`,
			title: Liferay.Language.get('copy-page'),
			uri: actionURL,
		});
	},

	delete: ({actionURL}) => {
		const deleteMessage = Liferay.Language.get(
			'are-you-sure-you-want-to-delete-this'
		);

		if (confirm(deleteMessage)) {
			Liferay.Util.navigate(actionURL);
		}
	},

	permissions: ({actionURL}) => {
		Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true,
				modal: true,
			},
			dialogIframe: {
				bodyCssClass: 'dialog-with-footer',
			},
			title: Liferay.Language.get('permissions'),
			uri: actionURL,
		});
	},
};

export default actionHandlers;
