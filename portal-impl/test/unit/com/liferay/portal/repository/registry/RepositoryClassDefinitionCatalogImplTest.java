/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.repository.registry;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.repository.RepositoryConfigurationBuilder;
import com.liferay.portal.kernel.repository.registry.RepositoryDefiner;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collection;
import java.util.Objects;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Leon Chi
 */
public class RepositoryClassDefinitionCatalogImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
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
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			RepositoryDefiner.class,
			_getRepositoryDefiner(
				_EXTERNAL_REPOSITORY_DEFINER_CLASS_NAME, true),
			null);
	}

	@After
	public void tearDown() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testGetExternalRepositoryClassDefinitions() throws Exception {
		Collection<RepositoryClassDefinition>
			externalRepositoryClassDefinitions =
				(Collection<RepositoryClassDefinition>)
					_repositoryClassDefinitionCatalogImpl.
						getExternalRepositoryClassDefinitions(
							CompanyConstants.SYSTEM);

		Assert.assertTrue(
			_EXTERNAL_REPOSITORY_DEFINER_CLASS_NAME + " not found in " +
				externalRepositoryClassDefinitions,
			externalRepositoryClassDefinitions.removeIf(
				repositoryClassDefinition ->
					_EXTERNAL_REPOSITORY_DEFINER_CLASS_NAME.equals(
						repositoryClassDefinition.getClassName())));
	}

	@Test
	public void testGetExternalRepositoryClassNames() throws Exception {
		Collection<String> externalRepositoryClassNames =
			_repositoryClassDefinitionCatalogImpl.
				getExternalRepositoryClassNames(CompanyConstants.SYSTEM);

		Assert.assertTrue(
			externalRepositoryClassNames.toString(),
			externalRepositoryClassNames.contains(
				_EXTERNAL_REPOSITORY_DEFINER_CLASS_NAME));
	}

	@Test
	public void testGetRepositoryClassDefinition() throws Exception {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		ServiceRegistration<?> serviceRegistration =
			bundleContext.registerService(
				RepositoryDefiner.class,
				_getRepositoryDefiner(_REPOSITORY_DEFINER_CLASS_NAME, false),
				null);

		try {
			RepositoryClassDefinition repositoryClassDefinition =
				_repositoryClassDefinitionCatalogImpl.
					getRepositoryClassDefinition(
						CompanyConstants.SYSTEM,
						_REPOSITORY_DEFINER_CLASS_NAME);

			Assert.assertEquals(
				_REPOSITORY_DEFINER_CLASS_NAME,
				repositoryClassDefinition.getClassName());

			RepositoryClassDefinition repositoryExternalClassDefinition =
				_repositoryClassDefinitionCatalogImpl.
					getRepositoryClassDefinition(
						CompanyConstants.SYSTEM,
						_EXTERNAL_REPOSITORY_DEFINER_CLASS_NAME);

			Assert.assertEquals(
				_EXTERNAL_REPOSITORY_DEFINER_CLASS_NAME,
				repositoryExternalClassDefinition.getClassName());
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	private RepositoryDefiner _getRepositoryDefiner(
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