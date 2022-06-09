/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.upgrade.v1_2_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Wade Cao
 */
public class SXPElementUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumn("SXPElement", "key_")) {
			alterTableDropColumn("SXPElement", "key_");
		}

		if (!hasColumn("SXPElement", "externalReferenceCode")) {
			alterTableAddColumn(
				"SXPElement", "externalReferenceCode", "VARCHAR(75)");
		}

		StringBundler sb = new StringBundler(3);

		sb.append("select SXPElement.externalReferenceCode, ");
		sb.append("SXPElement.sxpElementId, SXPElement.version from ");
		sb.append("SXPElement");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				sb.toString());
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update SXPElement set externalReferenceCode = ?, " +
						"version = ? where sxpElementId = ?")) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					String externalReferenceCode = resultSet.getString(1);

					if (Validator.isNull(externalReferenceCode)) {
						continue;
					}

					long sxpElementId = resultSet.getLong(2);

					String version = resultSet.getString(3);

					if (Validator.isNull(version)) {
						version = "1.0";
					}

					preparedStatement2.setString(1, externalReferenceCode);
					preparedStatement2.setString(2, version);

					preparedStatement2.setLong(3, sxpElementId);

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

}