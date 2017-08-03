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

package com.liferay.portal.resiliency.spi.util;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class SPIAdminUtil {

	public static List<Portlet> getCorePortlets() {
		List<Portlet> portlets = PortletLocalServiceUtil.getPortlets();

		Iterator<Portlet> iterator = portlets.iterator();

		while (iterator.hasNext()) {
			Portlet portlet = iterator.next();

			PortletApp portletApp = portlet.getPortletApp();

			if (portletApp.isWARFile() || portlet.isSystem() ||
				portlet.isUndeployedPortlet() ||
				(Arrays.binarySearch(
					PortletPropsValues.SPI_BLACKLIST_PORTLET_IDS,
					portlet.getPortletId()) >= 0)) {

				iterator.remove();
			}
		}

		Collections.sort(portlets, new PortletComparator());

		return portlets;
	}

	public static List<String> getPluginServletContextNames() {
		List<String> servletContextNames = ListUtil.fromCollection(
			ServletContextPool.keySet());

		servletContextNames.remove(PortalUtil.getPathContext());

		Iterator<String> itr = servletContextNames.iterator();

		while (itr.hasNext()) {
			String servletContextName = itr.next();

			if (!servletContextName.contains("portlet") &&
				!servletContextName.contains("web")) {

				itr.remove();
			}
			else if (Arrays.binarySearch(
						PortletPropsValues.SPI_BLACKLIST_SERVLET_CONTEXT_NAMES,
						servletContextName) >= 0) {

				itr.remove();
			}
		}

		return servletContextNames;
	}

	private static class PortletComparator implements Comparator<Portlet> {

		@Override
		public int compare(Portlet portlet1, Portlet portlet2) {
			String displayName1 = portlet1.getDisplayName();
			String displayName2 = portlet2.getDisplayName();

			return displayName1.compareTo(displayName2);
		}

	}

}