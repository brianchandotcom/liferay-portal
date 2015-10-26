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

package com.liferay.dynamic.data.mapping.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureServiceUtil;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.AdvancedPermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalUtil;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class DDMStructureServiceTest extends BaseDDMServiceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_classNameId = PortalUtil.getClassNameId(DDL_RECORD_SET_CLASS_NAME);

		_otherGroup = GroupTestUtil.addGroup();

		_siteAdminUser = UserTestUtil.addGroupAdminUser(group);

		setUpPermissionThreadLocal();

		setUpPrincipalThreadLocal();
	}

	@After
	public void tearDown() throws Exception {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);
	}

	@Test
	public void testGetStructures() throws Exception {
		addStructure(_classNameId, StringUtil.randomString());
		addStructure(_classNameId, StringUtil.randomString());
		addStructure(_classNameId, StringUtil.randomString());

		long[] groupIds =
			new long[] {group.getGroupId(), _otherGroup.getGroupId()};

		List<DDMStructure> ddmStructures =
			DDMStructureServiceUtil.getStructures(
				TestPropsValues.getCompanyId(), groupIds, _classNameId,
				WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(3, ddmStructures.size());
	}

	@Test
	public void testSearch() throws Exception {
		addStructure(_classNameId, StringUtil.randomString());
		addStructure(_classNameId, StringUtil.randomString());
		addStructure(_classNameId, StringUtil.randomString());

		long[] groupIds =
			new long[] {group.getGroupId(), _otherGroup.getGroupId()};

		List<DDMStructure> ddmStructures = DDMStructureServiceUtil.search(
			TestPropsValues.getCompanyId(), groupIds, _classNameId,
			StringPool.BLANK, WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertEquals(3, ddmStructures.size());
	}

	@Test
	public void testSearchByNameAndDescription() throws Exception {
		String name = StringUtil.randomString();
		String description = StringUtil.randomString();

		DDMStructure ddmStructure = addStructure(
			_classNameId, name, description);

		addStructure(_classNameId, name, StringUtil.randomString());
		addStructure(_classNameId, StringUtil.randomString(), description);

		long[] groupIds =
			new long[] {group.getGroupId(), _otherGroup.getGroupId()};

		List<DDMStructure> ddmStructures = DDMStructureServiceUtil.search(
				TestPropsValues.getCompanyId(), groupIds, _classNameId, name,
				description, StorageType.JSON.getValue(),
				DDMStructureConstants.TYPE_DEFAULT,
				WorkflowConstants.STATUS_ANY, true, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertEquals(1, ddmStructures.size());

		DDMStructure foundDDMStructure = ddmStructures.get(0);

		Assert.assertEquals(ddmStructure, foundDDMStructure);
	}

	@Test
	public void testSearchByNameOrDescription() throws Exception {
		String name = StringUtil.randomString();
		String description = StringUtil.randomString();

		addStructure(_classNameId, name, description);
		addStructure(_classNameId, name, StringUtil.randomString());
		addStructure(_classNameId, StringUtil.randomString(), description);

		long[] groupIds =
			new long[] {group.getGroupId(), _otherGroup.getGroupId()};

		List<DDMStructure> ddmStructures = DDMStructureServiceUtil.search(
				TestPropsValues.getCompanyId(), groupIds, _classNameId, name,
				description, StorageType.JSON.getValue(),
				DDMStructureConstants.TYPE_DEFAULT,
				WorkflowConstants.STATUS_ANY, false, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertEquals(3, ddmStructures.size());
	}

	@Test
	public void testSearchCount() throws Exception {
		addStructure(_classNameId, StringUtil.randomString());
		addStructure(_classNameId, StringUtil.randomString());
		addStructure(_classNameId, StringUtil.randomString());

		long[] groupIds =
			new long[] {group.getGroupId(), _otherGroup.getGroupId()};

		int ddmStructuresCount = DDMStructureServiceUtil.searchCount(
				TestPropsValues.getCompanyId(), groupIds, _classNameId,
				StringPool.BLANK, WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(3, ddmStructuresCount);
	}

	@Test
	public void testSearchCountByNameAndDescription() throws Exception {
		String name = StringUtil.randomString();
		String description = StringUtil.randomString();

		addStructure(_classNameId, name, description);
		addStructure(_classNameId, name, StringUtil.randomString());
		addStructure(_classNameId, StringUtil.randomString(), description);

		long[] groupIds =
			new long[] {group.getGroupId(), _otherGroup.getGroupId()};

		int count = DDMStructureServiceUtil.searchCount(
				TestPropsValues.getCompanyId(), groupIds, _classNameId, name,
				description, StorageType.JSON.getValue(),
				DDMStructureConstants.TYPE_DEFAULT,
				WorkflowConstants.STATUS_ANY, true);

		Assert.assertEquals(1, count);
	}

	@Test
	public void testSearchCountByNameOrDescription() throws Exception {
		String name = StringUtil.randomString();
		String description = StringUtil.randomString();

		addStructure(_classNameId, name, description);
		addStructure(_classNameId, name, StringUtil.randomString());
		addStructure(_classNameId, StringUtil.randomString(), description);

		long[] groupIds =
			new long[] {group.getGroupId(), _otherGroup.getGroupId()};

		int count = DDMStructureServiceUtil.searchCount(
				TestPropsValues.getCompanyId(), groupIds, _classNameId, name,
				description, StorageType.JSON.getValue(),
				DDMStructureConstants.TYPE_DEFAULT,
				WorkflowConstants.STATUS_ANY, false);

		Assert.assertEquals(3, count);
	}

	protected void setUpPermissionThreadLocal() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			new AdvancedPermissionChecker() {
				{
					init(_siteAdminUser);
				}
			});
	}

	protected void setUpPrincipalThreadLocal() throws Exception {
		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(_siteAdminUser.getUserId());
	}

	private static long _classNameId;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;

	@DeleteAfterTestRun
	private Group _otherGroup;

	@DeleteAfterTestRun
	private User _siteAdminUser;

}