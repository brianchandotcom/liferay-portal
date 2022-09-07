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

package com.liferay.portal.test.function;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;

import com.liferay.petra.function.UnsafeSupplier;

/**
 * @author Raymond Augé
 */
public class ConfigurationHolder extends CloseableHolder<Configuration> {

	public ConfigurationHolder(
			UnsafeSupplier<Configuration, Exception> onInitUnsafeSupplier)
		throws Exception {

		super(
			configuration -> configuration.delete(), onInitUnsafeSupplier);
	}

	public Dictionary<String, Object> getProperties() throws Exception {
		Configuration configuration = get();

		return configuration.getProcessedProperties(null);
	}

	public void update(Dictionary<String, Object> properties)
		throws Exception {

		Configuration configuration = get();

		configuration.update(properties);
	}

}