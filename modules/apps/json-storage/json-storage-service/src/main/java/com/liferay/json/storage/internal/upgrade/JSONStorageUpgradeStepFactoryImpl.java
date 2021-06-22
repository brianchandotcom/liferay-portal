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

package com.liferay.json.storage.internal.upgrade;

import com.liferay.json.storage.service.JSONStorageEntryLocalService;
import com.liferay.json.storage.upgrade.JSONStorageUpgradeStepFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = JSONStorageUpgradeStepFactory.class)
public class JSONStorageUpgradeStepFactoryImpl
	implements JSONStorageUpgradeStepFactory {

	@Override
	public UpgradeStep createUpgradeStep(
		Class<?> modelClass, String tableName, String primaryKeyName,
		String jsonColumnName) {

		return new JSONStorageUpgradeProcess(
			modelClass, tableName, primaryKeyName, jsonColumnName);
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private JSONStorageEntryLocalService _jsonStorageEntryLocalService;

	private class JSONStorageUpgradeProcess extends UpgradeProcess {

		@Override
		protected void doUpgrade() throws Exception {
			if (!hasColumn(_tableName, _jsonColumnName)) {
				return;
			}

			long classNameId = _classNameLocalService.getClassNameId(
				_modelClass);

			StringBundler sb = new StringBundler(7);

			sb.append("select companyId, ");
			sb.append(_primaryKeyName);
			sb.append(", ");
			sb.append(_jsonColumnName);
			sb.append(" from ");
			sb.append(_tableName);

			if (hasColumn(_tableName, "ctCollectionId")) {
				sb.append(" where ctCollectionId = 0");
			}

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(sb.toString());
				ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {
					String json = resultSet.getString(_jsonColumnName);

					if (Validator.isNotNull(json)) {
						_jsonStorageEntryLocalService.addJSONStorageEntries(
							resultSet.getLong("companyId"), classNameId,
							resultSet.getLong(_primaryKeyName), json);
					}
				}
			}

			runSQL(
				StringBundler.concat(
					"alter table ", _tableName, " drop column ",
					_jsonColumnName));
		}

		private JSONStorageUpgradeProcess(
			Class<?> modelClass, String tableName, String primaryKeyName,
			String jsonColumnName) {

			_modelClass = modelClass;
			_tableName = tableName;
			_primaryKeyName = primaryKeyName;
			_jsonColumnName = jsonColumnName;
		}

		private final String _jsonColumnName;
		private final Class<?> _modelClass;
		private final String _primaryKeyName;
		private final String _tableName;

	}

}