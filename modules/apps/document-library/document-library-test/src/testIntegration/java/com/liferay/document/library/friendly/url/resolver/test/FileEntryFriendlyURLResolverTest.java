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

package com.liferay.document.library.friendly.url.resolver.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.friendly.url.resolver.FileEntryFriendlyURLResolver;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class FileEntryFriendlyURLResolverTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testResolveFriendlyURL() throws Exception {
		String urlTitle = RandomTestUtil.randomString();

		_addFileEntry(urlTitle);

		Optional<FileEntry> fileEntryOptional =
			_fileEntryFriendlyURLResolver.resolveFriendlyURL(
				_group.getGroupId(), urlTitle);

		Assert.assertTrue(fileEntryOptional.isPresent());

		fileEntryOptional = _fileEntryFriendlyURLResolver.resolveFriendlyURL(
			_group.getGroupId(), RandomTestUtil.randomString());

		Assert.assertFalse(fileEntryOptional.isPresent());
	}

	private void _addFileEntry(String urlTitle) throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.configuration." +
						"FFFriendlyURLEntryFileEntryConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"enabled", true
					).build())) {

			_dlAppLocalService.addFileEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				RandomTestUtil.randomString(),
				ContentTypes.APPLICATION_OCTET_STREAM,
				RandomTestUtil.randomString(), urlTitle,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				(byte[])null, null, null,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
		}
	}

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private FileEntryFriendlyURLResolver _fileEntryFriendlyURLResolver;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Portal _portal;

}