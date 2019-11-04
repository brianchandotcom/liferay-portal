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

class LayoutsManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	convertSelectedPages(itemData) {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-convert-the-pages-selected'
				)
			)
		) {
			this._send(itemData.convertLayoutURL);
		}
	}

	deleteSelectedPages(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			this._send(itemData.deleteLayoutURL);
		}
	}

	_send(url) {
		submitForm(this.one('#fm'), url);
	}
}

export default LayoutsManagementToolbarDefaultEventHandler;
