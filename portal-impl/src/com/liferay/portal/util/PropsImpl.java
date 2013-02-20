/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.Props;

import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class PropsImpl implements Props {

	public boolean contains(String key) {
		return PropsUtil.contains(key);
	}

	public String get(String key) {
		return PropsUtil.get(key);
	}

	public String get(String key, Filter filter) {
		return PropsUtil.get(key, filter);
	}

	public String[] getArray(String key) {
		return PropsUtil.getArray(key);
	}

	public String[] getArray(String key, Filter filter) {
		return PropsUtil.getArray(key, filter);
	}

	public Properties getProperties() {
		return PropsUtil.getProperties();
	}

	public Properties getProperties(String prefix, boolean removePrefix) {
		return PropsUtil.getProperties(prefix, removePrefix);
	}

}