/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.graphql.servlet.instrumentation;

import com.liferay.portal.vulcan.internal.graphql.exception.QueryDepthLimitExceededException;

import graphql.execution.AbortExecutionException;

/**
 * @author Carlos Correa
 */
public class MaxQueryDepthInstrumentation
	extends graphql.analysis.MaxQueryDepthInstrumentation {

	public MaxQueryDepthInstrumentation(int queryDepthLimit) {
		super(
			0,
			queryDepthInfo ->
				(queryDepthLimit > 0) &&
				(queryDepthInfo.getDepth() > queryDepthLimit));

		_queryDepthLimit = queryDepthLimit;
	}

	@Override
	protected AbortExecutionException mkAbortException(
		int depth, int maxDepth) {

		throw new QueryDepthLimitExceededException(depth, _queryDepthLimit);
	}

	private final int _queryDepthLimit;

}