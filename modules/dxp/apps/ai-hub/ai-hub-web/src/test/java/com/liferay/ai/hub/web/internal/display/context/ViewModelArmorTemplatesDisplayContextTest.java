/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.display.context;

import com.liferay.ai.hub.web.internal.test.util.DisplayContextTestUtil;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author João Victor Alves
 */
public class ViewModelArmorTemplatesDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_setUpLanguageUtil();

		HttpServletRequest httpServletRequest =
			DisplayContextTestUtil.setUpHttpServletRequest();

		DisplayContextTestUtil.setUpThemeDisplay(
			Mockito.mock(Company.class), Mockito.mock(Group.class),
			httpServletRequest);

		_viewModelArmorTemplatesDisplayContext =
			new ViewModelArmorTemplatesDisplayContext(httpServletRequest);
	}

	@Test
	public void testGetCreationMenu() throws Exception {
		CreationMenu creationMenu =
			_viewModelArmorTemplatesDisplayContext.getCreationMenu();

		List<DropdownItem> dropdownItems = (List<DropdownItem>)creationMenu.get(
			"primaryItems");

		Assert.assertEquals(dropdownItems.toString(), 1, dropdownItems.size());

		DropdownItem dropdownItem = dropdownItems.get(0);

		Assert.assertEquals(
			"http://localhost:8080/web/test/guardrails",
			dropdownItem.get("href"));
		Assert.assertEquals("new-guardrail", dropdownItem.get("label"));
	}

	@Test
	public void testGetFDSActionDropdownItems() throws Exception {
		List<FDSActionDropdownItem> fdsActionDropdownItems =
			_viewModelArmorTemplatesDisplayContext.getFDSActionDropdownItems();

		Assert.assertEquals(
			fdsActionDropdownItems.toString(), 2,
			fdsActionDropdownItems.size());

		_assertFDSActionDropdownItem(
			fdsActionDropdownItems.get(0),
			"http://localhost:8080/web/test/guardrails" +
				"?externalReferenceCode={externalReferenceCode}",
			"view", "view", "view", "get", null);
		_assertFDSActionDropdownItem(
			fdsActionDropdownItems.get(1),
			"/o/ai-hub/v1.0/model-armor-templates/by-external-reference-code" +
				"/{externalReferenceCode}",
			"trash", "delete", "delete", "delete", "async");
	}

	private void _assertFDSActionDropdownItem(
		FDSActionDropdownItem fdsActionDropdownItem, String href, String icon,
		String id, String label, String method, String target) {

		Map<String, String> data =
			(Map<String, String>)fdsActionDropdownItem.get("data");

		Assert.assertEquals(id, data.get("id"));
		Assert.assertEquals(method, data.get("method"));

		Assert.assertEquals(href, fdsActionDropdownItem.get("href"));
		Assert.assertEquals(icon, fdsActionDropdownItem.get("icon"));
		Assert.assertEquals(label, fdsActionDropdownItem.get("label"));
		Assert.assertEquals(target, fdsActionDropdownItem.get("target"));
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(Mockito.mock(Language.class));

		Mockito.when(
			LanguageUtil.get(
				Mockito.any(HttpServletRequest.class), Mockito.anyString())
		).thenAnswer(
			invocation -> invocation.getArgument(1)
		);
	}

	private ViewModelArmorTemplatesDisplayContext
		_viewModelArmorTemplatesDisplayContext;

}