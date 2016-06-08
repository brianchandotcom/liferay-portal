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

package com.liferay.shopping.service.convert.database;

import com.liferay.portal.convert.database.DatabaseConverter;
import com.liferay.portal.convert.util.ModelMigrator;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.shopping.model.impl.ShoppingCartImpl;
import com.liferay.shopping.model.impl.ShoppingCategoryImpl;
import com.liferay.shopping.model.impl.ShoppingCouponImpl;
import com.liferay.shopping.model.impl.ShoppingItemFieldImpl;
import com.liferay.shopping.model.impl.ShoppingItemImpl;
import com.liferay.shopping.model.impl.ShoppingItemPriceImpl;
import com.liferay.shopping.model.impl.ShoppingOrderImpl;
import com.liferay.shopping.model.impl.ShoppingOrderItemImpl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ShoppingDatabaseConverter implements DatabaseConverter {
	@Override
	public void convert(DataSource dataSource) throws Exception {
		_modelMigrator.migrate(dataSource, _getModels());
	}

	public void setModelMigrator(ModelMigrator modelMigrator) {
		_modelMigrator = modelMigrator;
	}

	private List<Class<?extends BaseModel<?>>> _getModels() {
		List<Class<?extends BaseModel<?>>> models = new ArrayList<Class<?extends BaseModel<?>>>();

		models.add(ShoppingCartImpl.class);
		models.add(ShoppingCategoryImpl.class);
		models.add(ShoppingCouponImpl.class);
		models.add(ShoppingItemImpl.class);
		models.add(ShoppingItemFieldImpl.class);
		models.add(ShoppingItemPriceImpl.class);
		models.add(ShoppingOrderImpl.class);
		models.add(ShoppingOrderItemImpl.class);

		return models;
	}

	@ServiceReference(type = ModelMigrator.class)
	private ModelMigrator _modelMigrator;
}