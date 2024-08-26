/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v5_4_4;

import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.spi.converter.SPIDDMFormRuleConverter;
import com.liferay.dynamic.data.mapping.spi.converter.serializer.SPIDDMFormRuleSerializerContext;
import com.liferay.dynamic.data.mapping.util.DDMFormDeserializeUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormSerializeUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;
import java.util.Objects;

/**
 * @author Carolina Barbosa
 */
public class DDMStructureUpgradeProcess extends UpgradeProcess {

	public DDMStructureUpgradeProcess(
		DDMFormDeserializer ddmFormDeserializer,
		DDMFormSerializer ddmFormSerializer,
		SPIDDMFormRuleConverter spiDDMFormRuleConverter) {

		_ddmFormDeserializer = ddmFormDeserializer;
		_ddmFormSerializer = ddmFormSerializer;
		_spiDDMFormRuleConverter = spiDDMFormRuleConverter;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeDDMStructureVersion();

		_upgradeDDMStructure();
	}

	private void _upgradeDDMStructure() throws Exception {
		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					StringBundler.concat(
						"select DDMStructure.ctCollectionId, ",
						"DDMStructure.structureId, ",
						"DDMStructureVersion.definition from DDMStructure ",
						"inner join DDMStructureVersion on ",
						"DDMStructure.ctCollectionId = ",
						"DDMStructureVersion.ctCollectionId and ",
						"DDMStructure.structureid = ",
						"DDMStructureVersion.structureid where ",
						"DDMStructure.version = DDMStructureVersion.version ",
						"and DDMStructure.classNameId = ?"));
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set definition = ? where " +
						"ctCollectionId = ? and structureId = ?")) {

			selectPreparedStatement.setLong(
				1, PortalUtil.getClassNameId(DDMFormInstance.class.getName()));

			try (ResultSet resultSet = selectPreparedStatement.executeQuery()) {
				while (resultSet.next()) {
					updatePreparedStatement.setString(
						1, resultSet.getString("definition"));
					updatePreparedStatement.setLong(
						2, resultSet.getLong("ctCollectionId"));
					updatePreparedStatement.setLong(
						3, resultSet.getLong("structureId"));

					updatePreparedStatement.addBatch();
				}

				updatePreparedStatement.executeBatch();
			}
		}
	}

	private void _upgradeDDMStructureVersion() throws Exception {
		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					StringBundler.concat(
						"select DDMStructureVersion.ctCollectionId, ",
						"DDMStructureVersion.structureVersionId, ",
						"DDMStructureVersion.definition from DDMStructure ",
						"inner join DDMStructureVersion on ",
						"DDMStructure.structureId = ",
						"DDMStructureVersion.structureId where ",
						"DDMStructure.classNameId = ?"));
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureVersion set definition = ? where " +
						"ctCollectionId = ? and structureVersionId = ?")) {

			selectPreparedStatement.setLong(
				1, PortalUtil.getClassNameId(DDMFormInstance.class.getName()));

			try (ResultSet resultSet = selectPreparedStatement.executeQuery()) {
				while (resultSet.next()) {
					DDMForm ddmForm = DDMFormDeserializeUtil.deserialize(
						_ddmFormDeserializer,
						resultSet.getString("definition"));

					List<DDMFormRule> ddmFormRules = ddmForm.getDDMFormRules();

					SPIDDMFormRuleSerializerContext
						spiDDMFormRuleSerializerContext =
							new SPIDDMFormRuleSerializerContext();

					spiDDMFormRuleSerializerContext.addAttribute(
						"form", ddmForm);

					List<DDMFormRule> newDDMFormRules =
						_spiDDMFormRuleConverter.convert(
							_spiDDMFormRuleConverter.convert(ddmFormRules),
							spiDDMFormRuleSerializerContext);

					if (Objects.equals(ddmFormRules, newDDMFormRules)) {
						continue;
					}

					ddmForm.setDDMFormRules(newDDMFormRules);

					updatePreparedStatement.setString(
						1,
						DDMFormSerializeUtil.serialize(
							ddmForm, _ddmFormSerializer));
					updatePreparedStatement.setLong(
						2, resultSet.getLong("ctCollectionId"));
					updatePreparedStatement.setLong(
						3, resultSet.getLong("structureVersionId"));

					updatePreparedStatement.addBatch();
				}

				updatePreparedStatement.executeBatch();
			}
		}
	}

	private final DDMFormDeserializer _ddmFormDeserializer;
	private final DDMFormSerializer _ddmFormSerializer;
	private final SPIDDMFormRuleConverter _spiDDMFormRuleConverter;

}