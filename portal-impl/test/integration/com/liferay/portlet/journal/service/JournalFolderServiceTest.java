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

package com.liferay.portlet.journal.service;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.util.DDMStructureTestUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMTemplateTestUtil;
import com.liferay.portlet.journal.InvalidDDMStructureException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.util.JournalTestUtil;
import com.liferay.portlet.trash.RestoreEntryException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Juan Fernández
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
@Transactional
public class JournalFolderServiceTest {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_group);
	}

	@Test
	public void testAddArticle() throws Exception {
		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test Folder");

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(), folder.getFolderId(), "Test Article",
			"This is a test article.");

		Assert.assertEquals(article.getFolderId(), folder.getFolderId());
	}

	@Test
	public void testAddArticleToRestrictedFolder() throws Exception {
		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 1");

		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		long[] ddmStructureIds = new long[]{ddmStructure1.getStructureId()};

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		JournalFolderLocalServiceUtil.updateFolder(
			TestPropsValues.getUserId(), folder.getFolderId(),
			folder.getParentFolderId(), folder.getName(),
			folder.getDescription(), ddmStructureIds,
			JournalFolderConstants.RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW,
			false, serviceContext);

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			"Test Article");

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		DDMTemplate ddmTemplate2 = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure2.getStructureId(),
			LocaleUtil.getDefault());

		try {
			JournalTestUtil.addArticleWithXMLContent(
				_group.getGroupId(), folder.getFolderId(),
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml,
				ddmStructure2.getStructureKey(), ddmTemplate2.getTemplateKey());

			Assert.fail();
		}
		catch (InvalidDDMStructureException iddmse) {
		}

		JournalFolder subfolder = JournalTestUtil.addFolder(
			_group.getGroupId(), folder.getFolderId(), "Test 1.1");

		try {
			JournalTestUtil.addArticleWithXMLContent(
				_group.getGroupId(), subfolder.getFolderId(),
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml,
				ddmStructure2.getStructureKey(), ddmTemplate2.getTemplateKey());

			Assert.fail();
		}
		catch (InvalidDDMStructureException iddmse) {
		}
	}

	@Test
	public void testInheritedWorkflowFolder() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		JournalFolderServiceUtil.updateFolder(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, null, null,
			new long[0], JournalFolderConstants.RESTRICTION_TYPE_WORKFLOW,
			false, serviceContext);

		JournalFolder countries = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Countries");

		Assert.assertEquals(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalFolderLocalServiceUtil.getInheritedWorkflowFolderId(
				countries.getFolderId()));

		JournalFolder germany = JournalTestUtil.addFolder(
			_group.getGroupId(), countries.getFolderId(), "Germany");

		Assert.assertEquals(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalFolderLocalServiceUtil.getInheritedWorkflowFolderId(
				germany.getFolderId()));

		JournalFolder spain = JournalTestUtil.addFolder(
			_group.getGroupId(), countries.getFolderId(), "Spain");

		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		long[] ddmStructureIds = new long[]{ddmStructure1.getStructureId()};

		JournalFolderServiceUtil.updateFolder(
			spain.getFolderId(), spain.getParentFolderId(), spain.getName(),
			spain.getDescription(), ddmStructureIds,
			JournalFolderConstants.RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW,
			false, serviceContext);

		Assert.assertEquals(
			spain.getFolderId(),
			JournalFolderLocalServiceUtil.getInheritedWorkflowFolderId(
				spain.getFolderId()));

		JournalFolder madrid = JournalTestUtil.addFolder(
			_group.getGroupId(), spain.getFolderId(), "Madrid");

		Assert.assertEquals(
			spain.getFolderId(),
			JournalFolderLocalServiceUtil.getInheritedWorkflowFolderId(
				madrid.getFolderId()));
	}

	@Test
	public void testMoveArticleFromTrashToFolder() throws Exception {
		JournalFolder folder1 = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 1");

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			"Test Article");

		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		DDMTemplate ddmTemplate1 = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure1.getStructureId(),
			LocaleUtil.getDefault());

		JournalArticle article = JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), folder1.getFolderId(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml,
			ddmStructure1.getStructureKey(), ddmTemplate1.getTemplateKey());

		JournalFolderLocalServiceUtil.moveFolderToTrash(
			TestPropsValues.getUserId(), folder1.getFolderId());

		JournalFolder folder2 = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 2");

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		long[] ddmStructureIds = new long[]{ddmStructure2.getStructureId()};

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		JournalFolderLocalServiceUtil.updateFolder(
			TestPropsValues.getUserId(), folder2.getFolderId(),
			folder2.getParentFolderId(), folder2.getName(),
			folder2.getDescription(), ddmStructureIds,
			JournalFolderConstants.RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW,
			false, serviceContext);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			JournalArticle.class.getName());

		try {
			trashHandler.checkRestorableEntry(
				article.getResourcePrimKey(), folder2.getFolderId(), null);

			Assert.fail();
		}
		catch (RestoreEntryException ree) {
		}

		JournalFolder subfolder = JournalTestUtil.addFolder(
			_group.getGroupId(), folder2.getFolderId(), "Test 2.1");

		try {
			trashHandler.checkRestorableEntry(
				article.getResourcePrimKey(), subfolder.getFolderId(), null);

			Assert.fail();
		}
		catch (RestoreEntryException ree) {
		}
	}

	@Test
	public void testMoveArticleToRestrictedFolder() throws Exception {
		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		DDMTemplate ddmTemplate1 = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure1.getStructureId(),
			LocaleUtil.getDefault());

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			"Test Article");

		JournalArticle article = JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml,
			ddmStructure1.getStructureKey(), ddmTemplate1.getTemplateKey());

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 1");

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		long[] ddmStructureIds = new long[]{ddmStructure2.getStructureId()};

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		JournalFolderLocalServiceUtil.updateFolder(
			TestPropsValues.getUserId(), folder.getFolderId(),
			folder.getParentFolderId(), folder.getName(),
			folder.getDescription(), ddmStructureIds,
			JournalFolderConstants.RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW,
			false, serviceContext);

		try {
			JournalArticleLocalServiceUtil.moveArticle(
				_group.getGroupId(), article.getArticleId(),
				folder.getFolderId());

			Assert.fail();
		}
		catch (InvalidDDMStructureException iddmse) {
		}

		JournalFolder subfolder = JournalTestUtil.addFolder(
			_group.getGroupId(), folder.getFolderId(), "Test 1.1");

		try {
			JournalArticleLocalServiceUtil.moveArticle(
				_group.getGroupId(), article.getArticleId(),
				subfolder.getFolderId());

			Assert.fail();
		}
		catch (InvalidDDMStructureException iddmse) {
		}
	}

	@Test
	public void testMoveFolderWithAnArticleInTrashToFolder() throws Exception {
		JournalFolder folder1 = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 1");

		JournalFolder folder2 = JournalTestUtil.addFolder(
			_group.getGroupId(), folder1.getFolderId(), "Test 2");

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			"Test Article");

		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		DDMTemplate ddmTemplate1 = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure1.getStructureId(),
			LocaleUtil.getDefault());

		JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), folder2.getFolderId(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml,
			ddmStructure1.getStructureKey(), ddmTemplate1.getTemplateKey());

		JournalFolderLocalServiceUtil.moveFolderToTrash(
			TestPropsValues.getUserId(), folder1.getFolderId());

		JournalFolder folder3 = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 3");

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		long[] ddmStructureIds = new long[]{ddmStructure2.getStructureId()};

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		JournalFolderLocalServiceUtil.updateFolder(
			TestPropsValues.getUserId(), folder3.getFolderId(),
			folder3.getParentFolderId(), folder3.getName(),
			folder3.getDescription(), ddmStructureIds,
			JournalFolderConstants.RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW,
			false, serviceContext);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			JournalFolder.class.getName());

		try {
			trashHandler.checkRestorableEntry(
				folder2.getFolderId(), folder3.getFolderId(), null);

			Assert.fail();
		}
		catch (RestoreEntryException ree) {
		}

		JournalFolder subfolder = JournalTestUtil.addFolder(
			_group.getGroupId(), folder3.getFolderId(), "Test 3.1");

		try {
			trashHandler.checkRestorableEntry(
				folder2.getFolderId(), subfolder.getFolderId(), null);

			Assert.fail();
		}
		catch (RestoreEntryException ree) {
		}
	}

	@Test
	public void testMoveFolderWithAnArticleToFolder() throws Exception {
		JournalFolder folder1 = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 1");

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			"Test Article");

		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		DDMTemplate ddmTemplate1 = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure1.getStructureId(),
			LocaleUtil.getDefault());

		JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), folder1.getFolderId(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml,
			ddmStructure1.getStructureKey(), ddmTemplate1.getTemplateKey());

		JournalFolder folder2 = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 2");

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		long[] ddmStructureIds = new long[]{ddmStructure2.getStructureId()};

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		JournalFolderLocalServiceUtil.updateFolder(
			TestPropsValues.getUserId(), folder2.getFolderId(),
			folder2.getParentFolderId(), folder2.getName(),
			folder2.getDescription(), ddmStructureIds,
			JournalFolderConstants.RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW,
			false, serviceContext);

		try {
			JournalFolderLocalServiceUtil.moveFolder(
				folder1.getFolderId(), folder2.getFolderId(), serviceContext);

			Assert.fail();
		}
		catch (InvalidDDMStructureException iddmse) {
		}

		JournalFolder subfolder = JournalTestUtil.addFolder(
			_group.getGroupId(), folder2.getFolderId(), "Test 2.1");

		try {
			JournalFolderLocalServiceUtil.moveFolder(
				folder1.getFolderId(), subfolder.getFolderId(), serviceContext);

			Assert.fail();
		}
		catch (InvalidDDMStructureException iddmse) {
		}
	}

	@Test
	public void testSubfolders() throws Exception {
		JournalFolder folder1 = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 1");

		JournalFolder folder11 = JournalTestUtil.addFolder(
			_group.getGroupId(), folder1.getFolderId(), "Test 1.1");

		JournalFolder folder111 = JournalTestUtil.addFolder(
			_group.getGroupId(), folder11.getFolderId(), "Test 1.1.1");

		Assert.assertTrue(folder1.isRoot());
		Assert.assertFalse(folder11.isRoot());
		Assert.assertFalse(folder111.isRoot());
		Assert.assertEquals(
			folder1.getFolderId(), folder11.getParentFolderId());
		Assert.assertEquals(
			folder11.getFolderId(), folder111.getParentFolderId());
	}

	@Test
	public void testUpdateFolderRestrictions() throws Exception {
		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 1");

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			"Test Article");

		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		DDMTemplate ddmTemplate1 = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure1.getStructureId(),
			LocaleUtil.getDefault());

		JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), folder.getFolderId(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml,
			ddmStructure1.getStructureKey(), ddmTemplate1.getTemplateKey());

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		long[] ddmStructureIds = new long[]{ddmStructure2.getStructureId()};

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		try {
			JournalFolderLocalServiceUtil.updateFolder(
				TestPropsValues.getUserId(), folder.getFolderId(),
				folder.getParentFolderId(), folder.getName(),
				folder.getDescription(), ddmStructureIds,
				JournalFolderConstants.
					RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW,
				false, serviceContext);

			Assert.fail();
		}
		catch (InvalidDDMStructureException iddmse) {
		}

		JournalFolder subfolder = JournalTestUtil.addFolder(
			_group.getGroupId(), folder.getFolderId(), "Test 1.1");

		JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), subfolder.getFolderId(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml,
			ddmStructure1.getStructureKey(), ddmTemplate1.getTemplateKey());

		try {
			JournalFolderLocalServiceUtil.updateFolder(
				TestPropsValues.getUserId(), folder.getFolderId(),
				folder.getParentFolderId(), folder.getName(),
				folder.getDescription(), ddmStructureIds,
				JournalFolderConstants.
					RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW,
				false, serviceContext);

			Assert.fail();
		}
		catch (InvalidDDMStructureException iddmse) {
		}
	}

	private Group _group;

}