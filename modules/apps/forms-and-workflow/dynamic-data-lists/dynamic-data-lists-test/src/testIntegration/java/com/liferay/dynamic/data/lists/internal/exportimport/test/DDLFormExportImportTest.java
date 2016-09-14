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

package com.liferay.dynamic.data.lists.internal.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.lists.form.web.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.lists.helper.DDLRecordSetTestHelper;
import com.liferay.dynamic.data.lists.helper.DDLRecordTestHelper;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSetSettings;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.lar.test.BasePortletExportImportTestCase;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tamas Molnar
 */
@RunWith(Arquillian.class)
@Sync
public class DDLFormExportImportTest extends BasePortletExportImportTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	public String getPortletId() {
		return DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM;
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), DDLRecordSet.class.getName());

		_ddlRecordSetTestHelper = new DDLRecordSetTestHelper(group);

		DDLRecordSet recordSet = _ddlRecordSetTestHelper.addRecordSet(
			ddmStructure, DDLRecordSetConstants.SCOPE_FORMS);

		DDMFormValues settingsDDMFormValues =
			createDefaultSettingsDDMFormValues();

		DDLRecordSetLocalServiceUtil.updateRecordSet(
			recordSet.getRecordSetId(), settingsDDMFormValues);

		_ddlRecordTestHelper = new DDLRecordTestHelper(group, recordSet);
	}

	@Test
	public void testExportImport() throws Exception {
		DDLRecord record = _ddlRecordTestHelper.addRecord();

		DDLRecordSet recordSet = record.getRecordSet();

		Map<String, String[]> preferenceMap = new HashMap<>();

		preferenceMap.put(
			"recordSetId",
			new String[] {String.valueOf(recordSet.getRecordSetId())});

		PortletPreferences importedPortletPreferences =
			getImportedPortletPreferences(preferenceMap);

		DDLRecord importedRecord =
			DDLRecordLocalServiceUtil.fetchDDLRecordByUuidAndGroupId(
				record.getUuid(), importedGroup.getGroupId());

		Assert.assertNull(importedRecord);

		DDLRecordSet importedRecordSet =
			DDLRecordSetLocalServiceUtil.fetchDDLRecordSetByUuidAndGroupId(
				recordSet.getUuid(), importedGroup.getGroupId());

		Assert.assertNotNull(importedRecordSet);

		Assert.assertEquals(
			String.valueOf(importedRecordSet.getRecordSetId()),
			importedPortletPreferences.getValue(
				"recordSetId", StringPool.BLANK));
	}

	@Ignore
	@Override
	@Test
	public void testExportImportAssetLinks() throws Exception {
	}

	protected DDMFormValues createDefaultSettingsDDMFormValues() {
		DDMForm settingsDDMForm = DDMFormFactory.create(
			DDLRecordSetSettings.class);

		DDMFormValues settingsDDMFormValues =
			DDMFormValuesTestUtil.createDDMFormValues(settingsDDMForm);

		settingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"emailFromAddress", StringPool.BLANK));
		settingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"emailFromName", StringPool.BLANK));
		settingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"emailSubject", StringPool.BLANK));
		settingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"emailToAddress", StringPool.BLANK));
		settingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"published", Boolean.FALSE.toString()));
		settingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"redirectURL", StringPool.BLANK));
		settingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"requireCaptcha", Boolean.FALSE.toString()));
		settingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"sendEmailNotification", Boolean.FALSE.toString()));
		settingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"storageType", StorageType.JSON.toString()));
		settingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"workflowDefinition", StringPool.BLANK));

		return settingsDDMFormValues;
	}

	private DDLRecordSetTestHelper _ddlRecordSetTestHelper;
	private DDLRecordTestHelper _ddlRecordTestHelper;

}