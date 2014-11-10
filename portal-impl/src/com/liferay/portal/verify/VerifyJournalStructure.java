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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;

/**
 * @author Michael C. Han
 */
public class VerifyJournalStructure extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyJournalArticleStructures();
	}

	protected void verifyJournalArticleStructures() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			JournalArticleLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					JournalArticle article = (JournalArticle)object;

					try {
						JournalArticleLocalServiceUtil.checkStructure(
							article.getGroupId(), article.getArticleId(),
							article.getVersion());
					}
					catch (NoSuchStructureException nsse) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Removing reference to missing structure for " +
									"article " + article.getId());
						}

						article.setDDMStructureKey(StringPool.BLANK);
						article.setDDMTemplateKey(StringPool.BLANK);

						JournalArticleLocalServiceUtil.updateJournalArticle(
							article);
					}
					catch (Exception e) {
						_log.error(
							"Unable to check the structure for article " +
								article.getId(),
							e);
					}
				}
			});

		long count = actionableDynamicQuery.performCount();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + count +
					" default article versions in draft mode");
		}

		actionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyJournalStructure.class);

}