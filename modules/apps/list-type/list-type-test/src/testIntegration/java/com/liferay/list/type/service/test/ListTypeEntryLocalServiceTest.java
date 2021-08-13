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
import com.liferay.list.type.exception.ListTypeEntryNameException;
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
		ListTypeDefinition listTypeDefinition =
			ListTypeDefinitionLocalServiceUtil.addListTypeDefinition(
				TestPropsValues.getCompanyId(),
				Collections.singletonMap(LocaleUtil.US, "ListTypeDefinition1"));

		// Name is null

		try {
			_testAddListTypeEntry(
				listTypeDefinition.getListTypeDefinitionId(), "Test", null);

			Assert.fail();
		}
		catch (ListTypeEntryNameException listTypeEntryNameException) {
			Assert.assertEquals(
				"Name is null", listTypeEntryNameException.getMessage());
		}

		// Name must only contain letters and digits

		try {
			_testAddListTypeEntry(
				listTypeDefinition.getListTypeDefinitionId(), "Test", " test ");

			Assert.fail();
		}
		catch (ListTypeEntryNameException listTypeEntryNameException) {
			Assert.assertEquals(
				"Name must only contain letters and digits",
				listTypeEntryNameException.getMessage());
		}

		// Names must be less than 41 characters

		_testAddListTypeEntry(
			listTypeDefinition.getListTypeDefinitionId(), "Test",
			"A123456789a123456789a123456789a1234567891");

		try {
			_testAddListTypeEntry(
				listTypeDefinition.getListTypeDefinitionId(), "Test",
				"A123456789a123456789a123456789a12345678912");
			Assert.fail();
		}
		catch (ListTypeEntryNameException listTypeEntryNameException) {
			Assert.assertEquals(
				"Names must be less than 41 characters",
				listTypeEntryNameException.getMessage());
		}

		// Duplicate name for the same ListTypeDefinition

		ListTypeEntry listTypeEntry =
			ListTypeEntryLocalServiceUtil.addListTypeEntry(
				TestPropsValues.getCompanyId(),
				listTypeDefinition.getListTypeDefinitionId(),
				Collections.singletonMap(LocaleUtil.US, "Test"), "Test");

		try {
			_testAddListTypeEntry(
				listTypeDefinition.getListTypeDefinitionId(), "Test", "test");
		}
		catch (DuplicateListTypeEntryException
					duplicateListTypeEntryException) {

			Assert.assertEquals(
				"Duplicate name test for the same ListTypeDefinitionId " +
					listTypeDefinition.getListTypeDefinitionId(),
				duplicateListTypeEntryException.getMessage());
		}

		ListTypeEntryLocalServiceUtil.deleteListTypeEntry(listTypeEntry);
	}

	private void _testAddListTypeEntry(
			long listTypeDefinitionId, String label, String name)
		throws Exception {

		ListTypeEntry listTypeEntry = null;

		try {
			listTypeEntry = ListTypeEntryLocalServiceUtil.addListTypeEntry(
				TestPropsValues.getCompanyId(), listTypeDefinitionId,
				Collections.singletonMap(LocaleUtil.US, label), name);
		}
		finally {
			if (listTypeEntry != null) {
				ListTypeEntryLocalServiceUtil.deleteListTypeEntry(
					listTypeEntry);
			}
		}
	}

}