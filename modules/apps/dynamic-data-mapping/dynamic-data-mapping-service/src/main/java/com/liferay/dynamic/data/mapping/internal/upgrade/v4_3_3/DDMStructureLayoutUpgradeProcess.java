/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v4_3_3;

import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutDeserializeUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Objects;

/**
 * @author Carolina Barbosa
 */
public class DDMStructureLayoutUpgradeProcess extends UpgradeProcess {

	public DDMStructureLayoutUpgradeProcess(
		DDMFormLayoutDeserializer ddmFormLayoutDeserializer,
		DDMFormLayoutSerializer ddmFormLayoutSerializer,
		JSONFactory jsonFactory) {

		_ddmFormLayoutDeserializer = ddmFormLayoutDeserializer;
		_ddmFormLayoutSerializer = ddmFormLayoutSerializer;
		_jsonFactory = jsonFactory;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select DDMStructureLayout.structureLayoutId, ",
					"DDMStructureLayout.definition from DDMStructureLayout ",
					"inner join DDMStructureVersion on ",
					"DDMStructureLayout.structureVersionId = ",
					"DDMStructureVersion.structureVersionId inner join ",
					"DDMStructure on DDMStructure.structureId = ",
					"DDMStructureVersion.structureId where ",
					"DDMStructure.classNameId = ?"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureLayout set definition = ? where " +
						"structureLayoutId = ?")) {

			preparedStatement1.setLong(
				1, PortalUtil.getClassNameId(DDMFormInstance.class.getName()));

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					DDMFormLayout ddmFormLayout =
						DDMFormLayoutDeserializeUtil.deserialize(
							_ddmFormLayoutDeserializer,
							resultSet.getString("definition"));

					if (!Objects.equals(
							ddmFormLayout.getPaginationMode(),
							DDMFormLayout.WIZARD_MODE)) {

						continue;
					}

					ddmFormLayout.setPaginationMode(DDMFormLayout.MULTI_PAGES);

					DDMFormLayoutSerializerSerializeResponse
						ddmFormLayoutSerializerSerializeResponse =
							_ddmFormLayoutSerializer.serialize(
								DDMFormLayoutSerializerSerializeRequest.Builder.
									newBuilder(
										ddmFormLayout
									).build());

					preparedStatement2.setString(
						1,
						ddmFormLayoutSerializerSerializeResponse.getContent());

					long structureLayoutId = resultSet.getLong(
						"structureLayoutId");

					preparedStatement2.setLong(2, structureLayoutId);

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private final DDMFormLayoutDeserializer _ddmFormLayoutDeserializer;
	private final DDMFormLayoutSerializer _ddmFormLayoutSerializer;
	private final JSONFactory _jsonFactory;

}