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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import javax.servlet.ServletContext;

/**
 * @author Jorge Ferrer
 * @author Dennis Ju
 * @author Brian Wing Shun Chan
 */
public interface PortletLister {

	public TreeView getTreeView() throws PortalException;

	public void setHierarchicalTree(boolean hierarchicalTree);

	public void setIncludeInstanceablePortlets(
		boolean includeInstanceablePortlets);

	public void setIteratePortlets(boolean iteratePortlets);

	public void setLayoutTypePortlet(LayoutTypePortlet layoutTypePortlet);

	public void setRootNodeName(String rootNodeName);

	public void setServletContext(ServletContext servletContext);

	public void setThemeDisplay(ThemeDisplay themeDisplay);

	public void setUser(User user);

}