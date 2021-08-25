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
import com.liferay.headless.admin.list.type.client.dto.v1_0.ListTypeEntry;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collections;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Gabriel Albuquerque
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class ListTypeEntryResourceTest
	extends BaseListTypeEntryResourceTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_listTypeDefinition =
			ListTypeDefinitionLocalServiceUtil.addListTypeDefinition(
				TestPropsValues.getUserId(),
				Collections.singletonMap(LocaleUtil.getDefault(), "test"));
	}

	@Override
	protected Long
		testGetListTypeDefinitionListTypeEntriesPage_getListTypeDefinitionId() {

		return _listTypeDefinition.getListTypeDefinitionId();
	}

	@Override
	protected ListTypeEntry testGraphQLListTypeEntry_addListTypeEntry()
		throws Exception {

		return _addListTypeEntry();
	}

	private ListTypeEntry _addListTypeEntry() throws Exception {
		return listTypeEntryResource.postListTypeDefinitionListTypeEntry(
			_listTypeDefinition.getListTypeDefinitionId(),
			randomListTypeEntry());
	}

	private ListTypeDefinition _listTypeDefinition;

}