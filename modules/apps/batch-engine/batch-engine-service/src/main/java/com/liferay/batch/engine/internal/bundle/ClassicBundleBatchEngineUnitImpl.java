/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.bundle;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.batch.engine.unit.BatchEngineUnitConfiguration;
import com.liferay.batch.engine.unit.BatchEngineUnitMetaInfo;
import com.liferay.batch.engine.unit.BundleBatchEngineUnit;

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
public class ClassicBundleBatchEngineUnitImpl implements BundleBatchEngineUnit {

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
	public BatchEngineUnitMetaInfo getBatchEngineUnitMetaInfo() {
		return _batchEngineUnitMetaInfo;
	}

	@Override
	public Bundle getBundle() {
		return _bundle;
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

	public void setBatchEngineUnitMetaInfo(
		BatchEngineUnitMetaInfo batchEngineUnitMetaInfo) {

		_batchEngineUnitMetaInfo = batchEngineUnitMetaInfo;
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

	private BatchEngineUnitMetaInfo _batchEngineUnitMetaInfo;
	private final Bundle _bundle;
	private URL _configurationURL;
	private URL _dataURL;

}