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

package com.liferay.portlet.messageboards.lar;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.lar.test.BaseWorkflowedStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.persistence.RepositoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Daniel Kocsis
 */
public class MBMessageStagedModelDataHandlerTest
	extends BaseWorkflowedStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			TransactionalTestRule.INSTANCE);

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		MBCategory category = MBTestUtil.addCategory(group.getGroupId());

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

		MBMessage message = MBTestUtil.addMessageWithWorkflowAndAttachments(
			group.getGroupId(), category.getCategoryId(), true,
			objectValuePairs);

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

		Repository repository = RepositoryUtil.fetchByPrimaryKey(
			fileEntry.getRepositoryId());

		addDependentStagedModel(
			dependentStagedModelsMap, Repository.class, repository);

		return message;
	}

	@Override
	protected List<StagedModel> addWorkflowedStagedModels(Group group)
		throws Exception {

		List<StagedModel> stagedModels = new ArrayList<>();

		stagedModels.add(
			MBTestUtil.addMessageWithWorkflow(group.getGroupId(), true));

		stagedModels.add(
			MBTestUtil.addMessageWithWorkflow(group.getGroupId(), false));

		return stagedModels;
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return MBMessageLocalServiceUtil.getMBMessageByUuidAndGroupId(
				uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
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

		Assert.assertEquals(1, dependentStagedModels.size());

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

}