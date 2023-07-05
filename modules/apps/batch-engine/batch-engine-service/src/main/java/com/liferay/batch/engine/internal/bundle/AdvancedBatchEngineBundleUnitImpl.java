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

import com.liferay.batch.engine.internal.json.AdvancedJSONReader;
import com.liferay.batch.engine.unit.BatchEngineUnit;
import com.liferay.batch.engine.unit.BatchEngineUnitConfiguration;
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
public class AdvancedBatchEngineBundleUnitImpl implements BatchEngineUnit {

	public AdvancedBatchEngineBundleUnitImpl(Bundle bundle, URL url) {
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

			AdvancedJSONReader advancedJSONReader = new AdvancedJSONReader(
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
			AdvancedJSONReader advancedJSONReader = new AdvancedJSONReader(
				inputStream);

			return advancedJSONReader.hasKey("items");
		}
		catch (IOException ioException) {
			_log.error(
				"Unable to get data in file " + _url.getPath(), ioException);
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AdvancedBatchEngineBundleUnitImpl.class);

	private final Bundle _bundle;
	private final URL _url;

}