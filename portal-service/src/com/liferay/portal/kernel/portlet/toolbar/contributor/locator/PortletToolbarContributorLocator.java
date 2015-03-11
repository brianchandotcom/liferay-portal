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

package com.liferay.portal.kernel.portlet.toolbar.contributor.locator;

import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;

import java.util.List;

import javax.portlet.PortletRequest;

/**
 * Provides an interface responsible for providing {@link
 * PortletToolbarContributor} instances that extend the portlet toolbar by
 * adding more elements.
 *
 * <p>
 * Implementations of this class must use the OSGI Registry to return {@link
 * PortletToolbarContributor} implementations. The way that the {@link
 * PortletToolbarContributor}s are registered in OSGI Registry must be
 * synchronized with the way that implementations of this class searches for
 * them.
 * </p>
 *
 * <p>
 * Typically, implementations of this class leverage the MVC pattern used the by
 * the portlet. This allows for different extensions to the portlet toolbar for
 * different views of the portlet.
 * </p>
 *
 * <p>
 * Implementations of this class must be OSGI components.
 * </p>
 *
 * @author Sergio González
 */
public interface PortletToolbarContributorLocator {

	/**
	 * Returns portlet toolbar contributors for a particular portlet and
	 * request.
	 *
	 * @param  portletId the portlet's ID
	 * @param  portletRequest the portlet request
	 * @return portlet toolbar contributors for a particular portlet and request
	 */
	public List<PortletToolbarContributor> getPortletToolbarContributors(
		String portletId, PortletRequest portletRequest);

}