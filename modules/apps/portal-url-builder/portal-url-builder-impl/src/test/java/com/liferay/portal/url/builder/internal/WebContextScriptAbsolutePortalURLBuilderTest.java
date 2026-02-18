/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.url.builder.internal;

import com.liferay.portal.kernel.frontend.hashed.files.CachingStrategy;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.WebContextScriptAbsolutePortalURLBuilder;

import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Iván Zaera Avellón
 */
@RunWith(Parameterized.class)
public class WebContextScriptAbsolutePortalURLBuilderTest
	extends BaseAbsolutePortalURLBuilderTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Parameterized.Parameters(
		name = "{0}: cachingStrategy={1}, cdnHost={2}, context={3}, proxy={4}"
	)
	public static Collection<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{0, CachingStrategy.DO_NOT_USE_HASHES, false, false, false},
				{1, CachingStrategy.DO_NOT_USE_HASHES, false, false, true},
				{2, CachingStrategy.DO_NOT_USE_HASHES, false, true, false},
				{3, CachingStrategy.DO_NOT_USE_HASHES, false, true, true},
				{4, CachingStrategy.DO_NOT_USE_HASHES, true, false, false},
				{5, CachingStrategy.DO_NOT_USE_HASHES, true, false, true},
				{6, CachingStrategy.DO_NOT_USE_HASHES, true, true, false},
				{7, CachingStrategy.DO_NOT_USE_HASHES, true, true, true},
				{8, CachingStrategy.USE_ONE_HASH_PER_FILE, false, false, false},
				{9, CachingStrategy.USE_ONE_HASH_PER_FILE, false, false, true},
				{10, CachingStrategy.USE_ONE_HASH_PER_FILE, false, true, false},
				{11, CachingStrategy.USE_ONE_HASH_PER_FILE, false, true, true},
				{12, CachingStrategy.USE_ONE_HASH_PER_FILE, true, false, false},
				{13, CachingStrategy.USE_ONE_HASH_PER_FILE, true, false, true},
				{14, CachingStrategy.USE_ONE_HASH_PER_FILE, true, true, false},
				{15, CachingStrategy.USE_ONE_HASH_PER_FILE, true, true, true},
				{
					16, CachingStrategy.USE_ONE_HASH_PER_WEB_CONTEXT, false,
					false, false
				},
				{
					17, CachingStrategy.USE_ONE_HASH_PER_WEB_CONTEXT, false,
					false, true
				},
				{
					18, CachingStrategy.USE_ONE_HASH_PER_WEB_CONTEXT, false,
					true, false
				},
				{
					19, CachingStrategy.USE_ONE_HASH_PER_WEB_CONTEXT, false,
					true, true
				},
				{
					20, CachingStrategy.USE_ONE_HASH_PER_WEB_CONTEXT, true,
					false, false
				},
				{
					21, CachingStrategy.USE_ONE_HASH_PER_WEB_CONTEXT, true,
					false, true
				},
				{
					22, CachingStrategy.USE_ONE_HASH_PER_WEB_CONTEXT, true,
					true, false
				},
				{
					23, CachingStrategy.USE_ONE_HASH_PER_WEB_CONTEXT, true,
					true, true
				}
			});
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_absolutePortalURLBuilder = new AbsolutePortalURLBuilderImpl(
			mockCacheHelper(), mockHashedFilesRegistry(cachingStrategy),
			mockPortal(context, proxy, cdnHost), mockHttpServletRequest());
	}

	@After
	public void tearDown() {
		super.tearDown();
	}

	@Test
	public void test() {
		WebContextScriptAbsolutePortalURLBuilder
			webContextScriptAbsolutePortalURLBuilder =
				_absolutePortalURLBuilder.forWebContextScript(
					"classic-theme", "js/main.js");

		Assert.assertEquals(
			_RESULTS[index], webContextScriptAbsolutePortalURLBuilder.build());

		webContextScriptAbsolutePortalURLBuilder =
			_absolutePortalURLBuilder.forWebContextScript(
				"classic-theme", "js/main.js");

		webContextScriptAbsolutePortalURLBuilder.ignoreCDNHost();

		Assert.assertEquals(
			_RESULTS_IGNORE_CDN[index],
			webContextScriptAbsolutePortalURLBuilder.build());

		webContextScriptAbsolutePortalURLBuilder =
			_absolutePortalURLBuilder.forWebContextScript(
				"classic-theme", "js/main.js");

		webContextScriptAbsolutePortalURLBuilder.ignoreCDNHost();
		webContextScriptAbsolutePortalURLBuilder.ignorePathProxy();

		Assert.assertEquals(
			_RESULTS_IGNORE_CDN_AND_PROXY[index],
			webContextScriptAbsolutePortalURLBuilder.build());

		webContextScriptAbsolutePortalURLBuilder =
			_absolutePortalURLBuilder.forWebContextScript(
				"classic-theme", "js/main.js");

		webContextScriptAbsolutePortalURLBuilder.ignorePathProxy();

		Assert.assertEquals(
			_RESULTS_IGNORE_PROXY[index],
			webContextScriptAbsolutePortalURLBuilder.build());
	}

	@Parameterized.Parameter(1)
	public CachingStrategy cachingStrategy;

	@Parameterized.Parameter(2)
	public boolean cdnHost;

	@Parameterized.Parameter(3)
	public boolean context;

	@Parameterized.Parameter
	public int index;

	@Parameterized.Parameter(4)
	public boolean proxy;

	private static final String[] _RESULTS = {
		"/o/classic-theme/js/main.js", "/proxy/o/classic-theme/js/main.js",
		"/context/o/classic-theme/js/main.js",
		"/proxy/context/o/classic-theme/js/main.js",
		"http://cdn-host/o/classic-theme/js/main.js",
		"http://cdn-host/proxy/o/classic-theme/js/main.js",
		"http://cdn-host/context/o/classic-theme/js/main.js",
		"http://cdn-host/proxy/context/o/classic-theme/js/main.js",
		"/o/classic-theme/js/main.(HASH).js",
		"/proxy/o/classic-theme/js/main.(HASH).js",
		"/context/o/classic-theme/js/main.(HASH).js",
		"/proxy/context/o/classic-theme/js/main.(HASH).js",
		"http://cdn-host/o/classic-theme/js/main.(HASH).js",
		"http://cdn-host/proxy/o/classic-theme/js/main.(HASH).js",
		"http://cdn-host/context/o/classic-theme/js/main.(HASH).js",
		"http://cdn-host/proxy/context/o/classic-theme/js/main.(HASH).js",
		"/o/js/-/classic-theme(HASH)/js/main.js",
		"/proxy/o/js/-/classic-theme(HASH)/js/main.js",
		"/context/o/js/-/classic-theme(HASH)/js/main.js",
		"/proxy/context/o/js/-/classic-theme(HASH)/js/main.js",
		"http://cdn-host/o/js/-/classic-theme(HASH)/js/main.js",
		"http://cdn-host/proxy/o/js/-/classic-theme(HASH)/js/main.js",
		"http://cdn-host/context/o/js/-/classic-theme(HASH)/js/main.js",
		"http://cdn-host/proxy/context/o/js/-/classic-theme(HASH)/js/main.js"
	};

	private static final String[] _RESULTS_IGNORE_CDN = {
		"/o/classic-theme/js/main.js", "/proxy/o/classic-theme/js/main.js",
		"/context/o/classic-theme/js/main.js",
		"/proxy/context/o/classic-theme/js/main.js",
		"/o/classic-theme/js/main.js", "/proxy/o/classic-theme/js/main.js",
		"/context/o/classic-theme/js/main.js",
		"/proxy/context/o/classic-theme/js/main.js",
		"/o/classic-theme/js/main.(HASH).js",
		"/proxy/o/classic-theme/js/main.(HASH).js",
		"/context/o/classic-theme/js/main.(HASH).js",
		"/proxy/context/o/classic-theme/js/main.(HASH).js",
		"/o/classic-theme/js/main.(HASH).js",
		"/proxy/o/classic-theme/js/main.(HASH).js",
		"/context/o/classic-theme/js/main.(HASH).js",
		"/proxy/context/o/classic-theme/js/main.(HASH).js",
		"/o/js/-/classic-theme(HASH)/js/main.js",
		"/proxy/o/js/-/classic-theme(HASH)/js/main.js",
		"/context/o/js/-/classic-theme(HASH)/js/main.js",
		"/proxy/context/o/js/-/classic-theme(HASH)/js/main.js",
		"/o/js/-/classic-theme(HASH)/js/main.js",
		"/proxy/o/js/-/classic-theme(HASH)/js/main.js",
		"/context/o/js/-/classic-theme(HASH)/js/main.js",
		"/proxy/context/o/js/-/classic-theme(HASH)/js/main.js"
	};

	private static final String[] _RESULTS_IGNORE_CDN_AND_PROXY = {
		"/o/classic-theme/js/main.js", "/o/classic-theme/js/main.js",
		"/context/o/classic-theme/js/main.js",
		"/context/o/classic-theme/js/main.js", "/o/classic-theme/js/main.js",
		"/o/classic-theme/js/main.js", "/context/o/classic-theme/js/main.js",
		"/context/o/classic-theme/js/main.js",
		"/o/classic-theme/js/main.(HASH).js",
		"/o/classic-theme/js/main.(HASH).js",
		"/context/o/classic-theme/js/main.(HASH).js",
		"/context/o/classic-theme/js/main.(HASH).js",
		"/o/classic-theme/js/main.(HASH).js",
		"/o/classic-theme/js/main.(HASH).js",
		"/context/o/classic-theme/js/main.(HASH).js",
		"/context/o/classic-theme/js/main.(HASH).js",
		"/o/js/-/classic-theme(HASH)/js/main.js",
		"/o/js/-/classic-theme(HASH)/js/main.js",
		"/context/o/js/-/classic-theme(HASH)/js/main.js",
		"/context/o/js/-/classic-theme(HASH)/js/main.js",
		"/o/js/-/classic-theme(HASH)/js/main.js",
		"/o/js/-/classic-theme(HASH)/js/main.js",
		"/context/o/js/-/classic-theme(HASH)/js/main.js",
		"/context/o/js/-/classic-theme(HASH)/js/main.js"
	};

	private static final String[] _RESULTS_IGNORE_PROXY = {
		"/o/classic-theme/js/main.js", "/o/classic-theme/js/main.js",
		"/context/o/classic-theme/js/main.js",
		"/context/o/classic-theme/js/main.js",
		"http://cdn-host/o/classic-theme/js/main.js",
		"http://cdn-host/o/classic-theme/js/main.js",
		"http://cdn-host/context/o/classic-theme/js/main.js",
		"http://cdn-host/context/o/classic-theme/js/main.js",
		"/o/classic-theme/js/main.(HASH).js",
		"/o/classic-theme/js/main.(HASH).js",
		"/context/o/classic-theme/js/main.(HASH).js",
		"/context/o/classic-theme/js/main.(HASH).js",
		"http://cdn-host/o/classic-theme/js/main.(HASH).js",
		"http://cdn-host/o/classic-theme/js/main.(HASH).js",
		"http://cdn-host/context/o/classic-theme/js/main.(HASH).js",
		"http://cdn-host/context/o/classic-theme/js/main.(HASH).js",
		"/o/js/-/classic-theme(HASH)/js/main.js",
		"/o/js/-/classic-theme(HASH)/js/main.js",
		"/context/o/js/-/classic-theme(HASH)/js/main.js",
		"/context/o/js/-/classic-theme(HASH)/js/main.js",
		"http://cdn-host/o/js/-/classic-theme(HASH)/js/main.js",
		"http://cdn-host/o/js/-/classic-theme(HASH)/js/main.js",
		"http://cdn-host/context/o/js/-/classic-theme(HASH)/js/main.js",
		"http://cdn-host/context/o/js/-/classic-theme(HASH)/js/main.js"
	};

	private AbsolutePortalURLBuilder _absolutePortalURLBuilder;

}