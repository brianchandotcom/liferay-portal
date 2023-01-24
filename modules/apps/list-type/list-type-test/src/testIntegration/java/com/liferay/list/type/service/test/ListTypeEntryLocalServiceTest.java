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
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.test.rule.Inject;
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
			_listTypeDefinitionLocalService.addListTypeDefinition(
				null, TestPropsValues.getUserId(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()));

		_listTypeEntry = _listTypeEntryLocalService.addListTypeEntry(
			null, TestPropsValues.getUserId(),
			_listTypeDefinition.getListTypeDefinitionId(), "able",
			Collections.singletonMap(LocaleUtil.US, "Able"));
	}

	@After
	public void tearDown() throws Exception {
		_listTypeDefinitionLocalService.deleteListTypeDefinition(
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

		Assert.assertEquals(
			_listTypeEntry.getUuid(),
			_listTypeEntry.getExternalReferenceCode());

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

		String externalReferenceCode =
			_listTypeEntry.getExternalReferenceCode();

		try {
			_listTypeEntryLocalService.addListTypeEntry(
				externalReferenceCode, TestPropsValues.getUserId(),
				_listTypeDefinition.getListTypeDefinitionId(),
				RandomTestUtil.randomString(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()));
		}
		catch (DuplicateListTypeEntryExternalReferenceCodeException
					duplicateListTypeEntryExternalReferenceCodeException) {

			Assert.assertEquals(
				"Duplicate external reference code " + externalReferenceCode,
				duplicateListTypeEntryExternalReferenceCodeException.
					getMessage());
		}

		ListTypeEntry listTypeEntry =
			_listTypeEntryLocalService.addListTypeEntry(
				"externalReferenceCode", TestPropsValues.getUserId(),
				_listTypeDefinition.getListTypeDefinitionId(), "baker",
				Collections.singletonMap(LocaleUtil.US, "Baker"));

		Assert.assertEquals(
			"externalReferenceCode", listTypeEntry.getExternalReferenceCode());
		Assert.assertEquals("baker", listTypeEntry.getKey());
		Assert.assertEquals(
			Collections.singletonMap(LocaleUtil.US, "Baker"),
			listTypeEntry.getNameMap());

		_listTypeEntryLocalService.deleteListTypeEntry(listTypeEntry);
	}

	@Test
	public void testFetchListTypeEntry() throws Exception {
		ListTypeEntry listTypeEntry =
			_listTypeEntryLocalService.fetchListTypeEntry(
				_listTypeEntry.getListTypeEntryId());

		Assert.assertNotNull(listTypeEntry);

		listTypeEntry = _listTypeEntryLocalService.fetchListTypeEntry(0);

		Assert.assertNull(listTypeEntry);

		listTypeEntry =
			_listTypeEntryLocalService.
				fetchListTypeEntryByExternalReferenceCode(
					_listTypeEntry.getExternalReferenceCode(),
					_listTypeEntry.getCompanyId());

		Assert.assertNotNull(listTypeEntry);

		listTypeEntry =
			_listTypeEntryLocalService.
				fetchListTypeEntryByExternalReferenceCode(
					null, _listTypeEntry.getCompanyId());

		Assert.assertNull(listTypeEntry);
	}

	@Test
	public void testGetListTypeEntry() throws Exception {
		try {
			_listTypeEntryLocalService.getListTypeEntry(0, "able");
		}
		catch (Exception exception) {
			Assert.assertEquals(
				"No ListTypeEntry exists with the key " +
					"{listTypeDefinitionId=0, key=able}",
				exception.getMessage());
		}

		Assert.assertNotNull(
			_listTypeEntryLocalService.getListTypeEntry(
				_listTypeDefinition.getListTypeDefinitionId(),
				_listTypeEntry.getKey()));

		try {
			_listTypeEntryLocalService.getListTypeEntryByExternalReferenceCode(
				"noERC", _listTypeEntry.getCompanyId());
		}
		catch (Exception exception) {
			Assert.assertEquals(
				StringBundler.concat(
					"No ListTypeEntry exists with the key ",
					"{externalReferenceCode=noERC, companyId=",
					String.valueOf(_listTypeEntry.getCompanyId()), "}"),
				exception.getMessage());
		}

		Assert.assertNotNull(
			_listTypeEntryLocalService.getListTypeEntryByExternalReferenceCode(
				_listTypeEntry.getExternalReferenceCode(),
				_listTypeEntry.getCompanyId()));
	}

	@Test
	public void testUpdateListTypeEntry() throws Exception {
		String externalReferenceCode = "externalReferenceCode";

		Map<Locale, String> nameMap = Collections.singletonMap(
			LocaleUtil.US, "Updated Able");

		ListTypeEntry listTypeEntry =
			_listTypeEntryLocalService.updateListTypeEntry(
				externalReferenceCode, _listTypeEntry.getListTypeEntryId(),
				nameMap);

		Assert.assertEquals(
			externalReferenceCode, listTypeEntry.getExternalReferenceCode());

		Assert.assertEquals(nameMap, listTypeEntry.getNameMap());
	}

	private void _testAddListTypeEntry(long listTypeDefinitionId, String key)
		throws Exception {

		ListTypeEntry listTypeEntry = null;

		try {
			listTypeEntry = _listTypeEntryLocalService.addListTypeEntry(
				null, TestPropsValues.getUserId(), listTypeDefinitionId, key,
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()));
		}
		finally {
			if (listTypeEntry != null) {
				_listTypeEntryLocalService.deleteListTypeEntry(listTypeEntry);
			}
		}
	}

	@DeleteAfterTestRun
	private ListTypeDefinition _listTypeDefinition;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@DeleteAfterTestRun
	private ListTypeEntry _listTypeEntry;

	@Inject
	private ListTypeEntryLocalService _listTypeEntryLocalService;

}