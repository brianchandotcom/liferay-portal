/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.initializer.extender;

import com.liferay.portal.kernel.service.ServiceContext;

import javax.servlet.ServletContext;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Nilton Vieira
 */
@ProviderType
public interface OSBSiteInitializer {

	public void addOrUpdateSXPBlueprint(
			ServiceContext serviceContext, ServletContext servletContext)
		throws Exception;

}