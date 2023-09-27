/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import EventScreen from './EventScreen';

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
