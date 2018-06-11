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

import org.hibernate.PropertyNotFoundException;
import org.hibernate.property.Getter;
import org.hibernate.property.Setter;

/**
 * @author Brian Wing Shun Chan
 */
@SuppressWarnings("rawtypes")
public class CamelCasePropertyAccessor extends LiferayPropertyAccessor {

	@Override
	public Getter getGetter(Class clazz, String propertyName)
		throws PropertyNotFoundException {

		propertyName = fixPropertyName(propertyName);

		return super.getGetter(clazz, propertyName);
	}

	@Override
	public Setter getSetter(Class clazz, String propertyName)
		throws PropertyNotFoundException {

		propertyName = fixPropertyName(propertyName);

		return super.getSetter(clazz, propertyName);
	}

	protected String fixPropertyName(String propertyName) {
		if (propertyName.length() < 3) {
			return propertyName;
		}

		char c0 = propertyName.charAt(0);
		char c1 = propertyName.charAt(1);
		char c2 = propertyName.charAt(2);

		if (Character.isLowerCase(c0) && Character.isUpperCase(c1) &&
			Character.isLowerCase(c2)) {

			return Character.toUpperCase(c0) + propertyName.substring(1);
		}

		return propertyName;
	}

}