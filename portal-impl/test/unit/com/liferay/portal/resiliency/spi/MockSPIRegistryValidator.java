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

package com.liferay.portal.resiliency.spi;

import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIRegistryUtil;
import com.liferay.portal.kernel.resiliency.spi.SPIRegistryValidator;

/**
 * @author Shuyang Zhou
 */
public class MockSPIRegistryValidator implements SPIRegistryValidator {

	@Override
	public SPI validatePortletSPI(String portletId, SPI spi) {
		if (spi != null) {
			spi = SPIRegistryUtil.getErrorSPI();
		}

		return spi;
	}

	@Override
	public SPI validateServletContextSPI(String servletContextName, SPI spi) {
		if (spi != null) {
			spi = SPIRegistryUtil.getErrorSPI();
		}

		return spi;
	}

}