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

import com.liferay.portal.kernel.process.ProcessExecutor;

import java.util.concurrent.ConcurrentMap;

/**
 * @author Shuyang Zhou
 */
public class SPIUtil {

	public static SPI getSPI() {
		if (_spi == null) {
			throw new IllegalStateException(
				"Current process is not a spi instance");
		}

		return _spi;
	}

	public static boolean isSPI() {
		if (_spi == null) {
			return false;
		}
		else {
			return true;
		}
	}

	private static final SPI _spi;

	static {
		ConcurrentMap<String, Object> attributes =
			ProcessExecutor.ProcessContext.getAttributes();

		_spi = (SPI)attributes.remove(SPI.SPI_INSTANCE_PUBLICATION_KEY);
	}

}