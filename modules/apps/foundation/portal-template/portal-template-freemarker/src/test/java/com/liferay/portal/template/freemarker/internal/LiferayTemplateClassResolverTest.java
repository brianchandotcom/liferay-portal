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

package com.liferay.portal.template.freemarker.internal;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.template.freemarker.configuration.FreeMarkerEngineConfiguration;

import freemarker.core.TemplateClassResolver;

import freemarker.template.TemplateException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Tomas Polesovsky
 * @author Manuel de la Peña
 */
public class LiferayTemplateClassResolverTest {

	@Before
	public void setUp() throws Exception {
		_liferayTemplateClassResolver = new LiferayTemplateClassResolver();

		_updateProperties(null, null);
	}

	@Test
	public void testResolveAllowedClassByClassName() throws Exception {
		_updateProperties("freemarker.template.utility.ClassUtil", "");

		_liferayTemplateClassResolver.resolve(
			"freemarker.template.utility.ClassUtil", null, null);
	}

	@Test
	public void testResolveAllowedClassByStar() throws Exception {
		_updateProperties("freemarker.template.utility.*", "");

		_liferayTemplateClassResolver.resolve(
			"freemarker.template.utility.ClassUtil", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolveAllowedExecuteClass() throws Exception {
		_updateProperties("freemarker.template.utility.*", "");

		_liferayTemplateClassResolver.resolve(
			"freemarker.template.utility.Execute", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolveAllowedObjectConstructorClass() throws Exception {
		_updateProperties("freemarker.template.utility.*", "");

		_liferayTemplateClassResolver.resolve(
			"freemarker.template.utility.ObjectConstructor", null, null);
	}

	@Test
	public void testResolveAllowedPortalClass() throws Exception {
		_updateProperties("com.liferay.portal.kernel.model.User", null);

		_liferayTemplateClassResolver.resolve(
			"com.liferay.portal.kernel.model.User", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolveAllowedPortalClassExplicitlyRestricted()
		throws Exception {

		_updateProperties(
			"com.liferay.portal.kernel.model.User",
			"com.liferay.portal.kernel.model.*");

		_liferayTemplateClassResolver.resolve(
			"com.liferay.portal.kernel.model.User", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolveClassClass() throws Exception {
		_liferayTemplateClassResolver.resolve("java.lang.Class", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolveClassLoaderClass() throws Exception {
		_liferayTemplateClassResolver.resolve(
			"java.lang.ClassLoader", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolveExecuteClass() throws Exception {
		_liferayTemplateClassResolver.resolve(
			"freemarker.template.utility.Execute", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolveNotAllowedPortalClass() throws Exception {
		_liferayTemplateClassResolver.resolve(
			"com.liferay.portal.kernel.model.User", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolveObjectConstructorClass() throws Exception {
		_liferayTemplateClassResolver.resolve(
			"freemarker.template.utility.ObjectConstructor", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolveThreadClass() throws Exception {
		_liferayTemplateClassResolver.resolve("java.lang.Thread", null, null);
	}

	private void _updateProperties(
			String allowedClasses, String restrictedClasses)
		throws Exception {

		Object freeMarkerEngineConfiguration = ProxyUtil.newProxyInstance(
			LiferayTemplateClassResolverTest.class.getClassLoader(),
			new Class<?>[] {FreeMarkerEngineConfiguration.class},
			new InvocationHandler() {

				@Override
				public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {

					String methodName = method.getName();

					if (methodName.equals("allowedClasses")) {
						if (allowedClasses != null) {
							return new String[] {allowedClasses};
						}

						return null;
					}
					else if (methodName.equals("restrictedClasses")) {
						if (restrictedClasses != null) {
							return new String[] {restrictedClasses};
						}

						return new String[] {
							"java.lang.Class", "java.lang.ClassLoader",
							"java.lang.Thread"
						};
					}

					return null;
				}

			});

		ReflectionTestUtil.setFieldValue(
			_liferayTemplateClassResolver, "_freemarkerEngineConfiguration",
			freeMarkerEngineConfiguration);
	}

	private TemplateClassResolver _liferayTemplateClassResolver;

}