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

package com.liferay.portal.security.permission.comparator;

import com.liferay.portal.security.permission.ResourceActionsUtil;

import java.io.Serializable;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class ActionComparator implements Comparator<String>, Serializable {

	public ActionComparator(Locale locale) {
		_locale = locale;
	}

	@Override
	public int compare(String action1, String action2) {
		action1 = ResourceActionsUtil.getAction(_locale, action1);
		action2 = ResourceActionsUtil.getAction(_locale, action2);

		return action1.compareTo(action2);
	}

	private final Locale _locale;

}