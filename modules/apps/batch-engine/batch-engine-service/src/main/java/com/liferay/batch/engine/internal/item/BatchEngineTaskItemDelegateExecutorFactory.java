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

import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskItemDelegateRegistry;
import com.liferay.portal.kernel.model.Company;
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
public class BatchEngineTaskItemDelegateExecutorFactory {

	public BatchEngineTaskItemDelegateExecutorFactory(
		BatchEngineTaskItemDelegateRegistry batchEngineTaskItemDelegateRegistry,
		ExpressionConvert<Filter> expressionConvert,
		FilterParserProvider filterParserProvider,
		SortParserProvider sortParserProvider) {

		_batchEngineTaskItemDelegateRegistry =
			batchEngineTaskItemDelegateRegistry;
		_expressionConvert = expressionConvert;
		_filterParserProvider = filterParserProvider;
		_sortParserProvider = sortParserProvider;
	}

	public BatchEngineTaskItemDelegateExecutor create(
			String taskItemDelegateName, String className, Company company,
			Map<String, Serializable> parameters, User user)
		throws ReflectiveOperationException {

		BatchEngineTaskItemDelegate<?> batchEngineTaskItemDelegate =
			_batchEngineTaskItemDelegateRegistry.getBatchEngineTaskItemDelegate(
				className, taskItemDelegateName);

		if (batchEngineTaskItemDelegate == null) {
			throw new IllegalStateException(
				"No batch engine delegate available for class name " +
					className);
		}

		return new BatchEngineTaskItemDelegateExecutor(
			batchEngineTaskItemDelegate, company, _expressionConvert,
			_filterParserProvider, parameters, _sortParserProvider, user);
	}

	private final BatchEngineTaskItemDelegateRegistry
		_batchEngineTaskItemDelegateRegistry;
	private final ExpressionConvert<Filter> _expressionConvert;
	private final FilterParserProvider _filterParserProvider;
	private final SortParserProvider _sortParserProvider;

}