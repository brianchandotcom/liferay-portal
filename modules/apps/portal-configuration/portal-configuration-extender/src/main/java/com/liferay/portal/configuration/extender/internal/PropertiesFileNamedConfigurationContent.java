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

import com.liferay.portal.kernel.util.PropertiesUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Dictionary;
import java.util.function.Supplier;

/**
 * @author Carlos Sierra Andrés
 */
public final class PropertiesFileNamedConfigurationContent
	implements NamedConfigurationContent {

	public PropertiesFileNamedConfigurationContent(
		String name, InputStream inputStream) {

		_name = name;
		_inputStream = inputStream;
	}

	@Override
	public ConfigurationDescription getConfigurationDescription() {
		String pid = null;

		String name = getName();

		int index = name.lastIndexOf('-');

		if (index > 0) {
			String factoryPid = name.substring(0, index);
			pid = name.substring(index + 1);

			return new FactoryConfigurationDescription(
				factoryPid, pid, new PropertiesSupplier(_inputStream));
		}

		pid = name;

		return new SingleConfigurationDescription(
			pid, new PropertiesSupplier(_inputStream));
	}

	@Override
	public InputStream getInputStream() {
		return _inputStream;
	}

	@Override
	public String getName() {
		return _name;
	}

	private final InputStream _inputStream;
	private final String _name;

	private class PropertiesSupplier
		implements Supplier<Dictionary<String, Object>> {

		public PropertiesSupplier(InputStream inputStream) {
			_inputStream = inputStream;
		}

		@Override
		public Dictionary<String, Object> get() {
			try {
				return _loadProperties();
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}

		private Dictionary<String, Object> _loadProperties()
			throws IOException {

			Dictionary<?, ?> properties = PropertiesUtil.load(
				_inputStream, "UTF-8");

			return (Dictionary<String, Object>)properties;
		}

		private final InputStream _inputStream;

	}

}