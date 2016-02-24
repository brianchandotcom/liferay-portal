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

package com.liferay.dynamic.data.lists.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.lists.helper.DDLRecordSetTestHelper;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.StorageAdapter;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.dynamic.data.mapping.test.util.storage.FailStorageAdapter;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.Locale;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class DDLRecordSetServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceRegistration = registry.registerService(
			StorageAdapter.class, new FailStorageAdapter());
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceRegistration.unregister();
	}

	@Before
	public void setUp() throws Exception {
		_availableLocales = DDMFormTestUtil.createAvailableLocales(Locale.US);
		_defaultLocale = Locale.US;

		_group = GroupTestUtil.addGroup();

		_ddmStructureTestHelper = new DDMStructureTestHelper(
			PortalUtil.getClassNameId(DDLRecordSet.class), _group);

		_ddlRecordSetTestHelper = new DDLRecordSetTestHelper(_group);
	}

	@Test
	public void testAddRecordSetWithFailStorage() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			_availableLocales, _defaultLocale);

		DDLRecordSet ddlRecordSet = addRecordSet(
			ddmForm, FailStorageAdapter.STORAGE_TYPE);

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		Assert.assertEquals(
			ddmStructure.getStorageType(), FailStorageAdapter.STORAGE_TYPE);
	}

	@Test
	public void testUpdateRecordSetWithFailStorage() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			_availableLocales, _defaultLocale);

		DDLRecordSet ddlRecordSet = addRecordSet(
			ddmForm, FailStorageAdapter.STORAGE_TYPE);

		String storageAdpater = ddlRecordSet.getDDMStructure().getStorageType();

		DDMFormTestUtil.addTextDDMFormFields(ddmForm, "Name");

		ddlRecordSet = updateRecordSet(ddlRecordSet, ddmForm);

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		Assert.assertEquals(storageAdpater, ddmStructure.getStorageType());
	}

	protected DDLRecordSet addRecordSet(DDMForm ddmForm, String storageType)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureTestHelper.addStructure(
			ddmForm, storageType);

		return _ddlRecordSetTestHelper.addRecordSet(ddmStructure);
	}

	protected DDLRecordSet updateRecordSet(
			DDLRecordSet ddlRecordSet, DDMForm ddmForm)
		throws Exception {

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		ddmStructure = _ddmStructureTestHelper.updateStructure(
			ddmStructure.getStructureId(), ddmForm);

		return _ddlRecordSetTestHelper.updateRecordSet(
			ddlRecordSet.getRecordSetId(), ddmStructure);
	}

	private static ServiceRegistration<StorageAdapter> _serviceRegistration;

	private Set<Locale> _availableLocales;
	private DDLRecordSetTestHelper _ddlRecordSetTestHelper;
	private DDMStructureTestHelper _ddmStructureTestHelper;
	private Locale _defaultLocale;

	@DeleteAfterTestRun
	private Group _group;

}