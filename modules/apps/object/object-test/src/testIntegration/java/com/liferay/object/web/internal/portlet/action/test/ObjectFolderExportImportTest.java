/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.admin.rest.dto.v1_0.ObjectFolder;
import com.liferay.object.admin.rest.dto.v1_0.ObjectFolderItem;
import com.liferay.object.admin.rest.resource.v1_0.ObjectFolderResource;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Guilherme Sa
 */
@FeatureFlags("LPS-148856")
@RunWith(Arquillian.class)
public class ObjectFolderExportImportTest extends BaseExportImportTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		user = TestPropsValues.getUser();

		ObjectFolderResource.Builder builder =
			_objectFolderResourceFactory.create();

		_objectFolderResource = builder.user(
			user
		).build();
	}

	@Test
	public void testExportImportObjectFolder() throws Exception {
		testExportImport(
			"EmptyObjectFolderERC", "empty_object_folder.json",
			"EmptyObjectFolder");

		_assertObjectFolder(
			"EmptyObjectFolder", 0, Collections.emptyList(),
			Collections.emptyList());

		testExportImport(
			"object_folder_invalid_items.json", "empty_object_folder.json",
			"EmptyObjectFolderERC", "EmptyObjectFolder");

		_assertObjectFolder(
			"EmptyObjectFolder", 0, Collections.emptyList(),
			Collections.emptyList());

		// Import and export an object folder with a linked object folder item
		// that doesn't exist

		testExportImport(
			"ObjectFolder1ERC", "object_folder_1.json", "ObjectFolder1");

		_assertObjectFolder(
			"ObjectFolder1", 2,
			Collections.singletonList("ObjectDefinition2ERC"),
			Collections.singletonList("ObjectDefinition1ERC"));
		_assertUncategorizedObjectFolder(
			Collections.singletonList("ObjectDefinition1ERC"),
			Collections.singletonList("ObjectDefinition2ERC"));

		// Import and export an object folder that moves an object definition
		// between object folders during import

		testExportImport(
			"ObjectFolder2ERC", "object_folder_2.json", "ObjectFolder2");

		_assertObjectFolder(
			"ObjectFolder1", 2,
			Collections.singletonList("ObjectDefinition2ERC"),
			Collections.singletonList("ObjectDefinition1ERC"));
		_assertObjectFolder(
			"ObjectFolder2", 2,
			Collections.singletonList("ObjectDefinition1ERC"),
			Collections.singletonList("ObjectDefinition2ERC"));
		_assertUncategorizedObjectFolder(
			Collections.emptyList(), Collections.emptyList());

		// Import and export an object folder with duplicate external reference
		// code

		testExportImport(
			"ObjectFolder2ERC", "empty_object_folder.json",
			"EmptyObjectFolder");

		_assertObjectFolder(
			"ObjectFolder1", 2,
			Collections.singletonList("ObjectDefinition2ERC"),
			Collections.singletonList("ObjectDefinition1ERC"));
		_assertObjectFolder(
			"ObjectFolder2", 0, Collections.emptyList(),
			Collections.emptyList());
		_assertUncategorizedObjectFolder(
			Collections.singletonList("ObjectDefinition1ERC"),
			Collections.singletonList("ObjectDefinition2ERC"));
	}

	@Override
	protected ClassLoader getClassLoader() {
		return ObjectFolderExportImportTest.class.getClassLoader();
	}

	@Override
	protected Class<?> getClazz() {
		return getClass();
	}

	@Override
	protected long getId(String name) throws Exception {
		ObjectFolder importedFolder = _getObjectFolder(name);

		return importedFolder.getId();
	}

	@Override
	protected String getIdentifierName() {
		return "objectFolderId";
	}

	@Override
	protected String getJSONName() {
		return "objectFolderJSON";
	}

	@Override
	protected MVCActionCommand getMvcActionCommand() {
		return _mvcActionCommand;
	}

	@Override
	protected MVCResourceCommand getMvcResourceCommand() {
		return _mvcResourceCommand;
	}

	private void _assertObjectFolder(
			String name, long expectedLength,
			List<String> linkedObjectFolderItemExternalReferenceCodes,
			List<String> unlinkedObjectFolderItemExternalReferenceCodes)
		throws Exception {

		ObjectFolder objectFolder = _getObjectFolder(name);

		ObjectFolderItem[] objectFolderItems =
			objectFolder.getObjectFolderItems();

		Assert.assertEquals(
			Arrays.toString(objectFolderItems), expectedLength,
			objectFolderItems.length);

		for (ObjectFolderItem objectFolderItem : objectFolderItems) {
			if (objectFolderItem.getLinkedObjectDefinition()) {
				Assert.assertTrue(
					linkedObjectFolderItemExternalReferenceCodes.contains(
						objectFolderItem.
							getObjectDefinitionExternalReferenceCode()));
			}
			else {
				Assert.assertTrue(
					unlinkedObjectFolderItemExternalReferenceCodes.contains(
						objectFolderItem.
							getObjectDefinitionExternalReferenceCode()));
			}
		}
	}

	private void _assertUncategorizedObjectFolder(
			List<String> linkedObjectFolderItemExternalReferenceCodes,
			List<String> unlinkedObjectFolderItemExternalReferenceCodes)
		throws Exception {

		ObjectFolder uncategorizedObjectFolder = _getObjectFolder(
			ObjectFolderConstants.NAME_UNCATEGORIZED);

		List<String> uncategorizedObjectFolderItemExternalReferenceCodes =
			TransformUtil.transform(
				ListUtil.fromArray(
					uncategorizedObjectFolder.getObjectFolderItems()),
				ObjectFolderItem::getObjectDefinitionExternalReferenceCode);

		Assert.assertTrue(
			uncategorizedObjectFolderItemExternalReferenceCodes.containsAll(
				linkedObjectFolderItemExternalReferenceCodes));

		Assert.assertTrue(
			uncategorizedObjectFolderItemExternalReferenceCodes.containsAll(
				unlinkedObjectFolderItemExternalReferenceCodes));

		for (ObjectFolderItem objectFolderItem :
				uncategorizedObjectFolder.getObjectFolderItems()) {

			if (linkedObjectFolderItemExternalReferenceCodes.contains(
					objectFolderItem.
						getObjectDefinitionExternalReferenceCode())) {

				Assert.assertTrue(objectFolderItem.getLinkedObjectDefinition());
			}

			if (unlinkedObjectFolderItemExternalReferenceCodes.contains(
					objectFolderItem.
						getObjectDefinitionExternalReferenceCode())) {

				Assert.assertFalse(
					objectFolderItem.getLinkedObjectDefinition());
			}
		}
	}

	private ObjectFolder _getObjectFolder(String name) throws Exception {
		Page<ObjectFolder> page = _objectFolderResource.getObjectFoldersPage(
			name, Pagination.of(1, 1));

		List<ObjectFolder> items = (List<ObjectFolder>)page.getItems();

		return items.get(0);
	}

	@Inject
	private JSONFactory _jsonFactory;

	@Inject(
		filter = "mvc.command.name=/object_definitions/import_object_folder"
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject(
		filter = "mvc.command.name=/object_definitions/export_object_folder"
	)
	private MVCResourceCommand _mvcResourceCommand;

	private ObjectFolderResource _objectFolderResource;

	@Inject
	private ObjectFolderResource.Factory _objectFolderResourceFactory;

}