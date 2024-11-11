/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.list.type.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.list.type.client.dto.v1_0.ListTypeEntry;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gabriel Albuquerque
 */
@RunWith(Arquillian.class)
public class ListTypeEntryResourceTest
	extends BaseListTypeEntryResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_listTypeDefinition =
			ListTypeDefinitionLocalServiceUtil.addListTypeDefinition(
				null, TestPropsValues.getUserId(),
				Collections.singletonMap(LocaleUtil.getDefault(), "test"),
				false, Collections.emptyList());
	}

	@Override
	@Test
	public void testGetListTypeDefinitionByExternalReferenceCodeListTypeEntriesPageWithSortInteger()
		throws Exception {

		testGetListTypeDefinitionByExternalReferenceCodeListTypeEntriesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, listTypeEntry1, listTypeEntry2) -> {
				if (BeanTestUtil.hasProperty(
						listTypeEntry1, entityField.getName())) {

					BeanTestUtil.setProperty(
						listTypeEntry1, entityField.getName(), 0);
				}

				if (BeanTestUtil.hasProperty(
						listTypeEntry2, entityField.getName())) {

					BeanTestUtil.setProperty(
						listTypeEntry2, entityField.getName(), 1);
				}
			});
	}

	@Override
	@Test
	public void testGetListTypeDefinitionListTypeEntriesPageWithSortInteger()
		throws Exception {

		testGetListTypeDefinitionListTypeEntriesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, listTypeEntry1, listTypeEntry2) -> {
				if (BeanTestUtil.hasProperty(
						listTypeEntry1, entityField.getName())) {

					BeanTestUtil.setProperty(
						listTypeEntry1, entityField.getName(), 0);
				}

				if (BeanTestUtil.hasProperty(
						listTypeEntry2, entityField.getName())) {

					BeanTestUtil.setProperty(
						listTypeEntry2, entityField.getName(), 1);
				}
			});
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetListTypeEntry() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetListTypeEntryNotFound() throws Exception {
	}

	@Override
	@Test
	public void testPostListTypeDefinitionListTypeEntry() throws Exception {
		super.testPostListTypeDefinitionListTypeEntry();

		ListTypeEntry listTypeEntry = randomListTypeEntry();

		listTypeEntry.setName(RandomTestUtil.randomString());
		listTypeEntry.setName_i18n((Map<String, String>)null);

		_assertListTypeEntryNameLocalizedMap(
			testPostListTypeDefinitionListTypeEntry_addListTypeEntry(
				listTypeEntry));
	}

	@Override
	@Test
	public void testPutListTypeEntry() throws Exception {
		super.testPutListTypeEntry();

		ListTypeEntry listTypeEntry = testPutListTypeEntry_addListTypeEntry();

		listTypeEntry.setName(RandomTestUtil.randomString());
		listTypeEntry.setName_i18n((Map<String, String>)null);

		_assertListTypeEntryNameLocalizedMap(
			listTypeEntryResource.putListTypeEntry(
				listTypeEntry.getId(), listTypeEntry));
	}

	@Override
	protected ListTypeEntry randomListTypeEntry() throws Exception {
		ListTypeEntry listTypeEntry = super.randomListTypeEntry();

		listTypeEntry.setName_i18n(
			Collections.singletonMap("en-US", RandomTestUtil.randomString()));

		return listTypeEntry;
	}

	@Override
	protected ListTypeEntry testDeleteListTypeEntry_addListTypeEntry()
		throws Exception {

		return _addListTypeEntry();
	}

	@Override
	protected ListTypeEntry
			testGetListTypeDefinitionByExternalReferenceCodeListTypeEntriesPage_addListTypeEntry(
				String externalReferenceCode, ListTypeEntry listTypeEntry)
		throws Exception {

		return listTypeEntryResource.
			postListTypeDefinitionByExternalReferenceCodeListTypeEntry(
				externalReferenceCode, listTypeEntry);
	}

	@Override
	protected String
			testGetListTypeDefinitionByExternalReferenceCodeListTypeEntriesPage_getExternalReferenceCode()
		throws Exception {

		return _listTypeDefinition.getExternalReferenceCode();
	}

	@Override
	protected Long
		testGetListTypeDefinitionListTypeEntriesPage_getListTypeDefinitionId() {

		return _listTypeDefinition.getListTypeDefinitionId();
	}

	@Override
	protected ListTypeEntry testGetListTypeEntry_addListTypeEntry()
		throws Exception {

		return _addListTypeEntry();
	}

	@Override
	protected ListTypeEntry testGraphQLListTypeEntry_addListTypeEntry()
		throws Exception {

		return _addListTypeEntry();
	}

	@Override
	protected ListTypeEntry
			testPostListTypeDefinitionByExternalReferenceCodeListTypeEntry_addListTypeEntry(
				ListTypeEntry listTypeEntry)
		throws Exception {

		return listTypeEntryResource.
			postListTypeDefinitionByExternalReferenceCodeListTypeEntry(
				_listTypeDefinition.getExternalReferenceCode(), listTypeEntry);
	}

	@Override
	protected ListTypeEntry testPutListTypeEntry_addListTypeEntry()
		throws Exception {

		return _addListTypeEntry();
	}

	private ListTypeEntry _addListTypeEntry() throws Exception {
		return listTypeEntryResource.postListTypeDefinitionListTypeEntry(
			_listTypeDefinition.getListTypeDefinitionId(),
			randomListTypeEntry());
	}

	private void _assertListTypeEntryNameLocalizedMap(
		ListTypeEntry listTypeEntry) {

		Map<Locale, String> nameLocalizedMap = LocalizedMapUtil.getLocalizedMap(
			listTypeEntry.getName_i18n());

		Assert.assertEquals(
			listTypeEntry.getName(),
			nameLocalizedMap.get(LocaleUtil.getSiteDefault()));
	}

	@DeleteAfterTestRun
	private ListTypeDefinition _listTypeDefinition;

}