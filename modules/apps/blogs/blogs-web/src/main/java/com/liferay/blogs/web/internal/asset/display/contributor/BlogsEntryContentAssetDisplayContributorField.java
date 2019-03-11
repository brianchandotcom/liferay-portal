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

package com.liferay.blogs.web.internal.asset.display.contributor;

import com.liferay.asset.display.contributor.AssetDisplayContributorField;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "model.class.name=com.liferay.blogs.model.BlogsEntry",
	service = AssetDisplayContributorField.class
)
public class BlogsEntryContentAssetDisplayContributorField
	implements AssetDisplayContributorField<BlogsEntry> {

	@Override
	public String getKey() {
		return "content";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "content");
	}

	@Override
	public String getType() {
		return "text";
	}

	@Override
	public String getValue(BlogsEntry blogsEntry, Locale locale) {
		return blogsEntry.getContent();
	}

}