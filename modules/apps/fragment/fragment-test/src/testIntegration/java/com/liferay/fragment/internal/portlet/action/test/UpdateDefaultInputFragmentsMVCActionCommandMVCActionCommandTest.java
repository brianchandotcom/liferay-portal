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

package com.liferay.fragment.internal.portlet.action.test;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.helper.DefaultInputFragmentEntryConfigurationProvider;
import com.liferay.info.field.type.BooleanInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
@Sync
public class UpdateDefaultInputFragmentsMVCActionCommandMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());
	}

	@Test
	public void testUpdateDefaultInputFragments() throws Exception {
		JSONObject valuesJSONObject = JSONUtil.put(
			BooleanInfoFieldType.INSTANCE.getName(),
			JSONUtil.put(
				"groupKey", _group.getGroupKey()
			).put(
				"key", RandomTestUtil.randomString()
			)
		).put(
			TextInfoFieldType.INSTANCE.getName(),
			JSONUtil.put(
				"groupKey", _group.getGroupKey()
			).put(
				"key", RandomTestUtil.randomString()
			)
		);

		ReflectionTestUtil.invoke(
			_mvcActionCommand, "doProcessAction",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			_getMockLiferayPortletActionRequest(valuesJSONObject.toString()),
			new MockLiferayPortletActionResponse());

		JSONObject defaultInputFragmentEntryKeysJSONObject =
			_defaultInputFragmentEntryConfigurationProvider.
				getDefaultInputFragmentEntryKeysJSONObject(_group.getGroupId());

		_assertEqualJSONObject(
			valuesJSONObject.getJSONObject(
				BooleanInfoFieldType.INSTANCE.getName()),
			defaultInputFragmentEntryKeysJSONObject.getJSONObject(
				BooleanInfoFieldType.INSTANCE.getName()));

		_assertEqualJSONObject(
			valuesJSONObject.getJSONObject(
				TextInfoFieldType.INSTANCE.getName()),
			defaultInputFragmentEntryKeysJSONObject.getJSONObject(
				TextInfoFieldType.INSTANCE.getName()));
	}

	private void _assertEqualJSONObject(
			JSONObject expectedJSONObject, JSONObject actualJSONObject)
		throws Exception {

		Assert.assertEquals(
			_objectMapper.readTree(expectedJSONObject.toString()),
			_objectMapper.readTree(actualJSONObject.toString()));
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
			String values)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		mockLiferayPortletActionRequest.setParameter("values", values);

		return mockLiferayPortletActionRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);

		LayoutSet layoutSet = _group.getPublicLayoutSet();

		themeDisplay.setLookAndFeel(layoutSet.getTheme(), null);

		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DefaultInputFragmentEntryConfigurationProvider
		_defaultInputFragmentEntryConfigurationProvider;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject(
		filter = "mvc.command.name=/fragment/update_default_input_fragment_entries"
	)
	private MVCActionCommand _mvcActionCommand;

	private final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
		}
	};

}