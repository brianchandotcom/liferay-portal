/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class DefaultLayoutUtilityPageEntry extends PortalException {

	public DefaultLayoutUtilityPageEntry() {
	}

	public DefaultLayoutUtilityPageEntry(String msg) {
		super(msg);
	}

	public DefaultLayoutUtilityPageEntry(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DefaultLayoutUtilityPageEntry(Throwable throwable) {
		super(throwable);
	}

}