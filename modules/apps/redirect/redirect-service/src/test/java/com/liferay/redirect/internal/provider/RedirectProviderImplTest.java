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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.redirect.constants.RedirectConstants;
import com.liferay.redirect.model.RedirectPatternEntry;
import com.liferay.redirect.provider.RedirectProvider;
import com.liferay.redirect.service.RedirectEntryLocalService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
		MockitoAnnotations.initMocks(this);

		_redirectProviderImpl.setRedirectEntryLocalService(
			_redirectEntryLocalService);

		Mockito.when(
			_redirectEntryLocalService.fetchRedirectEntry(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyBoolean())
		).thenReturn(
			null
		);

		PropsUtil.setProps(_props);
	}

	@Test
	public void testControlPanelURLs() {
		_setupRedirectPatternEntries(
			Collections.singletonList(
				new RedirectPatternEntry(
					Pattern.compile("^.*/control_panel/manage"), "xyz",
					StringPool.BLANK)));

		Assert.assertNull(
			_getRedirectProviderRedirect(
				"/control_panel/manage", StringPool.BLANK));

		Mockito.verify(
			_redirectEntryLocalService, Mockito.never()
		).fetchRedirectEntry(
			Mockito.anyLong(), Mockito.anyString(), Mockito.anyBoolean()
		);
	}

	@Test
	public void testEmptyPatterns() {
		_setupRedirectPatternEntries(Collections.emptyList());

		Assert.assertNull(
			_getRedirectProviderRedirect(
				StringUtil.randomString(), StringPool.BLANK));

		_verifyMockInvocations();
	}

	@Test
	public void testFirstReplacementPatternMatches() {
		List<RedirectPatternEntry> redirectPatternEntries = new ArrayList<>();

		redirectPatternEntries.add(
			new RedirectPatternEntry(
				Pattern.compile("^a(b)c"), "u$1w", StringPool.BLANK));
		redirectPatternEntries.add(
			new RedirectPatternEntry(
				Pattern.compile("^abc"), "xyz", StringPool.BLANK));

		_setupRedirectPatternEntries(redirectPatternEntries);

		RedirectProvider.Redirect redirect = _getRedirectProviderRedirect(
			"abc", StringPool.BLANK);

		Assert.assertEquals("ubw", redirect.getDestinationURL());

		_verifyMockInvocations();
	}

	@Test
	public void testFirstSimplePatternMatches() {
		List<RedirectPatternEntry> redirectPatternEntries = new ArrayList<>();

		redirectPatternEntries.add(
			new RedirectPatternEntry(
				Pattern.compile("^abc"), "xyz", StringPool.BLANK));
		redirectPatternEntries.add(
			new RedirectPatternEntry(
				Pattern.compile("^a(b)c"), "u$1w", StringPool.BLANK));

		_setupRedirectPatternEntries(redirectPatternEntries);

		RedirectProvider.Redirect redirect = _getRedirectProviderRedirect(
			"abc", StringPool.BLANK);

		Assert.assertEquals("xyz", redirect.getDestinationURL());

		_verifyMockInvocations();
	}

	@Test
	public void testLastReplacementPatternMatches() {
		List<RedirectPatternEntry> redirectPatternEntries = new ArrayList<>();

		redirectPatternEntries.add(
			new RedirectPatternEntry(
				Pattern.compile("^uvw"), "xyz", StringPool.BLANK));
		redirectPatternEntries.add(
			new RedirectPatternEntry(
				Pattern.compile("^a(b)c"), "u$1w", StringPool.BLANK));

		_setupRedirectPatternEntries(redirectPatternEntries);

		RedirectProvider.Redirect redirect = _getRedirectProviderRedirect(
			"abc", StringPool.BLANK);

		Assert.assertEquals("ubw", redirect.getDestinationURL());

		_verifyMockInvocations();
	}

	@Test
	public void testLastSimplePatternMatches() {
		List<RedirectPatternEntry> redirectPatternEntries = new ArrayList<>();

		redirectPatternEntries.add(
			new RedirectPatternEntry(
				Pattern.compile("^u(v)w"), "x$1z", StringPool.BLANK));
		redirectPatternEntries.add(
			new RedirectPatternEntry(
				Pattern.compile("^abc"), "123", StringPool.BLANK));

		_setupRedirectPatternEntries(redirectPatternEntries);

		RedirectProvider.Redirect redirect = _getRedirectProviderRedirect(
			"abc", StringPool.BLANK);

		Assert.assertEquals("123", redirect.getDestinationURL());

		_verifyMockInvocations();
	}

	@Test
	public void testRewritePatternSingleMatch() {
		_setupRedirectPatternEntries(
			Collections.singletonList(
				new RedirectPatternEntry(
					Pattern.compile("^a(b)c"), "x$1z", StringPool.BLANK)));

		RedirectProvider.Redirect redirect = _getRedirectProviderRedirect(
			"abc", StringPool.BLANK);

		Assert.assertEquals("xbz", redirect.getDestinationURL());

		_verifyMockInvocations();
	}

	@Test
	public void testRewritePatternSingleMismatch() {
		_setupRedirectPatternEntries(
			Collections.singletonList(
				new RedirectPatternEntry(
					Pattern.compile("^a(b)c"), "x$1z", StringPool.BLANK)));

		Assert.assertNull(
			_getRedirectProviderRedirect("123", StringPool.BLANK));

		_verifyMockInvocations();
	}

	@Test
	public void testSimplePatternDoesntMatchUserAgentBot() {
		_redirectProviderImpl.setCrawlerUserAgents(_CRAWLER_USER_AGENTS);

		Mockito.when(
			_props.get("feature.flag.LPS-175850")
		).thenReturn(
			"true"
		);

		try {
			_setupRedirectPatternEntries(
				Collections.singletonList(
					new RedirectPatternEntry(
						Pattern.compile("^abc"), "xyz",
						RedirectConstants.USER_AGENT_BOT)));

			RedirectProvider.Redirect redirect = _getRedirectProviderRedirect(
				"abc", "another");

			Assert.assertNull(redirect);

			_verifyMockInvocations();
		}
		finally {
			_redirectProviderImpl.setCrawlerUserAgents(null);
		}
	}

	@Test
	public void testSimplePatternDoesntMatchUserAgentHuman() {
		_redirectProviderImpl.setCrawlerUserAgents(_CRAWLER_USER_AGENTS);

		Mockito.when(
			_props.get("feature.flag.LPS-175850")
		).thenReturn(
			"true"
		);

		try {
			_setupRedirectPatternEntries(
				Collections.singletonList(
					new RedirectPatternEntry(
						Pattern.compile("^abc"), "xyz",
						RedirectConstants.USER_AGENT_HUMAN)));

			RedirectProvider.Redirect redirect = _getRedirectProviderRedirect(
				"abc", "bot");

			Assert.assertNull(redirect);

			_verifyMockInvocations();
		}
		finally {
			_redirectProviderImpl.setCrawlerUserAgents(null);
		}
	}

	@Test
	public void testSimplePatternMatchesNoUserAgent() {
		Mockito.when(
			_props.get("feature.flag.LPS-175850")
		).thenReturn(
			"true"
		);

		_setupRedirectPatternEntries(
			Collections.singletonList(
				new RedirectPatternEntry(
					Pattern.compile("^abc"), "xyz", StringPool.BLANK)));

		RedirectProvider.Redirect redirect = _getRedirectProviderRedirect(
			"abc", "CrawlerBot");

		Assert.assertEquals("xyz", redirect.getDestinationURL());

		_verifyMockInvocations();
	}

	@Test
	public void testSimplePatternMatchesNoUserAgentOnRedirect() {
		Mockito.when(
			_props.get("feature.flag.LPS-175850")
		).thenReturn(
			"true"
		);

		_setupRedirectPatternEntries(
			Collections.singletonList(
				new RedirectPatternEntry(
					Pattern.compile("^abc"), "xyz",
					RedirectConstants.USER_AGENT_BOT)));

		RedirectProvider.Redirect redirect = _getRedirectProviderRedirect(
			"abc", StringPool.BLANK);

		Assert.assertEquals("xyz", redirect.getDestinationURL());

		_verifyMockInvocations();
	}

	@Test
	public void testSimplePatternMatchesUserAgentBot() {
		_redirectProviderImpl.setCrawlerUserAgents(_CRAWLER_USER_AGENTS);

		Mockito.when(
			_props.get("feature.flag.LPS-175850")
		).thenReturn(
			"true"
		);

		try {
			_setupRedirectPatternEntries(
				Collections.singletonList(
					new RedirectPatternEntry(
						Pattern.compile("^abc"), "xyz",
						RedirectConstants.USER_AGENT_BOT)));

			RedirectProvider.Redirect redirect = _getRedirectProviderRedirect(
				"abc", "CrawlerBot");

			Assert.assertEquals("xyz", redirect.getDestinationURL());

			_verifyMockInvocations();
		}
		finally {
			_redirectProviderImpl.setCrawlerUserAgents(null);
		}
	}

	@Test
	public void testSimplePatternMatchUserAgentBotNoFF() {
		_redirectProviderImpl.setCrawlerUserAgents(_CRAWLER_USER_AGENTS);

		try {
			_setupRedirectPatternEntries(
				Collections.singletonList(
					new RedirectPatternEntry(
						Pattern.compile("^abc"), "xyz",
						RedirectConstants.USER_AGENT_BOT)));

			RedirectProvider.Redirect redirect = _getRedirectProviderRedirect(
				"abc", "another");

			Assert.assertEquals("xyz", redirect.getDestinationURL());

			_verifyMockInvocations();
		}
		finally {
			_redirectProviderImpl.setCrawlerUserAgents(null);
		}
	}

	@Test
	public void testSimplePatternMatchUserAgentHumanNoFF() {
		_redirectProviderImpl.setCrawlerUserAgents(_CRAWLER_USER_AGENTS);

		try {
			_setupRedirectPatternEntries(
				Collections.singletonList(
					new RedirectPatternEntry(
						Pattern.compile("^abc"), "xyz",
						RedirectConstants.USER_AGENT_HUMAN)));

			RedirectProvider.Redirect redirect = _getRedirectProviderRedirect(
				"abc", "bot");

			Assert.assertEquals("xyz", redirect.getDestinationURL());

			_verifyMockInvocations();
		}
		finally {
			_redirectProviderImpl.setCrawlerUserAgents(null);
		}
	}

	@Test
	public void testSimplePatternSingleMatch() {
		_setupRedirectPatternEntries(
			Collections.singletonList(
				new RedirectPatternEntry(
					Pattern.compile("^abc"), "xyz", StringPool.BLANK)));

		RedirectProvider.Redirect redirect = _getRedirectProviderRedirect(
			"abc", StringPool.BLANK);

		Assert.assertEquals("xyz", redirect.getDestinationURL());

		_verifyMockInvocations();
	}

	@Test
	public void testSimplePatternSingleMatchesUserAgentAll() {
		Mockito.when(
			_props.get("feature.flag.LPS-175850")
		).thenReturn(
			"true"
		);

		_setupRedirectPatternEntries(
			Collections.singletonList(
				new RedirectPatternEntry(
					Pattern.compile("^abc"), "xyz",
					RedirectConstants.USER_AGENT_ALL)));

		RedirectProvider.Redirect redirect = _getRedirectProviderRedirect(
			"abc", "CrawlerBot");

		Assert.assertEquals("xyz", redirect.getDestinationURL());

		_verifyMockInvocations();
	}

	@Test
	public void testSimplePatternSingleMatchesUserAgentHuman() {
		Mockito.when(
			_props.get("feature.flag.LPS-175850")
		).thenReturn(
			"true"
		);

		_setupRedirectPatternEntries(
			Collections.singletonList(
				new RedirectPatternEntry(
					Pattern.compile("^abc"), "xyz",
					RedirectConstants.USER_AGENT_HUMAN)));

		RedirectProvider.Redirect redirect = _getRedirectProviderRedirect(
			"abc", "human");

		Assert.assertEquals("xyz", redirect.getDestinationURL());

		_verifyMockInvocations();
	}

	@Test
	public void testSimplePatternSingleMismatch() {
		_setupRedirectPatternEntries(
			Collections.singletonList(
				new RedirectPatternEntry(
					Pattern.compile("^abc"), "xyz", StringPool.BLANK)));

		Assert.assertNull(
			_getRedirectProviderRedirect("123", StringPool.BLANK));

		_verifyMockInvocations();
	}

	private RedirectProvider.Redirect _getRedirectProviderRedirect(
		String friendlyURL, String userAgent) {

		return _redirectProviderImpl.getRedirect(
			_GROUP_ID, friendlyURL, StringUtil.randomString(), userAgent);
	}

	private void _setupRedirectPatternEntries(
		List<RedirectPatternEntry> redirectPatternEntries) {

		_redirectProviderImpl.setRedirectPatternEntries(
			HashMapBuilder.put(
				_GROUP_ID, redirectPatternEntries
			).build());
	}

	private void _verifyMockInvocations() {
		Mockito.verify(
			_redirectEntryLocalService, Mockito.times(1)
		).fetchRedirectEntry(
			Mockito.eq(_GROUP_ID), Mockito.anyString(), Mockito.eq(false)
		);

		Mockito.verify(
			_redirectEntryLocalService, Mockito.times(1)
		).fetchRedirectEntry(
			Mockito.eq(_GROUP_ID), Mockito.anyString(), Mockito.eq(true)
		);
	}

	private static final String[] _CRAWLER_USER_AGENTS = {"bot", "crawlerbot"};

	private static final long _GROUP_ID = RandomTestUtil.randomLong();

	@Mock
	private Props _props;

	private final RedirectEntryLocalService _redirectEntryLocalService =
		Mockito.mock(RedirectEntryLocalService.class);
	private final RedirectProviderImpl _redirectProviderImpl =
		new RedirectProviderImpl();

}