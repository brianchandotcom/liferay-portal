/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Eduardo García
 */
public class SegmentsExperimentTypeException extends PortalException {

	public SegmentsExperimentTypeException() {
	}

	public SegmentsExperimentTypeException(String msg) {
		super(msg);
	}

	public SegmentsExperimentTypeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public SegmentsExperimentTypeException(Throwable throwable) {
		super(throwable);
	}

}