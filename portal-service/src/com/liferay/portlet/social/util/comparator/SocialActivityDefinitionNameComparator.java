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

package com.liferay.portlet.social.util.comparator;

import com.liferay.portlet.social.model.SocialActivityDefinition;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class SocialActivityDefinitionNameComparator
	implements Comparator<SocialActivityDefinition> {

	public SocialActivityDefinitionNameComparator(Locale locale) {
		_locale = locale;
	}

	@Override
	public int compare(
		SocialActivityDefinition activityDefinition1,
		SocialActivityDefinition activityDefinition2) {

		String name1 = activityDefinition1.getName(_locale);
		String name2 = activityDefinition2.getName(_locale);

		return name1.compareTo(name2);
	}

	private Locale _locale;

}