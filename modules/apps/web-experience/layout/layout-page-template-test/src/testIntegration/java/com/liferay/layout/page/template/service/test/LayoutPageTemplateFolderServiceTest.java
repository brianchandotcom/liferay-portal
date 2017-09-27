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

package com.liferay.layout.page.template.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.exception.DuplicateLayoutPageTemplateFolderException;
import com.liferay.layout.page.template.exception.LayoutPageTemplateFolderNameException;
import com.liferay.layout.page.template.model.LayoutPageTemplateFolder;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateFolderServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
@Sync
public class LayoutPageTemplateFolderServiceTest {

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

	@Test(expected = DuplicateLayoutPageTemplateFolderException.class)
	public void testAddDuplicateLayoutPageTemplateFolders() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateFolderServiceUtil.addLayoutPageTemplateFolder(
			_group.getGroupId(), "Layout Page Template Folder", null,
			serviceContext);

		LayoutPageTemplateFolderServiceUtil.addLayoutPageTemplateFolder(
			_group.getGroupId(), "Layout Page Template Folder", null,
			serviceContext);
	}

	@Test
	public void testAddLayoutPageTemplateFolder() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateFolder layoutPageTemplateFolder =
			LayoutPageTemplateFolderServiceUtil.addLayoutPageTemplateFolder(
				_group.getGroupId(), "Layout Page Template Folder", null,
				serviceContext);

		Assert.assertEquals(
			"Layout Page Template Folder", layoutPageTemplateFolder.getName());
	}

	@Test(expected = LayoutPageTemplateFolderNameException.class)
	public void testAddLayoutPageTemplateFolderWithEmptyName()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateFolderServiceUtil.addLayoutPageTemplateFolder(
			_group.getGroupId(), StringPool.BLANK, null, serviceContext);
	}

	@Test(expected = LayoutPageTemplateFolderNameException.class)
	public void testAddLayoutPageTemplateFolderWithNullName() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateFolderServiceUtil.addLayoutPageTemplateFolder(
			_group.getGroupId(), null, null, serviceContext);
	}

	@Test
	public void testAddMultipleLayoutPageTemplateFolders()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		int originalLayoutPageTemplateFoldersCount =
			LayoutPageTemplateFolderServiceUtil.
				getLayoutPageTemplateFoldersCount(_group.getGroupId());

		LayoutPageTemplateFolderServiceUtil.addLayoutPageTemplateFolder(
			_group.getGroupId(), "Layout Page Template Folder 1",
			StringPool.BLANK, serviceContext);

		LayoutPageTemplateFolderServiceUtil.addLayoutPageTemplateFolder(
			_group.getGroupId(), "Layout Page Template Folder 2",
			StringPool.BLANK, serviceContext);

		int actualLayoutPageTemplateFoldersCount =
			LayoutPageTemplateFolderServiceUtil.
				getLayoutPageTemplateFoldersCount(_group.getGroupId());

		Assert.assertEquals(
			originalLayoutPageTemplateFoldersCount + 2,
			actualLayoutPageTemplateFoldersCount);
	}

	@Test
	public void testDeleteLayoutPageTemplate() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateFolder layoutPageTemplateFolder =
			LayoutPageTemplateFolderServiceUtil.addLayoutPageTemplateFolder(
				_group.getGroupId(), "Layout Page Template Folder", null,
				serviceContext);

		LayoutPageTemplateFolderServiceUtil.deleteLayoutPageTemplateFolder(
			layoutPageTemplateFolder.getLayoutPageTemplateFolderId());

		Assert.assertNull(
			LayoutPageTemplateFolderServiceUtil.fetchLayoutPageTemplateFolder(
				layoutPageTemplateFolder.getLayoutPageTemplateFolderId()));
	}

	@Test
	public void testLayoutPageTemplateFoldersWithPageTemplates()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateFolder layoutPageTemplateFolder =
			LayoutPageTemplateFolderServiceUtil.addLayoutPageTemplateFolder(
				_group.getGroupId(), "Layout Page Template Folder", null,
				serviceContext);

		LayoutPageTemplateEntryServiceUtil.addLayoutPageTemplateEntry(
			_group.getGroupId(),
			layoutPageTemplateFolder.getLayoutPageTemplateFolderId(),
			"Layout Page Template Entry", null, serviceContext);

		LayoutPageTemplateFolderServiceUtil.deleteLayoutPageTemplateFolder(
			layoutPageTemplateFolder.getLayoutPageTemplateFolderId());

		Assert.assertNull(
			LayoutPageTemplateFolderServiceUtil.fetchLayoutPageTemplateFolder(
				layoutPageTemplateFolder.getLayoutPageTemplateFolderId()));
	}

	@DeleteAfterTestRun
	private Group _group;

}