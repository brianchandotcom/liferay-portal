/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.fragment.renderer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.SimplePermissionChecker;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class BreadcrumbComponentSectionFragmentRendererTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), StringUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), StringUtil.randomString()
			).build(),
			DepotConstants.TYPE_SPACE,
			ServiceContextTestUtil.getServiceContext());
	}

	@Test
	public void testProps() throws Exception {
		_withAllowedActionId(ActionKeys.VIEW, Assert::assertNull);
		_withAllowedActionId(
			ActionKeys.DELETE,
			actionItems -> _assertLabelsEquals(actionItems, "delete"));
		_withAllowedActionId(
			ActionKeys.PERMISSIONS,
			actionItems -> _assertLabelsEquals(
				actionItems, "permissions", "default-permissions"));
		_withAllowedActionId(
			ActionKeys.UPDATE,
			actionItems -> _assertLabelsEquals(actionItems, "space-settings"));
	}

	private void _assertLabelsEquals(
		JSONArray jsonArray, String... expectedLabels) {

		Assert.assertEquals(expectedLabels.length, jsonArray.length());

		for (int i = 0; i < expectedLabels.length; i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			Assert.assertEquals(
				expectedLabels[i], jsonObject.getString("label"));
		}
	}

	private void _withAllowedActionId(
			String allowedActionId,
			UnsafeConsumer<JSONArray, Exception> unsafeConsumer)
		throws Exception {

		PermissionChecker permissionChecker = new SimplePermissionChecker() {

			@Override
			public boolean hasPermission(
				Group group, String name, String primKey, String actionId) {

				return allowedActionId.equals(actionId);
			}

		};

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setPermissionChecker(permissionChecker);

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.setAttribute(
			InfoDisplayWebKeys.INFO_ITEM, _depotEntry);
		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		Map<String, Object> props = ReflectionTestUtil.invoke(
			_fragmentRenderer, "getProps",
			new Class<?>[] {
				FragmentRendererContext.class, HttpServletRequest.class
			},
			null, httpServletRequest);

		unsafeConsumer.accept((JSONArray)props.get("actionItems"));
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private DepotEntry _depotEntry;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.site.cms.site.initializer.internal.fragment.renderer.BreadcrumbComponentSectionFragmentRenderer"
	)
	private FragmentRenderer _fragmentRenderer;

}