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

package com.liferay.blogs.web.internal.info.display.contributor;

import com.liferay.asset.info.display.contributor.BaseAssetInfoDisplayContributor;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.info.display.contributor.InfoDisplayContributor;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = InfoDisplayContributor.class)
public class BlogsEntryAssetInfoDisplayContributor
	extends BaseAssetInfoDisplayContributor<BlogsEntry> {

	@Override
	public String getClassName() {
		return BlogsEntry.class.getName();
	}

	@Override
	public String getInfoURLSeparator() {
		return "/b/";
	}

	@Override
	protected Map<String, Object> getClassTypeValues(
		BlogsEntry blogsEntry, Locale locale) {

		return new HashMap<>();
	}

}