/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.upgrade.v5_7_1;

import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Igor Costa
 */
public class LayoutPageTemplateStructureRelUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			processConcurrently(
				StringBundler.concat(
					"select ctCollectionId, lPageTemplateStructureRelId, ",
					"data_ from LayoutPageTemplateStructureRel where data_ ",
					"like '%", _CLASS_NAME, "%' "),
				"update LayoutPageTemplateStructureRel set data_ = ? where " +
					"ctCollectionId = ? and lPageTemplateStructureRelId = ?",
				resultSet -> new Object[] {
					resultSet.getLong("ctCollectionId"),
					resultSet.getLong("lPageTemplateStructureRelId"),
					GetterUtil.getString(resultSet.getString("data_"))
				},
				(values, preparedStatement) -> {
					JSONObject dataJSONObject =
						JSONFactoryUtil.createJSONObject((String)values[2]);

					JSONObject itemsJSONObject = dataJSONObject.getJSONObject(
						"items");

					for (String key : itemsJSONObject.keySet()) {
						JSONObject itemJSONObject =
							itemsJSONObject.getJSONObject(key);

						if (!StringUtil.equals(
								itemJSONObject.getString("type"),
								LayoutDataItemTypeConstants.TYPE_COLLECTION)) {

							continue;
						}

						JSONObject configJSONObject =
							itemJSONObject.getJSONObject("config");

						JSONObject collectionJSONObject =
							configJSONObject.getJSONObject("collection");

						if (!StringUtil.startsWith(
								collectionJSONObject.getString("key"),
								_CLASS_NAME)) {

							continue;
						}

						collectionJSONObject.put(
							"key", collectionJSONObject.getString("itemType"));
					}

					preparedStatement.setString(1, dataJSONObject.toString());
					preparedStatement.setLong(2, (Long)values[0]);
					preparedStatement.setLong(3, (Long)values[1]);

					preparedStatement.addBatch();
				},
				"Unable update layout page template structure rel");
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.object.web.internal.info.collection.provider." +
			"ObjectEntrySingleFormVariationInfoCollectionProvider";

}