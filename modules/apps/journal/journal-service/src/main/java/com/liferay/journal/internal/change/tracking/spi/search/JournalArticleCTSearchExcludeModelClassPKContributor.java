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

package com.liferay.journal.internal.change.tracking.spi.search;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTEntry;
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
		String className, CTEntry ctEntry,
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
								JournalArticleTable.INSTANCE.id.eq(
									ctEntry.getModelClassPK())
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