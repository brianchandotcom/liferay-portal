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

import {DefaultEventHandler} from 'frontend-js-web';

class LayoutPrototypeDropdownDefaultEventHandler extends DefaultEventHandler {
	deleteLayoutPrototype(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			this._send(itemData.deleteLayoutPrototypeURL);
		}
	}

	exportLayoutPrototype(itemData) {
		this._openWindow('export', itemData.exportLayoutPrototypeURL);
	}

	importLayoutPrototype(itemData) {
		this._openWindow('import', itemData.importLayoutPrototypeURL);
	}

	permissionsLayoutPrototype(itemData) {
		this._openWindow('permissions', itemData.permissionsLayoutPrototypeURL);
	}

	_openWindow(label, url) {
		Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true,
				modal: true
			},
			dialogIframe: {
				bodyCssClass: 'dialog-with-footer'
			},
			title: Liferay.Language.get(label),
			uri: url
		});
	}

	_send(url) {
		submitForm(document.hrefFm, url);
	}
}

export default LayoutPrototypeDropdownDefaultEventHandler;
