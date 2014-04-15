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

		group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(group);
	}

	@Test(expected = InvalidDDMStructureException.class)
	public void testAddArticleToFolderRestrictedByStructure() throws Exception {
		testAddArticleToRestrictedFolder(false);
	}

	@Test(expected = InvalidDDMStructureException.class)
	public void testAddArticleToFolderWithParentFolderRestrictedByStructure()
		throws Exception {

		testAddArticleToRestrictedFolder(true);
	}

	@Test
	public void testContent() throws Exception {
		JournalFolder folder = JournalTestUtil.addFolder(
			group.getGroupId(), JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Test Folder");

		JournalArticle article = JournalTestUtil.addArticle(
			group.getGroupId(), folder.getFolderId(), "Test Article",
			"This is a test article.");

		Assert.assertEquals(article.getFolderId(), folder.getFolderId());
	}

	@Test(expected = RestoreEntryException.class)
	public void testMoveArticleFromTrashToFolderRestrictedByStructure()
		throws Exception {

		testMoveArticleFromTrashToFolder(false);
	}

	@Test(expected = RestoreEntryException.class)
	public void testMoveArticleFromTrashToFolderWithParentFolderRestrictedByStructure()
		throws Exception {

		testMoveArticleFromTrashToFolder(true);
	}

	@Test(expected = InvalidDDMStructureException.class)
	public void testMoveArticleToFolderRestrictedByStructure()
		throws Exception {

		testMoveArticleToRestrictedFolder(false);
	}

	@Test(expected = InvalidDDMStructureException.class)
	public void testMoveArticleToFolderWithParentFolderRestrictedByStructure()
		throws Exception {

		testMoveArticleToRestrictedFolder(true);
	}

	@Test(expected = RestoreEntryException.class)
	public void testMoveFolderWithAnArticleInsideFromTrashToFolderRestrictedByStructure()
		throws Exception {

		testMoveFolderWithAnArticleFromTrashInsideToFolder(false);
	}

	@Test(expected = RestoreEntryException.class)
	public void testMoveFolderWithAnArticleInsideFromTrashToFolderWithParentFolderRestrictedByStructure()
		throws Exception {

		testMoveFolderWithAnArticleFromTrashInsideToFolder(true);
	}

	@Test(expected = InvalidDDMStructureException.class)
	public void testMoveFolderWithAnArticleInsideToFolderRestrictedByStructure()
		throws Exception {

		testMoveFolderWithAnArticleInsideToFolder(false);
	}

	@Test(expected = InvalidDDMStructureException.class)
	public void testMoveFolderWithAnArticleInsideToFolderWithParentFolderRestrictedByStructure()
		throws Exception {

		testMoveFolderWithAnArticleInsideToFolder(true);
	}

	@Test
	public void testSubfolders() throws Exception {
		JournalFolder folder1 = JournalTestUtil.addFolder(
			group.getGroupId(), JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Test 1");

		JournalFolder folder11 = JournalTestUtil.addFolder(
			group.getGroupId(), folder1.getFolderId(), "Test 1.1");

		JournalFolder folder111 = JournalTestUtil.addFolder(
			group.getGroupId(), folder11.getFolderId(), "Test 1.1.1");

		Assert.assertTrue(folder1.isRoot());
		Assert.assertFalse(folder11.isRoot());
		Assert.assertFalse(folder111.isRoot());

		Assert.assertEquals(
			folder1.getFolderId(), folder11.getParentFolderId());

		Assert.assertEquals(
			folder11.getFolderId(), folder111.getParentFolderId());
	}

	@Test(expected = InvalidDDMStructureException.class)
	public void testUpdateFolderRestrictionsWithAnArticleInside()
		throws Exception {

		testUpdateFolderRestrictions(false);
	}

	@Test(expected = InvalidDDMStructureException.class)
	public void testUpdateParentFolderRestrictionsWithAnArticleInside()
		throws Exception {

		testUpdateFolderRestrictions(true);
	}

	protected void testAddArticleToRestrictedFolder(boolean parentFolder)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		JournalFolder folder = JournalTestUtil.addFolder(
			group.getGroupId(), JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Test 1");

		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		long[] ddmStructureIds = new long[]{ddmStructure1.getStructureId()};

		JournalFolderLocalServiceUtil.updateFolder(
			TestPropsValues.getUserId(), folder.getFolderId(),
			folder.getParentFolderId(), folder.getName(),
			folder.getDescription(), ddmStructureIds, true, false,
			serviceContext);

		long parentFolderId = folder.getFolderId();

		if (parentFolder) {
			JournalFolder subFolder = JournalTestUtil.addFolder(
				group.getGroupId(), folder.getFolderId(), "Test 1.1");

			parentFolderId = subFolder.getFolderId();
		}

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		DDMTemplate ddmTemplate2 = DDMTemplateTestUtil.addTemplate(
			group.getGroupId(), ddmStructure2.getStructureId(),
			LocaleUtil.getDefault());

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			"Test article");

		JournalTestUtil.addArticleWithXMLContent(
			group.getGroupId(), parentFolderId,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml,
			ddmStructure2.getStructureKey(), ddmTemplate2.getTemplateKey());
	}

	protected void testMoveArticleFromTrashToFolder(boolean parentFolder)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		DDMTemplate ddmTemplate1 = DDMTemplateTestUtil.addTemplate(
			group.getGroupId(), ddmStructure1.getStructureId(),
			LocaleUtil.getDefault());

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			"Test article");

		JournalFolder folder1 = JournalTestUtil.addFolder(
			group.getGroupId(), JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Test 1");

		JournalArticle article = JournalTestUtil.addArticleWithXMLContent(
			group.getGroupId(), folder1.getFolderId(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml,
			ddmStructure1.getStructureKey(), ddmTemplate1.getTemplateKey());

		JournalFolderLocalServiceUtil.moveFolderToTrash(
			TestPropsValues.getUserId(), folder1.getFolderId());

		JournalFolder folder2 = JournalTestUtil.addFolder(
			group.getGroupId(), JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Test 2");

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		long[] ddmStructureIds = new long[]{ddmStructure2.getStructureId()};

		JournalFolderLocalServiceUtil.updateFolder(
			TestPropsValues.getUserId(), folder2.getFolderId(),
			folder2.getParentFolderId(), folder2.getName(),
			folder2.getDescription(), ddmStructureIds, true, false,
			serviceContext);

		long parentFolderId = folder2.getFolderId();

		if (parentFolder) {
			JournalFolder subFolder = JournalTestUtil.addFolder(
				group.getGroupId(), folder2.getFolderId(), "Test 2.1");

			parentFolderId = subFolder.getFolderId();
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			JournalArticle.class.getName());

		trashHandler.checkRestorableEntry(
			article.getResourcePrimKey(), parentFolderId, null);
	}

	protected void testMoveArticleToRestrictedFolder(boolean parentFolder)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		DDMTemplate ddmTemplate1 = DDMTemplateTestUtil.addTemplate(
			group.getGroupId(), ddmStructure1.getStructureId(),
			LocaleUtil.getDefault());

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			"Test article");

		JournalArticle article = JournalTestUtil.addArticleWithXMLContent(
			group.getGroupId(), JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml,
			ddmStructure1.getStructureKey(), ddmTemplate1.getTemplateKey());

		JournalFolder folder = JournalTestUtil.addFolder(
			group.getGroupId(), JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Test 1");

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		long[] ddmStructureIds = new long[]{ddmStructure2.getStructureId()};

		JournalFolderLocalServiceUtil.updateFolder(
			TestPropsValues.getUserId(), folder.getFolderId(),
			folder.getParentFolderId(), folder.getName(),
			folder.getDescription(), ddmStructureIds, true, false,
			serviceContext);

		long parentFolderId = folder.getFolderId();

		if (parentFolder) {
			JournalFolder subFolder = JournalTestUtil.addFolder(
				group.getGroupId(), folder.getFolderId(), "Test 1.1");

			parentFolderId = subFolder.getFolderId();
		}

		JournalArticleLocalServiceUtil.moveArticle(
			group.getGroupId(), article.getArticleId(), parentFolderId);
	}

	protected void testMoveFolderWithAnArticleFromTrashInsideToFolder(
			boolean parentFolder)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		DDMTemplate ddmTemplate1 = DDMTemplateTestUtil.addTemplate(
			group.getGroupId(), ddmStructure1.getStructureId(),
			LocaleUtil.getDefault());

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			"Test article");

		JournalFolder folder1 = JournalTestUtil.addFolder(
			group.getGroupId(), JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Test 1");

		JournalFolder folder2 = JournalTestUtil.addFolder(
			group.getGroupId(), folder1.getFolderId(), "Test 2");

		JournalTestUtil.addArticleWithXMLContent(
			group.getGroupId(), folder2.getFolderId(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml,
			ddmStructure1.getStructureKey(), ddmTemplate1.getTemplateKey());

		JournalFolderLocalServiceUtil.moveFolderToTrash(
			TestPropsValues.getUserId(), folder1.getFolderId());

		JournalFolder folder3 = JournalTestUtil.addFolder(
			group.getGroupId(), JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Test 3");

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		long[] ddmStructureIds = new long[]{ddmStructure2.getStructureId()};

		JournalFolderLocalServiceUtil.updateFolder(
			TestPropsValues.getUserId(), folder3.getFolderId(),
			folder3.getParentFolderId(), folder3.getName(),
			folder3.getDescription(), ddmStructureIds, true, false,
			serviceContext);

		long parentFolderId = folder3.getFolderId();

		if (parentFolder) {
			JournalFolder subFolder = JournalTestUtil.addFolder(
				group.getGroupId(), folder3.getFolderId(), "Test 3.1");

			parentFolderId = subFolder.getFolderId();
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			JournalFolder.class.getName());

		trashHandler.checkRestorableEntry(
			folder2.getFolderId(), parentFolderId, null);
	}

	protected void testMoveFolderWithAnArticleInsideToFolder(
			boolean parentFolder)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		DDMTemplate ddmTemplate1 = DDMTemplateTestUtil.addTemplate(
			group.getGroupId(), ddmStructure1.getStructureId(),
			LocaleUtil.getDefault());

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			"Test article");

		JournalFolder folder1 = JournalTestUtil.addFolder(
			group.getGroupId(), JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Test 1");

		JournalTestUtil.addArticleWithXMLContent(
			group.getGroupId(), folder1.getFolderId(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml,
			ddmStructure1.getStructureKey(), ddmTemplate1.getTemplateKey());

		JournalFolder folder2 = JournalTestUtil.addFolder(
			group.getGroupId(), JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Test 2");

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		long[] ddmStructureIds = new long[]{ddmStructure2.getStructureId()};

		JournalFolderLocalServiceUtil.updateFolder(
			TestPropsValues.getUserId(), folder2.getFolderId(),
			folder2.getParentFolderId(), folder2.getName(),
			folder2.getDescription(), ddmStructureIds, true, false,
			serviceContext);

		long parentFolderId = folder2.getFolderId();

		if (parentFolder) {
			JournalFolder subFolder = JournalTestUtil.addFolder(
				group.getGroupId(), folder2.getFolderId(), "Test 2.1");

			parentFolderId = subFolder.getFolderId();
		}

		JournalFolderLocalServiceUtil.moveFolder(
			folder1.getFolderId(), parentFolderId, serviceContext);
	}

	protected void testUpdateFolderRestrictions(boolean parentFolder)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		JournalFolder folder = JournalTestUtil.addFolder(
			group.getGroupId(), JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Test 1");

		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		DDMTemplate ddmTemplate1 = DDMTemplateTestUtil.addTemplate(
			group.getGroupId(), ddmStructure1.getStructureId(),
			LocaleUtil.getDefault());

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			"Test article");

		long parentFolderId = folder.getFolderId();

		if (parentFolder) {
			JournalFolder subFolder = JournalTestUtil.addFolder(
				group.getGroupId(), folder.getFolderId(), "Test 1.1");

			parentFolderId = subFolder.getFolderId();
		}

		JournalTestUtil.addArticleWithXMLContent(
			group.getGroupId(), parentFolderId,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml,
			ddmStructure1.getStructureKey(), ddmTemplate1.getTemplateKey());

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		long[] ddmStructureIds = new long[]{ddmStructure2.getStructureId()};

		JournalFolderLocalServiceUtil.updateFolder(
			TestPropsValues.getUserId(), folder.getFolderId(),
			folder.getParentFolderId(), folder.getName(),
			folder.getDescription(), ddmStructureIds, true, false,
			serviceContext);
	}

	protected Group group;

}