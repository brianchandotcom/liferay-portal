/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.theme.minium;

import aQute.bnd.annotation.ProviderType;

import java.io.IOException;

/**
 * @author Marco Leo
 */
@ProviderType
public interface SiteInitializerDependencyResolver {

	public String getDependenciesPath();

	public ClassLoader getDisplayTemplatesClassLoader();

	public String getDisplayTemplatesDependencyPath();

	public ClassLoader getDocumentsClassLoader();

	public String getDocumentsDependencyPath();

	public ClassLoader getImageClassLoader();

	public String getImageDependencyPath();

	public String getJSON(String name) throws IOException;

	public String getKey();

}