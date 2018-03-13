'use strict';

import core from 'metal/src/core';
import dom from 'metal-dom/src/dom';
import Surface from 'senna/src/surface/Surface';

/**
 * LiferaySurface
 *
 * This class inherits from Senna's Surface. It calls Liferay.DOMTaskRunner
 * whenever we add content to a Surface.
 * @review
 */

class LiferaySurface extends Surface {

	/**
	 * @inheritDoc
	 * @review
	 */

	addContent(screenId, content) {
		if (core.isString(content)) {
			content = dom.buildFragment(content);
		}

		Liferay.DOMTaskRunner.runTasks(content);

		return super.addContent(screenId, content);
	}
}

export default LiferaySurface;