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

import {DefaultEventHandler, openSimpleInputModal} from 'frontend-js-web';
import {Config} from 'metal-state';

class DepotAdminManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	addDepotEntry(itemData) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('add-repository'),
			formSubmitURL: itemData.addDepotEntryURL,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			namespace: this.namespace,
			spritemap: this.spritemap
		});
	}

	deleteSelectedDepotEntries() {
		if (
			confirm(
				Liferay.Language.get(
					'removing-a-repository-can-affect-sites-that-use-the-contents-stored-in-it.-are-you-sure-you-want-to-continue-removing-this-repository'
				)
			)
		) {
			submitForm(this.one('#fm'));
		}
	}
}

DepotAdminManagementToolbarDefaultEventHandler.STATE = {
	spritemap: Config.string()
};

export default DepotAdminManagementToolbarDefaultEventHandler;
