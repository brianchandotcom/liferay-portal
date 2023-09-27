/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Eduardo García
 */
public class DefaultSegmentsEntryException extends PortalException {

	public DefaultSegmentsEntryException() {
	}

	public DefaultSegmentsEntryException(String msg) {
		super(msg);
	}

	public DefaultSegmentsEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DefaultSegmentsEntryException(Throwable throwable) {
		super(throwable);
	}

	public static class MustNotDeleteDefaultSegmentsEntry
		extends DefaultSegmentsEntryException {

		public MustNotDeleteDefaultSegmentsEntry(long segmentsEntryId) {
			super(
				String.format(
					"The default segments entry %s cannot be deleted",
					segmentsEntryId));
		}

	}

	public static class MustNotUpdateDefaultSegmentsEntry
		extends DefaultSegmentsEntryException {

		public MustNotUpdateDefaultSegmentsEntry(long segmentsEntryId) {
			super(
				String.format(
					"The default segments entry %s cannot be updated",
					segmentsEntryId));
		}

	}

}