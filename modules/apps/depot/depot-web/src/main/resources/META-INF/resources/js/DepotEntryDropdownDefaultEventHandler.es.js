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

import confirmDepotEntryDeletion from './confirmDepotEntryDeletion.es';

class DepotEntryDropdownDefaultEventHandler extends DefaultEventHandler {
	deleteDepotEntry(itemData) {
		if (confirmDepotEntryDeletion()) {
			submitForm(document.hrefFm, itemData.deleteDepotEntryURL);
		}
	}

	permissionsDepotEntry(itemData) {
		this._openWindow(
			Liferay.Language.get('permissions'),
			itemData.permissionsDepotEntryURL
		);
	}

	_openWindow(label, url) {
		Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true,
				modal: true,
			},
			dialogIframe: {
				bodyCssClass: 'dialog-with-footer',
			},
			title: Liferay.Language.get(label),
			uri: url,
		});
	}
}

export default DepotEntryDropdownDefaultEventHandler;
