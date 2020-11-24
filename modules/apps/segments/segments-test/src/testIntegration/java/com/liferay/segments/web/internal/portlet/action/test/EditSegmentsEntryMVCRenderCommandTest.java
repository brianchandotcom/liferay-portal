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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
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

	@Test
	public void testGetProps() throws Exception {
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			_getMockLiferayPortletRenderRequests();

		MockLiferayPortletRenderResponse mockLiferayPortletRenderResponse =
			new MockLiferayPortletRenderResponse();

		User user = TestPropsValues.getUser();

		SegmentsEntry segmentsEntry = _addSegmentEntry(
			String.format("(firstName eq '%s')", user.getFirstName()));

		mockLiferayPortletRenderRequest.setParameter(
			"segmentsEntryId",
			String.valueOf(segmentsEntry.getSegmentsEntryId()));

		_mvcRenderCommand.render(
			mockLiferayPortletRenderRequest, mockLiferayPortletRenderResponse);

		Map<String, Object> props = ReflectionTestUtil.invoke(
			mockLiferayPortletRenderRequest.getAttribute(
				"EDIT_SEGMENTS_ENTRY_DISPLAY_CONTEXT"),
			"getProps", new Class<?>[0]);

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
	}

	private SegmentsEntry _addSegmentEntry(String filterString)
		throws Exception {

		Criteria criteria = new Criteria();

		_userSegmentsCriteriaContributor.contribute(
			criteria, filterString, Criteria.Conjunction.AND);

		return SegmentsTestUtil.addSegmentsEntry(
			TestPropsValues.getGroupId(),
			CriteriaSerializer.serialize(criteria), User.class.getName());
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
		themeDisplay.setSiteGroupId(TestPropsValues.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());

		return themeDisplay;
	}

	private static Company _company;

	@Inject
	private static CompanyLocalService _companyLocalService;

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