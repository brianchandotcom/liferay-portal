/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.graphql.servlet.instrumentation;

import com.liferay.portal.vulcan.internal.graphql.exception.MaxQueryDepthExceededException;

import graphql.execution.AbortExecutionException;

/**
 * @author Carlos Correa
 */
public class MaxQueryDepthInstrumentation
	extends graphql.analysis.MaxQueryDepthInstrumentation {

	public MaxQueryDepthInstrumentation(int depthQueryLimit) {
		super(
			0,
			queryDepthInfo ->
				(depthQueryLimit > 0) &&
				(queryDepthInfo.getDepth() > depthQueryLimit));

		_depthQueryLimit = depthQueryLimit;
	}

	@Override
	protected AbortExecutionException mkAbortException(
		int depth, int maxDepth) {

		throw new MaxQueryDepthExceededException(depth, _depthQueryLimit);
	}

	private final int _depthQueryLimit;

}