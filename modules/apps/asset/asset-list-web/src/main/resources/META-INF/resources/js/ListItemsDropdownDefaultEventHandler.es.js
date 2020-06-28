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

class ListItemsDropdownDefaultEventHandler extends DefaultEventHandler {
	editContent(itemData) {
		this._navigate(itemData.editContentURL);
	}

	editDisplayPageTemplate(itemData) {
		this._navigate(itemData.editDisplayPageTemplateURL);
	}

	viewDisplayPage(itemData) {
		this._navigate(itemData.viewDisplayPageURL);
	}

	_navigate(url) {
		const openerWindow = Liferay.Util.getTop();

		openerWindow.Liferay.Util.navigate(url);
	}
}

ListItemsDropdownDefaultEventHandler.STATE = {
	spritemap: Config.string(),
};

export default ListItemsDropdownDefaultEventHandler;
