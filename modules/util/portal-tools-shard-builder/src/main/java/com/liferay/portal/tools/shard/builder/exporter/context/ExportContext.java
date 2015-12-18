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

package com.liferay.portal.tools.shard.builder.exporter.context;

import java.util.List;

/**
 * @author Manuel de la Peña
 */
public class ExportContext {

	public ExportContext(
		List<Long> companyIds, String outputDirName, String schemaName) {

		_companyIds = companyIds;
		_outputDirName = outputDirName;
		_schemaName = schemaName;
	}

	public List<Long> getCompanyIds() {
		return _companyIds;
	}

	public String getOutputDirName() {
		return _outputDirName;
	}

	public String getSchemaName() {
		return _schemaName;
	}

	private final List<Long> _companyIds;
	private final String _outputDirName;
	private final String _schemaName;

}