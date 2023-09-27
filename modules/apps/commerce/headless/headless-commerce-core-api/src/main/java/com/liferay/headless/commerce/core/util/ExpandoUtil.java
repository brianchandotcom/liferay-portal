/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.core.util;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.util.Enumeration;
import java.util.Map;

/**
 * @author Igor Beslic
 */
public class ExpandoUtil {

	public static void updateExpando(
		long companyId, Class<?> clazz, long classPK,
		Map<String, ?> expandoAttributes) {

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			companyId, clazz.getName(), classPK);

		Enumeration<String> enumeration = expandoBridge.getAttributeNames();

		while (enumeration.hasMoreElements()) {
			String attributeName = enumeration.nextElement();

			if (!expandoAttributes.containsKey(attributeName)) {
				continue;
			}

			expandoBridge.setAttribute(
				attributeName,
				(Serializable)expandoAttributes.get(attributeName));
		}
	}

}