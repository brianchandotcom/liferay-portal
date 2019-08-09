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

import {utils} from 'senna';

import EventScreen from './EventScreen.es';

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

			requestPath = utils.getUrlPath(uri.toString());
		}

		return requestPath;
	}
}

export default ActionURLScreen;
