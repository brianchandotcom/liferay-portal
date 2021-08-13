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

package com.liferay.list.type.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.list.type.exception.NoSuchListTypeDefinitionException;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalServiceUtil;
import com.liferay.list.type.service.ListTypeEntryLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gabriel Albuquerque
 */
@RunWith(Arquillian.class)
public class ListTypeEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddListTypeEntry() throws Exception {

		// NoSuchListTypeDefinitionException

		try {
			ListTypeEntryLocalServiceUtil.addListTypeEntry(
				TestPropsValues.getUserId(), 0,
				Collections.singletonMap(LocaleUtil.US, "ListTypeEntry1"));
		}
		catch (NoSuchListTypeDefinitionException
					noSuchListTypeDefinitionException) {

			Assert.assertEquals(
				"No ListTypeDefinition exists with the primary key 0",
				noSuchListTypeDefinitionException.getMessage());
		}

		ListTypeDefinition listTypeDefinition =
			ListTypeDefinitionLocalServiceUtil.addListTypeDefinition(
				TestPropsValues.getUserId(),
				Collections.singletonMap(LocaleUtil.US, "ListTypeDefinition1"));

		ListTypeEntry listTypeEntry =
			ListTypeEntryLocalServiceUtil.addListTypeEntry(
				TestPropsValues.getUserId(),
				listTypeDefinition.getListTypeDefinitionId(),
				Collections.singletonMap(LocaleUtil.US, "ListTypeEntry1"));

		Assert.assertNotNull(listTypeEntry);
		Assert.assertNotNull(
			ListTypeEntryLocalServiceUtil.fetchListTypeEntry(
				listTypeEntry.getListTypeEntryId()));
	}

}