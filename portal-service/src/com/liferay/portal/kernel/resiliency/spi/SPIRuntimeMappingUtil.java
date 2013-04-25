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

package com.liferay.portal.kernel.resiliency.spi;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.rmi.RemoteException;

import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class SPIRuntimeMappingUtil {

	public static void addSkipMappingPortletId(String portletId) {
		getSPIRuntimeMapping().addSkipMappingPortletId(portletId);
	}

	public static SPI getMappingSPIForPortlet(String portletId) {
		return getSPIRuntimeMapping().getMappingSPIForPortlet(portletId);
	}

	public static SPI getMappingSPIForServletContext(
		String servletContextName) {

		return getSPIRuntimeMapping().getMappingSPIForServletContext(
			servletContextName);
	}

	public static Set<String> getSkipMappingPortletIds() {
		return getSPIRuntimeMapping().getSkipMappingPortletIds();
	}

	public static SPIRuntimeMapping getSPIRuntimeMapping() {
		PortalRuntimePermission.checkGetBeanProperty(
			SPIRuntimeMappingUtil.class);

		return _spiRuntimeMapping;
	}

	public static void register(SPI spi) throws RemoteException {
		getSPIRuntimeMapping().register(spi);
	}

	public static void removeSkipMappingPortletId(String portletId) {
		getSPIRuntimeMapping().removeSkipMappingPortletId(portletId);
	}

	public static void unregister(SPI spi) {
		getSPIRuntimeMapping().unregister(spi);
	}

	public void setSPIRuntimeMapping(SPIRuntimeMapping spiRuntimeMapping) {
		PortalRuntimePermission.checkSetBeanProperty(
			SPIRuntimeMappingUtil.class);

		_spiRuntimeMapping = spiRuntimeMapping;
	}

	private static SPIRuntimeMapping _spiRuntimeMapping;

}