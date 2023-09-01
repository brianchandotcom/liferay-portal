/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.upload;

import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.exception.InvalidFileException;
import com.liferay.dynamic.data.mapping.form.web.internal.configuration.DDMFormWebConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
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
	public static void setUpClass() throws Exception {
		_frameworkUtilMockedStatic.when(
			() -> FrameworkUtil.getBundle(Mockito.any())
		).thenReturn(
			_bundleContext.getBundle()
		);

		_setUpConfigurationProvider();
	}

	@AfterClass
	public static void tearDownClass() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}

		_frameworkUtilMockedStatic.close();
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

	private static void _setUpConfigurationProvider() throws Exception {
		Mockito.doReturn(
			ConfigurableUtil.createConfigurable(
				DDMFormWebConfiguration.class, new HashMapDictionary<>())
		).when(
			_configurationProvider
		).getCompanyConfiguration(
			Mockito.any(Class.class), Mockito.anyLong()
		);

		_serviceRegistration = _bundleContext.registerService(
			ConfigurationProvider.class, _configurationProvider, null);
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
	private static final ConfigurationProvider _configurationProvider =
		Mockito.mock(ConfigurationProvider.class);
	private static final MockedStatic<FrameworkUtil>
		_frameworkUtilMockedStatic = Mockito.mockStatic(FrameworkUtil.class);
	private static ServiceRegistration<ConfigurationProvider>
		_serviceRegistration;

}