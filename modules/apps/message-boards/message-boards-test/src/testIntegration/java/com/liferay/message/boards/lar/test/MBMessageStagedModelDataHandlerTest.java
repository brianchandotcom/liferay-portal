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

package com.liferay.message.boards.lar.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.exportimport.kernel.lar.ExportImportClassedModelUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.exportimport.test.util.lar.BaseWorkflowedStagedModelDataHandlerTestCase;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBCategoryLocalServiceUtil;
import com.liferay.message.boards.service.MBCategoryServiceUtil;
import com.liferay.message.boards.service.MBMessageLocalServiceUtil;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.message.boards.test.util.MBTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.RepositoryLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Kocsis
 */
@RunWith(Arquillian.class)
public class MBMessageStagedModelDataHandlerTest
	extends BaseWorkflowedStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testDeleteAttachmentFileEntry() throws Exception {
		Map<String, List<StagedModel>> dependentStagedModelsMap =
			addDependentStagedModelsMap(stagingGroup);

		MBMessage mbMessage = (MBMessage)addStagedModel(
			stagingGroup, dependentStagedModelsMap);

		exportImportStagedModel(mbMessage);

		MBMessage importedMBMessage = (MBMessage)getStagedModel(
			mbMessage.getUuid(), liveGroup);

		Assert.assertEquals(
			1, importedMBMessage.getAttachmentsFileEntriesCount());

		List<FileEntry> attachmentsFileEntries =
			importedMBMessage.getAttachmentsFileEntries();

		FileEntry attachmentFileEntry = attachmentsFileEntries.get(0);

		MBMessageLocalServiceUtil.moveMessageAttachmentToTrash(
			mbMessage.getUserId(), mbMessage.getMessageId(),
			attachmentFileEntry.getFileName());

		exportImportStagedModel(mbMessage);

		importedMBMessage = (MBMessage)getStagedModel(
			mbMessage.getUuid(), liveGroup);

		Assert.assertEquals(
			0, importedMBMessage.getAttachmentsFileEntriesCount());
	}

	@Test
	public void testDoubleExportImport() throws Exception {
		Map<String, List<StagedModel>> dependentStagedModelsMap =
			addDependentStagedModelsMap(stagingGroup);

		StagedModel stagedModel = addStagedModel(
			stagingGroup, dependentStagedModelsMap);

		exportImportStagedModel(stagedModel);

		StagedModel importedStagedModel = getStagedModel(
			stagedModel.getUuid(), liveGroup);

		Assert.assertNotNull(importedStagedModel);

		exportImportStagedModel(stagedModel);

		importedStagedModel = getStagedModel(stagedModel.getUuid(), liveGroup);

		Assert.assertNotNull(importedStagedModel);
	}

	@Test
	public void testMoveCategory() throws Exception {
		Map<String, List<StagedModel>> dependentStagedModelsMap =
			addDependentStagedModelsMap(stagingGroup);

		MBMessage mbMessage = (MBMessage)addStagedModel(
			stagingGroup, dependentStagedModelsMap);

		exportImportStagedModel(mbMessage);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId(), TestPropsValues.getUserId());

		MBCategory mbCategory = MBCategoryServiceUtil.addCategory(
			TestPropsValues.getUserId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);

		MBThread mbThread = mbMessage.getThread();

		_mbThreadLocalService.moveThread(
			stagingGroup.getGroupId(), mbCategory.getCategoryId(),
			mbThread.getThreadId());

		MBMessage movedMbMessage = (MBMessage)getStagedModel(
			mbMessage.getUuid(), stagingGroup);

		exportImportStagedModel(movedMbMessage);

		MBMessage importedMBMessage = (MBMessage)getStagedModel(
			mbMessage.getUuid(), liveGroup);

		MBCategory importedCategory = MBCategoryServiceUtil.getCategory(
			importedMBMessage.getCategoryId());

		Assert.assertEquals(mbCategory.getName(), importedCategory.getName());
	}

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		MBCategory category = MBCategoryServiceUtil.addCategory(
			TestPropsValues.getUserId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);

		addDependentStagedModel(
			dependentStagedModelsMap, MBCategory.class, category);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			MBCategory.class.getSimpleName());

		MBCategory category = (MBCategory)dependentStagedModels.get(0);

		List<ObjectValuePair<String, InputStream>> objectValuePairs =
			MBTestUtil.getInputStreamOVPs(
				"attachment.txt", getClass(), StringPool.BLANK);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		MBMessage message = MBMessageLocalServiceUtil.addMessage(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			group.getGroupId(), category.getCategoryId(), 0, 0,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			MBMessageConstants.DEFAULT_FORMAT, objectValuePairs, false, 0.0,
			false, serviceContext);

		MBMessageLocalServiceUtil.updateAnswer(message, true, false);

		List<FileEntry> attachmentsFileEntries =
			message.getAttachmentsFileEntries();

		FileEntry fileEntry = attachmentsFileEntries.get(0);

		Folder folder = fileEntry.getFolder();

		while (folder != null) {
			addDependentStagedModel(
				dependentStagedModelsMap, DLFolder.class, folder);

			folder = folder.getParentFolder();
		}

		addDependentStagedModel(
			dependentStagedModelsMap, DLFileEntry.class,
			attachmentsFileEntries.get(0));

		Repository repository = RepositoryLocalServiceUtil.getRepository(
			fileEntry.getRepositoryId());

		addDependentStagedModel(
			dependentStagedModelsMap, Repository.class, repository);

		return message;
	}

	@Override
	protected List<StagedModel> addWorkflowedStagedModels(Group group)
		throws Exception {

		List<StagedModel> stagedModels = new ArrayList<>();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		MBMessage approvedMessage = MBTestUtil.addMessageWithWorkflow(
			group.getGroupId(), MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), true,
			serviceContext);

		stagedModels.add(approvedMessage);

		MBMessage pendingMessage = MBTestUtil.addMessageWithWorkflow(
			group.getGroupId(), MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			serviceContext);

		stagedModels.add(pendingMessage);

		return stagedModels;
	}

	@Override
	protected void deleteStagedModel(
			StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		@SuppressWarnings("rawtypes")
		StagedModelDataHandler stagedModelDataHandler =
			StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
				ExportImportClassedModelUtil.getClassName(stagedModel));

		stagedModelDataHandler.deleteStagedModel(stagedModel);

		for (List<StagedModel> dependentStagedModels :
				dependentStagedModelsMap.values()) {

			for (StagedModel dependentStagedModel : dependentStagedModels) {
				try {
					stagedModelDataHandler =
						StagedModelDataHandlerRegistryUtil.
							getStagedModelDataHandler(
								ExportImportClassedModelUtil.getClassName(
									dependentStagedModel));

					stagedModelDataHandler.deleteStagedModel(
						dependentStagedModel);
				}
				catch (NoSuchModelException nsme) {
					if (!(nsme instanceof NoSuchFileEntryException) &&
						!(nsme instanceof NoSuchFolderException)) {

						throw nsme;
					}
				}
			}
		}
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return MBMessageLocalServiceUtil.getMBMessageByUuidAndGroupId(
			uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return MBMessage.class;
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			MBCategory.class.getSimpleName());

		Assert.assertEquals(
			dependentStagedModels.toString(), 1, dependentStagedModels.size());

		MBCategory category = (MBCategory)dependentStagedModels.get(0);

		MBCategoryLocalServiceUtil.getMBCategoryByUuidAndGroupId(
			category.getUuid(), group.getGroupId());
	}

	@Override
	protected void validateImport(
			StagedModel stagedModel, StagedModelAssets stagedModelAssets,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		super.validateImport(
			stagedModel, stagedModelAssets, dependentStagedModelsMap, group);

		MBMessage importedMessage = (MBMessage)getStagedModel(
			stagedModel.getUuid(), group);

		Assert.assertEquals(
			1, importedMessage.getAttachmentsFileEntriesCount());
		Assert.assertTrue(importedMessage.isAnswer());
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		MBMessage message = (MBMessage)stagedModel;
		MBMessage importedMessage = (MBMessage)importedStagedModel;

		Assert.assertEquals(message.getSubject(), importedMessage.getSubject());
		Assert.assertEquals(message.getFormat(), importedMessage.getFormat());
		Assert.assertEquals(
			message.isAnonymous(), importedMessage.isAnonymous());
		Assert.assertEquals(
			message.getPriority(), importedMessage.getPriority(), 0L);
		Assert.assertEquals(
			message.isAllowPingbacks(), importedMessage.isAllowPingbacks());
		Assert.assertEquals(message.isAnswer(), importedMessage.isAnswer());
	}

	@Inject
	private MBThreadLocalService _mbThreadLocalService;

}