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

package com.liferay.portal.search.elasticsearch7.internal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.search.elasticsearch7.internal.io.StringOutputStream;

import java.io.IOException;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.common.io.stream.OutputStreamStreamOutput;

/**
 * @author Michael C. Han
 */
public class LogUtil {

	public static void logActionResponse(
		Log log, ActionResponse actionResponse) {

		if (!log.isInfoEnabled()) {
			return;
		}

		StringOutputStream stringOutputStream = new StringOutputStream();

		try {
			actionResponse.writeTo(
				new OutputStreamStreamOutput(stringOutputStream));
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		log.info(stringOutputStream);
	}

	public static void logActionResponse(Log log, BulkResponse bulkResponse) {
		if (bulkResponse.hasFailures() && log.isWarnEnabled()) {
			log.warn(bulkResponse.buildFailureMessage());
		}

		logActionResponse(log, (ActionResponse)bulkResponse);
	}

}