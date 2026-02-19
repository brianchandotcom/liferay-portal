/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.internal.upgrade.v3_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jhosseph Gonzalez
 */
public class AssetListEntryUsageUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select ctCollectionId, assetListEntryUsageId, companyId, ",
					"key_ from AssetListEntryUsage where (key_ like '",
					"com.liferay.object.internal.info.collection.provider.",
					"ManyToManyObjectRelationshipRelatedInfoCollection",
					"Provider%' or key_ like '",
					"com.liferay.object.internal.info.collection.provider.",
					"OneToManyObjectRelationshipRelatedInfoCollection",
					"Provider%')"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update AssetListEntryUsage set key_ = ? where " +
						"ctCollectionId = ? and assetListEntryUsageId = ? " +
							"and companyId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				String key = GetterUtil.getString(resultSet.getString("key_"));

				Matcher matcher = _pattern.matcher(key);

				if (!matcher.matches()) {
					continue;
				}

				long companyId = resultSet.getLong("companyId");

				String className = _fetchObjectDefinitionClassName(
					companyId, matcher.group(3));

				if (Validator.isNull(className)) {
					continue;
				}

				preparedStatement2.setString(
					1,
					StringBundler.concat(
						matcher.group(1), className, matcher.group(4)));
				preparedStatement2.setLong(
					2, resultSet.getLong("ctCollectionId"));
				preparedStatement2.setLong(
					3, resultSet.getLong("assetListEntryUsageId"));
				preparedStatement2.setLong(4, companyId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private String _fetchObjectDefinitionClassName(
			long companyId, String objectDefinitionName)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select className from ObjectDefinition where companyId = ? " +
					"and name = ?")) {

			preparedStatement.setLong(1, companyId);
			preparedStatement.setString(2, objectDefinitionName);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getString(1);
				}
			}
		}

		return null;
	}

	private static final Pattern _pattern = Pattern.compile(
		StringBundler.concat(
			"^(com\\.liferay\\.object\\.internal\\.info\\.collection\\.",
			"provider\\.",
			"[A-Za-z]+ObjectRelationshipRelatedInfoCollectionProvider",
			"_)(\\d+)_(.+)(_[A-Za-z0-9]+)$"));

}