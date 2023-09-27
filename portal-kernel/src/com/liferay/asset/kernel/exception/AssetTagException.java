/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class AssetTagException extends PortalException {

	public static final int AT_LEAST_ONE_TAG = 1;

	public static final int INVALID_CHARACTER = 2;

	public static final int MAX_LENGTH = 3;

	public AssetTagException(int type) {
		_type = type;
	}

	public AssetTagException(String message, int type) {
		super(message);

		_type = type;
	}

	public int getType() {
		return _type;
	}

	private final int _type;

}