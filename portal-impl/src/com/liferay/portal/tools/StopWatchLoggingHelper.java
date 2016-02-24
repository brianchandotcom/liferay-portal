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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.log.Log;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Tina Tian
 */
public class StopWatchLoggingHelper {

	public static void endLogging(
		StopWatch stopWatch, Log log, String targetName) {

		if (log.isInfoEnabled()) {
			log.info(
				"Completed " + targetName + " in " + stopWatch.getTime() +
					" ms");
		}
	}

	public static StopWatch startLogging(Log log, String targetName) {
		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		if (log.isInfoEnabled()) {
			log.info("Starting " + targetName);
		}

		return stopWatch;
	}

}