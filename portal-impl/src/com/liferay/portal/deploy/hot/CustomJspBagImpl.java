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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.kernel.url.URLContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Peter Fellwock
 * @author Raymond Augé
 */
public class CustomJspBagImpl implements CustomJspBag {

	public CustomJspBagImpl(
		URLContainer urlContainer, String customJspDir,
		boolean customJspGlobal) {

		_urlContainer = urlContainer;
		_customJspDir = customJspDir;
		_customJspGlobal = customJspGlobal;

		_customJsps = new ArrayList<>();
	}

	@Override
	public String getCustomJspDir() {
		return _customJspDir;
	}

	@Override
	public List<String> getCustomJsps() {
		return _customJsps;
	}

	@Override
	public URLContainer getURLContainer() {
		return _urlContainer;
	}

	@Override
	public boolean isCustomJspGlobal() {
		return _customJspGlobal;
	}

	private final String _customJspDir;
	private final boolean _customJspGlobal;
	private final List<String> _customJsps;
	private final URLContainer _urlContainer;

}