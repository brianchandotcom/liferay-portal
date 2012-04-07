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

	public BasePACLPolicy(String servletContextName, Properties properties) {
		_servletContextName = servletContextName;
		_properties = properties;
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

	protected Properties getProperties() {
		return _properties;
	}

	protected String getProperty(String key) {
		return _properties.getProperty(key);
	}

	protected String[] getPropertyArray(String key) {
		return StringUtil.split(getProperty(key));
	}

	protected Set<String> getPropertySet(String key) {
		return SetUtil.fromArray(getPropertyArray(key));
	}

	private Properties _properties;
	private String _servletContextName;

}