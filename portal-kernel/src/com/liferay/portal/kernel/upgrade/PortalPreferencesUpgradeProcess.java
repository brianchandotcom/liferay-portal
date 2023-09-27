/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;

import java.sql.PreparedStatement;

import java.util.Map;

/**
 * @author Eduardo García
 */
public abstract class PortalPreferencesUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Map<String, String> preferenceNamesMap = getPreferenceNamesMap();

		for (Map.Entry<String, String> entry : preferenceNamesMap.entrySet()) {
			String oldName = entry.getKey();

			String oldNamespace = null;
			String oldKey = oldName;

			int index = oldName.indexOf(CharPool.POUND);

			if (index > 0) {
				oldNamespace = oldName.substring(0, index);
				oldKey = oldName.substring(index + 1);
			}

			String newName = entry.getValue();

			String newNamespace = null;
			String newKey = newName;

			index = newName.indexOf(CharPool.POUND);

			if (index > 0) {
				newNamespace = newName.substring(0, index);
				newKey = newName.substring(index + 1);
			}

			StringBundler sb = new StringBundler(3);

			sb.append("update PortalPreferenceValue set namespace = ?, key_ ");
			sb.append("= ? where key_ = ? and ");

			if (oldNamespace == null) {
				sb.append("(namespace = '' or namespace is null)");
			}
			else {
				sb.append("namespace = ?");
			}

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(sb.toString())) {

				preparedStatement.setString(1, newNamespace);
				preparedStatement.setString(2, newKey);
				preparedStatement.setString(3, oldKey);

				if (oldNamespace != null) {
					preparedStatement.setString(4, oldNamespace);
				}

				preparedStatement.executeUpdate();
			}
		}
	}

	protected abstract Map<String, String> getPreferenceNamesMap();

}