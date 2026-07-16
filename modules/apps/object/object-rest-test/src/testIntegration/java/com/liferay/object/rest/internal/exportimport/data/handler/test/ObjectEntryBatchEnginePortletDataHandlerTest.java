/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.test.rule.ExportImportScopes;
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
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

/**
 * @author Alberto Javier Moreno Lage
 */
@ExportImportScopes({Scope.COMPANY, Scope.DEPOT, Scope.SITE})
@FeatureFlags(featureFlags = @FeatureFlag("LPD-43996"))
@RunWith(Arquillian.class)
public class ObjectEntryBatchEnginePortletDataHandlerTest
	extends BaseBatchEnginePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		List<ObjectDefinitionSetting> objectDefinitionSettings =
			Collections.emptyList();

		String scope = _objectDefinitionScope;

		if (StringUtil.equals(scope, ObjectDefinitionConstants.SCOPE_DEPOT)) {
			objectDefinitionSettings = Collections.singletonList(
				new ObjectDefinitionSettingBuilder(
				).name(
					ObjectDefinitionSettingConstants.NAME_ACCEPT_ALL_GROUPS
				).value(
					StringPool.TRUE
				).build());
		}

		_objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				null, TestPropsValues.getUserId(), 0, null, true, true, false,
				false, true, false, false, false, false, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionTestUtil.getRandomName(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, scope, ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				objectDefinitionSettings, Collections.emptyList(),
				Collections.emptyList(), new ServiceContext());

		ObjectField objectField = ObjectFieldUtil.addCustomObjectField(
			new TextObjectFieldBuilder(
			).userId(
				TestPropsValues.getUserId()
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(_OBJECT_FIELD_NAME)
			).name(
				_OBJECT_FIELD_NAME
			).objectDefinitionId(
				_objectDefinition.getObjectDefinitionId()
			).required(
				false
			).build());

		_objectDefinitionLocalService.updateTitleObjectFieldId(
			_objectDefinition.getObjectDefinitionId(),
			objectField.getObjectFieldId());

		_objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId());

		super.setUp();

		if (StringUtil.equals(scope, ObjectDefinitionConstants.SCOPE_COMPANY)) {
			_addTargetObjectDefinition();
		}
	}

	@After
	public void tearDown() throws Exception {
		if (_targetObjectDefinition != null) {
			_objectDefinitionLocalService.deleteObjectDefinition(
				_targetObjectDefinition);

			_targetObjectDefinition = null;
		}
	}

	@Rule(order = Integer.MIN_VALUE)
	public final TestRule objectDefinitionScopesTestRule =
		(statement, description) -> new Statement() {

			@Override
			public void evaluate() throws Throwable {
				for (Scope scope : exportImportScopesTestRule.getScopes()) {
					_objectDefinitionScope = _getObjectDefinitionScope(scope);

					try {
						statement.evaluate();
					}
					catch (Throwable throwable) {
						throw new AssertionError(
							StringBundler.concat(
								"Scope \"", _objectDefinitionScope, "\": ",
								throwable.getMessage()),
							throwable);
					}
				}
			}

		};

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
	protected String addEntry(long groupId, long userId, Date dateModified)
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

		objectEntry.setModifiedDate(dateModified);

		objectEntry = _objectEntryLocalService.updateObjectEntry(objectEntry);

		return objectEntry.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(long groupId, String externalReferenceCode)
		throws Exception {

		_objectEntryLocalService.deleteObjectEntry(
			_getObjectEntry(groupId, externalReferenceCode));
	}

	@Override
	protected long getCreatorUserId(long groupId, String externalReferenceCode)
		throws Exception {

		ObjectEntry objectEntry = _getObjectEntry(
			groupId, externalReferenceCode);

		return objectEntry.getUserId();
	}

	@Override
	protected String getEntryValue(long groupId, String externalReferenceCode)
		throws Exception {

		ObjectEntry objectEntry = _getObjectEntry(
			groupId, externalReferenceCode);

		return MapUtil.getString(objectEntry.getValues(), _OBJECT_FIELD_NAME);
	}

	@Override
	protected ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate() {

		return getExportImportVulcanBatchEngineTaskItemDelegate(
			ObjectEntryResource.class,
			StringBundler.concat(
				"(&(batch.engine.task.item.delegate.name=",
				_objectDefinition.getName(), ")(companyId=",
				_objectDefinition.getCompanyId(), "))"));
	}

	@Override
	protected List<String> getExternalReferenceCodes(long groupId)
		throws Exception {

		ObjectDefinition objectDefinition = _getObjectDefinition(groupId);

		return TransformUtil.transform(
			_objectEntryLocalService.getObjectEntries(
				_getObjectEntryGroupId(groupId),
				objectDefinition.getObjectDefinitionId(),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS),
			ObjectEntry::getExternalReferenceCode);
	}

	@Override
	protected long getPrimaryKey(long groupId, String externalReferenceCode)
		throws Exception {

		ObjectEntry objectEntry = _getObjectEntry(
			groupId, externalReferenceCode);

		return objectEntry.getObjectEntryId();
	}

	@Override
	protected int getStatus(long groupId, String externalReferenceCode)
		throws Exception {

		ObjectEntry objectEntry = _getObjectEntry(
			groupId, externalReferenceCode);

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
	protected void updateEntry(long groupId, String externalReferenceCode)
		throws Exception {

		ObjectEntry objectEntry = _getObjectEntry(
			groupId, externalReferenceCode);

		_objectEntryLocalService.updateObjectEntry(
			objectEntry.getUserId(), objectEntry.getObjectEntryId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			Collections.singletonMap(
				_OBJECT_FIELD_NAME, RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext(
				objectEntry.getCompanyId(), objectEntry.getGroupId(),
				objectEntry.getUserId()));
	}

	private void _addTargetObjectDefinition() throws Exception {
		User user = getTargetUser();

		_targetObjectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				_objectDefinition.getExternalReferenceCode(), user.getUserId(),
				0, null, true, true, false, false, true, false, false, false,
				false, StringUtil.toLowerCase(RandomTestUtil.randomString()),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				_objectDefinition.getShortName(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.emptyList(), Collections.emptyList(),
				Collections.emptyList(), new ServiceContext());

		ObjectField objectField = ObjectFieldUtil.addCustomObjectField(
			new TextObjectFieldBuilder(
			).userId(
				user.getUserId()
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(_OBJECT_FIELD_NAME)
			).name(
				_OBJECT_FIELD_NAME
			).objectDefinitionId(
				_targetObjectDefinition.getObjectDefinitionId()
			).required(
				false
			).build());

		_objectDefinitionLocalService.updateTitleObjectFieldId(
			_targetObjectDefinition.getObjectDefinitionId(),
			objectField.getObjectFieldId());

		_targetObjectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				user.getUserId(),
				_targetObjectDefinition.getObjectDefinitionId());
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

	private ObjectEntry _getObjectEntry(
			long groupId, String externalReferenceCode)
		throws Exception {

		ObjectDefinition objectDefinition = _getObjectDefinition(groupId);

		return _objectEntryLocalService.fetchObjectEntry(
			externalReferenceCode, _getObjectEntryGroupId(groupId),
			objectDefinition.getObjectDefinitionId());
	}

	private long _getObjectEntryGroupId(long groupId) {
		if (StringUtil.equals(
				_objectDefinitionScope,
				ObjectDefinitionConstants.SCOPE_COMPANY)) {

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

	private String _objectDefinitionScope;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	private ObjectDefinition _targetObjectDefinition;

}