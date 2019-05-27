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

package com.liferay.journal.web.internal.info.display.contributor.field;

import com.liferay.info.display.contributor.field.InfoDisplayContributorField;
import com.liferay.info.display.contributor.field.InfoDisplayContributorFieldType;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.comparator.ArticleVersionComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "model.class.name=com.liferay.journal.model.JournalArticle",
	service = InfoDisplayContributorField.class
)
public class JournalArticleAuthorNameInfoDisplayContributorField
	implements InfoDisplayContributorField<JournalArticle> {

	@Override
	public String getKey() {
		return "authorName";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return LanguageUtil.get(resourceBundle, "author-name");
	}

	@Override
	public InfoDisplayContributorFieldType getType() {
		return InfoDisplayContributorFieldType.TEXT;
	}

	@Override
	public String getValue(JournalArticle article, Locale locale) {
		List<JournalArticle> articles = _journalArticleLocalService.getArticles(
			article.getGroupId(), article.getArticleId(), 0, 1,
			new ArticleVersionComparator(true));

		article = articles.get(0);

		User user = _userLocalService.fetchUser(article.getUserId());

		if (user != null) {
			return user.getFullName();
		}

		return StringPool.BLANK;
	}

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}