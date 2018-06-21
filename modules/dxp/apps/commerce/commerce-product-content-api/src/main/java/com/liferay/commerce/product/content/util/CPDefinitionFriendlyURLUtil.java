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

package com.liferay.commerce.product.content.util;

import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;

/**
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 */
public class CPDefinitionFriendlyURLUtil {

	public static String getFriendlyURL(
		CPCatalogEntry cpCatalogEntry, ThemeDisplay themeDisplay) {

		Group group = themeDisplay.getScopeGroup();

		Layout layout = themeDisplay.getLayout();

		StringBundler sb = new StringBundler(3);

		sb.append(group.getDisplayURL(themeDisplay, layout.isPrivateLayout()));
		sb.append(CPConstants.SEPARATOR_PRODUCT_URL);
		sb.append(cpCatalogEntry.getUrl());

		return sb.toString();
	}

}