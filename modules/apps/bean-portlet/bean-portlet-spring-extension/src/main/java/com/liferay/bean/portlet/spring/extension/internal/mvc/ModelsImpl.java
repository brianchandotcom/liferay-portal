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

package com.liferay.bean.portlet.spring.extension.internal.mvc;

import java.io.Serializable;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.ManagedBean;

import javax.mvc.Models;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * @author  Neil Griffin
 */
@ManagedBean("models")
@Scope(proxyMode = ScopedProxyMode.INTERFACES, value = "portletRedirect")
public class ModelsImpl implements Models, Serializable {

	@Override
	public Map<String, Object> asMap() {
		return Collections.unmodifiableMap(_modelsMap);
	}

	public void clear() {
		_modelsMap.clear();
	}

	@Override
	public Object get(String name) {
		return get(name, Object.class);
	}

	@Override
	public <T> T get(String name, Class<T> clazz) {
		return clazz.cast(_modelsMap.get(name));
	}

	@Override
	public Iterator<String> iterator() {
		Set<String> keySet = _modelsMap.keySet();

		return keySet.iterator();
	}

	@Override
	public Models put(String name, Object model) {
		_modelsMap.put(name, model);

		return this;
	}

	private static final long serialVersionUID = 2433287856890024741L;

	private final Map<String, Object> _modelsMap = new LinkedHashMap<>();

}