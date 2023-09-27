/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.util;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Enumeration;

/**
 * @author Albert Lee
 */
public class ExpandoAttributesUtil {

	public static boolean hasVisibleAttributes(long companyId, Class<?> clazz) {
		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			companyId, clazz.getName());

		Enumeration<String> enumeration = expandoBridge.getAttributeNames();

		while (enumeration.hasMoreElements()) {
			String attributeName = enumeration.nextElement();

			UnicodeProperties unicodeProperties =
				expandoBridge.getAttributeProperties(attributeName);

			if (!GetterUtil.getBoolean(
					unicodeProperties.get(
						ExpandoColumnConstants.PROPERTY_HIDDEN))) {

				return true;
			}
		}

		return false;
	}

}