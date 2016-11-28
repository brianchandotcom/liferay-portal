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

package com.liferay.wiki.util.test;

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageConstants;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;

import java.io.File;
import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Julio Camarero
 * @author Roberto Díaz
 */
public class WikiTestUtil {

	public static WikiNode addNode(long groupId) throws Exception {
		return addNode(
			TestPropsValues.getUserId(), groupId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(50));
	}

	public static WikiNode addNode(
			long userId, long groupId, String name, String description)
		throws Exception {

		WorkflowThreadLocal.setEnabled(true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		serviceContext = (ServiceContext)serviceContext.clone();

		WikiNode node = WikiNodeLocalServiceUtil.addNode(
			userId, name, description, serviceContext);

		return node;
	}

	public static WikiPage addPage(long groupId, long nodeId, boolean approved)
		throws Exception {

		return addPage(
			TestPropsValues.getUserId(), groupId, nodeId,
			RandomTestUtil.randomString(), approved);
	}

	public static WikiPage addPage(
			long userId, long groupId, long nodeId, String title,
			boolean approved)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setLayoutFullURL("http://localhost");

		return addPage(
			userId, nodeId, title, "content", approved, serviceContext);
	}

	public static WikiPage addPage(
			long userId, long nodeId, String title, String content,
			boolean approved, ServiceContext serviceContext)
		throws Exception {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			serviceContext = (ServiceContext)serviceContext.clone();

			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			WikiPage page = WikiPageLocalServiceUtil.addPage(
				userId, nodeId, title, content, "Summary", false,
				serviceContext);

			if (approved) {
				page = updateStatus(page, serviceContext);
			}

			return page;
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	public static WikiPage addPage(
			long userId, long nodeId, String title, String content,
			String parentTitle, boolean approved, ServiceContext serviceContext)
		throws Exception {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			serviceContext = (ServiceContext)serviceContext.clone();

			serviceContext.setCommand(Constants.ADD);

			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			WikiPage page = WikiPageLocalServiceUtil.addPage(
				userId, nodeId, title, WikiPageConstants.VERSION_DEFAULT,
				content, "Summary", false, "creole", true, parentTitle, null,
				serviceContext);

			if (approved) {
				page = updateStatus(page, serviceContext);
			}

			return page;
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	public static WikiPage[] addPageWithChangedParentPage(
			long groupId, long nodeId)
		throws Exception {

		WikiPage initialParentPage = addPage(
			TestPropsValues.getUserId(), groupId, nodeId,
			RandomTestUtil.randomString(), true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		WikiPage childPage = addPage(
			TestPropsValues.getUserId(), nodeId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), initialParentPage.getTitle(), true,
			serviceContext);

		WikiPage finalParentPage = addPage(
			TestPropsValues.getUserId(), groupId, nodeId,
			RandomTestUtil.randomString(), true);

		WikiPageLocalServiceUtil.changeParent(
			TestPropsValues.getUserId(), nodeId, childPage.getTitle(),
			finalParentPage.getTitle(), serviceContext);

		childPage = WikiPageLocalServiceUtil.getPage(
			nodeId, childPage.getTitle());
		initialParentPage = WikiPageLocalServiceUtil.getPageByPageId(
			initialParentPage.getPageId());
		finalParentPage = WikiPageLocalServiceUtil.getPageByPageId(
			finalParentPage.getPageId());

		return new WikiPage[] {childPage, finalParentPage, initialParentPage};
	}

	public static WikiPage[] addPageWithChildPageAndRedirectPage(
			long groupId, long nodeId)
		throws Exception {

		addPage(TestPropsValues.getUserId(), groupId, nodeId, "TestPage", true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		addPage(
			TestPropsValues.getUserId(), nodeId, "TestChildPage",
			RandomTestUtil.randomString(), "TestPage", true, serviceContext);

		WikiPageLocalServiceUtil.renamePage(
			TestPropsValues.getUserId(), nodeId, "TestPage", "B",
			serviceContext);

		WikiPage page = WikiPageLocalServiceUtil.getPage(nodeId, "B");
		WikiPage childPage = WikiPageLocalServiceUtil.getPage(
			nodeId, "TestChildPage");
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			nodeId, "TestPage");

		return new WikiPage[] {page, childPage, redirectPage};
	}

	public static WikiPage[] addRenamedParentPageWithChildPageAndGrandchildPage(
			long groupId, long nodeId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		addPage(TestPropsValues.getUserId(), groupId, nodeId, "TestPage", true);

		WikiPage childPage = addPage(
			TestPropsValues.getUserId(), nodeId, "TestChildPage",
			RandomTestUtil.randomString(), "TestPage", true, serviceContext);

		WikiPage grandchildPage = addPage(
			TestPropsValues.getUserId(), nodeId, "TestGrandchildPage",
			RandomTestUtil.randomString(), "TestChildPage", true,
			serviceContext);

		WikiPageLocalServiceUtil.renamePage(
			TestPropsValues.getUserId(), nodeId, "TestPage", "B",
			serviceContext);

		WikiPage page = WikiPageLocalServiceUtil.getPage(nodeId, "B");
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(
			nodeId, "TestPage");
		childPage = WikiPageLocalServiceUtil.getPageByPageId(
			childPage.getPageId());
		grandchildPage = WikiPageLocalServiceUtil.getPageByPageId(
			grandchildPage.getPageId());

		return new WikiPage[] {page, redirectPage, childPage, grandchildPage};
	}

	public static WikiPage[] addRenamedTrashedPage(
			long groupId, long nodeId, boolean explicitlyRemoveRedirectPage)
		throws Exception {

		addPage(TestPropsValues.getUserId(), groupId, nodeId, "A", true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		WikiPageLocalServiceUtil.renamePage(
			TestPropsValues.getUserId(), nodeId, "A", "B", serviceContext);

		WikiPage page = WikiPageLocalServiceUtil.getPage(nodeId, "B");
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(nodeId, "A");

		if (explicitlyRemoveRedirectPage) {
			WikiPageLocalServiceUtil.movePageToTrash(
				TestPropsValues.getUserId(), nodeId, "A");
		}

		WikiPageLocalServiceUtil.movePageToTrash(
			TestPropsValues.getUserId(), nodeId, "B");

		page = WikiPageLocalServiceUtil.getPageByPageId(page.getPageId());
		redirectPage = WikiPageLocalServiceUtil.getPageByPageId(
			redirectPage.getPageId());

		return new WikiPage[] {page, redirectPage};
	}

	public static WikiPage[] addRenamedTrashedParentPage(
			long groupId, long nodeId, boolean explicitlyRemoveChildPage,
			boolean explicitlyRemoveRedirectPage)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		addPage(TestPropsValues.getUserId(), groupId, nodeId, "A", true);

		WikiPageLocalServiceUtil.renamePage(
			TestPropsValues.getUserId(), nodeId, "A", "B", serviceContext);

		WikiPage page = WikiPageLocalServiceUtil.getPage(nodeId, "B");
		WikiPage redirectPage = WikiPageLocalServiceUtil.getPage(nodeId, "A");

		WikiPage childPage = addPage(
			TestPropsValues.getUserId(), nodeId, "TestChildPage",
			RandomTestUtil.randomString(), "B", true, serviceContext);

		if (explicitlyRemoveChildPage) {
			WikiPageLocalServiceUtil.movePageToTrash(
				TestPropsValues.getUserId(), nodeId, "TestChildPage");
		}

		if (explicitlyRemoveRedirectPage) {
			WikiPageLocalServiceUtil.movePageToTrash(
				TestPropsValues.getUserId(), nodeId, "A");
		}

		WikiPageLocalServiceUtil.movePageToTrash(
			TestPropsValues.getUserId(), nodeId, "B");

		page = WikiPageLocalServiceUtil.getPageByPageId(page.getPageId());
		childPage = WikiPageLocalServiceUtil.getPageByPageId(
			childPage.getPageId());
		redirectPage = WikiPageLocalServiceUtil.getPageByPageId(
			redirectPage.getPageId());

		return new WikiPage[] {page, childPage, redirectPage};
	}

	public static WikiPage[] addTrashedPageWithChildPage(
			long groupId, long nodeId, boolean explicitlyRemoveChildPage)
		throws Exception {

		WikiPage page = addPage(
			TestPropsValues.getUserId(), groupId, nodeId, "TestPage", true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		WikiPage childPage = addPage(
			TestPropsValues.getUserId(), nodeId, "TestChildPage",
			RandomTestUtil.randomString(), "TestPage", true, serviceContext);

		if (explicitlyRemoveChildPage) {
			WikiPageLocalServiceUtil.movePageToTrash(
				TestPropsValues.getUserId(), childPage);
		}

		WikiPageLocalServiceUtil.movePageToTrash(
			TestPropsValues.getUserId(), page);

		page = WikiPageLocalServiceUtil.getPageByPageId(page.getPageId());
		childPage = WikiPageLocalServiceUtil.getPageByPageId(
			childPage.getPageId());

		return new WikiPage[] {page, childPage};
	}

	public static WikiPage[] addTrashedParentPageWithChildPageAndGrandchildPage(
			long groupId, long nodeId, boolean explicitMoveChildToTrash,
			boolean explicitMoveParentToTrash)
		throws Exception {

		WikiPage parentPage = addPage(
			TestPropsValues.getUserId(), groupId, nodeId,
			RandomTestUtil.randomString(), true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		WikiPage childPage = addPage(
			TestPropsValues.getUserId(), nodeId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), parentPage.getTitle(), true,
			serviceContext);

		WikiPage grandchildPage = addPage(
			TestPropsValues.getUserId(), nodeId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), childPage.getTitle(), true,
			serviceContext);

		if (explicitMoveChildToTrash) {
			WikiPageLocalServiceUtil.movePageToTrash(
				TestPropsValues.getUserId(), childPage);
		}

		if (explicitMoveParentToTrash) {
			WikiPageLocalServiceUtil.movePageToTrash(
				TestPropsValues.getUserId(), parentPage);
		}

		parentPage = WikiPageLocalServiceUtil.getPageByPageId(
			parentPage.getPageId());
		childPage = WikiPageLocalServiceUtil.getPageByPageId(
			childPage.getPageId());
		grandchildPage = WikiPageLocalServiceUtil.getPageByPageId(
			grandchildPage.getPageId());

		return new WikiPage[] {parentPage, childPage, grandchildPage};
	}

	public static File addWikiAttachment(
			long userId, long nodeId, String title, Class<?> clazz)
		throws Exception {

		String fileName = RandomTestUtil.randomString() + ".docx";

		return addWikiAttachment(userId, nodeId, title, fileName, clazz);
	}

	public static File addWikiAttachment(
			long userId, long nodeId, String title, String fileName,
			Class<?> clazz)
		throws Exception {

		byte[] fileBytes = FileUtil.getBytes(
			clazz, "dependencies/OSX_Test.docx");

		File file = null;

		if (ArrayUtil.isNotEmpty(fileBytes)) {
			file = FileUtil.createTempFile(fileBytes);
		}

		String mimeType = MimeTypesUtil.getExtensionContentType("docx");

		WikiPageLocalServiceUtil.addPageAttachment(
			userId, nodeId, title, fileName, file, mimeType);

		return file;
	}

	public static WikiPage copyPage(
			WikiPage page, boolean approved, ServiceContext serviceContext)
		throws Exception {

		WikiPage copyPage = addPage(
			page.getUserId(), page.getNodeId(), RandomTestUtil.randomString(),
			page.getContent(), approved, serviceContext);

		WikiPageLocalServiceUtil.copyPageAttachments(
			page.getUserId(), page.getNodeId(), page.getTitle(),
			copyPage.getNodeId(), copyPage.getTitle());

		return copyPage;
	}

	public static void populateNotificationsServiceContext(
			ServiceContext serviceContext, String command)
		throws Exception {

		serviceContext.setAttribute("entryURL", "http://localhost");

		if (Validator.isNotNull(command)) {
			serviceContext.setCommand(command);
		}

		serviceContext.setLayoutFullURL("http://localhost");
	}

	public static WikiPage updatePage(WikiPage page) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(page.getGroupId());

		serviceContext.setCommand(Constants.UPDATE);
		serviceContext.setLayoutFullURL("http://localhost");

		return updatePage(
			page, page.getUserId(), page.getTitle(),
			RandomTestUtil.randomString(50), true, serviceContext);
	}

	public static WikiPage updatePage(
			WikiPage page, long userId, String content,
			ServiceContext serviceContext)
		throws Exception {

		return updatePage(
			page, userId, page.getTitle(), content, true, serviceContext);
	}

	public static WikiPage updatePage(
			WikiPage page, long userId, String title, String content,
			boolean approved, ServiceContext serviceContext)
		throws Exception {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			serviceContext = (ServiceContext)serviceContext.clone();

			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			page = WikiPageLocalServiceUtil.updatePage(
				userId, page.getNodeId(), title, page.getVersion(), content,
				page.getSummary(), false, page.getFormat(),
				page.getParentTitle(), page.getRedirectTitle(), serviceContext);

			if (approved) {
				page = updateStatus(page, serviceContext);
			}

			return page;
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	protected static WikiPage updateStatus(
			WikiPage page, ServiceContext serviceContext)
		throws Exception {

		Map<String, Serializable> workflowContext = new HashMap<>();

		workflowContext.put(WorkflowConstants.CONTEXT_URL, "http://localhost");

		page = WikiPageLocalServiceUtil.updateStatus(
			page.getUserId(), page, WorkflowConstants.STATUS_APPROVED,
			serviceContext, workflowContext);

		return page;
	}

}