/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.list.type.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.list.type.dto.v1_0.ListTypeDefinition;
import com.liferay.headless.admin.list.type.resource.v1_0.ListTypeDefinitionResource;
import com.liferay.object.web.internal.BaseExportImportTestCase;
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
 * @author Guilherme Sa
 */
@RunWith(Arquillian.class)
public class ListTypeDefinitionExportImportTest
	extends BaseExportImportTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		user = TestPropsValues.getUser();

		ListTypeDefinitionResource.Builder builder =
			_listTypeDefinitionResourceFactory.create();

		_listTypeDefinitionResource = builder.user(
			user
		).build();
	}

	@Test
	public void testExportImportObjectDefinition() throws Exception {

		// Localized list type definition

		testExportImport(
			"test-list-type-definition.portuguese-locale.json",
			"test-list-type-definition.site-default-locale.json",
			"TESTLISTTYPEDEFINITIONPORTUGUESE",
			"TestListTypeDefinitionPortuguese");

		// Localized list type definition update

		testExportImport(
			"test-list-type-definition.portuguese-locale.json",
			"test-list-type-definition.site-default-locale.json",
			"TESTLISTTYPEDEFINITIONPORTUGUESE",
			"TestListTypeDefinitionPortuguese");
	}

	@Override
	protected ClassLoader getClassLoader() {
		return ListTypeDefinitionExportImportTest.class.getClassLoader();
	}

	@Override
	protected Class<?> getClazz() {
		return getClass();
	}

	@Override
	protected long getId(String name) throws Exception {
		ListTypeDefinition listTypeDefinition = _getListTypeDefinition(name);

		return listTypeDefinition.getId();
	}

	@Override
	protected String getIdentifierName() {
		return "listTypeDefinitionId";
	}

	@Override
	protected String getJSONName() {
		return "listTypeDefinitionJSON";
	}

	@Override
	protected MVCActionCommand getMVCActionCommand() {
		return _mvcActionCommand;
	}

	@Override
	protected MVCResourceCommand getMVCResourceCommand() {
		return _mvcResourceCommand;
	}

	private ListTypeDefinition _getListTypeDefinition(String name)
		throws Exception {

		Page<ListTypeDefinition> page =
			_listTypeDefinitionResource.getListTypeDefinitionsPage(
				name, null, null, Pagination.of(1, 1), null);

		List<ListTypeDefinition> items =
			(List<ListTypeDefinition>)page.getItems();

		return items.get(0);
	}

	private ListTypeDefinitionResource _listTypeDefinitionResource;

	@Inject
	private ListTypeDefinitionResource.Factory
		_listTypeDefinitionResourceFactory;

	@Inject(
		filter = "mvc.command.name=/list_type_definitions/import_list_type_definition"
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject(
		filter = "mvc.command.name=/list_type_definitions/export_list_type_definition"
	)
	private MVCResourceCommand _mvcResourceCommand;

}