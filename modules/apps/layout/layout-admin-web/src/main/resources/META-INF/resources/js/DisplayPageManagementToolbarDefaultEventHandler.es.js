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
import {Config} from 'metal-state';

import {openDisplayPageModal} from './modal/openDisplayPageModal.es';

class DisplayPageManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	addDisplayPage(itemData) {
		openDisplayPageModal({
			formSubmitURL: itemData.addDisplayPageURL,
			mappingTypes: itemData.mappingTypes,
			namespace: this.namespace,
			spritemap: this.spritemap,
			title: Liferay.Language.get('add-display-page-template')
		});
	}

	deleteSelectedDisplayPages() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}
}

DisplayPageManagementToolbarDefaultEventHandler.STATE = {
	spritemap: Config.string()
};

export default DisplayPageManagementToolbarDefaultEventHandler;
