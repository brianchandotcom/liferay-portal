/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class CPDefinitionVirtualSettingFileEntryIdException
	extends PortalException {

	public CPDefinitionVirtualSettingFileEntryIdException() {
	}

	public CPDefinitionVirtualSettingFileEntryIdException(String msg) {
		super(msg);
	}

	public CPDefinitionVirtualSettingFileEntryIdException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public CPDefinitionVirtualSettingFileEntryIdException(Throwable throwable) {
		super(throwable);
	}

}