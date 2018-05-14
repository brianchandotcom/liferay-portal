'use strict';

import core from 'metal/lib/core';
import dom from 'metal-dom/lib/dom';
import Surface from 'senna/lib/surface/Surface';

/**
 * LiferaySurface
 *
 * Inherits from Senna's Surface and calls {@link https://github.com/liferay/liferay-portal/blob/7.1.x/modules/apps/frontend-js/frontend-js-web/src/main/resources/META-INF/resources/liferay/dom_task_runner.js|Liferay.DOMTaskRunner}
 * when content is added to a Surface
 */

class LiferaySurface extends Surface {

	/**
	 * @inheritDoc
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