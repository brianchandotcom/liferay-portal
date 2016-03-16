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

package com.liferay.portal.tools.data.partitioning.sql.builder.exporter;

import com.liferay.portal.tools.data.partitioning.sql.builder.exporter.exception.DBProviderNotAvailableException;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Manuel de la Peña
 */
public class ShardExporterFactory {

	public static ShardExporter getShardExporter() {
		ServiceLoader<ShardExporter> serviceLoader = ServiceLoader.load(
			ShardExporter.class);

		List<ShardExporter> shardExporters = new ArrayList<>();

		for (ShardExporter shardExporter : serviceLoader) {
			shardExporters.add(shardExporter);
		}

		_logger.info(shardExporters.size() + " exporters available");

		for (ShardExporter shardExporter : shardExporters) {
			_logger.info("Shard exporter " + shardExporter);
		}

		if (shardExporters.isEmpty() || (shardExporters.size() > 1)) {
			throw new DBProviderNotAvailableException(
				shardExporters.size() + " exporters available");
		}

		return shardExporters.get(0);
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		ShardExporterFactory.class);

}