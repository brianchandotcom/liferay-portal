/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class RequiredAssetListEntryException extends PortalException {

	public RequiredAssetListEntryException() {
	}

	public RequiredAssetListEntryException(String msg) {
		super(msg);
	}

	public RequiredAssetListEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public RequiredAssetListEntryException(Throwable throwable) {
		super(throwable);
	}

}