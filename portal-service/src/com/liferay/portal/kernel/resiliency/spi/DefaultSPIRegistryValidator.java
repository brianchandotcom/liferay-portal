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

import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;

/**
 * @author Shuyang Zhou
 */
public class DefaultSPIRegistryValidator implements SPIRegistryValidator {

	@Override
	public SPI validatePortletSPI(String portletId, SPI spi) {
		if (spi != null) {
			spi = MPIHelperUtil.checkSPILiveness(spi);
		}

		return spi;
	}

	@Override
	public SPI validateServletContextSPI(String servletContextName, SPI spi) {
		if (spi != null) {
			spi = MPIHelperUtil.checkSPILiveness(spi);
		}

		return spi;
	}

}