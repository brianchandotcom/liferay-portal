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

package com.liferay.wiki.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.exportimport.kernel.lar.ExportImportClassedModelUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.RepositoryLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.lar.test.BaseWorkflowedStagedModelDataHandlerTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.wiki.attachments.test.WikiAttachmentsTest;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.util.test.WikiTestUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@RunWith(Arquillian.class)
@Sync
public class WikiPageStagedModelDataHandlerTest
	extends BaseWorkflowedStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		WikiNode node = WikiTestUtil.addNode(group.getGroupId());

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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		WikiPage page = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), node.getNodeId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), true,
			serviceContext);

		WikiTestUtil.addWikiAttachment(
			TestPropsValues.getUserId(), node.getNodeId(), page.getTitle(),
			WikiAttachmentsTest.class);

		List<FileEntry> attachmentsFileEntries =
			page.getAttachmentsFileEntries();

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

		return page;
	}

	@Override
	protected List<StagedModel> addWorkflowedStagedModels(Group group)
		throws Exception {

		List<StagedModel> stagedModels = new ArrayList<>();

		WikiNode node = WikiTestUtil.addNode(group.getGroupId());

		WikiPage page = WikiTestUtil.addPage(
			group.getGroupId(), node.getNodeId(), true);

		stagedModels.add(page);

		WikiPage draftPage = WikiTestUtil.addPage(
			group.getGroupId(), node.getNodeId(), false);

		stagedModels.add(draftPage);

		return stagedModels;
	}

	@Override
	protected void deleteStagedModel(
			StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

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
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return WikiPageLocalServiceUtil.getWikiPageByUuidAndGroupId(
				uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return WikiPage.class;
	}

	@Override
	protected boolean isCommentableStagedModel() {
		return true;
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			WikiNode.class.getSimpleName());

		Assert.assertEquals(
			dependentStagedModels.toString(), 1, dependentStagedModels.size());

		WikiNode node = (WikiNode)dependentStagedModels.get(0);

		WikiNodeLocalServiceUtil.getWikiNodeByUuidAndGroupId(
			node.getUuid(), group.getGroupId());
	}

	@Override
	protected void validateImport(
			StagedModel stagedModel, StagedModelAssets stagedModelAssets,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		super.validateImport(
			stagedModel, stagedModelAssets, dependentStagedModelsMap, group);

		WikiPage page = (WikiPage)stagedModel;

		List<FileEntry> attachmentFileEntries =
			page.getAttachmentsFileEntries();

		Assert.assertEquals(
			attachmentFileEntries.toString(), 1, attachmentFileEntries.size());

		validateImport(dependentStagedModelsMap, group);
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		WikiPage page = (WikiPage)stagedModel;
		WikiPage importedPage = (WikiPage)importedStagedModel;

		Assert.assertEquals(page.getTitle(), importedPage.getTitle());
		Assert.assertEquals(page.getVersion(), importedPage.getVersion(), 0L);
		Assert.assertEquals(page.isMinorEdit(), importedPage.isMinorEdit());
		Assert.assertEquals(page.getSummary(), importedPage.getSummary());
		Assert.assertEquals(page.getFormat(), importedPage.getFormat());
		Assert.assertEquals(page.isHead(), importedPage.isHead());
		Assert.assertEquals(
			page.getParentTitle(), importedPage.getParentTitle());
		Assert.assertEquals(
			page.getRedirectTitle(), importedPage.getRedirectTitle());
	}

}