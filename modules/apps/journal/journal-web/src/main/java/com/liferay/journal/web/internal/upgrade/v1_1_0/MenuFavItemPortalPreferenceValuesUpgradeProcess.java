/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.upgrade.v1_1_0;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Portal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lourdes Fernández Besada
 */
public class MenuFavItemPortalPreferenceValuesUpgradeProcess
	extends UpgradeProcess {

	public MenuFavItemPortalPreferenceValuesUpgradeProcess(
		ClassNameLocalService classNameLocalService,
		DDMStructureLocalService ddmStructureLocalService, Portal portal) {

		_classNameLocalService = classNameLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_portal = portal;
	}

	@Override
	protected void doUpgrade() throws Exception {
		Map<String, Map<Long, Long>> ddmStructureKeysMap =
			new ConcurrentHashMap<>();

		String keyPrefix = "journal-add-menu-fav-items-";

		int keyPrefixLength = keyPrefix.length();

		long classNameId = _classNameLocalService.getClassNameId(
			JournalArticle.class.getName());

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			processConcurrently(
				SQLTransformer.transform(
					StringBundler.concat(
						"select portalPreferenceValueId, key_, smallValue ",
						"from PortalPreferenceValue where ",
						"CAST_TEXT(namespace) = '", JournalPortletKeys.JOURNAL,
						"' and CAST_TEXT(key_) like '", keyPrefix, "%'")),
				"update PortalPreferenceValue set smallValue = ? where " +
					"portalPreferenceValueId = ?",
				resultSet -> new Object[] {
					resultSet.getLong(1),
					GetterUtil.getString(resultSet.getString(2)),
					GetterUtil.getString(resultSet.getString(3))
				},
				(values, preparedStatement) -> {
					String key = (String)values[1];
					String ddmStructureKey = (String)values[2];

					String groupIdFolderIdSuffix = key.substring(
						keyPrefixLength);

					String[] strings = groupIdFolderIdSuffix.split(
						StringPool.DASH);

					long groupId = GetterUtil.getLong(strings[0]);

					Map<Long, Long> groupIdsMap =
						ddmStructureKeysMap.computeIfAbsent(
							ddmStructureKey, key1 -> new ConcurrentHashMap<>());

					if (!groupIdsMap.containsKey(groupId)) {
						DDMStructure ddmStructure =
							_ddmStructureLocalService.fetchStructure(
								_portal.getSiteGroupId(groupId), classNameId,
								ddmStructureKey, true);

						if (ddmStructure != null) {
							groupIdsMap.put(
								groupId, ddmStructure.getStructureId());
						}
						else {
							groupIdsMap.put(groupId, 0L);
						}
					}

					long ddmStructureId = groupIdsMap.get(groupId);

					if (ddmStructureId == 0) {
						return;
					}

					preparedStatement.setString(
						1, String.valueOf(groupIdsMap.get(groupId)));
					preparedStatement.setLong(2, (Long)values[0]);

					preparedStatement.addBatch();
				},
				"Unable to update PortalPreferenceValue");
		}
	}

	private final ClassNameLocalService _classNameLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final Portal _portal;

}