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

package com.liferay.friendly.url.internal.upgrade.v3_1_1;

import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @deprecated As of Athanasius (7.3.x)
 * @author Ricardo Couso
 */
@Deprecated
public class FriendlyURLEntryLocalizationUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_addMissingFriendlyURLEntryLocalizations();
	}

	private void _addMissingFriendlyURLEntryLocalization(
			long ctCollectionId, long companyId, long friendlyURLEntryId,
			String languageId, String urlTitle, long groupId, long classPK)
		throws Exception {

		long friendlyURLEntryLocalizationId = increment(
			FriendlyURLEntryLocalization.class.getName());

		String uniqueURLTitle = _getUniqueURLTitle(
			ctCollectionId, urlTitle, groupId);

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"insert into FriendlyURLEntryLocalization (mvccVersion, ",
					"ctCollectionId, friendlyURLEntryLocalizationId, ",
					"companyId, friendlyURLEntryId, languageId, urlTitle, ",
					"groupId, classNameId, classPK) values (?, ?, ?, ?, ?, ?, ",
					"?, ?, ?, ?)"))) {

			preparedStatement.setLong(1, 0);
			preparedStatement.setLong(2, ctCollectionId);
			preparedStatement.setLong(3, friendlyURLEntryLocalizationId);
			preparedStatement.setLong(4, companyId);
			preparedStatement.setLong(5, friendlyURLEntryId);
			preparedStatement.setString(6, languageId);
			preparedStatement.setString(7, uniqueURLTitle);
			preparedStatement.setLong(8, groupId);
			preparedStatement.setLong(9, _CLASS_NAME_ID);
			preparedStatement.setLong(10, classPK);

			preparedStatement.executeUpdate();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to add friendly url entry localization", exception);
			}
		}
	}

	private void _addMissingFriendlyURLEntryLocalizations() throws Exception {
		try (Statement statement1 = connection.createStatement();
			ResultSet resultSet1 = statement1.executeQuery(
				StringBundler.concat(
					"select JournalArticle.id_, ",
					"JournalArticle.resourcePrimKey, JournalArticle.groupId, ",
					"JournalArticle.companyId from (select resourcePrimKey, ",
					"max(version) as latestVersion from JournalArticle group ",
					"by resourcePrimKey) LatestVersion inner join ",
					"JournalArticle on JournalArticle.resourcePrimKey = ",
					"LatestVersion.resourcePrimKey and JournalArticle.version ",
					"= LatestVersion.latestVersion"))) {

			while (resultSet1.next()) {
				long id = resultSet1.getLong(1);
				long resourcePrimKey = resultSet1.getLong(2);

				Map<String, String> urlTitleMap = new HashMap<>();

				try (Statement statement2 = connection.createStatement();
					ResultSet resultSet2 = statement2.executeQuery(
						StringBundler.concat(
							"select TEMP_TABLE_1.title, ",
							"TEMP_TABLE_1.languageId from (select title, ",
							"languageId from JournalArticleLocalization where ",
							"articlePK = ", id,
							") TEMP_TABLE_1 left join (select ",
							"friendlyURLEntryLocalizationId, languageId from ",
							"FriendlyURLEntryLocalization where classPK = ",
							resourcePrimKey, " and classNameId = ",
							_CLASS_NAME_ID,
							") TEMP_TABLE_2 on TEMP_TABLE_1.languageId = ",
							"TEMP_TABLE_2.languageId where ",
							"TEMP_TABLE_2.friendlyURLEntryLocalizationId is ",
							"null"))) {

					while (resultSet2.next()) {
						String title = resultSet2.getString(1);
						String languageId = resultSet2.getString(2);

						urlTitleMap.put(languageId, title);
					}

					if (urlTitleMap.isEmpty()) {
						continue;
					}

					long friendlyURLEntryId = _getFriendlyURLEntryId(
						resourcePrimKey);

					if (friendlyURLEntryId != -1) {
						long groupId = resultSet1.getLong(3);
						long companyId = resultSet1.getLong(4);

						urlTitleMap = _sortUrlTitleMap(groupId, urlTitleMap);

						long ctCollectionId =
							_getFriendlyURLEntryCTCollectionId(
								friendlyURLEntryId);

						for (Map.Entry<String, String> entry :
								urlTitleMap.entrySet()) {

							_addMissingFriendlyURLEntryLocalization(
								ctCollectionId, companyId, friendlyURLEntryId,
								entry.getKey(), entry.getValue(), groupId,
								resourcePrimKey);
						}
					}
					else {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Journal Article with id " + id +
									" has no associated FriendlyURLEntry.");
						}
					}
				}
			}
		}
	}

	private long _getFriendlyURLEntryCTCollectionId(long friendlyURLEntryId)
		throws SQLException {

		try (Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
				StringBundler.concat(
					"select ctCollectionId from FriendlyURLEntry where ",
					"friendlyURLEntryId = ", friendlyURLEntryId))) {

			if (resultSet.next()) {
				return resultSet.getLong(1);
			}
		}

		return -1;
	}

	private long _getFriendlyURLEntryId(long resourcePrimKey)
		throws SQLException {

		try (Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
				StringBundler.concat(
					"select friendlyURLEntryId from FriendlyURLEntryMapping ",
					"where classNameId = ", _CLASS_NAME_ID, " and classPK = ",
					resourcePrimKey))) {

			if (resultSet.next()) {
				return resultSet.getLong(1);
			}
		}

		return -1;
	}

	private int _getFriendlyURLEntryLocalizationsCount(
			long ctCollectionId, String urlTitle, long groupId)
		throws Exception {

		int count = 0;

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select count(*) from FriendlyURLEntryLocalization where ",
					"ctCollectionId = ", ctCollectionId, " and urlTitle = '",
					urlTitle, "' and groupId = ", groupId,
					" and classNameId = ", _CLASS_NAME_ID));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}
		}

		return count;
	}

	private String _getUniqueURLTitle(
			long ctCollectionId, String urlTitle, long groupId)
		throws Exception {

		String normalizedUrlTitle =
			FriendlyURLNormalizerUtil.normalizeWithEncoding(urlTitle);

		int maxLength = 255;

		String curUrlTitle = _getURLEncodedSubstring(
			urlTitle, normalizedUrlTitle, maxLength);

		String prefix = curUrlTitle;

		for (int i = 1;; i++) {
			int count = _getFriendlyURLEntryLocalizationsCount(
				ctCollectionId, curUrlTitle, groupId);

			if (count == 0) {
				break;
			}

			String suffix = StringPool.DASH + i;

			prefix = _getURLEncodedSubstring(
				urlTitle, prefix, maxLength - suffix.length());

			curUrlTitle = FriendlyURLNormalizerUtil.normalizeWithEncoding(
				prefix + suffix);
		}

		return curUrlTitle;
	}

	private String _getURLEncodedSubstring(
		String decodedString, String encodedString, int maxLength) {

		int endPos = decodedString.length();

		while (encodedString.length() > maxLength) {
			endPos--;

			if ((endPos > 0) &&
				Character.isHighSurrogate(decodedString.charAt(endPos - 1))) {

				endPos--;
			}

			encodedString = FriendlyURLNormalizerUtil.normalizeWithEncoding(
				decodedString.substring(0, endPos));
		}

		return encodedString;
	}

	private Map<String, String> _sortUrlTitleMap(
		long groupId, Map<String, String> urlTitleMap) {

		Map<String, String> sortedUrlTitleMap = new LinkedHashMap<>();

		for (Locale locale : LanguageUtil.getAvailableLocales(groupId)) {
			String languageId = LocaleUtil.toLanguageId(locale);

			String value = urlTitleMap.get(languageId);

			if (value == null) {
				continue;
			}

			sortedUrlTitleMap.put(languageId, value);
		}

		return sortedUrlTitleMap;
	}

	private static final long _CLASS_NAME_ID = PortalUtil.getClassNameId(
		"com.liferay.journal.model.JournalArticle");

	private static final Log _log = LogFactoryUtil.getLog(
		FriendlyURLEntryLocalizationUpgradeProcess.class);

}