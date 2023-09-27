/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public CircularRedirectEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CircularRedirectEntryException(Throwable throwable) {
		super(throwable);
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
	}

}