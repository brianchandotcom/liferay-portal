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
public class RequiredSegmentsExperienceException extends PortalException {

	public RequiredSegmentsExperienceException() {
	}

	public RequiredSegmentsExperienceException(String msg) {
		super(msg);
	}

	public RequiredSegmentsExperienceException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public RequiredSegmentsExperienceException(Throwable cause) {
		super(cause);
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