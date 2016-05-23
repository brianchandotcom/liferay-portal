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

package com.liferay.mobile.device.rules.service.convert.database;

import com.liferay.mobile.device.rules.model.impl.MDRActionImpl;
import com.liferay.mobile.device.rules.model.impl.MDRRuleGroupImpl;
import com.liferay.mobile.device.rules.model.impl.MDRRuleGroupInstanceImpl;
import com.liferay.mobile.device.rules.model.impl.MDRRuleImpl;

import com.liferay.portal.convert.database.DatabaseConverter;
import com.liferay.portal.convert.util.ModelMigrator;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * @author Edward C. Han
 * @generated
 */
public class MDRDatabaseConverter implements DatabaseConverter {
	@Override
	public void convert(DataSource dataSource) throws Exception {
		_modelMigrator.migrate(dataSource, _getModels());
	}

	public void setModelMigrator(ModelMigrator modelMigrator) {
		_modelMigrator = modelMigrator;
	}

	private List<Class<?extends BaseModel<?>>> _getModels() {
		List<Class<?extends BaseModel<?>>> models = new ArrayList<Class<?extends BaseModel<?>>>();

		models.add(MDRActionImpl.class);
		models.add(MDRRuleImpl.class);
		models.add(MDRRuleGroupImpl.class);
		models.add(MDRRuleGroupInstanceImpl.class);

		return models;
	}

	@BeanReference(type = ModelMigrator.class)
	private ModelMigrator _modelMigrator;
}