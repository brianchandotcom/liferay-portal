/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.permission.comparator;

import com.liferay.portal.security.permission.ResourceActionsUtil;

import java.io.Serializable;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class ModelResourceComparator
	implements Comparator<String>, Serializable {

	public ModelResourceComparator(Locale locale) {
		_locale = locale;
	}

	@Override
	public int compare(String resource1, String resource2) {
		resource1 = ResourceActionsUtil.getModelResource(_locale, resource1);
		resource2 = ResourceActionsUtil.getModelResource(_locale, resource2);

		return resource1.compareTo(resource2);
	}

	private Locale _locale;

}