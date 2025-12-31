/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Marco Galluzzi
 */
@FeatureFlag("LPD-17564")
@RunWith(Arquillian.class)
public class ViewStructuresDisplayContextTest
	extends BaseDisplayContextTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testGetCreationMenu() throws Exception {
		CreationMenu creationMenu = ReflectionTestUtil.invoke(
			_getSectionDisplayContext(getMockHttpServletRequest()),
			"getCreationMenu", new Class<?>[0]);

		List<DropdownItem> dropdownItems = (List<DropdownItem>)creationMenu.get(
			"primaryItems");

		Assert.assertEquals(dropdownItems.toString(), 2, dropdownItems.size());

		_assertDropdownItem(
			dropdownItems.get(0), "content", "L_CMS_CONTENT_STRUCTURES");
		_assertDropdownItem(dropdownItems.get(1), "file", "L_CMS_FILE_TYPES");
	}

	@Test
	public void testGetFDSActionDropdownItems() throws Exception {
		List<FDSActionDropdownItem> fdsActionDropdownItems =
			ReflectionTestUtil.invoke(
				_getSectionDisplayContext(getMockHttpServletRequest()),
				"getFDSActionDropdownItems", new Class<?>[0]);

		Assert.assertEquals(
			fdsActionDropdownItems.toString(), 6,
			fdsActionDropdownItems.size());

		_assertFDSActionDropdownItem(
			fdsActionDropdownItems.get(0), "pencil", "edit", "edit", "get",
			null);
		_assertFDSActionDropdownItem(
			fdsActionDropdownItems.get(1), "list-ul", "viewUsages",
			"view-usages", "get", null);
		_assertFDSActionDropdownItem(
			fdsActionDropdownItems.get(2), "export", "export", "export-as-json",
			"get", null);
		_assertFDSActionDropdownItem(
			fdsActionDropdownItems.get(3), "import", "import",
			"import-and-override", "get", null);
		_assertFDSActionDropdownItem(
			fdsActionDropdownItems.get(4), "password-policies", "permissions",
			"permissions", "get", null);
		_assertFDSActionDropdownItem(
			fdsActionDropdownItems.get(5), "trash", "delete", "delete",
			"delete", Map.of("system", false));
	}

	private void _assertDropdownItem(
		DropdownItem dropdownItem, String expectedLabel,
		String objectFolderExternalReferenceCode) {

		Assert.assertEquals(
			language.get(LocaleUtil.getDefault(), expectedLabel),
			dropdownItem.get("label"));
		Assert.assertEquals(
			GroupConstants.CMS_FRIENDLY_URL +
				"/structure-builder?objectFolderExternalReferenceCode=" +
					objectFolderExternalReferenceCode,
			dropdownItem.get("href"));
	}

	private void _assertFDSActionDropdownItem(
		FDSActionDropdownItem fdsActionDropdownItem, String icon, String id,
		String label, String method, Map<String, Object> visibilityFilters) {

		Map<String, Object> data =
			(Map<String, Object>)fdsActionDropdownItem.get("data");

		Assert.assertEquals(id, data.get("id"));
		Assert.assertEquals(method, data.get("method"));

		Assert.assertEquals(icon, fdsActionDropdownItem.get("icon"));
		Assert.assertEquals(
			language.get(LocaleUtil.getDefault(), label),
			fdsActionDropdownItem.get("label"));

		if (visibilityFilters != null) {
			Assert.assertEquals(
				data.get("visibilityFilters"), visibilityFilters);
		}
	}

	private Object _getSectionDisplayContext(
			HttpServletRequest httpServletRequest)
		throws Exception {

		_fragmentRenderer.render(
			null, httpServletRequest, new MockHttpServletResponse());

		Object viewStructuresDisplayContext = httpServletRequest.getAttribute(
			"com.liferay.site.cms.site.initializer.internal.display.context." +
				"ViewStructuresDisplayContext");

		Assert.assertNotNull(viewStructuresDisplayContext);

		return viewStructuresDisplayContext;
	}

	@Inject(
		filter = "component.name=com.liferay.site.cms.site.initializer.internal.fragment.renderer.ViewStructuresJSPSectionFragmentRenderer"
	)
	private FragmentRenderer _fragmentRenderer;

}