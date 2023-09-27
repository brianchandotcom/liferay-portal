/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.change.tracking.spi.search;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.spi.search.CTSearchExcludeModelClassPKContributor;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleTable;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = CTSearchExcludeModelClassPKContributor.class)
public class JournalArticleCTSearchExcludeModelClassPKContributor
	implements CTSearchExcludeModelClassPKContributor {

	@Override
	public void contribute(
		String className, long classPK,
		List<Long> excludeProductionModelClassPKs) {

		if (!className.equals(JournalArticle.class.getName())) {
			return;
		}

		List<JournalArticle> journalArticles =
			_journalArticleLocalService.dslQuery(
				DSLQueryFactoryUtil.select(
					JournalArticleTable.INSTANCE
				).from(
					JournalArticleTable.INSTANCE
				).where(
					JournalArticleTable.INSTANCE.ctCollectionId.eq(
						CTConstants.CT_COLLECTION_ID_PRODUCTION
					).and(
						JournalArticleTable.INSTANCE.resourcePrimKey.in(
							DSLQueryFactoryUtil.select(
								JournalArticleTable.INSTANCE.resourcePrimKey
							).from(
								JournalArticleTable.INSTANCE
							).where(
								JournalArticleTable.INSTANCE.id.eq(classPK)
							))
					)
				));

		for (JournalArticle journalArticle : journalArticles) {
			excludeProductionModelClassPKs.add(journalArticle.getId());
		}
	}

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

}