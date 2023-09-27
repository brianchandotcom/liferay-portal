/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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