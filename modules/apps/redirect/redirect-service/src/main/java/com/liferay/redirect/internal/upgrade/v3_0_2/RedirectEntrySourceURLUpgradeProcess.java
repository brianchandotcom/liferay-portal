/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		Map<Long, Map<String, Long>> redirectEntryIdsMap =
			new LinkedHashMap<>();
		Map<Long, Set<String>> sourceURLsMap = new LinkedHashMap<>();

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select redirectEntryId, groupId, sourceURL from " +
					"RedirectEntry order by redirectEntryId asc");
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

				Map<String, Long> redirectEntryIds =
					redirectEntryIdsMap.computeIfAbsent(
						groupId, key -> new LinkedHashMap<>());
				Set<String> sourceURLs = sourceURLsMap.computeIfAbsent(
					groupId, key -> new HashSet<>());

				String lowerCaseSourceURL = StringUtil.toLowerCase(sourceURL);

				if (sourceURLs.add(lowerCaseSourceURL)) {
					if (!sourceURL.equals(lowerCaseSourceURL)) {
						redirectEntryIds.put(
							lowerCaseSourceURL, redirectEntryId);
					}

					continue;
				}

				if (sourceURL.equals(lowerCaseSourceURL)) {
					redirectEntryIds.remove(lowerCaseSourceURL);
				}
				else {
					_log.error(
						StringBundler.concat(
							"Unable to modify ", sourceURL, " to ",
							lowerCaseSourceURL,
							" because it is already used by redirect entry ",
							redirectEntryId));
				}
			}

			for (Map<String, Long> redirectEntryIds :
					redirectEntryIdsMap.values()) {

				for (Map.Entry<String, Long> entry :
						redirectEntryIds.entrySet()) {

					preparedStatement2.setString(1, entry.getKey());
					preparedStatement2.setLong(2, entry.getValue());

					preparedStatement2.addBatch();
				}
			}

			preparedStatement2.executeBatch();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RedirectEntrySourceURLUpgradeProcess.class);

}