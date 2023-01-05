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

package com.liferay.client.extension.internal.event.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.service.ClientExtensionEntryLocalService;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class ClientExtensionServicePreActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		_layout = LayoutTestUtil.addTypeContentLayout(
			TestPropsValues.getUserId(), _group);
	}

	@Test
	public void testProcessServicePreActionLayoutFavicon() throws Exception {
		_addFaviconClientExtensionEntry();

		_clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(Layout.class), _layout.getPlid(),
			_clientExtensionEntry.getExternalReferenceCode(),
			ClientExtensionEntryConstants.TYPE_THEME_FAVICON, StringPool.BLANK);

		_assertFaviconURL();
	}

	@Test
	public void testProcessServicePreActionLayoutSetFavicon() throws Exception {
		_addFaviconClientExtensionEntry();

		LayoutSet layoutSet = _group.getPublicLayoutSet();

		_clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(LayoutSet.class), layoutSet.getLayoutSetId(),
			_clientExtensionEntry.getExternalReferenceCode(),
			ClientExtensionEntryConstants.TYPE_THEME_FAVICON, StringPool.BLANK);

		_assertFaviconURL();
	}

	@Test
	public void testProcessServicePreActionLayoutSetThemeCSS()
		throws Exception {

		_addThemeCSSClientExtensionEntry();

		LayoutSet layoutSet = _group.getPublicLayoutSet();

		_clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(LayoutSet.class), layoutSet.getLayoutSetId(),
			_clientExtensionEntry.getExternalReferenceCode(),
			ClientExtensionEntryConstants.TYPE_THEME_CSS, StringPool.BLANK);

		_assertThemeCSSURLs(_layout, Collections.emptyMap());
	}

	@Test
	public void testProcessServicePreActionLayoutThemeCSS() throws Exception {
		_addThemeCSSClientExtensionEntry();

		_clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(Layout.class), _layout.getPlid(),
			_clientExtensionEntry.getExternalReferenceCode(),
			ClientExtensionEntryConstants.TYPE_THEME_CSS, StringPool.BLANK);

		_assertThemeCSSURLs(_layout, Collections.emptyMap());
	}

	@Test
	public void testProcessServicePreActionMasterLayoutFavicon()
		throws Exception {

		_addFaviconClientExtensionEntry();

		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT, 0,
				WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(Layout.class),
			masterLayoutPageTemplateEntry.getPlid(),
			_clientExtensionEntry.getExternalReferenceCode(),
			ClientExtensionEntryConstants.TYPE_THEME_FAVICON, StringPool.BLANK);

		_layout.setMasterLayoutPlid(masterLayoutPageTemplateEntry.getPlid());

		_layoutLocalService.updateLayout(_layout);

		_assertFaviconURL();
	}

	@Test
	public void testProcessServicePreActionMasterLayoutThemeCSS()
		throws Exception {

		_addThemeCSSClientExtensionEntry();

		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT, 0,
				WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(Layout.class),
			masterLayoutPageTemplateEntry.getPlid(),
			_clientExtensionEntry.getExternalReferenceCode(),
			ClientExtensionEntryConstants.TYPE_THEME_CSS, StringPool.BLANK);

		_layout.setMasterLayoutPlid(masterLayoutPageTemplateEntry.getPlid());

		_layoutLocalService.updateLayout(_layout);

		_assertThemeCSSURLs(_layout, Collections.emptyMap());
	}

	private void _addFaviconClientExtensionEntry() throws Exception {
		_clientExtensionEntry =
			_clientExtensionEntryLocalService.addClientExtensionEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				StringPool.BLANK,
				Collections.singletonMap(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()),
				StringPool.BLANK, StringPool.BLANK,
				ClientExtensionEntryConstants.TYPE_THEME_FAVICON,
				UnicodePropertiesBuilder.create(
					true
				).put(
					"url", _URL_FAVICON
				).buildString());
	}

	private void _addThemeCSSClientExtensionEntry() throws Exception {
		_clientExtensionEntry =
			_clientExtensionEntryLocalService.addClientExtensionEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				StringPool.BLANK,
				Collections.singletonMap(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()),
				StringPool.BLANK, StringPool.BLANK,
				ClientExtensionEntryConstants.TYPE_THEME_CSS,
				UnicodePropertiesBuilder.create(
					true
				).put(
					"clayURL", _URL_CLAY_CSS
				).put(
					"mainURL", _URL_MAIN_CSS
				).buildString());
	}

	private void _assertFaviconURL() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest(_layout, Collections.emptyMap());

		_processServicePreAction(mockHttpServletRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)mockHttpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Assert.assertEquals(_URL_FAVICON, themeDisplay.getFaviconURL());
	}

	private void _assertThemeCSSURLs(Layout layout, Map<String, String> params)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest(layout, params);

		_processServicePreAction(mockHttpServletRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)mockHttpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Assert.assertEquals(_URL_CLAY_CSS, themeDisplay.getClayCSSURL());
		Assert.assertEquals(_URL_MAIN_CSS, themeDisplay.getMainCSSURL());
	}

	private LifecycleAction _getLifecycleAction() {
		Bundle bundle = FrameworkUtil.getBundle(
			ClientExtensionServicePreActionTest.class);

		ServiceTrackerList<LifecycleAction> lifecycleActions =
			ServiceTrackerListFactory.open(
				bundle.getBundleContext(), LifecycleAction.class,
				"(key=servlet.service.events.pre)");

		for (LifecycleAction lifecycleAction : lifecycleActions) {
			Class<?> clazz = lifecycleAction.getClass();

			if (Objects.equals(
					clazz.getName(),
					"com.liferay.client.extension.internal.events." +
						"ClientExtensionsServicePreAction")) {

				return lifecycleAction;
			}
		}

		throw new AssertionError(
			"ClientExtensionsServicePreAction is not registered");
	}

	private MockHttpServletRequest _getMockHttpServletRequest(
			Layout layout, Map<String, String> params)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(layout));

		mockHttpServletRequest.setParameters(params);

		return mockHttpServletRequest;
	}

	private ThemeDisplay _getThemeDisplay(Layout layout) throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLayout(layout);
		themeDisplay.setLifecycleRender(true);
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private void _processServicePreAction(
			MockHttpServletRequest mockHttpServletRequest)
		throws Exception {

		LifecycleAction lifecycleAction = _getLifecycleAction();

		lifecycleAction.processLifecycleEvent(
			new LifecycleEvent(
				mockHttpServletRequest, new MockHttpServletResponse()));
	}

	private static final String _URL_CLAY_CSS =
		"http://" + RandomTestUtil.randomString() + ".com/styles.css";

	private static final String _URL_FAVICON =
		"http://" + RandomTestUtil.randomString() + ".com";

	private static final String _URL_MAIN_CSS =
		"http://" + RandomTestUtil.randomString() + ".com/main.css";

	private ClientExtensionEntry _clientExtensionEntry;

	@Inject
	private ClientExtensionEntryLocalService _clientExtensionEntryLocalService;

	@Inject
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private Portal _portal;

}