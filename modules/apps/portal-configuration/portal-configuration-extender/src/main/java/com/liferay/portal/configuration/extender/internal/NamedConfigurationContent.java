/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		UnsafeSupplier<Dictionary<?, ?>, IOException> propertyUnsafeSupplier) {

		_factoryPid = factoryPid;
		_pid = pid;
		_propertyUnsafeSupplier = propertyUnsafeSupplier;
	}

	public String getFactoryPid() {
		return _factoryPid;
	}

	public String getPid() {
		return _pid;
	}

	@SuppressWarnings("unchecked")
	public Dictionary<String, Object> getProperties() throws IOException {
		Dictionary<?, ?> properties = _propertyUnsafeSupplier.get();

		return (Dictionary<String, Object>)properties;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{factoryPid=", _factoryPid, ", pid=", _pid, "}");
	}

	private final String _factoryPid;
	private final String _pid;
	private final UnsafeSupplier<Dictionary<?, ?>, IOException>
		_propertyUnsafeSupplier;

}