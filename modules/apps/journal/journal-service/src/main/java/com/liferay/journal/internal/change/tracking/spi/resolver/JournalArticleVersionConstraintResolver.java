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

package com.liferay.journal.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.comparator.ArticleVersionComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.MathUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageResources;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = ConstraintResolver.class)
public class JournalArticleVersionConstraintResolver
	implements ConstraintResolver<JournalArticle> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-article-version";
	}

	@Override
	public Class<JournalArticle> getModelClass() {
		return JournalArticle.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		return "the-article-version-was-updated-to-latest";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return LanguageResources.getResourceBundle(locale);
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"groupId", "articleId", "version"};
	}

	@Override
	public void resolveConflict(
			ConstraintResolverContext<JournalArticle> constraintResolverContext)
		throws PortalException {

		JournalArticle ctArticle = constraintResolverContext.getSourceCTModel();

		double latestVersion = constraintResolverContext.getInTarget(
			() -> {
				JournalArticle latestProductionArticle =
					_journalArticleLocalService.getLatestArticle(
						ctArticle.getResourcePrimKey(),
						WorkflowConstants.STATUS_ANY, false);

				return latestProductionArticle.getVersion();
			});

		List<JournalArticle> articles = _journalArticleLocalService.getArticles(
			ctArticle.getGroupId(), ctArticle.getArticleId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new ArticleVersionComparator());

		for (JournalArticle article : articles) {
			if (article.getCtCollectionId() == ctArticle.getCtCollectionId()) {
				latestVersion = MathUtil.format(latestVersion + 0.1, 1, 1);

				article.setVersion(latestVersion);

				_journalArticleLocalService.updateJournalArticle(article);
			}
		}
	}

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

}