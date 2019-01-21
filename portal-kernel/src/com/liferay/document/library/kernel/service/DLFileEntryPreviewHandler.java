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

package com.liferay.document.library.kernel.service;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Roberto Díaz
 */
public interface DLFileEntryPreviewHandler {

	public void addDLFileEntryPreview(
			long fileEntryId, long fileVersionId,
			DLFileEntryPreviewType fileEntryPreviewType)
		throws PortalException;

	public void deleteDLFileEntryPreviews(long fileEntryId)
		throws PortalException;

	public long getDLFileEntryPreviewId(long fileEntryId, long fileVersionId)
		throws PortalException;

	public long getDLFileEntryPreviewId(
			long fileEntryId, long fileVersionId,
			DLFileEntryPreviewType fileEntryPreviewType)
		throws PortalException;

	public void updateDLFileEntryPreview(
			long dlFileEntryPreviewId,
			DLFileEntryPreviewType fileEntryPreviewType)
		throws PortalException;

	public enum DLFileEntryPreviewType {

		FAIL(0), NOT_GENERATED(1), SUCCESS(2);

		public static DLFileEntryPreviewType fromInteger(int value) {
			for (DLFileEntryPreviewType dlFileEntryPreviewType : values()) {
				if (dlFileEntryPreviewType.toInteger() == value) {
					return dlFileEntryPreviewType;
				}
			}

			throw new IllegalArgumentException(
				"No DLFileEntryPreviewType exists with value " + value);
		}

		public int toInteger() {
			return _value;
		}

		private DLFileEntryPreviewType(int value) {
			_value = value;
		}

		private final int _value;

	}

}