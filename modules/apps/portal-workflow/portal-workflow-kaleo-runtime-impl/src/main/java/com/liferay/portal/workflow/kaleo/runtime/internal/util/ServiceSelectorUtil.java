/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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