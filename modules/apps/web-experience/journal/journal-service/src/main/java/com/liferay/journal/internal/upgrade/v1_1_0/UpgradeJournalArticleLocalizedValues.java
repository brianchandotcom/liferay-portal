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

package com.liferay.journal.internal.upgrade.v1_1_0;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Jürgen Kappler
 */
public class UpgradeJournalArticleLocalizedValues extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeSchema();

		updateJournalArticleDefaultLanguageId();
		updateJournalArticleLocalizedFields();

		dropTitleColumn();
		dropDescriptionColumn();
	}

	protected void dropDescriptionColumn() throws Exception {
		try {
			runSQL("alter table JournalArticle drop column description");
		}
		catch (SQLException sqle) {
			if (_log.isDebugEnabled()) {
				_log.debug(sqle, sqle);
			}
		}
	}

	protected void dropTitleColumn() throws Exception {
		try {
			runSQL("alter table JournalArticle drop column title");
		}
		catch (SQLException sqle) {
			if (_log.isDebugEnabled()) {
				_log.debug(sqle, sqle);
			}
		}
	}

	protected void updateJournalArticleDefaultLanguageId() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select id_, title from JournalArticle");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update JournalArticle set defaultLanguageId = ? where " +
						"id_ = ?");

			ResultSet rs = ps1.executeQuery()) {

			Locale defaultLocale = LocaleUtil.getSiteDefault();

			while (rs.next()) {
				String defaultLanguageId =
					LocalizationUtil.getDefaultLanguageId(
						rs.getString(2), defaultLocale);

				ps2.setString(1, defaultLanguageId);
				ps2.setLong(2, rs.getLong(1));

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	protected void updateJournalArticleLocalizedFields() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("insert into JournalArticleLocalization(");
		sb.append("articleLocalizationId, companyId, articlePK, title, ");
		sb.append("description, languageId) values(?, ?, ?, ?, ?, ?)");

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select id_, companyId, title, description from " +
					"JournalArticle");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb.toString());
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long articleId = rs.getLong(1);
				long companyId = rs.getLong(2);
				String title = rs.getString(3);
				String description = rs.getString(4);

				Map<Locale, String> titleMap =
					LocalizationUtil.getLocalizationMap(title);

				Map<Locale, String> descriptionMap =
					LocalizationUtil.getLocalizationMap(description);

				Set<Locale> localeSet = new HashSet<>();

				localeSet.addAll(titleMap.keySet());
				localeSet.addAll(descriptionMap.keySet());

				for (Locale locale : localeSet) {
					String articleTitle = titleMap.get(locale);
					String articleDescription = descriptionMap.get(locale);

					if ((articleTitle != null) &&
						(articleTitle.length() > _maxArticleTitleLength)) {

						articleTitle = articleTitle.substring(
							0, _maxArticleTitleLength);

						_logWarning(articleId, "Title");
					}

					if ((articleDescription != null) &&
						(articleDescription.length() >
							_maxArticleDescriptionLength)) {

						articleDescription = articleDescription.substring(
							0, _maxArticleDescriptionLength);

						_logWarning(articleId, "Description");
					}

					ps2.setLong(1, _increment());
					ps2.setLong(2, companyId);
					ps2.setLong(3, articleId);
					ps2.setString(4, articleTitle);
					ps2.setString(5, articleDescription);
					ps2.setString(6, LocaleUtil.toLanguageId(locale));

					ps2.addBatch();
				}
			}

			ps2.executeBatch();
		}
	}

	protected void upgradeSchema() throws Exception {
		String template = StringUtil.read(
			UpgradeJournalArticleLocalizedValues.class.getResourceAsStream(
				"dependencies/update.sql"));

		runSQLTemplateString(template, false, false);
	}

	private static long _increment() {
		DB db = DBManagerUtil.getDB();

		return db.increment();
	}

	private void _logWarning(long articleId, String field) {
		if (_log.isWarnEnabled()) {
			StringBundler sbInfo = new StringBundler(6);

			sbInfo.append(field);
			sbInfo.append(" for article");
			sbInfo.append(" with Id: ");
			sbInfo.append(articleId);
			sbInfo.append(" was truncated because it");
			sbInfo.append(" exceeded maximum length");

			_log.warn(sbInfo.toString());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeJournalArticleLocalizedValues.class);

	private static final int _maxArticleDescriptionLength = 4000;
	private static final int _maxArticleTitleLength = 400;

}