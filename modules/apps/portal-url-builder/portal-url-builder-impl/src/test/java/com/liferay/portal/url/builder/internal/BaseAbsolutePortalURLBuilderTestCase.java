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

package com.liferay.portal.url.builder.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;

import org.mockito.Mockito;

/**
 * @author Iván Zaera Avellón
 */
public abstract class BaseAbsolutePortalURLBuilderTestCase {

	protected Portal mockPortal(boolean context, boolean proxy, boolean cdnHost)
		throws PortalException {

		Portal portal = Mockito.mock(Portal.class);

		String pathProxy = proxy ? "/proxy" : StringPool.BLANK;

		Mockito.when(
			portal.getPathProxy()
		).thenReturn(
			pathProxy
		);

		Mockito.when(
			portal.getPathContext()
		).thenReturn(
			pathProxy + (context ? "/context" : StringPool.BLANK)
		);

		Mockito.when(
			portal.getCDNHost(Mockito.any(HttpServletRequest.class))
		).thenReturn(
			cdnHost ? "http://cdn-host" : StringPool.BLANK
		);

		return portal;
	}

}