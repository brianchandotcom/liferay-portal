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

import com.liferay.portal.kernel.util.CharPool;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

/**
 * @author Carlos Sierra Andrés
 */
public final class PropertiesFileConfigurationContent
	implements ConfigurationContent {

	public PropertiesFileConfigurationContent(
		String factoryPid, String pid, InputStream inputStream) {

		_factoryPid = factoryPid;
		_pid = pid;
		_inputStream = inputStream;
	}

	public PropertiesFileConfigurationContent(URL url) {
		String fileName = url.getFile();

		if (!fileName.isEmpty() && (fileName.charAt(0) == CharPool.SLASH)) {
			fileName = fileName.substring(1);
		}

		int index = fileName.lastIndexOf(CharPool.SLASH);

		if (index > 0) {
			fileName = fileName.substring(index + 1);
		}

		String factoryPid = null;
		String pid = fileName;

		index = fileName.lastIndexOf(CharPool.DASH);

		if (index > 0) {
			factoryPid = fileName.substring(0, index);
			pid = fileName.substring(index + 1);
		}

		_factoryPid = factoryPid;
		_pid = pid;

		try {
			_inputStream = url.openStream();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	@Override
	public String getFactoryPid() {
		return _factoryPid;
	}

	@Override
	public InputStream getInputStream() {
		return _inputStream;
	}

	@Override
	public String getPid() {
		return _pid;
	}

	private final String _factoryPid;
	private final InputStream _inputStream;
	private final String _pid;

}