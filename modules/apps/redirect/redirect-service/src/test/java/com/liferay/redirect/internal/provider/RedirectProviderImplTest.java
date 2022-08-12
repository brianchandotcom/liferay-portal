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

package com.liferay.redirect.internal.provider;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.redirect.provider.RedirectProvider;
import com.liferay.redirect.service.RedirectEntryLocalService;

import java.util.Collections;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class RedirectProviderImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_redirectProviderImpl.setRedirectEntryLocalService(
			_redirectEntryLocalService);

		Mockito.when(
			_redirectEntryLocalService.fetchRedirectEntry(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyBoolean())
		).thenReturn(
			null
		);
	}

	@Test
	public void testEmptyPatterns() {
		_redirectProviderImpl.setPatterns(Collections.emptyMap());

		Assert.assertNull(
			_getRedirectProviderRedirect(StringUtil.randomString()));

		_verifyMockInvocations();
	}

	@Test
	public void testFirstReplacementPatternMatches() {
		_redirectProviderImpl.setPatterns(
			LinkedHashMapBuilder.put(
				Pattern.compile("^a(b)c"), "u$1w"
			).put(
				Pattern.compile("^abc"), "xyz"
			).build());

		RedirectProvider.Redirect redirect = _getRedirectProviderRedirect(
			"abc");

		Assert.assertEquals("ubw", redirect.getDestinationURL());

		_verifyMockInvocations();
	}

	@Test
	public void testFirstSimplePatternMatches() {
		_redirectProviderImpl.setPatterns(
			LinkedHashMapBuilder.put(
				Pattern.compile("^abc"), "xyz"
			).put(
				Pattern.compile("^a(b)c"), "u$1w"
			).build());

		RedirectProvider.Redirect redirect = _getRedirectProviderRedirect(
			"abc");

		Assert.assertEquals("xyz", redirect.getDestinationURL());

		_verifyMockInvocations();
	}

	@Test
	public void testLastReplacementPatternMatches() {
		_redirectProviderImpl.setPatterns(
			LinkedHashMapBuilder.put(
				Pattern.compile("^uvw"), "xyz"
			).put(
				Pattern.compile("^a(b)c"), "u$1w"
			).build());

		RedirectProvider.Redirect redirect = _getRedirectProviderRedirect(
			"abc");

		Assert.assertEquals("ubw", redirect.getDestinationURL());

		_verifyMockInvocations();
	}

	@Test
	public void testLastSimplePatternMatches() {
		_redirectProviderImpl.setPatterns(
			LinkedHashMapBuilder.put(
				Pattern.compile("^u(v)w"), "x$1z"
			).put(
				Pattern.compile("^abc"), "123"
			).build());

		RedirectProvider.Redirect redirect = _getRedirectProviderRedirect(
			"abc");

		Assert.assertEquals("123", redirect.getDestinationURL());

		_verifyMockInvocations();
	}

	@Test
	public void testRewritePatternSingleMatch() {
		_redirectProviderImpl.setPatterns(
			Collections.singletonMap(Pattern.compile("^a(b)c"), "x$1z"));

		RedirectProvider.Redirect redirect = _getRedirectProviderRedirect(
			"abc");

		Assert.assertEquals("xbz", redirect.getDestinationURL());

		_verifyMockInvocations();
	}

	@Test
	public void testRewritePatternSingleMismatch() {
		_redirectProviderImpl.setPatterns(
			Collections.singletonMap(Pattern.compile("^a(b)c"), "x$1z"));

		Assert.assertNull(_getRedirectProviderRedirect("123"));

		_verifyMockInvocations();
	}

	@Test
	public void testSimplePatternSingleMatch() {
		_redirectProviderImpl.setPatterns(
			Collections.singletonMap(Pattern.compile("^abc"), "xyz"));

		RedirectProvider.Redirect redirect = _getRedirectProviderRedirect(
			"abc");

		Assert.assertEquals("xyz", redirect.getDestinationURL());

		_verifyMockInvocations();
	}

	@Test
	public void testSimplePatternSingleMismatch() {
		_redirectProviderImpl.setPatterns(
			Collections.singletonMap(Pattern.compile("^abc"), "xyz"));

		Assert.assertNull(_getRedirectProviderRedirect("123"));

		_verifyMockInvocations();
	}

	private RedirectProvider.Redirect _getRedirectProviderRedirect(
		String friendlyURL) {

		return _redirectProviderImpl.getRedirect(
			RandomTestUtil.randomLong(), StringUtil.randomString(),
			friendlyURL);
	}

	private void _verifyMockInvocations() {
		Mockito.verify(
			_redirectEntryLocalService, Mockito.times(1)
		).fetchRedirectEntry(
			Mockito.anyLong(), Mockito.anyString(), Mockito.eq(false)
		);

		Mockito.verify(
			_redirectEntryLocalService, Mockito.times(1)
		).fetchRedirectEntry(
			Mockito.anyLong(), Mockito.anyString(), Mockito.eq(true)
		);
	}

	private final RedirectEntryLocalService _redirectEntryLocalService =
		Mockito.mock(RedirectEntryLocalService.class);
	private final RedirectProviderImpl _redirectProviderImpl =
		new RedirectProviderImpl();

}