/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Eduardo García
 */
public class SegmentsExperimentConfidenceLevelException
	extends PortalException {

	public SegmentsExperimentConfidenceLevelException() {
	}

	public SegmentsExperimentConfidenceLevelException(String msg) {
		super(msg);
	}

	public SegmentsExperimentConfidenceLevelException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public SegmentsExperimentConfidenceLevelException(Throwable throwable) {
		super(throwable);
	}

}