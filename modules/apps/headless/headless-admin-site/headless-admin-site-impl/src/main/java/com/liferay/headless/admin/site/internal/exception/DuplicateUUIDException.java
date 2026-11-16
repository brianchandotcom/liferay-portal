/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.exception;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Javier Moral
 */
public class DuplicateUUIDException extends PortalException {

	public DuplicateUUIDException(String entityName, String uuid) {
		super(
			StringBundler.concat("Duplicate UUID ", uuid, " for ", entityName));

		this.entityName = entityName;
		this.uuid = uuid;
	}

	public final String entityName;
	public final String uuid;

}