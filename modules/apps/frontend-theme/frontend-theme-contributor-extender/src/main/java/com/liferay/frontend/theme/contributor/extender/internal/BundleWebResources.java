/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.theme.contributor.extender.internal;

import java.util.Collection;

/**
 * @author Carlos Sierra Andrés
 */
public interface BundleWebResources {

	public Collection<String> getCssResourcePaths();

	public Collection<String> getJsResourcePaths();

	public String getServletContextPath();

}