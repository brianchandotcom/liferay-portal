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

package com.liferay.portal.tools.data.partitioning.sql.builder.exporter.context;

import java.util.List;
import java.util.Properties;

/**
 * @author Manuel de la Peña
 */
public class ExportContext {

	public ExportContext(
		List<Long> companyIds, String outputDirName, Properties properties,
		String schemaName, boolean writeFile) {

		this(
			schemaName, companyIds, outputDirName, properties, schemaName,
			writeFile);
	}

	public ExportContext(
		String catalogName, List<Long> companyIds, String outputDirName,
		Properties properties, String schemaName, boolean writeFile) {

		_catalogName = catalogName;
		_companyIds = companyIds;
		_outputDirName = outputDirName;
		_properties = properties;
		_schemaName = schemaName;
		_writeFile = writeFile;
	}

	public String getCatalogName() {
		return _catalogName;
	}

	public List<Long> getCompanyIds() {
		return _companyIds;
	}

	public String getOutputDirName() {
		return _outputDirName;
	}

	public Properties getProperties() {
		return _properties;
	}

	public String getSchemaName() {
		return _schemaName;
	}

	public boolean isWriteFile() {
		return _writeFile;
	}

	private final String _catalogName;
	private final List<Long> _companyIds;
	private final String _outputDirName;
	private final Properties _properties;
	private final String _schemaName;
	private final boolean _writeFile;

}