/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.info.collection.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.relationship.util.ObjectRelationshipUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.test.util.ObjectRelationshipTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class OneToManyObjectRelationshipInfoCollectionProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_objectFieldName = "a" + RandomTestUtil.randomString();

		_customObjectDefinition1 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				Arrays.asList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						_objectFieldName
					).build()),
				ObjectDefinitionConstants.SCOPE_SITE);
		_customObjectDefinition2 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				Arrays.asList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						_objectFieldName
					).build()),
				ObjectDefinitionConstants.SCOPE_SITE);

		_group = GroupTestUtil.addGroup();

		_modifiableSystemObjectDefinition =
			ObjectDefinitionTestUtil.addModifiableSystemObjectDefinition(
				TestPropsValues.getUserId(), null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_SITE, null, 1,
				Arrays.asList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						_objectFieldName
					).build()));

		_objectDefinitionLocalService.publishSystemObjectDefinition(
			TestPropsValues.getUserId(),
			_modifiableSystemObjectDefinition.getObjectDefinitionId());
	}

	@Test
	public void testOneToManyObjectRelationshipRelatedInfoCollectionProviderWithCustomObjectDefinition()
		throws Exception {

		_testOneToManyObjectRelationshipRelatedInfoCollectionProvider(
			_customObjectDefinition1, _customObjectDefinition2);
	}

	@Test
	public void testOneToManyObjectRelationshipRelatedInfoCollectionProviderWithSystemObjectDefinition()
		throws Exception {

		// Modifiable system object definition as child

		_testOneToManyObjectRelationshipRelatedInfoCollectionProvider(
			_modifiableSystemObjectDefinition, _customObjectDefinition1);

		// Modifiable system object definition as parent

		_testOneToManyObjectRelationshipRelatedInfoCollectionProvider(
			_customObjectDefinition1, _modifiableSystemObjectDefinition);

		// Unmodifiable system object definition as child

		ObjectDefinition unmodifiableSystemObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
				TestPropsValues.getCompanyId(), User.class.getName());

		ObjectRelationshipTestUtil.addObjectRelationship(
			_objectRelationshipLocalService, _customObjectDefinition2,
			unmodifiableSystemObjectDefinition,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			StringUtil.randomId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		Assert.assertNull(
			_infoItemServiceRegistry.getFirstInfoItemService(
				RelatedInfoItemCollectionProvider.class,
				_customObjectDefinition2.getClassName()));

		// Unmodifiable system object definition as parent

		ObjectRelationshipTestUtil.addObjectRelationship(
			_objectRelationshipLocalService, unmodifiableSystemObjectDefinition,
			_customObjectDefinition2,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			StringUtil.randomId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		Assert.assertNull(
			_infoItemServiceRegistry.getFirstInfoItemService(
				RelatedInfoItemCollectionProvider.class,
				unmodifiableSystemObjectDefinition.getClassName()));
	}

	private ObjectEntry _addObjectEntry(ObjectDefinition objectDefinition)
		throws Exception {

		return _objectEntryLocalService.addObjectEntry(
			_group.getGroupId(), TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			null,
			HashMapBuilder.<String, Serializable>put(
				_objectFieldName, RandomTestUtil.randomString()
			).build(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	private ObjectEntry _addObjectEntry(
			ObjectDefinition childObjectDefinition,
			ObjectRelationship objectRelationship,
			ObjectDefinition parentObjectDefinition,
			ObjectEntry parentObjectEntry)
		throws Exception {

		return _objectEntryLocalService.addObjectEntry(
			_group.getGroupId(), TestPropsValues.getUserId(),
			childObjectDefinition.getObjectDefinitionId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			null,
			HashMapBuilder.<String, Serializable>put(
				_objectFieldName, RandomTestUtil.randomString()
			).put(
				ObjectRelationshipUtil.getObjectRelationshipFieldName(
					parentObjectDefinition, objectRelationship.getName()),
				parentObjectEntry.getObjectEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	private void _testOneToManyObjectRelationshipRelatedInfoCollectionProvider(
			ObjectDefinition childObjectDefinition,
			ObjectDefinition parentObjectDefinition)
		throws Exception {

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, parentObjectDefinition,
				childObjectDefinition,
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectEntry parentObjectEntry = _addObjectEntry(parentObjectDefinition);

		ObjectEntry childObjectEntry1 = _addObjectEntry(
			childObjectDefinition, objectRelationship, parentObjectDefinition,
			parentObjectEntry);
		ObjectEntry childObjectEntry2 = _addObjectEntry(
			childObjectDefinition, objectRelationship, parentObjectDefinition,
			parentObjectEntry);

		RelatedInfoItemCollectionProvider relatedInfoItemCollectionProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				RelatedInfoItemCollectionProvider.class,
				parentObjectDefinition.getClassName());

		Assert.assertEquals(
			childObjectDefinition.getClassName(),
			relatedInfoItemCollectionProvider.getCollectionItemClassName());

		CollectionQuery collectionQuery = new CollectionQuery();

		collectionQuery.setPagination(Pagination.of(2, 0));
		collectionQuery.setRelatedItemObject(parentObjectEntry);

		InfoPage collectionInfoPage =
			relatedInfoItemCollectionProvider.getCollectionInfoPage(
				collectionQuery);

		Assert.assertEquals(2, collectionInfoPage.getTotalCount());

		List<ObjectEntry> objectEntries = collectionInfoPage.getPageItems();

		Assert.assertEquals(objectEntries.toString(), 2, objectEntries.size());
		Assert.assertTrue(objectEntries.contains(childObjectEntry1));
		Assert.assertTrue(objectEntries.contains(childObjectEntry2));
	}

	@DeleteAfterTestRun
	private ObjectDefinition _customObjectDefinition1;

	@DeleteAfterTestRun
	private ObjectDefinition _customObjectDefinition2;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@DeleteAfterTestRun
	private ObjectDefinition _modifiableSystemObjectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	private String _objectFieldName;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}