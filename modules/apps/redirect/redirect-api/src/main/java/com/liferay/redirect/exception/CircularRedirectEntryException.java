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

package com.liferay.redirect.exception;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class CircularRedirectEntryException extends PortalException {

	public CircularRedirectEntryException() {
	}

	public CircularRedirectEntryException(String msg) {
		super(msg);
	}

	public CircularRedirectEntryException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public CircularRedirectEntryException(Throwable cause) {
		super(cause);
	}

	public static class DestinationURLMustNotBeEqualToSourceURL
		extends CircularRedirectEntryException {

		public DestinationURLMustNotBeEqualToSourceURL(
			String sourceURL, String destinationURL) {

			super(
				StringBundler.concat(
					"Redirect loop, this redirection cannot be created. ",
					"Please change the Source URL", sourceURL,
					" or Destination URL", destinationURL));
		}

	}

	public static class MustNotFormALoopWithAnotherRedirectEntry
		extends CircularRedirectEntryException {

		public MustNotFormALoopWithAnotherRedirectEntry() {
		}

	}

}