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

package com.liferay.portal.convert.database;

import com.liferay.portal.convert.DatabaseConverter;
import com.liferay.portal.convert.util.ModelMigrator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ModelHintsUtil;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import javax.sql.DataSource;

/**
 * @author Cristina González
 */
public class PortalDatabaseConverter implements DatabaseConverter {

	@Override
	public void convert(DataSource dataSource) throws Exception {
		_modelMigrator.migrate(dataSource, getModelClassesName(".*"));
	}

	public void setModelMigrator(ModelMigrator modelMigrator) {
		_modelMigrator = modelMigrator;
	}

	protected Class<? extends BaseModel<?>> getImplClass(String implClassName) {
		try {
			ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

			return (Class<? extends BaseModel<?>>)classLoader.loadClass(
				implClassName);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		// Backward compatibility so legacy plugins are converted

		for (String servletContextName : ServletContextPool.keySet()) {
			try {
				ServletContext servletContext = ServletContextPool.get(
					servletContextName);

				ClassLoader classLoader = servletContext.getClassLoader();

				return (Class<? extends BaseModel<?>>)classLoader.loadClass(
					implClassName);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		}

		return null;
	}

	protected List<Class<? extends BaseModel<?>>> getModelClassesName(
		String regex) {

		List<String> modelNames = ModelHintsUtil.getModels();

		List<Class<? extends BaseModel<?>>> implClassesNames =
			new ArrayList<>();

		for (String modelName : modelNames) {
			if (!modelName.contains(".model.")) {
				continue;
			}

			String implClassName = modelName.replaceFirst(
				"(\\.model\\.)(\\p{Upper}.*)", "$1impl.$2Impl");

			if (implClassName.matches(regex)) {
				Class<? extends BaseModel<?>> implClass = getImplClass(
					implClassName);

				if (implClass != null) {
					implClassesNames.add(implClass);
				}
			}
		}

		return implClassesNames;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalDatabaseConverter.class);

	private ModelMigrator _modelMigrator;

}