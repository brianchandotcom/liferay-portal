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

package com.liferay.portal.template.freemarker;

import freemarker.template.TemplateException;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Tomas Polesovsky
 */
public class LiferayTemplateClassResolverTest {

	@Before
	public void setUp() {
		_liferayTemplateClassResolver = new LiferayTemplateClassResolver();

		Map<String, Object> properties = new HashMap<>();

		_liferayTemplateClassResolver.activate(properties);
	}

	@Test()
	public void testResolveAllowedClass1() throws Exception {
		Map<String, Object> properties = new HashMap<>();

		properties.put(
			"allowedClasses", "freemarker.template.utility.ClassUtil");
		properties.put("restrictedClasses", "");

		_liferayTemplateClassResolver.activate(properties);

		_liferayTemplateClassResolver.resolve(
			"freemarker.template.utility.ClassUtil", null, null);
	}

	@Test()
	public void testResolveAllowedClass2() throws Exception {
		Map<String, Object> properties = new HashMap<>();

		properties.put("allowedClasses", "freemarker.template.utility.*");
		properties.put("restrictedClasses", "");

		_liferayTemplateClassResolver.activate(properties);

		_liferayTemplateClassResolver.resolve(
			"freemarker.template.utility.ClassUtil", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolvePortalClass() throws Exception {
		_liferayTemplateClassResolver.resolve(
			"com.liferay.portal.model.User", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolveRestrictedClass1() throws Exception {
		_liferayTemplateClassResolver.resolve(
			"freemarker.template.utility.Execute", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolveRestrictedClass2() throws Exception {
		Map<String, Object> properties = new HashMap<>();

		properties.put("allowedClasses", "freemarker.template.utility.*");
		properties.put("restrictedClasses", "");

		_liferayTemplateClassResolver.activate(properties);

		_liferayTemplateClassResolver.resolve(
			"freemarker.template.utility.Execute", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolveRestrictedClass3() throws Exception {
		Map<String, Object> properties = new HashMap<>();

		properties.put("allowedClasses", "com.liferay.portal.model.User");
		properties.put("restrictedClasses", "com.liferay.portal.model.*");

		_liferayTemplateClassResolver.activate(properties);

		_liferayTemplateClassResolver.resolve(
			"com.liferay.portal.model.User", null, null);
	}

	private LiferayTemplateClassResolver _liferayTemplateClassResolver;

}