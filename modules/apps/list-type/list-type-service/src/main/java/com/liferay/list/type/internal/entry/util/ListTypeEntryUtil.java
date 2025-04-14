/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.list.type.internal.entry.util;

import com.liferay.list.type.exception.ListTypeEntrySystemException;
import com.liferay.object.definition.util.ObjectDefinitionUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alberto Sousa
 */
public class ListTypeEntryUtil {

	public static void validateInvokerBundle(String message, boolean system)
		throws PortalException {

		if (!system || ObjectDefinitionUtil.isInvokerBundleAllowed()) {
			return;
		}

		throw new ListTypeEntrySystemException(message);
	}

}