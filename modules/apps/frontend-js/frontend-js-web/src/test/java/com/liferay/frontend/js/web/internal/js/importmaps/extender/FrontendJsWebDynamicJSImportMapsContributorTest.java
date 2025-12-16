/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.js.importmaps.extender;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesRegistry;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
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
		_testWriteGlobalImports(StringPool.BLANK);
	}

	@Test
	public void testWriteGlobalImportsWithPathContext() throws Exception {
		_testWriteGlobalImports("/dxp");
	}

	private void _testWriteGlobalImports(String pathContext) throws Exception {
		FrontendJsWebDynamicJSImportMapsContributor
			frontendJsWebDynamicJSImportMapsContributor =
				new FrontendJsWebDynamicJSImportMapsContributor();

		// HashedFilesRegistry

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

		ReflectionTestUtils.setField(
			frontendJsWebDynamicJSImportMapsContributor, "_hashedFilesRegistry",
			hashedFilesRegistry);

		// Portal

		Portal portal = Mockito.mock(Portal.class);

		Mockito.when(
			portal.getCDNHost(Mockito.any())
		).thenReturn(
			StringPool.BLANK
		);

		Mockito.when(
			portal.getPathContext((HttpServletRequest)Mockito.any())
		).thenReturn(
			pathContext
		);

		ReflectionTestUtils.setField(
			frontendJsWebDynamicJSImportMapsContributor, "_portal", portal);

		// Run test

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
			byteArrayOutputStream, StandardCharsets.UTF_8);

		frontendJsWebDynamicJSImportMapsContributor.writeGlobalImports(
			new MockHttpServletRequest(), outputStreamWriter);

		outputStreamWriter.close();

		Assert.assertEquals(
			StringUtil.replace(
				_TPL_TEST_WRITE_GLOBAL_IMPORTS, "[$", "$]",
				HashMapBuilder.put(
					"HASH", _HASH
				).put(
					"PATH_CONTEXT", pathContext
				).build()),
			byteArrayOutputStream.toString(StandardCharsets.UTF_8));
	}

	private static final String _HASH = RandomTestUtil.randomString(8);

	private static final String _TPL_TEST_WRITE_GLOBAL_IMPORTS =
		StringUtil.read(
			FrontendJsWebDynamicJSImportMapsContributorTest.class,
			"dependencies/test_write_global_imports.tpl");

}