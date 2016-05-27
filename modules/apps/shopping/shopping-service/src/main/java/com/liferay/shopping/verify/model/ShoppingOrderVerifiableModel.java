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
import com.liferay.shopping.model.ShoppingOrder;
import com.liferay.shopping.model.impl.ShoppingOrderModelImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingOrderVerifiableModel implements VerifiableResourcedModel {

	@Override
	public String getModelName() {
		return ShoppingOrder.class.getName();
	}

	@Override
	public String getPrimaryKeyColumnName() {
		return "orderId";
	}

	@Override
	public String getTableName() {
		return ShoppingOrderModelImpl.TABLE_NAME;
	}

	@Override
	public String getUserIdColumnName() {
		return "userId";
	}

}