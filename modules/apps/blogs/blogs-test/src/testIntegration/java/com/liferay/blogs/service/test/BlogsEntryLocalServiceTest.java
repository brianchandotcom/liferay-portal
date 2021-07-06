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

package com.liferay.blogs.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.attachments.test.BlogsEntryAttachmentFileEntryHelperTest;
import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.blogs.exception.DuplicateEntryExternalReferenceCodeException;
import com.liferay.blogs.exception.EntryContentException;
import com.liferay.blogs.exception.EntrySmallImageNameException;
import com.liferay.blogs.exception.EntryTitleException;
import com.liferay.blogs.exception.EntryUrlTitleException;
import com.liferay.blogs.exception.NoSuchEntryException;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.blogs.test.util.BlogsTestUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBMessageDisplay;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBMessageLocalServiceUtil;
import com.liferay.message.boards.test.util.MBTestUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.IdentityServiceContextFunction;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.mail.MailServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.subscription.service.SubscriptionLocalServiceUtil;

import java.io.InputStream;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Cristina González
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class BlogsEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			BlogsEntryAttachmentFileEntryHelperTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		for (Bundle installedBundle : bundleContext.getBundles()) {
			String symbolicName = installedBundle.getSymbolicName();

			if (symbolicName.equals("com.liferay.blogs.web")) {
				bundle = installedBundle;

				break;
			}
		}

		Class<?> clazz = bundle.loadClass(
			"com.liferay.blogs.web.internal.util.BlogsUtil");

		_getUrlTitleMethod = clazz.getMethod(
			"getUrlTitle", Long.TYPE, String.class);
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = TestPropsValues.getUser();

		UserTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test(expected = DuplicateEntryExternalReferenceCodeException.class)
	public void testAddBlogsEntryWithExistingExternalReferenceCode()
		throws Exception {

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), StringUtil.randomString(),
			StringUtil.randomString(), new Date(),
			ServiceContextTestUtil.getServiceContext());

		BlogsEntryLocalServiceUtil.addEntry(
			blogsEntry.getExternalReferenceCode(), blogsEntry.getUserId(),
			blogsEntry.getTitle(), blogsEntry.getSubtitle(),
			blogsEntry.getUrlTitle(), blogsEntry.getDescription(),
			blogsEntry.getContent(), blogsEntry.getDisplayDate(),
			blogsEntry.isAllowPingbacks(), blogsEntry.isAllowTrackbacks(),
			new String[] {blogsEntry.getTrackbacks()},
			blogsEntry.getCoverImageCaption(), null, null,
			ServiceContextTestUtil.getServiceContext());
	}

	@Test
	public void testAddBlogsEntryWithExternalReferenceCode() throws Exception {
		String externalReferenceCode = RandomTestUtil.randomString();

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			externalReferenceCode, TestPropsValues.getUserId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.nextDate(), false,
			false, new String[0], RandomTestUtil.randomString(), null, null,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			externalReferenceCode, blogsEntry.getExternalReferenceCode());
	}

	@Test
	public void testAddBlogsEntryWithoutExternalReferenceCode()
		throws Exception {

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			null, TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.nextDate(), false, false, new String[0],
			RandomTestUtil.randomString(), null, null,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			blogsEntry.getExternalReferenceCode(),
			String.valueOf(blogsEntry.getEntryId()));
	}

	@Test
	public void testAddCoverImageWithCoverImageURL() throws Exception {
		BlogsEntry entry = addEntry(false);

		String coverImageURL = StringUtil.randomString();

		BlogsEntryLocalServiceUtil.addCoverImage(
			entry.getEntryId(), new ImageSelector(coverImageURL));

		BlogsEntry updatedEntry = BlogsEntryLocalServiceUtil.getEntry(
			entry.getEntryId());

		Assert.assertEquals(0, updatedEntry.getCoverImageFileEntryId());
		Assert.assertEquals(coverImageURL, updatedEntry.getCoverImageURL());
		Assert.assertFalse(updatedEntry.isSmallImage());
	}

	@Test
	public void testAddCoverImageWithImageBytes() throws Exception {
		BlogsEntry entry = addEntry(false);

		byte[] bytes = FileUtil.getBytes(
			new UnsyncByteArrayInputStream(TestDataConstants.TEST_BYTE_ARRAY));

		BlogsEntryLocalServiceUtil.addCoverImage(
			entry.getEntryId(),
			new ImageSelector(
				bytes, StringUtil.randomString() + ".bin",
				ContentTypes.APPLICATION_OCTET_STREAM, StringPool.BLANK));

		BlogsEntry updatedEntry = BlogsEntryLocalServiceUtil.getEntry(
			entry.getEntryId());

		Assert.assertNotEquals(0, updatedEntry.getCoverImageFileEntryId());
		Assert.assertEquals(StringPool.BLANK, updatedEntry.getCoverImageURL());
		Assert.assertFalse(updatedEntry.isSmallImage());
	}

	@Test
	public void testAddCoverImageWithURL() throws Exception {
		BlogsEntry entry = addEntry(false);

		String imageURL = StringUtil.randomString();

		BlogsEntryLocalServiceUtil.addCoverImage(
			entry.getEntryId(), new ImageSelector(imageURL));

		BlogsEntry updatedEntry = BlogsEntryLocalServiceUtil.getEntry(
			entry.getEntryId());

		Assert.assertEquals(0, updatedEntry.getCoverImageFileEntryId());
		Assert.assertEquals(imageURL, updatedEntry.getCoverImageURL());
	}

	@Test
	public void testAddDiscussion() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), StringUtil.randomString(),
			StringUtil.randomString(), new Date(), serviceContext);

		_blogsEntries.add(blogsEntry);

		long initialCommentsCount = CommentManagerUtil.getCommentsCount(
			BlogsEntry.class.getName(), blogsEntry.getEntryId());

		CommentManagerUtil.addComment(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			BlogsEntry.class.getName(), blogsEntry.getEntryId(),
			StringUtil.randomString(),
			new IdentityServiceContextFunction(serviceContext));

		Assert.assertEquals(
			initialCommentsCount + 1,
			CommentManagerUtil.getCommentsCount(
				BlogsEntry.class.getName(), blogsEntry.getEntryId()));
	}

	@Test
	public void testAddDraftEntryWithBlankTitle() throws Exception {
		int initialCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
			_group.getGroupId(), _statusAnyQueryDefinition);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), StringPool.BLANK, RandomTestUtil.randomString(),
			serviceContext);

		int actualCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
			_group.getGroupId(), _statusAnyQueryDefinition);

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	@Test
	public void testAddDraftEntryWithDuplicateURLTitle() throws Exception {
		String urlTitle = RandomTestUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntryLocalServiceUtil.addEntry(
			RandomTestUtil.randomString(), _user.getUserId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			urlTitle, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new Date(), false, false, null, null,
			null, null, serviceContext);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			RandomTestUtil.randomString(), _user.getUserId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			urlTitle, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new Date(), false, false, null, null,
			null, null, serviceContext);

		Assert.assertNotEquals(urlTitle, entry.getUrlTitle());
	}

	@Test
	public void testAddDraftEntryWithNullTitle() throws Exception {
		int initialCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
			_group.getGroupId(), _statusAnyQueryDefinition);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), null, RandomTestUtil.randomString(),
			serviceContext);

		int actualCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
			_group.getGroupId(), _statusAnyQueryDefinition);

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	@Test
	public void testAddDuplicateAttachmentFileEntry() throws Exception {
		BlogsEntry entry = addEntry(false);

		String fileName = StringUtil.randomString();

		FileEntry fileEntry1 =
			BlogsEntryLocalServiceUtil.addAttachmentFileEntry(
				entry, entry.getUserId(), fileName,
				ContentTypes.APPLICATION_OCTET_STREAM,
				new UnsyncByteArrayInputStream(new byte[0]));

		FileEntry fileEntry2 =
			BlogsEntryLocalServiceUtil.addAttachmentFileEntry(
				entry, entry.getUserId(), fileName,
				ContentTypes.APPLICATION_OCTET_STREAM,
				new UnsyncByteArrayInputStream(new byte[0]));

		Assert.assertNotEquals(
			fileEntry1.getFileName(), fileEntry2.getFileName());

		Assert.assertEquals(
			2,
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				fileEntry2.getGroupId(), fileEntry2.getFolderId()));
	}

	@Test
	public void testAddEmptyCoverImage() throws Exception {
		BlogsEntry entry = addEntry(false);

		BlogsEntryLocalServiceUtil.addCoverImage(
			entry.getEntryId(), new ImageSelector());

		BlogsEntry updatedEntry = BlogsEntryLocalServiceUtil.getEntry(
			entry.getEntryId());

		Assert.assertEquals(0, updatedEntry.getCoverImageFileEntryId());
		Assert.assertEquals(StringPool.BLANK, updatedEntry.getCoverImageURL());
	}

	@Test
	public void testAddEmptyOriginalImageFileEntry() throws Exception {
		BlogsEntry entry = addEntry(false);

		Assert.assertEquals(
			0,
			BlogsEntryLocalServiceUtil.addOriginalImageFileEntry(
				entry.getUserId(), entry.getGroupId(), entry.getEntryId(),
				new ImageSelector()));
	}

	@Test
	public void testAddEmptySmallImage() throws Exception {
		BlogsEntry entry = addEntry(false);

		BlogsEntryLocalServiceUtil.addSmallImage(
			entry.getEntryId(), new ImageSelector());

		BlogsEntry updatedEntry = BlogsEntryLocalServiceUtil.getEntry(
			entry.getEntryId());

		Assert.assertEquals(0, updatedEntry.getSmallImageFileEntryId());
		Assert.assertEquals(StringPool.BLANK, updatedEntry.getCoverImageURL());
		Assert.assertFalse(updatedEntry.isSmallImage());
	}

	@Test
	public void testAddEntry() throws Exception {
		int initialCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
			_group.getGroupId(), _statusApprovedQueryDefinition);

		addEntry(false);

		int actualCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
			_group.getGroupId(), _statusApprovedQueryDefinition);

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	@Test
	public void testAddEntrySubscribesCreatorWhenSubscribeBlogsEntryCreatorToCommentsEnabled()
		throws Exception {

		_creatorUser = UserTestUtil.addUser();

		_withSubscribeBlogsEntryCreatorToCommentsEnabled(
			() -> {
				ServiceContext serviceContext =
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId(), _creatorUser.getUserId());

				BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
					_creatorUser.getUserId(), RandomTestUtil.randomString(),
					RandomTestUtil.randomString(), serviceContext);

				_addMBMessage(
					TestPropsValues.getUserId(), serviceContext, blogsEntry);

				Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
			});
	}

	@Test(expected = EntryUrlTitleException.class)
	public void testAddEntryWithInvalidURLTitle() throws Exception {
		BlogsEntryLocalServiceUtil.addEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(256), StringUtil.randomString(),
			StringUtil.randomString(), new Date(), true, true, new String[0],
			null, null, null, new ServiceContext());
	}

	@Test
	public void testAddEntryWithNoImages() throws Exception {
		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), new Date(), true, true, new String[0],
			null, new ImageSelector(), new ImageSelector(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(0, entry.getCoverImageFileEntryId());
		Assert.assertEquals(StringPool.BLANK, entry.getCoverImageURL());
		Assert.assertEquals(0, entry.getSmallImageFileEntryId());
		Assert.assertEquals(StringPool.BLANK, entry.getSmallImageURL());
		Assert.assertFalse(entry.isSmallImage());
	}

	@Test
	public void testAddEntryWithURLTitle() throws Exception {
		String urlTitle = StringUtil.toLowerCase(StringUtil.randomString());

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			StringUtil.randomString(), StringUtil.randomString(), urlTitle,
			StringUtil.randomString(), StringUtil.randomString(), new Date(),
			true, true, new String[0], null, null, null,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(urlTitle, entry.getUrlTitle());
	}

	@Test(expected = EntryContentException.class)
	public void testAddEntryWithVeryLongContent() throws Exception {
		int maxLength = ModelHintsUtil.getMaxLength(
			BlogsEntry.class.getName(), "content");

		String content = _repeat("0", maxLength + 1);

		BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), RandomTestUtil.randomString(), content,
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));
	}

	@Test(expected = EntryTitleException.class)
	public void testAddEntryWithVeryLongTitle() throws Exception {
		int maxLength = ModelHintsUtil.getMaxLength(
			BlogsEntry.class.getName(), "title");

		String title = _repeat("0", maxLength + 1);

		BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), title, RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));
	}

	@Test
	public void testAddNullCoverImage() throws Exception {
		BlogsEntry entry = addEntry(false);

		BlogsEntryLocalServiceUtil.addCoverImage(entry.getEntryId(), null);

		BlogsEntry updatedEntry = BlogsEntryLocalServiceUtil.getEntry(
			entry.getEntryId());

		Assert.assertEquals(0, updatedEntry.getCoverImageFileEntryId());
		Assert.assertEquals(StringPool.BLANK, updatedEntry.getCoverImageURL());
		Assert.assertFalse(updatedEntry.isSmallImage());
	}

	@Test
	public void testAddNullSmallImage() throws Exception {
		BlogsEntry entry = addEntry(false);

		BlogsEntryLocalServiceUtil.addSmallImage(entry.getEntryId(), null);

		BlogsEntry updatedEntry = BlogsEntryLocalServiceUtil.getEntry(
			entry.getEntryId());

		Assert.assertEquals(0, updatedEntry.getSmallImageFileEntryId());
		Assert.assertEquals(StringPool.BLANK, updatedEntry.getCoverImageURL());
		Assert.assertFalse(updatedEntry.isSmallImage());
	}

	@Test
	public void testAddOriginalImageInVisibleImageFolder() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		FileEntry tempFileEntry = getTempFileEntry(
			_user.getUserId(), _group.getGroupId(), "image.jpg");

		ImageSelector imageSelector = new ImageSelector(
			FileUtil.getBytes(tempFileEntry.getContentStream()),
			tempFileEntry.getTitle(), tempFileEntry.getMimeType(),
			StringPool.BLANK);

		long originalImageFileEntryId =
			BlogsEntryLocalServiceUtil.addOriginalImageFileEntry(
				_user.getUserId(), _group.getGroupId(), blogsEntry.getEntryId(),
				imageSelector);

		FileEntry portletFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				originalImageFileEntryId);

		DLFileEntry dlFileEntry = (DLFileEntry)portletFileEntry.getModel();

		Assert.assertEquals(StringPool.BLANK, dlFileEntry.getClassName());
		Assert.assertEquals(0, dlFileEntry.getClassPK());

		Folder folder = portletFileEntry.getFolder();

		Assert.assertEquals(BlogsConstants.SERVICE_NAME, folder.getName());
	}

	@Test(expected = EntrySmallImageNameException.class)
	public void testAddSmallImageWithNotSupportedExtension() throws Exception {
		BlogsEntry entry = addEntry(false);

		FileEntry fileEntry = getTempFileEntry(
			_user.getUserId(), _group.getGroupId(), "image1.svg");

		ImageSelector imageSelector = new ImageSelector(
			FileUtil.getBytes(fileEntry.getContentStream()),
			fileEntry.getTitle(), fileEntry.getMimeType(), StringPool.BLANK);

		BlogsEntryLocalServiceUtil.addSmallImage(
			entry.getEntryId(), imageSelector);
	}

	@Test
	public void testAddSmallImageWithSmallImageURL() throws Exception {
		BlogsEntry entry = addEntry(false);

		String imageURL = StringUtil.randomString();

		BlogsEntryLocalServiceUtil.addSmallImage(
			entry.getEntryId(), new ImageSelector(imageURL));

		BlogsEntry updatedEntry = BlogsEntryLocalServiceUtil.getEntry(
			entry.getEntryId());

		Assert.assertEquals(0, updatedEntry.getSmallImageFileEntryId());
		Assert.assertEquals(imageURL, updatedEntry.getSmallImageURL());
		Assert.assertTrue(updatedEntry.isSmallImage());
	}

	@Test
	public void testDeleteDiscussion() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), StringUtil.randomString(),
			StringUtil.randomString(), new Date(), serviceContext);

		_blogsEntries.add(blogsEntry);

		CommentManagerUtil.addComment(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			BlogsEntry.class.getName(), blogsEntry.getEntryId(),
			StringUtil.randomString(),
			new IdentityServiceContextFunction(serviceContext));

		Assert.assertTrue(
			CommentManagerUtil.hasDiscussion(
				BlogsEntry.class.getName(), blogsEntry.getEntryId()));

		CommentManagerUtil.deleteDiscussion(
			BlogsEntry.class.getName(), blogsEntry.getEntryId());

		Assert.assertFalse(
			CommentManagerUtil.hasDiscussion(
				BlogsEntry.class.getName(), blogsEntry.getEntryId()));
	}

	@Test(expected = NoSuchEntryException.class)
	public void testDeleteEntry() throws Exception {
		BlogsEntry entry = addEntry(false);

		BlogsEntryLocalServiceUtil.deleteEntry(entry);

		BlogsEntryLocalServiceUtil.getEntry(entry.getEntryId());
	}

	@Test
	public void testFetchNotNullAttachmentsFolder() throws Exception {
		BlogsEntry entry = addEntry(false);

		byte[] bytes = null;

		try (InputStream inputStream = new UnsyncByteArrayInputStream(
				TestDataConstants.TEST_BYTE_ARRAY)) {

			bytes = FileUtil.getBytes(inputStream);
		}

		BlogsEntryLocalServiceUtil.addOriginalImageFileEntry(
			entry.getUserId(), entry.getGroupId(), entry.getEntryId(),
			new ImageSelector(
				bytes, StringUtil.randomString() + ".bin",
				ContentTypes.APPLICATION_OCTET_STREAM, StringPool.BLANK));

		Assert.assertNotNull(
			BlogsEntryLocalServiceUtil.fetchAttachmentsFolder(
				entry.getUserId(), entry.getGroupId()));
	}

	@Test
	public void testFetchNullAttachmentsFolder() throws Exception {
		Assert.assertNull(
			BlogsEntryLocalServiceUtil.fetchAttachmentsFolder(
				TestPropsValues.getUserId(), TestPropsValues.getGroupId()));
	}

	@Test
	public void testGetCompanyEntriesCountInTrash() throws Exception {
		testGetCompanyEntriesCount(true);
	}

	@Test
	public void testGetCompanyEntriesCountNotInTrash() throws Exception {
		testGetCompanyEntriesCount(false);
	}

	@Test
	public void testGetCompanyEntriesInTrash() throws Exception {
		testGetCompanyEntries(true);
	}

	@Test
	public void testGetCompanyEntriesNotInTrash() throws Exception {
		testGetCompanyEntries(false);
	}

	@Test
	public void testGetDiscussionMessageDisplay() throws Exception {
		BlogsEntry entry = addEntry(false);

		MBMessageLocalServiceUtil.getDiscussionMessageDisplay(
			_user.getUserId(), _group.getGroupId(), BlogsEntry.class.getName(),
			entry.getEntryId(), WorkflowConstants.STATUS_ANY);
	}

	@Test
	public void testGetEntriesPrevAndNextByDisplayDate() throws Exception {
		BlogsEntry firstEntry = addEntry(false, 1);

		BlogsEntry thirdEntry = addEntry(false, 3);

		BlogsEntry secondEntry = addEntry(false, 2);

		BlogsEntry[] entries = BlogsEntryLocalServiceUtil.getEntriesPrevAndNext(
			secondEntry.getEntryId());

		Assert.assertNotNull(
			StringBundler.concat(
				"The previous entry relative to entry ",
				secondEntry.getEntryId(), " should be ",
				firstEntry.getEntryId(), " but is null"),
			entries[0]);
		Assert.assertNotNull(
			StringBundler.concat(
				"The current entry relative to entry ",
				secondEntry.getEntryId(), " should be ",
				secondEntry.getEntryId(), " but is null"),
			entries[1]);
		Assert.assertNotNull(
			StringBundler.concat(
				"The next entry relative to entry ", secondEntry.getEntryId(),
				" should be ", thirdEntry.getEntryId(), " but is null"),
			entries[2]);
		Assert.assertEquals(
			StringBundler.concat(
				"The previous entry relative to entry ",
				secondEntry.getEntryId(), " should be ",
				firstEntry.getEntryId()),
			entries[0].getEntryId(), firstEntry.getEntryId());
		Assert.assertEquals(
			StringBundler.concat(
				"The current entry relative to entry ",
				secondEntry.getEntryId(), " should be ",
				secondEntry.getEntryId()),
			entries[1].getEntryId(), secondEntry.getEntryId());
		Assert.assertEquals(
			StringBundler.concat(
				"The next entry relative to entry ", secondEntry.getEntryId(),
				" should be ", thirdEntry.getEntryId()),
			entries[2].getEntryId(), thirdEntry.getEntryId());
	}

	@Test
	public void testGetEntriesPrevAndNextRelativeToCurrentEntry()
		throws Exception {

		BlogsEntry previousEntry = addEntry(false);

		BlogsEntry currentEntry = addEntry(false);

		BlogsEntry nextEntry = addEntry(false);

		BlogsEntry[] entries = BlogsEntryLocalServiceUtil.getEntriesPrevAndNext(
			currentEntry.getEntryId());

		Assert.assertNotNull(
			StringBundler.concat(
				"The previous entry relative to entry ",
				currentEntry.getEntryId(), " should be ",
				previousEntry.getEntryId(), " but is null"),
			entries[0]);
		Assert.assertNotNull(
			StringBundler.concat(
				"The current entry relative to entry ",
				currentEntry.getEntryId(), " should be ",
				currentEntry.getEntryId(), " but is null"),
			entries[1]);
		Assert.assertNotNull(
			StringBundler.concat(
				"The next entry relative to entry ", currentEntry.getEntryId(),
				" should be ", nextEntry.getEntryId(), " but is null"),
			entries[2]);
		Assert.assertEquals(
			StringBundler.concat(
				"The previous entry relative to entry",
				currentEntry.getEntryId(), " should be ",
				previousEntry.getEntryId()),
			entries[0].getEntryId(), previousEntry.getEntryId());
		Assert.assertEquals(
			StringBundler.concat(
				"The current entry relative to entry ",
				currentEntry.getEntryId(), " should be ",
				currentEntry.getEntryId()),
			entries[1].getEntryId(), currentEntry.getEntryId());
		Assert.assertEquals(
			StringBundler.concat(
				"The next entry relative to entry ", currentEntry.getEntryId(),
				" should be ", nextEntry.getEntryId()),
			entries[2].getEntryId(), nextEntry.getEntryId());
	}

	@Test
	public void testGetEntriesPrevAndNextRelativeToNextEntry()
		throws Exception {

		addEntry(false);

		BlogsEntry currentEntry = addEntry(false);

		BlogsEntry nextEntry = addEntry(false);

		BlogsEntry[] entries = BlogsEntryLocalServiceUtil.getEntriesPrevAndNext(
			nextEntry.getEntryId());

		Assert.assertNull(
			"The next entry relative to entry " + nextEntry.getEntryId() +
				" should be null",
			entries[2]);
		Assert.assertNotNull(
			StringBundler.concat(
				"The current entry relative to entry ", nextEntry.getEntryId(),
				" should be ", nextEntry.getEntryId(), " but is null"),
			entries[1]);
		Assert.assertNotNull(
			StringBundler.concat(
				"The previous entry relative to entry ", nextEntry.getEntryId(),
				" should be ", currentEntry.getEntryId(), " but is null"),
			entries[0]);
		Assert.assertEquals(
			StringBundler.concat(
				"The previous entry relative to entry ", nextEntry.getEntryId(),
				" should be ", currentEntry.getEntryId()),
			entries[0].getEntryId(), currentEntry.getEntryId());
		Assert.assertEquals(
			StringBundler.concat(
				"The current entry relative to entry", nextEntry.getEntryId(),
				" should be ", nextEntry.getEntryId()),
			entries[1].getEntryId(), nextEntry.getEntryId());
	}

	@Test
	public void testGetEntriesPrevAndNextRelativeToPreviousEntry()
		throws Exception {

		BlogsEntry previousEntry = addEntry(false);

		BlogsEntry currentEntry = addEntry(false);

		addEntry(false);

		BlogsEntry[] entries = BlogsEntryLocalServiceUtil.getEntriesPrevAndNext(
			previousEntry.getEntryId());

		Assert.assertNull(
			"The previous entry relative to entry " +
				previousEntry.getEntryId() + " should be null",
			entries[0]);
		Assert.assertNotNull(
			StringBundler.concat(
				"The current entry relative to entry ",
				previousEntry.getEntryId(), " should be ",
				previousEntry.getEntryId(), " but is null"),
			entries[1]);
		Assert.assertNotNull(
			StringBundler.concat(
				"The next entry relative to entry ", previousEntry.getEntryId(),
				" should be ", currentEntry.getEntryId(), " but is null"),
			entries[2]);
		Assert.assertEquals(
			StringBundler.concat(
				"The current entry relative to entry ",
				previousEntry.getEntryId(), " should be ",
				previousEntry.getEntryId()),
			entries[1].getEntryId(), previousEntry.getEntryId());
		Assert.assertEquals(
			StringBundler.concat(
				"The next entry relative to entry ", previousEntry.getEntryId(),
				" should be ", currentEntry.getEntryId()),
			entries[2].getEntryId(), currentEntry.getEntryId());
	}

	@Test
	public void testGetEntryByGroupAndOldUrlTitle() throws Exception {
		BlogsEntry expectedEntry = addEntry(false);

		String oldUrlTitle = expectedEntry.getUrlTitle();

		String urlTitle = "new-friendly-url";

		BlogsEntryLocalServiceUtil.updateEntry(
			expectedEntry.getUserId(), expectedEntry.getEntryId(),
			expectedEntry.getTitle(), expectedEntry.getSubtitle(), urlTitle,
			expectedEntry.getDescription(), expectedEntry.getContent(),
			expectedEntry.getDisplayDate(), expectedEntry.isAllowPingbacks(),
			expectedEntry.isAllowTrackbacks(), new String[0],
			expectedEntry.getCoverImageCaption(), null, null,
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));

		BlogsEntry actualEntry = BlogsEntryLocalServiceUtil.getEntry(
			expectedEntry.getGroupId(), oldUrlTitle);

		BlogsTestUtil.assertEquals(expectedEntry, actualEntry);

		actualEntry = BlogsEntryLocalServiceUtil.getEntry(
			expectedEntry.getGroupId(), urlTitle);

		BlogsTestUtil.assertEquals(expectedEntry, actualEntry);
	}

	@Test
	public void testGetEntryByGroupAndUrlTitle() throws Exception {
		BlogsEntry expectedEntry = addEntry(false);

		BlogsEntry actualEntry = BlogsEntryLocalServiceUtil.getEntry(
			expectedEntry.getGroupId(), expectedEntry.getUrlTitle());

		BlogsTestUtil.assertEquals(expectedEntry, actualEntry);
	}

	@Test
	public void testGetGroupEntriesCountInTrashWithDisplayDate()
		throws Exception {

		testGetGroupEntriesCount(true, true);
	}

	@Test
	public void testGetGroupEntriesCountInTrashWithoutDisplayDate()
		throws Exception {

		testGetGroupEntriesCount(true, false);
	}

	@Test
	public void testGetGroupEntriesCountNotInTrashWithDisplayDate()
		throws Exception {

		testGetGroupEntriesCount(false, true);
	}

	@Test
	public void testGetGroupEntriesCountNotInTrashWithoutDisplayDate()
		throws Exception {

		testGetGroupEntriesCount(false, false);
	}

	@Test
	public void testGetGroupEntriesInTrashWithDisplayDate() throws Exception {
		testGetGroupEntries(true, true);
	}

	@Test
	public void testGetGroupEntriesInTrashWithoutDisplayDate()
		throws Exception {

		testGetGroupEntries(true, false);
	}

	@Test
	public void testGetGroupEntriesNotInTrashWithDisplayDate()
		throws Exception {

		testGetGroupEntries(false, true);
	}

	@Test
	public void testGetGroupEntriesNotInTrashWithoutDisplayDate()
		throws Exception {

		testGetGroupEntries(false, false);
	}

	@Test
	public void testGetGroupsEntries() throws Exception {
		List<BlogsEntry> groupsEntries =
			BlogsEntryLocalServiceUtil.getGroupsEntries(
				_user.getCompanyId(), _group.getGroupId(), new Date(),
				_statusInTrashQueryDefinition);

		int initialCount = groupsEntries.size();

		addEntry(false);
		addEntry(true);

		List<BlogsEntry> groupsEntriesInTrash =
			BlogsEntryLocalServiceUtil.getGroupsEntries(
				_user.getCompanyId(), _group.getGroupId(), new Date(),
				_statusInTrashQueryDefinition);

		Assert.assertEquals(
			groupsEntriesInTrash.toString(), initialCount + 1,
			groupsEntriesInTrash.size());

		for (BlogsEntry groupsEntry : groupsEntriesInTrash) {
			Assert.assertEquals(
				"Entry " + groupsEntry.getEntryId() + " is not in trash",
				WorkflowConstants.STATUS_IN_TRASH, groupsEntry.getStatus());
			Assert.assertEquals(
				StringBundler.concat(
					"Entry belongs to company ", groupsEntry.getCompanyId(),
					" but should belong to company ", _user.getCompanyId()),
				_user.getCompanyId(), groupsEntry.getCompanyId());
		}
	}

	@Test
	public void testGetGroupUserEntriesCountInTrash() throws Exception {
		testGetGroupUserEntriesCount(true);
	}

	@Test
	public void testGetGroupUserEntriesCountNotInTrash() throws Exception {
		testGetGroupUserEntriesCount(false);
	}

	@Test
	public void testGetGroupUserEntriesInTrash() throws Exception {
		testGetGroupUserEntries(true);
	}

	@Test
	public void testGetGroupUserEntriesNotInTrash() throws Exception {
		testGetGroupUserEntries(false);
	}

	@Test
	public void testGetOrganizationEntriesCountInTrash() throws Exception {
		testGetOrganizationEntriesCount(true);
	}

	@Test
	public void testGetOrganizationEntriesCountNotInTrash() throws Exception {
		testGetOrganizationEntriesCount(false);
	}

	@Test
	public void testGetOrganizationEntriesInTrash() throws Exception {
		testGetOrganizationEntries(true);
	}

	@Test
	public void testGetOrganizationEntriesNotInTrash() throws Exception {
		testGetOrganizationEntries(false);
	}

	@Test(expected = EntryTitleException.class)
	public void testPublishWithBlankTitle() throws Exception {
		BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), StringPool.BLANK, RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));
	}

	@Test(expected = EntryTitleException.class)
	public void testPublishWithNullTitle() throws Exception {
		BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), null, RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));
	}

	@Test(expected = EntryTitleException.class)
	public void testPublishWithoutTitle() throws Exception {
		BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), StringPool.BLANK, RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));
	}

	@Test
	public void testSubscribe() throws Exception {
		int initialCount =
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				_user.getUserId());

		BlogsEntryLocalServiceUtil.subscribe(
			_user.getUserId(), _group.getGroupId());

		int actualCount =
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				_user.getUserId());

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	@Test
	public void testUnsubscribe() throws Exception {
		int initialCount =
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				_user.getUserId());

		BlogsEntryLocalServiceUtil.subscribe(
			_user.getUserId(), _group.getGroupId());

		BlogsEntryLocalServiceUtil.unsubscribe(
			_user.getUserId(), _group.getGroupId());

		int actualCount =
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				_user.getUserId());

		Assert.assertEquals(initialCount, actualCount);
	}

	@Test
	public void testUpdateDraftEntryWithDuplicateURLTitle() throws Exception {
		String urlTitle = RandomTestUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntryLocalServiceUtil.addEntry(
			RandomTestUtil.randomString(), _user.getUserId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			urlTitle, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new Date(), false, false, null, null,
			null, null, serviceContext);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			RandomTestUtil.randomString(), _user.getUserId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			urlTitle, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new Date(), false, false, null, null,
			null, null, serviceContext);

		entry = BlogsEntryLocalServiceUtil.updateEntry(
			_user.getUserId(), entry.getEntryId(), entry.getTitle(),
			entry.getSubtitle(), urlTitle, entry.getDescription(),
			entry.getContent(), entry.getDisplayDate(), false, false, null,
			null, null, null, serviceContext);

		Assert.assertNotEquals(urlTitle, entry.getUrlTitle());
	}

	@Test
	public void testUpdateEntryResources() throws Exception {
		BlogsEntry entry = addEntry(false);

		BlogsEntryLocalServiceUtil.updateEntryResources(
			entry, new String[] {ActionKeys.ADD_DISCUSSION}, null);
	}

	@Test
	public void testURLTitleIsNotUpdatedWhenUpdatingEntryTitle()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		String urlTitle = entry.getUrlTitle();

		entry = BlogsEntryLocalServiceUtil.updateEntry(
			_user.getUserId(), entry.getEntryId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		Assert.assertEquals(urlTitle, entry.getUrlTitle());
	}

	@Test
	public void testURLTitleIsNotUpdatedWhenUpdatingEntryTitleToDraftEntry()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		String urlTitle = entry.getUrlTitle();

		serviceContext.setWorkflowAction(WorkflowConstants.STATUS_DRAFT);

		entry = BlogsEntryLocalServiceUtil.updateEntry(
			_user.getUserId(), entry.getEntryId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		Assert.assertEquals(urlTitle, entry.getUrlTitle());
	}

	@Test
	public void testURLTitleIsSavedWhenAddingApprovedEntry() throws Exception {
		String title = RandomTestUtil.randomString();

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), title, RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));

		Assert.assertEquals(
			_getUrlTitleMethod.invoke(null, entry.getEntryId(), title),
			entry.getUrlTitle());
	}

	@Test
	public void testURLTitleIsSavedWhenAddingApprovedEntryWithWorkflow()
		throws Exception {

		String title = RandomTestUtil.randomString();

		BlogsEntry entry = BlogsTestUtil.addEntryWithWorkflow(
			_user.getUserId(), title, true,
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));

		Assert.assertEquals(
			_getUrlTitleMethod.invoke(null, entry.getEntryId(), title),
			entry.getUrlTitle());
	}

	@Test
	public void testURLTitleIsSavedWhenAddingDraftEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		serviceContext.setWorkflowAction(WorkflowConstants.STATUS_DRAFT);

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		Assert.assertTrue(Validator.isNotNull(entry.getUrlTitle()));
	}

	@Test
	public void testURLTitleIsSavedWhenAddingDraftEntryWithWorkflow()
		throws Exception {

		BlogsEntry entry = BlogsTestUtil.addEntryWithWorkflow(
			_user.getUserId(), RandomTestUtil.randomString(), false,
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));

		Assert.assertTrue(Validator.isNotNull(entry.getUrlTitle()));
	}

	protected BlogsEntry addEntry(boolean statusInTrash) throws Exception {
		return addEntry(_user.getUserId(), statusInTrash);
	}

	protected BlogsEntry addEntry(boolean statusInTrash, int date)
		throws Exception {

		return addEntry(_user.getUserId(), statusInTrash, date);
	}

	protected BlogsEntry addEntry(long userId, boolean statusInTrash)
		throws Exception {

		return addEntry(userId, statusInTrash, 1);
	}

	protected BlogsEntry addEntry(long userId, boolean statusInTrash, int date)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), userId);

		Calendar displayDateCalendar = CalendarFactoryUtil.getCalendar(
			2012, 1, date);

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			userId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), displayDateCalendar.getTime(),
			serviceContext);

		if (statusInTrash) {
			entry = BlogsEntryLocalServiceUtil.moveEntryToTrash(userId, entry);
		}

		return entry;
	}

	protected void assertBlogsEntriesStatus(
		List<BlogsEntry> entries, boolean statusInTrash) {

		for (BlogsEntry entry : entries) {
			if (statusInTrash) {
				Assert.assertEquals(
					"The entry " + entry.getEntryId() + " should be in trash",
					WorkflowConstants.STATUS_IN_TRASH, entry.getStatus());
			}
			else {
				Assert.assertNotEquals(
					"The entry " + entry.getEntryId() +
						" should not be in trash",
					WorkflowConstants.STATUS_IN_TRASH, entry.getStatus());
			}
		}
	}

	protected FileEntry getTempFileEntry(
			long userId, long groupId, String title)
		throws PortalException {

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"com/liferay/blogs/dependencies/test.jpg");

		return TempFileEntryUtil.addTempFileEntry(
			groupId, userId, BlogsEntry.class.getName(), title, inputStream,
			MimeTypesUtil.getContentType(title));
	}

	protected void testGetCompanyEntries(boolean statusInTrash)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition =
			_statusInTrashQueryDefinition;

		if (!statusInTrash) {
			queryDefinition = _statusAnyQueryDefinition;
		}

		List<BlogsEntry> initialEntries =
			BlogsEntryLocalServiceUtil.getCompanyEntries(
				_user.getCompanyId(), new Date(), queryDefinition);

		int initialCount = initialEntries.size();

		addEntry(false);
		addEntry(true);

		List<BlogsEntry> actualEntries =
			BlogsEntryLocalServiceUtil.getCompanyEntries(
				_user.getCompanyId(), new Date(), queryDefinition);

		Assert.assertEquals(
			actualEntries.toString(), initialCount + 1, actualEntries.size());

		assertBlogsEntriesStatus(actualEntries, statusInTrash);
	}

	protected void testGetCompanyEntriesCount(boolean statusInTrash)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition =
			_statusInTrashQueryDefinition;

		if (!statusInTrash) {
			queryDefinition = _statusAnyQueryDefinition;
		}

		int initialCount = BlogsEntryLocalServiceUtil.getCompanyEntriesCount(
			_user.getCompanyId(), new Date(), queryDefinition);

		addEntry(false);
		addEntry(true);

		int actualCount = BlogsEntryLocalServiceUtil.getCompanyEntriesCount(
			_user.getCompanyId(), new Date(), queryDefinition);

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	protected void testGetGroupEntries(
			boolean statusInTrash, boolean displayDate)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition =
			_statusInTrashQueryDefinition;

		if (!statusInTrash) {
			queryDefinition = _statusAnyQueryDefinition;
		}

		List<BlogsEntry> initialEntries = null;

		if (displayDate) {
			initialEntries = BlogsEntryLocalServiceUtil.getGroupEntries(
				_group.getGroupId(), new Date(), queryDefinition);
		}
		else {
			initialEntries = BlogsEntryLocalServiceUtil.getGroupEntries(
				_group.getGroupId(), queryDefinition);
		}

		int initialCount = initialEntries.size();

		addEntry(false);
		addEntry(true);

		List<BlogsEntry> actualEntries = null;

		if (displayDate) {
			actualEntries = BlogsEntryLocalServiceUtil.getGroupEntries(
				_group.getGroupId(), new Date(), queryDefinition);
		}
		else {
			actualEntries = BlogsEntryLocalServiceUtil.getGroupEntries(
				_group.getGroupId(), queryDefinition);
		}

		Assert.assertEquals(
			actualEntries.toString(), initialCount + 1, actualEntries.size());

		assertBlogsEntriesStatus(actualEntries, statusInTrash);
	}

	protected void testGetGroupEntriesCount(
			boolean statusInTrash, boolean displayDate)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition =
			_statusInTrashQueryDefinition;

		if (!statusInTrash) {
			queryDefinition = _statusAnyQueryDefinition;
		}

		int initialCount = 0;

		if (displayDate) {
			initialCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
				_group.getGroupId(), new Date(), queryDefinition);
		}
		else {
			initialCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
				_group.getGroupId(), queryDefinition);
		}

		addEntry(false);
		addEntry(true);

		int actualCount = 0;

		if (displayDate) {
			actualCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
				_group.getGroupId(), new Date(), queryDefinition);
		}
		else {
			actualCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
				_group.getGroupId(), queryDefinition);
		}

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	protected void testGetGroupUserEntries(boolean statusInTrash)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition =
			_statusInTrashQueryDefinition;

		if (!statusInTrash) {
			queryDefinition = _statusAnyQueryDefinition;
		}

		List<BlogsEntry> initialEntries =
			BlogsEntryLocalServiceUtil.getGroupUserEntries(
				_group.getGroupId(), _user.getUserId(), new Date(),
				queryDefinition);

		int initialCount = initialEntries.size();

		addEntry(false);
		addEntry(true);

		List<BlogsEntry> actualEntries =
			BlogsEntryLocalServiceUtil.getGroupUserEntries(
				_group.getGroupId(), _user.getUserId(), new Date(),
				queryDefinition);

		Assert.assertEquals(
			actualEntries.toString(), initialCount + 1, actualEntries.size());

		assertBlogsEntriesStatus(actualEntries, statusInTrash);
	}

	protected void testGetGroupUserEntriesCount(boolean statusInTrash)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition =
			_statusInTrashQueryDefinition;

		if (!statusInTrash) {
			queryDefinition = _statusAnyQueryDefinition;
		}

		int initialCount = BlogsEntryLocalServiceUtil.getGroupUserEntriesCount(
			_group.getGroupId(), _user.getUserId(), new Date(),
			queryDefinition);

		addEntry(false);
		addEntry(true);

		int actualCount = BlogsEntryLocalServiceUtil.getGroupUserEntriesCount(
			_group.getGroupId(), _user.getUserId(), new Date(),
			queryDefinition);

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	protected void testGetOrganizationEntries(boolean statusInTrash)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition =
			_statusInTrashQueryDefinition;

		if (!statusInTrash) {
			queryDefinition = _statusAnyQueryDefinition;
		}

		_organization = OrganizationTestUtil.addOrganization();

		_organizationUser = UserTestUtil.addOrganizationOwnerUser(
			_organization);

		List<BlogsEntry> initialEntries =
			BlogsEntryLocalServiceUtil.getOrganizationEntries(
				_organization.getOrganizationId(), new Date(), queryDefinition);

		int initialCount = initialEntries.size();

		addEntry(_organizationUser.getUserId(), false);
		addEntry(_organizationUser.getUserId(), true);

		List<BlogsEntry> actualEntries =
			BlogsEntryLocalServiceUtil.getOrganizationEntries(
				_organization.getOrganizationId(), new Date(), queryDefinition);

		Assert.assertEquals(
			actualEntries.toString(), initialCount + 1, actualEntries.size());

		assertBlogsEntriesStatus(actualEntries, statusInTrash);
	}

	protected void testGetOrganizationEntriesCount(boolean statusInTrash)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition =
			_statusInTrashQueryDefinition;

		if (!statusInTrash) {
			queryDefinition = _statusAnyQueryDefinition;
		}

		_organization = OrganizationTestUtil.addOrganization();

		_organizationUser = UserTestUtil.addOrganizationOwnerUser(
			_organization);

		int initialCount =
			BlogsEntryLocalServiceUtil.getOrganizationEntriesCount(
				_organization.getOrganizationId(), new Date(), queryDefinition);

		addEntry(_organizationUser.getUserId(), false);
		addEntry(_organizationUser.getUserId(), true);

		int actualCount =
			BlogsEntryLocalServiceUtil.getOrganizationEntriesCount(
				_organization.getOrganizationId(), new Date(), queryDefinition);

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	private MBMessage _addMBMessage(
			long userId, ServiceContext serviceContext, BlogsEntry entry)
		throws Exception {

		MBMessageDisplay mbMessageDisplay =
			MBMessageLocalServiceUtil.getDiscussionMessageDisplay(
				TestPropsValues.getUserId(), _group.getGroupId(),
				BlogsEntry.class.getName(), entry.getEntryId(),
				WorkflowConstants.STATUS_APPROVED);

		MBThread mbThread = mbMessageDisplay.getThread();

		MBTestUtil.populateNotificationsServiceContext(
			serviceContext, Constants.ADD);

		return MBMessageLocalServiceUtil.addDiscussionMessage(
			userId, RandomTestUtil.randomString(), _group.getGroupId(),
			BlogsEntry.class.getName(), entry.getEntryId(),
			mbThread.getThreadId(),
			MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);
	}

	private String _repeat(String string, int times) {
		StringBundler sb = new StringBundler(times);

		for (int i = 0; i < times; i++) {
			sb.append(string);
		}

		return sb.toString();
	}

	private void _withSubscribeBlogsEntryCreatorToCommentsEnabled(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		Dictionary<String, Object> dictionary =
			HashMapDictionaryBuilder.<String, Object>put(
				"subscribeBlogsEntryCreatorToComments", true
			).build();

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.blogs.configuration." +
						"BlogsGroupServiceConfiguration",
					dictionary)) {

			unsafeRunnable.run();
		}
	}

	private static Method _getUrlTitleMethod;

	@DeleteAfterTestRun
	private final List<BlogsEntry> _blogsEntries = new ArrayList<>();

	@DeleteAfterTestRun
	private User _creatorUser;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Organization _organization;

	@DeleteAfterTestRun
	private User _organizationUser;

	private final QueryDefinition<BlogsEntry> _statusAnyQueryDefinition =
		new QueryDefinition<>(
			WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	private final QueryDefinition<BlogsEntry> _statusApprovedQueryDefinition =
		new QueryDefinition<>(
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	private final QueryDefinition<BlogsEntry> _statusInTrashQueryDefinition =
		new QueryDefinition<>(
			WorkflowConstants.STATUS_IN_TRASH, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	private User _user;

}