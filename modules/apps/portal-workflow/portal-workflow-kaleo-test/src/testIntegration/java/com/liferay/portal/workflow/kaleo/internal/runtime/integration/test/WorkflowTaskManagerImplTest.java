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

package com.liferay.portal.workflow.kaleo.internal.runtime.integration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLTrashServiceUtil;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Inácio Nery
 */
@RunWith(Arquillian.class)
public class WorkflowTaskManagerImplTest
	extends BaseWorkflowTaskManagerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testApproveDLFileEntryInDLFolderWhenHomeDLFolderHasWorkflow()
		throws Exception {

		activateSingleApproverWorkflow(DLFolder.class.getName(), 0, -1);

		Folder folder = addFolder();

		FileVersion fileVersion1 = addFileVersion(folder.getFolderId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion1.getStatus());

		FileVersion fileVersion2 = addFileVersion(folder.getFolderId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion2.getStatus());

		assignWorkflowTaskToUser(
			adminUser, adminUser, REVIEW, DLFileEntry.class.getName(),
			fileVersion1.getFileVersionId());

		assignWorkflowTaskToUser(
			adminUser, adminUser, REVIEW, DLFileEntry.class.getName(),
			fileVersion2.getFileVersionId());

		completeWorkflowTask(
			adminUser, Constants.APPROVE, REVIEW, DLFileEntry.class.getName(),
			fileVersion1.getFileVersionId());

		completeWorkflowTask(
			adminUser, Constants.APPROVE, REVIEW, DLFileEntry.class.getName(),
			fileVersion2.getFileVersionId());

		fileVersion1 = DLAppServiceUtil.getFileVersion(
			fileVersion1.getFileVersionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion1.getStatus());

		fileVersion2 = DLAppServiceUtil.getFileVersion(
			fileVersion2.getFileVersionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion2.getStatus());

		deactivateWorkflow(DLFolder.class.getName(), 0, -1);
	}

	@Test
	public void testApproveDLFileEntryInDLFolderWithoutWorkflowWhenHomeDLFolderHasWorkflow()
		throws Exception {

		activateSingleApproverWorkflow(DLFolder.class.getName(), 0, -1);

		Folder folder = addFolder();

		folder = updateFolder(
			folder, DLFolderConstants.RESTRICTION_TYPE_WORKFLOW);

		FileVersion fileVersion1 = addFileVersion(folder.getFolderId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion1.getStatus());

		FileVersion fileVersion2 = addFileVersion(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion2.getStatus());

		assignWorkflowTaskToUser(
			adminUser, adminUser, REVIEW, DLFileEntry.class.getName(),
			fileVersion2.getFileVersionId());

		completeWorkflowTask(
			adminUser, Constants.APPROVE, REVIEW, DLFileEntry.class.getName(),
			fileVersion2.getFileVersionId());

		fileVersion2 = DLAppServiceUtil.getFileVersion(
			fileVersion2.getFileVersionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion2.getStatus());

		deactivateWorkflow(DLFolder.class.getName(), 0, -1);
	}

	@Test
	public void testApproveDLFileEntryInDLFolderWithSpecificType()
		throws Exception {

		DLFileEntryType fileEntryType = addFileEntryType();

		Map<String, String> dlFileEntryTypeMap = HashMapBuilder.put(
			String.valueOf(fileEntryType.getFileEntryTypeId()),
			"Single Approver@1"
		).build();

		DLFileEntryType basicFileEntryType = getBasicFileEntryType();

		dlFileEntryTypeMap.put(
			String.valueOf(basicFileEntryType.getFileEntryTypeId()),
			StringPool.BLANK);

		Folder folder = addFolder();

		folder = updateFolder(
			folder,
			DLFolderConstants.RESTRICTION_TYPE_FILE_ENTRY_TYPES_AND_WORKFLOW,
			fileEntryType.getFileEntryTypeId(), dlFileEntryTypeMap);

		FileVersion fileVersion1 = addFileVersion(
			folder.getFolderId(), fileEntryType.getFileEntryTypeId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion1.getStatus());

		FileVersion fileVersion2 = addFileVersion(folder.getFolderId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion2.getStatus());

		FileVersion fileVersion3 = addFileVersion(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion3.getStatus());

		assignWorkflowTaskToUser(
			adminUser, adminUser, REVIEW, DLFileEntry.class.getName(),
			fileVersion1.getFileVersionId());

		completeWorkflowTask(
			adminUser, Constants.APPROVE, REVIEW, DLFileEntry.class.getName(),
			fileVersion1.getFileVersionId());

		fileVersion1 = DLAppServiceUtil.getFileVersion(
			fileVersion1.getFileVersionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion1.getStatus());
	}

	@Test
	public void testApproveDLFileEntryInDLFolderWithWorkflow()
		throws Exception {

		Folder folder = addFolder();

		Map<String, String> dlFileEntryTypeMap = HashMapBuilder.put(
			String.valueOf(DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL),
			"Single Approver@1"
		).build();

		folder = updateFolder(
			folder, DLFolderConstants.RESTRICTION_TYPE_WORKFLOW,
			dlFileEntryTypeMap);

		FileVersion fileVersion1 = addFileVersion(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion1.getStatus());

		FileVersion fileVersion2 = addFileVersion(folder.getFolderId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion2.getStatus());

		assignWorkflowTaskToUser(
			adminUser, adminUser, REVIEW, DLFileEntry.class.getName(),
			fileVersion2.getFileVersionId());

		completeWorkflowTask(
			adminUser, Constants.APPROVE, REVIEW, DLFileEntry.class.getName(),
			fileVersion2.getFileVersionId());

		fileVersion2 = DLAppServiceUtil.getFileVersion(
			fileVersion2.getFileVersionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion2.getStatus());
	}

	@Test
	public void testApproveJoinXorWorkflow() throws Exception {
		activateWorkflow(BlogsEntry.class.getName(), 0, 0, JOIN_XOR, 1);

		BlogsEntry blogsEntry = addBlogsEntry();

		assignWorkflowTaskToUser(siteAdminUser, siteAdminUser, "task1");

		completeWorkflowTask(siteAdminUser, "join-xor", "task1");

		WorkflowTask workflowTask2 = getWorkflowTask(
			siteAdminUser, "task2", true, BlogsEntry.class.getName(),
			blogsEntry.getEntryId());

		Assert.assertTrue(workflowTask2.isCompleted());

		blogsEntry = BlogsEntryLocalServiceUtil.getBlogsEntry(
			blogsEntry.getEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, blogsEntry.getStatus());

		deactivateWorkflow(BlogsEntry.class.getName(), 0, 0);
	}

	@Test
	public void testApproveJournalArticleAsAdmin() throws Exception {
		activateSingleApproverWorkflow(
			JournalFolder.class.getName(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL);

		JournalArticle article = addJournalArticle(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, article.getStatus());

		checkUserNotificationEventsByUsers(
			adminUser, portalContentReviewerUser, siteAdminUser);

		assignWorkflowTaskToUser(adminUser, adminUser);

		completeWorkflowTask(adminUser, Constants.APPROVE);

		getWorkflowInstance(JournalArticle.class.getName(), article.getId());

		article = JournalArticleLocalServiceUtil.getArticle(article.getId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, article.getStatus());

		deactivateWorkflow(
			JournalFolder.class.getName(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL);
	}

	@Test
	public void testApproveJournalArticleInFolderInheritedWorkflow()
		throws Exception {

		activateSingleApproverWorkflow(
			JournalFolder.class.getName(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL);

		JournalFolder folder = addJournalFolder();

		JournalArticle article = addJournalArticle(folder.getFolderId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, article.getStatus());

		checkUserNotificationEventsByUsers(
			adminUser, portalContentReviewerUser, siteAdminUser);

		assignWorkflowTaskToUser(adminUser, adminUser);

		completeWorkflowTask(adminUser, Constants.APPROVE);

		getWorkflowInstance(JournalArticle.class.getName(), article.getId());

		article = JournalArticleLocalServiceUtil.getArticle(article.getId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, article.getStatus());

		deactivateWorkflow(
			JournalFolder.class.getName(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL);
	}

	@Test
	public void testApproveJournalArticleInFolderStructureSpecificWorkflow()
		throws Exception {

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		JournalFolder folder = addJournalFolder(
			ddmStructure.getStructureId(),
			JournalFolderConstants.
				RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW);

		activateSingleApproverWorkflow(
			JournalFolder.class.getName(), folder.getFolderId(),
			ddmStructure.getStructureId());

		JournalArticle article = addJournalArticle(
			folder.getFolderId(), ddmStructure);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, article.getStatus());

		checkUserNotificationEventsByUsers(
			adminUser, portalContentReviewerUser, siteAdminUser);

		assignWorkflowTaskToUser(adminUser, adminUser);

		completeWorkflowTask(adminUser, Constants.APPROVE);

		getWorkflowInstance(JournalArticle.class.getName(), article.getId());

		article = JournalArticleLocalServiceUtil.getArticle(article.getId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, article.getStatus());

		deactivateWorkflow(
			JournalFolder.class.getName(), folder.getFolderId(),
			ddmStructure.getStructureId());
	}

	@Test
	public void testApproveJournalArticleUsingFolderSpecificWorkflow()
		throws Exception {

		JournalFolder folder = addJournalFolder(
			0, JournalFolderConstants.RESTRICTION_TYPE_WORKFLOW);

		activateSingleApproverWorkflow(
			JournalFolder.class.getName(), folder.getFolderId(),
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL);

		JournalArticle article = addJournalArticle(folder.getFolderId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, article.getStatus());

		checkUserNotificationEventsByUsers(
			adminUser, portalContentReviewerUser, siteAdminUser);

		assignWorkflowTaskToUser(adminUser, adminUser);

		completeWorkflowTask(adminUser, Constants.APPROVE);

		getWorkflowInstance(JournalArticle.class.getName(), article.getId());

		article = JournalArticleLocalServiceUtil.getArticle(article.getId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, article.getStatus());

		deactivateWorkflow(
			JournalFolder.class.getName(), folder.getFolderId(),
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL);
	}

	@Test
	public void testApproveOrganizationParentReviewer() throws Exception {
		Organization parentOrganization = createOrganization(true);

		User reviewerUser = createUser(
			ORGANIZATION_CONTENT_REVIEWER, parentOrganization.getGroup());

		OrganizationLocalServiceUtil.addUserOrganization(
			reviewerUser.getUserId(), parentOrganization);

		Organization childOrganization = createOrganization(
			parentOrganization.getOrganizationId(), true);

		User memberUser = createUser(
			RoleConstants.ORGANIZATION_ADMINISTRATOR,
			childOrganization.getGroup());

		OrganizationLocalServiceUtil.addUserOrganization(
			memberUser.getUserId(), childOrganization);

		serviceContext = ServiceContextTestUtil.getServiceContext(
			childOrganization.getGroupId());

		activateSingleApproverWorkflow(
			childOrganization.getGroupId(), BlogsEntry.class.getName(), 0, 0);

		BlogsEntry blogsEntry = addBlogsEntry(memberUser);

		checkUserNotificationEventsByUsers(reviewerUser);

		assignWorkflowTaskToUser(reviewerUser, reviewerUser);

		completeWorkflowTask(reviewerUser, Constants.APPROVE);

		blogsEntry = BlogsEntryLocalServiceUtil.getBlogsEntry(
			blogsEntry.getEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, blogsEntry.getStatus());

		deactivateWorkflow(
			childOrganization.getGroupId(), BlogsEntry.class.getName(), 0, 0);

		serviceContext = ServiceContextTestUtil.getServiceContext(
			group.getGroupId());
	}

	@Test
	public void testApproveOrganizationParentReviewerWithoutSite()
		throws Exception {

		Organization parentOrganization = createOrganization(false);

		User reviewerUser = createUser(
			ORGANIZATION_CONTENT_REVIEWER, parentOrganization.getGroup());

		OrganizationLocalServiceUtil.addUserOrganization(
			reviewerUser.getUserId(), parentOrganization);

		Organization childOrganization = createOrganization(
			parentOrganization.getOrganizationId(), true);

		User memberUser = createUser(
			RoleConstants.ORGANIZATION_ADMINISTRATOR,
			childOrganization.getGroup());

		OrganizationLocalServiceUtil.addUserOrganization(
			memberUser.getUserId(), childOrganization);

		serviceContext = ServiceContextTestUtil.getServiceContext(
			childOrganization.getGroupId());

		activateSingleApproverWorkflow(
			childOrganization.getGroupId(), BlogsEntry.class.getName(), 0, 0);

		BlogsEntry blogsEntry = addBlogsEntry(memberUser);

		checkUserNotificationEventsByUsers(reviewerUser);

		assignWorkflowTaskToUser(reviewerUser, reviewerUser);

		completeWorkflowTask(reviewerUser, Constants.APPROVE);

		blogsEntry = BlogsEntryLocalServiceUtil.getBlogsEntry(
			blogsEntry.getEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, blogsEntry.getStatus());

		deactivateWorkflow(
			childOrganization.getGroupId(), BlogsEntry.class.getName(), 0, 0);

		serviceContext = ServiceContextTestUtil.getServiceContext(
			group.getGroupId());
	}

	@Test
	public void testApproveScriptAssignmentOrganizationAndSiteReviewer()
		throws Exception {

		Organization organization = createOrganization(true);

		User organizationReviewerUser = createUser(
			ORGANIZATION_CONTENT_REVIEWER, organization.getGroup());

		OrganizationLocalServiceUtil.addUserOrganization(
			organizationReviewerUser.getUserId(), organization);

		User siteAdministratorUser = createUser(
			RoleConstants.SITE_ADMINISTRATOR);

		OrganizationLocalServiceUtil.addUserOrganization(
			siteAdministratorUser.getUserId(), organization);

		serviceContext = ServiceContextTestUtil.getServiceContext(
			organization.getGroupId());

		activateWorkflow(
			organization.getGroupId(), BlogsEntry.class.getName(), 0, 0,
			SCRIPTED_SINGLE_APPROVER, 1);

		BlogsEntry blogsEntry = addBlogsEntry(siteAdministratorUser);

		assignWorkflowTaskToUser(
			organizationReviewerUser, organizationReviewerUser);

		completeWorkflowTask(organizationReviewerUser, Constants.APPROVE);

		blogsEntry = BlogsEntryLocalServiceUtil.getBlogsEntry(
			blogsEntry.getEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, blogsEntry.getStatus());

		deactivateWorkflow(
			organization.getGroupId(), BlogsEntry.class.getName(), 0, 0);

		serviceContext = ServiceContextTestUtil.getServiceContext(
			group.getGroupId());
	}

	@Test
	public void testApproveSiteMember() throws Exception {
		activateWorkflow(
			JournalFolder.class.getName(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL,
			SITE_MEMBER_SINGLE_APPROVER, 1);

		JournalArticle article = addJournalArticle(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, article.getStatus());

		checkUserNotificationEventsByUsers(siteMemberUser);

		Assert.assertTrue(hasOtherAssignees(adminUser));

		assignWorkflowTaskToUser(adminUser, siteMemberUser);

		completeWorkflowTask(siteMemberUser, Constants.APPROVE);

		getWorkflowInstance(JournalArticle.class.getName(), article.getId());

		article = JournalArticleLocalServiceUtil.getArticle(article.getId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, article.getStatus());

		deactivateWorkflow(
			JournalFolder.class.getName(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL);
	}

	@Test
	public void testApproveWorkflowBlogsEntryAsSiteAdmin() throws Exception {
		activateSingleApproverWorkflow(BlogsEntry.class.getName(), 0, 0);

		BlogsEntry blogsEntry = addBlogsEntry();

		checkUserNotificationEventsByUsers(
			adminUser, portalContentReviewerUser, siteAdminUser);

		assignWorkflowTaskToUser(siteAdminUser, siteAdminUser);

		completeWorkflowTask(siteAdminUser, Constants.APPROVE);

		blogsEntry = BlogsEntryLocalServiceUtil.getBlogsEntry(
			blogsEntry.getEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, blogsEntry.getStatus());

		deactivateWorkflow(BlogsEntry.class.getName(), 0, 0);
	}

	@Test
	public void testApproveWorkflowDDLRecordAsAdmin() throws Exception {
		DDLRecordSet recordSet = addRecordSet();

		activateSingleApproverWorkflow(
			DDLRecordSet.class.getName(), recordSet.getRecordSetId(), 0);

		DDLRecord record = addRecord(recordSet);

		checkUserNotificationEventsByUsers(
			adminUser, portalContentReviewerUser, siteAdminUser);

		assignWorkflowTaskToUser(adminUser, adminUser);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, record.getStatus());

		completeWorkflowTask(adminUser, Constants.APPROVE);

		DDLRecordVersion recordVersion = record.getRecordVersion();

		getWorkflowInstance(
			DDLRecord.class.getName(), recordVersion.getRecordVersionId());

		record = DDLRecordLocalServiceUtil.getRecord(record.getRecordId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, record.getStatus());

		deactivateWorkflow(
			DDLRecordSet.class.getName(), recordSet.getRecordSetId(), 0);
	}

	@Test
	public void testAssignApproveWorkflowBlogsEntryAsPortalContentReviewer()
		throws Exception {

		activateSingleApproverWorkflow(BlogsEntry.class.getName(), 0, 0);

		BlogsEntry blogsEntry = addBlogsEntry();

		checkUserNotificationEventsByUsers(
			adminUser, portalContentReviewerUser, siteAdminUser);

		assignWorkflowTaskToUser(portalContentReviewerUser, adminUser);

		checkUserNotificationEventsByUsers(adminUser);

		assignWorkflowTaskToUser(adminUser, portalContentReviewerUser);

		checkUserNotificationEventsByUsers(portalContentReviewerUser);

		completeWorkflowTask(portalContentReviewerUser, Constants.APPROVE);

		blogsEntry = BlogsEntryLocalServiceUtil.getBlogsEntry(
			blogsEntry.getEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, blogsEntry.getStatus());

		deactivateWorkflow(BlogsEntry.class.getName(), 0, 0);
	}

	@Test
	public void testDeleteUserWithWorkflowTaskAssigned() throws Exception {
		activateSingleApproverWorkflow(BlogsEntry.class.getName(), 0, 0);

		addBlogsEntry();

		checkUserNotificationEventsByUsers(siteAdminUser);

		User user = createUser(RoleConstants.SITE_ADMINISTRATOR);

		assignWorkflowTaskToUser(siteAdminUser, user);

		Assert.assertEquals(
			1,
			WorkflowTaskManagerUtil.getWorkflowTaskCountByUser(
				user.getCompanyId(), user.getUserId(), false));

		userLocalService.deleteUser(user);

		Assert.assertEquals(
			0,
			WorkflowTaskManagerUtil.getWorkflowTaskCountByUser(
				user.getCompanyId(), user.getUserId(), false));

		deactivateWorkflow(BlogsEntry.class.getName(), 0, 0);
	}

	@Test
	public void testMovetoTrashAndRestoreFromTrashPendingDLFileEntryInDLFolderWithWorkflow()
		throws Exception {

		Folder folder = addFolder();

		Map<String, String> dlFileEntryTypeMap = HashMapBuilder.put(
			String.valueOf(DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL),
			"Single Approver@1"
		).build();

		folder = updateFolder(
			folder, DLFolderConstants.RESTRICTION_TYPE_WORKFLOW,
			dlFileEntryTypeMap);

		FileVersion fileVersion = addFileVersion(folder.getFolderId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion.getStatus());

		assignWorkflowTaskToUser(
			adminUser, adminUser, REVIEW, DLFileEntry.class.getName(),
			fileVersion.getFileVersionId());

		completeWorkflowTask(
			adminUser, Constants.APPROVE, REVIEW, DLFileEntry.class.getName(),
			fileVersion.getFileVersionId());

		fileVersion = DLAppServiceUtil.getFileVersion(
			fileVersion.getFileVersionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion.getStatus());

		fileVersion = updateFileVersion(fileVersion.getFileEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion.getStatus());

		FileEntry fileEntry = DLTrashServiceUtil.moveFileEntryToTrash(
			fileVersion.getFileEntryId());

		WorkflowInstanceLink workflowInstanceLink = fetchWorkflowInstanceLink(
			DLFileEntryConstants.getClassName(),
			fileVersion.getFileVersionId());

		Assert.assertNull(workflowInstanceLink);

		DLTrashServiceUtil.restoreFileEntryFromTrash(
			fileVersion.getFileEntryId());

		fileVersion = fileEntry.getLatestFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, fileVersion.getStatus());
	}

	@Test
	public void testRejectDLFileEntry() throws Exception {
		activateSingleApproverWorkflow(DLFolder.class.getName(), 0, -1);

		FileVersion fileVersion1 = addFileVersion(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion1.getStatus());

		assignWorkflowTaskToUser(
			adminUser, adminUser, REVIEW, DLFileEntry.class.getName(),
			fileVersion1.getFileVersionId());

		completeWorkflowTask(
			adminUser, Constants.REJECT, REVIEW, DLFileEntry.class.getName(),
			fileVersion1.getFileVersionId());

		fileVersion1 = DLAppServiceUtil.getFileVersion(
			fileVersion1.getFileVersionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion1.getStatus());

		getWorkflowTask(
			adminUser, Constants.UPDATE, false, DLFileEntry.class.getName(),
			fileVersion1.getFileVersionId());

		deactivateWorkflow(DLFolder.class.getName(), 0, -1);
	}

	@Test
	public void testRejectWorkflowBlogsEntryAndViewAssignee() throws Exception {
		activateSingleApproverWorkflow(BlogsEntry.class.getName(), 0, 0);

		BlogsEntry blogsEntry = addBlogsEntry();

		checkUserNotificationEventsByUsers(
			adminUser, portalContentReviewerUser, siteAdminUser);

		assignWorkflowTaskToUser(adminUser, portalContentReviewerUser);

		checkUserNotificationEventsByUsers(portalContentReviewerUser);

		completeWorkflowTask(portalContentReviewerUser, Constants.REJECT);

		checkUserNotificationEventsByUsers(adminUser);

		blogsEntry = BlogsEntryLocalServiceUtil.getBlogsEntry(
			blogsEntry.getEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, blogsEntry.getStatus());

		WorkflowTask workflowTask = getWorkflowTask();

		Assert.assertEquals(
			adminUser.getUserId(), workflowTask.getAssigneeUserId());

		deactivateWorkflow(BlogsEntry.class.getName(), 0, 0);
	}

	@Test
	public void testSearchWorkflowTaskByAssetTitle1() throws Exception {
		activateSingleApproverWorkflow(BlogsEntry.class.getName(), 0, 0);

		BlogsEntry blogsEntry = addBlogsEntry();

		int total = searchCount(blogsEntry.getTitle());

		Assert.assertEquals(1, total);

		total = searchCount(RandomTestUtil.randomString());

		Assert.assertEquals(0, total);

		deactivateWorkflow(BlogsEntry.class.getName(), 0, 0);
	}

	@Test
	public void testSearchWorkflowTaskByAssetTitle2() throws Exception {
		activateSingleApproverWorkflow(
			JournalFolder.class.getName(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL);

		JournalArticle article = addJournalArticle(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		int total = searchCount(article.getTitle());

		Assert.assertEquals(1, total);

		total = searchCount(RandomTestUtil.randomString());

		Assert.assertEquals(0, total);

		deactivateWorkflow(
			JournalFolder.class.getName(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL);
	}

	@Test
	public void testSearchWorkflowTaskByDeletedAsset() throws Exception {
		activateSingleApproverWorkflow(
			JournalFolder.class.getName(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL);

		JournalArticle article = addJournalArticle(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		int total = searchCount(article.getTitle());

		Assert.assertEquals(1, total);

		JournalArticleLocalServiceUtil.deleteArticle(article);

		total = searchCount(article.getTitle());

		Assert.assertEquals(0, total);

		deactivateWorkflow(
			JournalFolder.class.getName(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL);
	}

	@Test
	public void testSearchWorkflowTaskByUserRoles() throws Exception {
		activateSingleApproverWorkflow(BlogsEntry.class.getName(), 0, 0);

		addBlogsEntry();

		int total = searchCountByUserRoles(siteContentReviewerUser);

		Assert.assertEquals(1, total);

		deactivateWorkflow(BlogsEntry.class.getName(), 0, 0);
	}

	@Test
	public void testUpdateDueDate() throws Exception {
		activateSingleApproverWorkflow(BlogsEntry.class.getName(), 0, 0);

		BlogsEntry blogsEntry = addBlogsEntry();

		WorkflowTask workflowTask = getWorkflowTask();

		Date date = new Date(System.currentTimeMillis() + Time.DAY);

		workflowTask = WorkflowTaskManagerUtil.updateDueDate(
			siteAdminUser.getCompanyId(), siteAdminUser.getUserId(),
			workflowTask.getWorkflowTaskId(), StringPool.BLANK, date);

		Assert.assertEquals(date, workflowTask.getDueDate());

		BlogsEntryLocalServiceUtil.deleteEntry(blogsEntry);

		deactivateWorkflow(BlogsEntry.class.getName(), 0, 0);
	}

}