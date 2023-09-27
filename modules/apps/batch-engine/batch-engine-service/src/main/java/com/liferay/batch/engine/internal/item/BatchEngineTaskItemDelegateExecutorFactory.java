/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		BatchEngineTaskItemDelegate<?> batchEngineTaskItemDelegate,
		Company company, Map<String, Serializable> parameters, User user) {

		return new BatchEngineTaskItemDelegateExecutor(
			batchEngineTaskItemDelegate, company, _expressionConvert,
			_filterParserProvider, parameters, _sortParserProvider, user);
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

		return create(batchEngineTaskItemDelegate, company, parameters, user);
	}

	private final BatchEngineTaskItemDelegateRegistry
		_batchEngineTaskItemDelegateRegistry;
	private final ExpressionConvert<Filter> _expressionConvert;
	private final FilterParserProvider _filterParserProvider;
	private final SortParserProvider _sortParserProvider;

}