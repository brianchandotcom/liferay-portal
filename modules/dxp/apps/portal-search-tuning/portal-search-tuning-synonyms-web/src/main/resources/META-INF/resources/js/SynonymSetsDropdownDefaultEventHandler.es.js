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

class SynonymSetsDropdownDefaultEventHandler extends DefaultEventHandler {
	delete(itemData) {
		const message = Liferay.Language.get(
			'are-you-sure-you-want-to-delete-this'
		);

		if (confirm(message)) {
			this._send(itemData.deleteURL);
		}
	}

	_send(url) {
		submitForm(document.hrefFm, url);
	}
}

export default SynonymSetsDropdownDefaultEventHandler;
