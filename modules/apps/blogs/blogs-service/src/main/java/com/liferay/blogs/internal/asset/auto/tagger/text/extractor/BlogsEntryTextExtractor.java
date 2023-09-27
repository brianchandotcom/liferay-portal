/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.internal.asset.auto.tagger.text.extractor;

import com.liferay.asset.auto.tagger.text.extractor.TextExtractor;
import com.liferay.blogs.model.BlogsEntry;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alicia García
 * @author Alejandro Tardín
 */
@Component(service = TextExtractor.class)
public class BlogsEntryTextExtractor implements TextExtractor<BlogsEntry> {

	@Override
	public String extract(BlogsEntry blogsEntry, Locale locale) {
		return blogsEntry.getContent();
	}

	@Override
	public String getClassName() {
		return BlogsEntry.class.getName();
	}

}