/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.internal.upgrade.registry.v1_2_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.PortletPreferenceValue;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class PortletPreferenceValuesUpgradeProcess extends UpgradeProcess {

	public PortletPreferenceValuesUpgradeProcess(
		GroupLocalService groupLocalService) {

		_groupLocalService = groupLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		Map<Long, String> groupKeys = new HashMap<>();

		processConcurrently(
			"select ctCollectionId, companyId, portletPreferencesId, index_, " +
				"readOnly, smallValue from PortletPreferenceValue where name " +
					"= 'displayStyleGroupId'",
			StringBundler.concat(
				"insert into PortletPreferenceValue (mvccVersion, ",
				"ctCollectionId, portletPreferenceValueId, companyId, ",
				"portletPreferencesId, index_, largeValue, name, readOnly, ",
				"smallValue) values (0, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
			resultSet -> new Object[] {
				resultSet.getLong("ctCollectionId"),
				resultSet.getLong("companyId"),
				resultSet.getLong("portletPreferencesId"),
				resultSet.getInt("index_"), resultSet.getBoolean("readOnly"),
				resultSet.getString("smallValue")
			},
			(values, preparedStatement) -> {
				long smallValue = GetterUtil.getLong(values[5]);

				if (smallValue <= 0) {
					return;
				}

				preparedStatement.setLong(1, (Long)values[0]);
				preparedStatement.setLong(
					2, increment(PortletPreferenceValue.class.getName()));
				preparedStatement.setLong(3, (Long)values[1]);
				preparedStatement.setLong(4, (Long)values[2]);
				preparedStatement.setInt(5, (Integer)values[3]);
				preparedStatement.setString(6, StringPool.BLANK);
				preparedStatement.setString(7, "displayStyleGroupKey");
				preparedStatement.setBoolean(8, (Boolean)values[4]);

				String groupKey = groupKeys.get(smallValue);

				if (Validator.isNull(groupKey)) {
					Group group = _groupLocalService.fetchGroup(smallValue);

					if (group == null) {
						return;
					}

					groupKey = group.getGroupKey();

					groupKeys.put(smallValue, groupKey);
				}

				preparedStatement.setString(9, groupKey);

				preparedStatement.addBatch();
			},
			null);
	}

	private final GroupLocalService _groupLocalService;

}