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
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.File;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;

/**
 * @author Carolina Barbosa
 */
public class DDMFormUploadValidatorTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_setUpDDMFormWebConfigurationActivator();
	}

	@Test(expected = InvalidFileException.class)
	public void testInvalidFileException() throws Exception {
		_ddmFormUploadValidator.validateFileSize(null, "test.jpg");
	}

	@Test(expected = FileExtensionException.class)
	public void testInvalidFileExtension() throws Exception {
		_ddmFormUploadValidator.validateFileExtension("test.xml");
	}

	@Test(expected = FileSizeException.class)
	public void testInvalidFileSize() throws Exception {
		_ddmFormUploadValidator.validateFileSize(_mockFile(26), "test.jpg");
	}

	@Test
	public void testValidFileExtension() throws Exception {
		_ddmFormUploadValidator.validateFileExtension("test.JpG");
	}

	@Test
	public void testValidFileSize() throws Exception {
		_ddmFormUploadValidator.validateFileSize(_mockFile(24), "test.jpg");
	}

	private File _mockFile(long length) {
		File file = Mockito.mock(File.class);

		PowerMockito.when(
			file.length()
		).thenReturn(
			length * _FILE_LENGTH_MB
		);

		return file;
	}

	private void _setUpDDMFormWebConfigurationActivator() throws Exception {
		DDMFormWebConfigurationActivator ddmFormWebConfigurationActivator =
			Mockito.mock(DDMFormWebConfigurationActivator.class);

		MemberMatcher.field(
			DDMFormUploadValidator.class, "_ddmFormWebConfigurationActivator"
		).set(
			_ddmFormUploadValidator, ddmFormWebConfigurationActivator
		);

		DDMFormWebConfiguration ddmFormWebConfiguration =
			ConfigurableUtil.createConfigurable(
				DDMFormWebConfiguration.class, new HashMapDictionary<>());

		PowerMockito.when(
			ddmFormWebConfigurationActivator.getDDMFormWebConfiguration()
		).thenReturn(
			ddmFormWebConfiguration
		);
	}

	private static final long _FILE_LENGTH_MB = 1024 * 1024;

	private final DDMFormUploadValidator _ddmFormUploadValidator =
		new DDMFormUploadValidator();

}