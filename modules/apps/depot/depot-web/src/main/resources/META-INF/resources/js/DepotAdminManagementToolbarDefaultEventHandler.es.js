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

import confirmDepotEntryDeletion from './confirmDepotEntryDeletion.es';

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
		if (confirmDepotEntryDeletion()) {
			const form = this.one('#fm');

			Liferay.Util.postForm(form, {
				data: {
					deleteEntryIds: Liferay.Util.listCheckedExcept(
						form,
						this.ns('allRowIds')
					)
				},
				url: this.deleteDepotEntriesURL
			});
		}
	}
}

DepotAdminManagementToolbarDefaultEventHandler.STATE = {
	deleteDepotEntriesURL: Config.string(),
	spritemap: Config.string()
};

export default DepotAdminManagementToolbarDefaultEventHandler;
