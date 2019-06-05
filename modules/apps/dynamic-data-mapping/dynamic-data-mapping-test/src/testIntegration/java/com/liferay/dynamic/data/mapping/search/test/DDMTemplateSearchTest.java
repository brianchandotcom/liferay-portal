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

package com.liferay.dynamic.data.mapping.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Bryan Engler
 */
@RunWith(Arquillian.class)
public class DDMTemplateSearchTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		addStructureAndTemplate();

		_user = UserTestUtil.addUser();

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(_user.getUserId());
	}

	@After
	public void tearDown() throws Exception {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);
	}

	@Test
	public void testSearchWithRegularRoleCompanyPermission() throws Exception {
		_role = RoleTestUtil.addRole(
			StringUtil.randomString(), RoleConstants.TYPE_REGULAR,
			ResourceActionsUtil.getCompositeModelName(
				DDMTemplate.class.getName(), JournalArticle.class.getName()),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), ActionKeys.VIEW);

		RoleLocalServiceUtil.addUserRole(_user.getUserId(), _role);

		assertSearch();
	}

	@Test
	public void testSearchWithRegularRoleIndividualPermission()
		throws Exception {

		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		ResourcePermissionLocalServiceUtil.setResourcePermissions(
			TestPropsValues.getCompanyId(),
			ResourceActionsUtil.getCompositeModelName(
				DDMTemplate.class.getName(), JournalArticle.class.getName()),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(_ddmTemplate.getPrimaryKey()), _role.getRoleId(),
			new String[] {ActionKeys.VIEW});

		RoleLocalServiceUtil.addUserRole(_user.getUserId(), _role);

		assertSearch();
	}

	@Test
	public void testSearchWithSiteRoleGroupTemplatePermission()
		throws Exception {

		_role = RoleTestUtil.addRole(
			StringUtil.randomString(), RoleConstants.TYPE_SITE,
			ResourceActionsUtil.getCompositeModelName(
				DDMTemplate.class.getName(), JournalArticle.class.getName()),
			ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", ActionKeys.VIEW);

		RoleLocalServiceUtil.addUserRole(_user.getUserId(), _role);

		assertSearch();
	}

	protected void addStructureAndTemplate() throws Exception {
		_ddmStructure = DDMStructureTestUtil.addStructure(
			JournalArticle.class.getName());

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getSiteDefault(), "Test Template");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(false);
		serviceContext.setAddGuestPermissions(false);

		_ddmTemplate = DDMTemplateLocalServiceUtil.addTemplate(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			PortalUtil.getClassNameId(DDMStructure.class),
			_ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class), null, nameMap,
			null, DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, null,
			TemplateConstants.LANG_TYPE_VM,
			DDMTemplateTestUtil.getSampleTemplateXSL(), false, false, null,
			null, serviceContext);
	}

	protected void assertSearch() throws Exception {
		List<DDMTemplate> results = DDMTemplateServiceUtil.search(
			TestPropsValues.getCompanyId(),
			new long[] {TestPropsValues.getGroupId()},
			new long[] {PortalUtil.getClassNameId(DDMStructure.class)},
			new long[] {_ddmTemplate.getClassPK()},
			PortalUtil.getClassNameId(JournalArticle.class), StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK, WorkflowConstants.STATUS_ANY,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(results.toString(), 1, results.size());
		Assert.assertEquals(results.get(0), _ddmTemplate);
	}

	@DeleteAfterTestRun
	private DDMStructure _ddmStructure;

	@DeleteAfterTestRun
	private DDMTemplate _ddmTemplate;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;

	@DeleteAfterTestRun
	private Role _role;

	@DeleteAfterTestRun
	private User _user;

}