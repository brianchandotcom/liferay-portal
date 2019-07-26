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

package com.liferay.blogs.web.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.exception.NoSuchEntryException;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.blogs.web.test.util.MockLiferayPortletConfig;
import com.liferay.blogs.web.test.util.MockLiferayPortletRequest;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;
import com.liferay.trash.kernel.model.TrashEntry;
import com.liferay.trash.kernel.service.TrashEntryLocalService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionRequest;

import javax.servlet.http.HttpServletRequest;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
@Sync
public class EditEntryMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() {
		Registry registry = RegistryUtil.getRegistry();

		StringBundler sb = new StringBundler(3);

		sb.append("(component.name=");
		sb.append("com.liferay.blogs.web.internal.portlet.action.");
		sb.append("EditEntryMVCActionCommand)");

		Filter filter = registry.getFilter(sb.toString());

		_serviceTracker = registry.trackServices(filter);

		_serviceTracker.open();
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceTracker.close();
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		_company = _companyLocalService.getCompany(_group.getCompanyId());
		_layout = LayoutTestUtil.addLayout(_group);
	}

	@Test(expected = NoSuchEntryException.class)
	public void testDeleteEntries() throws PortalException {
		BlogsEntry blogsEntry = _addBlogEntry(RandomTestUtil.randomString());

		_deleteEntries(
			new MockActionRequest(
				_getMockHttpServletRequest(), blogsEntry.getEntryId()),
			false);

		_blogsEntryService.getEntry(blogsEntry.getEntryId());
	}

	@Test
	public void testDeleteEntriesToTrash() throws PortalException {
		BlogsEntry blogsEntry = _addBlogEntry(RandomTestUtil.randomString());

		_deleteEntries(
			new MockActionRequest(
				_getMockHttpServletRequest(), blogsEntry.getEntryId()),
			true);

		Assert.assertNotNull(
			_blogsEntryService.getEntry(blogsEntry.getEntryId()));

		List<TrashEntry> trashEntries = _trashEntryLocalService.getEntries(
			_group.getGroupId());

		Assert.assertFalse(
			"There are not trash elements on the recycle bin",
			trashEntries.isEmpty());
	}

	private BlogsEntry _addBlogEntry(String title) throws PortalException {
		return _blogsEntryService.addEntry(
			title, RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), 1, 1, 1990, 1, 1, true, false,
			new String[0], RandomTestUtil.randomString(), null, null,
			_serviceContext);
	}

	private void _deleteEntries(
		ActionRequest actionRequest, boolean moveToTrash) {

		ReflectionTestUtil.invoke(
			_serviceTracker.getService(), "deleteEntries",
			new Class<?>[] {ActionRequest.class, boolean.class}, actionRequest,
			moveToTrash);
	}

	private MockHttpServletRequest _getMockHttpServletRequest()
		throws PortalException {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		return mockHttpServletRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws PortalException {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setScopeGroupId(_layout.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private static ServiceTracker<MVCActionCommand, MVCActionCommand>
		_serviceTracker;

	@Inject
	private BlogsEntryService _blogsEntryService;

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;
	private ServiceContext _serviceContext;

	@Inject
	private TrashEntryLocalService _trashEntryLocalService;

	private static class MockActionRequest
		extends MockLiferayPortletRequest implements ActionRequest {

		public MockActionRequest(
			HttpServletRequest httpServletRequest, long entryId) {

			this.httpServletRequest = httpServletRequest;
			this.entryId = entryId;
		}

		@Override
		public Object getAttribute(String name) {
			if (Objects.equals(name, JavaConstants.JAVAX_PORTLET_CONFIG)) {
				return new MockLiferayPortletConfig();
			}

			return super.getAttribute(name);
		}

		@Override
		public HttpServletRequest getHttpServletRequest() {
			return httpServletRequest;
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			Map<String, String[]> parameters = new HashMap<>();

			parameters.put("entryId", new String[] {String.valueOf(entryId)});

			return parameters;
		}

		@Override
		public void setPortletRequestDispatcherRequest(
			HttpServletRequest httpServletRequest) {
		}

	}

}