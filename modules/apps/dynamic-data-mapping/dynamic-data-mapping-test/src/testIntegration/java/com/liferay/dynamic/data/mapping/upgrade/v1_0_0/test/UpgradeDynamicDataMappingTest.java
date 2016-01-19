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

package com.liferay.dynamic.data.mapping.upgrade.v1_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.upgrade.DDMServiceUpgrade;
import com.liferay.dynamic.data.mapping.upgrade.v1_0_0.UpgradeDynamicDataMapping;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 */
@RunWith(Arquillian.class)
public class UpgradeDynamicDataMappingTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_classNameId = PortalUtil.getClassNameId(
			"com.liferay.dynamic.data.lists.model.DDLRecordSet");
		_group = GroupTestUtil.addGroup();
		_now = new Timestamp(System.currentTimeMillis());

		setUpUpgradeDynamicDataMapping();
	}

	@Test
	public void testUpgradeStructureWithFileUploadField() throws Exception {
		long structureId = RandomTestUtil.randomLong();

		addStructure(
			structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-file-upload-field.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();

		String expectedDefinition = read(
			"ddm-structure-file-upload-field.json");

		String actualDefinition = getStructureDefinition(structureId);

		JSONAssert.assertEquals(expectedDefinition, actualDefinition, false);
	}

	protected void addStructure(
			long structureId, long parentStructureId, String version,
			String definition, String storageType)
		throws Exception {

		Connection con = DataAccess.getUpgradeOptimizedConnection();

		PreparedStatement ps = null;

		try {
			StringBundler sb = new StringBundler(6);

			sb.append("insert into DDMStructure (structureId, groupId, ");
			sb.append("companyId, userId, userName, versionUserId, ");
			sb.append("versionUserName, createDate, modifiedDate, ");
			sb.append("parentStructureId, classNameId, structureKey, ");
			sb.append("version, name, description, definition, storageType, ");
			sb.append("type_) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, structureId);
			ps.setLong(2, _group.getGroupId());
			ps.setLong(3, _group.getCompanyId());
			ps.setLong(4, TestPropsValues.getUserId());
			ps.setString(5, null);
			ps.setLong(6, TestPropsValues.getUserId());
			ps.setString(7, null);
			ps.setTimestamp(8, _now);
			ps.setTimestamp(9, _now);
			ps.setLong(10, parentStructureId);
			ps.setLong(11, _classNameId);
			ps.setString(12, StringUtil.randomString());
			ps.setString(13, version);
			ps.setString(14, StringUtil.randomString());
			ps.setString(15, StringPool.BLANK);
			ps.setString(16, definition);
			ps.setString(17, storageType);
			ps.setInt(18, DDMStructureConstants.TYPE_DEFAULT);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected String getStructureDefinition(long structureId) throws Exception {
		DDMStructure structure = DDMStructureLocalServiceUtil.getStructure(
			structureId);

		return structure.getDefinition();
	}

	protected Map<Class<?>, UpgradeStep> mapByClass(
		UpgradeStep[] upgradeSteps) {

		Map<Class<?>, UpgradeStep> upgradeStepsMap = new HashMap<>();

		for (UpgradeStep upgradeStep : upgradeSteps) {
			upgradeStepsMap.put(upgradeStep.getClass(), upgradeStep);
		}

		return upgradeStepsMap;
	}

	protected String read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz.getClassLoader(),
			"com/liferay/dynamic/data/mapping/dependencies/upgrade/v1_0_0/" +
				fileName);
	}

	protected void setUpUpgradeDynamicDataMapping() {
		Registry registry = RegistryUtil.getRegistry();

		DDMServiceUpgrade ddmServiceUpgrade = registry.getService(
			DDMServiceUpgrade.class);

		Map<Class<?>, UpgradeStep> upgradeStepsMap = mapByClass(
			ddmServiceUpgrade.getUpgradeSteps("1.0.0"));

		_upgradeDynamicDataMapping =
			(UpgradeDynamicDataMapping)upgradeStepsMap.get(
				UpgradeDynamicDataMapping.class);
	}

	private long _classNameId;

	@DeleteAfterTestRun
	private Group _group;

	private Timestamp _now;
	private UpgradeDynamicDataMapping _upgradeDynamicDataMapping;

}