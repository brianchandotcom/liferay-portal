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

package com.liferay.portlet.expando.util.comparator;

import com.liferay.expando.kernel.model.CustomAttributesDisplay;
import com.liferay.portal.kernel.security.permission.comparator.ModelResourceComparator;

import java.io.Serializable;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class CustomAttributesDisplayComparator
	implements Comparator<CustomAttributesDisplay>, Serializable {

	public CustomAttributesDisplayComparator(Locale locale) {
		_modelResourceComparator = new ModelResourceComparator(locale);
	}

	@Override
	public int compare(
		CustomAttributesDisplay customAttributesDisplay1,
		CustomAttributesDisplay customAttributesDisplay2) {

		return _modelResourceComparator.compare(
			customAttributesDisplay1.getClassName(),
			customAttributesDisplay2.getClassName());
	}

	private final ModelResourceComparator _modelResourceComparator;

}