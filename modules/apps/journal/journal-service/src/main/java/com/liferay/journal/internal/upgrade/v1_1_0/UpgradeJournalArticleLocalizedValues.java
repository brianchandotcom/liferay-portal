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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
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
		if (!hasColumn("JournalArticle", "title") ||
			!hasColumn("JournalArticle", "description")) {

			throw new IllegalStateException(
				"JournalArticle must have title and description columns");
		}

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
		String whereClause = StringPool.BLANK;

		if (!hasColumn("JournalArticle", "defaultLanguageId")) {
			runSQL(
				"alter table JournalArticle add defaultLanguageId " +
					"VARCHAR(75) null");
		}
		else {
			whereClause =
				" where defaultLanguageId is null or defaultLanguageId = ''";
		}

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select id_, title from JournalArticle" + whereClause);
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
					String localizedTitle = titleMap.get(locale);
					String localizedDescription = descriptionMap.get(locale);

					if ((localizedTitle != null) &&
						(localizedTitle.length() > _MAX_LENGTH_TITLE)) {

						localizedTitle = StringUtil.shorten(
							localizedTitle, _MAX_LENGTH_TITLE);

						_log(articleId, "title");
					}

					if (localizedDescription != null) {
						String safeLocalizedDescription = _truncate(
							localizedDescription, _MAX_LENGTH_DESCRIPTION);

						if (localizedDescription != safeLocalizedDescription) {
							_log(articleId, "description");
						}
					}

					ps2.setLong(1, _increment());
					ps2.setLong(2, companyId);
					ps2.setLong(3, articleId);
					ps2.setString(4, localizedTitle);
					ps2.setString(5, localizedDescription);
					ps2.setString(6, LocaleUtil.toLanguageId(locale));

					ps2.addBatch();
				}
			}

			ps2.executeBatch();
		}
	}

	protected void upgradeSchema() throws Exception {
		if (hasTable("JournalArticleLocalization")) {
			runSQL("drop table JournalArticleLocalization");
		}

		String template = StringUtil.read(
			UpgradeJournalArticleLocalizedValues.class.getResourceAsStream(
				"dependencies/update.sql"));

		runSQLTemplateString(template, false, false);
	}

	private static long _increment() {
		DB db = DBManagerUtil.getDB();

		return db.increment();
	}

	private void _log(long articleId, String columnName) {
		if (!_log.isWarnEnabled()) {
			return;
		}

		_log.warn(
			StringBundler.concat(
				"Truncated the ", columnName, " value for article ", articleId,
				" because it is too long"));
	}

	private String _truncate(String text, int maxBytes) throws Exception {
		byte[] valueBytes = text.getBytes(StringPool.UTF8);

		if (valueBytes.length <= maxBytes) {
			return text;
		}

		byte[] convertedValue = new byte[maxBytes];

		System.arraycopy(valueBytes, 0, convertedValue, 0, maxBytes);

		String returnValue = new String(convertedValue, StringPool.UTF8);

		return StringUtil.shorten(returnValue, returnValue.length() - 1);
	}

	private static final int _MAX_LENGTH_DESCRIPTION = 4000;

	private static final int _MAX_LENGTH_TITLE = 400;

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeJournalArticleLocalizedValues.class);

}