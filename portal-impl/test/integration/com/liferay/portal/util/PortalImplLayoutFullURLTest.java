/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.service.VirtualHostLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

import java.net.URL;

import org.junit.Test;

/**
 * @author Akos Thurzo
 */
public class PortalImplLayoutFullURLTest extends PortalImplBaseURLTestCase {

	@Test
	public void testFromCompanyVirtualHost() throws Exception {
		LayoutSet publicLayoutSet = publicLayout.getLayoutSet();

		VirtualHostLocalServiceUtil.updateVirtualHost(
			company.getCompanyId(), publicLayoutSet.getLayoutSetId(),
			VIRTUAL_HOSTNAME);

		ThemeDisplay themeDisplay = initThemeDisplay(
			company, group, publicLayout, "company.com");

		String fullURL = PortalUtil.getLayoutFullURL(
			publicLayout, themeDisplay);

		// Check if url is malformed

		new URL(fullURL);
	}

}