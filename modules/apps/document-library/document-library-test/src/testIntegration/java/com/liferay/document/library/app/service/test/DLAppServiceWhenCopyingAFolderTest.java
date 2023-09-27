/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.app.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.exception.DuplicateFolderNameException;
import com.liferay.document.library.kernel.exception.InvalidFolderException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.test.util.BaseDLAppTestCase;
import com.liferay.document.library.workflow.WorkflowHandlerInvocationCounter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 */
@RunWith(Arquillian.class)
public class DLAppServiceWhenCopyingAFolderTest extends BaseDLAppTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testShouldCallWorkflowHandler() throws Exception {
		try (WorkflowHandlerInvocationCounter<DLFileEntry>
				workflowHandlerInvocationCounter =
					new WorkflowHandlerInvocationCounter<>(
						DLFileEntryConstants.getClassName())) {

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			Folder folder = DLAppServiceUtil.addFolder(
				null, group.getGroupId(), parentFolder.getFolderId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				serviceContext);

			DLAppServiceTestUtil.addFileEntry(
				group.getGroupId(), folder.getFolderId());

			Assert.assertEquals(
				1,
				workflowHandlerInvocationCounter.getCount(
					"updateStatus", Object.class, int.class, Map.class));

			DLAppServiceUtil.copyFolder(
				folder.getRepositoryId(), folder.getFolderId(),
				parentFolder.getParentFolderId(), folder.getName(),
				folder.getDescription(), serviceContext);

			Assert.assertEquals(
				2,
				workflowHandlerInvocationCounter.getCount(
					"updateStatus", Object.class, int.class, Map.class));
		}
	}

	@Test
	public void testShouldFailIfDestinationIsSameFolder()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		try {
			DLAppServiceUtil.copyFolder(
				group.getGroupId(), parentFolder.getFolderId(),
				group.getGroupId(), parentFolder.getFolderId(), new HashMap<>(),
				null, serviceContext);

			Assert.fail();
		}
		catch (InvalidFolderException invalidFolderException1) {
			InvalidFolderException invalidFolderException2 =
				new InvalidFolderException(
					InvalidFolderException.CANNOT_COPY_INTO_ITSELF,
					parentFolder.getFolderId());

			Assert.assertEquals(
				invalidFolderException1.getMessageKey(),
				invalidFolderException2.getMessageKey());
			Assert.assertEquals(
				invalidFolderException1.getFolderId(),
				invalidFolderException2.getFolderId());
		}
	}

	@Test
	public void testShouldFailIfDestinationIsSubfolder()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		Folder folder = DLAppServiceUtil.addFolder(
			null, group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);

		try {
			DLAppServiceUtil.copyFolder(
				group.getGroupId(), parentFolder.getFolderId(),
				group.getGroupId(), folder.getFolderId(), new HashMap<>(), null,
				serviceContext);

			Assert.fail();
		}
		catch (InvalidFolderException invalidFolderException1) {
			InvalidFolderException invalidFolderException2 =
				new InvalidFolderException(
					InvalidFolderException.CANNOT_COPY_INTO_CHILD_FOLDER,
					folder.getFolderId());

			Assert.assertEquals(
				invalidFolderException1.getMessageKey(),
				invalidFolderException2.getMessageKey());
			Assert.assertEquals(
				invalidFolderException1.getFolderId(),
				invalidFolderException2.getFolderId());
		}
	}

	@Test(expected = DuplicateFolderNameException.class)
	public void testShouldFailIfUsingSameNameAndDestinationIsParentFolder()
		throws PortalException {

		DLAppServiceUtil.copyFolder(
			group.getGroupId(), parentFolder.getFolderId(), group.getGroupId(),
			parentFolder.getParentFolderId(), new HashMap<>(), null,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	@Test
	public void testShouldSucceedBetweenDifferentSites() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		Map<String, List<String>> fileNamesMap = _createFileNamesMap(3);

		_addFoldersAndFileEntries(fileNamesMap, serviceContext);

		Folder folder = DLAppServiceUtil.copyFolder(
			group.getGroupId(), parentFolder.getFolderId(),
			targetGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, new HashMap<>(), null,
			serviceContext);

		Assert.assertEquals(parentFolder.getName(), folder.getName());
		AssertUtils.assertEquals(fileNamesMap, _getFileNamesMap(folder));
	}

	private void _addFoldersAndFileEntries(
			Map<String, List<String>> fileNamesMap,
			ServiceContext serviceContext)
		throws Exception {

		for (Map.Entry<String, List<String>> entry : fileNamesMap.entrySet()) {
			Folder folder = DLAppServiceUtil.addFolder(
				null, group.getGroupId(), parentFolder.getFolderId(),
				entry.getKey(), StringPool.BLANK, serviceContext);

			for (String fileName : entry.getValue()) {
				DLAppServiceTestUtil.addFileEntry(
					group.getGroupId(), folder.getFolderId(), fileName);
			}
		}
	}

	private Map<String, List<String>> _createFileNamesMap(int foldersCount) {
		Map<String, List<String>> fileNamesMap = new HashMap<>(foldersCount);

		List<String> folderNames = ListUtil.fromArray(
			RandomTestUtil.randomStrings(foldersCount));

		for (int i = 0; i < foldersCount; i++) {
			List<String> fileNames = new ArrayList<>(i);

			if (i > 0) {
				Collections.addAll(fileNames, RandomTestUtil.randomStrings(i));
			}

			Collections.sort(fileNames);
			fileNamesMap.put(folderNames.get(i), fileNames);
		}

		return fileNamesMap;
	}

	private Map<String, List<String>> _getFileNamesMap(Folder parentFolder)
		throws Exception {

		Map<String, List<String>> fileNamesMap = new HashMap<>();

		List<Folder> folders = DLAppServiceUtil.getFolders(
			parentFolder.getRepositoryId(), parentFolder.getFolderId());

		for (Folder folder : folders) {
			List<FileEntry> fileEntries = DLAppServiceUtil.getFileEntries(
				parentFolder.getRepositoryId(), folder.getFolderId());

			List<String> fileNames = new ArrayList<>();

			fileEntries.forEach(
				fileEntry -> fileNames.add(fileEntry.getFileName()));

			Collections.sort(fileNames);
			fileNamesMap.put(folder.getName(), fileNames);
		}

		return fileNamesMap;
	}

}