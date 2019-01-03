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

package com.liferay.portal.apio.internal.architect.provider;

import com.liferay.apio.architect.language.AcceptLanguage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.core.HttpHeaders;

import org.junit.Assert;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Rubén Pulido
 */
public class AcceptLanguageProviderTest {

	@Test
	public void testCreateContextWithMultipleLocalesAndAcceptLanguageHeader() {
		AcceptLanguageProvider acceptLanguageProvider =
			new AcceptLanguageProvider();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader(
			HttpHeaders.ACCEPT_LANGUAGE, "someValue");

		List<Locale> locales = Arrays.asList(
			Locale.JAPAN, Locale.GERMAN, Locale.US);

		mockHttpServletRequest.setPreferredLocales(locales);

		AcceptLanguage acceptLanguage = acceptLanguageProvider.createContext(
			mockHttpServletRequest);

		Locale locale = acceptLanguage.getPreferredLocale();

		Assert.assertEquals("ja-JP", locale.toLanguageTag());
	}

	@Test
	public void testCreateContextWithMultipleLocalesAndNoAcceptLanguageHeaderAndNoUser() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		AcceptLanguageProvider acceptLanguageProvider =
			new AcceptLanguageProvider();

		List<Locale> locales = new ArrayList<Locale>() {
			{
				add(Locale.JAPAN);
				add(Locale.GERMAN);
				add(Locale.US);
			}
		};

		mockHttpServletRequest.setPreferredLocales(locales);

		AcceptLanguage acceptLanguage = acceptLanguageProvider.createContext(
			mockHttpServletRequest);

		Assert.assertNull(acceptLanguage.getPreferredLocale());
	}

}