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

package com.liferay.portal.kernel.resiliency.spi;

import java.rmi.RemoteException;

import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class SPIRegistryUtil {

	public static void addExcludedPortletId(String portletId) {
		getSPIRegistry().addExcludedPortletId(portletId);
	}

	public static SPI getErrorSPI() {
		return getSPIRegistry().getErrorSPI();
	}

	public static Set<String> getExcludedPortletIds() {
		return getSPIRegistry().getExcludedPortletIds();
	}

	public static SPI getPortletSPI(String portletId) {
		return getSPIRegistry().getPortletSPI(portletId);
	}

	public static SPI getServletContextSPI(String servletContextName) {
		return getSPIRegistry().getServletContextSPI(servletContextName);
	}

	public static SPIRegistry getSPIRegistry() {
		return _spiRegistry;
	}

	public static void registerSPI(SPI spi) throws RemoteException {
		getSPIRegistry().registerSPI(spi);
	}

	public static void removeExcludedPortletId(String portletId) {
		getSPIRegistry().removeExcludedPortletId(portletId);
	}

	public static void setSPIRegistryValidator(
		SPIRegistryValidator spiRegistryValidator) {

		getSPIRegistry().setSPIRegistryValidator(spiRegistryValidator);
	}

	public static void unregisterSPI(SPI spi) {
		getSPIRegistry().unregisterSPI(spi);
	}

	public void setSPIRegistry(SPIRegistry spiRegistry) {
		_spiRegistry = spiRegistry;
	}

	private static SPIRegistry _spiRegistry;

}