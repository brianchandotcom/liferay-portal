/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.related.models.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.related.models.test.util.ObjectEntryTestUtil;
import com.liferay.object.related.models.test.util.ObjectRelationshipTestUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Pedro Leite
 */
@RunWith(Arquillian.class)
public class UserSystemObjectRelatedModelsProviderTest
	extends BaseSystemObjectRelatedModelsProviderTestCase {

	@Test
	public void testDeleteObjectEntryWithSharedRelatedUser() throws Exception {
		_testDeleteObjectEntryWithSharedRelatedUser(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);
		_testDeleteObjectEntryWithSharedRelatedUser(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
	}

	@Test
	public void testSystemObjectEntry1toMObjectRelatedModelsWithNameTitleObjectField()
		throws Exception {

		ObjectDefinition objectDefinition = getSystemObjectDefinition();

		long originalTitleObjectFieldId =
			objectDefinition.getTitleObjectFieldId();

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectDefinition.getObjectDefinitionId(), "name");

		_objectDefinitionLocalService.updateTitleObjectFieldId(
			objectDefinition.getObjectDefinitionId(),
			objectField.getObjectFieldId());

		_searchByLastName = true;

		testSystemObjectEntry1toMObjectRelatedModels();

		_objectDefinitionLocalService.updateTitleObjectFieldId(
			objectDefinition.getObjectDefinitionId(),
			originalTitleObjectFieldId);
	}

	@Test
	public void testSystemObjectEntryMtoMObjectRelatedModelsWithNameTitleObjectField()
		throws Exception {

		ObjectDefinition objectDefinition = getSystemObjectDefinition();

		long originalTitleObjectFieldId =
			objectDefinition.getTitleObjectFieldId();

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectDefinition.getObjectDefinitionId(), "name");

		_objectDefinitionLocalService.updateTitleObjectFieldId(
			objectDefinition.getObjectDefinitionId(),
			objectField.getObjectFieldId());

		testSystemObjectEntryMtoMObjectRelatedModels();

		_objectDefinitionLocalService.updateTitleObjectFieldId(
			objectDefinition.getObjectDefinitionId(),
			originalTitleObjectFieldId);
	}

	@Override
	protected long[] addBaseModels(int count) throws Exception {
		long[] userIds = new long[count];

		for (int i = 0; i < count; i++) {
			User user = UserTestUtil.addUser();

			userIds[i] = user.getUserId();
		}

		return userIds;
	}

	@Override
	protected void assertFailure(long primaryKey) {
		AssertUtils.assertFailure(
			NoSuchUserException.class,
			"No User exists with the primary key " + primaryKey,
			() -> _userLocalService.getUser(primaryKey));
	}

	@Override
	protected void deleteBaseModel(long primaryKey) throws Exception {
		_userLocalService.deleteUser(primaryKey);
	}

	@Override
	protected Object fetchBaseModel(long primaryKey) {
		return _userLocalService.fetchUser(primaryKey);
	}

	@Override
	protected String getName(long primaryKey) throws Exception {
		User user = _userLocalService.getUser(primaryKey);

		if (_searchByLastName) {
			return user.getLastName();
		}

		return user.getFirstName();
	}

	@Override
	protected ObjectDefinition getSystemObjectDefinition() throws Exception {
		return _objectDefinitionLocalService.fetchObjectDefinitionByClassName(
			TestPropsValues.getCompanyId(), User.class.getName());
	}

	private String _getObjectFieldName(ObjectRelationship objectRelationship)
		throws Exception {

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectRelationship.getObjectFieldId2());

		return objectField.getName();
	}

	private void _testDeleteObjectEntryWithSharedRelatedUser(
			String relationshipType)
		throws Exception {

		ObjectDefinition objectDefinition1 =
			ObjectDefinitionTestUtil.publishObjectDefinition();
		ObjectDefinition objectDefinition2 =
			ObjectDefinitionTestUtil.publishObjectDefinition();

		_objectDefinitions.add(objectDefinition1);
		_objectDefinitions.add(objectDefinition2);

		ObjectDefinition userObjectDefinition = getSystemObjectDefinition();

		ObjectRelationship objectRelationship1 = addObjectRelationship(
			objectDefinition1, userObjectDefinition,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			relationshipType);
		ObjectRelationship objectRelationship2 = addObjectRelationship(
			userObjectDefinition, objectDefinition2,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
		ObjectRelationship objectRelationship3 = addObjectRelationship(
			objectDefinition2, userObjectDefinition,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		User user1 = UserTestUtil.addUser();

		ObjectEntry objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			0, objectDefinition2.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				_getObjectFieldName(objectRelationship2), user1.getUserId()
			).build());

		ObjectEntry objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			0, objectDefinition1.getObjectDefinitionId(),
			Collections.emptyMap());

		User user2 = UserTestUtil.addUser();

		Map<String, Serializable> values =
			HashMapBuilder.<String, Serializable>put(
				_getObjectFieldName(objectRelationship3),
				objectEntry2.getObjectEntryId()
			).build();

		if (StringUtil.equals(
				relationshipType,
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

			ObjectRelationshipTestUtil.addObjectRelationshipMappingTableValues(
				objectRelationship1.getObjectRelationshipId(),
				objectEntry1.getObjectEntryId(), user1.getUserId());
			ObjectRelationshipTestUtil.addObjectRelationshipMappingTableValues(
				objectRelationship1.getObjectRelationshipId(),
				objectEntry1.getObjectEntryId(), user2.getUserId());
		}
		else {
			String objectFieldName = _getObjectFieldName(objectRelationship1);

			_objectEntryLocalService.insertIntoOrUpdateExtensionTable(
				TestPropsValues.getUserId(),
				userObjectDefinition.getObjectDefinitionId(), user1.getUserId(),
				HashMapBuilder.<String, Serializable>put(
					objectFieldName, objectEntry1.getObjectEntryId()
				).build());

			values.put(objectFieldName, objectEntry1.getObjectEntryId());
		}

		_objectEntryLocalService.insertIntoOrUpdateExtensionTable(
			TestPropsValues.getUserId(),
			userObjectDefinition.getObjectDefinitionId(), user2.getUserId(),
			values);

		ObjectRelationshipTestUtil.assertGetRelatedModels(
			1, objectRelatedModelsProvider,
			objectRelationship3.getObjectRelationshipId(),
			objectEntry2.getObjectEntryId());

		AtomicInteger atomicInteger = new AtomicInteger();

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceRegistration<?> serviceRegistration =
			bundleContext.registerService(
				ModelListener.class,
				new BaseModelListener<User>() {

					@Override
					public Class<?> getModelClass() {
						return User.class;
					}

					@Override
					public void onAfterRemove(User user) {
						atomicInteger.incrementAndGet();
					}

				},
				null);

		try {
			_objectEntryLocalService.deleteObjectEntry(objectEntry1);
		}
		finally {
			serviceRegistration.unregister();
		}

		Assert.assertEquals(2, atomicInteger.get());
		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				objectEntry2.getObjectEntryId()));
		Assert.assertNull(_userLocalService.fetchUser(user1.getUserId()));
		Assert.assertNull(_userLocalService.fetchUser(user2.getUserId()));
	}

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@DeleteAfterTestRun
	private final List<ObjectDefinition> _objectDefinitions = new ArrayList<>();

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	private boolean _searchByLastName;

	@Inject
	private UserLocalService _userLocalService;

}