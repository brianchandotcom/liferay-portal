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

package com.liferay.portal.configuration.extender.internal;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import java.io.IOException;

import java.util.Dictionary;

/**
 * @author Carlos Sierra Andrés
 */
public class NamedConfigurationContent {

	public NamedConfigurationContent(
		String factoryPid, String pid,
		UnsafeSupplier<Dictionary<?, ?>, IOException> propertySupplier) {

		_factoryPid = factoryPid;
		_pid = pid;
		_propertySupplier = propertySupplier;
	}

	public String getFactoryPid() {
		return _factoryPid;
	}

	public String getPid() {
		return _pid;
	}

	@SuppressWarnings("unchecked")
	public Dictionary<String, Object> getProperties() throws IOException {
		Dictionary<?, ?> properties = _propertySupplier.get();

		return (Dictionary<String, Object>)properties;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{factoryPid=");
		sb.append(_factoryPid);
		sb.append(", pid=");
		sb.append(_pid);
		sb.append("}");

		return sb.toString();
	}

	private final String _factoryPid;
	private final String _pid;
	private final UnsafeSupplier<Dictionary<?, ?>, IOException>
		_propertySupplier;

}