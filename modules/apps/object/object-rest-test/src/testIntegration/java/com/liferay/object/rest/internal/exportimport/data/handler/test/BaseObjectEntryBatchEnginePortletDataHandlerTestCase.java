/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.exportimport.data.handler.test;

import com.liferay.exportimport.test.util.exportimport.data.handler.BaseBatchEnginePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectDefinitionSettingConstants;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.definition.setting.builder.ObjectDefinitionSettingBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectDefinitionSetting;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.resource.v1_0.ObjectEntryResource;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Before;

/**
 * @author Alberto Javier Moreno Lage
 */
public abstract class BaseObjectEntryBatchEnginePortletDataHandlerTestCase
	extends BaseBatchEnginePortletDataHandlerTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		Scope scope = getScope();

		List<ObjectDefinitionSetting> objectDefinitionSettings =
			Collections.emptyList();

		if (scope == Scope.DEPOT) {
			objectDefinitionSettings = Collections.singletonList(
				new ObjectDefinitionSettingBuilder(
				).name(
					ObjectDefinitionSettingConstants.NAME_ACCEPT_ALL_GROUPS
				).value(
					StringPool.TRUE
				).build());
		}

		_objectDefinition = _addObjectDefinition(
			null, ObjectDefinitionTestUtil.getRandomName(),
			objectDefinitionSettings, _getObjectDefinitionScope(scope),
			TestPropsValues.getUserId());

		super.setUp();

		if (scope == Scope.COMPANY) {
			User user = getTargetUser();

			_targetObjectDefinition = _addObjectDefinition(
				_objectDefinition.getExternalReferenceCode(),
				_objectDefinition.getShortName(), Collections.emptyList(),
				ObjectDefinitionConstants.SCOPE_COMPANY, user.getUserId());
		}
	}

	@Override
	protected String addEmptyEntry(long groupId, long userId) throws Exception {
		ObjectDefinition objectDefinition = _getObjectDefinition(groupId);

		ObjectEntry objectEntry =
			_objectEntryLocalService.getOrAddEmptyObjectEntry(
				RandomTestUtil.randomString(), _getObjectEntryGroupId(groupId),
				userId, objectDefinition.getObjectDefinitionId());

		return objectEntry.getExternalReferenceCode();
	}

	@Override
	protected String addEntry(long groupId, Date modifiedDate, long userId)
		throws Exception {

		ObjectDefinition objectDefinition = _getObjectDefinition(groupId);

		long objectEntryGroupId = _getObjectEntryGroupId(groupId);

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			objectEntryGroupId, userId,
			objectDefinition.getObjectDefinitionId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			null,
			Collections.singletonMap(
				_OBJECT_FIELD_NAME, RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext(
				objectDefinition.getCompanyId(), objectEntryGroupId, userId));

		objectEntry.setModifiedDate(modifiedDate);

		objectEntry = _objectEntryLocalService.updateObjectEntry(objectEntry);

		return objectEntry.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(String externalReferenceCode, long groupId)
		throws Exception {

		_objectEntryLocalService.deleteObjectEntry(
			_fetchObjectEntry(externalReferenceCode, groupId));
	}

	@Override
	protected Object fetchEntry(String externalReferenceCode, long groupId)
		throws Exception {

		ObjectEntry objectEntry = _fetchObjectEntry(
			externalReferenceCode, groupId);

		if ((objectEntry == null) || objectEntry.isInTrash()) {
			return null;
		}

		return objectEntry;
	}

	@Override
	protected long getCreatorUserId(String externalReferenceCode, long groupId)
		throws Exception {

		ObjectEntry objectEntry = _fetchObjectEntry(
			externalReferenceCode, groupId);

		return objectEntry.getUserId();
	}

	@Override
	protected Object getEntryValue(String externalReferenceCode, long groupId)
		throws Exception {

		ObjectEntry objectEntry = _fetchObjectEntry(
			externalReferenceCode, groupId);

		return MapUtil.getString(objectEntry.getValues(), _OBJECT_FIELD_NAME);
	}

	@Override
	protected ExportImportVulcanBatchEngineTaskItemDelegate<?>
			getExportImportVulcanBatchEngineTaskItemDelegate()
		throws Exception {

		return getExportImportVulcanBatchEngineTaskItemDelegate(
			ObjectEntryResource.class,
			StringBundler.concat(
				"(&(batch.engine.task.item.delegate.name=",
				_objectDefinition.getName(), ")(companyId=",
				_objectDefinition.getCompanyId(), "))"));
	}

	@Override
	protected long getPrimaryKey(String externalReferenceCode, long groupId)
		throws Exception {

		ObjectEntry objectEntry = _fetchObjectEntry(
			externalReferenceCode, groupId);

		return objectEntry.getObjectEntryId();
	}

	@Override
	protected int getStatus(String externalReferenceCode, long groupId)
		throws Exception {

		ObjectEntry objectEntry = _fetchObjectEntry(
			externalReferenceCode, groupId);

		return objectEntry.getStatus();
	}

	@Override
	protected String getTargetModelClassName() {
		if (_targetObjectDefinition == null) {
			return super.getTargetModelClassName();
		}

		return _targetObjectDefinition.getClassName();
	}

	@Override
	protected boolean supportsComments() {
		return true;
	}

	@Override
	protected boolean supportsEmptyEntries() {
		return true;
	}

	@Override
	protected boolean supportsPermissions() {
		return true;
	}

	@Override
	protected void updateEntry(String externalReferenceCode, long groupId)
		throws Exception {

		ObjectEntry objectEntry = _fetchObjectEntry(
			externalReferenceCode, groupId);

		_objectEntryLocalService.updateObjectEntry(
			objectEntry.getUserId(), objectEntry.getObjectEntryId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			Collections.singletonMap(
				_OBJECT_FIELD_NAME, RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext(
				objectEntry.getCompanyId(), objectEntry.getGroupId(),
				objectEntry.getUserId()));
	}

	private ObjectDefinition _addObjectDefinition(
			String externalReferenceCode, String name,
			List<ObjectDefinitionSetting> objectDefinitionSettings,
			String scope, long userId)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				externalReferenceCode, userId, 0, null, null, true, true, false,
				false, true, false, false, false, false,
				StringUtil.toLowerCase(RandomTestUtil.randomString()),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				name, null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, scope, ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				objectDefinitionSettings, Collections.emptyList(),
				Collections.emptyList(), new ServiceContext());

		ObjectField objectField = ObjectFieldUtil.addCustomObjectField(
			new TextObjectFieldBuilder(
			).userId(
				userId
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(_OBJECT_FIELD_NAME)
			).name(
				_OBJECT_FIELD_NAME
			).objectDefinitionId(
				objectDefinition.getObjectDefinitionId()
			).required(
				false
			).build());

		_objectDefinitionLocalService.updateTitleObjectFieldId(
			objectDefinition.getObjectDefinitionId(),
			objectField.getObjectFieldId());

		return _objectDefinitionLocalService.publishCustomObjectDefinition(
			userId, objectDefinition.getObjectDefinitionId());
	}

	private ObjectEntry _fetchObjectEntry(
			String externalReferenceCode, long groupId)
		throws Exception {

		ObjectDefinition objectDefinition = _getObjectDefinition(groupId);

		return _objectEntryLocalService.fetchObjectEntry(
			externalReferenceCode, _getObjectEntryGroupId(groupId),
			objectDefinition.getObjectDefinitionId());
	}

	private ObjectDefinition _getObjectDefinition(long groupId)
		throws Exception {

		if (_targetObjectDefinition == null) {
			return _objectDefinition;
		}

		Group group = _groupLocalService.getGroup(groupId);

		if (group.getCompanyId() == _targetObjectDefinition.getCompanyId()) {
			return _targetObjectDefinition;
		}

		return _objectDefinition;
	}

	private String _getObjectDefinitionScope(Scope scope) {
		if (scope == Scope.COMPANY) {
			return ObjectDefinitionConstants.SCOPE_COMPANY;
		}

		if (scope == Scope.DEPOT) {
			return ObjectDefinitionConstants.SCOPE_DEPOT;
		}

		return ObjectDefinitionConstants.SCOPE_SITE;
	}

	private long _getObjectEntryGroupId(long groupId) {
		if (getScope() == Scope.COMPANY) {
			return 0;
		}

		return groupId;
	}

	private static final String _OBJECT_FIELD_NAME =
		"x" + RandomTestUtil.randomString();

	@Inject
	private GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@DeleteAfterTestRun
	private ObjectDefinition _targetObjectDefinition;

}