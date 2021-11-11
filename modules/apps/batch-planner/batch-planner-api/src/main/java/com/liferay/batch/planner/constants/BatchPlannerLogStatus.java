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
public enum BatchPlannerLogStatus {

	COMPLETED(3), FAILED(4), QUEUED(1), STARTED(2);

	public static BatchPlannerLogStatus from(
		BatchEngineTaskExecuteStatus batchEngineTaskExecuteStatus) {

		if (batchEngineTaskExecuteStatus ==
				BatchEngineTaskExecuteStatus.COMPLETED) {

			return COMPLETED;
		}
		else if (batchEngineTaskExecuteStatus ==
					BatchEngineTaskExecuteStatus.FAILED) {

			return FAILED;
		}
		else if (batchEngineTaskExecuteStatus ==
					BatchEngineTaskExecuteStatus.INITIAL) {

			return QUEUED;
		}
		else if (batchEngineTaskExecuteStatus ==
					BatchEngineTaskExecuteStatus.STARTED) {

			return STARTED;
		}

		throw new IllegalArgumentException("Invalid status");
	}

	public static BatchPlannerLogStatus from(int status) {
		for (BatchPlannerLogStatus batchPlannerLogStatus : values()) {
			if (batchPlannerLogStatus._status == status) {
				return batchPlannerLogStatus;
			}
		}

		throw new IllegalArgumentException(
			"No BatchPlannerLogStatus found for status: " + status);
	}

	public int getStatus() {
		return _status;
	}

	private BatchPlannerLogStatus(int status) {
		_status = status;
	}

	private final int _status;

}