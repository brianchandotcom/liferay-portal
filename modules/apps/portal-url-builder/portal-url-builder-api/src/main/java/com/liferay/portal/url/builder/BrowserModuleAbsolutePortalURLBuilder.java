/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.url.builder;

import com.liferay.portal.url.builder.facet.BuildableAbsolutePortalURLBuilder;

/**
 * Builds a browser module script resource URL.
 *
 * <p>
 * This is used by the AMD server side resolver and it is very specialized since
 * it is part of the server AMD loader protocol and cannot/should not be used
 * for any other purpose.
 * </p>
 *
 * <p>
 * The URLs returned by this builder have to be usable both for isolated
 * requests and combo requests thus, they do not contain any parameter or cache
 * control, since those have to be managed by the AMD loader when composing the
 * actual request.
 * </p>
 *
 * @author Iván Zaera Avellón
 */
public interface BrowserModuleAbsolutePortalURLBuilder
	extends BuildableAbsolutePortalURLBuilder {
}