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
import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.util.DLValidator;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class DLValidatorImplTest {

	@Before
	public void setUp() {
		DLValidatorImpl dlValidatorImpl = new DLValidatorImpl();

		DLConfiguration dlConfiguration = Mockito.mock(DLConfiguration.class);

		Mockito.when(
			dlConfiguration.fileExtensions()
		).thenReturn(
			new String[] {"gif"}
		);

		dlValidatorImpl.setDLConfiguration(dlConfiguration);

		_dlValidator = dlValidatorImpl;
	}

	@Test(expected = FileExtensionException.class)
	public void testInvalidExtension() throws Exception {
		_dlValidator.validateFileExtension("gıf");
	}

	@Test
	public void testValidLowerCaseExtension() throws Exception {
		_dlValidator.validateFileExtension("gif");
	}

	@Test
	public void testValidMixedCaseExtension() throws Exception {
		_dlValidator.validateFileExtension("GiF");
	}

	@Test
	public void testValidUpperCaseExtension() throws Exception {
		_dlValidator.validateFileExtension("GIF");
	}

	private DLValidator _dlValidator;

}