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

package com.liferay.portal.kernel.repository.event;

import com.liferay.portal.kernel.repository.model.FileVersion;

/**
 * @author Roberto Díaz
 * @author Adolfo Pérez
 */
public interface FileVersionPreviewEventListener {

	public void deleteDLFileVersionPreviews(long fileEntryId);

	public long getDLFileVersionPreviewId(
		long fileEntryId, long fileVersionId, int fileVersionPreviewStatus);

	public void onFailure(FileVersion fileVersion);

	public void onSuccess(FileVersion fileVersion);

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