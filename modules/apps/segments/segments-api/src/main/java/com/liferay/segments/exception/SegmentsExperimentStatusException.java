/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Eduardo García
 */
public class SegmentsExperimentStatusException extends PortalException {

	public SegmentsExperimentStatusException() {
	}

	public SegmentsExperimentStatusException(String msg) {
		super(msg);
	}

	public SegmentsExperimentStatusException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public SegmentsExperimentStatusException(Throwable throwable) {
		super(throwable);
	}

}