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
import com.liferay.portal.convert.util.HibernateModelLoaderUtil;
import com.liferay.portal.convert.util.ModelMigrator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.model.BaseModel;

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

	protected List<Class<? extends BaseModel<?>>> getModelClassesName(
		String regex) {

		List<Class<? extends BaseModel<?>>> modelClassesName =
			new ArrayList<>();

		modelClassesName.addAll(
			HibernateModelLoaderUtil.getModelClassesName(
				ClassLoaderUtil.getPortalClassLoader(), ".*"));

		for (String servletContextName : ServletContextPool.keySet()) {
			ServletContext servletContext = ServletContextPool.get(
				servletContextName);

			ClassLoader classLoader = servletContext.getClassLoader();

			modelClassesName.addAll(
				HibernateModelLoaderUtil.getModelClassesName(
					classLoader, ".*"));
		}

		return modelClassesName;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalDatabaseConverter.class);

	private ModelMigrator _modelMigrator;

}