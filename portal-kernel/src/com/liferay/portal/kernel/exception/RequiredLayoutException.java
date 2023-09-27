/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class RequiredLayoutException extends PortalException {

	public static final int AT_LEAST_ONE = 1;

	public static final int FIRST_LAYOUT_TYPE = 3;

	public RequiredLayoutException(int type) {
		_type = type;
	}

	public int getType() {
		return _type;
	}

	private final int _type;

}