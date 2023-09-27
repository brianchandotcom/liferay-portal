/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.installer;

import com.liferay.batch.engine.internal.json.AdvancedJSONReader;
import com.liferay.batch.engine.unit.BatchEngineUnit;
import com.liferay.batch.engine.unit.BatchEngineUnitConfiguration;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Igor Beslic
 */
public class AdvancedZipBatchEngineUnitImpl implements BatchEngineUnit {

	public AdvancedZipBatchEngineUnitImpl(ZipFile zipFile, ZipEntry zipEntry) {
		_zipFile = zipFile;
		_zipEntry = zipEntry;
	}

	@Override
	public BatchEngineUnitConfiguration getBatchEngineUnitConfiguration()
		throws IOException {

		try (InputStream inputStream = _zipFile.getInputStream(_zipEntry)) {
			AdvancedJSONReader<BatchEngineUnitConfiguration>
				advancedJSONReader = new AdvancedJSONReader<>(inputStream);

			return advancedJSONReader.getObject(
				"configuration", BatchEngineUnitConfiguration.class);
		}
	}

	@Override
	public InputStream getConfigurationInputStream() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getDataFileName() {
		return _zipEntry.getName();
	}

	@Override
	public InputStream getDataInputStream() throws IOException {
		try (InputStream inputStream = _zipFile.getInputStream(_zipEntry)) {
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
		return _zipFile.getName();
	}

	@Override
	public boolean isValid() {
		if (_zipEntry == null) {
			return false;
		}

		try (InputStream inputStream = _zipFile.getInputStream(_zipEntry)) {
			AdvancedJSONReader<?> advancedJSONReader = new AdvancedJSONReader<>(
				inputStream);

			return advancedJSONReader.hasKey("items");
		}
		catch (IOException ioException) {
			_log.error(
				"Unable to get data in file " + _zipEntry.getName(),
				ioException);
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AdvancedZipBatchEngineUnitImpl.class);

	private final ZipEntry _zipEntry;
	private final ZipFile _zipFile;

}