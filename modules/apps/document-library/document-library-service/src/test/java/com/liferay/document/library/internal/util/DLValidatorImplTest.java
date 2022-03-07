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

package com.liferay.document.library.internal.util;

import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.document.library.internal.configuration.admin.service.DLSizeLimitManagedServiceFactory;
import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelper;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class DLValidatorImplTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		DLValidatorImpl dlValidatorImpl = new DLValidatorImpl();

		_dlConfiguration = Mockito.mock(DLConfiguration.class);

		dlValidatorImpl.setDLConfiguration(_dlConfiguration);

		_dlSizeLimitManagedServiceFactory = Mockito.mock(
			DLSizeLimitManagedServiceFactory.class);

		dlValidatorImpl.setDLSizeLimitManagedServiceFactory(
			_dlSizeLimitManagedServiceFactory);

		_uploadServletRequestConfigurationHelper = Mockito.mock(
			UploadServletRequestConfigurationHelper.class);

		dlValidatorImpl.setUploadServletRequestConfigurationHelper(
			_uploadServletRequestConfigurationHelper);

		_dlValidator = dlValidatorImpl;
	}

	@Test(expected = FileExtensionException.class)
	public void testInvalidExtension() throws Exception {
		_validateFileExtension("test.gıf");
	}

	@Test
	public void testMaxAllowableSizeDLFileMaxSizeTakesPrecedenceOverMimeTypeSizeLimit() {
		Mockito.when(
			_dlConfiguration.fileMaxSize()
		).thenReturn(
			10L
		);

		Mockito.when(
			_dlSizeLimitManagedServiceFactory.getCompanyMimeTypeSizeLimit(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			15L
		);

		Assert.assertEquals(10, _dlValidator.getMaxAllowableSize("image/png"));
	}

	@Test
	public void testMaxAllowableSizeMimeTypeSizeLimit() {
		Mockito.when(
			_uploadServletRequestConfigurationHelper.getMaxSize()
		).thenReturn(
			15L
		);

		Mockito.when(
			_dlConfiguration.fileMaxSize()
		).thenReturn(
			10L
		);

		Mockito.when(
			_dlSizeLimitManagedServiceFactory.getCompanyMimeTypeSizeLimit(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			5L
		);

		Assert.assertEquals(5, _dlValidator.getMaxAllowableSize("image/png"));
	}

	@Test
	public void testMaxAllowableSizeUploadServletRequestFileMaxSizeTakesPrecedenceOverDLFileMaxSize() {
		Mockito.when(
			_uploadServletRequestConfigurationHelper.getMaxSize()
		).thenReturn(
			10L
		);

		Mockito.when(
			_dlConfiguration.fileMaxSize()
		).thenReturn(
			15L
		);

		Assert.assertEquals(
			10,
			_dlValidator.getMaxAllowableSize(RandomTestUtil.randomString()));
	}

	@Test
	public void testValidLowerCaseExtension() throws Exception {
		_validateFileExtension("test.gif");
	}

	@Test
	public void testValidMixedCaseExtension() throws Exception {
		_validateFileExtension("test.GiF");
	}

	@Test
	public void testValidUpperCaseExtension() throws Exception {
		_validateFileExtension("test.GIF");
	}

	private void _validateFileExtension(String fileName) throws Exception {
		Mockito.when(
			_dlConfiguration.fileExtensions()
		).thenReturn(
			new String[] {".gif"}
		);

		_dlValidator.validateFileExtension(fileName);

		Mockito.when(
			_dlConfiguration.fileExtensions()
		).thenReturn(
			new String[] {"gif"}
		);

		_dlValidator.validateFileExtension(fileName);
	}

	private DLConfiguration _dlConfiguration;
	private DLSizeLimitManagedServiceFactory _dlSizeLimitManagedServiceFactory;
	private DLValidator _dlValidator;
	private UploadServletRequestConfigurationHelper
		_uploadServletRequestConfigurationHelper;

}