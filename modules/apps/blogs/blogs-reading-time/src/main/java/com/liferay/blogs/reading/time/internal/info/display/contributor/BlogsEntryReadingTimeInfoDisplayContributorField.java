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

package com.liferay.blogs.reading.time.internal.info.display.contributor;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.info.display.contributor.InfoDisplayContributorField;
import com.liferay.info.display.contributor.InfoDisplayContributorFieldType;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.reading.time.message.ReadingTimeMessageProvider;
import com.liferay.reading.time.model.ReadingTimeEntry;
import com.liferay.reading.time.service.ReadingTimeEntryLocalService;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "model.class.name=com.liferay.blogs.model.BlogsEntry",
	service = InfoDisplayContributorField.class
)
public class BlogsEntryReadingTimeInfoDisplayContributorField
	implements InfoDisplayContributorField<BlogsEntry> {

	@Override
	public String getKey() {
		return "readingTime";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return LanguageUtil.get(resourceBundle, "reading-time");
	}

	@Override
	public InfoDisplayContributorFieldType getType() {
		return InfoDisplayContributorFieldType.TEXT;
	}

	@Override
	public String getValue(BlogsEntry blogsEntry, Locale locale) {
		ReadingTimeEntry readingTimeEntry =
			_readingTimeEntryLocalService.fetchOrAddReadingTimeEntry(
				blogsEntry);

		return _readingTimeMessageProvider.provide(readingTimeEntry, locale);
	}

	@Reference
	private ReadingTimeEntryLocalService _readingTimeEntryLocalService;

	@Reference
	private ReadingTimeMessageProvider _readingTimeMessageProvider;

}