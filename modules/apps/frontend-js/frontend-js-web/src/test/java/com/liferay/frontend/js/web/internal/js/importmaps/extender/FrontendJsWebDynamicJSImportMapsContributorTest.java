/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.js.importmaps.extender;

import com.liferay.frontend.js.web.internal.util.FrontendJsWebUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.frontend.hashed.files.CachingLevel;
import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesRegistry;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;

import java.nio.charset.StandardCharsets;

import java.util.function.BiConsumer;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author Iván Zaera Avellón
 */
public class FrontendJsWebDynamicJSImportMapsContributorTest {

	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testWriteGlobalImports() throws Exception {
		_testWriteGlobalImports("/dxp");
		_testWriteGlobalImports(StringPool.BLANK);
	}

	private HashedFilesRegistry _mockHashedFileRegistry(String pathContext) {
		HashedFilesRegistry hashedFilesRegistry = Mockito.mock(
			HashedFilesRegistry.class);

		Mockito.doAnswer(
			(Answer<Void>)invocationOnMock -> {
				BiConsumer<String, String> biConsumer =
					invocationOnMock.getArgument(0);

				biConsumer.accept(
					pathContext + "/o/frontend-js-web/__liferay__/index.js",
					StringBundler.concat(
						pathContext, "/o/frontend-js-web/__liferay__/index.(",
						_HASH, ").js"));

				return null;
			}
		).when(
			hashedFilesRegistry
		).forEach(
			Mockito.any()
		);

		Mockito.when(
			hashedFilesRegistry.getCachingLevel(Mockito.any())
		).thenReturn(
			CachingLevel.USE_ONE_HASH_PER_FILE
		);

		return hashedFilesRegistry;
	}

	private Portal _mockPortal(String pathContext) throws Exception {
		Portal portal = Mockito.mock(Portal.class);

		Mockito.when(
			portal.getCDNHost(Mockito.any())
		).thenReturn(
			StringPool.BLANK
		);

		Mockito.when(
			portal.getPathContext()
		).thenReturn(
			pathContext
		);

		Mockito.when(
			portal.getPathContext((HttpServletRequest)Mockito.any())
		).thenReturn(
			pathContext
		);

		Mockito.when(
			portal.getPathProxy()
		).thenReturn(
			StringPool.BLANK
		);

		return portal;
	}

	private void _testWriteGlobalImports(String pathContext) throws Exception {
		ReflectionTestUtils.setField(FrontendJsWebUtil.class, "_baseURL", null);
		ReflectionTestUtils.setField(
			FrontendJsWebUtil.class, "_portalContextPath", null);

		FrontendJsWebDynamicJSImportMapsContributor
			frontendJsWebDynamicJSImportMapsContributor =
				new FrontendJsWebDynamicJSImportMapsContributor();

		ReflectionTestUtils.setField(
			frontendJsWebDynamicJSImportMapsContributor, "_hashedFilesRegistry",
			_mockHashedFileRegistry(pathContext));
		ReflectionTestUtils.setField(
			frontendJsWebDynamicJSImportMapsContributor, "_portal",
			_mockPortal(pathContext));

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
			byteArrayOutputStream, StandardCharsets.UTF_8);

		frontendJsWebDynamicJSImportMapsContributor.writeGlobalImports(
			new MockHttpServletRequest(), outputStreamWriter);

		outputStreamWriter.close();

		Assert.assertEquals(
			StringBundler.concat(
				"\"@liferay/language/\": \"", pathContext,
				"/o/js/language/\", \"", pathContext,
				"/o/frontend-js-web/__liferay__/index.js\": \"", pathContext,
				"/o/frontend-js-web/__liferay__/index.(", _HASH, ").js\""),
			byteArrayOutputStream.toString(StandardCharsets.UTF_8));
	}

	private static final String _HASH = RandomTestUtil.randomString(8);

}