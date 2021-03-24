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