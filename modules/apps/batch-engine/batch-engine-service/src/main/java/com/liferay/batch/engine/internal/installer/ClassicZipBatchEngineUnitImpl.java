/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.installer;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.batch.engine.unit.BatchEngineUnit;
import com.liferay.batch.engine.unit.BatchEngineUnitConfiguration;

import java.io.IOException;
import java.io.InputStream;

import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Igor Beslic
 */
public class ClassicZipBatchEngineUnitImpl implements BatchEngineUnit {

	public ClassicZipBatchEngineUnitImpl(
		ZipFile zipFile, ZipEntry... zipEntries) {

		_zipFile = zipFile;

		if ((zipEntries == null) || (zipEntries.length > 2)) {
			return;
		}

		for (ZipEntry zipEntry : zipEntries) {
			if (_isBatchEngineConfiguration(zipEntry.getName())) {
				_configurationZipEntry = zipEntry;

				continue;
			}

			_dataZipEntry = zipEntry;
		}
	}

	@Override
	public BatchEngineUnitConfiguration getBatchEngineUnitConfiguration()
		throws IOException {

		try (InputStream inputStream = _zipFile.getInputStream(
				_configurationZipEntry)) {

			ObjectMapper objectMapper = new ObjectMapper();

			return objectMapper.readValue(
				inputStream, BatchEngineUnitConfiguration.class);
		}
	}

	@Override
	public InputStream getConfigurationInputStream() throws IOException {
		return _zipFile.getInputStream(_configurationZipEntry);
	}

	@Override
	public String getDataFileName() {
		return _dataZipEntry.getName();
	}

	@Override
	public InputStream getDataInputStream() throws IOException {
		return _zipFile.getInputStream(_dataZipEntry);
	}

	@Override
	public String getFileName() {
		return _zipFile.getName();
	}

	@Override
	public boolean isValid() {
		if ((_configurationZipEntry == null) || (_dataZipEntry == null)) {
			return false;
		}

		return true;
	}

	private boolean _isBatchEngineConfiguration(String zipEntryName) {
		if (Objects.equals(zipEntryName, "batch-engine.json") ||
			zipEntryName.endsWith("/batch-engine.json")) {

			return true;
		}

		return false;
	}

	private ZipEntry _configurationZipEntry;
	private ZipEntry _dataZipEntry;
	private final ZipFile _zipFile;

}