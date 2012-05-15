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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.SortedProperties;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.pacl.checker.Checker;

import java.security.Permission;

import java.util.HashMap;
import java.util.Map;
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

		try {
			initCheckers();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
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

	public boolean getPropertyBoolean(String key) {
		return GetterUtil.getBoolean(getProperty(key));
	}

	public Set<String> getPropertySet(String key) {
		return SetUtil.fromArray(getPropertyArray(key));
	}

	public String getServletContextName() {
		return _servletContextName;
	}

	public boolean isCheckablePermission(Permission permission) {
		Class<?> clazz = permission.getClass();

		return _checkers.containsKey(clazz.getName());
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

	protected Checker getChecker(Class<? extends Permission> clazz) {
		return _checkers.get(clazz.getName());
	}

	protected Checker initChecker(Checker checker) {
		checker.setPACLPolicy(this);

		checker.afterPropertiesSet();

		return checker;
	}

	protected void initCheckers() throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		Properties portalProperties = PropsUtil.getProperties(
			"portal.security.manager.pacl.policy.checker", false);

		portalProperties = new SortedProperties(portalProperties);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Registering " + portalProperties.size() +
					" PACL policy checkers");
		}

		for (Map.Entry<Object, Object> entry : portalProperties.entrySet()) {
			String key = (String)entry.getKey();

			int x = key.indexOf("[");
			int y = key.indexOf("]");

			String permissionClassName = key.substring(x + 1, y);

			String checkerClassName = (String)entry.getValue();

			Class<?> checkerClass = classLoader.loadClass(checkerClassName);

			Checker checker = (Checker)checkerClass.newInstance();

			initChecker(checker);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Registering permission " + permissionClassName +
						" with PACL policy " + checkerClassName);
			}

			_checkers.put(permissionClassName, checker);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(BasePACLPolicy.class);

	private Map<String, Checker> _checkers = new HashMap<String, Checker>();

	private ClassLoader _classLoader;
	private Properties _properties;
	private String _servletContextName;

}