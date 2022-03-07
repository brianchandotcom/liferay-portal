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

package com.liferay.document.library.internal.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryService;
import com.liferay.document.library.test.util.BaseDLAppTestCase;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class FriendlyURLDLFileEntryServiceWrapperTest
	extends BaseDLAppTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddFileEntryAddsFriendlyURLEntry() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_FF_FRIENDLY_URL_ENTRY_FILE_ENTRY_CONFIGURATION_PID,
					HashMapDictionaryBuilder.<String, Object>put(
						"enabled", true
					).build())) {

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					group.getGroupId(), TestPropsValues.getUserId());

			byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

			InputStream inputStream = new ByteArrayInputStream(bytes);

			DLFileEntry dlFileEntry = _dlFileEntryService.addFileEntry(
				null, group.getGroupId(), group.getGroupId(),
				parentFolder.getFolderId(), RandomTestUtil.randomString(),
				ContentTypes.APPLICATION_OCTET_STREAM, "title", "urltitle",
				StringPool.BLANK, StringPool.BLANK,
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
				null, null, inputStream, bytes.length, null, null,
				serviceContext);

			FriendlyURLEntry friendlyURLEntry =
				_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
					_portal.getClassNameId(FileEntry.class),
					dlFileEntry.getFileEntryId());

			Assert.assertNotNull(friendlyURLEntry);

			Assert.assertEquals("urltitle", friendlyURLEntry.getUrlTitle());
		}
	}

	@Test
	public void testUpdateFileEntryUpdatesFriendlyURLEntry() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_FF_FRIENDLY_URL_ENTRY_FILE_ENTRY_CONFIGURATION_PID,
					HashMapDictionaryBuilder.<String, Object>put(
						"enabled", true
					).build())) {

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					group.getGroupId(), TestPropsValues.getUserId());

			byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

			InputStream inputStream = new ByteArrayInputStream(bytes);

			DLFileEntry dlFileEntry = _dlFileEntryService.addFileEntry(
				null, group.getGroupId(), group.getGroupId(),
				parentFolder.getFolderId(), RandomTestUtil.randomString(),
				ContentTypes.APPLICATION_OCTET_STREAM,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				StringPool.BLANK, StringPool.BLANK,
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
				null, null, inputStream, bytes.length, null, null,
				serviceContext);

			dlFileEntry = _dlFileEntryService.updateFileEntry(
				dlFileEntry.getFileEntryId(), StringUtil.randomString(),
				ContentTypes.APPLICATION_OCTET_STREAM,
				RandomTestUtil.randomString(), "urltitle", StringPool.BLANK,
				StringPool.BLANK, DLVersionNumberIncrease.MAJOR,
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
				Collections.emptyMap(), null, inputStream, 0, null, null,
				serviceContext);

			FriendlyURLEntry friendlyURLEntry =
				_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
					_portal.getClassNameId(FileEntry.class),
					dlFileEntry.getFileEntryId());

			Assert.assertEquals("urltitle", friendlyURLEntry.getUrlTitle());
		}
	}

	private static final String
		_FF_FRIENDLY_URL_ENTRY_FILE_ENTRY_CONFIGURATION_PID =
			"com.liferay.document.library.configuration." +
				"FFFriendlyURLEntryFileEntryConfiguration";

	@Inject
	private static DLAppService _dlAppService;

	@Inject
	private static DLFileEntryService _dlFileEntryService;

	@Inject
	private static FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Inject
	private static Portal _portal;

}