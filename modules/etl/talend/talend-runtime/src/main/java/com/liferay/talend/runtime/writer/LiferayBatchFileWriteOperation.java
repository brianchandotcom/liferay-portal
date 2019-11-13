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

import com.liferay.talend.runtime.LiferayBatchFileSink;

import java.util.Map;

import org.talend.components.api.component.runtime.Result;
import org.talend.components.api.component.runtime.Sink;
import org.talend.components.api.component.runtime.WriteOperation;
import org.talend.components.api.component.runtime.Writer;
import org.talend.components.api.container.RuntimeContainer;

/**
 * @author Igor Beslic
 */
public class LiferayBatchFileWriteOperation implements WriteOperation<Result> {

	public LiferayBatchFileWriteOperation(
		LiferayBatchFileSink liferayBatchFileSink) {

		_liferayBatchFileSink = liferayBatchFileSink;
	}

	@Override
	public Writer<Result> createWriter(RuntimeContainer runtimeContainer) {
		return new LiferayBatchFileWriter(this, runtimeContainer);
	}

	@Override
	public Map<String, Object> finalize(
		Iterable<Result> iterable, RuntimeContainer runtimeContainer) {

		return Result.accumulateAndReturnMap(iterable);
	}

	@Override
	public Sink getSink() {
		return _liferayBatchFileSink;
	}

	@Override
	public void initialize(RuntimeContainer runtimeContainer) {
	}

	private final LiferayBatchFileSink _liferayBatchFileSink;

}