/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.internal.upgrade.v3_0_0;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.DDMStorageEngineManager;
import com.liferay.layout.seo.internal.upgrade.v3_0_0.util.LayoutSEOEntryCustomMetaTagTable;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class LayoutSEOEntryCustomMetaTagUpgradeProcess extends UpgradeProcess {

	public LayoutSEOEntryCustomMetaTagUpgradeProcess(
		ClassNameLocalService classNameLocalService,
		CompanyLocalService companyLocalService,
		DDMStorageEngineManager ddmStorageEngineManager,
		DDMStorageLinkLocalService ddmStorageLinkLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		GroupLocalService groupLocalService) {

		_classNameLocalService = classNameLocalService;
		_companyLocalService = companyLocalService;
		_ddmStorageEngineManager = ddmStorageEngineManager;
		_ddmStorageLinkLocalService = ddmStorageLinkLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_groupLocalService = groupLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select ctCollectionId, layoutSEOEntryId, groupId, " +
					"companyId, ddmStorageId from LayoutSEOEntry");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into LayoutSEOEntryCustomMetaTag (",
						"mvccVersion, ctCollectionId, groupId, companyId, ",
						"layoutSEOEntryCustomMetaTagId, layoutSEOEntryId, ",
						"property, content) values (?, ?, ?, ?, ?, ?, ?, ?)"));
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			if (resultSet.next()) {
				long ctCollection = resultSet.getLong(1);
				long layoutSEOEntryId = resultSet.getLong(2);
				long groupId = resultSet.getLong(3);
				long companyId = resultSet.getLong(4);
				long ddmStorageId = resultSet.getLong(5);

				_addLayoutSEOEntryCustomMetaTags(
					companyId, ctCollection, ddmStorageId, groupId,
					layoutSEOEntryId, preparedStatement2);
			}

			preparedStatement2.executeBatch();
		}

		_companyLocalService.forEachCompanyId(
			companyId -> {
				Group companyGroup = _groupLocalService.getCompanyGroup(
					companyId);

				DDMStructure ddmStructure =
					_ddmStructureLocalService.fetchStructure(
						companyGroup.getGroupId(),
						_classNameLocalService.getClassNameId(
							LayoutSEOEntry.class.getName()),
						"custom-meta-tags");

				if (ddmStructure == null) {
					return;
				}

				_ddmStorageLinkLocalService.deleteStructureStorageLinks(
					ddmStructure.getStructureId());

				_ddmStructureLocalService.deleteStructure(ddmStructure);
			});
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropColumns("LayoutSEOEntry", "DDMStorageId")
		};
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {LayoutSEOEntryCustomMetaTagTable.create()};
	}

	private void _addLayoutSEOEntryCustomMetaTags(
			long companyId, long ctCollection, long ddmStorageId, long groupId,
			long layoutSEOEntryId, PreparedStatement preparedStatement)
		throws Exception {

		DDMFormValues ddmFormValues = _ddmStorageEngineManager.getDDMFormValues(
			ddmStorageId);

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		for (List<DDMFormFieldValue> ddmFormFieldValues :
				ddmFormFieldValuesMap.values()) {

			for (DDMFormFieldValue nameDDMFormFieldValue : ddmFormFieldValues) {
				if (_isLegacyDDMFormFieldValue(nameDDMFormFieldValue)) {
					List<DDMFormFieldValue> nestedDDMFormFieldValues =
						nameDDMFormFieldValue.getNestedDDMFormFieldValues();

					nameDDMFormFieldValue = nestedDDMFormFieldValues.get(0);
				}

				Value nameValue = nameDDMFormFieldValue.getValue();

				List<DDMFormFieldValue> nestedDDMFormFieldValues =
					nameDDMFormFieldValue.getNestedDDMFormFieldValues();

				DDMFormFieldValue valueDDMFormFieldValue =
					nestedDDMFormFieldValues.get(0);

				Value valueValue = valueDDMFormFieldValue.getValue();

				preparedStatement.setLong(1, 0);
				preparedStatement.setLong(2, ctCollection);
				preparedStatement.setLong(3, groupId);
				preparedStatement.setLong(4, companyId);
				preparedStatement.setLong(5, increment());
				preparedStatement.setLong(6, layoutSEOEntryId);
				preparedStatement.setString(
					7, nameValue.getString(nameValue.getDefaultLocale()));

				Map<String, String> localizedMap = new HashMap<>();

				Map<Locale, String> contentMap = valueValue.getValues();

				contentMap.forEach(
					(locale, value) -> localizedMap.put(
						LocaleUtil.toLanguageId(locale), value));

				preparedStatement.setString(
					8,
					LocalizationUtil.getXml(
						localizedMap,
						LocaleUtil.toLanguageId(valueValue.getDefaultLocale()),
						"Label"));

				preparedStatement.addBatch();
			}
		}

		_ddmStorageEngineManager.deleteByClass(ddmStorageId);
	}

	private boolean _isLegacyDDMFormFieldValue(
		DDMFormFieldValue ddmFormFieldValue) {

		List<DDMFormFieldValue> nestedDDMFormFieldValues =
			ddmFormFieldValue.getNestedDDMFormFieldValues();

		DDMFormFieldValue childDDMFormFieldValue = nestedDDMFormFieldValues.get(
			0);

		if (Objects.equals(childDDMFormFieldValue.getName(), "property")) {
			return true;
		}

		return false;
	}

	private final ClassNameLocalService _classNameLocalService;
	private final CompanyLocalService _companyLocalService;
	private final DDMStorageEngineManager _ddmStorageEngineManager;
	private final DDMStorageLinkLocalService _ddmStorageLinkLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final GroupLocalService _groupLocalService;

}