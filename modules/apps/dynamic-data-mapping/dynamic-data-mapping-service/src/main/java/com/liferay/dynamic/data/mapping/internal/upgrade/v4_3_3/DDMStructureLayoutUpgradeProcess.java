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
		StringBundler sb = new StringBundler(8);

		sb.append("select DDMStructureLayout.structureLayoutId, ");
		sb.append("DDMStructureLayout.definition from DDMStructureLayout ");
		sb.append("inner join DDMStructureVersion on ");
		sb.append("DDMStructureLayout.structureVersionId = ");
		sb.append("DDMStructureVersion.structureVersionId inner join ");
		sb.append("DDMStructure on DDMStructure.structureId = ");
		sb.append("DDMStructureVersion.structureId where ");
		sb.append("DDMStructure.classNameId = ?");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureLayout set definition = ? where " +
						"structureLayoutId = ?")) {

			ps1.setLong(
				1, PortalUtil.getClassNameId(DDMFormInstance.class.getName()));

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					DDMFormLayout ddmFormLayout =
						DDMFormLayoutDeserializeUtil.deserialize(
							_ddmFormLayoutDeserializer,
							rs.getString("definition"));

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

					ps2.setString(
						1,
						ddmFormLayoutSerializerSerializeResponse.getContent());

					long structureLayoutId = rs.getLong("structureLayoutId");

					ps2.setLong(2, structureLayoutId);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private final DDMFormLayoutDeserializer _ddmFormLayoutDeserializer;
	private final DDMFormLayoutSerializer _ddmFormLayoutSerializer;
	private final JSONFactory _jsonFactory;

}