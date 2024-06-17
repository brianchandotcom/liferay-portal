/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.taglib.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.taglib.servlet.taglib.TemplateSelectorTag;

import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPageContext;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class TemplateSelectorTagTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_ddmTemplate = _ddmTemplateLocalService.addTemplate(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(TemplateEntry.class), 0,
			_portal.getClassNameId(TemplateEntry.class),
			Collections.singletonMap(
				LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()),
			null, DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, StringPool.BLANK,
			TemplateConstants.LANG_TYPE_FTL, RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test
	public void testBackardsCompatibilityGetDisplayStyleGroupId()
		throws Exception {

		TemplateSelectorTag templateSelectorTag = new TemplateSelectorTag();

		templateSelectorTag.setDisplayStyleGroupKey(_group.getGroupKey());
		templateSelectorTag.setPageContext(
			new MockPageContext(
				null, _getMockHttpServletRequest(),
				new MockHttpServletResponse()));

		Assert.assertEquals(
			_group.getGroupId(), templateSelectorTag.getDisplayStyleGroupId());
	}

	@Test
	public void testDoStartTagWithDisplayStyleGroupId() throws Exception {
		TemplateSelectorTag templateSelectorTag = new TemplateSelectorTag();

		templateSelectorTag.setClassName(TemplateEntry.class.getName());
		templateSelectorTag.setDisplayStyle(
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				_ddmTemplate.getTemplateKey());
		templateSelectorTag.setDisplayStyleGroupId(_group.getGroupId());

		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest();

		templateSelectorTag.setPageContext(
			new MockPageContext(
				null, mockHttpServletRequest, new MockHttpServletResponse()));

		templateSelectorTag.doStartTag();

		_assertTemplateSelectorProps(
			templateSelectorTag.getAttributeNamespace(),
			mockHttpServletRequest);
	}

	@Test
	public void testDoStartTagWithDisplayStyleGroupKey() throws Exception {
		TemplateSelectorTag templateSelectorTag = new TemplateSelectorTag();

		templateSelectorTag.setClassName(TemplateEntry.class.getName());
		templateSelectorTag.setDisplayStyle(
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				_ddmTemplate.getTemplateKey());
		templateSelectorTag.setDisplayStyleGroupKey(_group.getGroupKey());

		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest();

		templateSelectorTag.setPageContext(
			new MockPageContext(
				null, mockHttpServletRequest, new MockHttpServletResponse()));

		templateSelectorTag.doStartTag();

		_assertTemplateSelectorProps(
			templateSelectorTag.getAttributeNamespace(),
			mockHttpServletRequest);
	}

	private void _assertTemplateSelectorProps(
			String attributeNamespace,
			MockHttpServletRequest mockHttpServletRequest)
		throws Exception {

		Map<String, Object> templateSelectorProps =
			(Map<String, Object>)mockHttpServletRequest.getAttribute(
				attributeNamespace + "templateSelectorProps");

		Assert.assertEquals(
			_group.getGroupId(),
			templateSelectorProps.get("displayStyleGroupId"));
		Assert.assertEquals(
			_group.getGroupKey(),
			templateSelectorProps.get("displayStyleGroupKey"));

		JSONArray templateSelectorPropsJSONArray =
			(JSONArray)templateSelectorProps.get("items");

		for (int i = 0; i < templateSelectorPropsJSONArray.length(); i++) {
			JSONObject templateSelectorPropsJSONObject =
				templateSelectorPropsJSONArray.getJSONObject(i);

			JSONArray itemsJSONArray =
				templateSelectorPropsJSONObject.getJSONArray("items");

			for (int j = 0; j < itemsJSONArray.length(); j++) {
				JSONObject itemsJSONObject = itemsJSONArray.getJSONObject(j);

				Assert.assertTrue(itemsJSONObject.has("groupId"));
				Assert.assertTrue(itemsJSONObject.has("groupKey"));

				Group group = _groupLocalService.fetchGroup(
					itemsJSONObject.getLong("groupId"));

				Assert.assertEquals(
					group.getGroupKey(), itemsJSONObject.getString("groupKey"));
			}
		}
	}

	private MockHttpServletRequest _getMockHttpServletRequest()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(_group.getCompanyId()));
		themeDisplay.setLocale(LocaleUtil.getDefault());
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setRequest(mockHttpServletRequest);
		themeDisplay.setResponse(new MockHttpServletResponse());
		themeDisplay.setScopeGroupId(_group.getGroupId());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	private DDMTemplate _ddmTemplate;

	@Inject
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private Portal _portal;

}