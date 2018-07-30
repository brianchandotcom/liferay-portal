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
import com.liferay.dynamic.data.lists.constants.DDLPortletKeys;
import com.liferay.dynamic.data.lists.helper.DDLRecordSetTestHelper;
import com.liferay.dynamic.data.lists.helper.DDLRecordTestHelper;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.exportimport.test.util.lar.BasePortletExportImportTestCase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tamas Molnar
 */
@RunWith(Arquillian.class)
public class DDLDisplayExportImportTest
	extends BasePortletExportImportTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public String getPortletId() {
		return DDLPortletKeys.DYNAMIC_DATA_LISTS_DISPLAY;
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
			ddmStructure);

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

		Assert.assertNotNull(importedRecord);

		DDLRecordSet importedRecordSet =
			DDLRecordSetLocalServiceUtil.fetchDDLRecordSetByUuidAndGroupId(
				recordSet.getUuid(), importedGroup.getGroupId());

		Assert.assertNotNull(importedRecordSet);

		Assert.assertEquals(
			String.valueOf(importedRecordSet.getRecordSetId()),
			importedPortletPreferences.getValue(
				"recordSetId", StringPool.BLANK));
	}

	@Override
	@Test
	public void testExportImportAssetLinks() throws Exception {
	}

	private DDLRecordSetTestHelper _ddlRecordSetTestHelper;
	private DDLRecordTestHelper _ddlRecordTestHelper;

}