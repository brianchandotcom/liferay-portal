/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.graphql.instrumentation;

import com.liferay.portal.vulcan.internal.graphql.exception.QueryDepthLimitExceededException;

import graphql.analysis.MaxQueryDepthInstrumentation;

import graphql.execution.AbortExecutionException;
import graphql.execution.instrumentation.Instrumentation;

/**
 * @author Carlos Correa
 */
public class QueryDepthLimitInstrumentation
	extends MaxQueryDepthInstrumentation implements Instrumentation {

	public QueryDepthLimitInstrumentation(int queryDepthLimit) {
		super(queryDepthLimit);
	}

	@Override
	public AbortExecutionException mkAbortException(
		int depth, int queryDepthLimit) {

		return new QueryDepthLimitExceededException(depth, queryDepthLimit);
	}

}