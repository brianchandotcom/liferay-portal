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
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
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
		_company = CompanyTestUtil.addCompany();

		_user = UserTestUtil.addCompanyAdminUser(_company);

		_group = GroupTestUtil.addGroup(
			_company.getCompanyId(), _user.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		_layout = LayoutTestUtil.addTypeContentLayout(
			_user.getUserId(), _group);
	}

	@Test
	public void testProcessServicePreActionLayoutFavicon() throws Exception {
		_addFaviconClientExtensionEntry();

		_clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
			_user.getUserId(), _portal.getClassNameId(Layout.class),
			_layout.getPlid(), _clientExtensionEntry.getExternalReferenceCode(),
			ClientExtensionEntryConstants.TYPE_THEME_FAVICON, StringPool.BLANK);

		_assertFaviconURL();
	}

	@Test
	public void testProcessServicePreActionLayoutSetFavicon() throws Exception {
		_addFaviconClientExtensionEntry();

		LayoutSet layoutSet = _group.getPublicLayoutSet();

		_clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
			_user.getUserId(), _portal.getClassNameId(LayoutSet.class),
			layoutSet.getLayoutSetId(),
			_clientExtensionEntry.getExternalReferenceCode(),
			ClientExtensionEntryConstants.TYPE_THEME_FAVICON, StringPool.BLANK);

		_assertFaviconURL();
	}

	@Test
	public void testProcessServicePreActionMasterLayoutFavicon() throws Exception {
		_addFaviconClientExtensionEntry();

		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				_user.getUserId(), _group.getGroupId(), 0,
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT, 0,
				WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
			_user.getUserId(), _portal.getClassNameId(Layout.class),
			masterLayoutPageTemplateEntry.getPlid(),
			_clientExtensionEntry.getExternalReferenceCode(),
			ClientExtensionEntryConstants.TYPE_THEME_FAVICON, StringPool.BLANK);

		_layout.setMasterLayoutPlid(masterLayoutPageTemplateEntry.getPlid());

		_layoutLocalService.updateLayout(_layout);

		_assertFaviconURL();
	}

	private void _addFaviconClientExtensionEntry() throws Exception {
		_clientExtensionEntry =
			_clientExtensionEntryLocalService.addClientExtensionEntry(
				RandomTestUtil.randomString(), _user.getUserId(),
				StringPool.BLANK,
				Collections.singletonMap(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()),
				StringPool.BLANK, StringPool.BLANK,
				ClientExtensionEntryConstants.TYPE_THEME_FAVICON,
				UnicodePropertiesBuilder.create(
					true
				).put(
					"url", _FAVICON_URL
				).buildString());
	}

	private void _assertFaviconURL() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest();

		_processServicePreAction(mockHttpServletRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)mockHttpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Assert.assertEquals(_FAVICON_URL, themeDisplay.getFaviconURL());
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

	private MockHttpServletRequest _getMockHttpServletRequest()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		return mockHttpServletRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);
		themeDisplay.setLifecycleRender(true);
		themeDisplay.setUser(_user);

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

	private static final String _FAVICON_URL =
		"http://" + RandomTestUtil.randomString() + ".com";

	private ClientExtensionEntry _clientExtensionEntry;

	@Inject
	private ClientExtensionEntryLocalService _clientExtensionEntryLocalService;

	@Inject
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

	@DeleteAfterTestRun
	private Company _company;

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

	private User _user;

}