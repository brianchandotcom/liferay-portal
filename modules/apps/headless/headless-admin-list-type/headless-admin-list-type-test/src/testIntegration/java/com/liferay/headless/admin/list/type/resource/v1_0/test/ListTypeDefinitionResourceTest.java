/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.admin.list.type.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.list.type.client.dto.v1_0.ListTypeDefinition;
import com.liferay.headless.admin.list.type.client.dto.v1_0.ListTypeEntry;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.util.PropsUtil;

import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gabriel Albuquerque
 */
@RunWith(Arquillian.class)
public class ListTypeDefinitionResourceTest
	extends BaseListTypeDefinitionResourceTestCase {

	@Before
	public void setUp() throws Exception {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-164278", "true"
			).build());

		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-164278", "false"
			).build());

		super.tearDown();
	}

	@Override
	@Test
	public void testGetListTypeDefinitionsPageWithSortInteger()
		throws Exception {

		testGetListTypeDefinitionsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, listTypeDefinition1, listTypeDefinition2) -> {
				if (BeanTestUtil.hasProperty(
						listTypeDefinition1, entityField.getName())) {

					BeanTestUtil.setProperty(
						listTypeDefinition1, entityField.getName(), 0);
				}

				if (BeanTestUtil.hasProperty(
						listTypeDefinition2, entityField.getName())) {

					BeanTestUtil.setProperty(
						listTypeDefinition2, entityField.getName(), 1);
				}
			});
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetListTypeDefinition() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetListTypeDefinitionNotFound() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetListTypeDefinitionsPage() throws Exception {
	}

	@Override
	protected ListTypeDefinition randomListTypeDefinition() throws Exception {
		ListTypeDefinition listTypeDefinition =
			super.randomListTypeDefinition();

		listTypeDefinition.setName_i18n(
			Collections.singletonMap("en-US", RandomTestUtil.randomString()));

		ListTypeEntry listTypeEntry = new ListTypeEntry();

		listTypeEntry.setName_i18n(
			Collections.singletonMap("en-US", RandomTestUtil.randomString()));
		listTypeEntry.setKey(RandomTestUtil.randomString());

		listTypeDefinition.setListTypeEntries(
			new ListTypeEntry[] {listTypeEntry});

		return listTypeDefinition;
	}

	@Override
	protected ListTypeDefinition
			testDeleteListTypeDefinition_addListTypeDefinition()
		throws Exception {

		return _addListTypeDefinition(randomListTypeDefinition());
	}

	@Override
	protected ListTypeDefinition
			testGetListTypeDefinition_addListTypeDefinition()
		throws Exception {

		return _addListTypeDefinition(randomListTypeDefinition());
	}

	@Override
	protected ListTypeDefinition
			testGetListTypeDefinitionsPage_addListTypeDefinition(
				ListTypeDefinition listTypeDefinition)
		throws Exception {

		return _addListTypeDefinition(listTypeDefinition);
	}

	@Override
	protected ListTypeDefinition
			testGraphQLListTypeDefinition_addListTypeDefinition()
		throws Exception {

		return _addListTypeDefinition(randomListTypeDefinition());
	}

	@Override
	protected ListTypeDefinition
			testPatchListTypeDefinition_addListTypeDefinition()
		throws Exception {

		return _addListTypeDefinition(randomListTypeDefinition());
	}

	@Override
	protected ListTypeDefinition
			testPostListTypeDefinition_addListTypeDefinition(
				ListTypeDefinition listTypeDefinition)
		throws Exception {

		return _addListTypeDefinition(listTypeDefinition);
	}

	@Override
	protected ListTypeDefinition
			testPutListTypeDefinition_addListTypeDefinition()
		throws Exception {

		return _addListTypeDefinition(randomListTypeDefinition());
	}

	private ListTypeDefinition _addListTypeDefinition(
			ListTypeDefinition listTypeDefinition)
		throws Exception {

		return listTypeDefinitionResource.postListTypeDefinition(
			listTypeDefinition);
	}

}