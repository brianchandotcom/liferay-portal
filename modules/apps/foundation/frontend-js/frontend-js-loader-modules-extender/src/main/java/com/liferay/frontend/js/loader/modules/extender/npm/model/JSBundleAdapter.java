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

package com.liferay.frontend.js.loader.modules.extender.npm.model;

import com.liferay.frontend.js.loader.modules.extender.npm.JSBundle;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A basic incomplete implementation of {@link JSBundle} that can be reused by
 * real implementations.
 * @author Iván Zaera
 */
public abstract class JSBundleAdapter implements JSBundle {

	/**
	 * @param id the OSGi bundle id
	 * @param name the OSGi name for this bundle
	 * @param version the OSGi version of this bundle
	 */
	public JSBundleAdapter(String id, String name, String version) {
		_id = id;
		_name = name;
		_version = version;
	}

	/**
	 * Add an NPM package description to this bundle.
	 */
	public void addJSPackage(JSPackage jsPackage) {
		_jsPackages.add(jsPackage);
	}

	@Override
	public String getId() {
		return _id;
	}

	@Override
	public Collection<JSPackage> getJSPackages() {
		return _jsPackages;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getVersion() {
		return _version;
	}

	private final String _id;
	private final Collection<JSPackage> _jsPackages = new ArrayList<>();
	private final String _name;
	private final String _version;

}