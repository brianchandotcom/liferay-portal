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

package com.liferay.journal.internal.change.tracking.spi.listener;

import com.liferay.change.tracking.model.CTEntryTable;
import com.liferay.change.tracking.spi.listener.CTEventListener;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleTable;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(
	property = "service.ranking:Integer=100", service = CTEventListener.class
)
public class JournalArticleCTSearchEventListener implements CTEventListener {

	@Override
	public void onAfterPublish(long ctCollectionId) {
		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				List<JournalArticle> journalArticles = null;

				try (SafeCloseable safeCloseable =
						CTCollectionThreadLocal.
							setCTCollectionIdWithSafeCloseable(
								ctCollectionId)) {

					journalArticles = _journalArticleLocalService.dslQuery(
						DSLQueryFactoryUtil.select(
							JournalArticleTable.INSTANCE
						).from(
							JournalArticleTable.INSTANCE
						).innerJoinON(
							CTEntryTable.INSTANCE,
							CTEntryTable.INSTANCE.modelClassPK.eq(
								JournalArticleTable.INSTANCE.id)
						).where(
							JournalArticleTable.INSTANCE.ctCollectionId.eq(
								ctCollectionId)
						));
				}

				_journalArticleIndexer.reindex(journalArticles);

				return null;
			});
	}

	@Reference(
		target = "(indexer.class.name=com.liferay.journal.model.JournalArticle)"
	)
	private Indexer<JournalArticle> _journalArticleIndexer;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

}