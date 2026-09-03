/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.admin.rest.dto.v1_0.ObjectFolder;
import com.liferay.object.admin.rest.dto.v1_0.ObjectFolderItem;
import com.liferay.object.admin.rest.resource.v1_0.ObjectFolderResource;
import com.liferay.object.admin.rest.resource.v1_0.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class ObjectFolderPutReindexTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			ObjectDefinition.class);

		ObjectFolderResource.Builder builder =
			_objectFolderResourceFactory.create();

		_objectFolderResource = builder.user(
			TestPropsValues.getUser()
		).build();

		_objectDefinition =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();

		_indexWriterHelper.commit(_objectDefinition.getCompanyId());
	}

	@After
	public void tearDown() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				_objectDefinition.getObjectDefinitionId());

		if (objectDefinition != null) {
			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition);
		}

		if (_objectFolderId != 0) {
			_objectFolderLocalService.deleteObjectFolder(_objectFolderId);
		}
	}

	@Test
	public void testPutObjectFolderReindexesMovedObjectDefinition()
		throws Exception {

		_deleteObjectDefinitionDocument();

		ObjectFolder objectFolder = _objectFolderResource.postObjectFolder(
			new ObjectFolder() {
				{
					setExternalReferenceCode(RandomTestUtil.randomString());
					setLabel(
						Collections.singletonMap(
							"en_US", RandomTestUtil.randomString()));
					setName(
						StringUtil.toLowerCase(RandomTestUtil.randomString()));
					setObjectFolderItems(new ObjectFolderItem[0]);
				}
			});

		_objectFolderId = objectFolder.getId();

		objectFolder.setObjectFolderItems(
			new ObjectFolderItem[] {
				_toObjectFolderItem(
					false, _objectDefinition.getExternalReferenceCode(), 0, 0)
			});

		_objectFolderResource.putObjectFolderByExternalReferenceCode(
			objectFolder.getExternalReferenceCode(), objectFolder);

		_indexWriterHelper.commit(_objectDefinition.getCompanyId());

		List<Long> objectDefinitionIds = _getIndexedObjectDefinitionIds();

		Assert.assertTrue(
			_getMessage(objectDefinitionIds),
			objectDefinitionIds.contains(
				_objectDefinition.getObjectDefinitionId()));
	}

	@Test
	public void testPutObjectFolderSkipsUnmovedObjectDefinition()
		throws Exception {

		_deleteObjectDefinitionDocument();

		ObjectFolder objectFolder =
			_objectFolderResource.getObjectFolderByExternalReferenceCode(
				ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_DEFAULT);

		ObjectFolderItem[] objectFolderItems =
			objectFolder.getObjectFolderItems();

		ObjectFolderItem[] newObjectFolderItems =
			new ObjectFolderItem[objectFolderItems.length];

		for (int i = 0; i < objectFolderItems.length; i++) {
			ObjectFolderItem objectFolderItem = objectFolderItems[i];

			newObjectFolderItems[i] = _toObjectFolderItem(
				objectFolderItem.getLinkedObjectDefinition(),
				objectFolderItem.getObjectDefinitionExternalReferenceCode(),
				objectFolderItem.getPositionX(),
				objectFolderItem.getPositionY());
		}

		objectFolder.setObjectFolderItems(newObjectFolderItems);

		_objectFolderResource.putObjectFolderByExternalReferenceCode(
			ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_DEFAULT,
			objectFolder);

		_indexWriterHelper.commit(_objectDefinition.getCompanyId());

		List<Long> objectDefinitionIds = _getIndexedObjectDefinitionIds();

		Assert.assertFalse(
			_getMessage(objectDefinitionIds),
			objectDefinitionIds.contains(
				_objectDefinition.getObjectDefinitionId()));
	}

	private void _deleteObjectDefinitionDocument() throws Exception {
		List<Long> objectDefinitionIds = _getIndexedObjectDefinitionIds();

		Assert.assertTrue(
			_getMessage(objectDefinitionIds),
			objectDefinitionIds.contains(
				_objectDefinition.getObjectDefinitionId()));

		_indexer.delete(_objectDefinition);

		_indexWriterHelper.commit(_objectDefinition.getCompanyId());

		objectDefinitionIds = _getIndexedObjectDefinitionIds();

		Assert.assertFalse(
			_getMessage(objectDefinitionIds),
			objectDefinitionIds.contains(
				_objectDefinition.getObjectDefinitionId()));
	}

	private List<Long> _getIndexedObjectDefinitionIds() throws Exception {
		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(
			Field.NAME, _objectDefinition.getShortName());
		searchContext.setCompanyId(_objectDefinition.getCompanyId());
		searchContext.setEntryClassNames(
			new String[] {ObjectDefinition.class.getName()});
		searchContext.setKeywords(_objectDefinition.getShortName());

		Hits hits = _indexer.search(searchContext);

		List<Long> objectDefinitionIds = new ArrayList<>();

		for (Document document : hits.getDocs()) {
			objectDefinitionIds.add(
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)));
		}

		return objectDefinitionIds;
	}

	private String _getMessage(List<Long> objectDefinitionIds) {
		return StringBundler.concat(
			"Object definition ", _objectDefinition.getObjectDefinitionId(),
			" named ", _objectDefinition.getShortName(),
			" against the indexed object definition IDs ", objectDefinitionIds);
	}

	private ObjectFolderItem _toObjectFolderItem(
		Boolean linkedObjectDefinition,
		String objectDefinitionExternalReferenceCode, Integer positionX,
		Integer positionY) {

		ObjectFolderItem objectFolderItem = new ObjectFolderItem();

		objectFolderItem.setLinkedObjectDefinition(linkedObjectDefinition);
		objectFolderItem.setObjectDefinitionExternalReferenceCode(
			objectDefinitionExternalReferenceCode);
		objectFolderItem.setPositionX(positionX);
		objectFolderItem.setPositionY(positionY);

		return objectFolderItem;
	}

	private Indexer<ObjectDefinition> _indexer;

	@Inject
	private IndexWriterHelper _indexWriterHelper;

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private long _objectFolderId;

	@Inject
	private ObjectFolderLocalService _objectFolderLocalService;

	private ObjectFolderResource _objectFolderResource;

	@Inject
	private ObjectFolderResource.Factory _objectFolderResourceFactory;

}