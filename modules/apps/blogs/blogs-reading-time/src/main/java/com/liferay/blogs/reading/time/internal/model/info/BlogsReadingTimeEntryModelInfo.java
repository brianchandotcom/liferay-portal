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

package com.liferay.blogs.reading.time.internal.model.info;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.reading.time.calculator.ReadingTimeModelInfo;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.blogs.model.BlogsEntry",
	service = ReadingTimeModelInfo.class
)
public class BlogsReadingTimeEntryModelInfo
	implements ReadingTimeModelInfo<BlogsEntry> {

	@Override
	public String getContent(BlogsEntry blogsEntry) {
		return blogsEntry.getContent();
	}

	@Override
	public String getContentType(BlogsEntry blogsEntry) {
		return ContentTypes.TEXT_HTML;
	}

	@Override
	public Locale getLocale(BlogsEntry blogsEntry) {
		return LocaleUtil.getDefault();
	}

}