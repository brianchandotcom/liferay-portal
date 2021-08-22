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

package com.liferay.object.internal.scope;

import com.liferay.object.scope.ObjectScopeGroupHttpRetriever;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class PortalObjectScopeGroupHttpRetriever
	implements ObjectScopeGroupHttpRetriever {

	public PortalObjectScopeGroupHttpRetriever(Portal portal) {
		_portal = portal;
	}

	@Override
	public long getGroupId(HttpServletRequest httpServletRequest)
		throws PortalException {

		return _portal.getScopeGroupId(httpServletRequest);
	}

	private final Portal _portal;

}