/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.expando.test.util.ExpandoTestUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.Serializable;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jiefeng Wu
 */
@RunWith(Arquillian.class)
public class ExpandoBridgeImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_className = RandomTestUtil.randomString();
		_classPK = RandomTestUtil.randomLong();

		_expandoTable = ExpandoTestUtil.addTable(
			PortalUtil.getClassNameId(_className),
			ExpandoTableConstants.DEFAULT_TABLE_NAME);

		ExpandoColumn hiddenColumn = ExpandoTestUtil.addColumn(
			_expandoTable, "hiddenColumn", ExpandoColumnConstants.STRING);

		ExpandoTestUtil.addValue(
			_expandoTable, hiddenColumn, _classPK, "hiddenValue");

		ExpandoTestUtil.addColumn(
			_expandoTable, "unsetColumn", ExpandoColumnConstants.STRING,
			"unsetDefault");

		_visibleColumn = ExpandoTestUtil.addColumn(
			_expandoTable, "visibleColumn", ExpandoColumnConstants.STRING);

		ExpandoTestUtil.addValue(
			_expandoTable, _visibleColumn, _classPK, "visibleValue");
	}

	@Test
	public void testGetAttributesWithViewPermission() throws Exception {
		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			TestPropsValues.getCompanyId(), _className, _classPK);

		Map<String, Serializable> attributes = expandoBridge.getAttributes();

		Assert.assertEquals("hiddenValue", attributes.get("hiddenColumn"));
		Assert.assertEquals("unsetDefault", attributes.get("unsetColumn"));
		Assert.assertEquals("visibleValue", attributes.get("visibleColumn"));
	}

	@Test
	public void testGetAttributesWithoutViewPermission() throws Exception {
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(), ExpandoColumn.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(_visibleColumn.getColumnId()), _role.getRoleId(),
			new String[] {ActionKeys.VIEW});

		_user = UserTestUtil.addUser();

		_roleLocalService.addUserRole(_user.getUserId(), _role.getRoleId());

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user)) {

			ExpandoBridge expandoBridge =
				ExpandoBridgeFactoryUtil.getExpandoBridge(
					TestPropsValues.getCompanyId(), _className, _classPK);

			Map<String, Serializable> attributes =
				expandoBridge.getAttributes();

			Assert.assertFalse(attributes.containsKey("hiddenColumn"));
			Assert.assertFalse(attributes.containsKey("unsetColumn"));
			Assert.assertEquals(
				"visibleValue", attributes.get("visibleColumn"));
		}
	}

	private String _className;
	private long _classPK;

	@DeleteAfterTestRun
	private ExpandoTable _expandoTable;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@DeleteAfterTestRun
	private Role _role;

	@Inject
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private User _user;

	private ExpandoColumn _visibleColumn;

}