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

package com.liferay.dynamic.data.mapping.service.convert.database;

import com.liferay.dynamic.data.mapping.model.impl.DDMContentImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceLinkImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMStorageLinkImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureLayoutImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureLinkImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureVersionImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMTemplateImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMTemplateLinkImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMTemplateVersionImpl;

import com.liferay.portal.convert.database.DatabaseConverter;
import com.liferay.portal.convert.util.ModelMigrator;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMDatabaseConverter implements DatabaseConverter {
	@Override
	public void convert(DataSource dataSource) throws Exception {
		_modelMigrator.migrate(dataSource, _getModels());
	}

	public void setModelMigrator(ModelMigrator modelMigrator) {
		_modelMigrator = modelMigrator;
	}

	private List<Class<?extends BaseModel<?>>> _getModels() {
		List<Class<?extends BaseModel<?>>> models = new ArrayList<Class<?extends BaseModel<?>>>();

		models.add(DDMContentImpl.class);
		models.add(DDMDataProviderInstanceImpl.class);
		models.add(DDMDataProviderInstanceLinkImpl.class);
		models.add(DDMStorageLinkImpl.class);
		models.add(DDMStructureImpl.class);
		models.add(DDMStructureLayoutImpl.class);
		models.add(DDMStructureLinkImpl.class);
		models.add(DDMStructureVersionImpl.class);
		models.add(DDMTemplateImpl.class);
		models.add(DDMTemplateLinkImpl.class);
		models.add(DDMTemplateVersionImpl.class);

		return models;
	}

	@ServiceReference(type = ModelMigrator.class)
	private ModelMigrator _modelMigrator;
}