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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;
import com.liferay.trash.kernel.model.TrashEntry;
import com.liferay.trash.kernel.service.TrashEntryLocalService;

import java.util.List;
import java.util.Map;

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
	}

	@Test(expected = NoSuchEntryException.class)
	public void testDeleteEntries() throws PortalException {
		BlogsEntry blogsEntry = _addBlogEntry(RandomTestUtil.randomString());

		_deleteEntries(new MockActionRequest(blogsEntry.getEntryId()), false);

		_blogsEntryService.getEntry(blogsEntry.getEntryId());
	}

	@Test
	public void testDeleteEntriesToTrash() throws PortalException {
		BlogsEntry blogsEntry = _addBlogEntry(RandomTestUtil.randomString());

		_deleteEntries(new MockActionRequest(blogsEntry.getEntryId()), true);

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
			_serviceTracker.getService(), "_deleteEntries",
			new Class<?>[] {ActionRequest.class, boolean.class}, actionRequest,
			moveToTrash);
	}

	private static ServiceTracker<MVCActionCommand, MVCActionCommand>
		_serviceTracker;

	@Inject
	private BlogsEntryService _blogsEntryService;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

	@Inject
	private TrashEntryLocalService _trashEntryLocalService;

	private static class MockActionRequest
		extends MockLiferayPortletActionRequest {

		public MockActionRequest(long entryId) {
			_entryId = entryId;
		}

		@Override
		public HttpServletRequest getHttpServletRequest() {
			return new MockHttpServletRequest();
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			return HashMapBuilder.put(
				"entryId", new String[] {String.valueOf(_entryId)}
			).build();
		}

		private final long _entryId;

	}

}