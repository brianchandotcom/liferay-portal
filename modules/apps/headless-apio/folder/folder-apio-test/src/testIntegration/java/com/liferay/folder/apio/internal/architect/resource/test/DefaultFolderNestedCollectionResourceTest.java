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

package com.liferay.folder.apio.internal.architect.resource.test;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.apio.test.util.PaginationRequest;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import java.util.Collection;
import java.util.Iterator;

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
public class DefaultFolderNestedCollectionResourceTest
	extends BaseFolderNestedCollectionResourceTest {

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
	public void testAddFolder() throws Exception {
		Folder folder = addFolder(
			_group.getGroupId(),
			new FolderImpl("My folder testAddFolder", "My folder description"));

		Assert.assertEquals("My folder description", folder.getDescription());
		Assert.assertEquals("My folder testAddFolder", folder.getName());
	}

	@Test
	public void testGetPageItems() throws Exception {
		addFolder(
			_group.getGroupId(),
			new FolderImpl(
				"My folder testGetPageItems", "My folder description"));

		PageItems<Folder> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId());

		Assert.assertEquals(1, pageItems.getTotalCount());

		Collection<Folder> dlFolders = pageItems.getItems();

		Iterator<Folder> iterator = dlFolders.iterator();

		Folder dlFolder = iterator.next();

		Assert.assertEquals("My folder description", dlFolder.getDescription());
		Assert.assertEquals("My folder testGetPageItems", dlFolder.getName());
	}

	@Test
	public void testUpdateFolder() throws Exception {
		Folder folder = addFolder(
			_group.getGroupId(),
			new FolderImpl(
				"My folder testUpdateFolder", "My folder description"));

		Folder updatedFolder = updateFolder(
			folder.getFolderId(),
			new FolderImpl(
				"My folder testUpdateFolder updated",
				"My folder description updated"));

		Assert.assertEquals(
			"My folder description updated", updatedFolder.getDescription());
		Assert.assertEquals(
			"My folder testUpdateFolder updated", updatedFolder.getName());
	}

	@DeleteAfterTestRun
	private Group _group;

	private static class FolderImpl
		implements com.liferay.folder.apio.architect.model.Folder {

		@Override
		public String getDescription() {
			return _description;
		}

		@Override
		public String getName() {
			return _name;
		}

		private FolderImpl(String name, String description) {
			_name = name;
			_description = description;
		}

		private final String _description;
		private final String _name;

	}

}