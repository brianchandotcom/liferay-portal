/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.bundle;

import com.liferay.batch.engine.internal.json.AdvancedJSONReader;
import com.liferay.batch.engine.unit.BatchEngineUnitConfiguration;
import com.liferay.batch.engine.unit.BatchEngineUnitMetaInfo;
import com.liferay.batch.engine.unit.BundleBatchEngineUnit;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import org.osgi.framework.Bundle;

/**
 * @author Raymond Augé
 * @author Igor Beslic
 */
public class AdvancedBundleBatchEngineUnitImpl
	implements BundleBatchEngineUnit {

	public AdvancedBundleBatchEngineUnitImpl(Bundle bundle, URL url) {
		_bundle = bundle;
		_url = url;
	}

	@Override
	public BatchEngineUnitConfiguration getBatchEngineUnitConfiguration()
		throws IOException {

		try (InputStream inputStream = _url.openStream()) {
			AdvancedJSONReader<BatchEngineUnitConfiguration>
				advancedJSONReader = new AdvancedJSONReader<>(inputStream);

			return advancedJSONReader.getObject(
				"configuration", BatchEngineUnitConfiguration.class);
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
		return _url.openStream();
	}

	@Override
	public String getDataFileName() {
		return _url.getPath();
	}

	@Override
	public InputStream getDataInputStream() throws IOException {
		try (InputStream inputStream = _url.openStream()) {
			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			AdvancedJSONReader<?> advancedJSONReader = new AdvancedJSONReader<>(
				inputStream);

			advancedJSONReader.transferJSONArray(
				"items", byteArrayOutputStream);

			return new ByteArrayInputStream(
				byteArrayOutputStream.toByteArray());
		}
	}

	@Override
	public String getFileName() {
		return _bundle.toString();
	}

	@Override
	public boolean isValid() {
		if (_url == null) {
			return false;
		}

		try (InputStream inputStream = _url.openStream()) {
			AdvancedJSONReader<?> advancedJSONReader = new AdvancedJSONReader<>(
				inputStream);

			return advancedJSONReader.hasKey("items");
		}
		catch (IOException ioException) {
			_log.error(
				"Unable to get data in file " + _url.getPath(), ioException);
		}

		return false;
	}

	public void setBatchEngineUnitMetaInfo(
		BatchEngineUnitMetaInfo batchEngineUnitMetaInfo) {

		_batchEngineUnitMetaInfo = batchEngineUnitMetaInfo;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AdvancedBundleBatchEngineUnitImpl.class);

	private BatchEngineUnitMetaInfo _batchEngineUnitMetaInfo;
	private final Bundle _bundle;
	private final URL _url;

}