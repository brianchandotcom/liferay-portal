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

import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.repository.util.ExternalRepositoryFactory;

import java.util.Collection;

/**
 * @author Adolfo Pérez
 */
public class RepositoryClassDefinitionCatalogUtil {

	public static Iterable<RepositoryClassDefinition>
		getExternalRepositoryClassDefinitions() {

		return getRepositoryClassDefinitionCatalog().
			getExternalRepositoryClassDefinitions();
	}

	public static Collection<String> getExternalRepositoryClassNames() {
		return getRepositoryClassDefinitionCatalog().
			getExternalRepositoryClassNames();
	}

	public static RepositoryClassDefinition getRepositoryClassDefinition(
		String repositoryTypeKey) {

		return getRepositoryClassDefinitionCatalog().
			getRepositoryClassDefinition(repositoryTypeKey);
	}

	public static RepositoryClassDefinitionCatalog
		getRepositoryClassDefinitionCatalog() {

		return _repositoryClassDefinitionCatalog;
	}

	public static void registerLegacyExternalRepositoryFactory(
		String className, ExternalRepositoryFactory externalRepositoryFactory,
		ResourceBundleLoader resourceBundleLoader) {

		getRepositoryClassDefinitionCatalog().
			registerLegacyExternalRepositoryFactory(
				className, externalRepositoryFactory, resourceBundleLoader);
	}

	public static void unregisterLegacyExternalRepositoryFactory(
		String className) {

		getRepositoryClassDefinitionCatalog().
			unregisterLegacyExternalRepositoryFactory(className);
	}

	public void setRepositoryClassDefinitionCatalog(
		RepositoryClassDefinitionCatalog repositoryClassDefinitionCatalog) {

		_repositoryClassDefinitionCatalog = repositoryClassDefinitionCatalog;
	}

	private static RepositoryClassDefinitionCatalog
		_repositoryClassDefinitionCatalog;

}