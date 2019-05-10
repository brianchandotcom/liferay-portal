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

package com.liferay.portal.repository.registry;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.RepositoryConfigurationBuilder;
import com.liferay.portal.kernel.repository.registry.RepositoryDefiner;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.Collection;
import java.util.Objects;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Leon Chi
 */
public class RepositoryClassDefinitionCatalogImplTest {

	@BeforeClass
	public static void setUpClass() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(new LanguageImpl());

		_repositoryClassDefinitionCatalogImpl =
			new RepositoryClassDefinitionCatalogImpl();

		_repositoryClassDefinitionCatalogImpl.afterPropertiesSet();
	}

	@AfterClass
	public static void tearDownClass() {
		_repositoryClassDefinitionCatalogImpl.destroy();
	}

	@Before
	public void setUp() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceRegistration = registry.registerService(
			RepositoryDefiner.class,
			_getRepositoryDefiner(
				_EXTERNAL_REPOSITORY_DEFINER_CLASS_NAME, true));
	}

	@After
	public void tearDown() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testGetExternalRepositoryClassDefinitions() {
		Collection<RepositoryClassDefinition>
			externalRepositoryClassDefinitions =
				(Collection<RepositoryClassDefinition>)
					_repositoryClassDefinitionCatalogImpl.
						getExternalRepositoryClassDefinitions();

		Assert.assertTrue(
			_EXTERNAL_REPOSITORY_DEFINER_CLASS_NAME + " not found in " +
				externalRepositoryClassDefinitions,
			externalRepositoryClassDefinitions.removeIf(
				repositoryClassDefinition ->
					_EXTERNAL_REPOSITORY_DEFINER_CLASS_NAME.equals(
						repositoryClassDefinition.getClassName())));
	}

	@Test
	public void testGetExternalRepositoryClassNames() {
		Collection<String> externalRepositoryClassNames =
			_repositoryClassDefinitionCatalogImpl.
				getExternalRepositoryClassNames();

		Assert.assertTrue(
			externalRepositoryClassNames.toString(),
			externalRepositoryClassNames.contains(
				_EXTERNAL_REPOSITORY_DEFINER_CLASS_NAME));
	}

	@Test
	public void testGetRepositoryClassDefinition() {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<RepositoryDefiner> serviceRegistration =
			registry.registerService(
				RepositoryDefiner.class,
				_getRepositoryDefiner(_REPOSITORY_DEFINER_CLASS_NAME, false));

		try {
			RepositoryClassDefinition repositoryClassDefinition =
				_repositoryClassDefinitionCatalogImpl.
					getRepositoryClassDefinition(
						_REPOSITORY_DEFINER_CLASS_NAME);

			Assert.assertEquals(
				_REPOSITORY_DEFINER_CLASS_NAME,
				repositoryClassDefinition.getClassName());

			RepositoryClassDefinition repositoryExternalClassDefinition =
				_repositoryClassDefinitionCatalogImpl.
					getRepositoryClassDefinition(
						_EXTERNAL_REPOSITORY_DEFINER_CLASS_NAME);

			Assert.assertEquals(
				_EXTERNAL_REPOSITORY_DEFINER_CLASS_NAME,
				repositoryExternalClassDefinition.getClassName());
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	private static RepositoryDefiner _getRepositoryDefiner(
		String className, boolean external) {

		return (RepositoryDefiner)ProxyUtil.newProxyInstance(
			RepositoryDefiner.class.getClassLoader(),
			new Class<?>[] {RepositoryDefiner.class},
			(proxy, method, args) -> {
				if (Objects.equals(method.getName(), "getClassName")) {
					return className;
				}

				if (Objects.equals(
						method.getName(), "getRepositoryConfiguration")) {

					RepositoryConfigurationBuilder
						repositoryConfigurationBuilder =
							new RepositoryConfigurationBuilder();

					return repositoryConfigurationBuilder.build();
				}

				if (Objects.equals(method.getName(), "isExternalRepository")) {
					return external;
				}

				return null;
			});
	}

	private static final String _EXTERNAL_REPOSITORY_DEFINER_CLASS_NAME =
		"TestExternalRepositoryDefiner";

	private static final String _REPOSITORY_DEFINER_CLASS_NAME =
		"TestRepositoryDefiner";

	private static RepositoryClassDefinitionCatalogImpl
		_repositoryClassDefinitionCatalogImpl;

	private ServiceRegistration<RepositoryDefiner> _serviceRegistration;

}