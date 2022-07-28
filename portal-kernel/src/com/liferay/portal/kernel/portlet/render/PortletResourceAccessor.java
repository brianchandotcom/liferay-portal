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

package com.liferay.portal.kernel.portlet.render;

import com.liferay.portal.kernel.model.Portlet;

import java.util.Collection;

/**
 * @author Iván Zaera Avellón
 */
public abstract class PortletResourceAccessor {

	public PortletResourceAccessor(boolean portalResource) {
		_portalResource = portalResource;
	}

	public abstract Collection<String> get(Portlet portlet);

	public final boolean isPortalResource() {
		return _portalResource;
	}

	private final boolean _portalResource;

}