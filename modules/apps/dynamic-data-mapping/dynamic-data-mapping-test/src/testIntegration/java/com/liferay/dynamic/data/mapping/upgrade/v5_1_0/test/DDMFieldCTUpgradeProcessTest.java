/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.upgrade.v5_1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseCTUpgradeProcessTestCase;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.dynamic.data.lists.constants.DDLRecordConstants;
import com.liferay.dynamic.data.lists.constants.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMField;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStorageLink;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Paulo Albuquerque
 */
@RunWith(Arquillian.class)
public class DDMFieldCTUpgradeProcessTest extends BaseCTUpgradeProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			RandomTestUtil.randomString());

		_ddmStructure = DDMStructureLocalServiceUtil.addStructure(
			null, TestPropsValues.getUserId(), _group.getGroupId(), 0,
			PortalUtil.getClassNameId(DDLRecordSet.class.getName()),
			"CUSTOM-META-TAGS", RandomTestUtil.randomLocaleStringMap(), null,
			ddmForm, _ddm.getDefaultDDMFormLayout(ddmForm),
			StorageType.DEFAULT.toString(), DDMStructureConstants.TYPE_DEFAULT,
			serviceContext);

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.addRecordSet(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_ddmStructure.getStructureId(), null,
			RandomTestUtil.randomLocaleStringMap(), null,
			DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT,
			DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS, serviceContext);

		DDLRecord ddlRecord = _ddlRecordLocalService.addRecord(
			TestPropsValues.getUserId(), _group.getGroupId(),
			ddlRecordSet.getRecordSetId(),
			DDLRecordConstants.DISPLAY_INDEX_DEFAULT, _createDDMFormValues(),
			serviceContext);

		ddmStorageLink = ddmStorageLinkLocalService.getClassStorageLink(
			ddlRecord.getDDMStorageId());

		_addDDMField("property", _PRIORITY);
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return _addDDMField("content", _PRIORITY + 1);
	}

	@Override
	protected CTService<?> getCTService() {
		return _ddmFieldLocalService;
	}

	@Override
	protected void runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();
	}

	@Override
	protected CTModel<?> updateCTModel(CTModel<?> ctModel) throws Exception {
		DDMField ddmField = (DDMField)ctModel;

		ddmField.setParentFieldId(RandomTestUtil.randomLong());

		return _ddmFieldLocalService.updateDDMField(ddmField);
	}

	protected DDMStorageLink ddmStorageLink;

	@Inject
	protected DDMStorageLinkLocalService ddmStorageLinkLocalService;

	private DDMField _addDDMField(String fieldName, int priority) {
		DDMField ddmField = _ddmFieldLocalService.createDDMField(
			_counterLocalService.increment());

		ddmField.setParentFieldId(0);
		ddmField.setStorageId(ddmStorageLink.getClassPK());
		ddmField.setStructureVersionId(ddmStorageLink.getStructureVersionId());
		ddmField.setFieldName(fieldName);
		ddmField.setFieldType(DDMFormFieldTypeConstants.TEXT);
		ddmField.setInstanceId(RandomTestUtil.randomString(8));
		ddmField.setLocalizable(false);
		ddmField.setPriority(priority);

		return _ddmFieldLocalService.addDDMField(ddmField);
	}

	private DDMFormValues _createDDMFormValues() {
		DDMForm ddmForm = _ddmStructure.getDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"parent", RandomTestUtil.randomString(),
			DDMFormFieldTypeConstants.FIELDSET, null, true, false, false);

		ddmFormField.addNestedDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"child", RandomTestUtil.randomString(),
				DDMFormFieldTypeConstants.TEXT, "string", true, false, false));

		ddmForm.setDDMFormFields(ListUtil.fromArray(ddmFormField));

		return new DDMFormValues(ddmForm);
	}

	private static final String _CLASS_NAME =
		"com.liferay.dynamic.data.mapping.internal.upgrade.v5_1_0." +
			"DDMFieldUpgradeProcess";

	private static final int _PRIORITY = RandomTestUtil.randomInt();

	@Inject(
		filter = "(&(component.name=com.liferay.dynamic.data.mapping.internal.upgrade.registry.DDMServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private CounterLocalService _counterLocalService;

	@Inject
	private DDLRecordLocalService _ddlRecordLocalService;

	@Inject
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Inject
	private DDM _ddm;

	@Inject
	private DDMFieldLocalService _ddmFieldLocalService;

	@DeleteAfterTestRun
	private DDMStructure _ddmStructure;

	@DeleteAfterTestRun
	private Group _group;

}