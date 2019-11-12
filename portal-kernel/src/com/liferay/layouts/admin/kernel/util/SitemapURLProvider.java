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

package com.liferay.layouts.admin.kernel.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.xml.Element;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author     Eduardo García
 * @deprecated As of Mueller (7.2.x), replaced by {@link
 *             com.liferay.layout.admin.kernel.util.SitemapURLProvider}
 */
@Deprecated
@ProviderType
public interface SitemapURLProvider {

	public String getClassName();

	public void visitLayout(
			Element element, String layoutUuid, LayoutSet layoutSet,
			ThemeDisplay themeDisplay)
		throws PortalException;

	public void visitLayoutSet(
			Element element, LayoutSet layoutSet, ThemeDisplay themeDisplay)
		throws PortalException;

}