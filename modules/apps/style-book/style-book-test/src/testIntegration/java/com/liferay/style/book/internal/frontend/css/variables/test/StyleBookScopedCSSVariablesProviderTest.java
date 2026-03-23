/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.internal.frontend.css.variables.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.frontend.css.variables.ScopedCSSVariables;
import com.liferay.frontend.css.variables.ScopedCSSVariablesProvider;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.util.Collection;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Gabriel Lima
 */
@RunWith(Arquillian.class)
public class StyleBookScopedCSSVariablesProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_group.getPublicLayoutSet(
		).setThemeId(
			_THEME_ID_CLASSIC
		);

		_layout = LayoutTestUtil.addTypeContentLayout(_group);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, TestPropsValues.getUserId());
	}

	@Test
	public void testGetScopedCSSVariablesCollectionWithNamespacedKeys()
		throws Exception {

		String namespacedJSON = StringBundler.concat(
			"{\"theme:primaryColor\": {\"cssVariableMapping\": ",
			"\"--primary-color\", \"value\": \"#000\"},",
			"\"clay:primaryColor\": {\"cssVariableMapping\": ",
			"\"--clay-primary\", \"value\": \"#fff\"}}");

		_styleBookEntryLocalService.addStyleBookEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_group.getGroupId(), true, namespacedJSON,
			RandomTestUtil.randomString(), null, _THEME_ID_CLASSIC,
			_serviceContext);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLayout(_layout);
		themeDisplay.setSiteGroupId(_group.getGroupId());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		Collection<ScopedCSSVariables> scopedCSSVariablesCollection =
			_styleBookScopedCSSVariablesProvider.
				getScopedCSSVariablesCollection(mockHttpServletRequest);

		Assert.assertEquals(
			scopedCSSVariablesCollection.toString(), 1,
			scopedCSSVariablesCollection.size());

		ScopedCSSVariables scopedCSSVariables =
			scopedCSSVariablesCollection.iterator(
			).next();

		Map<String, String> cssVariables = scopedCSSVariables.getCSSVariables();

		Assert.assertEquals(cssVariables.toString(), 2, cssVariables.size());
		Assert.assertEquals("#000", cssVariables.get("--primary-color"));
		Assert.assertEquals("#fff", cssVariables.get("--clay-primary"));
	}

	private static final String _THEME_ID_CLASSIC = "classic_WAR_classictheme";

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;
	private ServiceContext _serviceContext;

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.style.book.internal.frontend.css.variables.StyleBookScopedCSSVariablesProvider"
	)
	private ScopedCSSVariablesProvider _styleBookScopedCSSVariablesProvider;

}