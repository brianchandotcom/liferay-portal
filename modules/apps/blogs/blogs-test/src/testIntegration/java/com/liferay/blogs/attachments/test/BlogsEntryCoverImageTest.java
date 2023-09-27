/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.attachments.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
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
public class BlogsEntryCoverImageTest extends BaseBlogsEntryImageTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();
		user = TestPropsValues.getUser();

		UserTestUtil.setUser(TestPropsValues.getUser());
	}

	@Override
	protected BlogsEntry addBlogsEntry(ImageSelector imageSelector)
		throws Exception {

		return BlogsEntryLocalServiceUtil.addEntry(
			user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new Date(), true, true,
			new String[0], StringPool.BLANK, imageSelector, null,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId()));
	}

	@Override
	protected BlogsEntry addBlogsEntry(String imageTitle) throws Exception {
		FileEntry fileEntry = getTempFileEntry(
			user.getUserId(), imageTitle,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId()));

		ImageSelector imageSelector = new ImageSelector(
			FileUtil.getBytes(fileEntry.getContentStream()),
			fileEntry.getTitle(), fileEntry.getMimeType(), IMAGE_CROP_REGION);

		return addBlogsEntry(imageSelector);
	}

	@Override
	protected void addImage(long entryId, ImageSelector imageSelector)
		throws Exception {

		BlogsEntryLocalServiceUtil.addCoverImage(entryId, imageSelector);
	}

	@Override
	protected long getImageFileEntryId(BlogsEntry blogsEntry) {
		return blogsEntry.getCoverImageFileEntryId();
	}

	@Override
	protected BlogsEntry updateBlogsEntry(
			long blogsEntryId, ImageSelector imageSelector)
		throws Exception {

		return BlogsEntryLocalServiceUtil.updateEntry(
			user.getUserId(), blogsEntryId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new Date(), true, true,
			new String[0], StringPool.BLANK, imageSelector, null,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId()));
	}

	@Override
	protected BlogsEntry updateBlogsEntry(long blogsEntryId, String imageTitle)
		throws Exception {

		FileEntry fileEntry = getTempFileEntry(
			user.getUserId(), imageTitle,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId()));

		ImageSelector imageSelector = new ImageSelector(
			FileUtil.getBytes(fileEntry.getContentStream()),
			fileEntry.getTitle(), fileEntry.getMimeType(), IMAGE_CROP_REGION);

		return updateBlogsEntry(blogsEntryId, imageSelector);
	}

}