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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.User;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.spring.hibernate.LastSessionRecorderUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portal.util.test.UserTestUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class DLFileEntryFinderTest {

	@Before
	public void setUp() throws Exception {
		long classNameId = PortalUtil.getClassNameId(
			LiferayRepository.class.getName());

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		_group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_repository = RepositoryLocalServiceUtil.addRepository(
			TestPropsValues.getUserId(), _group.getGroupId(), classNameId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Repository A",
			StringPool.BLANK, "Test Portlet", typeSettingsProperties, true,
			serviceContext);

		Object[] objects = setUp(
			_group.getGroupId(), StringPool.BLANK, serviceContext);

		_defaultRepositoryFolder = (Folder)objects[0];
		_defaultRepositoryDLFileVersion = (DLFileVersion)objects[1];

		objects = setUp(
			_repository.getRepositoryId(), "-NewRepository", serviceContext);

		_newRepositoryFolder = (Folder)objects[0];
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_group);
	}

	@Test
	public void testCountByExtraSettings() throws Exception {
		Assert.assertEquals(
			4, DLFileEntryLocalServiceUtil.getExtraSettingsFileEntriesCount());
	}

	@Test
	public void testCountByG_U_F_M_StatusAny() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(3, doCountBy_G_U_F_M(0, null, queryDefinition));
	}

	@Test
	public void testCountByG_U_F_M_StatusAnyByMimeType() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			2, doCountBy_G_U_F_M(0, ContentTypes.TEXT_PLAIN, queryDefinition));
	}

	@Test
	public void testCountByG_U_F_M_StatusAnyByUserId() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			2,
			doCountBy_G_U_F_M(
				_defaultRepositoryFolder.getUserId(), null, queryDefinition));
	}

	@Test
	public void testCountByG_U_F_M_StatusAnyByUserIdAndMimeType()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			1,
			doCountBy_G_U_F_M(
				_defaultRepositoryFolder.getUserId(), ContentTypes.TEXT_PLAIN,
				queryDefinition));
	}

	@Test
	public void testCountByG_U_F_M_StatusApproved() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(2, doCountBy_G_U_F_M(0, null, queryDefinition));
	}

	@Test
	public void testCountByG_U_F_M_StatusApprovedByMimeType() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			1,
			doCountBy_G_U_F_M(0, ContentTypes.TEXT_PLAIN, queryDefinition));
	}

	@Test
	public void testCountByG_U_F_M_StatusApprovedByMimeType_BothRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			2,
			doCountBy_G_U_R_F_M_BothRepositories(
				0, ContentTypes.TEXT_PLAIN, queryDefinition));
	}

	@Test
	public void testCountByG_U_F_M_StatusApprovedByMimeType_DefaultRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			1,
			doCountBy_G_U_R_F_M_DefaultRepository(
				0, ContentTypes.TEXT_PLAIN, queryDefinition));
	}

	@Test
	public void testCountByG_U_F_M_StatusApprovedByMimeType_EmptyRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			2,
			doCountBy_G_U_R_F_M_EmptyRepositories(
				0, ContentTypes.TEXT_PLAIN, queryDefinition));
	}

	@Test
	public void testCountByG_U_F_M_StatusApprovedByMimeType_NewRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			1,
			doCountBy_G_U_R_F_M_NewRepository(
				0, ContentTypes.TEXT_PLAIN, queryDefinition));
	}

	@Test
	public void testCountByG_U_F_M_StatusApprovedByUserId() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			1,
			doCountBy_G_U_F_M(
				_defaultRepositoryFolder.getUserId(), null, queryDefinition));
	}

	@Test
	public void testCountByG_U_F_M_StatusApprovedByUserIdAndMimeType()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			0,
			doCountBy_G_U_F_M(
				_defaultRepositoryFolder.getUserId(), ContentTypes.TEXT_PLAIN,
				queryDefinition));
	}

	@Test
	public void testCountByG_U_F_M_StatusInTrash() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(2, doCountBy_G_U_F_M(0, null, queryDefinition));
	}

	@Test
	public void testCountByG_U_F_M_StatusInTrashByMimeType() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			1,
			doCountBy_G_U_F_M(0, ContentTypes.TEXT_PLAIN, queryDefinition));
	}

	@Test
	public void testCountByG_U_F_M_StatusInTrashByUserId() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			1,
			doCountBy_G_U_F_M(
				_defaultRepositoryFolder.getUserId(), null, queryDefinition));
	}

	@Test
	public void testCountByG_U_F_M_StatusInTrashByUserIdAndMimeType()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			0,
			doCountBy_G_U_F_M(
				_defaultRepositoryFolder.getUserId(), ContentTypes.TEXT_PLAIN,
				queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusAny_BothRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			6, doCountBy_G_U_R_F_M_BothRepositories(0, null, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusAny_DefaultRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			3, doCountBy_G_U_R_F_M_DefaultRepository(0, null, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusAny_EmptyRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			6, doCountBy_G_U_R_F_M_EmptyRepositories(0, null, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusAny_NewRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			3, doCountBy_G_U_R_F_M_NewRepository(0, null, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusAnyByMimeType_BothRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			4,
			doCountBy_G_U_R_F_M_BothRepositories(
				0, ContentTypes.TEXT_PLAIN, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusAnyByMimeType_DefaultRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			2,
			doCountBy_G_U_R_F_M_DefaultRepository(
				0, ContentTypes.TEXT_PLAIN, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusAnyByMimeType_EmptyRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			4,
			doCountBy_G_U_R_F_M_EmptyRepositories(
				0, ContentTypes.TEXT_PLAIN, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusAnyByMimeType_NewRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			2,
			doCountBy_G_U_R_F_M_NewRepository(
				0, ContentTypes.TEXT_PLAIN, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusAnyByUserId_BothRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			4,
			doCountBy_G_U_R_F_M_BothRepositories(
				_defaultRepositoryFolder.getUserId(), null, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusAnyByUserId_DefaultRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			2,
			doCountBy_G_U_R_F_M_DefaultRepository(
				_defaultRepositoryFolder.getUserId(), null, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusAnyByUserId_EmptyRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			4,
			doCountBy_G_U_R_F_M_EmptyRepositories(
				_defaultRepositoryFolder.getUserId(), null, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusAnyByUserId_NewRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			2,
			doCountBy_G_U_R_F_M_NewRepository(
				_defaultRepositoryFolder.getUserId(), null, queryDefinition));
	}

	@Test
	public void
	testCountByG_U_R_F_M_StatusAnyByUserIdAndMimeType_BothRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			2,
			doCountBy_G_U_R_F_M_BothRepositories(
				_defaultRepositoryFolder.getUserId(), ContentTypes.TEXT_PLAIN,
				queryDefinition));
	}

	@Test
	public void
	testCountByG_U_R_F_M_StatusAnyByUserIdAndMimeType_DefaultRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			1,
			doCountBy_G_U_R_F_M_DefaultRepository(
				_defaultRepositoryFolder.getUserId(), ContentTypes.TEXT_PLAIN,
				queryDefinition));
	}

	@Test
	public void
	testCountByG_U_R_F_M_StatusAnyByUserIdAndMimeType_EmptyRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			2,
			doCountBy_G_U_R_F_M_EmptyRepositories(
				_defaultRepositoryFolder.getUserId(), ContentTypes.TEXT_PLAIN,
				queryDefinition));
	}

	@Test
	public void
	testCountByG_U_R_F_M_StatusAnyByUserIdAndMimeType_NewRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			1,
			doCountBy_G_U_R_F_M_NewRepository(
				_defaultRepositoryFolder.getUserId(), ContentTypes.TEXT_PLAIN,
				queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusApproved_BothRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			4, doCountBy_G_U_R_F_M_BothRepositories(0, null, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusApproved_DefaultRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			2, doCountBy_G_U_R_F_M_DefaultRepository(0, null, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusApproved_EmptyRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			4, doCountBy_G_U_R_F_M_EmptyRepositories(0, null, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusApproved_NewRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			2, doCountBy_G_U_R_F_M_NewRepository(0, null, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusApprovedByUserId_BothRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			2,
			doCountBy_G_U_R_F_M_BothRepositories(
				_defaultRepositoryFolder.getUserId(), null, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusApprovedByUserId_DefaultRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			1,
			doCountBy_G_U_R_F_M_DefaultRepository(
				_defaultRepositoryFolder.getUserId(), null, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusApprovedByUserId_EmptyRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			2,
			doCountBy_G_U_R_F_M_EmptyRepositories(
				_defaultRepositoryFolder.getUserId(), null, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusApprovedByUserId_NewRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			1,
			doCountBy_G_U_R_F_M_NewRepository(
				_defaultRepositoryFolder.getUserId(), null, queryDefinition));
	}

	@Test
	public void
	testCountByG_U_R_F_M_StatusApprovedByUserIdAndMimeType_BothRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			0,
			doCountBy_G_U_R_F_M_BothRepositories(
				_defaultRepositoryFolder.getUserId(), ContentTypes.TEXT_PLAIN,
				queryDefinition));
	}

	@Test
	public void
	testCountByG_U_R_F_M_StatusApprovedByUserIdAndMimeType_DefaultRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			0,
			doCountBy_G_U_R_F_M_DefaultRepository(
				_defaultRepositoryFolder.getUserId(), ContentTypes.TEXT_PLAIN,
				queryDefinition));
	}

	@Test
	public void
	testCountByG_U_R_F_M_StatusApprovedByUserIdAndMimeType_EmptyRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			0,
			doCountBy_G_U_R_F_M_EmptyRepositories(
				_defaultRepositoryFolder.getUserId(), ContentTypes.TEXT_PLAIN,
				queryDefinition));
	}

	@Test
	public void
	testCountByG_U_R_F_M_StatusApprovedByUserIdAndMimeType_NewRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			0,
			doCountBy_G_U_R_F_M_NewRepository(
				_defaultRepositoryFolder.getUserId(), ContentTypes.TEXT_PLAIN,
				queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusInTrash_BothRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			4, doCountBy_G_U_R_F_M_BothRepositories(0, null, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusInTrash_DefaultRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			2, doCountBy_G_U_R_F_M_DefaultRepository(0, null, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusInTrash_EmptyRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			4, doCountBy_G_U_R_F_M_EmptyRepositories(0, null, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusInTrash_NewRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			2, doCountBy_G_U_R_F_M_NewRepository(0, null, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusInTrashByMimeType_BothRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			2,
			doCountBy_G_U_R_F_M_BothRepositories(
				0, ContentTypes.TEXT_PLAIN, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusInTrashByMimeType_DefaultRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			1,
			doCountBy_G_U_R_F_M_DefaultRepository(
				0, ContentTypes.TEXT_PLAIN, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusInTrashByMimeType_EmptyRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			2,
			doCountBy_G_U_R_F_M_EmptyRepositories(
				0, ContentTypes.TEXT_PLAIN, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusInTrashByMimeType_NewRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			1,
			doCountBy_G_U_R_F_M_NewRepository(
				0, ContentTypes.TEXT_PLAIN, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusInTrashByUserId_BothRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			2,
			doCountBy_G_U_R_F_M_BothRepositories(
				_defaultRepositoryFolder.getUserId(), null, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusInTrashByUserId_DefaultRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			1,
			doCountBy_G_U_R_F_M_DefaultRepository(
				_defaultRepositoryFolder.getUserId(), null, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusInTrashByUserId_EmptyRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			2,
			doCountBy_G_U_R_F_M_EmptyRepositories(
				_defaultRepositoryFolder.getUserId(), null, queryDefinition));
	}

	@Test
	public void testCountByG_U_R_F_M_StatusInTrashByUserId_NewRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			1,
			doCountBy_G_U_R_F_M_NewRepository(
				_defaultRepositoryFolder.getUserId(), null, queryDefinition));
	}

	@Test
	public void
	testCountByG_U_R_F_M_StatusInTrashByUserIdAndMimeType_BothRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			0,
			doCountBy_G_U_R_F_M_BothRepositories(
				_defaultRepositoryFolder.getUserId(), ContentTypes.TEXT_PLAIN,
				queryDefinition));
	}

	@Test
	public void
	testCountByG_U_R_F_M_StatusInTrashByUserIdAndMimeType_DefaultRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			0,
			doCountBy_G_U_R_F_M_DefaultRepository(
				_defaultRepositoryFolder.getUserId(), ContentTypes.TEXT_PLAIN,
				queryDefinition));
	}

	@Test
	public void
	testCountByG_U_R_F_M_StatusInTrashByUserIdAndMimeType_EmptyRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			0,
			doCountBy_G_U_R_F_M_EmptyRepositories(
				_defaultRepositoryFolder.getUserId(), ContentTypes.TEXT_PLAIN,
				queryDefinition));
	}

	@Test
	public void
	testCountByG_U_R_F_M_StatusInTrashByUserIdAndMimeType_NewRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			0,
			doCountBy_G_U_R_F_M_NewRepository(
				_defaultRepositoryFolder.getUserId(), ContentTypes.TEXT_PLAIN,
				queryDefinition));
	}

	@Test
	public void testFindByAnyImageId() throws Exception {
		DLFileEntry dlFileEntry =
			DLFileEntryLocalServiceUtil.fetchFileEntryByAnyImageId(
				_SMALL_IMAGE_ID);

		Assert.assertEquals("FE1.txt", dlFileEntry.getTitle());
	}

	@Test
	public void testFindByG_U_F_M_StatusAny() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		List<DLFileEntry> dlFileEntries = doFindBy_G_U_F_M(
			_defaultRepositoryFolder.getUserId(), ContentTypes.TEXT_PLAIN,
			queryDefinition);

		Assert.assertEquals(1, dlFileEntries.size());

		DLFileEntry dlFileEntry = dlFileEntries.get(0);

		Assert.assertEquals("FE3.txt", dlFileEntry.getDescription());
	}

	@Test
	public void testFindByG_U_F_M_StatusAny_BothRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		List<DLFileEntry> dlFileEntries = doFindBy_G_U_R_F_M_BothRepositories(
			_defaultRepositoryFolder.getUserId(), ContentTypes.TEXT_PLAIN,
			queryDefinition);

		Assert.assertEquals(2, dlFileEntries.size());
	}

	@Test
	public void testFindByG_U_F_M_StatusAny_DefaultRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		List<DLFileEntry> dlFileEntries = doFindBy_G_U_R_F_M_DefaultRepository(
			_defaultRepositoryFolder.getUserId(), ContentTypes.TEXT_PLAIN,
			queryDefinition);

		Assert.assertEquals(1, dlFileEntries.size());

		DLFileEntry dlFileEntry = dlFileEntries.get(0);

		Assert.assertEquals("FE3.txt", dlFileEntry.getDescription());
	}

	@Test
	public void testFindByG_U_F_M_StatusAny_EmptyRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		List<DLFileEntry> dlFileEntries = doFindBy_G_U_R_F_M_EmptyRepositories(
			_defaultRepositoryFolder.getUserId(), ContentTypes.TEXT_PLAIN,
			queryDefinition);

		Assert.assertEquals(2, dlFileEntries.size());
	}

	@Test
	public void testFindByG_U_F_M_StatusAny_NewRepository() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		List<DLFileEntry> dlFileEntries = doFindBy_G_U_R_F_M_NewRepository(
			_newRepositoryFolder.getUserId(), ContentTypes.TEXT_PLAIN,
			queryDefinition);

		Assert.assertEquals(1, dlFileEntries.size());

		DLFileEntry dlFileEntry = dlFileEntries.get(0);

		Assert.assertEquals("FE3.txt", dlFileEntry.getDescription());
	}

	@Test
	public void testFindByG_U_F_M_StatusApproved() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		List<DLFileEntry> dlFileEntries = doFindBy_G_U_F_M(
			_defaultRepositoryFolder.getUserId(), null, queryDefinition);

		Assert.assertEquals(1, dlFileEntries.size());

		DLFileEntry dlFileEntry = dlFileEntries.get(0);

		Assert.assertEquals("FE2.pdf", dlFileEntry.getTitle());
	}

	@Test
	public void testFindByG_U_F_M_StatusApproved_BothRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		List<DLFileEntry> dlFileEntries = doFindBy_G_U_R_F_M_BothRepositories(
			_defaultRepositoryFolder.getUserId(), null, queryDefinition);

		Assert.assertEquals(2, dlFileEntries.size());
	}

	@Test
	public void testFindByG_U_F_M_StatusApproved_DefaultRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		List<DLFileEntry> dlFileEntries = doFindBy_G_U_R_F_M_DefaultRepository(
			_defaultRepositoryFolder.getUserId(), null, queryDefinition);

		Assert.assertEquals(1, dlFileEntries.size());

		DLFileEntry dlFileEntry = dlFileEntries.get(0);

		Assert.assertEquals("FE2.pdf", dlFileEntry.getTitle());
	}

	@Test
	public void testFindByG_U_F_M_StatusApproved_EmptyRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		List<DLFileEntry> dlFileEntries = doFindBy_G_U_R_F_M_EmptyRepositories(
			_defaultRepositoryFolder.getUserId(), null, queryDefinition);

		Assert.assertEquals(2, dlFileEntries.size());
	}

	@Test
	public void testFindByG_U_F_M_StatusApproved_NewRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		List<DLFileEntry> dlFileEntries = doFindBy_G_U_R_F_M_NewRepository(
			_newRepositoryFolder.getUserId(), null, queryDefinition);

		Assert.assertEquals(1, dlFileEntries.size());

		DLFileEntry dlFileEntry = dlFileEntries.get(0);

		Assert.assertEquals("FE2.pdf-NewRepository", dlFileEntry.getTitle());
	}

	@Test
	public void testFindByG_U_F_M_StatusInTrash() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		List<DLFileEntry> dlFileEntries = doFindBy_G_U_F_M(
			0, ContentTypes.TEXT_PLAIN, queryDefinition);

		Assert.assertEquals(1, dlFileEntries.size());

		DLFileEntry dlFileEntry = dlFileEntries.get(0);

		Assert.assertEquals("FE1.txt", dlFileEntry.getTitle());
	}

	@Test
	public void testFindByG_U_F_M_StatusInTrash_BothRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		List<DLFileEntry> dlFileEntries = doFindBy_G_U_R_F_M_BothRepositories(
			0, ContentTypes.TEXT_PLAIN, queryDefinition);

		Assert.assertEquals(2, dlFileEntries.size());
	}

	@Test
	public void testFindByG_U_F_M_StatusInTrash_DefaultRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		List<DLFileEntry> dlFileEntries = doFindBy_G_U_R_F_M_DefaultRepository(
			0, ContentTypes.TEXT_PLAIN, queryDefinition);

		Assert.assertEquals(1, dlFileEntries.size());

		DLFileEntry dlFileEntry = dlFileEntries.get(0);

		Assert.assertEquals("FE1.txt", dlFileEntry.getTitle());
	}

	@Test
	public void testFindByG_U_F_M_StatusInTrash_EmptyRepositories()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		List<DLFileEntry> dlFileEntries = doFindBy_G_U_R_F_M_EmptyRepositories(
			0, ContentTypes.TEXT_PLAIN, queryDefinition);

		Assert.assertEquals(2, dlFileEntries.size());
	}

	@Test
	public void testFindByG_U_F_M_StatusInTrash_NewRepository()
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		List<DLFileEntry> dlFileEntries = doFindBy_G_U_R_F_M_NewRepository(
			0, ContentTypes.TEXT_PLAIN, queryDefinition);

		Assert.assertEquals(1, dlFileEntries.size());

		DLFileEntry dlFileEntry = dlFileEntries.get(0);

		Assert.assertEquals("FE1.txt-NewRepository", dlFileEntry.getTitle());
	}

	@Test
	public void testFindByMisversioned() throws Exception {
		long oldFileEntryId =  _defaultRepositoryDLFileVersion.getFileEntryId();

		try {
			_defaultRepositoryDLFileVersion.setFileEntryId(
				RandomTestUtil.randomLong());

			DLFileVersionLocalServiceUtil.updateDLFileVersion(
				_defaultRepositoryDLFileVersion);

			List<DLFileEntry> dlFileEntries =
				DLFileEntryLocalServiceUtil.getMisversionedFileEntries();

			Assert.assertEquals(1, dlFileEntries.size());

			DLFileEntry dlFileEntry = dlFileEntries.get(0);

			Assert.assertEquals("FE1.txt", dlFileEntry.getTitle());
		}
		finally {
			_defaultRepositoryDLFileVersion.setFileEntryId(oldFileEntryId);

			DLFileVersionLocalServiceUtil.updateDLFileVersion(
				_defaultRepositoryDLFileVersion);
		}
	}

	@Test
	public void testFindByNoAssets() throws Exception {
		AssetEntryLocalServiceUtil.deleteEntry(
			DLFileEntry.class.getName(),
			_defaultRepositoryDLFileVersion.getFileEntryId());

		LastSessionRecorderUtil.syncLastSessionState();

		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.getNoAssetFileEntries();

		Assert.assertEquals(1, dlFileEntries.size());

		DLFileEntry dlFileEntry = dlFileEntries.get(0);

		Assert.assertEquals("FE1.txt", dlFileEntry.getTitle());
	}

	protected int doCountBy_G_U_F_M(
			long userId, String mimeType, QueryDefinition queryDefinition)
		throws Exception {

		List<Long> folderIds = ListUtil.toList(
			new long[] {_defaultRepositoryFolder.getFolderId()});

		String[] mimeTypes = null;

		if (mimeType != null) {
			mimeTypes = new String[] {mimeType};
		}

		return DLFileEntryLocalServiceUtil.getFileEntriesCount(
			_group.getGroupId(), userId, folderIds, mimeTypes, queryDefinition);
	}

	protected int doCountBy_G_U_R_F_M_NewRepository(
			long userId, String mimeType, QueryDefinition queryDefinition)
		throws Exception {

		List<Long> repositoryIds = ListUtil.toList(
			new long[] {_newRepositoryFolder.getRepositoryId()});
		List<Long> folderIds = ListUtil.toList(
			new long[] {_newRepositoryFolder.getFolderId()});

		return doCountBy_G_U_R_F_M(
			userId, repositoryIds, folderIds, mimeType, queryDefinition);
	}

	protected int doCountBy_G_U_R_F_M_DefaultRepository(
			long userId, String mimeType, QueryDefinition queryDefinition)
		throws Exception {

		List<Long> repositoryIds = ListUtil.toList(
			new long[] {_defaultRepositoryFolder.getRepositoryId()});
		List<Long> folderIds = ListUtil.toList(
			new long[] {_defaultRepositoryFolder.getFolderId()});

		return doCountBy_G_U_R_F_M(
			userId, repositoryIds, folderIds, mimeType, queryDefinition);
	}

	protected int doCountBy_G_U_R_F_M_BothRepositories(
			long userId, String mimeType, QueryDefinition queryDefinition)
		throws Exception {

		List<Long> repositoryIds = ListUtil.toList(
			new long[] {
				_defaultRepositoryFolder.getRepositoryId(),
				_newRepositoryFolder.getRepositoryId()
			});
		List<Long> folderIds = ListUtil.toList(
			new long[] {
				_defaultRepositoryFolder.getFolderId(),
				_newRepositoryFolder.getFolderId(),
			});

		return doCountBy_G_U_R_F_M(
			userId, repositoryIds, folderIds, mimeType, queryDefinition);
	}

	protected int doCountBy_G_U_R_F_M_EmptyRepositories(
			long userId, String mimeType, QueryDefinition queryDefinition)
		throws Exception {

		List<Long> repositoryIds = new ArrayList<Long>();
		List<Long> folderIds = new ArrayList<Long>();

		return doCountBy_G_U_R_F_M(
			userId, repositoryIds, folderIds, mimeType, queryDefinition);
	}

	protected int doCountBy_G_U_R_F_M(
			long userId, List<Long> repositoryIds, List<Long> folderIds,
			String mimeType, QueryDefinition queryDefinition)
		throws Exception {

		String[] mimeTypes = null;

		if (mimeType != null) {
			mimeTypes = new String[] {mimeType};
		}

		return DLFileEntryLocalServiceUtil.getFileEntriesCount(
			_group.getGroupId(), userId, repositoryIds, folderIds, mimeTypes,
			queryDefinition);
	}

	protected List<DLFileEntry> doFindBy_G_U_F_M(
			long userId, String mimeType, QueryDefinition queryDefinition)
		throws Exception {

		List<Long> folderIds = ListUtil.toList(
			new long[] {_defaultRepositoryFolder.getFolderId()});

		String[] mimeTypes = null;

		if (mimeType != null) {
			mimeTypes = new String[] {mimeType};
		}

		return DLFileEntryLocalServiceUtil.getFileEntries(
			_group.getGroupId(), userId, folderIds, mimeTypes, queryDefinition);
	}

	protected List<DLFileEntry> doFindBy_G_U_R_F_M_NewRepository(
			long userId, String mimeType, QueryDefinition queryDefinition)
		throws Exception {

		List<Long> repositoryIds = ListUtil.toList(
			new long[] {_newRepositoryFolder.getRepositoryId()});
		List<Long> folderIds = ListUtil.toList(
			new long[] {_newRepositoryFolder.getFolderId()});

		return doFindBy_G_U_R_F_M(
			userId, repositoryIds, folderIds, mimeType, queryDefinition);
	}

	protected List<DLFileEntry> doFindBy_G_U_R_F_M_DefaultRepository(
			long userId, String mimeType, QueryDefinition queryDefinition)
		throws Exception {

		List<Long> repositoryIds = ListUtil.toList(
			new long[] {_defaultRepositoryFolder.getRepositoryId()});
		List<Long> folderIds = ListUtil.toList(
			new long[] {_defaultRepositoryFolder.getFolderId()});

		return doFindBy_G_U_R_F_M(
			userId, repositoryIds, folderIds, mimeType, queryDefinition);
	}

	protected List<DLFileEntry> doFindBy_G_U_R_F_M_BothRepositories(
			long userId, String mimeType, QueryDefinition queryDefinition)
		throws Exception {

		List<Long> repositoryIds = ListUtil.toList(
			new long[] {
				_defaultRepositoryFolder.getRepositoryId(),
				_newRepositoryFolder.getRepositoryId()
			});
		List<Long> folderIds = ListUtil.toList(
			new long[] {
				_defaultRepositoryFolder.getFolderId(),
				_newRepositoryFolder.getFolderId(),
			});

		return doFindBy_G_U_R_F_M(
			userId, repositoryIds, folderIds, mimeType, queryDefinition);
	}

	protected List<DLFileEntry> doFindBy_G_U_R_F_M_EmptyRepositories(
			long userId, String mimeType, QueryDefinition queryDefinition)
		throws Exception {

		List<Long> repositoryIds = new ArrayList<Long>();
		List<Long> folderIds = new ArrayList<Long>();

		return doFindBy_G_U_R_F_M(
			userId, repositoryIds, folderIds, mimeType, queryDefinition);
	}

	protected List<DLFileEntry> doFindBy_G_U_R_F_M(
			long userId, List<Long> repositoryIds, List<Long> folderIds,
			String mimeType, QueryDefinition queryDefinition)
		throws Exception {

		String[] mimeTypes = null;

		if (mimeType != null) {
			mimeTypes = new String[] {mimeType};
		}

		return DLFileEntryLocalServiceUtil.getFileEntries(
			_group.getGroupId(), userId, repositoryIds, folderIds, mimeTypes,
			queryDefinition);
	}

	protected Object[] setUp(
			long repositoryId, String titleSuffix,
			ServiceContext serviceContext)
		throws Exception {

		Folder folder = DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), repositoryId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Folder A",
			StringPool.BLANK, serviceContext);

		DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), repositoryId, folder.getFolderId(),
			"Folder B", StringPool.BLANK, serviceContext);

		Folder folderC = DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), repositoryId, folder.getFolderId(),
			"Folder C", StringPool.BLANK, serviceContext);

		DLAppServiceUtil.moveFolderToTrash(folderC.getFolderId());

		User user = UserTestUtil.addUser(
			RandomTestUtil.randomString(), _group.getGroupId());

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			user.getUserId(), _group.getGroupId(), repositoryId,
			folder.getFolderId(), "FE1.txt", ContentTypes.TEXT_PLAIN,
			"FE1.txt".concat(titleSuffix), null,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			WorkflowConstants.ACTION_PUBLISH);

		LiferayFileEntry liferayFileEntry = (LiferayFileEntry)fileEntry;

		DLFileEntry dlFileEntry = liferayFileEntry.getDLFileEntry();

		dlFileEntry.setExtraSettings("hello=world");
		dlFileEntry.setSmallImageId(_SMALL_IMAGE_ID);

		dlFileEntry = DLFileEntryLocalServiceUtil.updateDLFileEntry(
			dlFileEntry);

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		DLAppTestUtil.addFileEntry(
			_group.getGroupId(), repositoryId, folder.getFolderId(), "FE2.pdf",
			ContentTypes.APPLICATION_PDF, "FE2.pdf".concat(titleSuffix), null,
			WorkflowConstants.ACTION_PUBLISH);

		fileEntry = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), repositoryId, folder.getFolderId(), "FE3.txt",
			ContentTypes.TEXT_PLAIN, "FE3.txt".concat(titleSuffix), null,
			WorkflowConstants.ACTION_PUBLISH);

		fileEntry = DLAppTestUtil.updateFileEntry(
			_group.getGroupId(), fileEntry.getFileEntryId(), "FE3.txt",
			"FE3.txt".concat(titleSuffix));

		dlFileEntry = ((LiferayFileEntry)fileEntry).getDLFileEntry();

		dlFileEntry.setDescription("FE3.txt");

		DLFileEntryLocalServiceUtil.updateDLFileEntry(dlFileEntry);

		DLFileVersion dlFileVersion3 = dlFileEntry.getFileVersion();

		dlFileVersion3.setExtraSettings("hello=world");

		DLFileVersionLocalServiceUtil.updateDLFileVersion(dlFileVersion3);

		DLAppServiceUtil.moveFileEntryToTrash(fileEntry.getFileEntryId());

		return new Object[] {folder, dlFileVersion};
	}

	private static final long _SMALL_IMAGE_ID = 1234L;

	private DLFileVersion _defaultRepositoryDLFileVersion;
	private Folder _defaultRepositoryFolder;
	private Group _group;
	private Folder _newRepositoryFolder;
	private Repository _repository;

}