/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.validator;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.image.service.AMImageEntryLocalService;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class AMImageValidatorImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		Mockito.when(
			_adaptiveMedia.getValue(
				AMAttribute.getConfigurationUuidAMAttribute())
		).thenReturn(
			RandomTestUtil.randomString()
		);

		_amImageValidatorImpl.setAMImageEntryLocalService(
			_amImageEntryLocalService);
	}

	@Test
	public void testIsProcessingRequiredWhenContentPresent() {
		Mockito.when(
			_amImageEntryLocalService.hasAMImageEntryContent(
				Mockito.anyString(), Mockito.any(FileVersion.class))
		).thenReturn(
			true
		);

		Mockito.doReturn(
			true
		).when(
			_amImageValidatorImpl
		).isValid(
			Mockito.any(FileVersion.class)
		);

		Assert.assertFalse(
			_amImageValidatorImpl.isProcessingRequired(
				_adaptiveMedia, Mockito.mock(FileVersion.class)));

		_verifyAMImageEntryLocalServiceMock();
	}

	@Test
	public void testIsProcessingRequiredWhenFileVersionNotValid() {
		Mockito.when(
			_amImageEntryLocalService.hasAMImageEntryContent(
				Mockito.anyString(), Mockito.any(FileVersion.class))
		).thenReturn(
			false
		);
		Mockito.doReturn(
			false
		).when(
			_amImageValidatorImpl
		).isValid(
			Mockito.any(FileVersion.class)
		);

		Assert.assertFalse(
			_amImageValidatorImpl.isProcessingRequired(
				_adaptiveMedia, Mockito.mock(FileVersion.class)));
	}

	@Test
	public void testIsProcessingRequiredWhenNoContentPresent() {
		Mockito.when(
			_amImageEntryLocalService.hasAMImageEntryContent(
				Mockito.anyString(), Mockito.any(FileVersion.class))
		).thenReturn(
			false
		);
		Mockito.doReturn(
			true
		).when(
			_amImageValidatorImpl
		).isValid(
			Mockito.any(FileVersion.class)
		);

		Assert.assertTrue(
			_amImageValidatorImpl.isProcessingRequired(
				_adaptiveMedia, Mockito.mock(FileVersion.class)));

		_verifyAMImageEntryLocalServiceMock();
	}

	private void _verifyAMImageEntryLocalServiceMock() {
		Mockito.verify(
			_amImageEntryLocalService, Mockito.times(1)
		).hasAMImageEntryContent(
			Mockito.anyString(), Mockito.any(FileVersion.class)
		);
	}

	private final AdaptiveMedia<Object> _adaptiveMedia = Mockito.mock(
		AdaptiveMedia.class);
	private final AMImageEntryLocalService _amImageEntryLocalService =
		Mockito.mock(AMImageEntryLocalService.class);
	private final AMImageValidatorImpl _amImageValidatorImpl = Mockito.spy(
		new AMImageValidatorImpl());

}