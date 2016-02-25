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

package com.liferay.portal.kernel.cache;

/**
 * @author Joseph Shum
 */
public class PortalCacheException extends RuntimeException {

	public PortalCacheException() {
	}

	public PortalCacheException(String msg) {
		super(msg);
	}

	public PortalCacheException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public PortalCacheException(Throwable cause) {
		super(cause);
	}

}