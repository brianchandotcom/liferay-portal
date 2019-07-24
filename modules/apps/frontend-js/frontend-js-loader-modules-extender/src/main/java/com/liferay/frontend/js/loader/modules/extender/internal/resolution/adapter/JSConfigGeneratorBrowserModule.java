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