/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getUrlPath} from '../util/utils';
import EventScreen from './EventScreen';

/**
 * ActionURLScreen
 *
 * Inherits from {@link EventScreen|EventScreen}. The screen used for all
 * requests made to ActionURLs.
 */

class ActionURLScreen extends EventScreen {

	/**
	 * @inheritDoc
	 */

	constructor() {
		super();

		this.httpMethod = 'POST';
	}

	/**
	 * @inheritDoc
	 * Ensures that an action request (form submission) redirect's final
	 * URL has the lifecycle RENDER `p_p_lifecycle=0`
	 * @return {!String} The request path
	 */

	getRequestPath() {
		let requestPath = null;

		const request = this.getRequest();

		if (request) {
			const uri = new URL(super.getRequestPath(), window.location.origin);

			if (uri.searchParams.get('p_p_lifecycle') === '1') {
				uri.searchParams.set('p_p_lifecycle', '0');
			}

			requestPath = getUrlPath(uri.toString());
		}

		return requestPath;
	}
}

export default ActionURLScreen;
