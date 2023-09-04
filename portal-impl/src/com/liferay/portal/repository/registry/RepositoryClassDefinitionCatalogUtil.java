/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.repository.registry;

import java.util.Collection;

/**
 * @author Adolfo Pérez
 */
public class RepositoryClassDefinitionCatalogUtil {

	public static Iterable<RepositoryClassDefinition>
		getExternalRepositoryClassDefinitions(long companyId) {

		return _repositoryClassDefinitionCatalog.
			getExternalRepositoryClassDefinitions(companyId);
	}

	public static Collection<String> getExternalRepositoryClassNames(
		long companyId) {

		return _repositoryClassDefinitionCatalog.
			getExternalRepositoryClassNames(companyId);
	}

	public static RepositoryClassDefinition getRepositoryClassDefinition(
		long companyId, String repositoryTypeKey) {

		return _repositoryClassDefinitionCatalog.getRepositoryClassDefinition(
			companyId, repositoryTypeKey);
	}

	public void setRepositoryClassDefinitionCatalog(
		RepositoryClassDefinitionCatalog repositoryClassDefinitionCatalog) {

		_repositoryClassDefinitionCatalog = repositoryClassDefinitionCatalog;
	}

	private static RepositoryClassDefinitionCatalog
		_repositoryClassDefinitionCatalog;

}