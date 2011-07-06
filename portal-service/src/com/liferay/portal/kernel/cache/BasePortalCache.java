/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
 * @author Michael C. Han
 */
public abstract class BasePortalCache implements PortalCache {

	public void destroy() {
	}

	public boolean isDebug() {
		return _debug;
	}

	public void setDebug(boolean debug) {
		_debug = debug;
	}

	private boolean _debug;

}