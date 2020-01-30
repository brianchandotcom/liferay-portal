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

import com.liferay.batch.engine.internal.BatchEngineTaskMethodRegistry;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.odata.sort.SortParserProvider;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class BatchEngineTaskItemDelegateExecutorFactory {

	public BatchEngineTaskItemDelegateExecutorFactory(
		BatchEngineTaskMethodRegistry batchEngineTaskMethodRegistry,
		ExpressionConvert<Filter> expressionConvert,
		FilterParserProvider filterParserProvider,
		SortParserProvider sortParserProvider,
		UserLocalService userLocalService) {

		_batchEngineTaskMethodRegistry = batchEngineTaskMethodRegistry;
		_expressionConvert = expressionConvert;
		_filterParserProvider = filterParserProvider;
		_sortParserProvider = sortParserProvider;
		_userLocalService = userLocalService;
	}

	public BatchEngineTaskItemDelegateExecutor create(
			String className, Map<String, Serializable> parameters, long userId)
		throws Exception {

		BatchEngineTaskItemDelegateExecutorCreator
			batchEngineTaskItemDelegateExecutorCreator =
				_batchEngineTaskMethodRegistry.
					getBatchEngineTaskItemDelegateExecutorCreator(className);

		if (batchEngineTaskItemDelegateExecutorCreator == null) {
			throw new IllegalStateException(
				"No batch engine delegate available for class name " +
					className);
		}

		return batchEngineTaskItemDelegateExecutorCreator.create(
			_expressionConvert, _filterParserProvider, parameters,
			_sortParserProvider, _userLocalService.getUser(userId));
	}

	private final BatchEngineTaskMethodRegistry _batchEngineTaskMethodRegistry;
	private final ExpressionConvert<Filter> _expressionConvert;
	private final FilterParserProvider _filterParserProvider;
	private final SortParserProvider _sortParserProvider;
	private final UserLocalService _userLocalService;

}