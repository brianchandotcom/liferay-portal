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

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalServiceUtil;
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
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
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
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalServiceUtil;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestDataConstants;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.security.permission.SimplePermissionChecker;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Inácio Nery
 */
public abstract class BaseWorkflowTaskManagerTestCase {

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
		company = CompanyTestUtil.addCompany();

		companyAdminUser = UserTestUtil.addCompanyAdminUser(company);

		group = GroupTestUtil.addGroup(
			company.getCompanyId(), companyAdminUser.getUserId(), 0);

		serviceContext = ServiceContextTestUtil.getServiceContext(
			group, companyAdminUser.getUserId());

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

	protected void activateSingleApproverWorkflow(
			long groupId, String className, long classPK, long typePK)
		throws PortalException {

		activateWorkflow(
			groupId, className, classPK, typePK, "Single Approver", 1);
	}

	protected void activateSingleApproverWorkflow(
			String className, long classPK, long typePK)
		throws PortalException {

		activateWorkflow(
			group.getGroupId(), className, classPK, typePK, "Single Approver",
			1);
	}

	protected void activateWorkflow(
			long groupId, String className, long classPK, long typePK,
			String workflowDefinitionName, int workflowDefinitionVersion)
		throws PortalException {

		WorkflowDefinitionLinkLocalServiceUtil.updateWorkflowDefinitionLink(
			adminUser.getUserId(), company.getCompanyId(), groupId, className,
			classPK, typePK, workflowDefinitionName, workflowDefinitionVersion);
	}

	protected void activateWorkflow(
			String className, long classPK, long typePK,
			String workflowDefinitionName, int workflowDefinitionVersion)
		throws PortalException {

		activateWorkflow(
			group.getGroupId(), className, classPK, typePK,
			workflowDefinitionName, workflowDefinitionVersion);
	}

	protected BlogsEntry addBlogsEntry() throws PortalException {
		return addBlogsEntry(adminUser);
	}

	protected BlogsEntry addBlogsEntry(User user) throws PortalException {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_MAIL_ENGINE_CLASS_NAME, Level.OFF)) {

			return BlogsEntryLocalServiceUtil.addEntry(
				user.getUserId(), StringUtil.randomString(),
				StringUtil.randomString(), new Date(), serviceContext);
		}
	}

	protected DLFileEntryType addFileEntryType() throws Exception {
		LocalizedValuesMap localizedValuesMap = new LocalizedValuesMap(
			"defaultValue");

		localizedValuesMap.put(LocaleUtil.US, RandomTestUtil.randomString());

		Map<Locale, String> map = LocalizationUtil.getMap(localizedValuesMap);

		DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setAttribute(
			"ddmForm", DDMBeanTranslatorUtil.translate(ddmForm));

		DLFileEntryType fileEntryType =
			DLFileEntryTypeLocalServiceUtil.addFileEntryType(
				adminUser.getUserId(), group.getGroupId(), null, map, map,
				new long[0], serviceContext);

		_dlFileEntryType.add(fileEntryType);

		return fileEntryType;
	}

	protected FileVersion addFileVersion(long folderId) throws Exception {
		return addFileVersion(folderId, 0);
	}

	protected FileVersion addFileVersion(long folderId, long fileEntryTypeId)
		throws Exception {

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_MAIL_ENGINE_CLASS_NAME, Level.OFF)) {

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			serviceContext.setAttribute("fileEntryTypeId", fileEntryTypeId);

			FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
				adminUser.getUserId(), group.getGroupId(), folderId,
				RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN,
				RandomTestUtil.randomString(), StringPool.BLANK,
				StringPool.BLANK, TestDataConstants.TEST_BYTE_ARRAY,
				serviceContext);

			DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
				fileEntry.getFileEntryId());

			_dlFileEntries.add(dlFileEntry);

			_dlFileVersions.add(dlFileEntry.getFileVersion());

			return fileEntry.getLatestFileVersion();
		}
	}

	protected Folder addFolder() throws PortalException {
		Folder folder = DLAppServiceUtil.addFolder(
			group.getGroupId(), 0, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		_dlFolders.add(
			DLFolderLocalServiceUtil.getDLFolder(folder.getFolderId()));

		return folder;
	}

	protected JournalArticle addJournalArticle(long folderId) throws Exception {
		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		return addJournalArticle(folderId, ddmStructure);
	}

	protected JournalArticle addJournalArticle(
			long folderId, DDMStructure ddmStructure)
		throws Exception {

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_MAIL_ENGINE_CLASS_NAME, Level.OFF)) {

			DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
				group.getGroupId(), ddmStructure.getStructureId(),
				PortalUtil.getClassNameId(JournalArticle.class));

			Map<Locale, String> titleMap = HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build();

			Map<Locale, String> descriptionMap = HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build();

			String content = DDMStructureTestUtil.getSampleStructuredContent();

			return JournalArticleLocalServiceUtil.addArticle(
				adminUser.getUserId(), group.getGroupId(), folderId, titleMap,
				descriptionMap, content, ddmStructure.getStructureKey(),
				ddmTemplate.getTemplateKey(), serviceContext);
		}
	}

	protected JournalFolder addJournalFolder() throws PortalException {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_MAIL_ENGINE_CLASS_NAME, Level.OFF)) {

			return JournalFolderLocalServiceUtil.addFolder(
				adminUser.getUserId(), group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				serviceContext);
		}
	}

	protected JournalFolder addJournalFolder(
			long ddmStructureId, int restrictionType)
		throws PortalException {

		long[] ddmStructureIds = {ddmStructureId};

		JournalFolder folder = addJournalFolder();

		return JournalFolderLocalServiceUtil.updateFolder(
			adminUser.getUserId(), group.getGroupId(), folder.getFolderId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ddmStructureIds, restrictionType, false, serviceContext);
	}

	protected DDLRecord addRecord(DDLRecordSet recordSet)
		throws PortalException {

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_MAIL_ENGINE_CLASS_NAME, Level.OFF)) {

			DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
				RandomTestUtil.randomString());

			DDMFormValues ddmFormValues = _createDDMFormValues(ddmForm);

			return DDLRecordLocalServiceUtil.addRecord(
				adminUser.getUserId(), group.getGroupId(),
				recordSet.getRecordSetId(),
				DDLRecordConstants.DISPLAY_INDEX_DEFAULT, ddmFormValues,
				serviceContext);
		}
	}

	protected DDLRecordSet addRecordSet() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			RandomTestUtil.randomString());

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(DDLRecordSet.class), group);

		DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
			ddmForm, StorageType.JSON.toString());

		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.US, RandomTestUtil.randomString()
		).build();

		return DDLRecordSetLocalServiceUtil.addRecordSet(
			adminUser.getUserId(), group.getGroupId(),
			ddmStructure.getStructureId(), null, nameMap, null,
			DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT,
			DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS, serviceContext);
	}

	protected void assignWorkflowTaskToUser(User user, User assigneeUser)
		throws Exception {

		assignWorkflowTaskToUser(user, assigneeUser, null, null, 0);
	}

	protected void assignWorkflowTaskToUser(
			User user, User assigneeUser, String taskName)
		throws Exception {

		assignWorkflowTaskToUser(user, assigneeUser, taskName, null, 0);
	}

	protected void assignWorkflowTaskToUser(
			User user, User assigneeUser, String taskName, String className,
			long classPK)
		throws Exception {

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_MAIL_ENGINE_CLASS_NAME, Level.OFF)) {

			WorkflowTask workflowTask = getWorkflowTask(
				user, taskName, false, className, classPK);

			PermissionChecker userPermissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(userPermissionChecker);

			WorkflowTaskManagerUtil.assignWorkflowTaskToUser(
				group.getCompanyId(), user.getUserId(),
				workflowTask.getWorkflowTaskId(), assigneeUser.getUserId(),
				StringPool.BLANK, null, null);
		}
	}

	protected void checkUserNotificationEventsByUsers(User... users) {
		for (User user : users) {
			List<UserNotificationEvent> userNotificationEvents =
				UserNotificationEventLocalServiceUtil.
					getArchivedUserNotificationEvents(
						user.getUserId(),
						UserNotificationDeliveryConstants.TYPE_WEBSITE, false);

			Assert.assertEquals(
				userNotificationEvents.toString(), 1,
				userNotificationEvents.size());

			UserNotificationEvent userNotificationEvent =
				userNotificationEvents.get(0);

			userNotificationEvent.setArchived(true);

			UserNotificationEventLocalServiceUtil.updateUserNotificationEvent(
				userNotificationEvent);
		}
	}

	protected void completeWorkflowTask(User user, String transition)
		throws Exception {

		completeWorkflowTask(user, transition, null, null, 0);
	}

	protected void completeWorkflowTask(
			User user, String transition, String taskName)
		throws Exception {

		completeWorkflowTask(user, transition, taskName, null, 0);
	}

	protected void completeWorkflowTask(
			User user, String transition, String taskName, String className,
			long classPK)
		throws Exception {

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_MAIL_ENGINE_CLASS_NAME, Level.OFF)) {

			WorkflowTask workflowTask = getWorkflowTask(
				user, taskName, false, className, classPK);

			PermissionChecker userPermissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(userPermissionChecker);

			WorkflowTaskManagerUtil.completeWorkflowTask(
				group.getCompanyId(), user.getUserId(),
				workflowTask.getWorkflowTaskId(), transition, StringPool.BLANK,
				null);
		}
	}

	protected Organization createOrganization(boolean site)
		throws PortalException {

		return createOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID, site);
	}

	protected Organization createOrganization(
			long parentOrganizationId, boolean site)
		throws PortalException {

		return OrganizationLocalServiceUtil.addOrganization(
			adminUser.getUserId(), parentOrganizationId,
			StringUtil.randomString(), site);
	}

	protected User createUser(String roleName) throws Exception {
		return _createUser(roleName, group, true);
	}

	protected User createUser(String roleName, Group group) throws Exception {
		return _createUser(roleName, group, true);
	}

	protected void deactivateWorkflow(
			long groupId, String className, long classPK, long typePK)
		throws PortalException {

		WorkflowDefinitionLinkLocalServiceUtil.updateWorkflowDefinitionLink(
			adminUser.getUserId(), company.getCompanyId(), groupId, className,
			classPK, typePK, null);
	}

	protected void deactivateWorkflow(
			String className, long classPK, long typePK)
		throws PortalException {

		deactivateWorkflow(group.getGroupId(), className, classPK, typePK);
	}

	protected WorkflowInstanceLink fetchWorkflowInstanceLink(
			String className, long classPK)
		throws WorkflowException {

		return WorkflowInstanceLinkLocalServiceUtil.fetchWorkflowInstanceLink(
			adminUser.getCompanyId(), adminUser.getGroupId(), className,
			classPK);
	}

	protected DLFileEntryType getBasicFileEntryType() throws Exception {
		return DLFileEntryTypeLocalServiceUtil.getFileEntryType(
			0, "BASIC-DOCUMENT");
	}

	protected WorkflowInstance getWorkflowInstance(
			String className, long classPK)
		throws WorkflowException {

		return _getWorkflowInstance(className, classPK, true);
	}

	protected WorkflowTask getWorkflowTask() throws Exception {
		return getWorkflowTask(adminUser, null, false, null, 0);
	}

	protected WorkflowTask getWorkflowTask(
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
					WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(
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

	protected boolean hasAssignableUsers(User user) throws Exception {
		WorkflowTask workflowTask = getWorkflowTask(user, null, false, null, 0);

		return WorkflowTaskManagerUtil.hasAssignableUsers(
			user.getCompanyId(), workflowTask.getWorkflowTaskId());
	}

	protected int searchCount(String keywords) throws Exception {
		return WorkflowTaskManagerUtil.searchCount(
			adminUser.getCompanyId(), adminUser.getUserId(), keywords,
			new String[] {keywords}, null, null, null, null, null, false, true,
			null, false);
	}

	protected int searchCountByUserRoles(User user) throws Exception {
		return WorkflowTaskManagerUtil.searchCount(
			user.getCompanyId(), user.getUserId(), null, null, null, null, null,
			null, null, false, true, null, false);
	}

	protected FileVersion updateFileVersion(long fileEntryId) throws Exception {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_MAIL_ENGINE_CLASS_NAME, Level.OFF)) {

			FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
				fileEntryId, StringPool.BLANK, ContentTypes.TEXT_PLAIN,
				RandomTestUtil.randomString(), StringPool.BLANK, null,
				DLVersionNumberIncrease.AUTOMATIC, null, 0, serviceContext);

			return fileEntry.getLatestFileVersion();
		}
	}

	protected Folder updateFolder(Folder folder, int restrictionType)
		throws PortalException {

		return updateFolder(folder, restrictionType, -1, new HashMap<>());
	}

	protected Folder updateFolder(
			Folder folder, int restrictionType, long defaultFileEntryTypeId,
			Map<String, String> dlFileEntryTypeMap)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

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

		return DLAppServiceUtil.updateFolder(
			folder.getFolderId(), folder.getName(), folder.getDescription(),
			serviceContext);
	}

	protected Folder updateFolder(
			Folder folder, int restrictionType,
			Map<String, String> dlFileEntryTypeMap)
		throws PortalException {

		return updateFolder(folder, restrictionType, -1, dlFileEntryTypeMap);
	}

	protected static final String JOIN_XOR = "Join Xor";

	protected static final String ORGANIZATION_CONTENT_REVIEWER =
		"Organization Content Reviewer";

	protected static final String REVIEW = "review";

	protected static final String SCRIPTED_SINGLE_APPROVER =
		"Scripted Single Approver";

	protected static final String SITE_MEMBER_SINGLE_APPROVER =
		"Site Member Single Approver";

	protected User adminUser;

	@DeleteAfterTestRun
	protected Company company;

	protected User companyAdminUser;
	protected Group group;
	protected User portalContentReviewerUser;
	protected ServiceContext serviceContext;
	protected User siteAdminUser;
	protected User siteContentReviewerUser;
	protected User siteMemberUser;

	@Inject
	protected UserLocalService userLocalService;

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

			WorkflowDefinitionManagerUtil.getWorkflowDefinition(
				adminUser.getCompanyId(), JOIN_XOR, 1);
		}
		catch (WorkflowException workflowException) {
			String content = _read("join-xor-definition.xml");

			WorkflowDefinitionManagerUtil.deployWorkflowDefinition(
				adminUser.getCompanyId(), adminUser.getUserId(), JOIN_XOR,
				JOIN_XOR, content.getBytes());
		}
	}

	private void _createScriptedAssignmentWorkflow() throws Exception {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_PROXY_MESSAGE_LISTENER_CLASS_NAME, Level.OFF)) {

			WorkflowDefinitionManagerUtil.getWorkflowDefinition(
				adminUser.getCompanyId(), SCRIPTED_SINGLE_APPROVER, 1);
		}
		catch (WorkflowException workflowException) {
			String content = _read(
				"single-approver-definition-scripted-assignment.xml");

			WorkflowDefinitionManagerUtil.deployWorkflowDefinition(
				adminUser.getCompanyId(), adminUser.getUserId(),
				SCRIPTED_SINGLE_APPROVER, SCRIPTED_SINGLE_APPROVER,
				content.getBytes());
		}
	}

	private void _createSiteMemberWorkflow() throws Exception {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_PROXY_MESSAGE_LISTENER_CLASS_NAME, Level.OFF)) {

			WorkflowDefinitionManagerUtil.getWorkflowDefinition(
				adminUser.getCompanyId(), SITE_MEMBER_SINGLE_APPROVER, 1);
		}
		catch (WorkflowException workflowException) {
			String content = _read(
				"single-approver-definition-site-member.xml");

			WorkflowDefinitionManagerUtil.deployWorkflowDefinition(
				adminUser.getCompanyId(), adminUser.getUserId(),
				SITE_MEMBER_SINGLE_APPROVER, SITE_MEMBER_SINGLE_APPROVER,
				content.getBytes());
		}
	}

	private User _createUser(
			String roleName, Group group, boolean addUserToRole)
		throws Exception {

		User user = UserTestUtil.addUser(
			company.getCompanyId(), companyAdminUser.getUserId(),
			RandomTestUtil.randomString(
				NumericStringRandomizerBumper.INSTANCE,
				UniqueStringRandomizerBumper.INSTANCE),
			LocaleUtil.getDefault(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new long[] {group.getGroupId()},
			ServiceContextTestUtil.getServiceContext());

		Role role = RoleLocalServiceUtil.getRole(
			company.getCompanyId(), roleName);

		if (addUserToRole) {
			UserLocalServiceUtil.addRoleUser(role.getRoleId(), user);
		}

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			new long[] {user.getUserId()}, group.getGroupId(),
			role.getRoleId());

		return user;
	}

	private String _getBasePath() {
		return "com/liferay/portal/workflow/kaleo/dependencies/";
	}

	private WorkflowInstance _getWorkflowInstance(
			String className, long classPK, boolean completed)
		throws WorkflowException {

		List<WorkflowInstance> workflowInstances =
			WorkflowInstanceManagerUtil.getWorkflowInstances(
				adminUser.getCompanyId(), adminUser.getUserId(), className,
				classPK, completed, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			workflowInstances.toString(), 1, workflowInstances.size());

		return workflowInstances.get(0);
	}

	private List<WorkflowTask> _getWorkflowTasks(User user, boolean completed)
		throws Exception {

		return IdempotentRetryAssert.retryAssert(
			10, TimeUnit.SECONDS,
			() -> {
				List<WorkflowTask> workflowTasks = new ArrayList<>();

				workflowTasks.addAll(
					WorkflowTaskManagerUtil.getWorkflowTasksByUserRoles(
						user.getCompanyId(), user.getUserId(), completed,
						QueryUtil.ALL_POS, QueryUtil.ALL_POS, null));

				workflowTasks.addAll(
					WorkflowTaskManagerUtil.getWorkflowTasksByUser(
						user.getCompanyId(), user.getUserId(), completed,
						QueryUtil.ALL_POS, QueryUtil.ALL_POS, null));

				Assert.assertFalse(workflowTasks.isEmpty());

				return workflowTasks;
			});
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz.getClassLoader(), _getBasePath() + fileName);
	}

	private void _setUpPermissionThreadLocal() throws Exception {
		_permissionChecker = PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			new SimplePermissionChecker() {
				{
					init(companyAdminUser);
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

		PrincipalThreadLocal.setName(companyAdminUser.getUserId());
	}

	private void _setUpUsers() throws Exception {
		adminUser = createUser(RoleConstants.ADMINISTRATOR);

		portalContentReviewerUser = createUser(
			RoleConstants.PORTAL_CONTENT_REVIEWER);

		siteAdminUser = createUser(RoleConstants.SITE_ADMINISTRATOR);

		siteContentReviewerUser = createUser(
			RoleConstants.SITE_CONTENT_REVIEWER);

		siteMemberUser = createUser(RoleConstants.SITE_MEMBER);
	}

	private void _setUpWorkflow() throws Exception {
		_createJoinXorWorkflow();
		_createScriptedAssignmentWorkflow();
		_createSiteMemberWorkflow();
	}

	private static final String _MAIL_ENGINE_CLASS_NAME =
		"com.liferay.util.mail.MailEngine";

	private static final String _PROXY_MESSAGE_LISTENER_CLASS_NAME =
		"com.liferay.portal.kernel.messaging.proxy.ProxyMessageListener";

	private static Configuration _configuration;

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	@DeleteAfterTestRun
	private final List<DLFileEntry> _dlFileEntries = new ArrayList<>();

	@DeleteAfterTestRun
	private final List<DLFileEntryType> _dlFileEntryType = new ArrayList<>();

	@DeleteAfterTestRun
	private final List<DLFileVersion> _dlFileVersions = new ArrayList<>();

	@DeleteAfterTestRun
	private final List<DLFolder> _dlFolders = new ArrayList<>();

	private String _name;
	private PermissionChecker _permissionChecker;

}