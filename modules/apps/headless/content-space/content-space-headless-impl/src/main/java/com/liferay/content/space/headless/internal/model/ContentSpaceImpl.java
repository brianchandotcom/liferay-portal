/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.content.space.headless.internal.model;

import com.liferay.content.space.headless.internal.util.ContentSpaceUtil;
import com.liferay.content.space.headless.model.ContentSpace;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;


/**
 * @author Ruben Pulido
 */

public class ContentSpaceImpl implements ContentSpace {

	public ContentSpaceImpl(Group group) {
		_group = group;
	}

	@Override
	public List<String> getAvailableLanguages() {
		return Arrays.asList(
			LocaleUtil.toW3cLanguageIds(_group.getAvailableLanguageIds()));
	}

	@Override
	public String getDescription(Locale locale) {
		return _group.getDescription(locale);
	}

	@Override
	public long getId() {
		return _group.getGroupId();
	}

	@Override
	public String getName(Locale locale) {
		return ContentSpaceUtil.getName(_group, locale);
	}

	private final Group _group;

}
