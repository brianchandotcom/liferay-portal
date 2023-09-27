/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.loader.modules.extender.esm;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.taglib.aui.ESImport;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.ESModuleAbsolutePortalURLBuilder;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

/**
 * @author Iván Zaera Avellón
 */
public class ESImportUtilTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetESImportWithAliasedSymbol() {
		ESImport esImport = ESImportUtil.getESImport(
			_getAbsolutePortalURLBuilder(),
			"{x as y} from my-package/lib/index.js");

		Assert.assertEquals("y", esImport.getAlias());
		Assert.assertEquals("my-package|lib/index.js", esImport.getModule());
		Assert.assertEquals("x", esImport.getSymbol());
	}

	@Test
	public void testGetESImportWithAliasedSymbolAndAliasOverride() {
		ESImport esImport = ESImportUtil.getESImport(
			_getAbsolutePortalURLBuilder(), "z",
			"{x as y} from my-package/lib/index.js");

		Assert.assertEquals("z", esImport.getAlias());
		Assert.assertEquals("my-package|lib/index.js", esImport.getModule());
		Assert.assertEquals("x", esImport.getSymbol());
	}

	@Test
	public void testGetESImportWithDefaultImport() {
		ESImport esImport = ESImportUtil.getESImport(
			_getAbsolutePortalURLBuilder(), "x from my-package/lib/index.js");

		Assert.assertEquals("x", esImport.getAlias());
		Assert.assertEquals("my-package|lib/index.js", esImport.getModule());
		Assert.assertEquals(StringPool.BLANK, esImport.getSymbol());
	}

	@Test
	public void testGetESImportWithDefaultImportAndAliasOverride() {
		ESImport esImport = ESImportUtil.getESImport(
			_getAbsolutePortalURLBuilder(), "z",
			"x from my-package/lib/index.js");

		Assert.assertEquals("z", esImport.getAlias());
		Assert.assertEquals("my-package|lib/index.js", esImport.getModule());
		Assert.assertEquals(StringPool.BLANK, esImport.getSymbol());
	}

	@Test
	public void testGetESImportWithDefaultModule() {
		ESImport esImport = ESImportUtil.getESImport(
			_getAbsolutePortalURLBuilder(), "{x} from my-package");

		Assert.assertEquals(StringPool.BLANK, esImport.getAlias());
		Assert.assertEquals("my-package|index.js", esImport.getModule());
		Assert.assertEquals("x", esImport.getSymbol());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetESImportWithInvalidModule() {
		ESImportUtil.getESImport(
			_getAbsolutePortalURLBuilder(),
			"frontend-taglib-clay@1.1.0/index.js");
	}

	@Test
	public void testGetESImportWithNoSymbol() {
		ESImport esImport = ESImportUtil.getESImport(
			_getAbsolutePortalURLBuilder(), "from my-package/css/main.css.js");

		Assert.assertEquals(StringPool.BLANK, esImport.getAlias());
		Assert.assertEquals("my-package|css/main.css.js", esImport.getModule());
		Assert.assertEquals(StringPool.BLANK, esImport.getSymbol());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetESImportWithNoSymbolAndAliasOverride() {
		ESImportUtil.getESImport(
			_getAbsolutePortalURLBuilder(), "z",
			"from my-package/css/main.css.js");
	}

	@Test
	public void testGetESImportWithSymbol() {
		ESImport esImport = ESImportUtil.getESImport(
			_getAbsolutePortalURLBuilder(), "{x} from my-package/lib/index.js");

		Assert.assertEquals(StringPool.BLANK, esImport.getAlias());
		Assert.assertEquals("my-package|lib/index.js", esImport.getModule());
		Assert.assertEquals("x", esImport.getSymbol());
	}

	@Test
	public void testGetESImportWithSymbolAndAliasOverride() {
		ESImport esImport = ESImportUtil.getESImport(
			_getAbsolutePortalURLBuilder(), "z",
			"{x} from my-package/lib/index.js");

		Assert.assertEquals("z", esImport.getAlias());
		Assert.assertEquals("my-package|lib/index.js", esImport.getModule());
		Assert.assertEquals("x", esImport.getSymbol());
	}

	@Test
	public void testIsESImport() {
		Assert.assertTrue(
			ESImportUtil.isESImport("aName from my-nice-package/index.js"));
		Assert.assertTrue(
			ESImportUtil.isESImport("from my-nice-package/index.js"));
		Assert.assertFalse(
			ESImportUtil.isESImport("frontend-taglib-clay@1.1.0/index.js"));
		Assert.assertTrue(
			ESImportUtil.isESImport("{something} from a-package/index.js"));
	}

	private AbsolutePortalURLBuilder _getAbsolutePortalURLBuilder() {
		AbsolutePortalURLBuilder absolutePortalURLBuilder = Mockito.mock(
			AbsolutePortalURLBuilder.class);

		Mockito.when(
			absolutePortalURLBuilder.forESModule(
				Mockito.anyString(), Mockito.anyString())
		).thenAnswer(
			(Answer<ESModuleAbsolutePortalURLBuilder>)invocationOnMock -> {
				ESModuleAbsolutePortalURLBuilder
					esModuleAbsolutePortalURLBuilder = Mockito.mock(
						ESModuleAbsolutePortalURLBuilder.class);

				String webContextPath = invocationOnMock.getArgument(0);
				String esModulePath = invocationOnMock.getArgument(1);

				Mockito.when(
					esModuleAbsolutePortalURLBuilder.build()
				).thenReturn(
					webContextPath + StringPool.PIPE + esModulePath
				);

				return esModuleAbsolutePortalURLBuilder;
			}
		);

		return absolutePortalURLBuilder;
	}

}