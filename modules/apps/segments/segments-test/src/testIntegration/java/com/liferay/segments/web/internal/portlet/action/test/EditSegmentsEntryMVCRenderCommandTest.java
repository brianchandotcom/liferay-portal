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

package com.liferay.segments.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class EditSegmentsEntryMVCRenderCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_company = _companyLocalService.fetchCompany(
			TestPropsValues.getCompanyId());
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetPropsWithoutSegmentsEntryId() throws Exception {
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			_getMockLiferayPortletRenderRequests();

		mockLiferayPortletRenderRequest.setParameter(
			"groupId", String.valueOf(_group.getGroupId()));

		MockLiferayPortletRenderResponse mockLiferayPortletRenderResponse =
			new MockLiferayPortletRenderResponse();

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.USER, TestPropsValues.getUser());

		_mvcRenderCommand.render(
			mockLiferayPortletRenderRequest, mockLiferayPortletRenderResponse);

		Map<String, Object> data = ReflectionTestUtil.invoke(
			mockLiferayPortletRenderRequest.getAttribute(
				"EDIT_SEGMENTS_ENTRY_DISPLAY_CONTEXT"),
			"getData", new Class<?>[0]);

		Map<String, Object> props = (Map<String, Object>)data.get("props");

		JSONArray contributorsJSONArray = (JSONArray)props.get("contributors");

		for (Object object : contributorsJSONArray) {
			JSONObject jsonObject = (JSONObject)object;

			JSONObject initialQueryJSONObject = jsonObject.getJSONObject(
				"initialQuery");

			Assert.assertNull(
				"initialQuery is not null", initialQueryJSONObject);
		}

		String defaultLanguageId = (String)props.get("defaultLanguageId");

		Assert.assertEquals(LocaleUtil.US.toString(), defaultLanguageId);

		String formId = (String)props.get("formId");

		Assert.assertNotNull("formId is null", formId);

		long groupId = (long)props.get("groupId");

		Assert.assertEquals(_group.getGroupId(), groupId);

		boolean hasUpdatePermission = (boolean)props.get("hasUpdatePermission");

		Assert.assertTrue(hasUpdatePermission);

		int initialMembersCount = (int)props.get("initialMembersCount");

		Assert.assertEquals(0, initialMembersCount);

		boolean initialSegmentActive = (boolean)props.get(
			"initialSegmentActive");

		Assert.assertFalse(initialSegmentActive);

		JSONObject initialSegmentNameJSONObject = (JSONObject)props.get(
			"initialSegmentName");

		Assert.assertNull(initialSegmentNameJSONObject);

		boolean segmentationEnabled = (boolean)props.get(
			"isSegmentationEnabled");

		Assert.assertTrue(segmentationEnabled);

		String locale = (String)props.get("locale");

		Assert.assertEquals(LocaleUtil.US.toString(), locale);

		String previewMembersURL = (String)props.get("previewMembersURL");

		Assert.assertNotNull("previewMembersURL is null", previewMembersURL);

		String redirect = (String)props.get("redirect");

		Assert.assertNotNull("redirect is null", redirect);

		String requestMembersCountURL = (String)props.get(
			"requestMembersCountURL");

		Assert.assertNotNull(
			"requestMembersCountURL is null", requestMembersCountURL);

		String scopeName = (String)props.get("scopeName");

		Assert.assertNotNull("scopeName is null", scopeName);

		Assert.assertEquals(_group.getDescriptiveName(), scopeName);

		String segmentsConfigurationURL = (String)props.get(
			"segmentsConfigurationURL");

		Assert.assertNotNull(
			"segmentsConfigurationURL is null", segmentsConfigurationURL);

		boolean showInEditMode = (boolean)props.get("showInEditMode");

		Assert.assertTrue(showInEditMode);

		String siteItemSelectorURL = (String)props.get("siteItemSelectorURL");

		Assert.assertNotNull(
			"siteItemSelectorURL is null", siteItemSelectorURL);
	}

	@Test
	public void testGetPropsWithSegmentsEntryId() throws Exception {
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			_getMockLiferayPortletRenderRequests();

		MockLiferayPortletRenderResponse mockLiferayPortletRenderResponse =
			new MockLiferayPortletRenderResponse();

		User user = TestPropsValues.getUser();

		mockLiferayPortletRenderRequest.setAttribute(WebKeys.USER, user);

		SegmentsEntry segmentsEntry = _addSegmentEntry(
			String.format("(firstName eq '%s')", user.getFirstName()));

		mockLiferayPortletRenderRequest.setParameter(
			"segmentsEntryId",
			String.valueOf(segmentsEntry.getSegmentsEntryId()));

		_mvcRenderCommand.render(
			mockLiferayPortletRenderRequest, mockLiferayPortletRenderResponse);

		Map<String, Object> data = ReflectionTestUtil.invoke(
			mockLiferayPortletRenderRequest.getAttribute(
				"EDIT_SEGMENTS_ENTRY_DISPLAY_CONTEXT"),
			"getData", new Class<?>[0]);

		Map<String, Object> props = (Map<String, Object>)data.get("props");

		JSONArray contributorsJSONArray = (JSONArray)props.get("contributors");

		boolean findUserContributor = false;

		for (Object object : contributorsJSONArray) {
			JSONObject jsonObject = (JSONObject)object;

			if (Objects.equals(jsonObject.getString("propertyKey"), "user")) {
				JSONObject initialQueryJSONObject = jsonObject.getJSONObject(
					"initialQuery");

				Assert.assertEquals(
					"and", initialQueryJSONObject.getString("conjunctionName"));
				Assert.assertEquals(
					"group_0", initialQueryJSONObject.getString("groupId"));

				JSONArray itemsJSONArray = initialQueryJSONObject.getJSONArray(
					"items");

				JSONObject itemJSONObject = itemsJSONArray.getJSONObject(0);

				Assert.assertEquals(
					JSONUtil.put(
						"operatorName", "eq"
					).put(
						"propertyName", "firstName"
					).put(
						"value", "Test"
					).toString(),
					itemJSONObject.toString());

				findUserContributor = true;
			}
		}

		Assert.assertTrue(findUserContributor);

		String defaultLanguageId = (String)props.get("defaultLanguageId");

		Assert.assertEquals(LocaleUtil.US.toString(), defaultLanguageId);

		String formId = (String)props.get("formId");

		Assert.assertNotNull("formId is null", formId);

		long groupId = (long)props.get("groupId");

		Assert.assertEquals(_group.getGroupId(), groupId);

		boolean hasUpdatePermission = (boolean)props.get("hasUpdatePermission");

		Assert.assertTrue(hasUpdatePermission);

		int initialMembersCount = (int)props.get("initialMembersCount");

		Assert.assertEquals(1, initialMembersCount);

		boolean initialSegmentActive = (boolean)props.get(
			"initialSegmentActive");

		Assert.assertTrue(initialSegmentActive);

		JSONObject initialSegmentNameJSONObject = (JSONObject)props.get(
			"initialSegmentName");

		Assert.assertEquals(
			segmentsEntry.getName(LocaleUtil.US),
			initialSegmentNameJSONObject.get(LocaleUtil.US.toString()));

		boolean segmentationEnabled = (boolean)props.get(
			"isSegmentationEnabled");

		Assert.assertTrue(segmentationEnabled);

		String locale = (String)props.get("locale");

		Assert.assertEquals(LocaleUtil.US.toString(), locale);

		String previewMembersURL = (String)props.get("previewMembersURL");

		Assert.assertNotNull("previewMembersURL is null", previewMembersURL);

		String redirect = (String)props.get("redirect");

		Assert.assertNotNull("redirect is null", redirect);

		String requestMembersCountURL = (String)props.get(
			"requestMembersCountURL");

		Assert.assertNotNull(
			"requestMembersCountURL is null", requestMembersCountURL);

		String scopeName = (String)props.get("scopeName");

		Assert.assertNotNull("scopeName is null", scopeName);

		Assert.assertEquals(_group.getDescriptiveName(), scopeName);

		String segmentsConfigurationURL = (String)props.get(
			"segmentsConfigurationURL");

		Assert.assertNotNull(
			"segmentsConfigurationURL is null", segmentsConfigurationURL);

		boolean showInEditMode = (boolean)props.get("showInEditMode");

		Assert.assertTrue(showInEditMode);

		String siteItemSelectorURL = (String)props.get("siteItemSelectorURL");

		Assert.assertNull(
			"siteItemSelectorURL is not null", siteItemSelectorURL);
	}

	private SegmentsEntry _addSegmentEntry(String filterString)
		throws Exception {

		Criteria criteria = new Criteria();

		_userSegmentsCriteriaContributor.contribute(
			criteria, filterString, Criteria.Conjunction.AND);

		return SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), CriteriaSerializer.serialize(criteria),
			User.class.getName());
	}

	private MockLiferayPortletRenderRequest
			_getMockLiferayPortletRenderRequests()
		throws Exception {

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		return mockLiferayPortletRenderRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLanguageId(LanguageUtil.getLanguageId(LocaleUtil.US));
		themeDisplay.setLocale(LocaleUtil.US);
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private static Company _company;

	@Inject
	private static CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "mvc.command.name=/segments/edit_segments_entry",
		type = MVCRenderCommand.class
	)
	private MVCRenderCommand _mvcRenderCommand;

	@Inject(
		filter = "segments.criteria.contributor.key=user",
		type = SegmentsCriteriaContributor.class
	)
	private SegmentsCriteriaContributor _userSegmentsCriteriaContributor;

}