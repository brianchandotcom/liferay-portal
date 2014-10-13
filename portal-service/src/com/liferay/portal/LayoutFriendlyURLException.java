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

package com.liferay.portal;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 * @author Sergio González
 */
public class LayoutFriendlyURLException extends PortalException {

	public static final int ADJACENT_SLASHES = 4;

	public static final int DOES_NOT_START_WITH_SLASH = 1;

	public static final int DUPLICATE = 6;

	public static final int ENDS_WITH_SLASH = 2;

	public static final int INVALID_CHARACTERS = 5;

	public static final int KEYWORD_CONFLICT = 7;

	public static final int POSSIBLE_DUPLICATE = 8;

	public static final int TOO_DEEP = 9;

	public static final int TOO_LONG = 10;

	public static final int TOO_SHORT = 3;

	public LayoutFriendlyURLException(int type) {
		_type = type;
	}

	public String getDuplicateClassName() {
		return _duplicateClassName;
	}

	public long getDuplicateClassPK() {
		return _duplicateClassPK;
	}

	public String getKeywordConflict() {
		return _keywordConflict;
	}

	public int getType() {
		return _type;
	}

	public void setDuplicateClassName(String duplicateClassName) {
		_duplicateClassName = duplicateClassName;
	}

	public void setDuplicateClassPK(long duplicateClassPK) {
		_duplicateClassPK = duplicateClassPK;
	}

	public void setKeywordConflict(String keywordConflict) {
		_keywordConflict = keywordConflict;
	}

	private String _duplicateClassName;
	private long _duplicateClassPK;
	private String _keywordConflict;
	private final int _type;

}