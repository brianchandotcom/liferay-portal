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

package com.liferay.change.tracking.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.data.engine.renderer.DataLayoutRenderer;
import com.liferay.data.engine.renderer.DataLayoutRendererContext;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Samuel Trong Tran
 */
@RunWith(Arquillian.class)
public class DataLayoutRendererCTTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		String name = StringUtil.toLowerCase(RandomTestUtil.randomString());

		String virtualHostname =
			name + "." + StringUtil.toLowerCase(RandomTestUtil.randomString(3));

		_company = _companyLocalService.addCompany(
			null, name, virtualHostname, virtualHostname, false, 0, true);

		Group group = _groupLocalService.getGroup(
			_company.getCompanyId(), GroupConstants.GUEST);

		_user = UserTestUtil.addUser(
			_company.getCompanyId(), TestPropsValues.getUserId(),
			StringPool.BLANK,
			RandomTestUtil.randomString() + StringPool.AT + _company.getMx(),
			RandomTestUtil.randomString(
				NumericStringRandomizerBumper.INSTANCE,
				UniqueStringRandomizerBumper.INSTANCE),
			LocaleUtil.getDefault(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new long[] {group.getGroupId()},
			ServiceContextTestUtil.getServiceContext());

		Role role = _roleLocalService.getRole(
			_company.getCompanyId(), RoleConstants.ADMINISTRATOR);

		_userLocalService.addRoleUser(role.getRoleId(), _user);

		_ctCollection = _ctCollectionLocalService.addCTCollection(
			_company.getCompanyId(), _user.getUserId(),
			DataLayoutRendererCTTest.class.getName(), null);

		_group = GroupTestUtil.addGroup(
			_company.getCompanyId(), _user.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		_layout = LayoutTestUtil.addLayout(_group);
	}

	@Test
	public void testRenderDataLayoutWithUploadField() throws Exception {
		DataDefinitionResource dataDefinitionResource =
			DataDefinitionResource.builder(
			).checkPermissions(
				false
			).user(
				_user
			).build();

		DataDefinition dataDefinition = DataDefinition.toDTO(
			StringUtil.read(getClass(), "dependencies/definition.json"));

		dataDefinition =
			dataDefinitionResource.postSiteDataDefinitionByContentType(
				_group.getGroupId(), "journal", dataDefinition);

		DataLayoutRendererContext dataLayoutRendererContext =
			new DataLayoutRendererContext();

		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();

		httpServletRequest.setAttribute(
			WebKeys.CURRENT_URL, RandomTestUtil.randomString());

		httpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE,
			new MockLiferayPortletRenderResponse());

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);
		themeDisplay.setLayoutSet(_layout.getLayoutSet());
		themeDisplay.setRequest(httpServletRequest);
		themeDisplay.setResponse(new MockHttpServletResponse());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(_user);

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		dataLayoutRendererContext.setHttpServletRequest(httpServletRequest);

		dataLayoutRendererContext.setHttpServletResponse(
			new MockHttpServletResponse());
		dataLayoutRendererContext.setPersisted(false);
		dataLayoutRendererContext.setPortletNamespace(
			JournalPortletKeys.JOURNAL + StringPool.UNDERLINE);
		dataLayoutRendererContext.setReadOnly(false);

		DataLayout dataLayout = dataDefinition.getDefaultDataLayout();

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			_dataLayoutRenderer.render(
				dataLayout.getId(), dataLayoutRendererContext);
		}
	}

	@Inject
	private static CompanyLocalService _companyLocalService;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static DataLayoutRenderer _dataLayoutRenderer;

	@Inject
	private static GroupLocalService _groupLocalService;

	@Inject
	private static RoleLocalService _roleLocalService;

	@Inject
	private static UserLocalService _userLocalService;

	@DeleteAfterTestRun
	private Company _company;

	private CTCollection _ctCollection;
	private Group _group;
	private Layout _layout;
	private User _user;

}