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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;

import org.hibernate.property.access.internal.PropertyAccessFieldImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyFieldImpl;
import org.hibernate.property.access.spi.PropertyAccess;

/**
 * @author Dante Wang
 */
@SuppressWarnings("rawtypes")
public class PrivatePropertyAccessor extends PropertyAccessStrategyFieldImpl {

	@Override
	public PropertyAccess buildPropertyAccess(
		Class containerJavaType, String propertyName) {

		Class<?> superClass = null;

		while ((superClass = containerJavaType.getSuperclass()) !=
					BaseModelImpl.class) {

			containerJavaType = superClass;
		}

		propertyName = StringPool.UNDERLINE.concat(propertyName);

		return new PropertyAccessFieldImpl(
			this, containerJavaType, propertyName);
	}

}