/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	private final List<String> _customJsps = new ArrayList<>();
	private final URLContainer _urlContainer;

}