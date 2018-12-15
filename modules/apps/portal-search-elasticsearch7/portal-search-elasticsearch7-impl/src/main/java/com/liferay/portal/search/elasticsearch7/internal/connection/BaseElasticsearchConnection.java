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

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author Michael C. Han
 */
public abstract class BaseElasticsearchConnection
	implements ElasticsearchConnection {

	@Override
	public void close() {
		if (_restHighLevelClient == null) {
			return;
		}

		try {
			_restHighLevelClient.close();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		_restHighLevelClient = null;
	}

	@Override
	public void connect() {
		Log log = LogFactory.getLog(RestClient.class);

		if (log instanceof Log4JLogger) {
			Log4JLogger log4JLogger = (Log4JLogger)log;

			Logger logger = log4JLogger.getLogger();

			logger.setLevel(
				Level.toLevel(
					elasticsearchConfiguration.restClientLoggerLevel()));
		}

		_restHighLevelClient = createRestHighLevelClient();
	}

	@Override
	public RestHighLevelClient getRestHighLevelClient() {
		return _restHighLevelClient;
	}

	public boolean isConnected() {
		if (_restHighLevelClient != null) {
			return true;
		}

		return false;
	}

	protected abstract RestHighLevelClient createRestHighLevelClient();

	protected volatile ElasticsearchConfiguration elasticsearchConfiguration;

	private RestHighLevelClient _restHighLevelClient;

}