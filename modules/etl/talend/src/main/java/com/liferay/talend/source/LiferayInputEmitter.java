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

package com.liferay.talend.source;

import com.liferay.talend.configuration.LiferayInputMapperConfiguration;
import com.liferay.talend.service.ConnectionService;

import java.io.Serializable;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import javax.json.JsonObject;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.input.Producer;
import org.talend.sdk.component.api.record.Record;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 */
public class LiferayInputEmitter implements Serializable {

	public LiferayInputEmitter(
		final ConnectionService connectionService,
		@Option("liferayInputMapperConfiguration") final
			LiferayInputMapperConfiguration liferayInputMapperConfiguration,
		final RecordBuilderFactory recordBuilderFactory) {

		_connectionService = connectionService;
		_liferayInputMapperConfiguration = liferayInputMapperConfiguration;
		_recordBuilderFactory = recordBuilderFactory;
	}

	@PostConstruct
	public void init() {
	}

	@Producer
	public Record next() {
		JsonObject data = _connectionService.getResponseJsonObject(
			_liferayInputMapperConfiguration.getInputDataSet());

		if (data != null) {
			if (_logger.isLoggable(Level.FINE)) {
				_logger.fine("Process data:" + data.toString());
			}
		}

		return null;
	}

	@PreDestroy
	public void release() {
	}

	private final ConnectionService _connectionService;
	private final LiferayInputMapperConfiguration
		_liferayInputMapperConfiguration;
	private final Logger _logger = Logger.getLogger("LiferayInputEmitter");
	private final RecordBuilderFactory _recordBuilderFactory;

}