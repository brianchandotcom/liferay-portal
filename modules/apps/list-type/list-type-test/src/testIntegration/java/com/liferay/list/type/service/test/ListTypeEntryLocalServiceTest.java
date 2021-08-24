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
import com.liferay.list.type.exception.DuplicateListTypeEntryException;
import com.liferay.list.type.exception.ListTypeEntryKeyException;
import com.liferay.list.type.exception.NoSuchListTypeDefinitionException;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalServiceUtil;
import com.liferay.list.type.service.ListTypeEntryLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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

		// No ListTypeDefinition exists with the primary key

		try {
			_testAddListTypeEntry(0, "test");

			Assert.fail();
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
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()));

		// Key is null

		try {
			_testAddListTypeEntry(
				listTypeDefinition.getListTypeDefinitionId(), null);

			Assert.fail();
		}
		catch (ListTypeEntryKeyException listTypeEntryKeyException) {
			Assert.assertEquals(
				"Key is null", listTypeEntryKeyException.getMessage());
		}

		// Key must only contain letters and digits

		try {
			_testAddListTypeEntry(
				listTypeDefinition.getListTypeDefinitionId(), " test ");

			Assert.fail();
		}
		catch (ListTypeEntryKeyException listTypeEntryKeyException) {
			Assert.assertEquals(
				"Key must only contain letters and digits",
				listTypeEntryKeyException.getMessage());
		}

		// Keys must be less than 41 characters

		_testAddListTypeEntry(
			listTypeDefinition.getListTypeDefinitionId(),
			"A123456789a123456789a123456789a1234567891");

		try {
			_testAddListTypeEntry(
				listTypeDefinition.getListTypeDefinitionId(),
				"A123456789a123456789a123456789a12345678912");
			Assert.fail();
		}
		catch (ListTypeEntryKeyException listTypeEntryKeyException) {
			Assert.assertEquals(
				"Keys must be less than 41 characters",
				listTypeEntryKeyException.getMessage());
		}

		// Duplicate key for the same ListTypeDefinition

		ListTypeEntry listTypeEntry =
			ListTypeEntryLocalServiceUtil.addListTypeEntry(
				TestPropsValues.getUserId(),
				listTypeDefinition.getListTypeDefinitionId(), "test",
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()));

		try {
			_testAddListTypeEntry(
				listTypeDefinition.getListTypeDefinitionId(), "test");
		}
		catch (DuplicateListTypeEntryException
					duplicateListTypeEntryException) {

			Assert.assertEquals(
				"Duplicate key test for the same ListTypeDefinitionId " +
					listTypeDefinition.getListTypeDefinitionId(),
				duplicateListTypeEntryException.getMessage());
		}

		ListTypeEntryLocalServiceUtil.deleteListTypeEntry(listTypeEntry);
	}

	private void _testAddListTypeEntry(long listTypeDefinitionId, String key)
		throws Exception {

		ListTypeEntry listTypeEntry = null;

		try {
			listTypeEntry = ListTypeEntryLocalServiceUtil.addListTypeEntry(
				TestPropsValues.getUserId(), listTypeDefinitionId, key,
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()));
		}
		finally {
			if (listTypeEntry != null) {
				ListTypeEntryLocalServiceUtil.deleteListTypeEntry(
					listTypeEntry);
			}
		}
	}

}