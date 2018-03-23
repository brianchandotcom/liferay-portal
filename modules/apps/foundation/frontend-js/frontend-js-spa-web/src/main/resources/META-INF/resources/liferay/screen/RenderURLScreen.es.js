'use strict';

import EventScreen from './EventScreen.es';

/**
 * RenderURLScreen
 *
 * Inherits from {@link EventScreen|EventScreen}. This is the screen used for 
 * all requests made to RenderURLs.
 */

class RenderURLScreen extends EventScreen {

	/**
	 * @inheritDoc
	 */

	constructor() {
		super();

		this.cacheable = true;
	}
}

export default RenderURLScreen;