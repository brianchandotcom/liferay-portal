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

package com.liferay.trash.exception;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class RestoreEntryException extends PortalException {

	public static final int DUPLICATE = 1;

	public static final int INVALID_CONTAINER = 2;

	public static final int INVALID_NAME = 3;

	public static final int INVALID_STATUS = 4;

	public RestoreEntryException() {
	}

	public RestoreEntryException(int type) {
		_type = type;
	}

	public RestoreEntryException(int type, Throwable cause) {
		super(cause);

		_type = type;
	}

	public RestoreEntryException(String msg) {
		super(msg);
	}

	public RestoreEntryException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public RestoreEntryException(Throwable cause) {
		super(cause);
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