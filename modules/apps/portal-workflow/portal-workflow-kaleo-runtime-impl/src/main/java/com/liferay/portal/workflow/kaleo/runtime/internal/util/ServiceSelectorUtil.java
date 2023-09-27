/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.internal.util;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.workflow.kaleo.definition.ScriptLanguage;

import java.util.List;
import java.util.Objects;

/**
 * @author Jiaxu Wei
 */
public class ServiceSelectorUtil {

	public static <T> T getServiceByScriptLanguage(
		String className, String scriptLanguage,
		ServiceTrackerMap<String, List<T>> serviceTrackerMap) {

		List<T> list = serviceTrackerMap.getService(scriptLanguage);

		if (ListUtil.isEmpty(list)) {
			return null;
		}

		if (!Objects.equals(
				String.valueOf(ScriptLanguage.JAVA), scriptLanguage)) {

			return list.get(0);
		}

		for (T t : list) {
			if (Objects.equals(className, ClassUtil.getClassName(t))) {
				return t;
			}
		}

		return null;
	}

}