/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.trash.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class RestoreEntryException extends PortalException {

	public static final int DUPLICATE = 1;

	public static final int INVALID_CONTAINER = 2;

	public static final int INVALID_NAME = 3;

	public static final int INVALID_STATUS = 4;

	public static final int NOT_RESTORABLE = 5;

	public RestoreEntryException() {
	}

	public RestoreEntryException(int type) {
		_type = type;
	}

	public RestoreEntryException(int type, Throwable throwable) {
		super(throwable);

		_type = type;
	}

	public RestoreEntryException(String msg) {
		super(msg);
	}

	public RestoreEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public RestoreEntryException(Throwable throwable) {
		super(throwable);
	}

	public long getDuplicateEntryId() {
		return _duplicateEntryId;
	}

	public String getErrorMessage() {
		return _errorMessage;
	}

	public String getOldName() {
		return _oldName;
	}

	public long getTrashEntryId() {
		return _trashEntryId;
	}

	public int getType() {
		return _type;
	}

	public boolean isOverridable() {
		return _overridable;
	}

	public void setDuplicateEntryId(long duplicateEntryId) {
		_duplicateEntryId = duplicateEntryId;
	}

	public void setErrorMessage(String errorMessage) {
		_errorMessage = errorMessage;
	}

	public void setOldName(String oldName) {
		_oldName = oldName;
	}

	public void setOverridable(boolean overridable) {
		_overridable = overridable;
	}

	public void setTrashEntryId(long trashEntryId) {
		_trashEntryId = trashEntryId;
	}

	public void setType(int type) {
		_type = type;
	}

	private long _duplicateEntryId;
	private String _errorMessage;
	private String _oldName;
	private boolean _overridable = true;
	private long _trashEntryId;
	private int _type;

}