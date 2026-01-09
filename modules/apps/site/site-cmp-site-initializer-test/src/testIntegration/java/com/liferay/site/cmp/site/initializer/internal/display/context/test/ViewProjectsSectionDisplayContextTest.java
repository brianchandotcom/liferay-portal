/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.cmp.site.initializer.test.util.CMPTestUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
public class ViewProjectsSectionDisplayContextTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		CMPTestUtil.getOrAddGroup(ViewProjectsSectionDisplayContextTest.class);

		_objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMP_PROJECT", TestPropsValues.getCompanyId());
		_themeDisplay = new ThemeDisplay() {
			{
				setCompany(
					_companyLocalService.getCompany(
						TestPropsValues.getCompanyId()));
				setLocale(LocaleUtil.getDefault());
				setURLCurrent("http://localhost:8080/currentURL");
			}
		};
	}

	@Test
	public void testGetCreationMenu() throws Exception {
		CreationMenu creationMenu = ReflectionTestUtil.invoke(
			_getViewProjectsSectionDisplayContext(), "getCreationMenu",
			new Class<?>[0]);

		List<DropdownItem> dropdownItems = (List<DropdownItem>)creationMenu.get(
			"primaryItems");

		Assert.assertEquals(dropdownItems.toString(), 1, dropdownItems.size());

		DropdownItem dropdownItem = dropdownItems.get(0);

		Assert.assertEquals("createProject", _getValue(dropdownItem, "action"));
		Assert.assertEquals("New Project", dropdownItem.get("label"));
		Assert.assertEquals(
			String.valueOf(_objectDefinition.getObjectDefinitionId()),
			_getValue(dropdownItem, "objectDefinitionId"));
		Assert.assertEquals(
			StringBundler.concat(
				_themeDisplay.getPortalURL(), _themeDisplay.getPathMain(),
				GroupConstants.CMS_FRIENDLY_URL,
				"/add_project?objectDefinitionId=",
				_objectDefinition.getObjectDefinitionId(), "&plid=",
				_themeDisplay.getPlid(), "&redirect=",
				_themeDisplay.getURLCurrent()),
			_getValue(dropdownItem, "redirect"));
		Assert.assertEquals("Project", _getValue(dropdownItem, "title"));
	}

	@Test
	public void testGetFDSActionDropdownItems() throws Exception {
		List<FDSActionDropdownItem> fdsActionDropdownItems =
			ReflectionTestUtil.invoke(
				_getViewProjectsSectionDisplayContext(),
				"getFDSActionDropdownItems", new Class<?>[0]);

		Assert.assertEquals(
			fdsActionDropdownItems.toString(), 4,
			fdsActionDropdownItems.size());

		_assertFDSActionDropdownItem(
			"pencil", "edit", "Edit", "get", fdsActionDropdownItems.get(0));
		_assertFDSActionDropdownItem(
			"view", "actionLink", "View", null, fdsActionDropdownItems.get(1));
		_assertFDSActionDropdownItem(
			"users", "view-members", "View Members", null,
			fdsActionDropdownItems.get(2));
		_assertFDSActionDropdownItem(
			"trash", "delete", "Delete", null, fdsActionDropdownItems.get(3));
	}

	private void _assertFDSActionDropdownItem(
		String expectedIcon, String expectedId, String expectedLabel,
		String expectedMethod, FDSActionDropdownItem fdsActionDropdownItem) {

		Map<String, String> data =
			(Map<String, String>)fdsActionDropdownItem.get("data");

		Assert.assertEquals(expectedId, data.get("id"));
		Assert.assertEquals(expectedMethod, data.get("method"));

		Assert.assertEquals(expectedIcon, fdsActionDropdownItem.get("icon"));
		Assert.assertEquals(expectedLabel, fdsActionDropdownItem.get("label"));
	}

	private String _getValue(DropdownItem dropdownItem, String key) {
		return MapUtil.getString(
			(HashMap<String, Object>)dropdownItem.get("data"), key);
	}

	private Object _getViewProjectsSectionDisplayContext() throws Exception {
		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, _themeDisplay);

		_fragmentRenderer.render(
			null, httpServletRequest, new MockHttpServletResponse());

		return httpServletRequest.getAttribute(
			"com.liferay.site.cmp.site.initializer.internal.display.context." +
				"ViewProjectsSectionDisplayContext");
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "component.name=com.liferay.site.cmp.site.initializer.internal.fragment.renderer.ViewProjectsJSPSectionFragmentRenderer"
	)
	private FragmentRenderer _fragmentRenderer;

	@Inject
	private Language _language;

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private ThemeDisplay _themeDisplay;

}