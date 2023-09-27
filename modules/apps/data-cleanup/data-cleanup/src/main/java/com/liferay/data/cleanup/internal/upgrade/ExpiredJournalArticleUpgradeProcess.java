/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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