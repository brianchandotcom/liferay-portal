/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.lar;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.lar.BaseWorkflowedStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.RepositoryUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.wiki.attachments.WikiAttachmentsTest;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.util.WikiTestUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class WikiPageStagedModelDataHandlerTest
	extends BaseWorkflowedStagedModelDataHandlerTestCase {

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<String, List<StagedModel>>();

		WikiNode node = WikiTestUtil.addNode(
			TestPropsValues.getUserId(), group.getGroupId(),
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString());

		addDependentStagedModel(dependentStagedModelsMap, WikiNode.class, node);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			WikiNode.class.getSimpleName());

		WikiNode node = (WikiNode)dependentStagedModels.get(0);

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiPage page = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), node.getNodeId(),
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString(),
			true, serviceContext);

		WikiTestUtil.addWikiAttachment(
			TestPropsValues.getUserId(), node.getNodeId(), page.getTitle(),
			WikiAttachmentsTest.class);

		List<FileEntry> attachmentsFileEntries =
			page.getAttachmentsFileEntries();

		FileEntry fileEntry = attachmentsFileEntries.get(0);

		Folder folder = fileEntry.getFolder();

		while (folder != null) {
			addDependentStagedModel(
				dependentStagedModelsMap, Folder.class, folder);

			folder = folder.getParentFolder();
		}

		addDependentStagedModel(
			dependentStagedModelsMap, FileEntry.class,
			attachmentsFileEntries.get(0));

		Repository repository = RepositoryUtil.fetchByPrimaryKey(
			fileEntry.getRepositoryId());

		addDependentStagedModel(
			dependentStagedModelsMap, Repository.class, repository);

		return page;
	}

	@Override
	protected List<StagedModel> addWorkflowedStagedModels(Group group)
		throws Exception {

		List<StagedModel> stagedModels = new ArrayList<StagedModel>();

		WikiNode node = WikiTestUtil.addNode(
			TestPropsValues.getUserId(), group.getGroupId(),
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString());

		WikiPage page = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), group.getGroupId(), node.getNodeId(),
			ServiceTestUtil.randomString(), true);

		stagedModels.add(page);

		WikiPage draftPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), group.getGroupId(), node.getNodeId(),
			ServiceTestUtil.randomString(), false);

		stagedModels.add(draftPage);

		return stagedModels;
	}

	@Override
	protected void deleteStagedModel(
			StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		WikiPageLocalServiceUtil.deletePage((WikiPage)stagedModel);
	}

	@Override
	protected StagedModelType[] getDeletionSystemEventStagedModelTypes() {
		WikiPortletDataHandler portletDataHandler =
			new WikiPortletDataHandler();

		return portletDataHandler.getDeletionSystemEventStagedModelTypes();
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws SystemException {

		return WikiPageLocalServiceUtil.fetchWikiPageByUuidAndGroupId(
			uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return WikiPage.class;
	}

	@Override
	protected String getStagedModelPath(long groupId, StagedModel stagedModel) {
		if (stagedModel instanceof FileEntry) {
			FileEntry fileEntry = (FileEntry)stagedModel;

			return ExportImportPathUtil.getModelPath(
				groupId, FileEntry.class.getName(), fileEntry.getFileEntryId());
		}
		else if (stagedModel instanceof Folder) {
			Folder folder = (Folder)stagedModel;

			return ExportImportPathUtil.getModelPath(
				groupId, Folder.class.getName(), folder.getFolderId());
		}

		return super.getStagedModelPath(groupId, stagedModel);
	}

	@Override
	protected void validateDeletion(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			Folder.class.getSimpleName());

		Folder folder = (Folder)dependentStagedModels.get(0);

		try {
			DLFolderLocalServiceUtil.getDLFolderByUuidAndGroupId(
				folder.getUuid(), folder.getGroupId());

			Assert.fail("Not Deleted: " + Folder.class);
		}
		catch (NoSuchFolderException nsfe) {
		}

		dependentStagedModels = dependentStagedModelsMap.get(
			FileEntry.class.getSimpleName());

		Assert.assertEquals(1, dependentStagedModels.size());

		FileEntry fileEntry = (FileEntry)dependentStagedModels.get(0);

		try {
			DLFileEntryLocalServiceUtil.getDLFileEntryByUuidAndGroupId(
				fileEntry.getUuid(), fileEntry.getGroupId());

			Assert.fail("Not Deleted: " + FileEntry.class);
		}
		catch (NoSuchFileEntryException nsfee) {
		}
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			WikiNode.class.getSimpleName());

		Assert.assertEquals(1, dependentStagedModels.size());

		WikiNode node = (WikiNode)dependentStagedModels.get(0);

		WikiNodeLocalServiceUtil.getWikiNodeByUuidAndGroupId(
			node.getUuid(), group.getGroupId());
	}

	@Override
	protected void validateImport(
			StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		WikiPage page = (WikiPage)getStagedModel(stagedModel.getUuid(), group);

		List<FileEntry> attachmentFileEntries =
			page.getAttachmentsFileEntries();

		Assert.assertEquals(1, attachmentFileEntries.size());

		validateImport(dependentStagedModelsMap, group);
	}

}