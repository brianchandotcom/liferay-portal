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

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.Locale;

import javax.portlet.ActionRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
@Sync
public class DuplicateFragmentEntryLinkMVCActionCommandTest {

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
		_layout = LayoutTestUtil.addLayout(_group);

		_serviceContext = _getServiceContext(
			_group, TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);

		Registry registry = RegistryUtil.getRegistry();

		_serviceRegistration = registry.registerService(
			FragmentRenderer.class, new TestFragmentRenderer());
	}

	@After
	public void tearDown() {
		_serviceRegistration.unregister();

		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testDuplicateDynamicFragment() throws Exception {
		TestFragmentRenderer testFragmentRenderer = new TestFragmentRenderer();

		FragmentEntryLink originalFragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0, 0,
				PortalUtil.getClassNameId(Layout.class.getName()),
				_layout.getPlid(), StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, 0, testFragmentRenderer.getKey(),
				_serviceContext);

		ActionRequest actionRequest = _getMockActionRequest(
			originalFragmentEntryLink.getFragmentEntryLinkId());

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "_duplicateFragmentEntryLink",
			new Class<?>[] {ActionRequest.class}, actionRequest);

		Assert.assertNotNull(jsonObject);

		Assert.assertTrue(jsonObject.has("configuration"));
		Assert.assertTrue(jsonObject.has("editableValues"));
		Assert.assertTrue(jsonObject.has("fragmentEntryKey"));
		Assert.assertTrue(jsonObject.has("fragmentEntryLinkId"));
		Assert.assertTrue(jsonObject.has("name"));

		Assert.assertEquals(
			testFragmentRenderer.getConfiguration(null),
			jsonObject.getString("configuration"));
		Assert.assertEquals(
			testFragmentRenderer.getKey(),
			jsonObject.getString("fragmentEntryKey"));
		Assert.assertEquals(
			testFragmentRenderer.getLabel(null), jsonObject.getString("name"));

		FragmentEntryLink persistedFragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				jsonObject.getLong("fragmentEntryLinkId"));

		Assert.assertNotNull(persistedFragmentEntryLink);

		Assert.assertNotEquals(
			originalFragmentEntryLink.getFragmentEntryLinkId(),
			persistedFragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertEquals(
			originalFragmentEntryLink.getClassNameId(),
			persistedFragmentEntryLink.getClassNameId());
		Assert.assertEquals(
			originalFragmentEntryLink.getClassPK(),
			persistedFragmentEntryLink.getClassPK());
		Assert.assertEquals(
			originalFragmentEntryLink.getConfiguration(),
			persistedFragmentEntryLink.getConfiguration());
		Assert.assertEquals(
			originalFragmentEntryLink.getCss(),
			persistedFragmentEntryLink.getCss());
		Assert.assertEquals(
			originalFragmentEntryLink.getEditableValues(),
			persistedFragmentEntryLink.getEditableValues());
		Assert.assertEquals(
			originalFragmentEntryLink.getFragmentEntryId(),
			persistedFragmentEntryLink.getFragmentEntryId());
		Assert.assertEquals(
			originalFragmentEntryLink.getHtml(),
			persistedFragmentEntryLink.getHtml());
		Assert.assertEquals(
			originalFragmentEntryLink.getJs(),
			persistedFragmentEntryLink.getJs());
		Assert.assertEquals(
			originalFragmentEntryLink.getRendererKey(),
			persistedFragmentEntryLink.getRendererKey());
	}

	@Test
	public void testDuplicateFragmentEntryLink() throws Exception {
		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				StringUtil.randomString(), StringPool.BLANK, _serviceContext);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				StringUtil.randomString(), StringUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), "{\"fieldSets\":[]}", 0,
				FragmentConstants.TYPE_COMPONENT,
				WorkflowConstants.STATUS_APPROVED, _serviceContext);

		FragmentEntryLink originalFragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), fragmentEntry.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(),
				PortalUtil.getClassNameId(Layout.class.getName()),
				_layout.getPlid(), fragmentEntry.getCss(),
				fragmentEntry.getHtml(), fragmentEntry.getJs(),
				fragmentEntry.getConfiguration(), "{}",
				StringUtil.randomString(), 0, StringUtil.randomString(),
				_serviceContext);

		ActionRequest actionRequest = _getMockActionRequest(
			originalFragmentEntryLink.getFragmentEntryLinkId());

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "_duplicateFragmentEntryLink",
			new Class<?>[] {ActionRequest.class}, actionRequest);

		Assert.assertNotNull(jsonObject);

		Assert.assertTrue(jsonObject.has("configuration"));
		Assert.assertTrue(jsonObject.has("editableValues"));
		Assert.assertTrue(jsonObject.has("fragmentEntryKey"));
		Assert.assertTrue(jsonObject.has("fragmentEntryLinkId"));
		Assert.assertTrue(jsonObject.has("name"));

		Assert.assertEquals(
			fragmentEntry.getFragmentEntryKey(),
			jsonObject.getString("fragmentEntryKey"));
		Assert.assertEquals(
			fragmentEntry.getName(), jsonObject.getString("name"));

		FragmentEntryLink persistedFragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				jsonObject.getLong("fragmentEntryLinkId"));

		Assert.assertNotNull(persistedFragmentEntryLink);

		Assert.assertNotEquals(
			originalFragmentEntryLink.getFragmentEntryLinkId(),
			persistedFragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertEquals(
			originalFragmentEntryLink.getClassNameId(),
			persistedFragmentEntryLink.getClassNameId());
		Assert.assertEquals(
			originalFragmentEntryLink.getClassPK(),
			persistedFragmentEntryLink.getClassPK());
		Assert.assertEquals(
			originalFragmentEntryLink.getConfiguration(),
			persistedFragmentEntryLink.getConfiguration());
		Assert.assertEquals(
			originalFragmentEntryLink.getCss(),
			persistedFragmentEntryLink.getCss());
		Assert.assertEquals(
			originalFragmentEntryLink.getEditableValues(),
			persistedFragmentEntryLink.getEditableValues());
		Assert.assertEquals(
			originalFragmentEntryLink.getFragmentEntryId(),
			persistedFragmentEntryLink.getFragmentEntryId());
		Assert.assertEquals(
			originalFragmentEntryLink.getHtml(),
			persistedFragmentEntryLink.getHtml());
		Assert.assertEquals(
			originalFragmentEntryLink.getJs(),
			persistedFragmentEntryLink.getJs());
		Assert.assertEquals(
			originalFragmentEntryLink.getRendererKey(),
			persistedFragmentEntryLink.getRendererKey());
	}

	@Test
	public void testDuplicateInvalidFragmentEntryLink() throws Exception {
		ActionRequest actionRequest = _getMockActionRequest(
			RandomTestUtil.randomLong());

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "_duplicateFragmentEntryLink",
			new Class<?>[] {ActionRequest.class}, actionRequest);

		Assert.assertNotNull(jsonObject);

		Assert.assertTrue(jsonObject.has("error"));

		Assert.assertEquals(
			"the-section-could-not-be-duplicated-because-it-has-been-deleted",
			jsonObject.getString("error"));
	}

	private MockActionRequest _getMockActionRequest(long fragmentEntryLinkId)
		throws PortalException {

		ThemeDisplay themeDisplay = _getThemeDisplay();

		MockActionRequest mockActionRequest = new MockActionRequest(
			themeDisplay);

		mockActionRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		mockActionRequest.addParameter(
			"fragmentEntryLinkId", String.valueOf(fragmentEntryLinkId));

		return mockActionRequest;
	}

	private ServiceContext _getServiceContext(Group group, long userId) {
		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE, new MockActionResponse());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group, userId);

		serviceContext.setRequest(httpServletRequest);

		return serviceContext;
	}

	private ThemeDisplay _getThemeDisplay() throws PortalException {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);
		themeDisplay.setLayoutSet(_layout.getLayoutSet());

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
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject(
		filter = "mvc.command.name=/content_layout/duplicate_fragment_entry_link"
	)
	private MVCActionCommand _mvcActionCommand;

	private ServiceContext _serviceContext;
	private ServiceRegistration<FragmentRenderer> _serviceRegistration;

	private static class MockActionRequest
		extends MockLiferayPortletActionRequest {

		public MockActionRequest(ThemeDisplay themeDisplay) {
			_themeDisplay = themeDisplay;
		}

		@Override
		public HttpServletRequest getHttpServletRequest() {
			MockHttpServletRequest httpServletRequest =
				new MockHttpServletRequest();

			httpServletRequest.setAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE, new MockActionResponse());
			httpServletRequest.setAttribute(
				WebKeys.THEME_DISPLAY, _themeDisplay);

			return httpServletRequest;
		}

		private final ThemeDisplay _themeDisplay;

	}

	private static class MockActionResponse
		extends MockLiferayPortletActionResponse {

		@Override
		public HttpServletResponse getHttpServletResponse() {
			return new MockHttpServletResponse();
		}

	}

	private static class TestFragmentRenderer implements FragmentRenderer {

		@Override
		public String getCollectionKey() {
			return "test-collection";
		}

		@Override
		public String getConfiguration(
			FragmentRendererContext fragmentRendererContext) {

			return "{\"fieldSets\":[]}";
		}

		@Override
		public String getKey() {
			return "test-key";
		}

		@Override
		public String getLabel(Locale locale) {
			return "test-label";
		}

		@Override
		public void render(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		}

	}

}