/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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