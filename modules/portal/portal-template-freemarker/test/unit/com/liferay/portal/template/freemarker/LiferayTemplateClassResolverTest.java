package com.liferay.portal.template.freemarker;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import freemarker.template.TemplateException;

public class LiferayTemplateClassResolverTest {

	@Before
	public void setUp() {
		_liferayTemplateClassResolver = new LiferayTemplateClassResolver();

		Map<String, Object> properties = new HashMap<>();

		_liferayTemplateClassResolver.activate(properties);
	}

	@Test()
	public void testResolveAllowedClass() throws Exception {
		Map<String, Object> properties = new HashMap<>();

		properties.put("restrictedClasses", "");
		properties.put("allowedClasses", "freemarker.template.utility.ClassUtil");
		_liferayTemplateClassResolver.activate(properties);

		_liferayTemplateClassResolver.resolve(
			"freemarker.template.utility.ClassUtil", null, null);
	}

	@Test()
	public void testResolveAllowedWildcardClass() throws Exception {
		Map<String, Object> properties = new HashMap<>();

		properties.put("restrictedClasses", "");
		properties.put("allowedClasses", "freemarker.template.utility.*");
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
	public void testResolveRestrictedClassAllowedWildcardClass()
		throws Exception {

		Map<String, Object> properties = new HashMap<>();

		properties.put("restrictedClasses", "");
		properties.put("allowedClasses", "freemarker.template.utility.*");
		_liferayTemplateClassResolver.activate(properties);

		_liferayTemplateClassResolver.resolve(
			"freemarker.template.utility.Execute", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolveRestrictedClass() throws Exception {
		_liferayTemplateClassResolver.resolve(
			"freemarker.template.utility.Execute", null, null);
	}

	@Test(expected = TemplateException.class)
	public void testResolveRestrictedWildcardClass() throws Exception {
		Map<String, Object> properties = new HashMap<>();

		properties.put("restrictedClasses", "com.liferay.portal.model.*");
		properties.put("allowedClasses", "com.liferay.portal.model.User");

		_liferayTemplateClassResolver.activate(properties);

		_liferayTemplateClassResolver.resolve(
			"com.liferay.portal.model.User", null, null);
	}

	private LiferayTemplateClassResolver _liferayTemplateClassResolver;
}
