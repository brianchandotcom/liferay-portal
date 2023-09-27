/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.kernel.DDMForm;
import com.liferay.dynamic.data.mapping.kernel.DDMFormField;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManager;
import com.liferay.dynamic.data.mapping.storage.DDMStorageEngineManager;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Leonardo Barros
 */
@RunWith(Arquillian.class)
public class DDMStructureManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_classNameId = _portal.getClassNameId(
			"com.liferay.dynamic.data.mapping.model.DDMFormInstance");
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());
	}

	@Test
	public void testAddStructure() throws Exception {
		DDMStructure structure = addStructure();

		Assert.assertNotNull(structure);
	}

	@Test
	public void testDeleteStructure() throws Exception {
		DDMStructure structure = addStructure();

		Assert.assertNotNull(structure);

		_ddmStructureManager.deleteStructure(structure.getStructureId());

		Assert.assertNull(
			_ddmStructureManager.fetchStructure(
				structure.getGroupId(), structure.getClassNameId(),
				structure.getStructureKey()));
	}

	@Test
	public void testFetchStructure() throws Exception {
		DDMStructure expectedStructure = addStructure();

		DDMStructure actualStructure = _ddmStructureManager.fetchStructure(
			_group.getGroupId(), expectedStructure.getClassNameId(),
			expectedStructure.getStructureKey());

		Assert.assertNotNull(actualStructure);

		assertEquals(expectedStructure, actualStructure);
	}

	@Test
	public void testGetClassStructures() throws Exception {
		List<DDMStructure> structures = _ddmStructureManager.getClassStructures(
			_group.getCompanyId(), _classNameId);

		int initialSize = structures.size();

		addStructure();

		structures = _ddmStructureManager.getClassStructures(
			_group.getCompanyId(), _classNameId);

		Assert.assertEquals(
			structures.toString(), initialSize + 1, structures.size());
	}

	@Test
	public void testGetClassStructuresWithCompanyAndClassNameId()
		throws Exception {

		List<DDMStructure> structures = _ddmStructureManager.getClassStructures(
			_group.getCompanyId(), _classNameId);

		int initialSize = structures.size();

		addStructure();

		structures = _ddmStructureManager.getClassStructures(
			_group.getCompanyId(), _classNameId);

		Assert.assertEquals(
			structures.toString(), initialSize + 1, structures.size());
	}

	@Test
	public void testUpdateStructure() throws Exception {
		DDMStructure expectedStructure = addStructure();

		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.US, "Structure Name Modified"
		).build();

		Map<Locale, String> descriptionMap = HashMapBuilder.put(
			LocaleUtil.US, "Structure Description Modified"
		).build();

		DDMStructure actualStructure = _ddmStructureManager.updateStructure(
			TestPropsValues.getUserId(), expectedStructure.getStructureId(), 0,
			nameMap, descriptionMap, expectedStructure.getDDMForm(),
			_serviceContext);

		Assert.assertEquals(nameMap, actualStructure.getNameMap());
		Assert.assertEquals(
			descriptionMap, actualStructure.getDescriptionMap());
	}

	@Test
	public void testUpdateStructureKey() throws Exception {
		DDMStructure expectedStructure = addStructure();

		_ddmStructureManager.updateStructureKey(
			expectedStructure.getStructureId(), "NEW_KEY");

		DDMStructure structure = _ddmStructureManager.fetchStructure(
			expectedStructure.getStructureId());

		Assert.assertEquals("NEW_KEY", structure.getStructureKey());
	}

	protected DDMStructure addStructure() throws Exception {
		return _ddmStructureManager.addStructure(
			TestPropsValues.getUserId(), _group.getGroupId(), null,
			_classNameId, StringUtil.randomString(),
			HashMapBuilder.put(
				LocaleUtil.US, "Test Structure Name"
			).build(),
			HashMapBuilder.put(
				LocaleUtil.US, "Test Structure Description"
			).build(),
			createDDMForm(), DDMStorageEngineManager.STORAGE_TYPE_DEFAULT,
			DDMStructureManager.STRUCTURE_TYPE_AUTO, _serviceContext);
	}

	protected void assertEquals(
		DDMStructure expectedStructure, DDMStructure actualStructure) {

		Assert.assertEquals(
			expectedStructure.getStructureId(),
			actualStructure.getStructureId());
		Assert.assertEquals(
			expectedStructure.getGroupId(), actualStructure.getGroupId());
		Assert.assertEquals(
			expectedStructure.getCompanyId(), actualStructure.getCompanyId());
		Assert.assertEquals(
			expectedStructure.getClassNameId(),
			actualStructure.getClassNameId());
		Assert.assertEquals(
			expectedStructure.getStructureKey(),
			actualStructure.getStructureKey());
		Assert.assertEquals(
			expectedStructure.getNameMap(), actualStructure.getNameMap());
		Assert.assertEquals(
			expectedStructure.getDescriptionMap(),
			actualStructure.getDescriptionMap());
	}

	protected DDMForm createDDMForm() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setDefaultLocale(LocaleUtil.US);
		ddmForm.addAvailableLocale(LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField("fieldName", "text");

		ddmForm.addDDMFormField(ddmFormField);

		return ddmForm;
	}

	private static long _classNameId;

	@Inject
	private static DDMStructureManager _ddmStructureManager;

	@Inject
	private static Portal _portal;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

}