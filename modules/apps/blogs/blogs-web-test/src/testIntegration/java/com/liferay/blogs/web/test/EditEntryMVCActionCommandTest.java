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
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.service.TrashEntryLocalService;

import java.util.List;

import javax.portlet.ActionRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());
	}

	@Test(expected = NoSuchEntryException.class)
	public void testDeleteEntries() throws PortalException {
		BlogsEntry blogsEntry = _addBlogEntry(RandomTestUtil.randomString());

		_deleteEntries(
			_getMockLiferayPortletActionRequest(blogsEntry.getEntryId()),
			false);

		_blogsEntryService.getEntry(blogsEntry.getEntryId());
	}

	@Test
	public void testDeleteEntriesToTrash() throws PortalException {
		BlogsEntry blogsEntry = _addBlogEntry(RandomTestUtil.randomString());

		_deleteEntries(
			_getMockLiferayPortletActionRequest(blogsEntry.getEntryId()), true);

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
			_mvcActionCommand, "_deleteEntries",
			new Class<?>[] {ActionRequest.class, boolean.class}, actionRequest,
			moveToTrash);
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
		long entryId) {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.addParameter(
			"entryId", new String[] {String.valueOf(entryId)});

		return mockLiferayPortletActionRequest;
	}

	@Inject
	private BlogsEntryService _blogsEntryService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "component.name=com.liferay.blogs.web.internal.portlet.action.EditEntryMVCActionCommand"
	)
	private MVCActionCommand _mvcActionCommand;

	private ServiceContext _serviceContext;

	@Inject
	private TrashEntryLocalService _trashEntryLocalService;

}