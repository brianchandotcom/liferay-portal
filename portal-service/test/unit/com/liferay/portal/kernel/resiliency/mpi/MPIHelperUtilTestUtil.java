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

package com.liferay.portal.kernel.resiliency.mpi;

import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class MPIHelperUtilTestUtil {

	public static void directResigterSPI(String spiId, SPI spi)
		throws Exception {

		Map<String, Object> spiProviderContainers =
			(Map<String, Object>)ReflectionTestUtil.getFieldValue(
				MPIHelperUtil.class, "_spiProviderContainers");

		Object spiProviderContainer = spiProviderContainers.get(
			spi.getSPIProviderName());

		Map<String, SPI> spis =
			(Map<String, SPI>)ReflectionTestUtil.getFieldValue(
				spiProviderContainer, "_spis");

		spis.put(spiId, spi);
	}

}