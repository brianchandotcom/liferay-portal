/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {DefaultEventHandler} from 'frontend-js-web';

import openAddElementModal from './OpenAddElementModalCommand';

class ElementEntriesManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	addElement(itemData) {
		openAddElementModal({
			defaultLocale: itemData.defaultLocale,
			dialogTitle: Liferay.Language.get('new-search-element'),
			formSubmitURL: itemData.editElementURL,
			namespace: this.namespace,
			spritemap: this.spritemap,
			type: itemData.type,
		});
	}
}

export default ElementEntriesManagementToolbarDefaultEventHandler;
