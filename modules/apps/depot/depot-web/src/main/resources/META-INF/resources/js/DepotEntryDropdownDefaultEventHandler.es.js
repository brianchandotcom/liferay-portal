/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	DefaultEventHandler,
	openConfirmModal,
	openWindow,
} from 'frontend-js-web';

class DepotEntryDropdownDefaultEventHandler extends DefaultEventHandler {
	deleteDepotEntry(itemData) {
		openConfirmModal({
			message: Liferay.Language.get(
				'removing-an-asset-library-can-affect-sites-that-use-the-contents-stored-in-it.-are-you-sure-you-want-to-continue-removing-this-asset-library'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					submitForm(document.hrefFm, itemData.deleteDepotEntryURL);
				}
			},
		});
	}

	permissionsDepotEntry(itemData) {
		this._openWindow(
			Liferay.Language.get('permissions'),
			itemData.permissionsDepotEntryURL
		);
	}

	_openWindow(label, url) {
		openWindow({
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
