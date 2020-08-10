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
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.DLTrashService;
import com.liferay.dynamic.data.lists.constants.DDLRecordConstants;
import com.liferay.dynamic.data.lists.constants.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMBeanTranslatorUtil;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.security.permission.SimplePermissionChecker;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.apache.log4j.Level;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Inácio Nery
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class WorkflowTaskManagerImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_configuration = _configurationAdmin.getConfiguration(
			"com.liferay.portal.workflow.configuration." +
				"WorkflowDefinitionConfiguration",
			StringPool.QUESTION);

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("company.administrator.can.publish", true);

		ConfigurationTestUtil.saveConfiguration(_configuration, properties);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		ConfigurationTestUtil.deleteConfiguration(_configuration);
	}

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_companyAdminUser = UserTestUtil.addCompanyAdminUser(_company);

		_group = GroupTestUtil.addGroup(
			_company.getCompanyId(), _companyAdminUser.getUserId(), 0);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, _companyAdminUser.getUserId());

		_setUpPermissionThreadLocal();
		_setUpPrincipalThreadLocal();
		_setUpUsers();
		_setUpWorkflow();
	}

	@After
	public void tearDown() throws PortalException {
		PermissionThreadLocal.setPermissionChecker(_permissionChecker);

		PrincipalThreadLocal.setName(_name);
	}

	@Test
	public void testApproveDLFileEntryInDLFolderWhenHomeDLFolderHasWorkflow()
		throws Exception {

		_activateSingleApproverWorkflow(DLFolder.class.getName(), 0, -1);

		Folder folder = _addFolder();

		FileVersion fileVersion1 = _addFileVersion(folder.getFolderId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion1.getStatus());

		FileVersion fileVersion2 = _addFileVersion(folder.getFolderId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion2.getStatus());

		_assignWorkflowTaskToUser(
			_adminUser, _adminUser, _REVIEW, DLFileEntry.class.getName(),
			fileVersion1.getFileVersionId());

		_assignWorkflowTaskToUser(
			_adminUser, _adminUser, _REVIEW, DLFileEntry.class.getName(),
			fileVersion2.getFileVersionId());

		_completeWorkflowTask(
			_adminUser, Constants.APPROVE, _REVIEW, DLFileEntry.class.getName(),
			fileVersion1.getFileVersionId());

		_completeWorkflowTask(
			_adminUser, Constants.APPROVE, _REVIEW, DLFileEntry.class.getName(),
			fileVersion2.getFileVersionId());

		fileVersion1 = _dlAppService.getFileVersion(
			fileVersion1.getFileVersionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion1.getStatus());

		fileVersion2 = _dlAppService.getFileVersion(
			fileVersion2.getFileVersionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion2.getStatus());

		_deactivateWorkflow(DLFolder.class.getName(), 0, -1);
	}

	@Test
	public void testApproveDLFileEntryInDLFolderWithoutWorkflowWhenHomeDLFolderHasWorkflow()
		throws Exception {

		_activateSingleApproverWorkflow(DLFolder.class.getName(), 0, -1);

		Folder folder = _addFolder();

		folder = _updateFolder(
			folder, DLFolderConstants.RESTRICTION_TYPE_WORKFLOW);

		FileVersion fileVersion1 = _addFileVersion(folder.getFolderId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion1.getStatus());

		FileVersion fileVersion2 = _addFileVersion(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion2.getStatus());

		_assignWorkflowTaskToUser(
			_adminUser, _adminUser, _REVIEW, DLFileEntry.class.getName(),
			fileVersion2.getFileVersionId());

		_completeWorkflowTask(
			_adminUser, Constants.APPROVE, _REVIEW, DLFileEntry.class.getName(),
			fileVersion2.getFileVersionId());

		fileVersion2 = _dlAppService.getFileVersion(
			fileVersion2.getFileVersionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion2.getStatus());

		_deactivateWorkflow(DLFolder.class.getName(), 0, -1);
	}

	@Test
	public void testApproveDLFileEntryInDLFolderWithSpecificType()
		throws Exception {

		DLFileEntryType fileEntryType = _addFileEntryType();

		Map<String, String> dlFileEntryTypeMap = HashMapBuilder.put(
			String.valueOf(fileEntryType.getFileEntryTypeId()),
			"Single Approver@1"
		).put(
			() -> {
				DLFileEntryType basicFileEntryType = _getBasicFileEntryType();

				return String.valueOf(basicFileEntryType.getFileEntryTypeId());
			},
			StringPool.BLANK
		).build();

		Folder folder = _addFolder();

		folder = _updateFolder(
			folder,
			DLFolderConstants.RESTRICTION_TYPE_FILE_ENTRY_TYPES_AND_WORKFLOW,
			fileEntryType.getFileEntryTypeId(), dlFileEntryTypeMap);

		FileVersion fileVersion1 = _addFileVersion(
			folder.getFolderId(), fileEntryType.getFileEntryTypeId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion1.getStatus());

		FileVersion fileVersion2 = _addFileVersion(folder.getFolderId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion2.getStatus());

		FileVersion fileVersion3 = _addFileVersion(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion3.getStatus());

		_assignWorkflowTaskToUser(
			_adminUser, _adminUser, _REVIEW, DLFileEntry.class.getName(),
			fileVersion1.getFileVersionId());

		_completeWorkflowTask(
			_adminUser, Constants.APPROVE, _REVIEW, DLFileEntry.class.getName(),
			fileVersion1.getFileVersionId());

		fileVersion1 = _dlAppService.getFileVersion(
			fileVersion1.getFileVersionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion1.getStatus());
	}

	@Test
	public void testApproveDLFileEntryInDLFolderWithWorkflow()
		throws Exception {

		Folder folder = _addFolder();

		folder = _updateFolder(
			folder, DLFolderConstants.RESTRICTION_TYPE_WORKFLOW,
			HashMapBuilder.put(
				String.valueOf(DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL),
				"Single Approver@1"
			).build());

		FileVersion fileVersion1 = _addFileVersion(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion1.getStatus());

		FileVersion fileVersion2 = _addFileVersion(folder.getFolderId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion2.getStatus());

		_assignWorkflowTaskToUser(
			_adminUser, _adminUser, _REVIEW, DLFileEntry.class.getName(),
			fileVersion2.getFileVersionId());

		_completeWorkflowTask(
			_adminUser, Constants.APPROVE, _REVIEW, DLFileEntry.class.getName(),
			fileVersion2.getFileVersionId());

		fileVersion2 = _dlAppService.getFileVersion(
			fileVersion2.getFileVersionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion2.getStatus());
	}

	@Test
	public void testApproveJoinXorWorkflow() throws Exception {
		_activateWorkflow(BlogsEntry.class.getName(), 0, 0, _JOIN_XOR, 1);

		BlogsEntry blogsEntry = _addBlogsEntry();

		_assignWorkflowTaskToUser(_siteAdminUser, _siteAdminUser, "task1");

		_completeWorkflowTask(_siteAdminUser, "join-xor", "task1");

		WorkflowTask workflowTask2 = _getWorkflowTask(
			_siteAdminUser, "task2", true, BlogsEntry.class.getName(),
			blogsEntry.getEntryId());

		Assert.assertTrue(workflowTask2.isCompleted());

		blogsEntry = _blogsEntryLocalService.getBlogsEntry(
			blogsEntry.getEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, blogsEntry.getStatus());

		_deactivateWorkflow(BlogsEntry.class.getName(), 0, 0);
	}

	@Test
	public void testApproveJournalArticleAsAdmin() throws Exception {
		_activateSingleApproverWorkflow(
			JournalFolder.class.getName(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL);

		JournalArticle article = _addJournalArticle(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, article.getStatus());

		_checkUserNotificationEventsByUsers(
			_adminUser, _portalContentReviewerUser, _siteAdminUser);

		_assignWorkflowTaskToUser(_adminUser, _adminUser);

		_completeWorkflowTask(_adminUser, Constants.APPROVE);

		_getWorkflowInstance(JournalArticle.class.getName(), article.getId());

		article = _journalArticleLocalService.getArticle(article.getId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, article.getStatus());

		_deactivateWorkflow(
			JournalFolder.class.getName(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL);
	}

	@Test
	public void testApproveJournalArticleInFolderInheritedWorkflow()
		throws Exception {

		_activateSingleApproverWorkflow(
			JournalFolder.class.getName(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL);

		JournalFolder folder = _addJournalFolder();

		JournalArticle article = _addJournalArticle(folder.getFolderId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, article.getStatus());

		_checkUserNotificationEventsByUsers(
			_adminUser, _portalContentReviewerUser, _siteAdminUser);

		_assignWorkflowTaskToUser(_adminUser, _adminUser);

		_completeWorkflowTask(_adminUser, Constants.APPROVE);

		_getWorkflowInstance(JournalArticle.class.getName(), article.getId());

		article = _journalArticleLocalService.getArticle(article.getId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, article.getStatus());

		_deactivateWorkflow(
			JournalFolder.class.getName(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL);
	}

	@Test
	public void testApproveJournalArticleInFolderStructureSpecificWorkflow()
		throws Exception {

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		JournalFolder folder = _addJournalFolder(
			ddmStructure.getStructureId(),
			JournalFolderConstants.
				RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW);

		_activateSingleApproverWorkflow(
			JournalFolder.class.getName(), folder.getFolderId(),
			ddmStructure.getStructureId());

		JournalArticle article = _addJournalArticle(
			folder.getFolderId(), ddmStructure);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, article.getStatus());

		_checkUserNotificationEventsByUsers(
			_adminUser, _portalContentReviewerUser, _siteAdminUser);

		_assignWorkflowTaskToUser(_adminUser, _adminUser);

		_completeWorkflowTask(_adminUser, Constants.APPROVE);

		_getWorkflowInstance(JournalArticle.class.getName(), article.getId());

		article = _journalArticleLocalService.getArticle(article.getId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, article.getStatus());

		_deactivateWorkflow(
			JournalFolder.class.getName(), folder.getFolderId(),
			ddmStructure.getStructureId());
	}

	@Test
	public void testApproveJournalArticleUsingFolderSpecificWorkflow()
		throws Exception {

		JournalFolder folder = _addJournalFolder(
			0, JournalFolderConstants.RESTRICTION_TYPE_WORKFLOW);

		_activateSingleApproverWorkflow(
			JournalFolder.class.getName(), folder.getFolderId(),
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL);

		JournalArticle article = _addJournalArticle(folder.getFolderId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, article.getStatus());

		_checkUserNotificationEventsByUsers(
			_adminUser, _portalContentReviewerUser, _siteAdminUser);

		_assignWorkflowTaskToUser(_adminUser, _adminUser);

		_completeWorkflowTask(_adminUser, Constants.APPROVE);

		_getWorkflowInstance(JournalArticle.class.getName(), article.getId());

		article = _journalArticleLocalService.getArticle(article.getId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, article.getStatus());

		_deactivateWorkflow(
			JournalFolder.class.getName(), folder.getFolderId(),
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL);
	}

	@Test
	public void testApproveOrganizationParentReviewer() throws Exception {
		Organization parentOrganization = _createOrganization(true);

		User reviewerUser = _createUser(
			_ORGANIZATION_CONTENT_REVIEWER, parentOrganization.getGroup());

		_organizationLocalService.addUserOrganization(
			reviewerUser.getUserId(), parentOrganization);

		Organization childOrganization = _createOrganization(
			parentOrganization.getOrganizationId(), true);

		User memberUser = _createUser(
			RoleConstants.ORGANIZATION_ADMINISTRATOR,
			childOrganization.getGroup());

		_organizationLocalService.addUserOrganization(
			memberUser.getUserId(), childOrganization);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			childOrganization.getGroupId());

		_activateSingleApproverWorkflow(
			childOrganization.getGroupId(), BlogsEntry.class.getName(), 0, 0);

		BlogsEntry blogsEntry = _addBlogsEntry(memberUser);

		_checkUserNotificationEventsByUsers(reviewerUser);

		_assignWorkflowTaskToUser(reviewerUser, reviewerUser);

		_completeWorkflowTask(reviewerUser, Constants.APPROVE);

		blogsEntry = _blogsEntryLocalService.getBlogsEntry(
			blogsEntry.getEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, blogsEntry.getStatus());

		_deactivateWorkflow(
			childOrganization.getGroupId(), BlogsEntry.class.getName(), 0, 0);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());
	}

	@Test
	public void testApproveOrganizationParentReviewerWithoutSite()
		throws Exception {

		Organization parentOrganization = _createOrganization(false);

		User reviewerUser = _createUser(
			_ORGANIZATION_CONTENT_REVIEWER, parentOrganization.getGroup());

		_organizationLocalService.addUserOrganization(
			reviewerUser.getUserId(), parentOrganization);

		Organization childOrganization = _createOrganization(
			parentOrganization.getOrganizationId(), true);

		User memberUser = _createUser(
			RoleConstants.ORGANIZATION_ADMINISTRATOR,
			childOrganization.getGroup());

		_organizationLocalService.addUserOrganization(
			memberUser.getUserId(), childOrganization);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			childOrganization.getGroupId());

		_activateSingleApproverWorkflow(
			childOrganization.getGroupId(), BlogsEntry.class.getName(), 0, 0);

		BlogsEntry blogsEntry = _addBlogsEntry(memberUser);

		_checkUserNotificationEventsByUsers(reviewerUser);

		_assignWorkflowTaskToUser(reviewerUser, reviewerUser);

		_completeWorkflowTask(reviewerUser, Constants.APPROVE);

		blogsEntry = _blogsEntryLocalService.getBlogsEntry(
			blogsEntry.getEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, blogsEntry.getStatus());

		_deactivateWorkflow(
			childOrganization.getGroupId(), BlogsEntry.class.getName(), 0, 0);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());
	}

	@Test
	public void testApproveScriptAssignmentOrganizationAndSiteReviewer()
		throws Exception {

		Organization organization = _createOrganization(true);

		User organizationReviewerUser = _createUser(
			_ORGANIZATION_CONTENT_REVIEWER, organization.getGroup());

		_organizationLocalService.addUserOrganization(
			organizationReviewerUser.getUserId(), organization);

		User siteAdministratorUser = _createUser(
			RoleConstants.SITE_ADMINISTRATOR);

		_organizationLocalService.addUserOrganization(
			siteAdministratorUser.getUserId(), organization);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			organization.getGroupId());

		_activateWorkflow(
			organization.getGroupId(), BlogsEntry.class.getName(), 0, 0,
			_SCRIPTED_SINGLE_APPROVER, 1);

		BlogsEntry blogsEntry = _addBlogsEntry(siteAdministratorUser);

		_assignWorkflowTaskToUser(
			organizationReviewerUser, organizationReviewerUser);

		_completeWorkflowTask(organizationReviewerUser, Constants.APPROVE);

		blogsEntry = _blogsEntryLocalService.getBlogsEntry(
			blogsEntry.getEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, blogsEntry.getStatus());

		_deactivateWorkflow(
			organization.getGroupId(), BlogsEntry.class.getName(), 0, 0);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());
	}

	@Test
	public void testApproveSiteMember() throws Exception {
		_activateWorkflow(
			JournalFolder.class.getName(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL,
			_SITE_MEMBER_SINGLE_APPROVER, 1);

		JournalArticle article = _addJournalArticle(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, article.getStatus());

		_checkUserNotificationEventsByUsers(_siteMemberUser);

		Assert.assertTrue(_hasAssignableUsers(_adminUser));

		_assignWorkflowTaskToUser(_adminUser, _siteMemberUser);

		_completeWorkflowTask(_siteMemberUser, Constants.APPROVE);

		_getWorkflowInstance(JournalArticle.class.getName(), article.getId());

		article = _journalArticleLocalService.getArticle(article.getId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, article.getStatus());

		_deactivateWorkflow(
			JournalFolder.class.getName(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL);
	}

	@Test
	public void testApproveWorkflowBlogsEntryAsSiteAdmin() throws Exception {
		_activateSingleApproverWorkflow(BlogsEntry.class.getName(), 0, 0);

		BlogsEntry blogsEntry = _addBlogsEntry();

		_checkUserNotificationEventsByUsers(
			_adminUser, _portalContentReviewerUser, _siteAdminUser);

		_assignWorkflowTaskToUser(_siteAdminUser, _siteAdminUser);

		_completeWorkflowTask(_siteAdminUser, Constants.APPROVE);

		blogsEntry = _blogsEntryLocalService.getBlogsEntry(
			blogsEntry.getEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, blogsEntry.getStatus());

		_deactivateWorkflow(BlogsEntry.class.getName(), 0, 0);
	}

	@Test
	public void testApproveWorkflowDDLRecordAsAdmin() throws Exception {
		DDLRecordSet recordSet = _addRecordSet();

		_activateSingleApproverWorkflow(
			DDLRecordSet.class.getName(), recordSet.getRecordSetId(), 0);

		DDLRecord record = _addRecord(recordSet);

		_checkUserNotificationEventsByUsers(
			_adminUser, _portalContentReviewerUser, _siteAdminUser);

		_assignWorkflowTaskToUser(_adminUser, _adminUser);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, record.getStatus());

		_completeWorkflowTask(_adminUser, Constants.APPROVE);

		DDLRecordVersion recordVersion = record.getRecordVersion();

		_getWorkflowInstance(
			DDLRecord.class.getName(), recordVersion.getRecordVersionId());

		record = _ddlRecordLocalService.getRecord(record.getRecordId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, record.getStatus());

		_deactivateWorkflow(
			DDLRecordSet.class.getName(), recordSet.getRecordSetId(), 0);
	}

	@Test
	public void testAssignApproveWorkflowBlogsEntryAsPortalContentReviewer()
		throws Exception {

		_activateSingleApproverWorkflow(BlogsEntry.class.getName(), 0, 0);

		BlogsEntry blogsEntry = _addBlogsEntry();

		_checkUserNotificationEventsByUsers(
			_adminUser, _portalContentReviewerUser, _siteAdminUser);

		_assignWorkflowTaskToUser(_portalContentReviewerUser, _adminUser);

		_checkUserNotificationEventsByUsers(_adminUser);

		_assignWorkflowTaskToUser(_adminUser, _portalContentReviewerUser);

		_checkUserNotificationEventsByUsers(_portalContentReviewerUser);

		_completeWorkflowTask(_portalContentReviewerUser, Constants.APPROVE);

		blogsEntry = _blogsEntryLocalService.getBlogsEntry(
			blogsEntry.getEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, blogsEntry.getStatus());

		_deactivateWorkflow(BlogsEntry.class.getName(), 0, 0);
	}

	@Test
	public void testDeleteUserWithWorkflowTaskAssigned() throws Exception {
		_activateSingleApproverWorkflow(BlogsEntry.class.getName(), 0, 0);

		_addBlogsEntry();

		_checkUserNotificationEventsByUsers(_siteAdminUser);

		User user = _createUser(RoleConstants.SITE_ADMINISTRATOR);

		_assignWorkflowTaskToUser(_siteAdminUser, user);

		Assert.assertEquals(
			1,
			_workflowTaskManager.getWorkflowTaskCountByUser(
				user.getCompanyId(), user.getUserId(), false));

		_userLocalService.deleteUser(user);

		Assert.assertEquals(
			0,
			_workflowTaskManager.getWorkflowTaskCountByUser(
				user.getCompanyId(), user.getUserId(), false));

		_deactivateWorkflow(BlogsEntry.class.getName(), 0, 0);
	}

	@Test
	public void testMovetoTrashAndRestoreFromTrashPendingDLFileEntryInDLFolderWithWorkflow()
		throws Exception {

		Folder folder = _addFolder();

		folder = _updateFolder(
			folder, DLFolderConstants.RESTRICTION_TYPE_WORKFLOW,
			HashMapBuilder.put(
				String.valueOf(DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL),
				"Single Approver@1"
			).build());

		FileVersion fileVersion = _addFileVersion(folder.getFolderId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion.getStatus());

		_assignWorkflowTaskToUser(
			_adminUser, _adminUser, _REVIEW, DLFileEntry.class.getName(),
			fileVersion.getFileVersionId());

		_completeWorkflowTask(
			_adminUser, Constants.APPROVE, _REVIEW, DLFileEntry.class.getName(),
			fileVersion.getFileVersionId());

		fileVersion = _dlAppService.getFileVersion(
			fileVersion.getFileVersionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion.getStatus());

		fileVersion = _updateFileVersion(fileVersion.getFileEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion.getStatus());

		FileEntry fileEntry = _dlTrashService.moveFileEntryToTrash(
			fileVersion.getFileEntryId());

		WorkflowInstanceLink workflowInstanceLink = _fetchWorkflowInstanceLink(
			DLFileEntryConstants.getClassName(),
			fileVersion.getFileVersionId());

		Assert.assertNull(workflowInstanceLink);

		_dlTrashService.restoreFileEntryFromTrash(fileVersion.getFileEntryId());

		fileVersion = fileEntry.getLatestFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, fileVersion.getStatus());
	}

	@Test
	public void testRejectDLFileEntry() throws Exception {
		_activateSingleApproverWorkflow(DLFolder.class.getName(), 0, -1);

		FileVersion fileVersion1 = _addFileVersion(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion1.getStatus());

		_assignWorkflowTaskToUser(
			_adminUser, _adminUser, _REVIEW, DLFileEntry.class.getName(),
			fileVersion1.getFileVersionId());

		_completeWorkflowTask(
			_adminUser, Constants.REJECT, _REVIEW, DLFileEntry.class.getName(),
			fileVersion1.getFileVersionId());

		fileVersion1 = _dlAppService.getFileVersion(
			fileVersion1.getFileVersionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion1.getStatus());

		_getWorkflowTask(
			_adminUser, Constants.UPDATE, false, DLFileEntry.class.getName(),
			fileVersion1.getFileVersionId());

		_deactivateWorkflow(DLFolder.class.getName(), 0, -1);
	}

	@Test
	public void testRejectWorkflowBlogsEntryAndViewAssignee() throws Exception {
		_activateSingleApproverWorkflow(BlogsEntry.class.getName(), 0, 0);

		BlogsEntry blogsEntry = _addBlogsEntry();

		_checkUserNotificationEventsByUsers(
			_adminUser, _portalContentReviewerUser, _siteAdminUser);

		_assignWorkflowTaskToUser(_adminUser, _portalContentReviewerUser);

		_checkUserNotificationEventsByUsers(_portalContentReviewerUser);

		_completeWorkflowTask(_portalContentReviewerUser, Constants.REJECT);

		_checkUserNotificationEventsByUsers(_adminUser);

		blogsEntry = _blogsEntryLocalService.getBlogsEntry(
			blogsEntry.getEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, blogsEntry.getStatus());

		WorkflowTask workflowTask = _getWorkflowTask();

		Assert.assertEquals(
			_adminUser.getUserId(), workflowTask.getAssigneeUserId());

		_deactivateWorkflow(BlogsEntry.class.getName(), 0, 0);
	}

	@Test
	public void testSearchWorkflowTaskByAssetTitle1() throws Exception {
		_activateSingleApproverWorkflow(BlogsEntry.class.getName(), 0, 0);

		BlogsEntry blogsEntry = _addBlogsEntry();

		int total = _searchCount(blogsEntry.getTitle());

		Assert.assertEquals(1, total);

		total = _searchCount(RandomTestUtil.randomString());

		Assert.assertEquals(0, total);

		_deactivateWorkflow(BlogsEntry.class.getName(), 0, 0);
	}

	@Test
	public void testSearchWorkflowTaskByAssetTitle2() throws Exception {
		_activateSingleApproverWorkflow(
			JournalFolder.class.getName(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL);

		JournalArticle article = _addJournalArticle(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		int total = _searchCount(article.getTitle());

		Assert.assertEquals(1, total);

		total = _searchCount(RandomTestUtil.randomString());

		Assert.assertEquals(0, total);

		_deactivateWorkflow(
			JournalFolder.class.getName(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL);
	}

	@Test
	public void testSearchWorkflowTaskByDeletedAsset() throws Exception {
		_activateSingleApproverWorkflow(
			JournalFolder.class.getName(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL);

		JournalArticle article = _addJournalArticle(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		int total = _searchCount(article.getTitle());

		Assert.assertEquals(1, total);

		_journalArticleLocalService.deleteArticle(article);

		total = _searchCount(article.getTitle());

		Assert.assertEquals(0, total);

		_deactivateWorkflow(
			JournalFolder.class.getName(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL);
	}

	@Test
	public void testSearchWorkflowTaskByUserRoles() throws Exception {
		_activateSingleApproverWorkflow(BlogsEntry.class.getName(), 0, 0);

		_addBlogsEntry();

		int total = _searchCountByUserRoles(_siteContentReviewerUser);

		Assert.assertEquals(1, total);

		_deactivateWorkflow(BlogsEntry.class.getName(), 0, 0);
	}

	@Test
	public void testSearchWorkflowTaskByUserRolesWhenGroupIsInactive()
		throws Exception {

		_activateSingleApproverWorkflow(BlogsEntry.class.getName(), 0, 0);

		_addBlogsEntry();

		_group.setActive(false);

		_groupLocalService.updateGroup(_group);

		int total = _searchCountByUserRoles(_siteContentReviewerUser);

		Assert.assertEquals(1, total);

		_deactivateWorkflow(BlogsEntry.class.getName(), 0, 0);
	}

	@Test
	public void testUpdateDueDate() throws Exception {
		_activateSingleApproverWorkflow(BlogsEntry.class.getName(), 0, 0);

		BlogsEntry blogsEntry = _addBlogsEntry();

		WorkflowTask workflowTask = _getWorkflowTask();

		Date date = new Date(System.currentTimeMillis() + Time.DAY);

		workflowTask = _workflowTaskManager.updateDueDate(
			_siteAdminUser.getCompanyId(), _siteAdminUser.getUserId(),
			workflowTask.getWorkflowTaskId(), StringPool.BLANK, date);

		Assert.assertEquals(date, workflowTask.getDueDate());

		_blogsEntryLocalService.deleteEntry(blogsEntry);

		_deactivateWorkflow(BlogsEntry.class.getName(), 0, 0);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	private void _activateSingleApproverWorkflow(
			long groupId, String className, long classPK, long typePK)
		throws Exception {

		_activateWorkflow(
			groupId, className, classPK, typePK, "Single Approver", 1);
	}

	private void _activateSingleApproverWorkflow(
			String className, long classPK, long typePK)
		throws Exception {

		_activateWorkflow(
			_group.getGroupId(), className, classPK, typePK, "Single Approver",
			1);
	}

	private void _activateWorkflow(
			long groupId, String className, long classPK, long typePK,
			String workflowDefinitionName, int workflowDefinitionVersion)
		throws Exception {

		_workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(
			_adminUser.getUserId(), _company.getCompanyId(), groupId, className,
			classPK, typePK, workflowDefinitionName, workflowDefinitionVersion);
	}

	private void _activateWorkflow(
			String className, long classPK, long typePK,
			String workflowDefinitionName, int workflowDefinitionVersion)
		throws Exception {

		_activateWorkflow(
			_group.getGroupId(), className, classPK, typePK,
			workflowDefinitionName, workflowDefinitionVersion);
	}

	private BlogsEntry _addBlogsEntry() throws Exception {
		return _addBlogsEntry(_adminUser);
	}

	private BlogsEntry _addBlogsEntry(User user) throws Exception {
		return _blogsEntryLocalService.addEntry(
			user.getUserId(), StringUtil.randomString(),
			StringUtil.randomString(), new Date(), _serviceContext);
	}

	private DLFileEntryType _addFileEntryType() throws Exception {
		LocalizedValuesMap localizedValuesMap = new LocalizedValuesMap(
			"defaultValue");

		localizedValuesMap.put(LocaleUtil.US, RandomTestUtil.randomString());

		Map<Locale, String> map = LocalizationUtil.getMap(localizedValuesMap);

		DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAttribute(
			"ddmForm", DDMBeanTranslatorUtil.translate(ddmForm));

		return _dlFileEntryTypeLocalService.addFileEntryType(
			_adminUser.getUserId(), _group.getGroupId(), null, map, map,
			new long[0], serviceContext);
	}

	private FileVersion _addFileVersion(long folderId) throws Exception {
		return _addFileVersion(folderId, 0);
	}

	private FileVersion _addFileVersion(long folderId, long fileEntryTypeId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAttribute("fileEntryTypeId", fileEntryTypeId);

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			_adminUser.getUserId(), _group.getGroupId(), folderId,
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			TestDataConstants.TEST_BYTE_ARRAY, serviceContext);

		return fileEntry.getLatestFileVersion();
	}

	private Folder _addFolder() throws Exception {
		return _dlAppService.addFolder(
			_group.getGroupId(), 0, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _serviceContext);
	}

	private JournalArticle _addJournalArticle(long folderId) throws Exception {
		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		return _addJournalArticle(folderId, ddmStructure);
	}

	private JournalArticle _addJournalArticle(
			long folderId, DDMStructure ddmStructure)
		throws Exception {

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			_portal.getClassNameId(JournalArticle.class));

		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.getDefault(), RandomTestUtil.randomString()
		).build();

		Map<Locale, String> descriptionMap = HashMapBuilder.put(
			LocaleUtil.getDefault(), RandomTestUtil.randomString()
		).build();

		String content = DDMStructureTestUtil.getSampleStructuredContent();

		return _journalArticleLocalService.addArticle(
			_adminUser.getUserId(), _group.getGroupId(), folderId, titleMap,
			descriptionMap, content, ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey(), _serviceContext);
	}

	private JournalFolder _addJournalFolder() throws Exception {
		return _journalFolderLocalService.addFolder(
			_adminUser.getUserId(), _group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_serviceContext);
	}

	private JournalFolder _addJournalFolder(
			long ddmStructureId, int restrictionType)
		throws Exception {

		long[] ddmStructureIds = {ddmStructureId};

		JournalFolder folder = _addJournalFolder();

		return _journalFolderLocalService.updateFolder(
			_adminUser.getUserId(), _group.getGroupId(), folder.getFolderId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ddmStructureIds, restrictionType, false, _serviceContext);
	}

	private DDLRecord _addRecord(DDLRecordSet recordSet) throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			RandomTestUtil.randomString());

		DDMFormValues ddmFormValues = _createDDMFormValues(ddmForm);

		return _ddlRecordLocalService.addRecord(
			_adminUser.getUserId(), _group.getGroupId(),
			recordSet.getRecordSetId(),
			DDLRecordConstants.DISPLAY_INDEX_DEFAULT, ddmFormValues,
			_serviceContext);
	}

	private DDLRecordSet _addRecordSet() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			RandomTestUtil.randomString());

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				_portal.getClassNameId(DDLRecordSet.class), _group);

		DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
			ddmForm, StorageType.JSON.toString());

		return _ddlRecordSetLocalService.addRecordSet(
			_adminUser.getUserId(), _group.getGroupId(),
			ddmStructure.getStructureId(), null,
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			null, DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT,
			DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS, _serviceContext);
	}

	private void _assignWorkflowTaskToUser(User user, User assigneeUser)
		throws Exception {

		_assignWorkflowTaskToUser(user, assigneeUser, null, null, 0);
	}

	private void _assignWorkflowTaskToUser(
			User user, User assigneeUser, String taskName)
		throws Exception {

		_assignWorkflowTaskToUser(user, assigneeUser, taskName, null, 0);
	}

	private void _assignWorkflowTaskToUser(
			User user, User assigneeUser, String taskName, String className,
			long classPK)
		throws Exception {

		WorkflowTask workflowTask = _getWorkflowTask(
			user, taskName, false, className, classPK);

		PermissionChecker userPermissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		PermissionThreadLocal.setPermissionChecker(userPermissionChecker);

		_workflowTaskManager.assignWorkflowTaskToUser(
			_group.getCompanyId(), user.getUserId(),
			workflowTask.getWorkflowTaskId(), assigneeUser.getUserId(),
			StringPool.BLANK, null, null);
	}

	private void _checkUserNotificationEventsByUsers(User... users) {
		for (User user : users) {
			List<UserNotificationEvent> userNotificationEvents =
				_userNotificationEventLocalService.
					getArchivedUserNotificationEvents(
						user.getUserId(),
						UserNotificationDeliveryConstants.TYPE_WEBSITE, false);

			Assert.assertEquals(
				userNotificationEvents.toString(), 1,
				userNotificationEvents.size());

			UserNotificationEvent userNotificationEvent =
				userNotificationEvents.get(0);

			userNotificationEvent.setArchived(true);

			_userNotificationEventLocalService.updateUserNotificationEvent(
				userNotificationEvent);
		}
	}

	private void _completeWorkflowTask(User user, String transition)
		throws Exception {

		_completeWorkflowTask(user, transition, null, null, 0);
	}

	private void _completeWorkflowTask(
			User user, String transition, String taskName)
		throws Exception {

		_completeWorkflowTask(user, transition, taskName, null, 0);
	}

	private void _completeWorkflowTask(
			User user, String transition, String taskName, String className,
			long classPK)
		throws Exception {

		WorkflowTask workflowTask = _getWorkflowTask(
			user, taskName, false, className, classPK);

		PermissionChecker userPermissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		PermissionThreadLocal.setPermissionChecker(userPermissionChecker);

		_workflowTaskManager.completeWorkflowTask(
			_group.getCompanyId(), user.getUserId(),
			workflowTask.getWorkflowTaskId(), transition, StringPool.BLANK,
			null);
	}

	private DDMFormValues _createDDMFormValues(DDMForm ddmForm) {
		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createLocalizedDDMFormFieldValue(
				RandomTestUtil.randomString(), StringPool.BLANK);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		return ddmFormValues;
	}

	private void _createJoinXorWorkflow() throws Exception {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_PROXY_MESSAGE_LISTENER_CLASS_NAME, Level.OFF)) {

			_workflowDefinitionManager.getWorkflowDefinition(
				_adminUser.getCompanyId(), _JOIN_XOR, 1);
		}
		catch (WorkflowException workflowException) {
			String content = _read("join-xor-definition.xml");

			_workflowDefinitionManager.deployWorkflowDefinition(
				_adminUser.getCompanyId(), _adminUser.getUserId(), _JOIN_XOR,
				_JOIN_XOR, content.getBytes());
		}
	}

	private Organization _createOrganization(boolean site) throws Exception {
		return _createOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID, site);
	}

	private Organization _createOrganization(
			long parentOrganizationId, boolean site)
		throws Exception {

		return _organizationLocalService.addOrganization(
			_adminUser.getUserId(), parentOrganizationId,
			StringUtil.randomString(), site);
	}

	private void _createScriptedAssignmentWorkflow() throws Exception {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_PROXY_MESSAGE_LISTENER_CLASS_NAME, Level.OFF)) {

			_workflowDefinitionManager.getWorkflowDefinition(
				_adminUser.getCompanyId(), _SCRIPTED_SINGLE_APPROVER, 1);
		}
		catch (WorkflowException workflowException) {
			String content = _read(
				"single-approver-definition-scripted-assignment.xml");

			_workflowDefinitionManager.deployWorkflowDefinition(
				_adminUser.getCompanyId(), _adminUser.getUserId(),
				_SCRIPTED_SINGLE_APPROVER, _SCRIPTED_SINGLE_APPROVER,
				content.getBytes());
		}
	}

	private void _createSiteMemberWorkflow() throws Exception {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_PROXY_MESSAGE_LISTENER_CLASS_NAME, Level.OFF)) {

			_workflowDefinitionManager.getWorkflowDefinition(
				_adminUser.getCompanyId(), _SITE_MEMBER_SINGLE_APPROVER, 1);
		}
		catch (WorkflowException workflowException) {
			String content = _read(
				"single-approver-definition-site-member.xml");

			_workflowDefinitionManager.deployWorkflowDefinition(
				_adminUser.getCompanyId(), _adminUser.getUserId(),
				_SITE_MEMBER_SINGLE_APPROVER, _SITE_MEMBER_SINGLE_APPROVER,
				content.getBytes());
		}
	}

	private User _createUser(String roleName) throws Exception {
		return _createUser(roleName, _group, true);
	}

	private User _createUser(String roleName, Group group) throws Exception {
		return _createUser(roleName, group, true);
	}

	private User _createUser(
			String roleName, Group group, boolean addUserToRole)
		throws Exception {

		User user = UserTestUtil.addUser(
			_company.getCompanyId(), _companyAdminUser.getUserId(),
			RandomTestUtil.randomString(
				NumericStringRandomizerBumper.INSTANCE,
				UniqueStringRandomizerBumper.INSTANCE),
			LocaleUtil.getDefault(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new long[] {group.getGroupId()},
			ServiceContextTestUtil.getServiceContext());

		Role role = _roleLocalService.getRole(
			_company.getCompanyId(), roleName);

		if (addUserToRole) {
			_userLocalService.addRoleUser(role.getRoleId(), user);
		}

		_userGroupRoleLocalService.addUserGroupRoles(
			new long[] {user.getUserId()}, group.getGroupId(),
			role.getRoleId());

		return user;
	}

	private void _deactivateWorkflow(
			long groupId, String className, long classPK, long typePK)
		throws Exception {

		_workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(
			_adminUser.getUserId(), _company.getCompanyId(), groupId, className,
			classPK, typePK, null);
	}

	private void _deactivateWorkflow(
			String className, long classPK, long typePK)
		throws Exception {

		_deactivateWorkflow(_group.getGroupId(), className, classPK, typePK);
	}

	private WorkflowInstanceLink _fetchWorkflowInstanceLink(
			String className, long classPK)
		throws Exception {

		return _workflowInstanceLinkLocalService.fetchWorkflowInstanceLink(
			_adminUser.getCompanyId(), _adminUser.getGroupId(), className,
			classPK);
	}

	private String _getBasePath() {
		return "com/liferay/portal/workflow/kaleo/dependencies/";
	}

	private DLFileEntryType _getBasicFileEntryType() throws Exception {
		return _dlFileEntryTypeLocalService.getFileEntryType(
			0, "BASIC-DOCUMENT");
	}

	private WorkflowInstance _getWorkflowInstance(
			String className, long classPK)
		throws Exception {

		return _getWorkflowInstance(className, classPK, true);
	}

	private WorkflowInstance _getWorkflowInstance(
			String className, long classPK, boolean completed)
		throws Exception {

		List<WorkflowInstance> workflowInstances =
			_workflowInstanceManager.getWorkflowInstances(
				_adminUser.getCompanyId(), _adminUser.getUserId(), className,
				classPK, completed, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			workflowInstances.toString(), 1, workflowInstances.size());

		return workflowInstances.get(0);
	}

	private WorkflowTask _getWorkflowTask() throws Exception {
		return _getWorkflowTask(_adminUser, null, false, null, 0);
	}

	private WorkflowTask _getWorkflowTask(
			User user, String taskName, boolean completed, String className,
			long classPK)
		throws Exception {

		List<WorkflowTask> workflowTasks = _getWorkflowTasks(user, completed);

		WorkflowInstance workflowInstance = null;

		if (Validator.isNotNull(className) && (classPK > 0)) {
			workflowInstance = _getWorkflowInstance(
				className, classPK, completed);

			if (workflowTasks.isEmpty()) {
				workflowTasks.addAll(
					_workflowTaskManager.getWorkflowTasksByWorkflowInstance(
						user.getCompanyId(), user.getUserId(),
						workflowInstance.getWorkflowInstanceId(), completed,
						QueryUtil.ALL_POS, QueryUtil.ALL_POS, null));
			}
		}

		for (WorkflowTask workflowTask : workflowTasks) {
			if (Objects.equals(taskName, workflowTask.getName())) {
				if ((workflowInstance != null) &&
					(workflowInstance.getWorkflowInstanceId() !=
						workflowTask.getWorkflowInstanceId())) {

					continue;
				}

				return workflowTask;
			}
		}

		Assert.assertNull(taskName);

		Assert.assertNull(className);

		Assert.assertEquals(workflowTasks.toString(), 1, workflowTasks.size());

		return workflowTasks.get(0);
	}

	private List<WorkflowTask> _getWorkflowTasks(User user, boolean completed)
		throws Exception {

		List<WorkflowTask> workflowTasks = new ArrayList<>();

		workflowTasks.addAll(
			_workflowTaskManager.getWorkflowTasksByUserRoles(
				user.getCompanyId(), user.getUserId(), completed,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null));

		workflowTasks.addAll(
			_workflowTaskManager.getWorkflowTasksByUser(
				user.getCompanyId(), user.getUserId(), completed,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null));

		Assert.assertFalse(workflowTasks.isEmpty());

		return workflowTasks;
	}

	private boolean _hasAssignableUsers(User user) throws Exception {
		WorkflowTask workflowTask = _getWorkflowTask(
			user, null, false, null, 0);

		return _workflowTaskManager.hasAssignableUsers(
			user.getCompanyId(), workflowTask.getWorkflowTaskId());
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz.getClassLoader(), _getBasePath() + fileName);
	}

	private int _searchCount(String keywords) throws Exception {
		return _workflowTaskManager.searchCount(
			_adminUser.getCompanyId(), _adminUser.getUserId(), keywords,
			new String[] {keywords}, null, null, null, null, null, null, false,
			true, null, null, false);
	}

	private int _searchCountByUserRoles(User user) throws Exception {
		return _workflowTaskManager.searchCount(
			user.getCompanyId(), user.getUserId(), null, null, null, null, null,
			null, null, null, false, true, null, null, false);
	}

	private void _setUpPermissionThreadLocal() throws Exception {
		_permissionChecker = PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			new SimplePermissionChecker() {
				{
					init(_companyAdminUser);
				}

				@Override
				public boolean hasOwnerPermission(
					long companyId, String name, String primKey, long ownerId,
					String actionId) {

					return true;
				}

			});
	}

	private void _setUpPrincipalThreadLocal() throws Exception {
		_name = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(_companyAdminUser.getUserId());
	}

	private void _setUpUsers() throws Exception {
		_adminUser = _createUser(RoleConstants.ADMINISTRATOR);

		_portalContentReviewerUser = _createUser(
			RoleConstants.PORTAL_CONTENT_REVIEWER);

		_siteAdminUser = _createUser(RoleConstants.SITE_ADMINISTRATOR);

		_siteContentReviewerUser = _createUser(
			RoleConstants.SITE_CONTENT_REVIEWER);

		_siteMemberUser = _createUser(RoleConstants.SITE_MEMBER);
	}

	private void _setUpWorkflow() throws Exception {
		_createJoinXorWorkflow();
		_createScriptedAssignmentWorkflow();
		_createSiteMemberWorkflow();
	}

	private FileVersion _updateFileVersion(long fileEntryId) throws Exception {
		FileEntry fileEntry = _dlAppService.updateFileEntry(
			fileEntryId, StringPool.BLANK, ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomString(), StringPool.BLANK, null,
			DLVersionNumberIncrease.AUTOMATIC, null, 0, _serviceContext);

		return fileEntry.getLatestFileVersion();
	}

	private Folder _updateFolder(Folder folder, int restrictionType)
		throws Exception {

		return _updateFolder(folder, restrictionType, -1, new HashMap<>());
	}

	private Folder _updateFolder(
			Folder folder, int restrictionType, long defaultFileEntryTypeId,
			Map<String, String> dlFileEntryTypeMap)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAttribute("restrictionType", restrictionType);

		if (defaultFileEntryTypeId > -1) {
			serviceContext.setAttribute(
				"defaultFileEntryTypeId", defaultFileEntryTypeId);
		}

		serviceContext.setAttribute(
			"dlFileEntryTypesSearchContainerPrimaryKeys",
			StringUtil.merge(dlFileEntryTypeMap.keySet()));

		dlFileEntryTypeMap.forEach(
			(dlFileEntryType, workflowDefinition) ->
				serviceContext.setAttribute(
					"workflowDefinition" + dlFileEntryType,
					workflowDefinition));

		return _dlAppService.updateFolder(
			folder.getFolderId(), folder.getName(), folder.getDescription(),
			serviceContext);
	}

	private Folder _updateFolder(
			Folder folder, int restrictionType,
			Map<String, String> dlFileEntryTypeMap)
		throws Exception {

		return _updateFolder(folder, restrictionType, -1, dlFileEntryTypeMap);
	}

	private static final String _JOIN_XOR = "Join Xor";

	private static final String _ORGANIZATION_CONTENT_REVIEWER =
		"Organization Content Reviewer";

	private static final String _PROXY_MESSAGE_LISTENER_CLASS_NAME =
		"com.liferay.portal.kernel.messaging.proxy.ProxyMessageListener";

	private static final String _REVIEW = "review";

	private static final String _SCRIPTED_SINGLE_APPROVER =
		"Scripted Single Approver";

	private static final String _SITE_MEMBER_SINGLE_APPROVER =
		"Site Member Single Approver";

	private static Configuration _configuration;

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	private User _adminUser;

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	private Company _company;
	private User _companyAdminUser;

	@Inject
	private DDLRecordLocalService _ddlRecordLocalService;

	@Inject
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLAppService _dlAppService;

	@Inject
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Inject
	private DLTrashService _dlTrashService;

	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private JournalFolderLocalService _journalFolderLocalService;

	private String _name;

	@Inject
	private OrganizationLocalService _organizationLocalService;

	private PermissionChecker _permissionChecker;

	@Inject
	private Portal _portal;

	private User _portalContentReviewerUser;

	@Inject
	private RoleLocalService _roleLocalService;

	private ServiceContext _serviceContext;
	private User _siteAdminUser;
	private User _siteContentReviewerUser;
	private User _siteMemberUser;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private UserLocalService _userLocalService;

	@Inject
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

	@Inject
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Inject
	private WorkflowDefinitionManager _workflowDefinitionManager;

	@Inject
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

	@Inject
	private WorkflowInstanceManager _workflowInstanceManager;

	@Inject
	private WorkflowTaskManager _workflowTaskManager;

}