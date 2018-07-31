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

package com.liferay.portal.util;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.util.Props;

import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public class PropsImpl implements Props {

	@Override
	public boolean contains(String key) {
		return PropsUtil.contains(key);
	}

	@Override
	public String get(String key) {
		return PropsUtil.get(key);
	}

	@Override
	public String get(String key, Filter filter) {
		return PropsUtil.get(key, filter);
	}

	@Override
	public String[] getArray(String key) {
		return PropsUtil.getArray(key);
	}

	@Override
	public String[] getArray(String key, Filter filter) {
		return PropsUtil.getArray(key, filter);
	}

	@Override
	public Properties getProperties() {
		return PropsUtil.getProperties();
	}

	@Override
	public Properties getProperties(String prefix, boolean removePrefix) {
		return PropsUtil.getProperties(prefix, removePrefix);
	}

}