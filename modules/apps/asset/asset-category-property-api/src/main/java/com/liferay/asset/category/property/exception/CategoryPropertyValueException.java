/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.category.property.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class CategoryPropertyValueException extends PortalException {

	public CategoryPropertyValueException() {
	}

	public CategoryPropertyValueException(String msg) {
		super(msg);
	}

	public CategoryPropertyValueException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CategoryPropertyValueException(Throwable throwable) {
		super(throwable);
	}

}