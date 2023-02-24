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

package com.liferay.batch.engine.internal.auto.deploy;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Igor Beslic
 */
public class ClassicBatchEngineZipUnitImpl<T> implements BatchEngineZipUnit<T> {

	public ClassicBatchEngineZipUnitImpl(
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
	public T getBatchEngineConfiguration(Class<T> clazz) throws IOException {
		try (InputStream inputStream = _zipFile.getInputStream(
				_configurationZipEntry)) {

			ObjectMapper objectMapper = new ObjectMapper();

			return objectMapper.readValue(inputStream, clazz);
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
	public String getZipFileName() {
		return _zipFile.getName();
	}

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