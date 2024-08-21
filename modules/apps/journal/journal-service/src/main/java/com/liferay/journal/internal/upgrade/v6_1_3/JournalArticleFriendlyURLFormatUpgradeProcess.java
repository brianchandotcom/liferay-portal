/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v6_1_3;

import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Joao Victor Alves
 */
public class JournalArticleFriendlyURLFormatUpgradeProcess
	extends UpgradeProcess {

	public JournalArticleFriendlyURLFormatUpgradeProcess(
		ClassNameLocalService classNameLocalService,
		FriendlyURLEntryLocalService friendlyURLEntryLocalService) {

		_classNameLocalService = classNameLocalService;
		_friendlyURLEntryLocalService = friendlyURLEntryLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (
			PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select distinct classPK, ctCollectionId, ",
					"friendlyURLEntryId, groupId, languageId, urlTitle from ",
					"FriendlyURLEntryLocalization where urlTitle like '%/' ",
					"and classNameId = ?")
			);

			PreparedStatement preparedStatement2 = connection.prepareStatement(
				"select defaultLanguageId from JournalArticle where " +
				"resourcePrimKey = ?"
			);

			PreparedStatement preparedStatement3 = connection.prepareStatement(
				"update JournalArticle set urlTitle = ? where " +
				"resourcePrimKey = ? and ctCollectionId = ?"
			)
		) {

			long classNameId = _classNameLocalService.getClassNameId(
				JournalArticle.class);

			preparedStatement1.setLong(1, classNameId);

			preparedStatement1.addBatch();

			ResultSet resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				long classPK = resultSet1.getLong(1);

				long friendlyURLEntryId = resultSet1.getLong(3);

				long groupId = resultSet1.getLong(4);

				String languageId = resultSet1.getString(5);

				String urlTitle = resultSet1.getString(6);

				while (urlTitle.endsWith("/")) {
					urlTitle = urlTitle.substring(0, urlTitle.length() - 1);
				}

				urlTitle = _friendlyURLEntryLocalService.getUniqueUrlTitle(
					groupId, classNameId, classPK, urlTitle, languageId);

				preparedStatement2.setLong(1, classPK);
				preparedStatement2.addBatch();

				ResultSet resultSet2 = preparedStatement2.executeQuery();

				resultSet2.next();

				String defaultLanguageId = resultSet2.getString(1);

				if (defaultLanguageId.equals(languageId)) {
					long ctCollectionId = resultSet1.getLong(2);

					preparedStatement3.setString(1, urlTitle);
					preparedStatement3.setLong(2, classPK);
					preparedStatement3.setLong(3, ctCollectionId);

					preparedStatement3.addBatch();
					preparedStatement3.executeBatch();
				}

				FriendlyURLEntry friendlyURLEntry =
					_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
						friendlyURLEntryId);

				_friendlyURLEntryLocalService.
					updateFriendlyURLEntryLocalization(
						friendlyURLEntry, languageId, urlTitle);
			}
		}
	}

	private final ClassNameLocalService _classNameLocalService;
	private final FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

}