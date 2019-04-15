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

import static java.util.Collections.singletonList;

import com.liferay.talend.configuration.LiferayInputMapperConfiguration;
import com.liferay.talend.service.ConnectionService;

import java.io.Serializable;

import java.util.List;

import org.talend.sdk.component.api.component.Icon;
import org.talend.sdk.component.api.component.Version;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.input.Assessor;
import org.talend.sdk.component.api.input.Emitter;
import org.talend.sdk.component.api.input.PartitionMapper;
import org.talend.sdk.component.api.input.PartitionSize;
import org.talend.sdk.component.api.input.Split;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 */
@Icon(custom = "LiferayInput", value = Icon.IconType.CUSTOM)
@PartitionMapper(family = "Liferay", name = "Input")
@Version(1)
public class LiferayInputMapper implements Serializable {

	public LiferayInputMapper(
		final ConnectionService connectionService,
		@Option("liferayInputMapperConfiguration") final
			LiferayInputMapperConfiguration liferayInputMapperConfiguration,
		final RecordBuilderFactory recordBuilderFactory) {

		_connectionService = connectionService;
		_liferayInputMapperConfiguration = liferayInputMapperConfiguration;
		_recordBuilderFactory = recordBuilderFactory;
	}

	@Emitter
	public LiferayInputEmitter createWorker() {
		return new LiferayInputEmitter(
			_connectionService, _liferayInputMapperConfiguration,
			_recordBuilderFactory);
	}

	@Assessor
	public long estimateSize() {
		return 1L;
	}

	@Split
	public List<LiferayInputMapper> split(@PartitionSize final long bundles) {
		return singletonList(this);
	}

	private final ConnectionService _connectionService;
	private final LiferayInputMapperConfiguration
		_liferayInputMapperConfiguration;
	private final RecordBuilderFactory _recordBuilderFactory;

}