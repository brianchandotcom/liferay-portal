/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.loader.modules.extender.internal.resolution.adapter;

import com.liferay.frontend.js.loader.modules.extender.internal.config.generator.JSConfigGeneratorModule;
import com.liferay.frontend.js.loader.modules.extender.internal.resolution.BrowserModule;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Collection;
import java.util.Map;

/**
 * This is the browser's domain equivalent of {@link JSConfigGeneratorModule} in
 * server's domain.
 *
 * @author Rodolfo Roza Miranda
 */
public class JSConfigGeneratorBrowserModule implements BrowserModule {

	public JSConfigGeneratorBrowserModule(
		JSConfigGeneratorModule jsConfigGeneratorModule) {

		_jsConfigGeneratorModule = jsConfigGeneratorModule;
	}

	@Override
	public Collection<String> getDependencies() {
		return _jsConfigGeneratorModule.getDependencies();
	}

	@Override
	public Map<String, String> getDependenciesMap() {
		return null;
	}

	@Override
	public JSONObject getFlagsJSONObject() {
		return null;
	}

	@Override
	public String getName() {
		return _jsConfigGeneratorModule.getId();
	}

	@Override
	public String getPath() {
		return _jsConfigGeneratorModule.getURL();
	}

	private final JSConfigGeneratorModule _jsConfigGeneratorModule;

}