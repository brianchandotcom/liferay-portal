/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.upgrade;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

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
		ActionableDynamicQuery actionableDynamicQuery =
			_journalArticleLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property property = PropertyFactoryUtil.forName("status");

				dynamicQuery.add(property.eq(WorkflowConstants.STATUS_EXPIRED));
			});
		actionableDynamicQuery.setInterval(
			PropsValues.UPGRADE_CONCURRENT_FETCH_SIZE);
		actionableDynamicQuery.setPerformActionMethod(
			(JournalArticle journalArticle) -> {
				try {
					_journalArticleLocalService.deleteArticle(journalArticle);
				}
				catch (Exception exception) {
					_log.error(
						"Unable to delete expired journal article " +
							journalArticle.getId(),
						exception);
				}
			});

		actionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExpiredJournalArticleUpgradeProcess.class);

	private final JournalArticleLocalService _journalArticleLocalService;

}