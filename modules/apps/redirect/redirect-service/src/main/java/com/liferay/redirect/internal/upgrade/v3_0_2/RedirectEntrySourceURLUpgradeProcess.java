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

package com.liferay.redirect.internal.upgrade.v3_0_2;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Attila Bakay
 */
public class RedirectEntrySourceURLUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select redirectEntryId, sourceURL from RedirectEntry");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update RedirectEntry set sourceURL = ? where " +
						"redirectEntryId = ?")) {

			ResultSet resultSet = preparedStatement1.executeQuery();

			while (resultSet.next()) {
				String sourceURL = resultSet.getString(2);

				String lowerCaseSourceURL = StringUtil.toLowerCase(sourceURL);

				if (sourceURL.equals(lowerCaseSourceURL)) {
					continue;
				}

				long redirectEntryId = resultSet.getLong(1);

				if (!_hasRedirectEntry(redirectEntryId, lowerCaseSourceURL)) {
					preparedStatement2.setString(1, lowerCaseSourceURL);
					preparedStatement2.setLong(2, redirectEntryId);

					preparedStatement2.addBatch();
				}
				else {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Can not modify '", sourceURL, "' to '",
								lowerCaseSourceURL,
								"' because it is already in use id:  at id: ",
								redirectEntryId));
					}
				}
			}

			preparedStatement2.executeBatch();
		}
	}

	private boolean _hasRedirectEntry(long redirectEntryId, String sourceURL)
		throws Exception {

		if (_duplicateSourceURLs.contains(sourceURL)) {
			return true;
		}

		_duplicateSourceURLs.add(sourceURL);

		try (PreparedStatement preparedStatement2 = connection.prepareStatement(
				"select count(redirectEntryId) from RedirectEntry  where " +
					"redirectEntryId != ? and sourceURL = ?")) {

			preparedStatement2.setLong(1, redirectEntryId);
			preparedStatement2.setString(2, sourceURL);

			try (ResultSet resultSet2 = preparedStatement2.executeQuery()) {
				while (resultSet2.next()) {
					long redirectEntryCount = resultSet2.getLong(1);

					if (redirectEntryCount > 0) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RedirectEntrySourceURLUpgradeProcess.class);

	private final Set<String> _duplicateSourceURLs = new HashSet<>();

}