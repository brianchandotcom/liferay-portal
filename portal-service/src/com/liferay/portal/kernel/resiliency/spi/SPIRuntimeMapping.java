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

import java.rmi.RemoteException;

import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public interface SPIRuntimeMapping {

	public void addSkipMappingPortletId(String portletId);

	public SPI getMappingSPIForPortlet(String portletId);

	public SPI getMappingSPIForServletContext(String servletContextName);

	public Set<String> getSkipMappingPortletIds();

	public void register(SPI spi) throws RemoteException;

	public void removeSkipMappingPortletId(String portletId);

	public void unregister(SPI spi);

}