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

package com.liferay.portal.vulcan.internal.batch.engine.strategy;

import com.liferay.batch.engine.strategy.ImportStrategy;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.vulcan.batch.engine.strategy.BatchStrategy;

import java.util.Collection;

/**
 * @author Matija Petanjek
 */
public class BatchStrategyImpl implements BatchStrategy {

	public BatchStrategyImpl(ImportStrategy importStrategy) {
		_importStrategy = importStrategy;
	}

	public <T> void apply(
			Collection<T> collection,
			UnsafeConsumer<T, Exception> unsafeConsumer)
		throws Exception {

		_importStrategy.apply(collection, unsafeConsumer);
	}

	private final ImportStrategy _importStrategy;

}