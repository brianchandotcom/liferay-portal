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

package com.liferay.journal.internal.upgrade.v4_4_0;

import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Lourdes Fernández Besada
 */
public class GlobalJournalArticleUrlTitleUpgradeProcess extends UpgradeProcess {

	public GlobalJournalArticleUrlTitleUpgradeProcess(
		ClassNameLocalService classNameLocalService,
		CompanyLocalService companyLocalService,
		GroupLocalService groupLocalService) {

		_classNameLocalService = classNameLocalService;
		_companyLocalService = companyLocalService;
		_groupLocalService = groupLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_updateGlobalJournalArticles();
	}

	private String _getUrlTitle(
			long classNameId, long classPK, long groupId, String languageId)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select urlTitle from FriendlyURLEntryLocalization where " +
					"languageId = ? and groupId = ? and classNameId = ? and " +
						"classPK = ?")) {

			preparedStatement.setString(1, languageId);
			preparedStatement.setLong(2, groupId);
			preparedStatement.setLong(3, classNameId);
			preparedStatement.setLong(4, classPK);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getString("urlTitle");
				}
			}
		}

		return StringPool.BLANK;
	}

	private void _updateGlobalJournalArticles() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long classNameId = _classNameLocalService.getClassNameId(
				JournalArticle.class.getName());

			_companyLocalService.forEachCompanyId(
				companyId -> _updateGlobalJournalArticles(
					classNameId, companyId));
		}
	}

	private void _updateGlobalJournalArticles(long classNameId, long companyId)
		throws Exception {

		Group companyGroup = _groupLocalService.getCompanyGroup(companyId);

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select id_, resourcePrimKey, defaultLanguageId from " +
					"JournalArticle where groupId = ? and (urlTitle is null " +
						"or urlTitle = '')");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update JournalArticle set urlTitle = ? where id_ = ?")) {

			preparedStatement1.setLong(1, companyGroup.getGroupId());

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					long classPK = resultSet.getLong("resourcePrimKey");
					String defaultLanguageId = resultSet.getString(
						"defaultLanguageId");

					preparedStatement2.setString(
						1,
						_getUrlTitle(
							classNameId, classPK, companyGroup.getGroupId(),
							defaultLanguageId));

					long id = resultSet.getLong("id_");

					preparedStatement2.setLong(2, id);

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private final ClassNameLocalService _classNameLocalService;
	private final CompanyLocalService _companyLocalService;
	private final GroupLocalService _groupLocalService;

}