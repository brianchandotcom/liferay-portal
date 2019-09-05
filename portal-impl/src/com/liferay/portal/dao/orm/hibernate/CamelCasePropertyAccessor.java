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

/**
 * @author Brian Wing Shun Chan
 */
public class CamelCasePropertyAccessor extends LiferayPropertyAccessor {

	@Override
	protected String formatPropertyName(String propertyName) {
		if (propertyName.length() < 3) {
			return super.formatPropertyName(propertyName);
		}

		char c0 = propertyName.charAt(0);
		char c1 = propertyName.charAt(1);
		char c2 = propertyName.charAt(2);

		if (Character.isLowerCase(c0) && Character.isUpperCase(c1) &&
			Character.isLowerCase(c2)) {

			propertyName =
				Character.toUpperCase(c0) + propertyName.substring(1);
		}

		return super.formatPropertyName(propertyName);
	}

}