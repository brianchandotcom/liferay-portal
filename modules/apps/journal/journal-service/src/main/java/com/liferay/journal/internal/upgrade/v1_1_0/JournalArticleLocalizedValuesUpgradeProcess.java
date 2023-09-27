/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v1_1_0;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Jürgen Kappler
 */
public class JournalArticleLocalizedValuesUpgradeProcess
	extends UpgradeProcess {

	public JournalArticleLocalizedValuesUpgradeProcess(
		CounterLocalService counterLocalService) {

		_counterLocalService = counterLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("JournalArticle", "title") ||
			!hasColumn("JournalArticle", "description")) {

			throw new IllegalStateException(
				"JournalArticle must have title and description columns");
		}

		_upgradeSchema();

		_updateJournalArticleDefaultLanguageId();

		_updateJournalArticleLocalizedFields();

		_dropTitleColumn();
		_dropDescriptionColumn();
	}

	private void _dropDescriptionColumn() throws Exception {
		try {
			alterTableDropColumn("JournalArticle", "description");
		}
		catch (SQLException sqlException) {
			if (_log.isDebugEnabled()) {
				_log.debug(sqlException);
			}
		}
	}

	private void _dropTitleColumn() throws Exception {
		try {
			alterTableDropColumn("JournalArticle", "title");
		}
		catch (SQLException sqlException) {
			if (_log.isDebugEnabled()) {
				_log.debug(sqlException);
			}
		}
	}

	private Map<Locale, String> _getLocalizationMap(
		String value, String defaultLanguageId) {

		if (Validator.isXml(value)) {
			return LocalizationUtil.getLocalizationMap(value);
		}

		return HashMapBuilder.put(
			LocaleUtil.fromLanguageId(defaultLanguageId), value
		).build();
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

	private void _updateDefaultLanguage(String columnName, boolean strictUpdate)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			Map<Long, Locale> defaultSiteLocales = new HashMap<>();

			processConcurrently(
				StringBundler.concat(
					"select id_, groupId, ", columnName,
					" from JournalArticle where defaultLanguageId is null or ",
					"defaultLanguageId = ''"),
				resultSet -> new Object[] {
					resultSet.getLong(1), resultSet.getLong(2),
					resultSet.getString(3)
				},
				values -> {
					String columnValue = (String)values[2];

					if (Validator.isXml(columnValue) || strictUpdate) {
						long groupId = (Long)values[1];

						Locale defaultSiteLocale = defaultSiteLocales.get(
							groupId);

						if (defaultSiteLocale == null) {
							defaultSiteLocale = PortalUtil.getSiteDefaultLocale(
								groupId);

							defaultSiteLocales.put(groupId, defaultSiteLocale);
						}

						long id = (Long)values[0];

						String defaultLanguageId =
							LocalizationUtil.getDefaultLanguageId(
								columnValue, defaultSiteLocale);

						try {
							runSQL(
								connection,
								StringBundler.concat(
									"update JournalArticle set ",
									"defaultLanguageId = '", defaultLanguageId,
									"' where id_ = ", id));
						}
						catch (Exception exception) {
							_log.error(
								"Unable to update default language ID for " +
									"article " + id,
								exception);

							throw exception;
						}
					}
				},
				"Unable to update journal article default language IDs");
		}
	}

	private void _updateJournalArticleDefaultLanguageId() throws Exception {
		alterTableAddColumn(
			"JournalArticle", "defaultLanguageId", "VARCHAR(75) null");

		_updateDefaultLanguage("title", false);
		_updateDefaultLanguage("content", true);
	}

	private void _updateJournalArticleLocalizedFields() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			processConcurrently(
				"select id_, companyId, title, description, " +
					"defaultLanguageId from JournalArticle",
				"insert into JournalArticleLocalization (" +
					"articleLocalizationId, companyId, articlePK, title, " +
						"description, languageId) values (?, ?, ?, ?, ?, ?)",
				resultSet -> new Object[] {
					resultSet.getLong(1), resultSet.getLong(2),
					resultSet.getString(3), resultSet.getString(4),
					resultSet.getString(5)
				},
				(values, preparedStatement) -> {
					long id = (Long)values[0];
					long companyId = (Long)values[1];

					String title = (String)values[2];
					String description = (String)values[3];
					String defaultLanguageId = (String)values[4];

					Map<Locale, String> titleMap = _getLocalizationMap(
						title, defaultLanguageId);
					Map<Locale, String> descriptionMap = _getLocalizationMap(
						description, defaultLanguageId);

					Set<Locale> locales = new HashSet<>();

					locales.addAll(titleMap.keySet());
					locales.addAll(descriptionMap.keySet());

					for (Locale locale : locales) {
						String localizedTitle = titleMap.get(locale);
						String localizedDescription = descriptionMap.get(
							locale);

						if ((localizedTitle != null) &&
							(localizedTitle.length() > _MAX_LENGTH_TITLE)) {

							localizedTitle = StringUtil.shorten(
								localizedTitle, _MAX_LENGTH_TITLE);

							_log(id, "title");
						}

						if (localizedDescription != null) {
							String safeLocalizedDescription = _truncate(
								localizedDescription, _MAX_LENGTH_DESCRIPTION);

							if (localizedDescription !=
									safeLocalizedDescription) {

								_log(id, "description");
							}

							localizedDescription = safeLocalizedDescription;
						}

						preparedStatement.setLong(
							1, _counterLocalService.increment());
						preparedStatement.setLong(2, companyId);
						preparedStatement.setLong(3, id);
						preparedStatement.setString(4, localizedTitle);
						preparedStatement.setString(5, localizedDescription);
						preparedStatement.setString(
							6, LocaleUtil.toLanguageId(locale));

						preparedStatement.addBatch();
					}
				},
				"Unable to update journal article localized fields");
		}
	}

	private void _upgradeSchema() throws Exception {
		dropTable("JournalArticleLocalization");

		String template = StringUtil.read(
			JournalArticleLocalizedValuesUpgradeProcess.class.
				getResourceAsStream("dependencies/update.sql"));

		runSQLTemplateString(template, false);
	}

	private static final int _MAX_LENGTH_DESCRIPTION = 4000;

	private static final int _MAX_LENGTH_TITLE = 800;

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleLocalizedValuesUpgradeProcess.class);

	private final CounterLocalService _counterLocalService;

}