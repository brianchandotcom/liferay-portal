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

	public RequiredSegmentsEntryException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public RequiredSegmentsEntryException(Throwable cause) {
		super(cause);
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