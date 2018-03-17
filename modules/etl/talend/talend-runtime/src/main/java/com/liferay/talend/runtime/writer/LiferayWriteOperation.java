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

package com.liferay.talend.runtime.writer;

import com.liferay.talend.runtime.LiferaySink;
import com.liferay.talend.tliferayoutput.TLiferayOutputProperties;

import java.util.Map;

import org.talend.components.api.component.runtime.Result;
import org.talend.components.api.component.runtime.WriteOperation;
import org.talend.components.api.container.RuntimeContainer;

/**
 * @author Zoltán Takács
 */
public class LiferayWriteOperation implements WriteOperation<Result> {

	public LiferayWriteOperation(
		LiferaySink liferaySink,
		TLiferayOutputProperties tLiferayOutputProperties) {

		_liferaySink = liferaySink;
		_tLiferayOutputProperties = tLiferayOutputProperties;
	}

	@Override
	public LiferayWriter createWriter(RuntimeContainer runtimeContainer) {
		return new LiferayWriter(
			this, runtimeContainer, _tLiferayOutputProperties);
	}

	@Override
	public Map<String, Object> finalize(
		Iterable<Result> writerResults, RuntimeContainer runtimeContainer) {

		return Result.accumulateAndReturnMap(writerResults);
	}

	@Override
	public LiferaySink getSink() {
		return _liferaySink;
	}

	@Override
	public void initialize(RuntimeContainer runtimeContainer) {
	}

	private final LiferaySink _liferaySink;
	private final TLiferayOutputProperties _tLiferayOutputProperties;

}