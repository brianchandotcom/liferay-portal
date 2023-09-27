/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.resource.v1_0.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public class NameMapUtil {

	public static <K, V> Map<K, V> copy(
		Map<? extends K, ? extends V> sourceNameMap) {

		Map<K, V> targetNameMap = new HashMap<>();

		for (Map.Entry<? extends K, ? extends V> entry :
				sourceNameMap.entrySet()) {

			Locale locale = null;

			if (entry.getKey() instanceof Locale) {
				locale = (Locale)entry.getKey();
			}
			else {
				locale = LocaleUtil.fromLanguageId((String)entry.getKey());
			}

			targetNameMap.put(
				(K)entry.getKey(),
				(V)StringUtil.appendParentheticalSuffix(
					(String)entry.getValue(),
					LanguageUtil.get(locale, "copy")));
		}

		return targetNameMap;
	}

}