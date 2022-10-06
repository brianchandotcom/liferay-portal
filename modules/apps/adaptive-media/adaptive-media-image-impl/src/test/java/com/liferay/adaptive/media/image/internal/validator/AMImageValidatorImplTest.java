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

package com.liferay.adaptive.media.image.internal.validator;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.image.service.AMImageEntryLocalService;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Optional;

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
			_adaptiveMedia.getValueOptional(
				AMAttribute.getConfigurationUuidAMAttribute())
		).thenReturn(
			Optional.of(RandomTestUtil.randomString())
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

		Assert.assertFalse(
			_amImageValidatorImpl.isProcessingRequired(
				_adaptiveMedia, Mockito.mock(FileVersion.class)));

		_verifyAMImageEntryLocalServiceMock();
	}

	@Test
	public void testIsProcessingRequiredWhenNoContentPresent() {
		Mockito.when(
			_amImageEntryLocalService.hasAMImageEntryContent(
				Mockito.anyString(), Mockito.any(FileVersion.class))
		).thenReturn(
			false
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
	private final AMImageValidatorImpl _amImageValidatorImpl =
		new AMImageValidatorImpl();

}