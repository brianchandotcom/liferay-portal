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
import com.liferay.talend.service.TalendService;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.input.Producer;
import org.talend.sdk.component.api.meta.Documentation;
import org.talend.sdk.component.api.record.Record;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;

/**
 * @author Zoltán Takács
 */
@Documentation("TODO fill the documentation for this source")
public class LiferayInputEmitter implements Serializable {

	public LiferayInputEmitter(
		@Option("configuration") final
			LiferayInputMapperConfiguration configuration,
		final TalendService service,
		final RecordBuilderFactory builderFactory) {

		this.configuration = configuration;
		this.service = service;
		this.builderFactory = builderFactory;
	}

	@PostConstruct
	public void init() {

		// this method will be executed once for the whole component execution,
		// this is where you can establish a connection for instance

	}

	@Producer
	public Record next() {

		// this is the method allowing you to go through the dataset associated
		// to the component configuration

		//

		// return null means the dataset has no more data to go through
		// you can use the builderFactory to create a new Record.

		return null;
	}

	@PreDestroy
	public void release() {

		// this is the symmetric method of the init() one,
		// release potential connections you created or data you cached

	}

	private final RecordBuilderFactory builderFactory;
	private final LiferayInputMapperConfiguration configuration;
	private final TalendService service;

}