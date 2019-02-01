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

package com.liferay.headless.document.library.internal.resource.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.headless.document.library.dto.Folder;
import com.liferay.headless.document.library.internal.resource.test.util.PaginationRequest;
import com.liferay.headless.document.library.resource.FolderResource;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.portal.vulcan.dto.Page;

import java.util.List;

import javax.ws.rs.NotFoundException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class FolderResourceImplTest {

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
	public void testGetDocumentsRepositoryFolderPage() throws Exception {
		_dlAppService.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"My folder name", "My folder description", new ServiceContext());

		Page<Folder> page = _folderResource.getDocumentsRepositoryFolderPage(
			_group.getGroupId(), PaginationRequest.of(10, 1));

		List<Folder> folders = (List<Folder>)page.getItems();

		Assert.assertFalse(folders.isEmpty());

		Folder folder = folders.get(0);

		Assert.assertEquals("My folder description", folder.getDescription());
		Assert.assertEquals("My folder name", folder.getName());
	}

	@Test
	public void testGetDocumentsRepositoryFolderPageNonexistingGroup()
		throws Exception {

		try {
			_folderResource.getDocumentsRepositoryFolderPage(
				99999999999L, PaginationRequest.of(10, 1));
			Assert.fail("Should throw NotFoundException");
		}
		catch (NotFoundException nfe) {
			Assert.assertEquals("HTTP 404 Not Found", nfe.getMessage());
		}
	}

	@Test
	public void testGetFolderFolderPage() throws Exception {
		com.liferay.portal.kernel.repository.model.Folder parentFolder =
			_dlAppService.addFolder(
				_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				RandomTestUtil.randomString(10),
				RandomTestUtil.randomString(10), new ServiceContext());

		_dlAppService.addFolder(
			_group.getGroupId(), parentFolder.getFolderId(), "My folder name",
			"My folder description", new ServiceContext());

		Page<Folder> page = _folderResource.getFolderFolderPage(
			parentFolder.getFolderId(), PaginationRequest.of(10, 1));

		List<Folder> folders = (List<Folder>)page.getItems();

		Assert.assertFalse(folders.isEmpty());

		Folder folder = folders.get(0);

		Assert.assertEquals("My folder description", folder.getDescription());
		Assert.assertEquals("My folder name", folder.getName());
	}

	@Test
	public void testGetFolderFolderPageNonexistingFolder() throws Exception {
		try {
			_folderResource.getFolderFolderPage(
				99999999999L, PaginationRequest.of(10, 1));
			Assert.fail("Should throw NotFoundException");
		}
		catch (NotFoundException nfe) {
			Assert.assertEquals("HTTP 404 Not Found", nfe.getMessage());
		}
	}

	@Inject
	private DLAppService _dlAppService;

	@Inject
	private FolderResource _folderResource;

	@DeleteAfterTestRun
	private Group _group;

}