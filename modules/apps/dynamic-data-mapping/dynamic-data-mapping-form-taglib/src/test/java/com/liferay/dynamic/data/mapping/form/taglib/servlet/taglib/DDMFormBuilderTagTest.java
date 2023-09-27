/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.taglib.servlet.taglib;

import com.liferay.dynamic.data.mapping.internal.io.DDMFormJSONDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureVersionImpl;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Lily Chi
 */
public class DDMFormBuilderTagTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		Mockito.when(
			FrameworkUtil.getBundle(Mockito.any())
		).thenReturn(
			bundleContext.getBundle()
		);

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("ddm.form.deserializer.type", "json");

		_ddmFormDeserializerServiceRegistration = bundleContext.registerService(
			DDMFormDeserializer.class, _ddmFormDeserializer, properties);
	}

	@AfterClass
	public static void tearDownClass() {
		_ddmFormDeserializerServiceRegistration.unregister();

		_ddmStructureLocalServiceUtilMockedStatic.close();

		_ddmStructureVersionLocalServiceUtilMockedStatic.close();

		_frameworkUtilMockedStatic.close();
	}

	@Before
	public void setUp() throws Exception {
		_setUpDDMStructureLocalService();
		_setUpDDMStructureVersionLocalService();
	}

	@Test
	public void testGetDDMFormFromDDMStructure() {
		DDMForm ddmForm = _ddmStructure.getDDMForm();

		Assert.assertTrue(
			ddmForm.equals(
				_ddmFormBuilderTag.getDDMForm(
					_ddmStructure.getStructureId(), 0)));
	}

	@Test
	public void testGetDDMFormFromDDMStructureVersion1() {
		DDMForm ddmForm = _ddmStructureVersion.getDDMForm();

		Assert.assertTrue(
			ddmForm.equals(
				_ddmFormBuilderTag.getDDMForm(
					0, _ddmStructureVersion.getStructureId())));
	}

	@Test
	public void testGetDDMFormFromDDMStructureVersion2() {
		DDMForm ddmForm = _ddmStructureVersion.getDDMForm();

		Assert.assertTrue(
			ddmForm.equals(
				_ddmFormBuilderTag.getDDMForm(
					_ddmStructure.getStructureId(),
					_ddmStructureVersion.getStructureId())));
	}

	@Test
	public void testGetEmptyDDMFormTest() {
		Assert.assertEquals(new DDMForm(), _ddmFormBuilderTag.getDDMForm(0, 0));
	}

	private DDMStructure _createDDMStructure(DDMForm ddmForm) {
		DDMStructure ddmStructure = new DDMStructureImpl();

		ddmStructure.setDDMForm(ddmForm);
		ddmStructure.setStructureId(RandomTestUtil.randomLong());
		ddmStructure.setName(RandomTestUtil.randomString());

		return ddmStructure;
	}

	private DDMStructureVersion _createDDMStructureVersion(DDMForm ddmForm) {
		DDMStructureVersion ddmStructureVersion = new DDMStructureVersionImpl();

		ddmStructureVersion.setDDMForm(ddmForm);
		ddmStructureVersion.setStructureId(RandomTestUtil.randomLong());
		ddmStructureVersion.setName(RandomTestUtil.randomString());

		return ddmStructureVersion;
	}

	private void _setUpDDMStructureLocalService() throws Exception {
		_ddmStructure = _createDDMStructure(
			DDMFormTestUtil.createDDMForm("Text"));

		Mockito.when(
			DDMStructureLocalServiceUtil.getService()
		).thenReturn(
			Mockito.mock(DDMStructureLocalService.class)
		);

		Mockito.when(
			DDMStructureLocalServiceUtil.fetchDDMStructure(Mockito.anyLong())
		).thenReturn(
			_ddmStructure
		);
	}

	private void _setUpDDMStructureVersionLocalService() throws Exception {
		_ddmStructureVersion = _createDDMStructureVersion(
			DDMFormTestUtil.createDDMForm("Text1", "Text2"));

		Mockito.when(
			DDMStructureVersionLocalServiceUtil.getService()
		).thenReturn(
			Mockito.mock(DDMStructureVersionLocalService.class)
		);

		Mockito.when(
			DDMStructureVersionLocalServiceUtil.fetchDDMStructureVersion(
				Mockito.anyLong())
		).thenReturn(
			_ddmStructureVersion
		);
	}

	private static final DDMFormDeserializer _ddmFormDeserializer =
		new DDMFormJSONDeserializer();
	private static ServiceRegistration<DDMFormDeserializer>
		_ddmFormDeserializerServiceRegistration;
	private static final MockedStatic<DDMStructureLocalServiceUtil>
		_ddmStructureLocalServiceUtilMockedStatic = Mockito.mockStatic(
			DDMStructureLocalServiceUtil.class);
	private static final MockedStatic<DDMStructureVersionLocalServiceUtil>
		_ddmStructureVersionLocalServiceUtilMockedStatic = Mockito.mockStatic(
			DDMStructureVersionLocalServiceUtil.class);
	private static final MockedStatic<FrameworkUtil>
		_frameworkUtilMockedStatic = Mockito.mockStatic(FrameworkUtil.class);

	private final DDMFormBuilderTag _ddmFormBuilderTag =
		new DDMFormBuilderTag();
	private DDMStructure _ddmStructure;
	private DDMStructureVersion _ddmStructureVersion;

}