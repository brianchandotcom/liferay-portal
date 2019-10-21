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

import core from 'metal';
import dom from 'metal-dom';
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
