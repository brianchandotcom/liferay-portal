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

package com.liferay.portal.configurator.extender;

import com.liferay.portal.kernel.util.Supplier;

import java.util.Dictionary;

/**
* @author Carlos Sierra Andrés
*/
public final class SingleConfigurationDescription
	extends ConfigurationDescription {

	public SingleConfigurationDescription(
		String pid, Supplier<Dictionary<String, Object>> propertiesSupplier) {

		_pid = pid;
		_propertiesSupplier = propertiesSupplier;
	}

	public String getPid() {
		return _pid;
	}

	public Supplier<Dictionary<String, Object>> getPropertiesSupplier() {
		return _propertiesSupplier;
	}

	@Override
	public String toString() {
		return "SingleConfigurationDescriptor{" +
			"pid='" + _pid + '\'' +
			'}';
	}

	private final String _pid;
	private final Supplier<Dictionary<String, Object>> _propertiesSupplier;

}