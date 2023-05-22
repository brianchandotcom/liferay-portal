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

package com.liferay.dynamic.data.mapping.form.web.internal.upload;

import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.exception.InvalidFileException;
import com.liferay.dynamic.data.mapping.form.web.internal.configuration.DDMFormWebConfiguration;
import com.liferay.dynamic.data.mapping.form.web.internal.configuration.activator.DDMFormWebConfigurationActivator;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Carolina Barbosa
 */
public class DDMFormUploadValidatorTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_frameworkUtilMockedStatic.when(
			() -> FrameworkUtil.getBundle(Mockito.any())
		).thenReturn(
			_bundleContext.getBundle()
		);

		_setUpDDMFormWebConfigurationActivator();
	}

	@AfterClass
	public static void tearDownClass() {
		_frameworkUtilMockedStatic.close();

		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Test(expected = InvalidFileException.class)
	public void testInvalidFileException() throws Exception {
		DDMFormUploadValidator.validateFileSize(null, "test.jpg");
	}

	@Test(expected = FileExtensionException.class)
	public void testInvalidFileExtension() throws Exception {
		DDMFormUploadValidator.validateFileExtension("test.xml");
	}

	@Test(expected = FileSizeException.class)
	public void testInvalidFileSize() throws Exception {
		DDMFormUploadValidator.validateFileSize(_mockFile(26), "test.jpg");
	}

	@Test
	public void testValidFileExtension() throws Exception {
		DDMFormUploadValidator.validateFileExtension("test.JpG");
	}

	@Test
	public void testValidFileSize() throws Exception {
		DDMFormUploadValidator.validateFileSize(_mockFile(24), "test.jpg");
	}

	private static void _setUpDDMFormWebConfigurationActivator() {
		DDMFormWebConfigurationActivator ddmFormWebConfigurationActivator =
			new DDMFormWebConfigurationActivator();

		DDMFormWebConfiguration ddmFormWebConfiguration =
			ConfigurableUtil.createConfigurable(
				DDMFormWebConfiguration.class, new HashMapDictionary<>());

		ReflectionTestUtil.setFieldValue(
			ddmFormWebConfigurationActivator, "_ddmFormWebConfiguration",
			ddmFormWebConfiguration);

		_serviceRegistration = _bundleContext.registerService(
			DDMFormWebConfigurationActivator.class,
			ddmFormWebConfigurationActivator, null);
	}

	private File _mockFile(long length) {
		File file = Mockito.mock(File.class);

		Mockito.when(
			file.length()
		).thenReturn(
			length * _FILE_LENGTH_MB
		);

		return file;
	}

	private static final long _FILE_LENGTH_MB = 1024 * 1024;

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private static final MockedStatic<FrameworkUtil>
		_frameworkUtilMockedStatic = Mockito.mockStatic(FrameworkUtil.class);
	private static ServiceRegistration<DDMFormWebConfigurationActivator>
		_serviceRegistration;

}