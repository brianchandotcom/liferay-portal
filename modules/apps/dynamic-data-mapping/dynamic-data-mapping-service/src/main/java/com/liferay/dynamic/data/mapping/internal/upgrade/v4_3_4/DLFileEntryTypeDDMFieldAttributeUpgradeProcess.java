/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v4_3_4;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.NumberFormat;

import java.util.Locale;

/**
 * @author Alicia García
 */
public class DLFileEntryTypeDDMFieldAttributeUpgradeProcess
	extends UpgradeProcess {

	public DLFileEntryTypeDDMFieldAttributeUpgradeProcess(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompanyId(
			companyId -> _updateDDMFieldAttribute(companyId));
	}

	private void _updateDDMFieldAttribute(long companyId) throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select DDMField.storageId, DDMField.fieldId from ",
					"DLFileEntryType inner join DDMStructureLink on ",
					"DDMStructureLink.classNameId = ? and ",
					"DDMStructureLink.classPK = ",
					"DLFileEntryType.fileEntryTypeId inner join ",
					"DDMStructureVersion on DDMStructureVersion.structureId = ",
					"DDMStructureLink.structureId inner join DDMField on ",
					"DDMStructureVersion.structureVersionId = ",
					"DDMField.structureVersionId and DDMField.companyId = ? ",
					"and DDMField.fieldType like ?"))) {

			PreparedStatement preparedStatement2 = connection.prepareStatement(
				StringBundler.concat(
					"select fieldAttributeId, languageId, smallAttributeValue ",
					"from DDMFieldAttribute where storageId = ? and fieldId = ",
					"? and (attributeName is null or attributeName = '') "));

			PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update DDMFieldAttribute set smallAttributeValue = ? " +
						"where fieldAttributeId = ? ");

			preparedStatement1.setLong(
				1, PortalUtil.getClassNameId(DLFileEntryType.class));
			preparedStatement1.setLong(2, companyId);
			preparedStatement1.setString(3, "numeric");

			try (ResultSet resultSet1 = preparedStatement1.executeQuery()) {
				while (resultSet1.next()) {
					preparedStatement2.setLong(1, resultSet1.getLong(1));
					preparedStatement2.setLong(2, resultSet1.getLong(2));

					try (ResultSet resultSet2 =
							preparedStatement2.executeQuery()) {

						while (resultSet2.next()) {
							String languageId = resultSet2.getString(2);

							Locale locale = LocaleUtil.fromLanguageId(
								languageId);

							NumberFormat numberFormat =
								NumberFormat.getNumberInstance(locale);

							numberFormat.setGroupingUsed(true);

							String valueString = resultSet2.getString(3);

							if (Validator.isNull(valueString)) {
								preparedStatement3.setString(1, null);
							}
							else {
								Number number = numberFormat.parse(valueString);

								numberFormat.setGroupingUsed(false);

								preparedStatement3.setString(
									1, numberFormat.format(number));
							}

							preparedStatement3.setLong(
								2, resultSet2.getLong(1));

							preparedStatement3.addBatch();
						}
					}
				}

				preparedStatement3.executeBatch();
			}
		}
	}

	private final CompanyLocalService _companyLocalService;

}