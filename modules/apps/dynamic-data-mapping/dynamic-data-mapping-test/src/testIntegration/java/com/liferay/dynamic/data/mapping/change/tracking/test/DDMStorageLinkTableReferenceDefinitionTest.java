/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseTableReferenceDefinitionTestCase;
import com.liferay.data.engine.rest.dto.v2_0.DataRecord;
import com.liferay.dynamic.data.lists.constants.DDLRecordConstants;
import com.liferay.dynamic.data.lists.constants.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Gislayne Vitorino
 */
@RunWith(Arquillian.class)
public class DDMStorageLinkTableReferenceDefinitionTest
	extends BaseTableReferenceDefinitionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), DDLRecordSet.class.getName());

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.addRecordSet(
			TestPropsValues.getUserId(), group.getGroupId(),
			_ddmStructure.getStructureId(), null,
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			null, DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT,
			DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS,
			ServiceContextTestUtil.getServiceContext());

		DDMFormValues ddmFormValues = _createDDMFormValues();

		DDLRecord ddlRecord = _ddlRecordLocalService.addRecord(
			TestPropsValues.getUserId(), group.getGroupId(),
			ddlRecordSet.getRecordSetId(),
			DDLRecordConstants.DISPLAY_INDEX_DEFAULT, ddmFormValues,
			ServiceContextTestUtil.getServiceContext());

		_ddmStorageId = ddlRecord.getDDMStorageId();
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		DDMStructureVersion ddmStructureVersion =
			_ddmStructure.getStructureVersion();

		return _ddmStorageLinkLocalService.addStorageLink(
			_portal.getClassNameId(DataRecord.class.getName()), _ddmStorageId,
			ddmStructureVersion.getStructureVersionId(),
			ServiceContextTestUtil.getServiceContext());
	}

	private DDMFormField _createDDMFormField(
		String dataType, String name, String type) {

		DDMFormField ddmFormField = new DDMFormField(name, type);

		ddmFormField.setDataType(dataType);
		ddmFormField.setLocalizable(true);

		return ddmFormField;
	}

	private DDMFormValues _createDDMFormValues() {
		DDMFormField ddmFormField = _createDDMFormField(
			null, "parent", "fieldset");

		ddmFormField.addNestedDDMFormField(
			_createDDMFormField("string", "child", "text"));

		DDMForm ddmForm = _ddmStructure.getDDMForm();

		ddmForm.setDDMFormFields(ListUtil.fromArray(ddmFormField));

		return new DDMFormValues(ddmForm);
	}

	@Inject
	private DDLRecordLocalService _ddlRecordLocalService;

	@Inject
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	private long _ddmStorageId;

	@Inject
	private DDMStorageLinkLocalService _ddmStorageLinkLocalService;

	private DDMStructure _ddmStructure;

	@Inject
	private Portal _portal;

}