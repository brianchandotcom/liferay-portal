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

import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.ModuleNameUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistryUpdate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

	public Collection<JSModuleRegistration> getJSModuleRegistrations(
		JSPackage jsPackage) {

		Collection<JSModuleRegistration> jsModuleRegistrations =
			_jsModuleRegistrationsMap.get(jsPackage.getId());

		if (jsModuleRegistrations == null) {
			return Collections.emptyList();
		}

		return jsModuleRegistrations;
	}

	public JSModuleUpdate getJSModuleUpdate(String id) {
		return _jsModuleUpdates.get(id);
	}

	public boolean isUnregistered(String id) {
		return _jsModuleUnregistrations.contains(id);
	}

	@Override
	public void registerJSModule(
		JSPackage jsPackage, String moduleName, Collection<String> dependencies,
		String js, String map) {

		_failIfFinished();

		String id = ModuleNameUtil.getModuleId(jsPackage, moduleName);

		Collection<JSModuleRegistration> jsModuleRegistrations =
			_jsModuleRegistrationsMap.get(jsPackage.getId());

		if (jsModuleRegistrations == null) {
			jsModuleRegistrations = new ArrayList<>();

			_jsModuleRegistrationsMap.put(
				jsPackage.getId(), jsModuleRegistrations);
		}

		jsModuleRegistrations.add(
			new JSModuleRegistration(id, moduleName, dependencies, js, map));
	}

	@Override
	public void unregisterJSModule(JSModule jsModule) {
		_failIfFinished();

		_jsModuleUnregistrations.add(jsModule.getId());
	}

	@Override
	public void updateJSModule(
		JSModule jsModule, Collection<String> dependencies, String js,
		String map) {

		_failIfFinished();

		_jsModuleUpdates.put(
			jsModule.getId(),
			new JSModuleUpdate(jsModule.getName(), dependencies, js, map));
	}

	public static class JSModuleRegistration {

		public JSModuleRegistration(
			String id, String moduleName, Collection<String> dependencies,
			String js, String map) {

			_id = id;
			_moduleName = moduleName;
			_dependencies = new ArrayList<>(dependencies);
			_js = js;
			_map = map;
		}

		public Collection<String> getDependencies() {
			return _dependencies;
		}

		public String getId() {
			return _id;
		}

		public String getJS() {
			return _js;
		}

		public String getMap() {
			return _map;
		}

		public String getModuleName() {
			return _moduleName;
		}

		private final Collection<String> _dependencies;
		private final String _id;
		private final String _js;
		private final String _map;
		private final String _moduleName;

	}

	public static class JSModuleUpdate {

		public JSModuleUpdate(
			String moduleName, Collection<String> dependencies, String js,
			String map) {

			_moduleName = moduleName;
			_dependencies = new ArrayList<>(dependencies);
			_js = js;
			_map = map;
		}

		public Collection<String> getDependencies() {
			return _dependencies;
		}

		public String getJS() {
			return _js;
		}

		public String getMap() {
			return _map;
		}

		public String getModuleName() {
			return _moduleName;
		}

		private Collection<String> _dependencies;
		private String _js;
		private String _map;
		private String _moduleName;

	}

	private synchronized void _failIfFinished() {
		if (_finished) {
			throw new IllegalStateException("Update has been finished already");
		}
	}

	private boolean _finished;
	private final Map<String, Collection<JSModuleRegistration>>
		_jsModuleRegistrationsMap = new HashMap<>();
	private final Set<String> _jsModuleUnregistrations = new HashSet<>();
	private final Map<String, JSModuleUpdate> _jsModuleUpdates =
		new HashMap<>();
	private final NPMRegistryImpl _npmRegistryImpl;

}