/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.url.builder.internal;

import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesRegistry;
import com.liferay.portal.url.builder.ESModuleAbsolutePortalURLBuilder;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class ESModuleAbsolutePortalURLBuilderImpl
	extends BaseWebContextResourceAbsolutePortalURLBuilderImpl
		<ESModuleAbsolutePortalURLBuilder>
	implements ESModuleAbsolutePortalURLBuilder {

	public ESModuleAbsolutePortalURLBuilderImpl(
		String cdnHost, String esModulePath,
		HashedFilesRegistry hashedFilesRegistry,
		HttpServletRequest httpServletRequest, String pathModule,
		String pathProxy, String webContextPath) {

		super(
			cdnHost, hashedFilesRegistry, httpServletRequest, pathModule,
			pathProxy, "/__liferay__/" + esModulePath, webContextPath);
	}

}