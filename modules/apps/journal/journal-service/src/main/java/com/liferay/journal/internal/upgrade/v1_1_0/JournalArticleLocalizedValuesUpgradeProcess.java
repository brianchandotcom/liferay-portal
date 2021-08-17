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

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.BaseUpgradeCallable;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
		catch (SQLException sqlException) {
			if (_log.isDebugEnabled()) {
				_log.debug(sqlException, sqlException);
			}
		}
	}

	protected void dropTitleColumn() throws Exception {
		try {
			runSQL("alter table JournalArticle drop column title");
		}
		catch (SQLException sqlException) {
			if (_log.isDebugEnabled()) {
				_log.debug(sqlException, sqlException);
			}
		}
	}

	protected void updateJournalArticleDefaultLanguageId() throws Exception {
		if (!hasColumn("JournalArticle", "defaultLanguageId")) {
			runSQL(
				"alter table JournalArticle add defaultLanguageId " +
					"VARCHAR(75) null");
		}

		_updateDefaultLanguage("title", false);
		_updateDefaultLanguage("content", true);
	}

	protected void updateJournalArticleLocalizedFields() throws Exception {
		String sql =
			"insert into JournalArticleLocalization(articleLocalizationId, " +
				"companyId, articlePK, title, description, languageId) " +
					"values(?, ?, ?, ?, ?, ?)";

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					"select id_, companyId, title, description, " +
						"defaultLanguageId from JournalArticle");
			ResultSet resultSet = selectPreparedStatement.executeQuery()) {

			concurrentUpgrade(
				() -> {
					if (resultSet.next()) {
						return new Object[] {
							resultSet.getLong(1), resultSet.getLong(2),
							resultSet.getString(3), resultSet.getString(4),
							resultSet.getString(5)
						};
					}

					return null;
				},
				values -> {
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

					try (PreparedStatement updatePreparedStatement =
							AutoBatchPreparedStatementUtil.concurrentAutoBatch(
								connection, sql)) {

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
									localizedDescription,
									_MAX_LENGTH_DESCRIPTION);

								if (localizedDescription !=
										safeLocalizedDescription) {

									_log(id, "description");
								}

								localizedDescription = safeLocalizedDescription;
							}

							updatePreparedStatement.setLong(
								1, _counterLocalService.increment());
							updatePreparedStatement.setLong(2, companyId);
							updatePreparedStatement.setLong(3, id);
							updatePreparedStatement.setString(
								4, localizedTitle);
							updatePreparedStatement.setString(
								5, localizedDescription);
							updatePreparedStatement.setString(
								6, LocaleUtil.toLanguageId(locale));

							updatePreparedStatement.addBatch();
						}

						updatePreparedStatement.executeBatch();
					}
				});
		}
	}

	protected void upgradeSchema() throws Exception {
		if (hasTable("JournalArticleLocalization")) {
			runSQL("drop table JournalArticleLocalization");
		}

		String template = StringUtil.read(
			JournalArticleLocalizedValuesUpgradeProcess.class.
				getResourceAsStream("dependencies/update.sql"));

		runSQLTemplateString(template, false);
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

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select id_, groupId, ", columnName,
					" from JournalArticle where defaultLanguageId is null or ",
					"defaultLanguageId = ''"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			List<UpdateDefaultLanguageUpgradeCallable>
				updateDefaultLanguageCallables = new ArrayList<>();

			while (resultSet.next()) {
				String columnValue = resultSet.getString(3);

				if (Validator.isXml(columnValue) || strictUpdate) {
					long groupId = resultSet.getLong(2);

					Locale defaultSiteLocale = _defaultSiteLocales.get(groupId);

					if (defaultSiteLocale == null) {
						defaultSiteLocale = PortalUtil.getSiteDefaultLocale(
							groupId);

						_defaultSiteLocales.put(groupId, defaultSiteLocale);
					}

					UpdateDefaultLanguageUpgradeCallable
						updateDefaultLanguageCallable =
							new UpdateDefaultLanguageUpgradeCallable(
								resultSet.getLong(1), columnValue,
								defaultSiteLocale);

					updateDefaultLanguageCallables.add(
						updateDefaultLanguageCallable);
				}
			}

			ExecutorService executorService = Executors.newWorkStealingPool();

			List<Future<Boolean>> futures = executorService.invokeAll(
				updateDefaultLanguageCallables);

			executorService.shutdown();

			for (Future<Boolean> future : futures) {
				boolean success = GetterUtil.get(future.get(), true);

				if (!success) {
					throw new UpgradeException(
						"Unable to update journal article default language " +
							"IDs");
				}
			}
		}
	}

	private static final int _MAX_LENGTH_DESCRIPTION = 4000;

	private static final int _MAX_LENGTH_TITLE = 400;

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleLocalizedValuesUpgradeProcess.class);

	private final CounterLocalService _counterLocalService;
	private final Map<Long, Locale> _defaultSiteLocales = new HashMap<>();

	private class UpdateDefaultLanguageUpgradeCallable
		extends BaseUpgradeCallable<Boolean> {

		public UpdateDefaultLanguageUpgradeCallable(
			long id, String xml, Locale defaultSiteLocale) {

			_id = id;

			_xml = xml;

			_defaultLanguageId = LocalizationUtil.getDefaultLanguageId(
				_xml, defaultSiteLocale);
		}

		@Override
		protected Boolean doCall() throws Exception {
			try {
				runSQL(
					connection,
					StringBundler.concat(
						"update JournalArticle set defaultLanguageId = '",
						_defaultLanguageId, "' where id_ = ", _id));
			}
			catch (Exception exception) {
				_log.error(
					"Unable to update default language ID for article " + _id,
					exception);

				return false;
			}

			return true;
		}

		private final String _defaultLanguageId;
		private final long _id;
		private final String _xml;

	}

}