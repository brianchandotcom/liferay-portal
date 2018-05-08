'use strict';

import EventScreen from './EventScreen.es';
import Uri from 'metal-uri/src/Uri';
import utils from 'senna/src/utils/utils';

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

		let request = this.getRequest();

		if (request) {
			let uri = new Uri(super.getRequestPath());

			if (uri.getParameterValue('p_p_lifecycle') === '1') {
				uri.setParameterValue('p_p_lifecycle', '0');
			}

			requestPath = utils.getUrlPath(uri.toString());
		}

		return requestPath;
	}
}

export default ActionURLScreen;