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

package com.liferay.portal.resiliency.spi.cache;

import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.nio.intraband.cache.IntrabandPortalCacheManager;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIUtil;

import java.io.Serializable;

import java.rmi.RemoteException;

/**
 * @author Shuyang Zhou
 */
public class SPIPortalCacheManagerConfigurator {

	public static <K extends Serializable, V extends Serializable>
		PortalCacheManager<K, V> createSPIPortalCacheManager(
			PortalCacheManager<K, V> portalCacheManager)
		throws RemoteException {

		if (SPIUtil.isSPI()) {
			SPI spi = SPIUtil.getSPI();

			portalCacheManager = new IntrabandPortalCacheManager<K, V>(
				spi.getRegistrationReference());
		}

		IntrabandPortalCacheManager.setPortalCacheManager(portalCacheManager);

		return portalCacheManager;
	}

}