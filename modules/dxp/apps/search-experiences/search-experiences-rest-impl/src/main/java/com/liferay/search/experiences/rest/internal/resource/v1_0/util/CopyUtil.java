/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.internal.resource.v1_0.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class CopyUtil {

	public static Map<Locale, String> createTitleMapCopy(
		Map<Locale, String> sourceTitleMap) {

		Map<Locale, String> targetTitleMap = new HashMap<>();

		for (Map.Entry<Locale, String> entry : sourceTitleMap.entrySet()) {
			StringBundler sb = new StringBundler(4);

			sb.append(entry.getValue());
			sb.append(" (");
			sb.append(LanguageUtil.get(entry.getKey(), "copy"));
			sb.append(")");

			targetTitleMap.put(entry.getKey(), sb.toString());
		}

		return targetTitleMap;
	}

}