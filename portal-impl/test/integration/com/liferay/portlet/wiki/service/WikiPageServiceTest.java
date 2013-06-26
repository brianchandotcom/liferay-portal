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

package com.liferay.portlet.wiki.service;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.util.ExpandoTestUtil;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.util.WikiTestUtil;

import java.io.Serializable;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.testng.Assert;

/**
 * @author Manuel de la Peña
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
@Transactional
public class WikiPageServiceTest {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		_group = GroupTestUtil.addGroup();

		_node = WikiTestUtil.addNode(
			TestPropsValues.getUserId(), _group.getGroupId(),
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString(50));
	}

	@Test
	public void testGetPage() throws Exception {
		WikiPage page = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			ServiceTestUtil.randomString(), true);

		WikiPage retrievedPage = WikiPageLocalServiceUtil.getPage(
			page.getResourcePrimKey());

		Assert.assertEquals(page.getPageId(), retrievedPage.getPageId());
	}

	@Test
	public void testRevertWikiPage() throws Exception {
		testRevertWikiPage(false);
	}

	@Test
	public void testRevertWikiPageWithCustomField() throws Exception {
		testRevertWikiPage(true);
	}

	protected void testRevertWikiPage(boolean checkCustomField)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		String originalContent = ServiceTestUtil.randomString();

		WikiPage originalPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(),
			ServiceTestUtil.randomString(), originalContent, true,
			serviceContext);

		ExpandoTable table = null;
		ExpandoColumn column = null;
		ExpandoValue value = null;
		ExpandoBridge expandoBridge = null;

		if (checkCustomField) {
			table = ExpandoTestUtil.addTable(
				PortalUtil.getClassNameId(WikiPage.class),
				ServiceTestUtil.randomString());

			column = ExpandoTestUtil.addColumn(
				table, ServiceTestUtil.randomString(),
				ExpandoColumnConstants.STRING);

			value = ExpandoTestUtil.addValue(
				table, column, originalPage.getPrimaryKey(),
				ServiceTestUtil.randomString());

			expandoBridge = originalPage.getExpandoBridge();

			expandoBridge.addAttribute(
				column.getName(), ExpandoColumnConstants.STRING,
				value.getString());
		}

		StringBuilder sb = new StringBuilder(3);

		sb.append(originalContent);
		sb.append(StringPool.NEW_LINE);
		sb.append("Added second line");

		originalPage.setContent(sb.toString());

		WikiPage updatedWikiPage = WikiTestUtil.updatePage(
			originalPage, TestPropsValues.getUserId(), sb.toString(),
			serviceContext);

		sb = new StringBuilder(3);

		sb.append(updatedWikiPage.getContent());
		sb.append(StringPool.NEW_LINE);
		sb.append("Added third line");

		updatedWikiPage.setContent(sb.toString());

		WikiPage updatedWikiPage2 = WikiTestUtil.updatePage(
			updatedWikiPage, TestPropsValues.getUserId(), sb.toString(),
			serviceContext);

		double originalVersion = originalPage.getVersion();

		WikiPage revertedPage = WikiPageLocalServiceUtil.revertPage(
			TestPropsValues.getUserId(), _node.getNodeId(),
			updatedWikiPage2.getTitle(), originalVersion, serviceContext);

		Assert.assertEquals(originalContent, revertedPage.getContent());

		if (checkCustomField) {
			expandoBridge = revertedPage.getExpandoBridge();

			Map<String, Serializable> attributes =
				expandoBridge.getAttributes();

			if (attributes.isEmpty()) {
				Assert.fail("Expando values have not been reverted with page");
			}

			Assert.assertEquals(
				value.getString(), attributes.get(column.getName()));
		}
	}

	private Group _group;
	private WikiNode _node;

}