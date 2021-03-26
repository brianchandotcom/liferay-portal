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

package com.liferay.data.cleanup.internal.upgrade;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

/**
 * @author Kevin Lee
 */
public class ExpiredJournalArticleUpgradeProcess extends UpgradeProcess {

	public ExpiredJournalArticleUpgradeProcess(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		DynamicQuery dynamicQuery = _journalArticleLocalService.dynamicQuery();

		Property property = PropertyFactoryUtil.forName("status");

		dynamicQuery.add(property.eq(WorkflowConstants.STATUS_EXPIRED));

		List<JournalArticle> journalArticles =
			_journalArticleLocalService.dynamicQuery(dynamicQuery);

		for (JournalArticle journalArticle : journalArticles) {
			_journalArticleLocalService.deleteArticle(journalArticle);
		}
	}

	private final JournalArticleLocalService _journalArticleLocalService;

}