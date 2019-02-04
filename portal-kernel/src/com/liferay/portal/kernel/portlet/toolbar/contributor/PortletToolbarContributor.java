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

package com.liferay.portal.kernel.portlet.toolbar.contributor;

import com.liferay.portal.kernel.servlet.taglib.ui.Menu;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * Provides an interface responsible for extending the portlet toolbar by adding
 * more elements.
 *
 * <p>
 * Implementations of this class must be OSGI components that are registered in
 * the OSGI Registry. The way that this component is registered in the OSGI
 * Registry must be consistent with the way the {@link
 * com.liferay.portal.kernel.portlet.toolbar.contributor.locator.PortletToolbarContributorLocator}
 * implementations search for it in the registry.
 * </p>
 *
 * @author Sergio González
 */
public interface PortletToolbarContributor {

	/**
	 * Returns menus to be rendered in the portlet toolbar.
	 *
	 * @param  portletRequest the portlet request
	 * @return menus to be rendered in the portlet toolbar
	 */
	public List<Menu> getPortletTitleMenus(
		PortletRequest portletRequest, PortletResponse portletResponse);

	public default boolean isShowInEditMode() {
		return false;
	}

}