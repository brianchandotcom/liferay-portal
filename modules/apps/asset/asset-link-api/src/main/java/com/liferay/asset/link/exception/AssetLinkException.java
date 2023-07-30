/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.link.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class AssetLinkException extends PortalException {

	public AssetLinkException() {
	}

	public AssetLinkException(String msg) {
		super(msg);
	}

	public AssetLinkException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public AssetLinkException(Throwable throwable) {
		super(throwable);
	}

}