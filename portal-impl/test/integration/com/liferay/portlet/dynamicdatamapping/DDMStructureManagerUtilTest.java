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

package com.liferay.portlet.dynamicdatamapping;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortalUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class DDMStructureManagerUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());
	}

	@After
	public void tearDown() throws Exception {
		if (_structure != null) {
			DDMStructureManagerUtil.deleteStructure(
				_structure.getStructureId());
		}

		_structure = null;
	}

	@Test
	public void testAddStructure() {
		try {
			addStructure();
		}
		catch (PortalException e) {
			Assert.fail("Structure not inserted");
		}
	}

	@Test
	public void testDeleteStructure() {
		try {
			DDMStructure structure = addStructure();
			DDMStructureManagerUtil.deleteStructure(structure.getStructureId());
			_structure = null;
		}
		catch (PortalException e) {
			Assert.fail("Structure not removed");
		}
	}

	@Test
	public void testFetchStructure() {
		try {
			addStructure();

			DDMStructure structure = DDMStructureManagerUtil.fetchStructure(
				_group.getGroupId(),
				PortalUtil.getClassNameId(
			"com.liferay.portlet.dynamicdatamapping.model.DDMStructure"),
				"KEY");

			assertStructure(structure);
		}
		catch (PortalException e) {
			Assert.fail("Should not throw exception");
		}
	}

	@Test
	public void testFetchStructureByUuidAndGroupId() {
		try {
			DDMStructure structure = addStructure();

			structure = DDMStructureManagerUtil.fetchStructureByUuidAndGroupId(
				structure.getUuid(), _group.getGroupId());

			assertStructure(structure);
		}
		catch (PortalException e) {
			Assert.fail("Should not throw exception");
		}
	}

	@Test
	public void testGetClassStructures() {
		try {
			addStructure();

			List<DDMStructure> structures =
				DDMStructureManagerUtil.getClassStructures(
					_group.getCompanyId(),
					PortalUtil.getClassNameId(
				"com.liferay.portlet.dynamicdatamapping.model.DDMStructure"),
					0, 1);

			Assert.assertEquals(1, structures.size());
		}
		catch (PortalException e) {
			Assert.fail("Should not throw exception");
		}
	}

	@Test
	public void testGetStructure() {
		try {
			addStructure();

			DDMStructure structure = DDMStructureManagerUtil.getStructure(
				_group.getGroupId(),
				PortalUtil.getClassNameId(
			"com.liferay.portlet.dynamicdatamapping.model.DDMStructure"),
				"KEY");

			assertStructure(structure);
		}
		catch (PortalException e) {
			Assert.fail("Should not throw exception");
		}
	}

	@Test
	public void testGetStructureById() {
		try {
			DDMStructure structure = addStructure();

			structure = DDMStructureManagerUtil.getStructure(
				structure.getStructureId());

			assertStructure(structure);
		}
		catch (PortalException e) {
			Assert.fail("Should not throw exception");
		}
	}

	@Test
	public void testGetStructureByUuidAndGroupId() {
		try {
			DDMStructure structure = addStructure();

			structure = DDMStructureManagerUtil.getStructureByUuidAndGroupId(
				structure.getUuid(), _group.getGroupId());

			assertStructure(structure);
		}
		catch (PortalException e) {
			Assert.fail("Should not throw exception");
		}
	}

	@Test
	public void testUpdateStructure() {
		try {
			DDMStructure structure = addStructure();

			Map<Locale, String> nameMap = new HashMap<>();
			nameMap.put(LocaleUtil.US, "test");

			Map<Locale, String> descriptionMap = new HashMap<>();
			descriptionMap.put(LocaleUtil.US, "description");

			structure = DDMStructureManagerUtil.updateStructure(
				TestPropsValues.getUserId(), structure.getStructureId(), 0,
				nameMap, descriptionMap, structure.getDDMForm(),
				structure.getDDMFormLayout(), _serviceContext);

			assertStructure(structure);
		}
		catch (PortalException e) {
			Assert.fail("Should not throw exception");
		}
	}

	private DDMStructure addStructure() throws PortalException {
		Map<Locale, String> nameMap = new HashMap<>();
		nameMap.put(LocaleUtil.US, "test");

		Map<Locale, String> descriptionMap = new HashMap<>();
		descriptionMap.put(LocaleUtil.US, "description");

		_structure = DDMStructureManagerUtil.addStructure(
			TestPropsValues.getUserId(), _group.getGroupId(), null,
			PortalUtil.getClassNameId(
		"com.liferay.portlet.dynamicdatamapping.model.DDMStructure"),
			"KEY", nameMap, descriptionMap, createForm(), createFormLayout(),
			"json", 1, _serviceContext);

		return _structure;
	}

	private void assertStructure(DDMStructure structure)
		throws PortalException {

		Assert.assertNotNull(structure);
		Assert.assertEquals("KEY", structure.getStructureKey());

		Map<Locale, String> nameMap = structure.getNameMap();

		Assert.assertTrue(nameMap.containsKey(LocaleUtil.US));
		Assert.assertEquals("test", nameMap.get(LocaleUtil.US));

		Map<Locale, String> descriptionMap = structure.getDescriptionMap();
		Assert.assertTrue(descriptionMap.containsKey(LocaleUtil.US));
		Assert.assertEquals("description", descriptionMap.get(LocaleUtil.US));
	}

	private DDMForm createForm() {
		Set<Locale> availableLocales = new LinkedHashSet<>();
		availableLocales.add(LocaleUtil.US);

		DDMForm ddmForm = new DDMForm();
		ddmForm.setDefaultLocale(LocaleUtil.US);
		ddmForm.setAvailableLocales(availableLocales);

		DDMFormField ddmFormField = new DDMFormField();
		ddmFormField.setType("text");
		ddmFormField.setName("fieldName");

		List<DDMFormField> ddmFormFields = new ArrayList<>();
		ddmFormFields.add(ddmFormField);

		ddmForm.setDDMFormFields(ddmFormFields);

		return ddmForm;
	}

	private DDMFormLayout createFormLayout() {
		List<DDMFormLayoutPage> ddmFormLayoutPages = new ArrayList<>();
		ddmFormLayoutPages.add(createLayoutPage());

		DDMFormLayout ddmFormLayout = new DDMFormLayout();
		ddmFormLayout.setDefaultLocale(LocaleUtil.US);
		ddmFormLayout.setDDMFormLayoutPages(ddmFormLayoutPages);

		return ddmFormLayout;
	}

	private DDMFormLayoutColumn createFormLayoutColumn() {
		DDMFormLayoutColumn ddmFormLayoutColumn = new DDMFormLayoutColumn();

		List<String> names = new ArrayList<>();
		names.add("fieldName");

		ddmFormLayoutColumn.setDDMFormFieldNames(names);

		ddmFormLayoutColumn.setSize(1);

		return ddmFormLayoutColumn;
	}

	private DDMFormLayoutRow createFormLayoutRow() {
		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		List<DDMFormLayoutColumn> ddmFormLayoutColumns = new ArrayList<>();
		ddmFormLayoutColumns.add(createFormLayoutColumn());

		ddmFormLayoutRow.setDDMFormLayoutColumns(ddmFormLayoutColumns);
		return ddmFormLayoutRow;
	}

	private DDMFormLayoutPage createLayoutPage() {
		Map<Locale, String> titleValues = new HashMap<>();
		titleValues.put(LocaleUtil.US, "title");

		Map<Locale, String> descriptionValues = new HashMap<>();
		descriptionValues.put(LocaleUtil.US, "description");

		LocalizedValue title = new LocalizedValue();
		title.setDefaultLocale(LocaleUtil.US);
		title.setValues(titleValues);

		LocalizedValue description = new LocalizedValue();
		title.setDefaultLocale(LocaleUtil.US);
		title.setValues(descriptionValues);

		DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage();
		ddmFormLayoutPage.setTitle(title);
		ddmFormLayoutPage.setDescription(description);

		List<DDMFormLayoutRow> ddmFormLayoutRows = new ArrayList<>();
		ddmFormLayoutRows.add(createFormLayoutRow());

		ddmFormLayoutPage.setDDMFormLayoutRows(ddmFormLayoutRows);

		return ddmFormLayoutPage;
	}

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;
	private DDMStructure _structure = null;

}