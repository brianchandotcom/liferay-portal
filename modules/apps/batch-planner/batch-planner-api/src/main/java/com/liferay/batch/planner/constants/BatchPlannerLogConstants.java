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

package com.liferay.batch.planner.constants;

import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;

/**
 * @author Matija Petanjek
 */
public class BatchPlannerLogConstants {

	public static final String LABEL_COMPLETED = "completed";

	public static final String LABEL_FAILED = "failed";

	public static final String LABEL_QUEUED = "queued";

	public static final String LABEL_RUNNING = "running";

	public static final int STATUS_COMPLETED = 3;

	public static final int STATUS_FAILED = 4;

	public static final int STATUS_QUEUED = 1;

	public static final int STATUS_RUNNING = 2;

	public static int getStatus(
		BatchEngineTaskExecuteStatus batchEngineTaskExecuteStatus) {

		if (batchEngineTaskExecuteStatus ==
				BatchEngineTaskExecuteStatus.COMPLETED) {

			return STATUS_COMPLETED;
		}
		else if (batchEngineTaskExecuteStatus ==
					BatchEngineTaskExecuteStatus.FAILED) {

			return STATUS_FAILED;
		}
		else if (batchEngineTaskExecuteStatus ==
					BatchEngineTaskExecuteStatus.INITIAL) {

			return STATUS_QUEUED;
		}
		else if (batchEngineTaskExecuteStatus ==
					BatchEngineTaskExecuteStatus.STARTED) {

			return STATUS_RUNNING;
		}

		throw new IllegalArgumentException(
			"Invalid batch engine task execute status " +
				batchEngineTaskExecuteStatus);
	}

	public static String getStatusCssClass(int status) {
		if (status == STATUS_COMPLETED) {
			return "text-success";
		}
		else if (status == STATUS_FAILED) {
			return "text-danger";
		}
		else if ((status == STATUS_QUEUED) || (status == STATUS_RUNNING)) {
			return "text-info";
		}

		throw new IllegalArgumentException("Invalid status " + status);
	}

	public static String getStatusLabel(int status) {
		if (status == STATUS_COMPLETED) {
			return LABEL_COMPLETED;
		}
		else if (status == STATUS_FAILED) {
			return LABEL_FAILED;
		}
		else if (status == STATUS_QUEUED) {
			return LABEL_QUEUED;
		}
		else if (status == STATUS_RUNNING) {
			return LABEL_RUNNING;
		}

		throw new IllegalArgumentException("Invalid status " + status);
	}

}