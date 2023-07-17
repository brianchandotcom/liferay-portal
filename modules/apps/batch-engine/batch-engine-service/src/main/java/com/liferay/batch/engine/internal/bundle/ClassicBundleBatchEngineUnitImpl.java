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

package com.liferay.batch.engine.internal.bundle;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.batch.engine.unit.BatchEngineUnit;
import com.liferay.batch.engine.unit.BatchEngineUnitConfiguration;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.List;
import java.util.Objects;

import org.osgi.framework.Bundle;

/**
 * @author Raymond Augé
 * @author Igor Beslic
 */
public class ClassicBundleBatchEngineUnitImpl implements BatchEngineUnit {

	public ClassicBundleBatchEngineUnitImpl(Bundle bundle, List<URL> urls) {
		_bundle = bundle;

		if ((urls == null) || (urls.size() > 2)) {
			return;
		}

		for (URL url : urls) {
			if (_isBatchEngineConfiguration(url)) {
				_configurationURL = url;

				continue;
			}

			_dataURL = url;
		}
	}

	@Override
	public BatchEngineUnitConfiguration getBatchEngineUnitConfiguration()
		throws IOException {

		try (InputStream inputStream = _configurationURL.openStream()) {
			ObjectMapper objectMapper = new ObjectMapper();

			return objectMapper.readValue(
				inputStream, BatchEngineUnitConfiguration.class);
		}
	}

	@Override
	public InputStream getConfigurationInputStream() throws IOException {
		return _configurationURL.openStream();
	}

	@Override
	public String getDataFileName() {
		return _dataURL.getPath();
	}

	@Override
	public InputStream getDataInputStream() throws IOException {
		return _dataURL.openStream();
	}

	@Override
	public String getFileName() {
		return _bundle.toString();
	}

	@Override
	public boolean isValid() {
		if ((_configurationURL == null) || (_dataURL == null)) {
			return false;
		}

		return true;
	}

	private boolean _isBatchEngineConfiguration(URL url) {
		if (url == null) {
			return false;
		}

		String bundlePath = url.getPath();

		if (Objects.equals(bundlePath, "batch-engine.json") ||
			bundlePath.endsWith("/batch-engine.json")) {

			return true;
		}

		return false;
	}

	private final Bundle _bundle;
	private URL _configurationURL;
	private URL _dataURL;

}