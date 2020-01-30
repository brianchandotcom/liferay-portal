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

package com.liferay.batch.engine.internal.item;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.odata.sort.SortParserProvider;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Ivica Cardic
 */
@FunctionalInterface
public interface BatchEngineTaskItemDelegateExecutorCreator {

	public BatchEngineTaskItemDelegateExecutor create(
			ExpressionConvert<Filter> expressionConvert,
			FilterParserProvider filterParserProvider,
			Map<String, Serializable> parameters,
			SortParserProvider sortParserProvider, User user)
		throws ReflectiveOperationException;

}