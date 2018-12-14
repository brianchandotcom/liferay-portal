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

package com.liferay.portal.kernel.servlet;

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tina Tian
 */
@NewEnv(type = NewEnv.Type.CLASSLOADER)
public class ServletContextClassLoaderPoolTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, NewEnvTestRule.INSTANCE);

	@Test
	public void testGetClassLoader() {
		_testGetClassLoader(false);
	}

	@Test
	public void testGetClassLoaderWithFallback() {
		_testGetClassLoader(true);
	}

	@Test
	public void testGetServletContextName() {
		_testGetServletContextName(false);
	}

	@Test
	public void testGetServletContextNameWithFallback() {
		_testGetServletContextName(true);
	}

	@Test
	public void testMisc() {
		PropsTestUtil.setProps(Collections.emptyMap());

		new ServletContextClassLoaderPool();

		ServletContextClassLoaderPool.unregister(_TEST_SERVLET_CONTEXT_NAME);
	}

	private void _testGetClassLoader(boolean fallback) {
		PropsTestUtil.setProps(
			PropsKeys.SERVLET_CONTEXT_CLASS_LOADER_POOL_FALLBACK,
			String.valueOf(fallback));

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		if (fallback) {
			Assert.assertSame(
				contextClassLoader,
				ServletContextClassLoaderPool.getClassLoader(null));
			Assert.assertSame(
				contextClassLoader,
				ServletContextClassLoaderPool.getClassLoader(
					_TEST_SERVLET_CONTEXT_NAME));
		}
		else {
			Assert.assertNull(
				ServletContextClassLoaderPool.getClassLoader(null));
			Assert.assertNull(
				ServletContextClassLoaderPool.getClassLoader(
					_TEST_SERVLET_CONTEXT_NAME));
		}

		ServletContextClassLoaderPool.register(
			_TEST_SERVLET_CONTEXT_NAME, _TEST_CLASS_LOADER);

		Assert.assertSame(
			_TEST_CLASS_LOADER,
			ServletContextClassLoaderPool.getClassLoader(
				_TEST_SERVLET_CONTEXT_NAME));

		ServletContextClassLoaderPool.unregister(_TEST_SERVLET_CONTEXT_NAME);

		if (fallback) {
			Assert.assertSame(
				contextClassLoader,
				ServletContextClassLoaderPool.getClassLoader(
					_TEST_SERVLET_CONTEXT_NAME));
		}
		else {
			Assert.assertNull(
				ServletContextClassLoaderPool.getClassLoader(
					_TEST_SERVLET_CONTEXT_NAME));
		}

		ClassLoaderPool.register(
			_TEST_SERVLET_CONTEXT_NAME, _TEST_CLASS_LOADER);

		if (fallback) {
			Assert.assertSame(
				_TEST_CLASS_LOADER,
				ServletContextClassLoaderPool.getClassLoader(
					_TEST_SERVLET_CONTEXT_NAME));
		}
		else {
			Assert.assertNull(
				ServletContextClassLoaderPool.getClassLoader(
					_TEST_SERVLET_CONTEXT_NAME));
		}

		ClassLoaderPool.unregister(_TEST_SERVLET_CONTEXT_NAME);
	}

	private void _testGetServletContextName(boolean fallback) {
		PropsTestUtil.setProps(
			PropsKeys.SERVLET_CONTEXT_CLASS_LOADER_POOL_FALLBACK,
			String.valueOf(fallback));

		if (fallback) {
			Assert.assertEquals(
				"null",
				ServletContextClassLoaderPool.getServletContextName(null));
			Assert.assertEquals(
				"null",
				ServletContextClassLoaderPool.getServletContextName(
					_TEST_CLASS_LOADER));
		}
		else {
			Assert.assertNull(
				ServletContextClassLoaderPool.getServletContextName(null));
			Assert.assertNull(
				ServletContextClassLoaderPool.getServletContextName(
					_TEST_CLASS_LOADER));
		}

		ServletContextClassLoaderPool.register(
			_TEST_SERVLET_CONTEXT_NAME, _TEST_CLASS_LOADER);

		Assert.assertEquals(
			_TEST_SERVLET_CONTEXT_NAME,
			ServletContextClassLoaderPool.getServletContextName(
				_TEST_CLASS_LOADER));

		ServletContextClassLoaderPool.unregister(_TEST_SERVLET_CONTEXT_NAME);

		if (fallback) {
			Assert.assertEquals(
				"null",
				ServletContextClassLoaderPool.getServletContextName(
					_TEST_CLASS_LOADER));
		}
		else {
			Assert.assertNull(
				ServletContextClassLoaderPool.getServletContextName(
					_TEST_CLASS_LOADER));
		}

		ClassLoaderPool.register(
			_TEST_SERVLET_CONTEXT_NAME, _TEST_CLASS_LOADER);

		if (fallback) {
			Assert.assertEquals(
				_TEST_SERVLET_CONTEXT_NAME,
				ServletContextClassLoaderPool.getServletContextName(
					_TEST_CLASS_LOADER));
		}
		else {
			Assert.assertNull(
				ServletContextClassLoaderPool.getServletContextName(
					_TEST_CLASS_LOADER));
		}

		ClassLoaderPool.unregister(_TEST_SERVLET_CONTEXT_NAME);
	}

	private static final ClassLoader _TEST_CLASS_LOADER = new ClassLoader() {
	};

	private static final String _TEST_SERVLET_CONTEXT_NAME =
		"TEST_SERVLET_CONTEXT_NAME";

}