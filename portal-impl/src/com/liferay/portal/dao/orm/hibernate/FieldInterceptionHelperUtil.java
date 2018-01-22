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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.petra.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.Field;

import org.hibernate.intercept.FieldInterceptionHelper;

/**
 * This utility class collaborates with hibernate FieldInterceptionHelper patch
 * from LPS-52218 to ensure the _instrumentedCache does not cause ClassLoader
 * leaking for plugins
 *
 * @author Shuyang Zhou
 */
public class FieldInterceptionHelperUtil {

	public static void initialize() {
		try {
			Field instrumentedCacheField = ReflectionUtil.getDeclaredField(
				FieldInterceptionHelper.class, "_instrumentedCache");

			instrumentedCacheField.set(
				null,
				new ConcurrentReferenceKeyHashMap<Class<?>, Boolean>(
					FinalizeManager.WEAK_REFERENCE_FACTORY));
		}
		catch (NoSuchFieldException nsfe) {
			_log.error(
				"Missing Hibernate FieldInterceptionHelper patch from " +
					"LPS-52218",
				nsfe);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FieldInterceptionHelperUtil.class);

}