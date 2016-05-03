'use strict';

import core from 'metal/src/core';
import dom from 'metal-dom/src/dom';
import Surface from 'senna/src/surface/Surface';

class LiferaySurface extends Surface {
	addContent(screenId, content) {
		if (core.isString(content)) {
			content = dom.buildFragment(content);
		}

		Liferay.DOMTaskRunner.runTasks(content);

		return super.addContent(screenId, content);
	}
}

export default LiferaySurface;