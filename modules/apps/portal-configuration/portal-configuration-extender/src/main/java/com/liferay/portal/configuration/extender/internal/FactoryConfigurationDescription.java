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

import com.liferay.petra.string.StringBundler;

import java.util.Dictionary;
import java.util.function.Supplier;

/**
 * @author Carlos Sierra Andrés
 */
public final class FactoryConfigurationDescription
	implements ConfigurationDescription {

	public FactoryConfigurationDescription(
		String factoryPid, String pid,
		Supplier<Dictionary<String, Object>> propertiesSupplier) {

		_factoryPid = factoryPid;
		_pid = pid;
		_propertiesSupplier = propertiesSupplier;
	}

	@Override
	public String getFactoryPid() {
		return _factoryPid;
	}

	@Override
	public String getPid() {
		return _pid;
	}

	@Override
	public Supplier<Dictionary<String, Object>> getPropertiesSupplier() {
		return _propertiesSupplier;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{factoryPid=");
		sb.append(_factoryPid);
		sb.append(", pid=");
		sb.append(_pid);
		sb.append(", propertiesSupplier=");
		sb.append(_propertiesSupplier);
		sb.append("}");

		return sb.toString();
	}

	private final String _factoryPid;
	private final String _pid;
	private final Supplier<Dictionary<String, Object>> _propertiesSupplier;

}