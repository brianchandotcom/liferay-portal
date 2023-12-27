/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.search.spi.model.result.contributor;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.search.spi.model.result.contributor.ModelVisibilityContributor;

import java.util.List;

/**
 * @author Lourdes Fernández Besada
 */
public class JournalArticleModelVisibilityContributor
	implements ModelVisibilityContributor {

	public JournalArticleModelVisibilityContributor(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	@Override
	public boolean isVisible(long classPK, int status) {
		List<JournalArticle> articles =
			_journalArticleLocalService.getArticlesByResourcePrimKey(classPK);

		for (JournalArticle article : articles) {
			if (isVisible(article.getStatus(), status)) {
				return true;
			}
		}

		return false;
	}

	private final JournalArticleLocalService _journalArticleLocalService;

}