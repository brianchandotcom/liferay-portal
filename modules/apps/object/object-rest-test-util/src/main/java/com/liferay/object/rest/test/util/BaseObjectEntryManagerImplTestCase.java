/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.test.util;

import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Paulo Albuquerque
 */
public abstract class BaseObjectEntryManagerImplTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			BaseObjectEntryManagerImplTestCase.class);

		bundleContext = bundle.getBundleContext();

		_testObjectEntryModelListener = new TestObjectEntryModelListener();

		_serviceRegistration = bundleContext.registerService(
			ModelListener.class, _testObjectEntryModelListener, null);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_serviceRegistration.unregister();
	}

	@Before
	public void setUp() throws Exception {
		dtoConverterContext = new DefaultDTOConverterContext(
			false, Collections.emptyMap(), dtoConverterRegistry, null,
			LocaleUtil.getDefault(), null, adminUser);
	}

	@After
	public void tearDown() throws Exception {
		for (com.liferay.object.model.ObjectEntry objectEntry :
				_testObjectEntryModelListener.getObjectEntries()) {

			try {
				objectEntryLocalService.deleteObjectEntry(
					objectEntry.getObjectEntryId());
			}
			catch (Exception exception) {
			}
		}

		_testObjectEntryModelListener.clear();
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

	protected String buildRangeExpression(
		Date date1, Date date2, String fieldName, String pattern) {

		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			pattern);

		return StringBundler.concat(
			"(( ", fieldName, " ge ", dateFormat.format(date1), ") and ( ",
			fieldName, " le ", dateFormat.format(date2), "))");
	}

	protected Page<ObjectEntry> getObjectEntries(
			Map<String, String> context, Sort[] sorts)
		throws Exception {

		return null;
	}

	protected String getValue(Object value) {
		if (value instanceof Date) {
			DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				"yyyy-MM-dd");

			return dateFormat.format(value);
		}
		else if (value instanceof String) {
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
		else {
			sorts = new Sort[] {SortFactoryUtil.create("createDate", false)};
		}

		Page<ObjectEntry> page = getObjectEntries(context, sorts);

		assertEquals(
			(List<ObjectEntry>)page.getItems(),
			ListUtil.fromArray(expectedObjectEntries));
	}

	protected static User adminUser;
	protected static BundleContext bundleContext;
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
	protected ObjectEntryLocalService objectEntryLocalService;

	@Inject
	protected ObjectFieldLocalService objectFieldLocalService;

	private static ServiceRegistration<?> _serviceRegistration;
	private static TestObjectEntryModelListener _testObjectEntryModelListener;

	private static class TestObjectEntryModelListener
		extends BaseModelListener<com.liferay.object.model.ObjectEntry> {

		public void clear() {
			_objectEntries.clear();
		}

		public List<com.liferay.object.model.ObjectEntry> getObjectEntries() {
			return _objectEntries;
		}

		@Override
		public void onAfterCreate(
				com.liferay.object.model.ObjectEntry objectEntry)
			throws ModelListenerException {

			_objectEntries.add(objectEntry);
		}

		private List<com.liferay.object.model.ObjectEntry> _objectEntries =
			new ArrayList<>();

	}

}