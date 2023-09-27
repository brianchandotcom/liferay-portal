/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = WikiPageTitleValidator.class)
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