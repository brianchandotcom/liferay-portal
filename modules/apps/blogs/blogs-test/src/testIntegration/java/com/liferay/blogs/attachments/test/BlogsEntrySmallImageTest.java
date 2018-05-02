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

package com.liferay.blogs.attachments.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class BlogsEntrySmallImageTest extends BaseBlogsEntryImageTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();
		user = TestPropsValues.getUser();

		ServiceTestUtil.setUser(TestPropsValues.getUser());
	}

	@Override
	protected BlogsEntry addBlogsEntry(ImageSelector imageSelector)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());

		return BlogsEntryLocalServiceUtil.addEntry(
			user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new Date(), true, true,
			new String[0], StringPool.BLANK, null, imageSelector,
			serviceContext);
	}

	@Override
	protected BlogsEntry addBlogsEntry(String imageTitle) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());

		FileEntry fileEntry = getTempFileEntry(
			user.getUserId(), imageTitle, serviceContext);

		ImageSelector imageSelector = new ImageSelector(
			FileUtil.getBytes(fileEntry.getContentStream()),
			fileEntry.getTitle(), fileEntry.getMimeType(), StringPool.BLANK);

		return addBlogsEntry(imageSelector);
	}

	@Override
	protected void addImage(long entryId, ImageSelector imageSelector)
		throws Exception {

		BlogsEntryLocalServiceUtil.addSmallImage(entryId, imageSelector);
	}

	@Override
	protected long getImageFileEntryId(BlogsEntry blogsEntry) {
		return blogsEntry.getSmallImageFileEntryId();
	}

	@Override
	protected BlogsEntry updateBlogsEntry(
			long blogsEntryId, ImageSelector imageSelector)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());

		return BlogsEntryLocalServiceUtil.updateEntry(
			user.getUserId(), blogsEntryId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new Date(), true, true,
			new String[0], StringPool.BLANK, null, imageSelector,
			serviceContext);
	}

	@Override
	protected BlogsEntry updateBlogsEntry(
			long blogsEntryId, String coverImageTitle)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());

		FileEntry fileEntry = getTempFileEntry(
			user.getUserId(), coverImageTitle, serviceContext);

		ImageSelector imageSelector = new ImageSelector(
			FileUtil.getBytes(fileEntry.getContentStream()),
			fileEntry.getTitle(), fileEntry.getMimeType(), StringPool.BLANK);

		return updateBlogsEntry(blogsEntryId, imageSelector);
	}

}