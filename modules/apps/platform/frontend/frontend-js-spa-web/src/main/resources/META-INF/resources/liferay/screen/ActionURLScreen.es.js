'use strict';

import EventScreen from './EventScreen.es';

class ActionURLScreen extends EventScreen {
	constructor() {
		super();

		this.httpMethod = 'POST';
	}
}

export default ActionURLScreen;