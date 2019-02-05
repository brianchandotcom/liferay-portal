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

package com.liferay.frontend.js.loader.modules.extender.internal.resolution.descriptor;

import com.liferay.frontend.js.loader.modules.extender.internal.config.generator.JSConfigGeneratorModule;
import com.liferay.frontend.js.loader.modules.extender.internal.config.generator.JSConfigGeneratorPackage;
import com.liferay.frontend.js.loader.modules.extender.internal.resolution.ModuleDescriptor;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.Collection;
import java.util.Map;

/**
 * @author Rodolfo Roza Miranda
 */
public class JSConfigGeneratorModuleDescriptor implements ModuleDescriptor {

	public JSConfigGeneratorModuleDescriptor(
		JSConfigGeneratorModule jsConfigGeneratorModule) {

		_jsConfigGeneratorModule = jsConfigGeneratorModule;

		JSConfigGeneratorPackage jsConfigGeneratorPackage =
			_jsConfigGeneratorModule.getJSConfigGeneratorPackage();

		_name = StringBundler.concat(
			jsConfigGeneratorPackage.getName(), StringPool.AT,
			jsConfigGeneratorPackage.getVersion(), StringPool.SLASH,
			_jsConfigGeneratorModule.getName());
	}

	@Override
	public Collection<String> getDependencies() {
		return _jsConfigGeneratorModule.getDependencies();
	}

	@Override
	public Map<String, String> getMappings() {
		return null;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getPath() {
		return _jsConfigGeneratorModule.getResolvedURL();
	}

	private final JSConfigGeneratorModule _jsConfigGeneratorModule;
	private final String _name;

}