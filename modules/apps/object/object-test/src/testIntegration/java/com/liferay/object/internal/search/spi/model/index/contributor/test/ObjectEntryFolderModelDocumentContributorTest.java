/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.index.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class ObjectEntryFolderModelDocumentContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testContribute() throws Exception {

		// Content root folder

		ObjectEntryFolder contentParentObjectEntryFolder =
			_addObjectEntryFolder(
				ObjectEntryFolderConstants.
					CONTENTS_OBJECT_ENTRY_FOLDER_EXTERNAL_REFERENCE_CODE,
				ObjectEntryFolderConstants.
					PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT);

		ObjectEntryFolder objectEntryFolder1 = _addObjectEntryFolder(
			null, contentParentObjectEntryFolder.getObjectEntryFolderId());

		Document document1 = _contribute(objectEntryFolder1);

		Assert.assertEquals(
			String.valueOf(objectEntryFolder1.getObjectEntryFolderId()),
			document1.get(Field.FOLDER_ID));
		Assert.assertEquals(
			objectEntryFolder1.getName(), document1.get(Field.NAME));
		Assert.assertEquals("content", document1.get("cms_section"));

		// Files root folder

		ObjectEntryFolder filesParentObjectEntryFolder = _addObjectEntryFolder(
			ObjectEntryFolderConstants.
				FILES_OBJECT_ENTRY_FOLDER_EXTERNAL_REFERENCE_CODE,
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT);

		ObjectEntryFolder objectEntryFolder2 = _addObjectEntryFolder(
			null, filesParentObjectEntryFolder.getObjectEntryFolderId());

		Document document2 = _contribute(objectEntryFolder2);

		Assert.assertEquals(
			String.valueOf(objectEntryFolder2.getObjectEntryFolderId()),
			document2.get(Field.FOLDER_ID));
		Assert.assertEquals(
			objectEntryFolder2.getName(), document2.get(Field.NAME));
		Assert.assertEquals("files", document2.get("cms_section"));

		// Root folder outside of CMS

		ObjectEntryFolder parentObjectEntryFolder = _addObjectEntryFolder(
			null,
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT);

		ObjectEntryFolder objectEntryFolder3 = _addObjectEntryFolder(
			null, parentObjectEntryFolder.getObjectEntryFolderId());

		Document document3 = _contribute(objectEntryFolder3);

		Assert.assertEquals(
			String.valueOf(objectEntryFolder3.getObjectEntryFolderId()),
			document3.get(Field.FOLDER_ID));
		Assert.assertEquals(
			objectEntryFolder3.getName(), document3.get(Field.NAME));
		Assert.assertEquals("none", document3.get("cms_section"));
	}

	private ObjectEntryFolder _addObjectEntryFolder(
			String externalReferenceCode, long parentObjectEntryFolderId)
		throws Exception {

		return _objectEntryFolderLocalService.addObjectEntryFolder(
			externalReferenceCode, TestPropsValues.getUserId(),
			_group.getGroupId(), parentObjectEntryFolderId,
			HashMapBuilder.put(
				LocaleUtil.ENGLISH, RandomTestUtil.randomString()
			).build(),
			RandomTestUtil.randomString(), new ServiceContext());
	}

	private Document _contribute(ObjectEntryFolder objectEntryFolder) {
		Document document = new DocumentImpl();

		_objectEntryFolderModelDocumentContributor.contribute(
			document, objectEntryFolder);

		return document;
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Inject(
		filter = "component.name=com.liferay.object.internal.search.spi.model.index.contributor.ObjectEntryFolderModelDocumentContributor"
	)
	private ModelDocumentContributor<ObjectEntryFolder>
		_objectEntryFolderModelDocumentContributor;

}