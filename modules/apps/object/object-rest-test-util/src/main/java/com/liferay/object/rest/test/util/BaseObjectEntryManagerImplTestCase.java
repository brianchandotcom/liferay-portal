/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.test.util;

import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;

/**
 * @author Paulo Albuquerque
 */
public abstract class BaseObjectEntryManagerImplTestCase {

	@Before
	public void setUp() throws Exception {
		dtoConverterContext = new DefaultDTOConverterContext(
			false, Collections.emptyMap(), dtoConverterRegistry, null,
			LocaleUtil.getDefault(), null, adminUser);
	}

	protected void assertEquals(
			List<ObjectEntry> actualObjectEntries,
			List<ObjectEntry> expectedObjectEntries)
		throws Exception {

		Assert.assertEquals(
			actualObjectEntries.toString(), expectedObjectEntries.size(),
			actualObjectEntries.size());

		for (int i = 0; i < expectedObjectEntries.size(); i++) {
			assertEquals(
				actualObjectEntries.get(i), expectedObjectEntries.get(i));
		}
	}

	protected void assertEquals(
			ObjectEntry actualObjectEntry, ObjectEntry expectedObjectEntry)
		throws Exception {

		Map<String, Object> actualObjectEntryProperties =
			actualObjectEntry.getProperties();

		Map<String, Object> expectedObjectEntryProperties =
			expectedObjectEntry.getProperties();

		for (Map.Entry<String, Object> expectedEntry :
				expectedObjectEntryProperties.entrySet()) {

			assertObjectEntryProperties(
				actualObjectEntry, actualObjectEntryProperties, expectedEntry);
		}
	}

	protected void assertObjectEntryProperties(
			ObjectEntry actualObjectEntry,
			Map<String, Object> actualObjectEntryProperties,
			Map.Entry<String, Object> expectedEntry)
		throws Exception {

		Assert.assertEquals(
			expectedEntry.getKey(), expectedEntry.getValue(),
			actualObjectEntryProperties.get(expectedEntry.getKey()));
	}

	protected String buildEqualsExpressionFilterString(
		String fieldName, Object value) {

		return StringBundler.concat(fieldName, " eq ", getValue(value));
	}

	protected Page<ObjectEntry> getObjectEntries(
			Map<String, String> context, Sort[] sorts)
		throws Exception {

		return null;
	}

	protected String getValue(Object value) {
		if (value instanceof String) {
			return StringUtil.quote(String.valueOf(value));
		}

		return String.valueOf(value);
	}

	protected void testGetObjectEntries(
			Map<String, String> context, ObjectEntry... expectedObjectEntries)
		throws Exception {

		Sort[] sorts = null;

		if (context.containsKey("sort")) {
			String[] sort = StringUtil.split(context.get("sort"), ":");

			sorts = new Sort[] {
				SortFactoryUtil.create(sort[0], Objects.equals(sort[1], "desc"))
			};
		}

		Page<ObjectEntry> page = getObjectEntries(context, sorts);

		assertEquals(
			(List<ObjectEntry>)page.getItems(),
			ListUtil.fromArray(expectedObjectEntries));
	}

	protected static User adminUser;
	protected static long companyId;
	protected static DTOConverterContext dtoConverterContext;

	@Inject
	protected static DTOConverterRegistry dtoConverterRegistry;

	protected ListTypeDefinition listTypeDefinition;

	@Inject
	protected ListTypeDefinitionLocalService listTypeDefinitionLocalService;

	@Inject
	protected ObjectDefinitionLocalService objectDefinitionLocalService;

	@Inject
	protected ObjectFieldLocalService objectFieldLocalService;

}