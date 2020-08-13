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

package com.liferay.style.book.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockActionResponse;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class DeleteStyleBookEntryMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = new ServiceContext();

		_serviceContext.setScopeGroupId(_group.getGroupId());
		_serviceContext.setUserId(TestPropsValues.getUserId());
	}

	@Test
	public void testDeleteStyleBookEntries() throws Exception {
		StyleBookEntry styleBookEntry1 =
			_styleBookEntryLocalService.addStyleBookEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				_serviceContext);

		StyleBookEntry styleBookEntry2 =
			_styleBookEntryLocalService.addStyleBookEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				_serviceContext);

		MockLiferayPortletActionRequest actionRequest =
			new MockLiferayPortletActionRequest();

		actionRequest.addParameter(
			"rowIds",
			new String[] {
				String.valueOf(styleBookEntry1.getStyleBookEntryId()),
				String.valueOf(styleBookEntry2.getStyleBookEntryId())
			});

		_deleteStyleBookEntryMVCActionCommandTest.processAction(
			actionRequest, new MockActionResponse());

		Assert.assertEquals(
			0,
			_styleBookEntryLocalService.getStyleBookEntriesCount(
				_group.getGroupId()));
	}

	@Test
	public void testDeleteStyleBookEntry() throws Exception {
		String name = RandomTestUtil.randomString();

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				TestPropsValues.getUserId(), _group.getGroupId(), name,
				StringPool.BLANK, _serviceContext);

		MockLiferayPortletActionRequest actionRequest =
			new MockLiferayPortletActionRequest();

		actionRequest.addParameter(
			"styleBookEntryId",
			String.valueOf(styleBookEntry.getStyleBookEntryId()));

		_deleteStyleBookEntryMVCActionCommandTest.processAction(
			actionRequest, new MockActionResponse());

		Assert.assertEquals(
			0,
			_styleBookEntryLocalService.getStyleBookEntriesCount(
				_group.getGroupId()));
		Assert.assertEquals(
			0,
			_styleBookEntryLocalService.getStyleBookEntriesCount(
				_group.getGroupId(), name));
	}

	@Test(expected = NoSuchFileEntryException.class)
	public void testDeleteStyleBookEntryWithPreviewFileEntry()
		throws Exception {

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				_serviceContext);

		FileEntry fileEntry = _addFileEntry(styleBookEntry);

		styleBookEntry = _styleBookEntryLocalService.updatePreviewFileEntryId(
			styleBookEntry.getStyleBookEntryId(), fileEntry.getFileEntryId());

		MockLiferayPortletActionRequest actionRequest =
			new MockLiferayPortletActionRequest();

		actionRequest.addParameter(
			"styleBookEntryId",
			String.valueOf(styleBookEntry.getStyleBookEntryId()));

		_deleteStyleBookEntryMVCActionCommandTest.processAction(
			actionRequest, new MockActionResponse());

		PortletFileRepositoryUtil.getPortletFileEntry(
			styleBookEntry.getPreviewFileEntryId());
	}

	private FileEntry _addFileEntry(StyleBookEntry styleBookEntry)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			_group.getGroupId(), RandomTestUtil.randomString(), serviceContext);

		Class<?> clazz = getClass();

		return PortletFileRepositoryUtil.addPortletFileEntry(
			_group.getGroupId(), TestPropsValues.getUserId(),
			StyleBookEntry.class.getName(),
			styleBookEntry.getStyleBookEntryId(), RandomTestUtil.randomString(),
			repository.getDlFolderId(),
			clazz.getResourceAsStream("dependencies/thumbnail.png"),
			RandomTestUtil.randomString(), ContentTypes.IMAGE_PNG, false);
	}

	@Inject(filter = "mvc.command.name=/style_book/delete_style_book_entry")
	private MVCActionCommand _deleteStyleBookEntryMVCActionCommandTest;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

}