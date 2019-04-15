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

package com.liferay.talend.sink;

import com.liferay.talend.configuration.LiferayOutputConfiguration;
import com.liferay.talend.service.TalendService;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.talend.sdk.component.api.component.Icon;
import org.talend.sdk.component.api.component.Version;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.processor.AfterGroup;
import org.talend.sdk.component.api.processor.BeforeGroup;
import org.talend.sdk.component.api.processor.ElementListener;
import org.talend.sdk.component.api.processor.Input;
import org.talend.sdk.component.api.processor.Processor;
import org.talend.sdk.component.api.record.Record;

/**
 * @author Zoltán Takács
 */
@Icon(custom = "LiferayOutput", value = Icon.IconType.CUSTOM)
@Processor(family = "Liferay", name = "Output")
@Version(1)
public class LiferayOutput implements Serializable {

	public LiferayOutput(
		@Option("liferayOutputConfiguration") final
			LiferayOutputConfiguration liferayOutputConfiguration,
		final TalendService talendService) {

		_liferayOutputConfiguration = liferayOutputConfiguration;
		_talendService = talendService;
	}

	@AfterGroup
	public void afterGroup() {
	}

	@BeforeGroup
	public void beforeGroup() {
	}

	@PostConstruct
	public void init() {
	}

	@ElementListener
	public void onNext(@Input final Record record) {
	}

	@PreDestroy
	public void release() {
	}

	private final LiferayOutputConfiguration _liferayOutputConfiguration;
	private final TalendService _talendService;

}