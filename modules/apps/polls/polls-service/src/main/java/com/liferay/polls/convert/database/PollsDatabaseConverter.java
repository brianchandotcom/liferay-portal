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

package com.liferay.polls.convert.database;

import com.liferay.polls.constants.PollsPortletKeys;
import com.liferay.portal.convert.DatabaseConverter;
import com.liferay.portal.convert.util.ModelMigrator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ModelHintsUtil;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(immediate = true, service = DatabaseConverter.class)
public class PollsDatabaseConverter implements DatabaseConverter {

	@Override
	public void convert(DataSource dataSource) throws Exception {
		_modelMigrator.migrate(dataSource, getModelClassesName(".*Polls.*"));
	}

	protected Class<? extends BaseModel<?>> getImplClass(String implClassName) {
		try {
			ClassLoader classLoader = getClass().getClassLoader();

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

	@Reference(unbind = "-")
	private void setModelMigrator(ModelMigrator modelMigrator) {
		_modelMigrator = modelMigrator;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PollsDatabaseConverter.class);

	private ModelMigrator _modelMigrator;

}