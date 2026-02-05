/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.cmp.site.initializer.test.util.CMPTestUtil;
import com.liferay.site.cms.site.initializer.util.CMSDefaultPermissionUtil;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Stefano Motta
 */
@FeatureFlags(
	featureFlags = {@FeatureFlag("LPD-17564"), @FeatureFlag("LPD-58677")}
)
@RunWith(Arquillian.class)
public class ObjectEntryModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_auditRouter = (AuditRouter)ReflectionTestUtil.getAndSetFieldValue(
			_objectEntryModelListener, "_auditRouter",
			ProxyUtil.newProxyInstance(
				AuditRouter.class.getClassLoader(),
				new Class<?>[] {AuditRouter.class},
				(proxy, method, arguments) -> {
					_auditMessages.add((AuditMessage)arguments[0]);

					return null;
				}));

		CMPTestUtil.getOrAddGroup(ObjectEntryModelListenerTest.class);

		_cmpTaskObjectEntry = CMPTestUtil.addTaskObjectEntry();

		_cmpTaskObjectEntry = _objectEntryLocalService.partialUpdateObjectEntry(
			_cmpTaskObjectEntry.getUserId(),
			_cmpTaskObjectEntry.getObjectEntryId(),
			_cmpTaskObjectEntry.getObjectEntryFolderId(),
			HashMapBuilder.<String, Serializable>put(
				"title", RandomTestUtil.randomString()
			).build(),
			_getServiceContext("L_CMP_TASK_123"));

		_cmsAdministratorRole = _getOrAddCMSAdministratorRole(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());
		_ownerRole = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.OWNER);
		_userRole = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.USER);

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext());
	}

	@After
	public void tearDown() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_objectEntryModelListener, "_auditRouter", _auditRouter);

		_objectEntryLocalService.deleteObjectEntry(_cmpTaskObjectEntry);

		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testOnAfterCreate() throws Exception {

		// Audit router

		String title = RandomTestUtil.randomString();

		_addObjectEntry(title, "L_CMP_TASK_123");

		_assertAuditMessage("CMP_ADD_ASSET", title);

		// Resource permissions

		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), StringUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), StringUtil.randomString()
			).build(),
			DepotConstants.TYPE_SPACE,
			ServiceContextTestUtil.getServiceContext());

		Group group = depotEntry.getGroup();

		ObjectEntryFolder objectEntryFolder1 =
			_objectEntryFolderLocalService.
				getObjectEntryFolderByExternalReferenceCode(
					ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS,
					group.getGroupId(), depotEntry.getCompanyId());

		ObjectEntryFolder objectEntryFolder2 =
			_objectEntryFolderLocalService.addObjectEntryFolder(
				RandomTestUtil.randomString(), group.getGroupId(),
				group.getCreatorUserId(),
				objectEntryFolder1.getObjectEntryFolderId(), "",
				HashMapBuilder.put(
					LocaleUtil.ENGLISH, RandomTestUtil.randomString()
				).build(),
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext());

		JSONObject jsonObject = CMSDefaultPermissionUtil.getJSONObject(
			objectEntryFolder2.getCompanyId(), objectEntryFolder2.getUserId(),
			objectEntryFolder2.getExternalReferenceCode(),
			objectEntryFolder2.getModelClassName(), _filterFactory);

		String randomString = RandomTestUtil.randomString();

		jsonObject.put(
			ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS,
			JSONUtil.put(
				RoleConstants.CMS_ADMINISTRATOR,
				JSONUtil.putAll(
					ActionKeys.UPDATE, ActionKeys.VIEW, randomString)
			).put(
				RoleConstants.USER, JSONUtil.putAll(ActionKeys.VIEW)
			));

		ObjectEntry objectEntry1 = CMSDefaultPermissionUtil.fetchObjectEntry(
			objectEntryFolder2.getCompanyId(), objectEntryFolder2.getUserId(),
			objectEntryFolder2.getExternalReferenceCode(),
			objectEntryFolder2.getModelClassName(), _filterFactory);

		CMSDefaultPermissionUtil.addOrUpdateObjectEntry(
			objectEntry1.getExternalReferenceCode(),
			objectEntryFolder2.getCompanyId(), objectEntryFolder2.getUserId(),
			objectEntryFolder2.getExternalReferenceCode(),
			objectEntryFolder2.getModelClassName(), jsonObject,
			objectEntryFolder2.getGroupId(), objectEntryFolder2.getTreePath());

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMS_BASIC_WEB_CONTENT",
					objectEntryFolder2.getCompanyId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setAttribute(
			"friendlyUrlMap", new HashMap<String, String>());

		ObjectEntry objectEntry2 = _objectEntryLocalService.addObjectEntry(
			depotEntry.getGroupId(), depotEntry.getUserId(),
			objectDefinition.getObjectDefinitionId(),
			objectEntryFolder2.getObjectEntryFolderId(), "en_US",
			HashMapBuilder.<String, Serializable>put(
				"title_i18n",
				HashMapBuilder.put(
					"en_US", RandomTestUtil.randomString()
				).build()
			).build(),
			serviceContext);

		ResourcePermission resourcePermission =
			_resourcePermissionLocalService.getResourcePermission(
				objectEntry2.getCompanyId(), objectEntry2.getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntry2.getObjectEntryId()),
				_cmsAdministratorRole.getRoleId());

		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.DELETE));
		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.UPDATE));
		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.VIEW));
		Assert.assertFalse(resourcePermission.hasActionId(randomString));

		resourcePermission =
			_resourcePermissionLocalService.getResourcePermission(
				objectEntry2.getCompanyId(), objectEntry2.getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntry2.getObjectEntryId()),
				_ownerRole.getRoleId());

		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.DELETE));
		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.UPDATE));
		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.VIEW));

		resourcePermission =
			_resourcePermissionLocalService.getResourcePermission(
				objectEntry2.getCompanyId(), objectEntry2.getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntry2.getObjectEntryId()),
				_userRole.getRoleId());

		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.DELETE));
		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.UPDATE));
		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.VIEW));

		objectEntryFolder1 =
			_objectEntryFolderLocalService.
				getObjectEntryFolderByExternalReferenceCode(
					ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_FILES,
					group.getGroupId(), depotEntry.getCompanyId());

		objectEntryFolder2 =
			_objectEntryFolderLocalService.addObjectEntryFolder(
				RandomTestUtil.randomString(), group.getGroupId(),
				group.getCreatorUserId(),
				objectEntryFolder1.getObjectEntryFolderId(), "",
				HashMapBuilder.put(
					LocaleUtil.ENGLISH, RandomTestUtil.randomString()
				).build(),
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext());

		jsonObject = CMSDefaultPermissionUtil.getJSONObject(
			objectEntryFolder2.getCompanyId(), objectEntryFolder2.getUserId(),
			objectEntryFolder2.getExternalReferenceCode(),
			objectEntryFolder2.getModelClassName(), _filterFactory);

		jsonObject.put(
			ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_FILES,
			JSONUtil.put(
				RoleConstants.CMS_ADMINISTRATOR,
				JSONUtil.putAll(
					ActionKeys.DELETE, ActionKeys.VIEW, randomString)
			).put(
				RoleConstants.OWNER, JSONUtil.putAll(ActionKeys.DELETE)
			).put(
				RoleConstants.USER, JSONUtil.putAll(ActionKeys.UPDATE)
			));

		objectEntry1 = CMSDefaultPermissionUtil.fetchObjectEntry(
			objectEntryFolder2.getCompanyId(), objectEntryFolder2.getUserId(),
			objectEntryFolder2.getExternalReferenceCode(),
			objectEntryFolder2.getModelClassName(), _filterFactory);

		CMSDefaultPermissionUtil.addOrUpdateObjectEntry(
			objectEntry1.getExternalReferenceCode(),
			objectEntryFolder2.getCompanyId(), objectEntryFolder2.getUserId(),
			objectEntryFolder2.getExternalReferenceCode(),
			objectEntryFolder2.getModelClassName(), jsonObject,
			objectEntryFolder2.getGroupId(), objectEntryFolder2.getTreePath());

		objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMS_BASIC_DOCUMENT", objectEntryFolder2.getCompanyId());

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), depotEntry.getGroupId(),
			depotEntry.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), null, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null, null,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, new ByteArrayInputStream(TestDataConstants.TEST_BYTE_ARRAY),
			TestDataConstants.TEST_BYTE_ARRAY.length, null, null, null,
			ServiceContextTestUtil.getServiceContext());

		objectEntry2 = _objectEntryLocalService.addObjectEntry(
			depotEntry.getGroupId(), depotEntry.getUserId(),
			objectDefinition.getObjectDefinitionId(),
			objectEntryFolder2.getObjectEntryFolderId(), "en_US",
			HashMapBuilder.<String, Serializable>put(
				"file", dlFileEntry.getFileEntryId()
			).put(
				"title_i18n",
				HashMapBuilder.put(
					"en_US", RandomTestUtil.randomString()
				).build()
			).build(),
			serviceContext);

		resourcePermission =
			_resourcePermissionLocalService.getResourcePermission(
				objectEntry2.getCompanyId(), objectEntry2.getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntry2.getObjectEntryId()),
				_cmsAdministratorRole.getRoleId());

		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.DELETE));
		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.UPDATE));
		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.VIEW));
		Assert.assertFalse(resourcePermission.hasActionId(randomString));

		resourcePermission =
			_resourcePermissionLocalService.getResourcePermission(
				objectEntry2.getCompanyId(), objectEntry2.getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntry2.getObjectEntryId()),
				_ownerRole.getRoleId());

		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.DELETE));
		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.UPDATE));
		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.VIEW));

		resourcePermission =
			_resourcePermissionLocalService.getResourcePermission(
				objectEntry2.getCompanyId(), objectEntry2.getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntry2.getObjectEntryId()),
				_userRole.getRoleId());

		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.DELETE));
		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.UPDATE));
		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.VIEW));
	}

	@Test
	public void testOnAfterRemove() throws Exception {
		String title = RandomTestUtil.randomString();

		ObjectEntry objectEntry = _addObjectEntry(title, "L_CMP_TASK_123");

		_assertAuditMessage("CMP_ADD_ASSET", title);

		_objectEntryLocalService.deleteObjectEntry(objectEntry);

		_assertAuditMessage("CMP_REMOVE_ASSET", title);
	}

	@Test
	public void testOnAfterUpdate() throws Exception {

		// Audit router

		String title = RandomTestUtil.randomString();

		ObjectEntry objectEntry1 = _addObjectEntry(title, "L_CMP_TASK_123");

		_assertAuditMessage("CMP_ADD_ASSET", title);

		_objectEntryLocalService.partialUpdateObjectEntry(
			objectEntry1.getUserId(), objectEntry1.getObjectEntryId(),
			objectEntry1.getObjectEntryFolderId(), Collections.emptyMap(),
			_getServiceContext(RandomTestUtil.randomString()));

		_assertAuditMessage("CMP_REMOVE_ASSET", title);

		// Resource permissions

		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), StringUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), StringUtil.randomString()
			).build(),
			DepotConstants.TYPE_SPACE,
			ServiceContextTestUtil.getServiceContext());

		Group group = depotEntry.getGroup();

		ObjectEntryFolder objectEntryFolder1 =
			_objectEntryFolderLocalService.
				getObjectEntryFolderByExternalReferenceCode(
					ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS,
					group.getGroupId(), depotEntry.getCompanyId());

		ObjectEntryFolder objectEntryFolder2 =
			_objectEntryFolderLocalService.addObjectEntryFolder(
				RandomTestUtil.randomString(), group.getGroupId(),
				group.getCreatorUserId(),
				objectEntryFolder1.getObjectEntryFolderId(), "",
				HashMapBuilder.put(
					LocaleUtil.ENGLISH, RandomTestUtil.randomString()
				).build(),
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext());

		JSONObject jsonObject = CMSDefaultPermissionUtil.getJSONObject(
			objectEntryFolder2.getCompanyId(), objectEntryFolder2.getUserId(),
			objectEntryFolder2.getExternalReferenceCode(),
			objectEntryFolder2.getModelClassName(), _filterFactory);

		String randomString = RandomTestUtil.randomString();

		jsonObject.put(
			ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS,
			JSONUtil.put(
				RoleConstants.CMS_ADMINISTRATOR,
				JSONUtil.putAll(
					ActionKeys.UPDATE, ActionKeys.VIEW, randomString)
			).put(
				RoleConstants.USER, JSONUtil.putAll(ActionKeys.VIEW)
			));

		ObjectEntry objectEntry2 = CMSDefaultPermissionUtil.fetchObjectEntry(
			objectEntryFolder2.getCompanyId(), objectEntryFolder2.getUserId(),
			objectEntryFolder2.getExternalReferenceCode(),
			objectEntryFolder2.getModelClassName(), _filterFactory);

		CMSDefaultPermissionUtil.addOrUpdateObjectEntry(
			objectEntry2.getExternalReferenceCode(),
			objectEntryFolder2.getCompanyId(), objectEntryFolder2.getUserId(),
			objectEntryFolder2.getExternalReferenceCode(),
			objectEntryFolder2.getModelClassName(), jsonObject,
			objectEntryFolder2.getGroupId(), objectEntryFolder2.getTreePath());

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMS_BASIC_WEB_CONTENT",
					objectEntryFolder2.getCompanyId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setAttribute(
			"friendlyUrlMap", new HashMap<String, String>());

		ObjectEntry objectEntry3 = _objectEntryLocalService.addObjectEntry(
			depotEntry.getGroupId(), depotEntry.getUserId(),
			objectDefinition.getObjectDefinitionId(),
			objectEntryFolder2.getObjectEntryFolderId(), "en_US",
			HashMapBuilder.<String, Serializable>put(
				"title_i18n",
				HashMapBuilder.put(
					"en_US", RandomTestUtil.randomString()
				).build()
			).build(),
			serviceContext);

		ResourcePermission resourcePermission =
			_resourcePermissionLocalService.getResourcePermission(
				objectEntry3.getCompanyId(), objectEntry3.getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntry3.getObjectEntryId()),
				_cmsAdministratorRole.getRoleId());

		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.DELETE));
		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.UPDATE));
		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.VIEW));
		Assert.assertFalse(resourcePermission.hasActionId(randomString));

		resourcePermission =
			_resourcePermissionLocalService.getResourcePermission(
				objectEntry3.getCompanyId(), objectEntry3.getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntry3.getObjectEntryId()),
				_ownerRole.getRoleId());

		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.DELETE));
		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.UPDATE));
		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.VIEW));

		resourcePermission =
			_resourcePermissionLocalService.getResourcePermission(
				objectEntry3.getCompanyId(), objectEntry3.getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntry3.getObjectEntryId()),
				_userRole.getRoleId());

		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.DELETE));
		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.UPDATE));
		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.VIEW));

		ObjectEntryFolder objectEntryFolder3 =
			_objectEntryFolderLocalService.addObjectEntryFolder(
				RandomTestUtil.randomString(), group.getGroupId(),
				group.getCreatorUserId(),
				objectEntryFolder1.getObjectEntryFolderId(), "",
				HashMapBuilder.put(
					LocaleUtil.ENGLISH, RandomTestUtil.randomString()
				).build(),
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext());

		jsonObject = CMSDefaultPermissionUtil.getJSONObject(
			objectEntryFolder3.getCompanyId(), objectEntryFolder3.getUserId(),
			objectEntryFolder3.getExternalReferenceCode(),
			objectEntryFolder3.getModelClassName(), _filterFactory);

		jsonObject.put(
			ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS,
			JSONUtil.put(
				RoleConstants.CMS_ADMINISTRATOR,
				JSONUtil.putAll(ActionKeys.DELETE)
			).put(
				RoleConstants.USER, JSONUtil.putAll(ActionKeys.DELETE)
			));

		ObjectEntry objectEntry4 = CMSDefaultPermissionUtil.fetchObjectEntry(
			objectEntryFolder3.getCompanyId(), objectEntryFolder3.getUserId(),
			objectEntryFolder3.getExternalReferenceCode(),
			objectEntryFolder3.getModelClassName(), _filterFactory);

		CMSDefaultPermissionUtil.addOrUpdateObjectEntry(
			objectEntry4.getExternalReferenceCode(),
			objectEntryFolder3.getCompanyId(), objectEntryFolder3.getUserId(),
			objectEntryFolder3.getExternalReferenceCode(),
			objectEntryFolder3.getModelClassName(), jsonObject,
			objectEntryFolder3.getGroupId(), objectEntryFolder3.getTreePath());

		objectEntry3 = _objectEntryLocalService.moveObjectEntry(
			objectEntry3.getUserId(), objectEntry3.getObjectEntryId(),
			objectEntryFolder3.getObjectEntryFolderId(),
			objectEntry3.getValues(), serviceContext);

		resourcePermission =
			_resourcePermissionLocalService.getResourcePermission(
				objectEntry3.getCompanyId(), objectEntry3.getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntry3.getObjectEntryId()),
				_cmsAdministratorRole.getRoleId());

		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.DELETE));
		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.UPDATE));
		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.VIEW));

		resourcePermission =
			_resourcePermissionLocalService.getResourcePermission(
				objectEntry3.getCompanyId(), objectEntry3.getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntry3.getObjectEntryId()),
				_ownerRole.getRoleId());

		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.DELETE));
		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.UPDATE));
		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.VIEW));

		resourcePermission =
			_resourcePermissionLocalService.getResourcePermission(
				objectEntry3.getCompanyId(), objectEntry3.getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntry3.getObjectEntryId()),
				_userRole.getRoleId());

		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.DELETE));
		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.UPDATE));
		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.VIEW));
	}

	private ObjectEntry _addObjectEntry(String title, String... assetTagNames)
		throws Exception {

		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), StringUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), StringUtil.randomString()
			).build(),
			DepotConstants.TYPE_SPACE,
			ServiceContextTestUtil.getServiceContext());

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMS_BASIC_WEB_CONTENT", depotEntry.getCompanyId());

		return _objectEntryLocalService.addObjectEntry(
			depotEntry.getGroupId(), depotEntry.getUserId(),
			objectDefinition.getObjectDefinitionId(), 0, "en_US",
			HashMapBuilder.<String, Serializable>put(
				"title_i18n",
				HashMapBuilder.put(
					"en_US", title
				).build()
			).build(),
			_getServiceContext(assetTagNames));
	}

	private void _assertAuditMessage(
			String expectedEventType, String exptectedTitle)
		throws Exception {

		AuditMessage auditMessage = _auditMessages.poll();

		JSONAssert.assertEquals(
			JSONUtil.put(
				"attributes",
				JSONUtil.putAll(JSONUtil.put("name", exptectedTitle))
			).toString(),
			String.valueOf(auditMessage.getAdditionalInfo()),
			JSONCompareMode.STRICT_ORDER);
		Assert.assertEquals(
			_cmpTaskObjectEntry.getModelClassName(),
			auditMessage.getClassName());
		Assert.assertEquals(
			_cmpTaskObjectEntry.getObjectEntryId(),
			GetterUtil.getLong(auditMessage.getClassPK()));
		Assert.assertEquals(expectedEventType, auditMessage.getEventType());

		_auditMessages.clear();
	}

	private Role _getOrAddCMSAdministratorRole(long companyId, long userId)
		throws Exception {

		Role role = _roleLocalService.fetchRole(
			companyId, RoleConstants.CMS_ADMINISTRATOR);

		if (role != null) {
			return role;
		}

		return _roleLocalService.addRole(
			null, userId, null, 0, RoleConstants.CMS_ADMINISTRATOR, null, null,
			RoleConstants.TYPE_REGULAR, null, null);
	}

	private ServiceContext _getServiceContext(String... assetTagNames)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setAssetTagNames(assetTagNames);

		return serviceContext;
	}

	private final Queue<AuditMessage> _auditMessages = new LinkedList<>();
	private AuditRouter _auditRouter;
	private ObjectEntry _cmpTaskObjectEntry;
	private Role _cmsAdministratorRole;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject(
		filter = "filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT
	)
	private FilterFactory<Predicate> _filterFactory;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.site.cms.site.initializer.internal.model.listener.ObjectEntryModelListener"
	)
	private ModelListener<ObjectEntry> _objectEntryModelListener;

	private Role _ownerRole;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	private Role _userRole;

}