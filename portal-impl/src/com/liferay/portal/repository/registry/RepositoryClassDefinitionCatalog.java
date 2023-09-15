/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.repository.registry;

import java.util.Collection;

/**
 * @author Adolfo Pérez
 */
public interface RepositoryClassDefinitionCatalog {

	public Iterable<RepositoryClassDefinition>
		getExternalRepositoryClassDefinitions(long companyId);

	public Collection<String> getExternalRepositoryClassNames(long companyId);

	public RepositoryClassDefinition getRepositoryClassDefinition(
		long companyId, String className);

}