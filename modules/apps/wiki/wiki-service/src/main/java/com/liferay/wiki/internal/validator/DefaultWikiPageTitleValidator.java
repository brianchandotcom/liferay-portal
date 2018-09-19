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

package com.liferay.wiki.internal.validator;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.wiki.exception.PageTitleException;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.validator.WikiPageTitleValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(immediate = true, service = WikiPageTitleValidator.class)
public class DefaultWikiPageTitleValidator implements WikiPageTitleValidator {

	@Override
	public String normalize(String title) {
		Matcher matcher = _pageTitleRemovePattern.matcher(title);

		title = matcher.replaceAll(StringPool.BLANK);

		return StringUtil.shorten(title, 255);
	}

	@Override
	public void validate(String title) throws PageTitleException {
		int titleMaxLength = ModelHintsUtil.getMaxLength(
			WikiPage.class.getName(), "title");

		if (title.length() > titleMaxLength) {
			throw new PageTitleException(
				"Title has more than " + titleMaxLength + " characters");
		}

		if (title.equals("all_pages") || title.equals("orphan_pages") ||
			title.equals("recent_changes")) {

			throw new PageTitleException(title + " is reserved");
		}

		Matcher matcher = _pageTitlePattern.matcher(title);

		if (!matcher.matches()) {
			throw new PageTitleException();
		}
	}

	private static final Pattern _pageTitlePattern = Pattern.compile(
		"[^\\\\\\[\\]\\|:;%<>]+");
	private static final Pattern _pageTitleRemovePattern = Pattern.compile(
		"[\\\\\\[\\]\\|:;%<>]+");

}