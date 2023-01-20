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
import com.liferay.list.type.exception.DuplicateListTypeEntryExternalReferenceCodeException;
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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void setUp() throws Exception {
		_listTypeDefinition =
			ListTypeDefinitionLocalServiceUtil.addListTypeDefinition(
				null, TestPropsValues.getUserId(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()));
	}

	@After
	public void tearDown() throws Exception {
		ListTypeDefinitionLocalServiceUtil.deleteListTypeDefinition(
			_listTypeDefinition);
	}

	@Test
	public void testAddListTypeEntry() throws Exception {

		// No ListTypeDefinition exists with the primary key

		try {
			_testAddListTypeEntry(0, "able");

			Assert.fail();
		}
		catch (NoSuchListTypeDefinitionException
					noSuchListTypeDefinitionException) {

			Assert.assertEquals(
				"No ListTypeDefinition exists with the primary key 0",
				noSuchListTypeDefinitionException.getMessage());
		}

		// Key is null

		try {
			_testAddListTypeEntry(
				_listTypeDefinition.getListTypeDefinitionId(), null);

			Assert.fail();
		}
		catch (ListTypeEntryKeyException listTypeEntryKeyException) {
			Assert.assertEquals(
				"Key is null", listTypeEntryKeyException.getMessage());
		}

		// Key must only contain letters and digits

		try {
			_testAddListTypeEntry(
				_listTypeDefinition.getListTypeDefinitionId(), " able ");

			Assert.fail();
		}
		catch (ListTypeEntryKeyException listTypeEntryKeyException) {
			Assert.assertEquals(
				"Key must only contain letters and digits",
				listTypeEntryKeyException.getMessage());
		}

		ListTypeEntry listTypeEntry =
			ListTypeEntryLocalServiceUtil.addListTypeEntry(
				null, TestPropsValues.getUserId(),
				_listTypeDefinition.getListTypeDefinitionId(), "able",
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()));

		// Generate External Reference Code

		Assert.assertEquals(
			listTypeEntry.getUuid(), listTypeEntry.getExternalReferenceCode());

		// Duplicate key

		try {
			_testAddListTypeEntry(
				_listTypeDefinition.getListTypeDefinitionId(), "able");
		}
		catch (DuplicateListTypeEntryException
					duplicateListTypeEntryException) {

			Assert.assertEquals(
				"Duplicate key able",
				duplicateListTypeEntryException.getMessage());
		}

		ListTypeEntryLocalServiceUtil.deleteListTypeEntry(listTypeEntry);

		listTypeEntry = ListTypeEntryLocalServiceUtil.addListTypeEntry(
			"bakerExternalReferenceCode", TestPropsValues.getUserId(),
			_listTypeDefinition.getListTypeDefinitionId(), "baker",
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()));

		Assert.assertEquals(
			"bakerExternalReferenceCode",
			listTypeEntry.getExternalReferenceCode());

		// Duplicate external reference code

		try {
			ListTypeEntryLocalServiceUtil.addListTypeEntry(
				"bakerExternalReferenceCode", TestPropsValues.getUserId(),
				_listTypeDefinition.getListTypeDefinitionId(), "charlie",
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()));
		}
		catch (DuplicateListTypeEntryExternalReferenceCodeException
					duplicateListTypeEntryExternalReferenceCodeException) {

			Assert.assertEquals(
				"Duplicate external reference code bakerExternalReferenceCode",
				duplicateListTypeEntryExternalReferenceCodeException.
					getMessage());
		}

		ListTypeEntryLocalServiceUtil.deleteListTypeEntry(listTypeEntry);
	}

	@Test
	public void testFetchListTypeEntryByExternalReferenceCode()
		throws Exception {

		ListTypeEntry ablelistTypeEntry =
			ListTypeEntryLocalServiceUtil.addListTypeEntry(
				null, TestPropsValues.getUserId(),
				_listTypeDefinition.getListTypeDefinitionId(), "able",
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()));

		ListTypeEntry bakerListTypeEntry =
			ListTypeEntryLocalServiceUtil.
				fetchListTypeEntryByExternalReferenceCode(
					ablelistTypeEntry.getExternalReferenceCode(),
					ablelistTypeEntry.getCompanyId());

		Assert.assertNotNull(bakerListTypeEntry);

		bakerListTypeEntry =
			ListTypeEntryLocalServiceUtil.
				fetchListTypeEntryByExternalReferenceCode(
					null, ablelistTypeEntry.getCompanyId());

		Assert.assertNull(bakerListTypeEntry);

		ListTypeEntryLocalServiceUtil.deleteListTypeEntry(ablelistTypeEntry);
	}

	@Test
	public void testGetListTypeEntryByExternalReferenceCode() throws Exception {
		ListTypeEntry ablelistTypeEntry =
			ListTypeEntryLocalServiceUtil.addListTypeEntry(
				null, TestPropsValues.getUserId(),
				_listTypeDefinition.getListTypeDefinitionId(), "able",
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()));

		ListTypeEntry bakerListTypeEntry =
			ListTypeEntryLocalServiceUtil.
				getListTypeEntryByExternalReferenceCode(
					ablelistTypeEntry.getExternalReferenceCode(),
					ablelistTypeEntry.getCompanyId());

		Assert.assertNotNull(bakerListTypeEntry);

		try {
			ListTypeEntryLocalServiceUtil.
				getListTypeEntryByExternalReferenceCode(
					"noERC", ablelistTypeEntry.getCompanyId());
		}
		catch (Exception exception) {
			Assert.assertEquals(
				StringBundler.concat(
					"No ListTypeEntry exists with the key ",
					"{externalReferenceCode=noERC, companyId=",
					String.valueOf(ablelistTypeEntry.getCompanyId()), "}"),
				exception.getMessage());
		}

		ListTypeEntryLocalServiceUtil.deleteListTypeEntry(ablelistTypeEntry);
	}

	@Test
	public void testUpdateListTypeEntry() throws Exception {
		ListTypeEntry ablelistTypeEntry =
			ListTypeEntryLocalServiceUtil.addListTypeEntry(
				null, TestPropsValues.getUserId(),
				_listTypeDefinition.getListTypeDefinitionId(), "able",
				Collections.singletonMap(LocaleUtil.US, "Able"));

		String listTypeEntryExternalReferenceCode =
			"listTypeEntryExternalReferenceCode";

		Map<Locale, String> updatedNameMap = Collections.singletonMap(
			LocaleUtil.US, "Updated Able");

		ListTypeEntry updatedAblelistTypeEntry =
			ListTypeEntryLocalServiceUtil.updateListTypeEntry(
				listTypeEntryExternalReferenceCode,
				ablelistTypeEntry.getListTypeEntryId(), updatedNameMap);

		Assert.assertEquals(
			listTypeEntryExternalReferenceCode,
			updatedAblelistTypeEntry.getExternalReferenceCode());

		Assert.assertEquals(
			updatedNameMap, updatedAblelistTypeEntry.getNameMap());

		ListTypeEntryLocalServiceUtil.deleteListTypeEntry(ablelistTypeEntry);
	}

	private void _testAddListTypeEntry(long listTypeDefinitionId, String key)
		throws Exception {

		ListTypeEntry listTypeEntry = null;

		try {
			listTypeEntry = ListTypeEntryLocalServiceUtil.addListTypeEntry(
				null, TestPropsValues.getUserId(), listTypeDefinitionId, key,
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

	private ListTypeDefinition _listTypeDefinition;

}