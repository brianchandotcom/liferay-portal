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

package com.liferay.frontend.data.set.views.web.internal.resource;

/**
 * @author Marko Cikos
 */
public class FDSHeadlessResource {

	public FDSHeadlessResource(
		String bundleLabel, String entityClassName, String name,
		String version) {

		_bundleLabel = bundleLabel;
		_entityClassName = entityClassName;
		_name = name;
		_version = version;
	}

	public String getBundleLabel() {
		return _bundleLabel;
	}

	public String getEntityClassName() {
		return _entityClassName;
	}

	public String getName() {
		return _name;
	}

	public String getVersion() {
		return _version;
	}

	private final String _bundleLabel;
	private final String _entityClassName;
	private final String _name;
	private final String _version;

}