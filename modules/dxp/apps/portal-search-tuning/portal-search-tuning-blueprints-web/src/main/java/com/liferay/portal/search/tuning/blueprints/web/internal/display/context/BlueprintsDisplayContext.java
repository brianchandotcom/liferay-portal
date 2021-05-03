/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.web.internal.display.context;

import java.util.Map;

/**
 * @author Kevin Tan
 */
public class BlueprintsDisplayContext {

	public BlueprintsDisplayContext(
		Map<String, Object> data, boolean configured) {

		_data = data;
		_configured = configured;
	}

	public Map<String, Object> getData() {
		return _data;
	}

	public boolean isConfigured() {
		return _configured;
	}

	private final boolean _configured;
	private final Map<String, Object> _data;

}