/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gabriel Albuquerque
 */
@RunWith(Arquillian.class)
public class ObjectDefinitionExportImportTest extends BaseExportImportTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		user = TestPropsValues.getUser();

		ObjectDefinitionResource.Builder builder =
			_objectDefinitionResourceFactory.create();

		_objectDefinitionResource = builder.user(
			user
		).build();
	}

	@Test
	public void testExportImportObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition = _getObjectDefinition(
			"AccountEntry");

		testExportImport(
			"test-account-entry-system-object-definition.json",
			"test-account-entry-system-object-definition.json",
			objectDefinition.getExternalReferenceCode(), "AccountEntry");

		testExportImport(
			"test-object-definition-1.json", "test-object-definition-1.json",
			null, "TestObjectDefinition1");

		testExportImport(
			"test-object-definition-2.json",
			"test-object-definition-2.site-default-locale.json", null,
			"TestObjectDefinition2");
	}

	@Override
	protected ClassLoader getClassLoader() {
		return ObjectDefinitionExportImportTest.class.getClassLoader();
	}

	@Override
	protected Class<?> getClazz() {
		return getClass();
	}

	@Override
	protected long getId(String name) throws Exception {
		ObjectDefinition objectDefinition = _getObjectDefinition(name);

		return objectDefinition.getId();
	}

	@Override
	protected String getIdentifierName() {
		return "objectDefinitionId";
	}

	@Override
	protected String getJSONName() {
		return "objectDefinitionJSON";
	}

	@Override
	protected MVCActionCommand getMVCActionCommand() {
		return _mvcActionCommand;
	}

	@Override
	protected MVCResourceCommand getMVCResourceCommand() {
		return _mvcResourceCommand;
	}

	private ObjectDefinition _getObjectDefinition(String name)
		throws Exception {

		Page<ObjectDefinition> page =
			_objectDefinitionResource.getObjectDefinitionsPage(
				name, null, null, Pagination.of(1, 1), null);

		List<ObjectDefinition> items = (List<ObjectDefinition>)page.getItems();

		return items.get(0);
	}

	@Inject(
		filter = "mvc.command.name=/object_definitions/import_object_definition"
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject(
		filter = "mvc.command.name=/object_definitions/export_object_definition"
	)
	private MVCResourceCommand _mvcResourceCommand;

	private ObjectDefinitionResource _objectDefinitionResource;

	@Inject
	private ObjectDefinitionResource.Factory _objectDefinitionResourceFactory;

}