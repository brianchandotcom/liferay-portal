/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.simplecaptcha;

import com.liferay.captcha.configuration.CaptchaConfiguration;
import com.liferay.captcha.provider.CaptchaProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import nl.captcha.noise.NoiseProducer;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Pedro Victor Silvestre
 */
public class SimpleCaptchaImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetNoiseProducer() {
		SimpleCaptchaImpl simpleCaptchaImpl = new SimpleCaptchaImpl();

		_mockCaptchaProvider(
			new String[] {
				"nl.captcha.noise.CurvedLineNoiseProducer",
				"nl.captcha.noise.StraightLineNoiseProducer"
			});

		ReflectionTestUtil.setFieldValue(
			simpleCaptchaImpl, "captchaProvider", _captchaProvider);

		NoiseProducer noiseProducer = simpleCaptchaImpl.getNoiseProducer();

		_mockCaptchaProvider(new String[] {StringPool.BLANK});

		ReflectionTestUtil.setFieldValue(
			simpleCaptchaImpl, "captchaProvider", _captchaProvider);

		Assert.assertNotEquals(
			noiseProducer, simpleCaptchaImpl.getNoiseProducer());
	}

	private void _mockCaptchaProvider(String[] expectedClassNames) {
		_captchaProvider = Mockito.mock(CaptchaProvider.class);

		CaptchaConfiguration captchaConfiguration = Mockito.mock(
			CaptchaConfiguration.class);

		Mockito.when(
			_captchaProvider.getCaptchaConfiguration()
		).thenReturn(
			captchaConfiguration
		);

		Mockito.when(
			captchaConfiguration.simpleCaptchaNoiseProducers()
		).thenReturn(
			expectedClassNames
		);
	}

	private static CaptchaProvider _captchaProvider;

}