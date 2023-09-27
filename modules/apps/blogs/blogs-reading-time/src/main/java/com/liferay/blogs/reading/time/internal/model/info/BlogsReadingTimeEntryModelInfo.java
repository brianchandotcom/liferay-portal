/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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