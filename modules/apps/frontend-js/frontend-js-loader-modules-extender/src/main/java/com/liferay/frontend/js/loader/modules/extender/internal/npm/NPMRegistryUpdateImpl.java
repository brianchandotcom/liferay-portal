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

package com.liferay.frontend.js.loader.modules.extender.internal.npm;

import com.liferay.frontend.js.loader.modules.extender.internal.npm.dynamic.DynamicJSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.ModifiableJSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistryUpdate;

import java.util.Collection;

/**
 * @author Iván Zaera
 */
public class NPMRegistryUpdateImpl implements NPMRegistryUpdate {

	public NPMRegistryUpdateImpl(NPMRegistryImpl npmRegistryImpl) {
		_npmRegistryImpl = npmRegistryImpl;
	}

	@Override
	public void finish() {
		_failIfFinished();

		_finished = true;

		_npmRegistryImpl.finishUpdate(this);
	}

	@Override
	public JSModule registerJSModule(
		JSPackage jsPackage, String moduleName, Collection<String> dependencies,
		String js, String map) {

		_failIfFinished();

		if (!(jsPackage instanceof ModifiableJSPackage)) {
			throw new IllegalArgumentException(
				"Invalid JS package type " + jsPackage.getClass());
		}

		ModifiableJSPackage modifiableJSPackage =
			(ModifiableJSPackage)jsPackage;

		JSModule jsModule = new DynamicJSModule(
			modifiableJSPackage, moduleName, dependencies, js, map);

		modifiableJSPackage.addJSModule(jsModule);

		return jsModule;
	}

	@Override
	public void unregisterJSModule(JSModule jsModule) {
		_failIfFinished();

		JSPackage jsPackage = jsModule.getJSPackage();

		if (!(jsPackage instanceof ModifiableJSPackage)) {
			throw new IllegalArgumentException(
				"Invalid JS package type " + jsPackage.getClass());
		}

		ModifiableJSPackage modifiableJSPackage =
			(ModifiableJSPackage)jsPackage;

		modifiableJSPackage.removeJSModule(jsModule);
	}

	@Override
	public void updateJSModule(
		JSModule jsModule, Collection<String> dependencies, String js,
		String map) {

		_failIfFinished();

		JSPackage jsPackage = jsModule.getJSPackage();

		if (!(jsPackage instanceof ModifiableJSPackage)) {
			throw new IllegalArgumentException(
				"Invalid JS package type " + jsPackage.getClass());
		}

		ModifiableJSPackage modifiableJSPackage =
			(ModifiableJSPackage)jsPackage;

		modifiableJSPackage.replaceJSModule(
			new DynamicJSModule(
				modifiableJSPackage, jsModule.getName(), dependencies, js,
				map));
	}

	private synchronized void _failIfFinished() {
		if (_finished) {
			throw new IllegalStateException("Update has been finished already");
		}
	}

	private boolean _finished;
	private final NPMRegistryImpl _npmRegistryImpl;

}