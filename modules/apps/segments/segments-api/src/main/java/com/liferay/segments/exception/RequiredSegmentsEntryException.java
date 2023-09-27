/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Eduardo García
 */
public class RequiredSegmentsEntryException extends PortalException {

	public RequiredSegmentsEntryException() {
	}

	public RequiredSegmentsEntryException(String msg) {
		super(msg);
	}

	public RequiredSegmentsEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public RequiredSegmentsEntryException(Throwable throwable) {
		super(throwable);
	}

	public static class
		MustNotDeleteSegmentsEntryReferencedBySegmentsExperiences
			extends RequiredSegmentsEntryException {

		public MustNotDeleteSegmentsEntryReferencedBySegmentsExperiences(
			long segmentsEntryId) {

			super(
				String.format(
					"Segments entry %s cannot be deleted because it is " +
						"referenced by one or more segments experiences",
					segmentsEntryId));
		}

	}

}