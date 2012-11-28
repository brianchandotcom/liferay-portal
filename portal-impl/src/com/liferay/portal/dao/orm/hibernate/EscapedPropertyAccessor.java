/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.CharPool;

import org.hibernate.PropertyNotFoundException;
import org.hibernate.property.BasicPropertyAccessor;
import org.hibernate.property.Getter;
import org.hibernate.property.Setter;

/**
 * @author Shuyang Zhou
 */
public class EscapedPropertyAccessor extends BasicPropertyAccessor {

	@Override
	public Getter getGetter(
			@SuppressWarnings("rawtypes") Class clazz, String propertyName)
		throws PropertyNotFoundException {

		if (propertyName.charAt(propertyName.length() - 1) ==
				CharPool.UNDERLINE) {

			propertyName = propertyName.substring(0, propertyName.length() - 1);
		}

		return super.getGetter(clazz, propertyName);
	}

	@Override
	public Setter getSetter(
			@SuppressWarnings("rawtypes") Class clazz, String propertyName)
		throws PropertyNotFoundException {

		if (propertyName.charAt(propertyName.length() - 1) ==
				CharPool.UNDERLINE) {

			propertyName = propertyName.substring(0, propertyName.length() - 1);
		}

		return super.getSetter(clazz, propertyName);
	}

}