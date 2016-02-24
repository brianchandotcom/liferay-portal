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

package com.liferay.shopping.verify.model;

import com.liferay.portal.kernel.verify.model.VerifiableResourcedModel;
import com.liferay.shopping.model.ShoppingCategory;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingCategoryVerifiableModel
	implements VerifiableResourcedModel {

	@Override
	public String getModelName() {
		return ShoppingCategory.class.getName();
	}

	@Override
	public String getPrimaryKeyColumnName() {
		return "categoryId";
	}

	@Override
	public String getTableName() {
		return "ShoppingCategory";
	}

	@Override
	public String getUserIdColumnName() {
		return "userId";
	}

}