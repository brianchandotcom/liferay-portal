/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.cmp.site.initializer.test.util.CMPTestUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.io.Serializable;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Carolina Barbosa
 */
@FeatureFlags(
	featureFlags = {@FeatureFlag("LPD-17564"), @FeatureFlag("LPD-58677")}
)
@RunWith(Arquillian.class)
@Sync
public class ViewAssigneeSectionDisplayContextTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		CMPTestUtil.getOrAddGroup(ViewAssigneeSectionDisplayContextTest.class);

		_taskObjectEntry = CMPTestUtil.addTaskObjectEntry();

		_httpServletRequest = new MockHttpServletRequest();

		_httpServletRequest.setAttribute(
			InfoDisplayWebKeys.INFO_ITEM, _taskObjectEntry);

		_themeDisplay = new ThemeDisplay() {
			{
				setCompany(
					_companyLocalService.fetchCompany(
						TestPropsValues.getCompanyId()));
				setLocale(LocaleUtil.US);
				setScopeGroupId(TestPropsValues.getGroupId());
			}
		};

		_httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, _themeDisplay);
	}

	@Test
	public void testGetProperties() throws Exception {
		Map<String, Object> properties = _getProperties();

		Assert.assertEquals("Assignee", properties.get("label"));
		Assert.assertEquals("ObjectField_assignTo", properties.get("name"));
		Assert.assertEquals(
			"/o/cmp/assignee-context/", properties.get("searchURL"));
		Assert.assertTrue((Boolean)properties.get("visible"));

		ClassName className = _classNameLocalService.getClassName(
			Role.class.getName());
		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.USER);

		_assertAssigneeFieldValue(
			className.getClassNameId(), role.getRoleId(),
			role.getExternalReferenceCode(), null, role.getName(), "role");

		className = _classNameLocalService.getClassName(User.class.getName());
		User user = _userLocalService.getUser(_taskObjectEntry.getUserId());

		_assertAssigneeFieldValue(
			className.getClassNameId(), user.getUserId(),
			user.getExternalReferenceCode(), user.getPortraitURL(_themeDisplay),
			user.getFullName(), "user");
	}

	private void _assertAssigneeFieldValue(
			long classNameId, long classPK,
			String expectedExternalReferenceCode, String expectedImage,
			String expectedName, String expectedType)
		throws Exception {

		_taskObjectEntry = _objectEntryLocalService.partialUpdateObjectEntry(
			_taskObjectEntry.getUserId(), _taskObjectEntry.getObjectEntryId(),
			_taskObjectEntry.getObjectEntryFolderId(),
			HashMapBuilder.<String, Serializable>put(
				"assignTo",
				HashMapBuilder.put(
					"classNameId", classNameId
				).put(
					"classPK", classPK
				).build()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_httpServletRequest.setAttribute(
			InfoDisplayWebKeys.INFO_ITEM, _taskObjectEntry);

		JSONObject jsonObject = _jsonFactory.createJSONObject(_getProperties());

		JSONAssert.assertEquals(
			JSONUtil.put(
				"externalReferenceCode", expectedExternalReferenceCode
			).put(
				"image", expectedImage
			).put(
				"name", expectedName
			).put(
				"type", expectedType
			).toString(),
			String.valueOf(jsonObject.getJSONObject("value")), true);
	}

	private Map<String, Object> _getProperties() throws Exception {
		_fragmentRenderer.render(
			null, _httpServletRequest, new MockHttpServletResponse());

		return ReflectionTestUtil.invoke(
			_httpServletRequest.getAttribute(
				"com.liferay.site.cmp.site.initializer.internal.display." +
					"context.ViewAssigneeSectionDisplayContext"),
			"getProperties", new Class<?>[0]);
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "component.name=com.liferay.site.cmp.site.initializer.internal.fragment.renderer.ViewAssigneeJSPSectionFragmentRenderer"
	)
	private FragmentRenderer _fragmentRenderer;

	private HttpServletRequest _httpServletRequest;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	private ObjectEntry _taskObjectEntry;
	private ThemeDisplay _themeDisplay;

	@Inject
	private UserLocalService _userLocalService;

}