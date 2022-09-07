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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Attila Bakay
 */
public class RedirectEntrySourceURLUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select redirectEntryId, groupId, sourceURL from " +
					"RedirectEntry ORDER BY redirectentryid ASC");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update RedirectEntry set sourceURL = ? where " +
						"redirectEntryId = ?")) {

			ResultSet resultSet = preparedStatement1.executeQuery();

			while (resultSet.next()) {
				long redirectEntryId = resultSet.getLong(1);
				long groupId = resultSet.getLong(2);
				String sourceURL = resultSet.getString(3);

				Set<String> groupSourceURLs;
				Map<String, Long> groupSourceURLEntryIdMap;

				if (!_sourceURLs.containsKey(groupId)) {
					groupSourceURLs = new HashSet<>();
					groupSourceURLEntryIdMap = new LinkedHashMap<>();
				}
				else {
					groupSourceURLs = _sourceURLs.get(groupId);
					groupSourceURLEntryIdMap = _sourceURLEntryIdMap.get(
						groupId);
				}

				String lowerCaseSourceURL = StringUtil.toLowerCase(sourceURL);

				if (!groupSourceURLs.contains(lowerCaseSourceURL)) {
					groupSourceURLs.add(lowerCaseSourceURL);

					if (!sourceURL.equals(lowerCaseSourceURL)) {
						groupSourceURLEntryIdMap.put(
							lowerCaseSourceURL, redirectEntryId);
					}

					_sourceURLEntryIdMap.put(groupId, groupSourceURLEntryIdMap);
					_sourceURLs.put(groupId, groupSourceURLs);
				}
				else {
					if (sourceURL.equals(lowerCaseSourceURL)) {
						groupSourceURLEntryIdMap.remove(lowerCaseSourceURL);
					}
					else if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Can not modify '", sourceURL, "' to '",
								lowerCaseSourceURL,
								"' because it is already in use in group: ",
								groupId, " at id: ", redirectEntryId));
					}
				}
			}

			for (Map<String, Long> groupSourceURLEntryIdMap :
					_sourceURLEntryIdMap.values()) {

				for (Map.Entry<String, Long> upgradeRedirectEntry :
						groupSourceURLEntryIdMap.entrySet()) {

					String lowerCaseSourceURL = upgradeRedirectEntry.getKey();
					long redirectEntryId = upgradeRedirectEntry.getValue();

					preparedStatement2.setString(1, lowerCaseSourceURL);
					preparedStatement2.setLong(2, redirectEntryId);

					preparedStatement2.addBatch();
				}
			}

			preparedStatement2.executeBatch();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RedirectEntrySourceURLUpgradeProcess.class);

	private final Map<Long, Map<String, Long>> _sourceURLEntryIdMap =
		new LinkedHashMap<>();
	private final Map<Long, Set<String>> _sourceURLs = new LinkedHashMap<>();

}