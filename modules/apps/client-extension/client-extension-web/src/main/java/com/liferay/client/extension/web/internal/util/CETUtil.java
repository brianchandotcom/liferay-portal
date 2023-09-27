/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.web.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Binh Tran
 */
public class CETUtil {

	public static String normalizeExternalReferenceCodeForPortletId(
		String externalReferenceCode) {

		if (Validator.isNotNull(externalReferenceCode)) {
			return externalReferenceCode.replaceAll(
				"\\W", StringPool.UNDERLINE);
		}

		return externalReferenceCode;
	}

}