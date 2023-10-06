/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.graphql.instrumentation;

import com.liferay.portal.vulcan.internal.graphql.exception.QueryDepthLimitExceededException;

import graphql.analysis.MaxQueryDepthInstrumentation;
import graphql.analysis.QueryDepthInfo;

import graphql.execution.AbortExecutionException;
import graphql.execution.instrumentation.Instrumentation;

import java.util.function.Function;

/**
 * @author Carlos Correa
 */
public class QueryDepthLimitInstrumentation
	extends MaxQueryDepthInstrumentation implements Instrumentation {

	public static QueryDepthLimitInstrumentation of(int queryDepthLimit) {
		return new QueryDepthLimitInstrumentation(
			queryDepthInfo ->
				(queryDepthLimit > 0) &&
				(queryDepthInfo.getDepth() > queryDepthLimit),
			queryDepthLimit);
	}

	public Function<QueryDepthInfo, Boolean> getFunction() {
		return _function;
	}

	@Override
	public AbortExecutionException mkAbortException(int depth, int maxDepth) {
		throw new QueryDepthLimitExceededException(depth, _queryDepthLimit);
	}

	private QueryDepthLimitInstrumentation(
		Function<QueryDepthInfo, Boolean> function, int queryDepthLimit) {

		super(0, function);

		_function = function;
		_queryDepthLimit = queryDepthLimit;
	}

	private final Function<QueryDepthInfo, Boolean> _function;
	private final int _queryDepthLimit;

}