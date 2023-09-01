/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.util;

import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.document.library.internal.configuration.helper.DLSizeLimitConfigurationHelper;
import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upload.configuration.UploadServletRequestConfigurationProvider;
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

		dlValidatorImpl.setDLConfiguration(_dlConfiguration);

		dlValidatorImpl.setGroupLocalService(_groupLocalService);

		dlValidatorImpl.setUploadServletRequestConfigurationHelper(
			_uploadServletRequestConfigurationProvider);

		_dlValidator = dlValidatorImpl;

		ReflectionTestUtil.setFieldValue(
			dlValidatorImpl, "_dlSizeLimitConfigurationHelper",
			_dlSizeLimitConfigurationHelper);
	}

	@Test
	public void testCompanyMimeTypeSizeLimitTakesPrecedenceOverGroupMimeTypeSizeLimit()
		throws Exception {

		Mockito.when(
			_dlSizeLimitConfigurationHelper.getCompanyMimeTypeSizeLimit(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			10L
		);

		Mockito.when(
			_dlSizeLimitConfigurationHelper.getGroupMimeTypeSizeLimit(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			15L
		);

		Assert.assertEquals(
			10,
			_dlValidator.getMaxAllowableSize(
				RandomTestUtil.randomInt(), "image/png"));
	}

	@Test(expected = FileExtensionException.class)
	public void testInvalidExtension() throws Exception {
		_validateFileExtension("test.gıf");
	}

	@Test
	public void testMaxAllowableSizeDLFileMaxSizeTakesPrecedenceOverMimeTypeSizeLimit()
		throws Exception {

		Mockito.when(
			_dlSizeLimitConfigurationHelper.getCompanyFileMaxSize(
				Mockito.anyLong())
		).thenReturn(
			10L
		);

		Mockito.when(
			_dlSizeLimitConfigurationHelper.getCompanyMimeTypeSizeLimit(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			15L
		);

		Assert.assertEquals(
			10,
			_dlValidator.getMaxAllowableSize(
				RandomTestUtil.randomInt(), "image/png"));
	}

	@Test
	public void testMaxAllowableSizeMimeTypeSizeLimit() throws Exception {
		Mockito.when(
			_uploadServletRequestConfigurationProvider.getMaxSize()
		).thenReturn(
			15L
		);

		Mockito.when(
			_dlSizeLimitConfigurationHelper.getCompanyFileMaxSize(
				Mockito.anyLong())
		).thenReturn(
			10L
		);

		Mockito.when(
			_dlSizeLimitConfigurationHelper.getCompanyMimeTypeSizeLimit(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			5L
		);

		Assert.assertEquals(
			5,
			_dlValidator.getMaxAllowableSize(
				RandomTestUtil.randomInt(), "image/png"));
	}

	@Test
	public void testMaxAllowableSizeUploadServletRequestFileMaxSizeTakesPrecedenceOverDLFileMaxSize()
		throws Exception {

		Mockito.when(
			_uploadServletRequestConfigurationProvider.getMaxSize()
		).thenReturn(
			10L
		);

		Mockito.when(
			_dlSizeLimitConfigurationHelper.getCompanyFileMaxSize(
				Mockito.anyLong())
		).thenReturn(
			15L
		);

		Assert.assertEquals(
			10,
			_dlValidator.getMaxAllowableSize(
				RandomTestUtil.randomInt(), RandomTestUtil.randomString()));
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

	private final DLConfiguration _dlConfiguration = Mockito.mock(
		DLConfiguration.class);
	private final DLSizeLimitConfigurationHelper
		_dlSizeLimitConfigurationHelper = Mockito.mock(
			DLSizeLimitConfigurationHelper.class);
	private DLValidator _dlValidator;
	private final GroupLocalService _groupLocalService = Mockito.mock(
		GroupLocalService.class);
	private final UploadServletRequestConfigurationProvider
		_uploadServletRequestConfigurationProvider = Mockito.mock(
			UploadServletRequestConfigurationProvider.class);

}