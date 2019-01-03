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

package com.liferay.portal.osgi.web.wab.extender.internal.registration;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletSecurityElement;

/**
 * @author Juan González
 */
public class ServletRegistrationImpl implements ServletRegistration.Dynamic {

	@Override
	public Set<String> addMapping(String... urlPatterns) {
		for (String urlPattern : urlPatterns) {
			if (!_mappings.contains(urlPattern)) {
				_mappings.add(urlPattern);
			}
		}

		return new HashSet<>();
	}

	@Override
	public String getClassName() {
		return _className;
	}

	@Override
	public String getInitParameter(String name) {
		return _initParameters.get(name);
	}

	@Override
	public Map<String, String> getInitParameters() {
		return _initParameters;
	}

	public Servlet getInstance() {
		return _instance;
	}

	public String getJspFile() {
		return _jspFile;
	}

	public int getLoadOnStartup() {
		return _loadOnStartup;
	}

	@Override
	public Collection<String> getMappings() {
		return _mappings;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getRunAsRole() {
		throw new UnsupportedOperationException();
	}

	public boolean isAsyncSupported() {
		return _asyncSupported;
	}

	@Override
	public void setAsyncSupported(boolean asyncSupported) {
		_asyncSupported = asyncSupported;
	}

	public void setClassName(String className) {
		_className = className;
	}

	@Override
	public boolean setInitParameter(String name, String value) {
		boolean exists = _initParameters.containsKey(name);

		_initParameters.put(name, value);

		return exists;
	}

	@Override
	public Set<String> setInitParameters(Map<String, String> initParameters) {
		_initParameters = initParameters;

		return new HashSet<>();
	}

	public void setInstance(Servlet instance) {
		_instance = instance;
	}

	public void setJspFile(String jspFile) {
		_jspFile = jspFile;
	}

	@Override
	public void setLoadOnStartup(int loadOnStartup) {
		_loadOnStartup = loadOnStartup;
	}

	@Override
	public void setMultipartConfig(MultipartConfigElement multipartConfig) {
		throw new UnsupportedOperationException();
	}

	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setRunAsRole(String roleName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> setServletSecurity(ServletSecurityElement constraint) {
		throw new UnsupportedOperationException();
	}

	private boolean _asyncSupported;
	private String _className;
	private Map<String, String> _initParameters = new HashMap<>();
	private Servlet _instance;
	private String _jspFile;
	private int _loadOnStartup;
	private final Collection<String> _mappings = new HashSet<>();
	private String _name;

}