/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.list.type.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.list.type.exception.DuplicateListTypeEntryException;
import com.liferay.list.type.exception.DuplicateListTypeEntryExternalReferenceCodeException;
import com.liferay.list.type.exception.ListTypeDefinitionSystemException;
import com.liferay.list.type.exception.ListTypeEntryKeyException;
import com.liferay.list.type.exception.NoSuchListTypeDefinitionException;
import com.liferay.list.type.exception.NoSuchListTypeEntryException;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SystemProperties;
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
					LocaleUtil.US, RandomTestUtil.randomString()),
				false, Collections.emptyList());

		_listTypeEntry = _listTypeEntryLocalService.addListTypeEntry(
			null, TestPropsValues.getUserId(),
			_listTypeDefinition.getListTypeDefinitionId(), "able",
			Collections.singletonMap(LocaleUtil.US, "Able"));

		_systemListTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				null, TestPropsValues.getUserId(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				true, Collections.emptyList());

		_systemListTypeEntry = _listTypeEntryLocalService.addListTypeEntry(
			null, TestPropsValues.getUserId(),
			_systemListTypeDefinition.getListTypeDefinitionId(), "able",
			Collections.singletonMap(LocaleUtil.US, "Able"));
	}

	@After
	public void tearDown() throws Exception {
		_listTypeDefinitionLocalService.deleteListTypeDefinition(
			_listTypeDefinition);
	}

	@Test
	public void testAddListTypeEntry() throws Exception {
		String externalReferenceCode =
			_listTypeEntry.getExternalReferenceCode();

		Assert.assertEquals(_listTypeEntry.getUuid(), externalReferenceCode);

		AssertUtils.assertFailure(
			DuplicateListTypeEntryException.class, "Duplicate key able",
			() -> _testAddListTypeEntry(
				_listTypeDefinition.getListTypeDefinitionId(), "able"));
		AssertUtils.assertFailure(
			DuplicateListTypeEntryExternalReferenceCodeException.class,
			"Duplicate external reference code " + externalReferenceCode,
			() -> _listTypeEntryLocalService.addListTypeEntry(
				externalReferenceCode, TestPropsValues.getUserId(),
				_listTypeDefinition.getListTypeDefinitionId(),
				RandomTestUtil.randomString(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString())));

		AssertUtils.assertFailure(
			ListTypeDefinitionSystemException.class, false,
			"Only allowed bundles can add system list type entries",
			() -> _testAddListTypeEntry(
				_systemListTypeDefinition.getListTypeDefinitionId(), "baker"));
		AssertUtils.assertFailure(
			ListTypeEntryKeyException.class, "Key is null",
			() -> _testAddListTypeEntry(
				_listTypeDefinition.getListTypeDefinitionId(), null));
		AssertUtils.assertFailure(
			ListTypeEntryKeyException.class,
			"Key must only contain letters and digits",
			() -> _testAddListTypeEntry(
				_listTypeDefinition.getListTypeDefinitionId(), " able "));
		AssertUtils.assertFailure(
			NoSuchListTypeDefinitionException.class,
			"No ListTypeDefinition exists with the primary key 0",
			() -> _testAddListTypeEntry(0, "able"));

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
	public void testDeleteListTypeEntry() throws Exception {
		AssertUtils.assertFailure(
			ListTypeDefinitionSystemException.class, false,
			"Only allowed bundles can delete system list type entries",
			() -> _listTypeEntryLocalService.deleteListTypeEntry(
				_systemListTypeEntry.getListTypeEntryId()));
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
					_listTypeEntry.getCompanyId(),
					_listTypeDefinition.getListTypeDefinitionId());

		Assert.assertNotNull(listTypeEntry);

		listTypeEntry =
			_listTypeEntryLocalService.
				fetchListTypeEntryByExternalReferenceCode(
					null, _listTypeEntry.getCompanyId(),
					_listTypeDefinition.getListTypeDefinitionId());

		Assert.assertNull(listTypeEntry);
	}

	@Test
	public void testGetListTypeEntry() throws Exception {
		try {
			_listTypeEntryLocalService.getListTypeEntry(0, "able");
		}
		catch (NoSuchListTypeEntryException noSuchListTypeEntryException) {
			Assert.assertNotNull(noSuchListTypeEntryException);
		}

		Assert.assertNotNull(
			_listTypeEntryLocalService.getListTypeEntry(
				_listTypeDefinition.getListTypeDefinitionId(),
				_listTypeEntry.getKey()));

		try {
			_listTypeEntryLocalService.getListTypeEntryByExternalReferenceCode(
				RandomTestUtil.randomString(), _listTypeEntry.getCompanyId(),
				_listTypeDefinition.getListTypeDefinitionId());
		}
		catch (NoSuchListTypeEntryException noSuchListTypeEntryException) {
			Assert.assertNotNull(noSuchListTypeEntryException);
		}

		Assert.assertNotNull(
			_listTypeEntryLocalService.getListTypeEntryByExternalReferenceCode(
				_listTypeEntry.getExternalReferenceCode(),
				_listTypeEntry.getCompanyId(),
				_listTypeDefinition.getListTypeDefinitionId()));
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

		externalReferenceCode = "externalReferenceCode2";

		String liferayMode = SystemProperties.get("liferay.mode");

		SystemProperties.clear("liferay.mode");

		try {
			_systemListTypeEntry =
				_listTypeEntryLocalService.updateListTypeEntry(
					externalReferenceCode,
					_systemListTypeEntry.getListTypeEntryId(), nameMap);
		}
		finally {
			SystemProperties.set("liferay.mode", liferayMode);
		}

		Assert.assertNotEquals(
			externalReferenceCode,
			_systemListTypeEntry.getExternalReferenceCode());

		Assert.assertEquals(nameMap, _systemListTypeEntry.getNameMap());
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

	private ListTypeDefinition _listTypeDefinition;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	private ListTypeEntry _listTypeEntry;

	@Inject
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	private ListTypeDefinition _systemListTypeDefinition;
	private ListTypeEntry _systemListTypeEntry;

}