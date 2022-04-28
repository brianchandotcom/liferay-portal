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

package com.liferay.portal.url.builder;

import com.liferay.portal.url.builder.facet.BuildableAbsolutePortalURLBuilder;

/**
 * Builds a browser module script resource URL.
 *
 * <p>
 * This is used by the AMD server side resolver and it is very specialized since
 * it is part of the server-AMD loader protocol and cannot/shouldn't be used for
 * any other purpose.
 * </p>
 *
 * <p>
 * The URLs returned by this builder have to be usable both for isolated
 * requests and combo requests thus, they don't contain any parameter or cache
 * control, since those have to be managed by the AMD loader when composing the
 * actual request.
 * </p>
 *
 * @author Iván Zaera Avellón
 */
public interface BrowserModuleAbsolutePortalURLBuilder
	extends BuildableAbsolutePortalURLBuilder {
}