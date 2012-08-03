/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.attachments;

import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.trash.BaseDLTrashHandlerTestCase;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageConstants;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@ExecutionTestListeners(
	listeners = {
			EnvironmentExecutionTestListener.class,
			TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class AttachmentsTrashTest extends BaseDLTrashHandlerTestCase {

	@Test
	@Transactional
	public void testTrash() throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(TestPropsValues.getGroupId());

		WikiNode wikiNode = WikiNodeLocalServiceUtil.addNode(
			TestPropsValues.getUserId(), ServiceTestUtil.randomString(), "",
			serviceContext);

		WikiPage wikiPage = WikiPageLocalServiceUtil.addPage(
			TestPropsValues.getUserId(), wikiNode.getNodeId(),
			ServiceTestUtil.randomString(), null, WikiPageConstants.NEW, true,
			serviceContext);

		int initialNotInTrashCount = wikiPage.getAttachmentsFiles().length;
		int initialTrashEntriesCount =
			wikiPage.getDeletedAttachmentsFiles().length;

		Class<?> clazz = getClass();

		byte[] fileBytes = FileUtil.getBytes(
			clazz.getResourceAsStream(_OFFICE_TEST_DOCX));

		File file = null;

		if ((fileBytes != null) && (fileBytes.length > 0)) {
			file = FileUtil.createTempFile(fileBytes);
		}

		WikiPageLocalServiceUtil.addPageAttachment(
			TestPropsValues.getUserId(), wikiNode.getNodeId(),
			wikiPage.getTitle(), "Test File.txt", file);

		Assert.assertEquals(
			initialTrashEntriesCount,
			wikiPage.getDeletedAttachmentsFiles().length);

		Assert.assertEquals(
			initialNotInTrashCount + 1, wikiPage.getAttachmentsFiles().length);

		String fileName = wikiPage.getAttachmentsFiles()[0];

		WikiPageLocalServiceUtil.movePageAttachmentToTrash(
			wikiNode.getNodeId(), wikiPage.getTitle(), fileName);

		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			wikiPage.getDeletedAttachmentsFiles().length);

		Assert.assertEquals(
			initialNotInTrashCount, wikiPage.getAttachmentsFiles().length);

		fileName = wikiPage.getDeletedAttachmentsFiles()[0];

		WikiPageLocalServiceUtil.movePageAttachmentFromTrash(
			wikiNode.getNodeId(), wikiPage.getTitle(), fileName);

		Assert.assertEquals(
			initialTrashEntriesCount,
			wikiPage.getDeletedAttachmentsFiles().length);

		Assert.assertEquals(
			initialNotInTrashCount + 1, wikiPage.getAttachmentsFiles().length);
	}

	private static final String _OFFICE_TEST_DOCX =
		"dependencies/OSX_Test.docx";

}