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

package com.liferay.commerce.product.util;

import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionFriendlyURLUtil {

	public static String getFriendlyURL(
			CPCatalogEntry cpCatalogEntry, ThemeDisplay themeDisplay)
		throws PortalException {

		Group group = themeDisplay.getScopeGroup();

		String currentSiteURL =
			PortalUtil.getPortalURL(themeDisplay) +
				themeDisplay.getPathFriendlyURLPublic() +
					group.getFriendlyURL();

		return currentSiteURL + CPConstants.SEPARATOR_PRODUCT_URL +
			cpCatalogEntry.getUrl();
	}

}