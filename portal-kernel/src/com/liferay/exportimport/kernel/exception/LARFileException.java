/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Raymond Augé
 */
public class LARFileException extends PortalException {

	public static final int TYPE_DEFAULT = 0;

	public static final int TYPE_INVALID_MANIFEST = 2;

	public static final int TYPE_MISSING_MANIFEST = 1;

	public LARFileException() {
	}

	public LARFileException(int type) {
		_type = type;
	}

	public LARFileException(int type, String msg) {
		this(msg);

		_type = type;
	}

	public LARFileException(int type, String msg, Throwable throwable) {
		this(msg, throwable);

		_type = type;
	}

	public LARFileException(int type, Throwable throwable) {
		this(throwable);

		_type = type;
	}

	public LARFileException(String msg) {
		super(msg);
	}

	public LARFileException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public LARFileException(Throwable throwable) {
		super(throwable);
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	private int _type = TYPE_DEFAULT;

}