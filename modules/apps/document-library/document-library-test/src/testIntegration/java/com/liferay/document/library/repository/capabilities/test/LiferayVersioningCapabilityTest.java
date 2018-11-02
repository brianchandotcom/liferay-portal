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

package com.liferay.document.library.repository.capabilities.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import java.util.Dictionary;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class LiferayVersioningCapabilityTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testLimitsTheNumberOfVersionsPerFileEntry() throws Exception {
		_withMaximumNumberOfVersionsConfigured(
			2,
			() -> {
				ServiceContext serviceContext =
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId());

				FileEntry fileEntry = _addRandomFileEntry(serviceContext);

				Assert.assertEquals(
					1,
					fileEntry.getFileVersionsCount(
						WorkflowConstants.STATUS_ANY));

				_generateNewVersion(fileEntry, serviceContext);

				Assert.assertEquals(
					2,
					fileEntry.getFileVersionsCount(
						WorkflowConstants.STATUS_ANY));

				_generateNewVersion(fileEntry, serviceContext);

				Assert.assertEquals(
					2,
					fileEntry.getFileVersionsCount(
						WorkflowConstants.STATUS_ANY));
			});
	}

	private FileEntry _addRandomFileEntry(ServiceContext serviceContext)
		throws PortalException {

		String content = StringUtil.randomString();

		return DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			content.getBytes(), serviceContext);
	}

	private FileEntry _generateNewVersion(
			FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException {

		String content = RandomTestUtil.randomString();

		return DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), fileEntry.getFileName(),
			fileEntry.getMimeType(), fileEntry.getTitle(),
			fileEntry.getDescription(), RandomTestUtil.randomString(),
			DLVersionNumberIncrease.MINOR, content.getBytes(), serviceContext);
	}

	private void _withMaximumNumberOfVersionsConfigured(
			int maximumNumberOfVersions,
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		dictionary.put("maximumNumberOfVersions", maximumNumberOfVersions);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.configuration." +
						"DLConfiguration",
					dictionary)) {

			unsafeRunnable.run();
		}
	}

	@DeleteAfterTestRun
	private Group _group;

}