'use strict';

import EventScreen from './EventScreen.es';
import Uri from 'metal-uri/src/Uri';
import utils from 'senna/src/utils/utils';

class ActionURLScreen extends EventScreen {
	constructor() {
		super();

		this.httpMethod = 'POST';
	}

	getRequestPath() {
		var request = this.getRequest();

		if (request) {
			let uri = new Uri(super.getRequestPath());

			if (uri.getParameterValue('p_p_lifecycle') === '1') {
				uri.setParameterValue('p_p_lifecycle', '0');
			}

			return utils.getUrlPath(uri.toString());
		}

		return null;
	}
}

export default ActionURLScreen;