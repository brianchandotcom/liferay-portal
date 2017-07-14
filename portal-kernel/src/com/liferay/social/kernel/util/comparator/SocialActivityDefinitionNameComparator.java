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

package com.liferay.social.kernel.util.comparator;

import com.liferay.portal.kernel.util.CollatorUtil;
import com.liferay.social.kernel.model.SocialActivityDefinition;

import java.text.Collator;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class SocialActivityDefinitionNameComparator
	implements Comparator<SocialActivityDefinition> {

	public SocialActivityDefinitionNameComparator(Locale locale) {
		_locale = locale;

		_collator = CollatorUtil.getInstance(_locale);
	}

	@Override
	public int compare(
		SocialActivityDefinition activityDefinition1,
		SocialActivityDefinition activityDefinition2) {

		String name1 = activityDefinition1.getName(_locale);
		String name2 = activityDefinition2.getName(_locale);

		return _collator.compare(name1, name2);
	}

	private final Collator _collator;
	private final Locale _locale;

}