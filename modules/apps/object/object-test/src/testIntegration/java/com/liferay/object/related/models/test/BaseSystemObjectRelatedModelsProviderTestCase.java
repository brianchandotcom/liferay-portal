/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.related.models.test;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.RequiredObjectRelationshipException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.related.models.ObjectRelatedModelsProvider;
import com.liferay.object.related.models.ObjectRelatedModelsProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Pedro Leite
 */
public abstract class BaseSystemObjectRelatedModelsProviderTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_systemObjectDefinition = getSystemObjectDefinition();

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService);

		ObjectField objectField = _objectFieldLocalService.addCustomObjectField(
			null, TestPropsValues.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, false, false, null,
			LocalizedMapUtil.getLocalizedMap("Able"), false, "able", null, null,
			false, false, Collections.emptyList());

		objectDefinition.setTitleObjectFieldId(objectField.getObjectFieldId());

		objectDefinition = _objectDefinitionLocalService.updateObjectDefinition(
			objectDefinition);

		_objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));

		PrincipalThreadLocal.setName(
			TestPropsValues.getUser(
			).getUserId());
	}

	@Test
	public void testSystemObjectEntry1toMObjectRelatedModelsProviderImpl()
		throws Exception {

		long[] primaryKeys = addSystemObjectEntry(3);

		_addObjectRelationship(
			_objectDefinition, _systemObjectDefinition,
			ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectEntry objectEntry1 = _addObjectEntry(
			_objectDefinition.getObjectDefinitionId(), Collections.emptyMap());

		_assertGetRelatedModels(objectEntry1.getObjectEntryId(), 0);

		_insertIntoOrUpdateExtensionTable(
			objectEntry1.getObjectEntryId(), primaryKeys[0],
			_systemObjectDefinition.getObjectDefinitionId());
		_insertIntoOrUpdateExtensionTable(
			objectEntry1.getObjectEntryId(), primaryKeys[1],
			_systemObjectDefinition.getObjectDefinitionId());
		_insertIntoOrUpdateExtensionTable(
			objectEntry1.getObjectEntryId(), primaryKeys[2],
			_systemObjectDefinition.getObjectDefinitionId());

		_assertGetRelatedModels(objectEntry1.getObjectEntryId(), 3);

		// Get related models with search

		_assertSearchRelatedModels(
			objectEntry1.getObjectEntryId(), StringUtil.randomString(), 0);
		_assertSearchRelatedModels(
			objectEntry1.getObjectEntryId(), String.valueOf(primaryKeys[1]), 1);

		_assertSearchRelatedModels(
			objectEntry1.getObjectEntryId(),
			getSystemObjectEntryName(primaryKeys[1]), 1);

		// Disassociate related models

		_objectRelatedModelsProvider.disassociateRelatedModels(
			TestPropsValues.getUserId(),
			_objectRelationship.getObjectRelationshipId(),
			objectEntry1.getPrimaryKey(), primaryKeys[0]);

		_assertGetRelatedModels(objectEntry1.getObjectEntryId(), 2);

		// Object relationship deletion type cascade

		_updateObjectRelationship(
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE);

		_objectEntryLocalService.deleteObjectEntry(objectEntry1);

		assertFailureNoSuchException(primaryKeys[1]);
		assertFailureNoSuchException(primaryKeys[2]);

		// Object relationship deletion type disassociate

		_updateObjectRelationship(
			ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE);

		ObjectEntry objectEntry2 = _addObjectEntry(
			_objectDefinition.getObjectDefinitionId(), Collections.emptyMap());

		_insertIntoOrUpdateExtensionTable(
			objectEntry2.getObjectEntryId(), primaryKeys[0],
			_systemObjectDefinition.getObjectDefinitionId());

		_assertGetRelatedModels(objectEntry2.getObjectEntryId(), 1);

		_objectEntryLocalService.deleteObjectEntry(objectEntry2);

		_assertGetRelatedModels(objectEntry2.getObjectEntryId(), 0);

		Assert.assertNotNull(fetchSystemObjectEntry(primaryKeys[0]));

		// Object relationship deletion type prevent

		_updateObjectRelationship(
			ObjectRelationshipConstants.DELETION_TYPE_PREVENT);

		ObjectEntry objectEntry3 = _addObjectEntry(
			_objectDefinition.getObjectDefinitionId(), Collections.emptyMap());

		_insertIntoOrUpdateExtensionTable(
			objectEntry3.getObjectEntryId(), primaryKeys[0],
			_systemObjectDefinition.getObjectDefinitionId());

		AssertUtils.assertFailure(
			RequiredObjectRelationshipException.class,
			StringBundler.concat(
				"Object relationship ",
				_objectRelationship.getObjectRelationshipId(),
				" does not allow deletes"),
			() -> _objectEntryLocalService.deleteObjectEntry(objectEntry3));

		_assertGetRelatedModels(objectEntry3.getObjectEntryId(), 1);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship);
	}

	@Test
	public void testSystemObjectEntryMtoMObjectRelatedModels()
		throws Exception {

		long[] primaryKeys = addSystemObjectEntry(3);

		_addObjectRelationship(
			_objectDefinition, _systemObjectDefinition,
			ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		ObjectEntry objectEntry1 = _addObjectEntry(
			_objectDefinition.getObjectDefinitionId(), Collections.emptyMap());

		_assertGetRelatedModels(objectEntry1.getObjectEntryId(), 0);

		_addObjectRelationshipMappingTableValues(
			objectEntry1.getObjectEntryId(), primaryKeys[0]);
		_addObjectRelationshipMappingTableValues(
			objectEntry1.getObjectEntryId(), primaryKeys[1]);

		_assertGetRelatedModels(objectEntry1.getObjectEntryId(), 2);

		// Get related models with search

		_assertSearchRelatedModels(
			objectEntry1.getObjectEntryId(), StringUtil.randomString(), 0);
		_assertSearchRelatedModels(
			objectEntry1.getObjectEntryId(), String.valueOf(primaryKeys[0]), 1);

		_assertSearchRelatedModels(
			objectEntry1.getObjectEntryId(),
			getSystemObjectEntryName(primaryKeys[1]), 1);

		// Object relationship deletion type cascade

		_updateObjectRelationship(
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE);

		deleteSystemObjectEntry(primaryKeys[1]);

		Assert.assertNotNull(
			_objectEntryLocalService.fetchObjectEntry(
				objectEntry1.getObjectEntryId()));

		assertFailureNoSuchException(primaryKeys[1]);

		_objectEntryLocalService.deleteObjectEntry(objectEntry1);

		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				objectEntry1.getObjectEntryId()));

		assertFailureNoSuchException(primaryKeys[0]);

		// Object relationship deletion type disassociate

		_updateObjectRelationship(
			ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE);

		ObjectEntry objectEntry2 = _addObjectEntry(
			_objectDefinition.getObjectDefinitionId(), Collections.emptyMap());

		_addObjectRelationshipMappingTableValues(
			objectEntry2.getObjectEntryId(), primaryKeys[2]);

		_assertGetRelatedModels(objectEntry2.getObjectEntryId(), 1);

		_objectEntryLocalService.deleteObjectEntry(objectEntry2);

		_assertGetRelatedModels(objectEntry2.getObjectEntryId(), 0);

		Assert.assertNotNull(fetchSystemObjectEntry(primaryKeys[2]));

		// Object relationship deletion type prevent

		_updateObjectRelationship(
			ObjectRelationshipConstants.DELETION_TYPE_PREVENT);

		ObjectEntry objectEntry3 = _addObjectEntry(
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"able", "Entry"
			).build());

		_addObjectRelationshipMappingTableValues(
			objectEntry3.getObjectEntryId(), primaryKeys[2]);

		AssertUtils.assertFailure(
			RequiredObjectRelationshipException.class,
			StringBundler.concat(
				"Object relationship ",
				_objectRelationship.getObjectRelationshipId(),
				" does not allow deletes"),
			() -> _objectEntryLocalService.deleteObjectEntry(objectEntry3));

		// Reverse object relationship

		// Get related models with database

		_objectRelationship =
			_objectRelationshipLocalService.fetchReverseObjectRelationship(
				_objectRelationship, true);

		_objectRelatedModelsProvider =
			_objectRelatedModelsProviderRegistry.getObjectRelatedModelsProvider(
				_objectDefinition.getClassName(),
				_objectDefinition.getCompanyId(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		ObjectEntry objectEntry4 = _addObjectEntry(
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"able", "New Entry"
			).build());

		_addObjectRelationshipMappingTableValues(
			primaryKeys[2], objectEntry4.getObjectEntryId());

		_assertGetRelatedModels(primaryKeys[2], 2);

		// Get related models with search

		_assertSearchRelatedModels(
			primaryKeys[2], StringUtil.randomString(), 0);
		_assertSearchRelatedModels(
			primaryKeys[2], String.valueOf(objectEntry3.getObjectEntryId()), 1);
		_assertSearchRelatedModels(primaryKeys[2], "New", 1);
		_assertSearchRelatedModels(primaryKeys[2], "Entry", 2);

		_objectRelationshipLocalService.deleteObjectRelationships(
			_objectDefinition.getObjectDefinitionId());

		Assert.assertNull(
			_objectRelationshipLocalService.fetchObjectRelationship(
				_objectRelationship.getObjectRelationshipId()));
	}

	protected long[] addSystemObjectEntry(int count) throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertFailureNoSuchException(long primaryKey)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void deleteSystemObjectEntry(long primaryKey) throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Object fetchSystemObjectEntry(long primaryKey) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected ObjectDefinition getSystemObjectDefinition() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String getSystemObjectEntryName(long primaryKey)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	private ObjectEntry _addObjectEntry(
			long objectDefinitionId, Map<String, Serializable> values)
		throws Exception {

		return _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0, objectDefinitionId, values,
			ServiceContextTestUtil.getServiceContext());
	}

	private void _addObjectRelationship(
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2, String deletionType,
			String relationshipType)
		throws Exception {

		_objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				objectDefinition1.getObjectDefinitionId(),
				objectDefinition2.getObjectDefinitionId(), 0, deletionType,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(), relationshipType);

		if (!StringUtil.equals(
				relationshipType,
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

			_relationshipObjectField = _objectFieldLocalService.getObjectField(
				_objectRelationship.getObjectFieldId2());
		}

		_objectRelatedModelsProvider =
			_objectRelatedModelsProviderRegistry.getObjectRelatedModelsProvider(
				objectDefinition2.getClassName(),
				objectDefinition2.getCompanyId(), relationshipType);
	}

	private void _addObjectRelationshipMappingTableValues(
			long primaryKey1, long primaryKey2)
		throws Exception {

		_objectRelationshipLocalService.addObjectRelationshipMappingTableValues(
			TestPropsValues.getUserId(),
			_objectRelationship.getObjectRelationshipId(), primaryKey1,
			primaryKey2, ServiceContextTestUtil.getServiceContext());
	}

	private void _assertGetRelatedModels(long primaryKey, int expectedSize)
		throws Exception {

		List<ObjectEntry> objectEntries =
			_objectRelatedModelsProvider.getRelatedModels(
				0, _objectRelationship.getObjectRelationshipId(), primaryKey,
				null, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			objectEntries.toString(), expectedSize, objectEntries.size());
	}

	private void _assertSearchRelatedModels(
			long primaryKey, String search, int expectedSize)
		throws Exception {

		List<ObjectEntry> objectEntries =
			_objectRelatedModelsProvider.getRelatedModels(
				0, _objectRelationship.getObjectRelationshipId(), primaryKey,
				search, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			objectEntries.toString(), expectedSize, objectEntries.size());
	}

	private void _insertIntoOrUpdateExtensionTable(
			long objectEntryId, long primaryKey, long systemObjectDefinitionId)
		throws Exception {

		_objectEntryLocalService.insertIntoOrUpdateExtensionTable(
			TestPropsValues.getUserId(), systemObjectDefinitionId, primaryKey,
			HashMapBuilder.<String, Serializable>put(
				"textField", RandomTestUtil.randomString()
			).put(
				_relationshipObjectField.getName(), objectEntryId
			).build());
	}

	private void _updateObjectRelationship(String deletionType)
		throws Exception {

		_objectRelationship =
			_objectRelationshipLocalService.updateObjectRelationship(
				_objectRelationship.getObjectRelationshipId(), 0, deletionType,
				_objectRelationship.getLabelMap());
	}

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	private ObjectRelatedModelsProvider<ObjectEntry>
		_objectRelatedModelsProvider;

	@Inject
	private ObjectRelatedModelsProviderRegistry
		_objectRelatedModelsProviderRegistry;

	private ObjectRelationship _objectRelationship;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	private ObjectField _relationshipObjectField;
	private ObjectDefinition _systemObjectDefinition;

}