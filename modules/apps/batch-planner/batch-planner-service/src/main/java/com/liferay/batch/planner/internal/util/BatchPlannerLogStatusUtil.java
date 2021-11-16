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

package com.liferay.batch.planner.internal.util;

import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.planner.constants.BatchPlannerLogConstants;

/**
 * @author Matija Petanjek
 */
public class BatchPlannerLogStatusUtil {

	public static int getStatus(
		BatchEngineTaskExecuteStatus batchEngineTaskExecuteStatus) {

		if (batchEngineTaskExecuteStatus ==
				BatchEngineTaskExecuteStatus.COMPLETED) {

			return BatchPlannerLogConstants.STATUS_COMPLETED;
		}
		else if (batchEngineTaskExecuteStatus ==
					BatchEngineTaskExecuteStatus.FAILED) {

			return BatchPlannerLogConstants.STATUS_FAILED;
		}
		else if (batchEngineTaskExecuteStatus ==
					BatchEngineTaskExecuteStatus.INITIAL) {

			return BatchPlannerLogConstants.STATUS_QUEUED;
		}
		else if (batchEngineTaskExecuteStatus ==
					BatchEngineTaskExecuteStatus.STARTED) {

			return BatchPlannerLogConstants.STATUS_RUNNING;
		}

		throw new IllegalArgumentException(
			"Invalid batch engine task status " + batchEngineTaskExecuteStatus);
	}

}