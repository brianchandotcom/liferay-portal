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

package com.liferay.journal.internal.model.listener;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.util.JournalContent;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.servlet.filters.cache.CacheUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Jon Steer
 * @author Raymond Augé
 */
@Component(immediate = true, service = ModelListener.class)
public class JournalArticleModelListener
	extends BaseModelListener<JournalArticle> {

	@Override
	public void onAfterRemove(JournalArticle journalArticle) {
		clearCache(journalArticle);
	}

	@Override
	public void onAfterUpdate(JournalArticle journalArticle) {
		clearCache(journalArticle);
	}

	protected void clearCache(JournalArticle journalArticle) {
		if (journalArticle == null) {
			return;
		}

		// Journal content

		_journalContent.clearCache(
			journalArticle.getGroupId(), journalArticle.getArticleId(),
			journalArticle.getDDMTemplateKey());

		// Layout cache

		CacheUtil.clearCache(journalArticle.getCompanyId());
	}

	@Reference(unbind = "-")
	protected void setJournalContent(JournalContent journalContent) {
		_journalContent = journalContent;
	}

	private JournalContent _journalContent;

}