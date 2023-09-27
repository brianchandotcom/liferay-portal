/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.url.builder;

import com.liferay.portal.url.builder.facet.BuildableAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.facet.CDNAwareAbsolutePortalURLBuilder;

/**
 * Builds a portlet dependency URL.
 *
 * <p>
 * Portlet dependency resources are retrieved from a configured CSS URN or JS
 * URN if present. (See
 * <code>com.liferay.portal.kernel.util.PropsKeys#PORTLET_DEPENDENCY_CSS_URN</code>
 * and <code>PropsKeys#PORTLET_DEPENDENCY_JAVASCRIPT_URN</code>).
 * </p>
 *
 * <p>
 * If neither are present, the resource is retrieved from a CDN host if
 * configured, or portal otherwise.
 * </p>
 *
 * @author Neil Griffin
 */
public interface PortletDependencyAbsolutePortalURLBuilder
	extends BuildableAbsolutePortalURLBuilder,
			CDNAwareAbsolutePortalURLBuilder
				<PortletDependencyAbsolutePortalURLBuilder> {
}