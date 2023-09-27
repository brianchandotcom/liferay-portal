/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.url.builder;

import com.liferay.portal.url.builder.facet.BuildableAbsolutePortalURLBuilder;

/**
 * Builds a URL that connects to a dynamic servlet.
 *
 * <p>
 * API requests are, by definition, not cacheable and should not be stored in
 * CDNs.
 * </p>
 *
 * @author Iván Zaera Avellón
 */
public interface ServletAbsolutePortalURLBuilder
	extends BuildableAbsolutePortalURLBuilder {
}