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

package com.liferay.portal.convert.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ModelHintsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cristina González
 */
public class HibernateModelUtil {

	public static List<Class<? extends BaseModel<?>>> getModelClassNames(
		ClassLoader classLoader, String regex) {

		List<String> modelNames = ModelHintsUtil.getModels();

		List<Class<? extends BaseModel<?>>> implClassNames = new ArrayList<>();

		for (String modelName : modelNames) {
			if (!modelName.contains(".model.")) {
				continue;
			}

			String implClassName = modelName.replaceFirst(
				"(\\.model\\.)(\\p{Upper}.*)", "$1impl.$2Impl");

			if (implClassName.matches(regex)) {
				Class<? extends BaseModel<?>> implClass = getImplClass(
					classLoader, implClassName);

				if (implClass != null) {
					implClassNames.add(implClass);
				}
			}
		}

		return implClassNames;
	}

	protected static Class<? extends BaseModel<?>> getImplClass(
		ClassLoader classLoader, String implClassName) {

		try {
			return (Class<? extends BaseModel<?>>)classLoader.loadClass(
				implClassName);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HibernateModelUtil.class);

}