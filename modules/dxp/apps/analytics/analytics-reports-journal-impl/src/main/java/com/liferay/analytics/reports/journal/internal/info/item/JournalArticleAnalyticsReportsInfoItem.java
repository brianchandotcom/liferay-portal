/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.reports.journal.internal.info.item;

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.comparator.ArticleVersionComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(service = AnalyticsReportsInfoItem.class)
public class JournalArticleAnalyticsReportsInfoItem
	implements AnalyticsReportsInfoItem<JournalArticle> {

	@Override
	public String getAuthorName(JournalArticle article) {
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

	@Override
	public String getKey() {
		return JournalArticle.class.getName();
	}

	@Override
	public Date getPublishDate(JournalArticle article) {
		return article.getDisplayDate();
	}

	@Override
	public String getTitle(JournalArticle article, Locale locale) {
		return article.getTitle(locale);
	}

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}