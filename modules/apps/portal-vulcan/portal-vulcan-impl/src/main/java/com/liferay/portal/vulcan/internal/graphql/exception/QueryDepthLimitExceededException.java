/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.graphql.exception;

import com.liferay.petra.string.StringBundler;

import graphql.execution.AbortExecutionException;

/**
 * @author Carlos Correa
 */
public class QueryDepthLimitExceededException extends AbortExecutionException {

	public QueryDepthLimitExceededException(int depth, int queryDepthLimit) {
		super(
			StringBundler.concat(
				"Depth ", depth, " is greater than the query depth limit of ",
				queryDepthLimit));
	}

}