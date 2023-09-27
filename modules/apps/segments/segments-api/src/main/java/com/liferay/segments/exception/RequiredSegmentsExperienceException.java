/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Eduardo García
 */
public class RequiredSegmentsExperienceException extends PortalException {

	public RequiredSegmentsExperienceException() {
	}

	public RequiredSegmentsExperienceException(String msg) {
		super(msg);
	}

	public RequiredSegmentsExperienceException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public RequiredSegmentsExperienceException(Throwable throwable) {
		super(throwable);
	}

	public static class
		MustNotDeleteSegmentsExperienceReferencedBySegmentsExperiments
			extends RequiredSegmentsEntryException {

		public MustNotDeleteSegmentsExperienceReferencedBySegmentsExperiments(
			long segmentsExperienceId) {

			super(
				String.format(
					"Segments experience %s cannot be deleted because it is " +
						"referenced by one or more segments experiments",
					segmentsExperienceId));
		}

	}

}