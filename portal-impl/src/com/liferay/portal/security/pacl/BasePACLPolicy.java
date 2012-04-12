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

package com.liferay.portal.security.pacl;

import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Properties;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BasePACLPolicy implements PACLPolicy {

	public BasePACLPolicy(
		String servletContextName, ClassLoader classLoader,
		Properties properties) {

		_servletContextName = servletContextName;
		_classLoader = classLoader;
		_properties = properties;
	}

	public ClassLoader getClassLoader() {
		return _classLoader;
	}

	public Properties getProperties() {
		return _properties;
	}

	public String getProperty(String key) {
		return _properties.getProperty(key);
	}

	public String[] getPropertyArray(String key) {
		return StringUtil.split(getProperty(key));
	}

	public Set<String> getPropertySet(String key) {
		return SetUtil.fromArray(getPropertyArray(key));
	}

	public String getServletContextName() {
		return _servletContextName;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{active=");
		sb.append(isActive());
		sb.append(", hashCode=");
		sb.append(hashCode());
		sb.append(", servletContextName=");
		sb.append(_servletContextName);
		sb.append("}");

		return sb.toString();
	}

	private ClassLoader _classLoader;
	private Properties _properties;
	private String _servletContextName;

}