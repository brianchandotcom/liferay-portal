'use strict';

import EventScreen from './EventScreen.es';

class RenderURLScreen extends EventScreen {
	constructor() {
		super();

		this.cacheable = true;
	}
}

export default RenderURLScreen;