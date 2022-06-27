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
import com.liferay.client.extension.model.ClientExtensionEntryRel;
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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
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
import java.util.Objects;

import junit.framework.Assert;

import org.junit.After;
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
public class ClientExtensionPreActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		_clientExtensionEntryLocalService.deleteClientExtensionEntry(
			_clientExtensionEntry);
		_clientExtensionEntryRelLocalService.deleteClientExtensionEntryRel(
			_clientExtensionEntryRel);
	}

	@Test
	public void testProcessServicePreActionLayout() throws Exception {
		String faviconURL = "http://faviconlayout.com";

		_clientExtensionEntry = _addClientExtensionEntry(faviconURL);

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		_clientExtensionEntryRel =
			_clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
				TestPropsValues.getUserId(),
				_portal.getClassNameId(Layout.class), layout.getPlid(),
				_clientExtensionEntry.getExternalReferenceCode(),
				ClientExtensionEntryConstants.TYPE_THEME_FAVICON);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(layout));

		_processServicePreAction(mockHttpServletRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)mockHttpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Assert.assertEquals(faviconURL, themeDisplay.getFaviconURL());
	}

	@Test
	public void testProcessServicePreActionLayoutSet() throws Exception {
		String faviconURL = "http://faviconlayoutset.com";

		_clientExtensionEntry = _addClientExtensionEntry(faviconURL);

		LayoutSet layoutSet = _layoutSetLocalService.fetchLayoutSet(
			_group.getGroupId(), false);

		_clientExtensionEntryRel =
			_clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
				TestPropsValues.getUserId(),
				_portal.getClassNameId(LayoutSet.class),
				layoutSet.getLayoutSetId(),
				_clientExtensionEntry.getExternalReferenceCode(),
				ClientExtensionEntryConstants.TYPE_THEME_FAVICON);

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(layout));

		_processServicePreAction(mockHttpServletRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)mockHttpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Assert.assertEquals(faviconURL, themeDisplay.getFaviconURL());
	}

	@Test
	public void testProcessServicePreActionMasterLayout() throws Exception {
		String faviconURL = "http://faviconmasterlayout.com";

		_clientExtensionEntry = _addClientExtensionEntry(faviconURL);

		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT, 0,
				WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_clientExtensionEntryRel =
			_clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
				TestPropsValues.getUserId(),
				_portal.getClassNameId(Layout.class),
				masterLayoutPageTemplateEntry.getPlid(),
				_clientExtensionEntry.getExternalReferenceCode(),
				ClientExtensionEntryConstants.TYPE_THEME_FAVICON);

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		layout.setMasterLayoutPlid(masterLayoutPageTemplateEntry.getPlid());

		_layoutLocalService.updateLayout(layout);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(layout));

		_processServicePreAction(mockHttpServletRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)mockHttpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Assert.assertEquals(faviconURL, themeDisplay.getFaviconURL());
	}

	private ClientExtensionEntry _addClientExtensionEntry(String faviconURL)
		throws Exception {

		return _clientExtensionEntryLocalService.addClientExtensionEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			StringPool.BLANK,
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			StringPool.BLANK, StringPool.BLANK,
			ClientExtensionEntryConstants.TYPE_THEME_FAVICON,
			UnicodePropertiesBuilder.create(
				true
			).put(
				"url", faviconURL
			).buildString());
	}

	private LifecycleAction _getLifecycleAction() {
		Bundle bundle = FrameworkUtil.getBundle(
			ClientExtensionPreActionTest.class);

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

		LifecycleEvent lifecycleEvent = new LifecycleEvent(
			mockHttpServletRequest, new MockHttpServletResponse());

		lifecycleAction.processLifecycleEvent(lifecycleEvent);
	}

	private ClientExtensionEntry _clientExtensionEntry;

	@Inject
	private ClientExtensionEntryLocalService _clientExtensionEntryLocalService;

	private ClientExtensionEntryRel _clientExtensionEntryRel;

	@Inject
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject
	private Portal _portal;

}