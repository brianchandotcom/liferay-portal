/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.workflow.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@FeatureFlag("LPD-42553")
@RunWith(Arquillian.class)
public class ObjectEntryWorkflowHandlerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_objectDefinition = _addObjectDefinition();
		_objectEntryFolder = _addObjectEntryFolder();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());

		_serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
	}

	@Test
	public void testDeleteObjectEntryFolder() throws PortalException {
		_objectEntryFolder = _updateObjectEntryFolderSingleApprover(
			_serviceContext);

		long objectEntryFolderId = _objectEntryFolder.getObjectEntryFolderId();

		WorkflowDefinitionLink workflowDefinitionLink =
			_workflowDefinitionLinkLocalService.fetchWorkflowDefinitionLink(
				TestPropsValues.getCompanyId(), _group.getGroupId(),
				ObjectEntryFolder.class.getName(), objectEntryFolderId,
				ObjectDefinitionConstants.OBJECT_DEFINITION_ID_ALL, true);

		Assert.assertNotNull(workflowDefinitionLink);

		_objectEntryFolderLocalService.deleteObjectEntryFolder(
			objectEntryFolderId);

		workflowDefinitionLink =
			_workflowDefinitionLinkLocalService.fetchWorkflowDefinitionLink(
				TestPropsValues.getCompanyId(), _group.getGroupId(),
				ObjectEntryFolder.class.getName(), objectEntryFolderId,
				ObjectDefinitionConstants.OBJECT_DEFINITION_ID_ALL, true);

		Assert.assertNull(workflowDefinitionLink);
	}

	@Test
	public void testGetStatusApproved() throws PortalException {
		ObjectEntry objectEntry1 = _addObjectEntry(
			_objectEntryFolder.getObjectEntryFolderId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, objectEntry1.getStatus());

		_objectEntryFolder = _updateObjectEntryFolderSingleApprover(
			_serviceContext);

		ObjectEntry objectEntry2 = _addObjectEntry(
			_objectEntryFolder.getObjectEntryFolderId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, objectEntry2.getStatus());

		_updateStatus(objectEntry2, WorkflowConstants.STATUS_APPROVED);

		objectEntry2 = _objectEntryLocalService.getObjectEntry(
			objectEntry2.getObjectEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, objectEntry2.getStatus());
	}

	@Test
	public void testGetStatusDenied() throws PortalException {
		_objectEntryFolder = _updateObjectEntryFolderSingleApprover(
			_serviceContext);

		ObjectEntry objectEntry = _addObjectEntry(
			_objectEntryFolder.getObjectEntryFolderId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, objectEntry.getStatus());

		_updateStatus(objectEntry, WorkflowConstants.STATUS_DENIED);

		objectEntry = _objectEntryLocalService.getObjectEntry(
			objectEntry.getObjectEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_DENIED, objectEntry.getStatus());
	}

	private ObjectDefinition _addObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, null, false, false, false,
				false, false, false, null,
				LocalizedMapUtil.getLocalizedMap(StringUtil.randomString()),
				"A" + StringUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_SITE,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.emptyList(),
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(), "fieldName")));

		return _objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());
	}

	private ObjectEntry _addObjectEntry(long objectEntryFolderId)
		throws PortalException {

		return _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_objectDefinition.getObjectDefinitionId(), objectEntryFolderId,
			null,
			HashMapBuilder.<String, Serializable>put(
				"fieldName", StringUtil.randomString()
			).build(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	private ObjectEntryFolder _addObjectEntryFolder() throws Exception {
		return _objectEntryFolderLocalService.addObjectEntryFolder(
			StringUtil.randomString(), TestPropsValues.getUserId(),
			_group.getGroupId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			RandomTestUtil.randomString(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), StringUtil.randomString()
			).build(),
			StringUtil.randomString(),
			ServiceContextTestUtil.getServiceContext());
	}

	private ObjectEntryFolder _updateObjectEntryFolderSingleApprover(
			ServiceContext serviceContext)
		throws PortalException {

		serviceContext.setAttribute(
			"workflowDefinition" +
				ObjectDefinitionConstants.OBJECT_DEFINITION_ID_ALL,
			"Single Approver@1");

		return _objectEntryFolderLocalService.updateObjectEntryFolder(
			TestPropsValues.getUserId(),
			_objectEntryFolder.getObjectEntryFolderId(),
			_objectEntryFolder.getParentObjectEntryFolderId(),
			_objectEntryFolder.getDescription(),
			_objectEntryFolder.getLabelMap(), _objectEntryFolder.getName(),
			serviceContext);
	}

	private void _updateStatus(ObjectEntry objectEntry, int status)
		throws PortalException {

		WorkflowHandler<ObjectEntry> workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(
				_objectDefinition.getClassName());

		workflowHandler.updateStatus(
			status,
			HashMapBuilder.<String, Serializable>put(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK,
				String.valueOf(objectEntry.getObjectEntryId())
			).put(
				WorkflowConstants.CONTEXT_USER_ID,
				String.valueOf(TestPropsValues.getUserId())
			).put(
				"serviceContext", _serviceContext
			).build());
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private ObjectEntryFolder _objectEntryFolder;

	@Inject
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	private ServiceContext _serviceContext;

	@Inject
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}