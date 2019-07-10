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

package com.liferay.dynamic.data.mapping.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.helper.DDMFormInstanceRecordTestHelper;
import com.liferay.dynamic.data.mapping.helper.DDMFormInstanceTestHelper;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.exportimport.test.util.lar.BasePortletExportImportTestCase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
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
 * @author Pedro Queiroz
 * @author Tamas Molnar
 */
@RunWith(Arquillian.class)
@Sync
public class DDMFormDisplayExportImportTest
	extends BasePortletExportImportTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	public String getPortletId() {
		return DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM;
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), DDMFormInstance.class.getName());

		_ddmFormInstanceTestHelper = new DDMFormInstanceTestHelper(group);

		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceTestHelper.addDDMFormInstance(ddmStructure);

		DDMFormValues settingsDDMFormValues =
			DDMFormInstanceTestHelper.createFormInstanceSettingsDDMFormValues();

		_ddmFormInstanceTestHelper.updateFormInstance(
			ddmFormInstance.getFormInstanceId(), settingsDDMFormValues);

		_ddmFormInstanceRecordTestHelper = new DDMFormInstanceRecordTestHelper(
			group, ddmFormInstance);
	}

	@Test
	public void testExportImport() throws Exception {
		DDMFormInstanceRecord ddmFormInstanceRecord =
			_ddmFormInstanceRecordTestHelper.addDDMFormInstanceRecord();

		DDMFormInstanceRecord importedDDMFormInstanceRecord =
			DDMFormInstanceRecordLocalServiceUtil.
				fetchDDMFormInstanceRecordByUuidAndGroupId(
					ddmFormInstanceRecord.getUuid(),
					importedGroup.getGroupId());

		Assert.assertNull(importedDDMFormInstanceRecord);

		DDMFormInstance ddmFormInstance =
			ddmFormInstanceRecord.getFormInstance();

		Map<String, String[]> preferenceMap = new HashMap<>();

		preferenceMap.put(
			"formInstanceId",
			new String[] {String.valueOf(ddmFormInstance.getFormInstanceId())});

		PortletPreferences importedPortletPreferences =
			getImportedPortletPreferences(preferenceMap);

		DDMFormInstance importedDDMFormInstance =
			DDMFormInstanceLocalServiceUtil.
				fetchDDMFormInstanceByUuidAndGroupId(
					ddmFormInstance.getUuid(), importedGroup.getGroupId());

		Assert.assertNotNull(importedDDMFormInstance);

		Assert.assertEquals(
			String.valueOf(importedDDMFormInstance.getFormInstanceId()),
			importedPortletPreferences.getValue(
				"formInstanceId", StringPool.BLANK));
	}

	@Ignore
	@Override
	@Test
	public void testExportImportAssetLinks() throws Exception {
		super.testExportImportAssetLinks();
	}

	private DDMFormInstanceRecordTestHelper _ddmFormInstanceRecordTestHelper;
	private DDMFormInstanceTestHelper _ddmFormInstanceTestHelper;

}