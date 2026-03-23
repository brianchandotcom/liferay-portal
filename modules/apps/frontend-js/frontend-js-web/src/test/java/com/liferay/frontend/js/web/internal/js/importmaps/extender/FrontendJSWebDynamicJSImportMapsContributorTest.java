/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.js.importmaps.extender;

import com.liferay.frontend.js.web.internal.util.FrontendJSWebUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.frontend.hashed.files.CachingStrategy;
import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesRegistry;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;

import java.nio.charset.StandardCharsets;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.BiConsumer;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author Iván Zaera Avellón
 */
@RunWith(Parameterized.class)
public class FrontendJSWebDynamicJSImportMapsContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Parameterized.Parameters(
		name = "{0}: cachingStrategy={1} cdnHost={2}, pathContext={3}, pathProxy={4}"
	)
	public static Collection<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{
					0, CachingStrategy.DO_NOT_USE_HASHES, StringPool.BLANK,
					StringPool.BLANK, StringPool.BLANK
				},
				{
					1, CachingStrategy.DO_NOT_USE_HASHES, StringPool.BLANK,
					StringPool.BLANK, "/liferay"
				},
				{
					2, CachingStrategy.DO_NOT_USE_HASHES, StringPool.BLANK,
					"/dxp", StringPool.BLANK
				},
				{
					3, CachingStrategy.DO_NOT_USE_HASHES, StringPool.BLANK,
					"/dxp", "/liferay"
				},
				{
					4, CachingStrategy.DO_NOT_USE_HASHES, "http://cdn.com",
					StringPool.BLANK, StringPool.BLANK
				},
				{
					5, CachingStrategy.DO_NOT_USE_HASHES, "http://cdn.com",
					StringPool.BLANK, "/liferay"
				},
				{
					6, CachingStrategy.DO_NOT_USE_HASHES, "http://cdn.com",
					"/dxp", StringPool.BLANK
				},
				{
					7, CachingStrategy.DO_NOT_USE_HASHES, "http://cdn.com",
					"/dxp", "/liferay"
				},
				{
					8, CachingStrategy.USE_ONE_HASH_PER_FILE, StringPool.BLANK,
					StringPool.BLANK, StringPool.BLANK
				},
				{
					9, CachingStrategy.USE_ONE_HASH_PER_FILE, StringPool.BLANK,
					StringPool.BLANK, "/liferay"
				},
				{
					10, CachingStrategy.USE_ONE_HASH_PER_FILE, StringPool.BLANK,
					"/dxp", StringPool.BLANK
				},
				{
					11, CachingStrategy.USE_ONE_HASH_PER_FILE, StringPool.BLANK,
					"/dxp", "/liferay"
				},
				{
					12, CachingStrategy.USE_ONE_HASH_PER_FILE, "http://cdn.com",
					StringPool.BLANK, StringPool.BLANK
				},
				{
					13, CachingStrategy.USE_ONE_HASH_PER_FILE, "http://cdn.com",
					StringPool.BLANK, "/liferay"
				},
				{
					14, CachingStrategy.USE_ONE_HASH_PER_FILE, "http://cdn.com",
					"/dxp", StringPool.BLANK
				},
				{
					15, CachingStrategy.USE_ONE_HASH_PER_FILE, "http://cdn.com",
					"/dxp", "/liferay"
				},
				{
					16, CachingStrategy.USE_ONE_HASH_PER_WEB_CONTEXT,
					StringPool.BLANK, StringPool.BLANK, StringPool.BLANK
				},
				{
					17, CachingStrategy.USE_ONE_HASH_PER_WEB_CONTEXT,
					StringPool.BLANK, StringPool.BLANK, "/liferay"
				},
				{
					18, CachingStrategy.USE_ONE_HASH_PER_WEB_CONTEXT,
					StringPool.BLANK, "/dxp", StringPool.BLANK
				},
				{
					19, CachingStrategy.USE_ONE_HASH_PER_WEB_CONTEXT,
					StringPool.BLANK, "/dxp", "/liferay"
				},
				{
					20, CachingStrategy.USE_ONE_HASH_PER_WEB_CONTEXT,
					"http://cdn.com", StringPool.BLANK, StringPool.BLANK
				},
				{
					21, CachingStrategy.USE_ONE_HASH_PER_WEB_CONTEXT,
					"http://cdn.com", StringPool.BLANK, "/liferay"
				},
				{
					22, CachingStrategy.USE_ONE_HASH_PER_WEB_CONTEXT,
					"http://cdn.com", "/dxp", StringPool.BLANK
				},
				{
					23, CachingStrategy.USE_ONE_HASH_PER_WEB_CONTEXT,
					"http://cdn.com", "/dxp", "/liferay"
				}
			});
	}

	@Test
	public void testWriteGlobalImports() throws Exception {
		FrontendJSWebUtil.clearCache();

		FrontendJSWebDynamicJSImportMapsContributor
			frontendJSWebDynamicJSImportMapsContributor =
				new FrontendJSWebDynamicJSImportMapsContributor();

		ReflectionTestUtils.setField(
			frontendJSWebDynamicJSImportMapsContributor, "_hashedFilesRegistry",
			_mockHashedFileRegistry());
		ReflectionTestUtils.setField(
			frontendJSWebDynamicJSImportMapsContributor, "_portal",
			_mockPortal());

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
			byteArrayOutputStream, StandardCharsets.UTF_8);

		frontendJSWebDynamicJSImportMapsContributor.writeGlobalImports(
			new MockHttpServletRequest(), outputStreamWriter);

		outputStreamWriter.close();

		Assert.assertEquals(
			_RESULTS[index],
			byteArrayOutputStream.toString(StandardCharsets.UTF_8));
	}

	@Parameterized.Parameter(1)
	public CachingStrategy cachingStrategy;

	@Parameterized.Parameter(2)
	public String cdnHost;

	@Parameterized.Parameter
	public int index;

	@Parameterized.Parameter(3)
	public String pathContext;

	@Parameterized.Parameter(4)
	public String pathProxy;

	private HashedFilesRegistry _mockHashedFileRegistry() {
		HashedFilesRegistry hashedFilesRegistry = Mockito.mock(
			HashedFilesRegistry.class);

		Mockito.doAnswer(
			(Answer<Void>)invocationOnMock -> {
				BiConsumer<String, String> biConsumer =
					invocationOnMock.getArgument(0);

				biConsumer.accept(
					pathContext + "/o/frontend-js-web/__liferay__/index.js",
					pathContext +
						"/o/frontend-js-web/__liferay__/index.(HASH).js");

				return null;
			}
		).when(
			hashedFilesRegistry
		).forEachHashedFileURI(
			Mockito.any()
		);

		Mockito.doAnswer(
			(Answer<Void>)invocationOnMock -> {
				BiConsumer<String, String> biConsumer =
					invocationOnMock.getArgument(0);

				biConsumer.accept("frontend-js-web", "HASH");

				return null;
			}
		).when(
			hashedFilesRegistry
		).forEachServletContextHash(
			Mockito.any()
		);

		Mockito.when(
			hashedFilesRegistry.getCachingStrategy(Mockito.any())
		).thenReturn(
			cachingStrategy
		);

		return hashedFilesRegistry;
	}

	private Portal _mockPortal() throws Exception {
		Portal portal = Mockito.mock(Portal.class);

		Mockito.when(
			portal.getCDNHost(Mockito.any())
		).thenReturn(
			cdnHost
		);

		Mockito.when(
			portal.getPathContext()
		).thenReturn(
			pathProxy + pathContext
		);

		Mockito.when(
			portal.getPathContext((HttpServletRequest)Mockito.any())
		).thenReturn(
			pathContext
		);

		Mockito.when(
			portal.getPathProxy()
		).thenReturn(
			pathProxy
		);

		return portal;
	}

	private static final String[] _RESULTS = {
		"\"@liferay/language/\": \"/o/js/language/\"",
		"\"@liferay/language/\": \"/liferay/o/js/language/\"",
		"\"@liferay/language/\": \"/dxp/o/js/language/\"",
		"\"@liferay/language/\": \"/liferay/dxp/o/js/language/\"",
		"\"@liferay/language/\": \"http://cdn.com/o/js/language/\"",
		"\"@liferay/language/\": \"http://cdn.com/liferay/o/js/language/\"",
		"\"@liferay/language/\": \"http://cdn.com/dxp/o/js/language/\"",
		"\"@liferay/language/\": \"http://cdn.com/liferay/dxp/o/js/language/\"",
		StringBundler.concat(
			"\"@liferay/language/\": \"/o/js/language/\", ",
			"\"/o/frontend-js-web/__liferay__/index.js\": ",
			"\"/o/frontend-js-web/__liferay__/index.(HASH).js\""),
		StringBundler.concat(
			"\"@liferay/language/\": \"/liferay/o/js/language/\", ",
			"\"/liferay/o/frontend-js-web/__liferay__/index.js\": ",
			"\"/liferay/o/frontend-js-web/__liferay__/index.(HASH).js\""),
		StringBundler.concat(
			"\"@liferay/language/\": \"/dxp/o/js/language/\", ",
			"\"/dxp/o/frontend-js-web/__liferay__/index.js\": ",
			"\"/dxp/o/frontend-js-web/__liferay__/index.(HASH).js\""),
		StringBundler.concat(
			"\"@liferay/language/\": \"/liferay/dxp/o/js/language/\", ",
			"\"/liferay/dxp/o/frontend-js-web/__liferay__/index.js\": ",
			"\"/liferay/dxp/o/frontend-js-web/__liferay__/index.(HASH).js\""),
		StringBundler.concat(
			"\"@liferay/language/\": \"http://cdn.com/o/js/language/\", ",
			"\"http://cdn.com/o/frontend-js-web/__liferay__/index.js\": ",
			"\"http://cdn.com/o/frontend-js-web/__liferay__/index.(HASH).js\""),
		StringBundler.concat(
			"\"@liferay/language/\": ",
			"\"http://cdn.com/liferay/o/js/language/\", ",
			"\"http://cdn.com/liferay/o/frontend-js-web/__liferay__/index.",
			"js\": ",
			"\"http://cdn.com/liferay/o/frontend-js-web/__liferay__/index.",
			"(HASH).js\""),
		StringBundler.concat(
			"\"@liferay/language/\": \"http://cdn.com/dxp/o/js/language/\", ",
			"\"http://cdn.com/dxp/o/frontend-js-web/__liferay__/index.js\": ",
			"\"http://cdn.com/dxp/o/frontend-js-web/__liferay__/index.(HASH).",
			"js\""),
		StringBundler.concat(
			"\"@liferay/language/\": ",
			"\"http://cdn.com/liferay/dxp/o/js/language/\", ",
			"\"http://cdn.com/liferay/dxp/o/frontend-js-web/__liferay__",
			"/index.js\": ",
			"\"http://cdn.com/liferay/dxp/o/frontend-js-web/__liferay__",
			"/index.(HASH).js\""),
		StringBundler.concat(
			"\"@liferay/language/\": \"/o/js/language/\", ",
			"\"/o/js/-/frontend-js-web/\": ",
			"\"/o/js/-/frontend-js-web(HASH)/\""),
		StringBundler.concat(
			"\"@liferay/language/\": \"/liferay/o/js/language/\", ",
			"\"/liferay/o/js/-/frontend-js-web/\": ",
			"\"/liferay/o/js/-/frontend-js-web(HASH)/\""),
		StringBundler.concat(
			"\"@liferay/language/\": \"/dxp/o/js/language/\", ",
			"\"/dxp/o/js/-/frontend-js-web/\": ",
			"\"/dxp/o/js/-/frontend-js-web(HASH)/\""),
		StringBundler.concat(
			"\"@liferay/language/\": \"/liferay/dxp/o/js/language/\", ",
			"\"/liferay/dxp/o/js/-/frontend-js-web/\": ",
			"\"/liferay/dxp/o/js/-/frontend-js-web(HASH)/\""),
		StringBundler.concat(
			"\"@liferay/language/\": \"http://cdn.com/o/js/language/\", ",
			"\"http://cdn.com/o/js/-/frontend-js-web/\": ",
			"\"http://cdn.com/o/js/-/frontend-js-web(HASH)/\""),
		StringBundler.concat(
			"\"@liferay/language/\": ",
			"\"http://cdn.com/liferay/o/js/language/\", ",
			"\"http://cdn.com/liferay/o/js/-/frontend-js-web/\": ",
			"\"http://cdn.com/liferay/o/js/-/frontend-js-web(HASH)/\""),
		StringBundler.concat(
			"\"@liferay/language/\": \"http://cdn.com/dxp/o/js/language/\", ",
			"\"http://cdn.com/dxp/o/js/-/frontend-js-web/\": ",
			"\"http://cdn.com/dxp/o/js/-/frontend-js-web(HASH)/\""),
		StringBundler.concat(
			"\"@liferay/language/\": ",
			"\"http://cdn.com/liferay/dxp/o/js/language/\", ",
			"\"http://cdn.com/liferay/dxp/o/js/-/frontend-js-web/\": ",
			"\"http://cdn.com/liferay/dxp/o/js/-/frontend-js-web(HASH)/\"")
	};

}